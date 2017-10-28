package com.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.web.entity.Pdxx;

public interface PdxxRepository extends JpaRepository<Pdxx, String> {
	
	/**
	 * 重置所有资产的盘点状态
	 * @param pdzt 盘点状态
	 * @return 受影响的行数
	 */
	@Modifying
	@Transactional
	@Query("update Zichan bean set bean.pdzt=:pdzt")
	public int PdztReset(@Param("pdzt")String pdzt);
	
}
