package com.web.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 流转信息表
 * 
 * @author 夏夜梦星辰
 *
 */

@Entity
@Table(name="lzxx")
public class Lzxx implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="idGen",strategy="uuid")
	@GeneratedValue(generator="idGen")
	private String uuid;// varchar(32) not null comment '记录编号',
	@Column(name="operate_id")
	private String operateID;//varchar(50) 操作ID - 对于一次操作(选中多个资产实施流转), 该ID相同
	@Column(name="biaozhi")
	private String biaozhi;// varchar(30) comment '发放回收流转标志',
	@Column(name="fk_zichan_zcid")
	private String fkZichanZcID;// varchar(32) comment '资产编码',
	@Column(name="fk_zichan_uuid")
	private String fkZichanUuid;// varchar(32) comment '资产表uuid',
	@Column(name="fk_zhaopian_pzzpurl")
	private String fkZhaopianPzzpURL;// varchar(32) comment '凭证照片附件',
	@Column(name="fk_zhaopian_sbzpid")
	private String fkZhaopianSbzpid;// varchar(32) comment '设备照片',
	@Column(name="lzsl")
	private BigDecimal lzsl;//流转数量
	@Column(name="lzsj")
	private Date lzsj;// datetime comment '流转时间',
	@Column(name="fk_bgr_fcrid")
	private String fkBgrFcrID;// varchar(32) comment '发出人编号',
	@Column(name="fk_bgr_jsrid")
	private String fkBgrJsrID;// varchar(32) comment '接受人编号',
	@Column(name="lzbz")
	private String lzbz;// varchar(200) comment '流转备注',
	@Column(name="status")
	private Integer status;//流转执行状态 0.未完成  1.已完成

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getOperateID() {
		return operateID;
	}

	public void setOperateID(String operateID) {
		this.operateID = operateID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFkZichanUuid() {
		return fkZichanUuid;
	}

	public void setFkZichanUuid(String fkZichanUuid) {
		this.fkZichanUuid = fkZichanUuid;
	}

	public BigDecimal getLzsl() {
		return lzsl;
	}

	public void setLzsl(BigDecimal lzsl) {
		this.lzsl = lzsl;
	}
}
