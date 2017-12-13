package com.web.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.utils.HqlUtils;
import com.utils.PageUtil;
import com.web.dao.BgrRepository;
import com.web.dao.ZichanRepository;
import com.web.entity.Bgr;
import com.web.entity.Zichan;

@Service
public class ZichanService {
	private static Logger log = Logger.getLogger(ZichanService.class);
	
	@Autowired
	private ZichanRepository zichanRep;
	
	@Autowired
	private BgrRepository bgrRep;
	/**
	 * 数据库名称
	 */
	@Value("${jdbc.schema-name}")
	private String schemaName;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	/**
	 * 查询(根据需要的查询字段而定)
	 * @param zcID
	 * @param mingch
	 * @param lbie
	 * @param uuids
	 * @return
	 */
	public List<Zichan> find(Map<String, String> params, PageUtil page) {
		String[] uuidArr = null;
		StringBuilder where = new StringBuilder(" where 1=1 ");
		String uuids = params.get("uuids");
		String zcid = params.get("zcid");
		String mingch = params.get("mingch");
		String lbie = params.get("lbie");
		
		String bgrId = params.get("bgrId"); //根据用户ID查询该用户名下的资产
		String role = params.get("role");  //只查询属于MA或者属于MK的资产
		
		if(StringHelper.isNotEmpty(uuids)) {
			uuidArr = uuids.split(",");
			where.append(" and bean.uuid in ("+HqlUtils.createPlaceholder(uuidArr.length)+") ");
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		if(StringHelper.isNotEmpty(zcid)) {
			where.append(" and bean.zcid like :zcid ");
			paramMap.put("zcid", zcid);
		}
		if(StringHelper.isNotEmpty(mingch)) {
			where.append(" and bean.mingch like :mingch ");
			paramMap.put("mingch", mingch);
		}
		if(StringHelper.isNotEmpty(lbie)) {
			where.append(" and bean.lbie=:lbie ");
		}
		if(StringHelper.isNotEmpty(bgrId)) {
			where.append(" and bean.bgr.uuid=:bgrId ");
		}
		if(StringHelper.isNotEmpty(role)) {
			where.append(" and bean.bgr.uuid in(select gx.fkBgrId from Ryjs js,Ryjsgx gx "
					+ " where gx.fkRyjsId=js.uuid and js.qxdj=:role) ");
		}
		TypedQuery<Zichan> query = entityManager.createQuery("from Zichan bean"+where.toString(), Zichan.class);
		if(StringHelper.isNotEmpty(uuids)) {
			HqlUtils.setParamList(query, uuidArr, 1);
		}
		
		if(StringHelper.isNotEmpty(zcid)) {
			query.setParameter("zcid", "%"+zcid+"%");
		}
		if(StringHelper.isNotEmpty(mingch)) {
			query.setParameter("mingch", "%"+mingch+"%");
		}
		if(StringHelper.isNotEmpty(lbie)) {
			query.setParameter("lbie", lbie);
		}
		if(StringHelper.isNotEmpty(bgrId)) {
			query.setParameter("bgrId", bgrId);
		}
		if(StringHelper.isNotEmpty(role)) {
			query.setParameter("role", role);
		}
		if(page != null) {
			query.setFirstResult(page.getRowStart());
			query.setMaxResults(page.getPageSize());
			Query countQuery = entityManager.createQuery("select count(*) from Zichan bean"+where.toString());
			
			if(StringHelper.isNotEmpty(uuids)) {
				HqlUtils.setParamList(query, uuidArr, 1);
			}
			if(StringHelper.isNotEmpty(zcid)) {
				countQuery.setParameter("zcID", "%"+zcid+"%");
			}
			if(StringHelper.isNotEmpty(mingch)) {
				countQuery.setParameter("mingch", "%"+mingch+"%");
			}
			if(StringHelper.isNotEmpty(lbie)) {
				countQuery.setParameter("lbie", lbie);
			}
			if(StringHelper.isNotEmpty(bgrId)) {
				countQuery.setParameter("bgrId", bgrId);
			}
			if(StringHelper.isNotEmpty(role)) {
				query.setParameter("role", role);
			}
			page.setRowCount(countQuery.getSingleResult().toString());
		}
		return query.getResultList();
	}
	/**
	 * 根据流转ID查询资产数据
	 * @param lzIds
	 * @return
	 */
	public List<Zichan> getByOperateId(String operateId) {
		return zichanRep.getByOperateId(operateId);
	}
	
	public List<Zichan> findByBgrId(String bgrId) {
		return zichanRep.findByFkBgrID(bgrId);
	}
	/**
	 * 新增或修改
	 * @param zichan
	 * @return
	 */
	public Zichan save(Zichan zichan) {
		if(StringHelper.isEmpty(zichan.getPdzt())) {
			zichan.setPdzt("新入库");
		}
		if(StringHelper.isEmpty(zichan.getStatus())) {
			zichan.setStatus("正常");
		}
		return zichanRep.save(zichan);
	}
	
	public Zichan findOne(String uuid) {
		return zichanRep.findOne(uuid);
	}
	
	/**
	 * 删除
	 * @param uuid 数据主键
	 */
	public void delete(String uuid) {
		zichanRep.delete(uuid);
	}
	
	public List<String> findLastPhoto(String zcid) {
		return zichanRep.findLastPhoto(zcid);
	}
	
	/**
	 * 解析Excel文件, 导入资产数据
	 * @param input 字节输入流
	 * @return excel数据当中的错误内容, 键为行数, 值为若干错误信息组成的List
	 * @throws IOException
	 */
	public Map<String, Object> importExcel(InputStream input) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(input));//工作簿
		HSSFSheet sheet = wb.getSheetAt(0); //工作表
		int rowNum = sheet.getLastRowNum();
		
