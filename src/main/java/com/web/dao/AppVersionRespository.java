package com.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

import com.web.entity.AppVersion;

public interface AppVersionRespository extends JpaRepository<AppVersion, String> {
	//TODO 不知道哪里的错误
//	@Query("from AppVersion bean where bean.os=:osName limit 0,1")
//	public AppVersion getLatestVersion(@Param("osName")String os);
}
