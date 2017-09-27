package com.web.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.zxing.WriterException;
import com.utils.LzFlag;
import com.utils.QrcodeUtils;
import com.web.dao.LzxxRespository;
import com.web.entity.Lzxx;

@Service
public class LzxxService {
	private static Logger log = Logger.getLogger(LzxxService.class);
	@Autowired
	private LzxxRespository lzxxResp;
	
	/**
	 * 保存流转信息
	 * @param zcIds 资产ID
	 * @param flag 标识(用于区分 出库 流转 回收)
	 * @return 操作ID
	 */
	public String save(Set<String> zcIds, LzFlag flag) {
		List<String> lzIds = new ArrayList<String>();
		String operateId = UUID.randomUUID().toString();
		for(String zcId : zcIds) {
			Lzxx lzxx = new Lzxx();
			lzxx.setOperateID(operateId);//操作ID
			lzxx.setBiaozhi(flag.getFlag().toString());
			lzxx.setFkZichanZcID(zcId);
			lzxx.setLzsj(new Date());
			
			//TODO 上传保存照片 记录照片ID?
			lzIds.add(lzxxResp.save(lzxx).getUuid());
		}
		return operateId;
	}
	/**
	 * 输出二维码图片
	 * @param operateId 操作ID
	 * @param output 字节输出流
	 */
	public void outputQrcode(String operateId, OutputStream output) {
		List<Lzxx> result = lzxxResp.findByOperateID(operateId);
		if(result == null || result.isEmpty()) { //按照操作ID未查到数据
			return;
		}
		List<String> lzIds = new ArrayList<String>();
		for(Lzxx lzxx : result) {
			lzIds.add(lzxx.getUuid());
		}
		Map<String, Object> qrcodeContent = new HashMap<String, Object>();
		qrcodeContent.put("operateType", result.get(0).getBiaozhi()); //操作类型 1.出库 2.流转 3.回收
		qrcodeContent.put("lzIds", lzIds);//多个流转ID
		try {
			QrcodeUtils.createQrcode(JSON.toJSONString(qrcodeContent), output);
		} catch (WriterException | IOException e) {
			log.error("生成二维码失败", e);
		}
	}
}