		//错误信息集合
		Map<Integer, List<String>> errMap = new HashMap<Integer, List<String>>();
		List<Zichan> zcList = new ArrayList<Zichan>();
		int cntSuccess = 0;
		int cntErr = 0;
		//第一行为表头, 从第二行开始读取
		for(int rowIndex=1 ; rowIndex <= rowNum ; rowIndex++) {
			boolean hasErr = false; //该条数据是否存在错误
			HSSFRow row = sheet.getRow(rowIndex);
			Zichan zc = new Zichan();
			String zcid = getStringCellValue(row.getCell(0), true); //资产编码
			//资产编码校验
			if(StringHelper.isEmpty(zcid)) {
				addErrInfo(errMap, rowIndex, "资产编码为空");
				hasErr = true;
			}
			if(!zichanRep.findByZcid(zcid).isEmpty()) {
				addErrInfo(errMap, rowIndex, "资产编码已存在:" + zcid);
				hasErr = true;
			}
			zc.setZcid(zcid); //写入资产编码
			zc.setFkBgdwid(getStringCellValue(row.getCell(1), true));//保管单位
			
			String bgrName = getStringCellValue(row.getCell(2), true);//保管人姓名
			if(StringHelper.isNotEmpty(bgrName)) {
				List<Bgr> bgrs = bgrRep.findByRealname(bgrName);
				if(!bgrs.isEmpty()) {
					zc.setBgr(bgrs.get(0));//保管人
				} else {
					addErrInfo(errMap, rowIndex, "保管人不存在:"+bgrName);
					hasErr = true;
				}
			}
			zc.setFkXmID(getStringCellValue(row.getCell(3), true)); //项目部
			zc.setMingch(getStringCellValue(row.getCell(4), true)); //名称
			zc.setZcly(getStringCellValue(row.getCell(5), true)); //资产来源
			zc.setGysDcxm(getStringCellValue(row.getCell(6), true));//供应商/调出项目名称
			zc.setBeizhu(getStringCellValue(row.getCell(7), true));//备注
			
			try {
				zc.setShul(new BigDecimal(getStringCellValue(row.getCell(8), false))); //数量
			} catch(Exception e) {
				addErrInfo(errMap, rowIndex, "非法的数量值(必须为数字):"+getStringCellValue(row.getCell(8), false));
				hasErr = true;
			}
			zc.setGgxh(getStringCellValue(row.getCell(9), true)); //规格型号
			zc.setLbie(getStringCellValue(row.getCell(10), true)); //类别
			zc.setPpcj(getStringCellValue(row.getCell(11), true)); //品牌厂家
			zc.setDanwei(getStringCellValue(row.getCell(12), true)); //单位
			try {
				zc.setDanjia(new BigDecimal(getStringCellValue(row.getCell(13), false))); //单价
			} catch(Exception e) {
				addErrInfo(errMap, rowIndex, "非法的单价值(必须为数字):"+getStringCellValue(row.getCell(13), false));
				hasErr = true;
			}
			zc.setZczt(getStringCellValue(row.getCell(14), true)); //资产状态(调入/采购)
			zc.setStatus("正常"); //状态
			zc.setPdzt("新入库"); //盘点状态
			if(hasErr) {
				cntErr ++;
			} else {
				cntSuccess ++;
				zcList.add(zc);
			}
		}
		if(!zcList.isEmpty()) {
			zichanRep.save(zcList); //插入到数据库
		}
		wb.close();
		String countInfo = "成功" + cntSuccess + "个, 失败" + cntErr + "个";
		log.info("Excel数据解析完毕, " + countInfo);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errorInfo", errMap);  //错误信息集合
		result.put("countInfo", countInfo); //统计信息
		return result;
	}
	
	/**
	 * 添加Excel解析过程当中的错误信息
	 * @param errMap 错误信息Map集合
	 * @param index 所在行
	 * @param msg 错误信息内容
	 */
	private void addErrInfo(Map<Integer, List<String>> errMap, int index, String msg) {
		if(errMap.containsKey(index)) {
			errMap.get(index).add(msg);
		} else {
			List<String> msgList = new ArrayList<String>();
			errMap.put(index, msgList);
			msgList.add(msg);
		}
	}
	
	/**
	 * 获取单元格内容的字符串形式数据
	 * @param cell 单元格对象
	 * @param forceStr 是否强制字符串(非强制字符串的单元格如果是数字,默认会带上".0")
	 * @return
	 */
	private String getStringCellValue(HSSFCell cell, boolean forceStr) {
        String strCell = null;
        switch (cell.getCellTypeEnum()) {
        case STRING:
            strCell = cell.getStringCellValue();
            break;
        case NUMERIC:
        	double val = cell.getNumericCellValue();
        	if(forceStr) {
        		strCell = String.valueOf((int)val);
        	} else {
        		strCell = String.valueOf(cell.getNumericCellValue());
        	}
            break;
        case BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        return strCell;
    }
	
	/**
	 * 对zcid相同的资产数据进行合并(这些资产的保管人相同)
	 * @param zichans
	 */
	void mergeZc(List<Zichan> zichans) {
		//step1 : 执行排序, 将zcid相同的数据聚在一起
		zichans.sort((zc1, zc2) -> {
			if(zc1.getZcid() == null) {
				return -1;
			}
			return zc1.getZcid().compareTo(zc2.getZcid());
		});
		Zichan lastItem = null;
		//step2 : 对数据进行遍历,对于zcid相同的数据, 数量求和, 删除重复的, 只留1个 
		for(Zichan zc : zichans) {
			if(lastItem == null) {
				lastItem = zc;
				continue;
			}
			if(zc.getZcid()!=null && zc.getZcid().equals(lastItem.getZcid())) {
				zc.setShul(zc.getShul().add(lastItem.getShul()));
				zichanRep.save(zc);//更新当前数据
				zichanRep.delete(lastItem.getUuid());//删除上一条数据
				/* eg:
				 现有两条数据 
				 uuid:"1001",zcid:"101",shul:2.5
				 uuid:"1002",zcid:"101",shul:1.2
				 处理之后保留uuid为1002的数据, shul修改为3.7, uuid为1001的数据删除
				 */
			}
			lastItem = zc;
		}
	}
}
