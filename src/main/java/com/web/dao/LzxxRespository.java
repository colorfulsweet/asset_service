package com.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.entity.Lzxx;

public interface LzxxRespository extends JpaRepository<Lzxx, String> {
	public List<Lzxx> findByOperateID(String operateID);
}
