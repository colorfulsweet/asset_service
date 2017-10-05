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
	
	/**
	 * 在资产执行 出库/流转 操作完成之后, 变更资产的保管人ID为该次流转的接收人ID
	 * @param operateId 流转的操作ID
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("update Zichan zc set zc.fkBgrID = " + 
			"(select lz1.fkBgrJsrID from Lzxx lz1 where lz1.operateID =:operateId " + 
			"group by lz1.fkBgrJsrID) " + 
			"where zc.uuid in (select lz2.fkZichanZcID from Lzxx lz2 where lz2.operateID =:operateId)")
	public int updateBgrId(@Param("operateId")String operateId);
	
	
	public List<Zichan> findByFkBgrID(String fkBgrID);
}
