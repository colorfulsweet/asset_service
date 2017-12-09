package com.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.entity.AppVersion;

public interface AppVersionRespository extends JpaRepository<AppVersion, String> {

}
