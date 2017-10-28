package com.web.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 照片索引表
 * @author 夏夜梦星辰
 *
 */
@Entity
@Table(name="asset_photo_index")
public class PhotoIndex implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="idGen",strategy="uuid")
	@GeneratedValue(generator="idGen")
	private String id;// varchar(36) NOT NULL COMMENT 'id',
	
	@Column(name="fk_asset_id")
	private String fkAssetId;// varchar(36) DEFAULT NULL COMMENT '资产记录编号id',
	
	@Column(name="photo_path")
	private String photoPath;// varchar(100) DEFAULT NULL COMMENT '照片路径',
	
	@Column(name="photo_name")
	private String photoName;// varchar(45) DEFAULT NULL COMMENT '照片名称',
	
	@Column(name="photo_nature")
	private String photoNature;// varchar(36) DEFAULT NULL COMMENT '照片性质',
	
	@Column(name="photo_time")
	private Date photoTime;// datetime DEFAULT NULL COMMENT '照片拍摄时间',
	
	@Column(name="photo_format")
	private String photoFormat;// varchar(45) DEFAULT NULL COMMENT '照片格式',
	
	@Column(name="photo_size")
	private String photoSize;// varchar(45) DEFAULT NULL COMMENT '照片大小',

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFkAssetId() {
		return fkAssetId;
	}

	public void setFkAssetId(String fkAssetId) {
		this.fkAssetId = fkAssetId;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getPhotoNature() {
		return photoNature;
	}

	public void setPhotoNature(String photoNature) {
		this.photoNature = photoNature;
	}

	public Date getPhotoTime() {
		return photoTime;
	}

	public void setPhotoTime(Date photoTime) {
		this.photoTime = photoTime;
	}

	public String getPhotoFormat() {
		return photoFormat;
	}

	public void setPhotoFormat(String photoFormat) {
		this.photoFormat = photoFormat;
	}

	public String getPhotoSize() {
		return photoSize;
	}

	public void setPhotoSize(String photoSize) {
		this.photoSize = photoSize;
	}
}
