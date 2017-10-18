package com.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.web.entity.Lzxx;

public interface LzxxRepository extends JpaRepository<Lzxx, String> {
	public List<Lzxx> findByOperateID(String operateID);
	/**
	 * 根据操作ID和资产ID查询流转信息()
	 * @param operateID
	 * @param zcId
	 * @return
	 */
	public Lzxx findByOperateIDAndFkZichanZcID(String operateID, String fkZichanZcID);
	
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
	 * 自定义update语句 - 接收人操作确认
	 * @param operateId 操作ID
	 * @return 影响的行数
	 */
	@Modifying
	@Transactional
	@Query("update Lzxx bean set bean.status=1,bean.fkBgrJsrID=:bgrId where bean.operateID=:operateId")
	public int jsrFinished(@Param("operateId")String operateId, @Param("bgrId")String bgrId);
	
	/**
	 * 自定义update语句 - 发出人操作确认
	 * @param operateId 操作ID
	 * @return 影响的行数
	 */
	@Modifying
	@Transactional
	@Query("update Lzxx bean set bean.status=1,bean.fkBgrFcrID=:bgrId where bean.operateID=:operateId")
	public int fcrFinished(@Param("operateId")String operateId, @Param("bgrId")String bgrId);
	
	/**
	 * 统计一次流转中资产的类型数量(涉及几种类型的物资)
	 * @param operateId 操作ID
	 * @return
	 */
	@Query(value="select count(*) from " + 
			"(select zc.lbie from zichan zc where zc.uuid in " + 
			"(select fk_zichan_zcid from lzxx lz where lz.operate_ID=:operateId)" + 
			"group by zc.lbie) t", nativeQuery=true)
	public int typeCount(@Param("operateId")String operateId);
	
	/**
	 * 统计一次流转的详情信息(接收人的名称, 流转时间)
	 * @param operateId 操作ID
	 * @return
	 */
	@Query(value="select b.user,lz.lzsj from lzxx lz " + 
			" join bgr b on b.uuid=lz.fk_bgr_jsrID " + 
			" where lz.operate_ID=:operateId group by lz.fk_bgr_jsrID,lz.lzsj ", nativeQuery=true)
	public List<Object[]> getLzxxDetail(@Param("operateId")String operateId);
	
	/**
	 * 统计一次流转当中添加的照片数量
	 * @param operateId 操作ID
	 * @return
	 */
	@Query(value="select count(*) from lzxx lz " + 
			"where lz.operate_id=:operateId " + 
			"and " + 
			"lz.fk_zhaopian_pzzpURL is not null and lz.fk_zhaopian_pzzpURL!=''", nativeQuery=true)
	public int countPhotoNum(@Param("operateId") String operateId);
	
	/**
	 * 检验一次流转操作是否已经**全部**上传照片
	 * @param operateId 操作ID
	 * @return 已全部上传返回1, 否则返回0
	 */
	@Query(value="select count(*)=count(case when fk_zhaopian_pzzpurl is not null and fk_zhaopian_pzzpurl != '' then 1 end) " + 
			"from lzxx where operate_id =:operateId", nativeQuery=true)
	public int checkUpload(@Param("operateId")String operateId);
}
