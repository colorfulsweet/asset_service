package com.web.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="app_version")
public class AppVersion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="idGen",strategy="uuid")
	@GeneratedValue(generator="idGen")
	private String uuid; //VARCHAR(50) NOT NULL,
	
	@Column(name="file_name")
	private String fileName; //VARCHAR(200) NULL COMMENT '上传的文件名',
	
	@Column(name="file_path")
	private String filePath; //VARCHAR(200) NULL COMMENT '升级包所在的路径',
	
	@Column(name="ext")
	private String ext; //文件的扩展名
	
	@Column(name="version_l")
	private Integer versionL; //大版本号
	
	@Column(name="version_m")
	private Integer versionM; //中版本号
	
	@Column(name="version_s")
	private Integer versionS; //小版本号
	
	@Column(name="create_time")
	private Date createTime; //DATETIME NULL COMMENT '上传的时间',
	
	@Column(name="os")
	private String os; //对应平台(iOS或者Android)
	
	@Column(name="increment")
	private Boolean increment; //BIT NULL COMMENT '是否是增量包',

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getIncrement() {
		return increment;
	}

	public void setIncrement(Boolean increment) {
		this.increment = increment;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public Integer getVersionL() {
		return versionL;
	}

	public void setVersionL(Integer versionL) {
		this.versionL = versionL;
	}

	public Integer getVersionM() {
		return versionM;
	}

	public void setVersionM(Integer versionM) {
		this.versionM = versionM;
	}

	public Integer getVersionS() {
		return versionS;
	}

	public void setVersionS(Integer versionS) {
		this.versionS = versionS;
	}
	
	public String getVersion() {
		return (versionL==null? 0 : versionL) + "." +
				(versionM==null? 0 : versionM) + "." +
				(versionS==null? 0 : versionS);
	}
}
