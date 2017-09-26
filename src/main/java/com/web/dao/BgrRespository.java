package com.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.entity.Bgr;

public interface BgrRespository extends JpaRepository<Bgr, String> {
	
	public Bgr findByUserAndPassword(String user, String password);
}
