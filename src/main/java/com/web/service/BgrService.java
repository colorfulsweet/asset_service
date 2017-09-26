package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.dao.BgrRespository;
import com.web.entity.Bgr;

@Service
public class BgrService {
	@Autowired
	private BgrRespository bgrResp;
	
	public Bgr checkUser(String user, String password) {
		return bgrResp.findByUserAndPassword(user, password);
	}
}
