package com.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.entity.Bgr;

public interface BgrRespository extends JpaRepository<Bgr, String> {
	
	public Bgr findByUserAndPassword(String user, String password);
	
	@Query(value="select js.qxdj from ryjs js where js.uuid in " + 
			"(select gx.fk_ryjs_id from ryjsgx gx where gx.fk_bgr_id=:bgrId)", nativeQuery=true) //原生SQL查询
	public List<String> queryQxByBgr(@Param("bgrId")String bgrId);
}
