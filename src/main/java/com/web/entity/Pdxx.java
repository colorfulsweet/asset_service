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
 * 盘点信息表
 * 
 * @author 夏夜梦星辰
 *
 */
@Entity
@Table(name = "pdxx")
public class Pdxx implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="idGen",strategy="uuid")
	@GeneratedValue(generator="idGen")
	private String uuid;// varchar(32) NOT NULL COMMENT '盘点记录编码',
	
	@Column(name="fk_zichan_uuid")
	private String fkZichanUuid;//资产UUID
	
	@Column(name="fk_zichan_zcid")
	private String fkZichanZcid;// varchar(32) DEFAULT NULL COMMENT '资产编码',
	
	@Column(name="pdsj")
	private Date pdsj;// datetime DEFAULT NULL COMMENT '盘点时间',
	
	@Column(name="fk_bgr_bgrid")
	private String fkBgrBgrid;// varchar(32) DEFAULT NULL COMMENT '保管人',
	
	@Column(name="shul")
	private BigDecimal shul;// decimal(30,6) DEFAULT NULL COMMENT '数量',
	
	@Column(name="unit")
	private String unit;// varchar(30) DEFAULT NULL COMMENT '单位',
	
	@Column(name="fk_zhaopian_id")
	private String fkZhaopianId;// varchar(32) DEFAULT NULL COMMENT '照片',
	
	@Column(name="pdbz")
	private String pdbz;// varchar(200) DEFAULT NULL COMMENT '盘点备注',
	
	@Column(name="pdpsjl")
	private String pdpsjl;// varchar(30) DEFAULT NULL COMMENT '盘点审批结论',
	
	@Column(name="pdps")
	private String pdps;// varchar(100) DEFAULT NULL COMMENT '盘点批示',
	
	@Column(name="fk_bgr_sprid")
	private String fkBgrSprid;// varchar(32) DEFAULT NULL COMMENT '审批人',

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFkZichanZcid() {
		return fkZichanZcid;
	}

	public void setFkZichanZcid(String fkZichanZcid) {
		this.fkZichanZcid = fkZichanZcid;
	}

	public Date getPdsj() {
		return pdsj;
	}

	public void setPdsj(Date pdsj) {
		this.pdsj = pdsj;
	}

	public String getFkBgrBgrid() {
		return fkBgrBgrid;
	}

	public void setFkBgrBgrid(String fkBgrBgrid) {
		this.fkBgrBgrid = fkBgrBgrid;
	}

	public BigDecimal getShul() {
		return shul;
	}

	public void setShul(BigDecimal shul) {
		this.shul = shul;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getFkZhaopianId() {
		return fkZhaopianId;
	}

	public void setFkZhaopianId(String fkZhaopianId) {
		this.fkZhaopianId = fkZhaopianId;
	}

	public String getPdbz() {
		return pdbz;
	}

	public void setPdbz(String pdbz) {
		this.pdbz = pdbz;
	}

	public String getPdpsjl() {
		return pdpsjl;
	}

	public void setPdpsjl(String pdpsjl) {
		this.pdpsjl = pdpsjl;
	}

	public String getPdps() {
		return pdps;
	}

	public void setPdps(String pdps) {
		this.pdps = pdps;
	}

	public String getFkBgrSprid() {
		return fkBgrSprid;
	}

	public void setFkBgrSprid(String fkBgrSprid) {
		this.fkBgrSprid = fkBgrSprid;
	}

	public String getFkZichanUuid() {
		return fkZichanUuid;
	}

	public void setFkZichanUuid(String fkZichanUuid) {
		this.fkZichanUuid = fkZichanUuid;
	}
}
