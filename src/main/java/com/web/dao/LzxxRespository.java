package com.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.entity.Lzxx;

public interface LzxxRespository extends JpaRepository<Lzxx, String> {
	public List<Lzxx> findByOperateID(String operateID);
	
	@Query("from Lzxx a " + 
			"where " + 
			"a.operateID = (select b.operateID from Lzxx b where b.uuid=:lzId) " + 
			"and a.fkZichanZcID=:zcId")
	public Lzxx lzxxFilter(@Param("lzId")String lzId, @Param("zcId")String zcId);
}
