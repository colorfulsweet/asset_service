package com.web.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.utils.HqlUtils;
import com.utils.PageUtil;
import com.web.dao.ZichanRepository;
import com.web.entity.Zichan;

@Service
public class ZichanService {
	
	@Autowired
	private ZichanRepository zichanRep;
	
	/**
	 * 数据库名称
	 */
	@Value("${jdbc.schema-name}")
	private String schemaName;
	
	@PersistenceContext
    private EntityManager entityManager;
	
//	public Page<Zichan> findByPage(PageUtil page) {
//		Pageable pageRequest = new PageRequest(page.getPageNow()-1, page.getPageSize());
//		return zichanRep.findAll(pageRequest);
//	}
	
	/**
	 * 查询(根据需要的查询字段而定)
	 * @param zcID
	 * @param mingch
	 * @param lbie
	 * @param uuids
	 * @return
	 */
	public List<Zichan> find(String zcID, String mingch, String lbie, String uuids, PageUtil page) {
		String[] uuidArr = null;
		StringBuilder where = new StringBuilder(" where 1=1 ");
		if(StringHelper.isNotEmpty(uuids)) {
			uuidArr = uuids.split(",");
			where.append(" and bean.uuid in ("+HqlUtils.createPlaceholder(uuidArr.length)+") ");
		}
		if(StringHelper.isNotEmpty(zcID)) {
			where.append(" and bean.zcid like :zcID ");
		}
		if(StringHelper.isNotEmpty(mingch)) {
			where.append(" and bean.mingch like :mingch ");
		}
		if(StringHelper.isNotEmpty(lbie)) {
			where.append(" and bean.lbie=:lbie ");
		}
		
		TypedQuery<Zichan> query = entityManager.createQuery("from Zichan bean"+where.toString(), Zichan.class);
		if(StringHelper.isNotEmpty(uuids)) {
			HqlUtils.setParamList(query, uuidArr, 1);
		}
		if(StringHelper.isNotEmpty(zcID)) {
			query.setParameter("zcID", "%"+zcID+"%");
		}
		if(StringHelper.isNotEmpty(mingch)) {
			query.setParameter("mingch", "%"+mingch+"%");
		}
		if(StringHelper.isNotEmpty(lbie)) {
			query.setParameter("lbie", lbie);
		}
		if(page != null) {
			query.setFirstResult(page.getRowStart());
			query.setMaxResults(page.getPageSize());
			Query countQuery = entityManager.createQuery("select count(*) from Zichan bean"+where.toString());
			
			if(StringHelper.isNotEmpty(uuids)) {
				HqlUtils.setParamList(query, uuidArr, 1);
			}
			if(StringHelper.isNotEmpty(zcID)) {
				countQuery.setParameter("zcID", "%"+zcID+"%");
			}
			if(StringHelper.isNotEmpty(mingch)) {
				countQuery.setParameter("mingch", "%"+mingch+"%");
			}
			if(StringHelper.isNotEmpty(lbie)) {
				countQuery.setParameter("lbie", lbie);
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
