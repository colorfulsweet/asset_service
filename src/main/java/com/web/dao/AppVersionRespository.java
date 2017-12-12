package com.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.entity.AppVersion;

public interface AppVersionRespository extends JpaRepository<AppVersion, String> {

	@Query(value="select * from app_version bean where bean.os=:osName "
			+ "order by version_l desc,version_m desc,version_s desc limit 0,1",nativeQuery=true)
	public AppVersion getLatestVersion(@Param("osName")String os);
}
