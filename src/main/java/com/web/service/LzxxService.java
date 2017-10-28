package com.web.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.zxing.WriterException;
import com.utils.FileUtil;
import com.utils.QrcodeUtils;
import com.utils.enums.LzType;
import com.utils.enums.Role;
import com.web.dao.BgrRepository;
import com.web.dao.LzxxRepository;
import com.web.dao.ZichanRepository;
import com.web.entity.Lzxx;
import com.web.entity.Zichan;

@Service
public class LzxxService {
	private static Logger log = Logger.getLogger(LzxxService.class);
	@Autowired
	private LzxxRepository lzxxRep;
	
	@Autowired
	private ZichanRepository zichanRep;
	
	@Autowired
	private BgrRepository bgrResp;
	
	@Autowired
	private QrcodeUtils qrcodeUtils;
	
	@Autowired
	private FileUtil fileUtil;
	
	@Autowired
	private ZichanService zichanService;
	
	/**
	 * 保存流转信息
	 * @param zcIds 资产ID与数量
	 * @param flag 标识(用于区分 出库 流转 回收)
	 * @param bgr 操作用户(可能是MA或者MK)
	 * @return 操作ID
	 */
	public String save(Map<String, Object> zcInfoMap, LzType flag, String bgrId) {
		String fcrId = null; //发出人
		String jsrId = null; //接受人
		if(bgrId != null) {
			List<String> qxList = bgrResp.queryQxByBgr(bgrId);//当前用户具备的权限
			switch(flag) {
			case CK : //----出库----
				if(qxList.contains(Role.MA.getCode())) {
					//当前用户是材料员
					fcrId = bgrId;
				} else if(qxList.contains(Role.MK.getCode())) {
					//当前用户是保管员
					jsrId = bgrId;
				} else {
					log.warn("当前用户不具备操作权限!");
					return null;
				}
				break;
			case LZ : //----流转----
				if(qxList.contains(Role.MK.getCode())) {
					//当前用户是保管员
					fcrId = bgrId;
				} else {
					log.warn("当前用户不具备操作权限!");
					return null;
				}
				break; 
			case HS : break; //TODO
			default : 
				log.warn("未知的操作类型 : " + flag);
			}
		} else {
			log.warn("用户未登录!");
			return null;
		}
		
		String operateId = UUID.randomUUID().toString();
		for(Entry<String,Object> entry : zcInfoMap.entrySet()) {
			Zichan zc = zichanRep.getOne(entry.getKey());
			BigDecimal currentNum = zc.getShul(); //现有的资产数量
			BigDecimal lzNum = null; //需要进行流转的资产数量
			if(entry.getValue() instanceof BigDecimal) {
				//entry中的value如果是整数就是Integer, 如果是小数就是BigDecimal
				lzNum = (BigDecimal)entry.getValue();
			} else if(entry.getValue() instanceof Integer) {
				lzNum = new BigDecimal((Integer)entry.getValue());
			} else {
				log.warn("未知的数据类型:"+entry.getValue().getClass().getName());
			}
			String zcUuid = null;
			if(lzNum.compareTo(currentNum) < 0) { 
				//需要流转的资产数量小于现有数量
				//则需要进行数据的拆分
				zc.setShul(currentNum.subtract(lzNum)); //减法
				zichanRep.save(zc);
				/*
				 * --拷贝该对象--
				 * 持久化的对象是一个代理实例
				 * 直接操作主键之后的保存更新会报错
				 */
				Zichan zcCopy = new Zichan(zc); 
				zcCopy.setUuid(null);
				zcCopy.setShul(lzNum);
				zcUuid = zichanRep.save(zcCopy).getUuid();
			} else {
				zcUuid = zc.getUuid();
			}
			Lzxx lzxx = new Lzxx();
			lzxx.setOperateID(operateId);//操作ID
			lzxx.setBiaozhi(flag.getFlag().toString());//流转类型标识(1出库 2流转 3回收)
			lzxx.setFkZichanUuid(zcUuid);//资产表数据的uuid
			lzxx.setFkZichanZcID(zc.getZcid());//资产编码
			lzxx.setLzsl(lzNum);//流转数量
			lzxx.setLzsj(new Date());//流转时间
			lzxx.setFkBgrFcrID(fcrId); //发出人
			lzxx.setFkBgrJsrID(jsrId); //接受人
			lzxx.setStatus(0); //状态 : 0未完成
			lzxxRep.save(lzxx);
		}
		return operateId;
	}
	/**
	 * 输出二维码图片
	 * @param operateId 操作ID
	 * @param output 字节输出流
	 */
	public void outputQrcode(String operateId, OutputStream output) {
		List<Lzxx> result = lzxxRep.findByOperateID(operateId);
		if(result == null || result.isEmpty()) { //按照操作ID未查到数据
			return;
		}
		Map<String, Object> qrcodeContent = new HashMap<String, Object>();
		qrcodeContent.put("operateType", result.get(0).getBiaozhi()); //操作类型 1.出库 2.流转 3.回收
		qrcodeContent.put("operateId", operateId);//多个流转ID
		try {
			qrcodeUtils.createQrcode(JSON.toJSONString(qrcodeContent), output);
		} catch (WriterException | IOException e) {
			log.error("生成二维码失败", e);
		}
	}
	
	
	public Lzxx findByOperateIdAndZcUuid(String operateId, String zcUuid) {
		return lzxxRep.findByOperateIDAndFkZichanUuid(operateId, zcUuid);
	}
	/**
	 * 更新流转信息当中的照片URL
	 * @param lzxx
	 * @param photoPath 照片保存的**相对**路径
	 */
	public void updatePhotoId(Lzxx lzxx, String photoPath, ServletContext context) {
		String oldPath = lzxx.getFkZhaopianPzzpURL();
		if(StringHelper.isNotEmpty(oldPath)) {
			//如果原文件存在, 则删除
			File oldFile = new File(fileUtil.getAbsolutePath(oldPath, context));
			if(oldFile.exists()) {
				oldFile.delete();
			}
		}
		lzxx.setFkZhaopianPzzpURL(photoPath);
		lzxxRep.save(lzxx);
	}
	/**
	 * 验证该操作ID对应的流转信息是否已经全部完成
	 * @param operateId 操作ID
	 * @return 全部完成返回true, 否则返回false
	 */
	public boolean checkFinished(String operateId) {
		return lzxxRep.checkFinished(operateId) == 1;
	}
	/**
	 * 将该操作ID对应的流转数据状态改为1 (已完成)
	 * @param operateId 操作ID
	 * @param flag 操作的标识(用于区分 出库 流转 回收)
	 * @param bgrId 执行操作的用户ID
	 * @return update影响的行数(大于0代表操作成功)
	 */
	public int finished(String operateId, LzType flag, String bgrId) {
		int result = 0;
		boolean updateFlag = true;
		if(bgrId != null) {
			List<String> qxList = bgrResp.queryQxByBgr(bgrId);//当前用户具备的权限
			switch(flag) {
			case CK : //----出库----
				if(qxList.contains(Role.MA.getCode())) {
					//当前用户是材料员
					result = lzxxRep.fcrFinished(operateId, bgrId);
				} else if(qxList.contains(Role.MK.getCode())) {
					//当前用户是保管员
					result = lzxxRep.jsrFinished(operateId, bgrId);
				} else {
					updateFlag = false;
					log.warn("当前用户不具备操作权限!");
				}
				break;
			case LZ : //----流转----
				if(qxList.contains(Role.MK.getCode())) {
					result = lzxxRep.jsrFinished(operateId, bgrId);
				} else {
					updateFlag = false;
					log.warn("当前用户不具备操作权限!");
				}
				break; 
			case HS : //TODO 回收
				updateFlag = false;
				break;
			default : 
				updateFlag = false;
				log.warn("未知的操作类型 : " + flag);
			}
			if(updateFlag) {
				zichanRep.updateBgrId(operateId);
				List<Zichan> zichans = zichanRep.findByFkBgrID(lzxxRep.getJsrIdByOperateid(operateId));
				zichanService.mergeZc(zichans);//对zcid相同的数据进行合并(数量相加)
			}
		} else {
			log.warn("用户未登录!");
		}
		return result;
	}
	/**
	 * 统计一次流转中资产的类型数量(涉及几种类型的物资)
	 * @param operateId 操作ID
	 * @return
	 */
	public int typeCount(String operateId) {
		return lzxxRep.typeCount(operateId);
	}
	
