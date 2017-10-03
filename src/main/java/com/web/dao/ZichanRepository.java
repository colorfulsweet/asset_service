package com.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.web.entity.Zichan;

public interface ZichanRepository extends JpaRepository<Zichan, String> {
	
	@Query("from Zichan a where a.uuid in(select b.fkZichanZcID from Lzxx b where b.operateID =:operateId)")
	public List<Zichan> getByOperateId(@Param("operateId")String operateId);
	
	@Modifying
	@Transactional
	@Query("update Zichan bean set bean.bgrId=:bgrId where bean.uuid in "
			+ "(select lz.fkZichanZcID from Lzxx lz where lz.operateID=:operateId)")
	public int updateBgrId(@Param("operateId")String operateId, @Param("bgrId")String bgrId);
}
