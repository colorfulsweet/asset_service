package com.web.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 流转信息表
 * 
 * @author Sookie
 *
 */

@Entity
@Table(name="LZXX")
public class Lzxx implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="idGen",strategy="uuid")
	@GeneratedValue(generator="idGen")
	private String UUID;// varchar(32) not null comment '记录编号',
	private String biaozhi;// varchar(30) comment '发放回收流转标志',
	private String fkZichanZcID;// varchar(32) comment '资产编码',
	private String fkZhaopianPzzpURL;// varchar(32) comment '凭证照片附件',
	private String fkZhaopianSbzpid;// varchar(32) comment '设备照片',
	private Date lzsj;// datetime comment '流转时间',
	private String fkBgrFcrID;// varchar(32) comment '发出人编号',
	private String fkBgrJsrID;// varchar(32) comment '接受人编号',
	private String lzbz;// varchar(200) comment '流转备注',

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getBiaozhi() {
		return biaozhi;
	}

	public void setBiaozhi(String biaozhi) {
		this.biaozhi = biaozhi;
	}

	public String getFkZichanZcID() {
		return fkZichanZcID;
	}

	public void setFkZichanZcID(String fkZichanZcID) {
		this.fkZichanZcID = fkZichanZcID;
	}

	public String getFkZhaopianPzzpURL() {
		return fkZhaopianPzzpURL;
	}

	public void setFkZhaopianPzzpURL(String fkZhaopianPzzpURL) {
		this.fkZhaopianPzzpURL = fkZhaopianPzzpURL;
	}

	public String getFkZhaopianSbzpid() {
		return fkZhaopianSbzpid;
	}

	public void setFkZhaopianSbzpid(String fkZhaopianSbzpid) {
		this.fkZhaopianSbzpid = fkZhaopianSbzpid;
	}

	public Date getLzsj() {
		return lzsj;
	}

	public void setLzsj(Date lzsj) {
		this.lzsj = lzsj;
	}

	public String getFkBgrFcrID() {
		return fkBgrFcrID;
	}

	public void setFkBgrFcrID(String fkBgrFcrID) {
		this.fkBgrFcrID = fkBgrFcrID;
	}

	public String getFkBgrJsrID() {
		return fkBgrJsrID;
	}

	public void setFkBgrJsrID(String fkBgrJsrID) {
		this.fkBgrJsrID = fkBgrJsrID;
	}

	public String getLzbz() {
		return lzbz;
	}

	public void setLzbz(String lzbz) {
		this.lzbz = lzbz;
	}
}