	/**
	 * 统计一次流转当中添加的照片数量
	 * @param operateId 操作ID
	 * @return 照片数量
	 */
	public int countPhotoNum(String operateId) {
		return lzxxRep.countPhotoNum(operateId);
	}
	
	public List<Lzxx> findByOperateID(String operateID) {
		return lzxxRep.findByOperateID(operateID);
	}
	
	public List<Object[]> getLzxxDetail(String operateId) {
		return lzxxRep.getLzxxDetail(operateId);
	}
	
	/**
	 * 检验一次流转操作是否已经**全部**上传照片
	 * @param operateId 操作ID
	 * @return 已全部上传返回1, 否则返回0
	 */
	public int checkUpload(String operateId) {
		return lzxxRep.checkUpload(operateId);
	}
	
	/**
	 * 查询用户的接收和转出记录
	 * @param bgrId 用户ID
	 * @return
	 */
	public List<Map<String, Object>> findRecordByBgr(String bgrId) {
		List<Object[]> result = lzxxRep.findRecordByBgr(bgrId);
		List<Map<String, Object>> finalResult = new ArrayList<Map<String, Object>>();
		for(Object[] record : result) {
			Map<String,Object> recordMap = new HashMap<String,Object>();
			finalResult.add(recordMap);
			recordMap.put("zcName", record[0]);
			recordMap.put("shul", record[1]);
			recordMap.put("jjf", record[2]);
			recordMap.put("lzsj", record[3]);
		}
		return finalResult;
	}
	
	
}
