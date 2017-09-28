package com.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.entity.Zichan;

public interface ZichanRepository extends JpaRepository<Zichan, String> {
	
	@Query("from Zichan a where a.uuid in(select b.fkZichanZcID from Lzxx b where b.operateID =:operateId)")
	public List<Zichan> getByOperateId(@Param("operateId")String operateId);
}
