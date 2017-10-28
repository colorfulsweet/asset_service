package com.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.entity.PhotoIndex;

public interface PhotoRepository extends JpaRepository<PhotoIndex, String> {
	
	/**
	 * 根据照片路径查找照片ID
	 * @param photoPath 照片路径
	 * @return
	 */
	public PhotoIndex findByPhotoPath(String photoPath);
}
