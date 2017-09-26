package com.web.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.dao.ZichanRepository;
import com.web.entity.Zichan;

@Service
public class ZichanService {
	
	@Autowired
	private ZichanRepository zichanRep;
	
	@PersistenceContext
    private EntityManager entityManager;
	/**
	 * 查询(根据需要的查询字段而定)
	 * @param zcID
	 * @param mingch
	 * @param lbie
	 * @return
	 */
	public List<Zichan> find(String zcID, String mingch, String lbie) {
		StringBuilder hql = new StringBuilder("from Zichan bean where 1=1 ");
		if(StringHelper.isNotEmpty(zcID)) {
			hql.append(" and bean.zcID like :zcID ");
		}
		if(StringHelper.isNotEmpty(mingch)) {
			hql.append(" and bean.mingch like :mingch ");
		}
		if(StringHelper.isNotEmpty(lbie)) {
			hql.append(" and bean.lbie=:lbie ");
		}
		TypedQuery<Zichan> query = entityManager.createQuery(hql.toString(), Zichan.class);
		if(StringHelper.isNotEmpty(zcID)) {
			query.setParameter("zcID", "%"+zcID+"%");
		}
		if(StringHelper.isNotEmpty(mingch)) {
			query.setParameter("mingch", "%"+mingch+"%");
		}
		if(StringHelper.isNotEmpty(lbie)) {
			query.setParameter("lbie", lbie);
		}
		return query.getResultList();
	}
	/**
	 * 新增或修改
	 * @param zichan
	 * @return
	 */
	public Zichan save(Zichan zichan) {
		return zichanRep.save(zichan);
	}
	
	/**
	 * 删除
	 * @param uuid 数据主键
	 */
	public void delete(String uuid) {
		zichanRep.delete(uuid);
	}
}