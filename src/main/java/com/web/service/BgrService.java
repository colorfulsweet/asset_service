package com.web.service;

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
}
