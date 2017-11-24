package com.web.service;

import java.util.List;

import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.dao.BgrRepository;
import com.web.entity.Bgr;

@Service
public class BgrService {
	@Autowired
	private BgrRepository bgrRep;
	
	public Bgr checkUser(String user, String password) {
		return bgrRep.findByUserAndPassword(user, password);
	}
	
	public Bgr findByUuidAndPassword(String uuid, String password) {
		return bgrRep.findByUuidAndPassword(uuid, password);
	}
	
	public Bgr save(Bgr bgr) {
		if(StringHelper.isEmpty(bgr.getUuid())) {
			return bgrRep.save(bgr);
		} else {
			Bgr currentBgr = bgrRep.getOne(bgr.getUuid());
//			ReflectUtils.reflectCopyField(currentBgr, bgr);
			currentBgr.setUser(bgr.getUser()); //用户名
			currentBgr.setRealname(bgr.getRealname()); //姓名
			currentBgr.setDepartment(bgr.getDepartment()); //部门
			currentBgr.setTitle(bgr.getTitle()); //职务
			currentBgr.setLxdh(bgr.getLxdh()); //联系电话
			currentBgr.setDzyx(bgr.getDzyx()); //电子邮箱
			return bgrRep.save(currentBgr);
		}
	}
	
	public List<String> queryQxByBgr(String bgrId) {
		return bgrRep.queryQxByBgr(bgrId);
	}
	
	public List<Bgr> findByLxdh(String lxdh) {
		return bgrRep.findByLxdh(lxdh);
	}
}
