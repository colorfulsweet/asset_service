package com.web.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import com.utils.PageUtil;
import com.web.entity.AppVersion;

@Service
public class AppVersionService {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public List<AppVersion> find(PageUtil page) {
		StringBuilder where = new StringBuilder(" where 1=1 ");
		
		TypedQuery<AppVersion> query = entityManager.createQuery("from AppVersion bean"+where.toString(), AppVersion.class);
		if(page != null) {
			query.setFirstResult(page.getRowStart());
			query.setMaxResults(page.getPageSize());
			Query countQuery = entityManager.createQuery("select count(*) from AppVersion bean"+where.toString());
			
			page.setRowCount(countQuery.getSingleResult().toString());
		}
		return query.getResultList();
	}
}
