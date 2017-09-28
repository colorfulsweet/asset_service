package com.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.web.entity.Lzxx;

public interface LzxxRespository extends JpaRepository<Lzxx, String> {
	public List<Lzxx> findByOperateID(String operateID);
	
	@Query("from Lzxx a " + 
			"where " + 
			"a.operateID = (select b.operateID from Lzxx b where b.uuid=:lzId) " + 
			"and a.fkZichanZcID=:zcId")
	public Lzxx lzxxFilter(@Param("lzId")String lzId, @Param("zcId")String zcId);
	
	/**
	 * 返回1代表全部完成, 返回0代表没有全部完成(只有这两种情况)
	 * @param operateId 操作ID
	 * @return 0或者1
	 */
	@Query(value="select " +  //状态为0的没有数据  状态为1的有数据, 则全部完成
			" (select count(*)=0 from lzxx where status=0 and operate_id=:operateId) " + 
			" and " + 
			" (select count(*)!=0 from lzxx where status=1 and operate_id=:operateId) ", nativeQuery=true)
	public int checkFinished(@Param("operateId")String operateId);
	
	/**
	 * 自定义update语句
	 * @param operateId 操作ID
	 * @return 影响的行数
	 */
	@Modifying
	@Transactional
	@Query("update Lzxx bean set bean.status=1 where bean.operateId=:operateId")
	public int finished(@Param("operateId")String operateId);
}
