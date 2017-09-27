package com.web.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 资产表实体类
 * @author zjl
 *
 */
@Entity
@Table(name="zichan")
public class Zichan implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// 主键
	@Id
	@GenericGenerator(name="idGen",strategy="uuid")
	@GeneratedValue(generator="idGen")
	private String uuid;                 //varchar(32) not null comment 'id',
	@Column(name="zcid")
	private String  zcID;                 //varchar(32) comment '资产编码',
	@Column(name="fk_bgdwid")
	private String fk_bgdwID;            //varchar(32) comment '保管单位编号',
	@Column(name="mingch")
	private String mingch;               //varchar(30) comment '名称',
	@Column(name="zcly")
	private String  zcly;                 //varchar(30) comment '资产来源',
	@Column(name="gys")
	private String gys;                  //varchar(30) comment '供应商',
	@Column(name="dcxmmc")
	private String dcxmmc;               //varchar(30) comment '调出项目名称',
	@Column(name="shul")
	private String shul;                 //varchar(30) comment '数量',
	@Column(name="ggxh")
	private String  ggxh;                 //varchar(30) comment '规格型号',
	@Column(name="lbie")
	private String  lbie;                 //varchar(30) comment '类别',
	@Column(name="ppcj")
	private String  ppcj;                 //varchar(30) comment '品牌厂家',
	@Column(name="danwei")
	private String  danwei;               //varchar(30) comment '单位',
	@Column(name="danjia")
	private String  danjia;               //varchar(30) comment '单价',
	@Column(name="zczt")
	private String   zczt;                 //varchar(32) comment '资产状态',
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getZcID() {
		return zcID;
	}
	public void setZcID(String zcID) {
		this.zcID = zcID;
	}
	public String getFk_bgdwID() {
		return fk_bgdwID;
	}
	public void setFk_bgdwID(String fk_bgdwID) {
		this.fk_bgdwID = fk_bgdwID;
	}
	public String getMingch() {
		return mingch;
	}
	public void setMingch(String mingch) {
		this.mingch = mingch;
	}
	public String getZcly() {
		return zcly;
	}
	public void setZcly(String zcly) {
		this.zcly = zcly;
	}
	public String getGys() {
		return gys;
	}
	public void setGys(String gys) {
		this.gys = gys;
	}
	public String getDcxmmc() {
		return dcxmmc;
	}
	public void setDcxmmc(String dcxmmc) {
		this.dcxmmc = dcxmmc;
	}
	public String getShul() {
		return shul;
	}
	public void setShul(String shul) {
		this.shul = shul;
	}
	public String getGgxh() {
		return ggxh;
	}
	public void setGgxh(String ggxh) {
		this.ggxh = ggxh;
	}
	public String getLbie() {
		return lbie;
	}
	public void setLbie(String lbie) {
		this.lbie = lbie;
	}
	public String getPpcj() {
		return ppcj;
	}
	public void setPpcj(String ppcj) {
		this.ppcj = ppcj;
	}
	public String getDanwei() {
		return danwei;
	}
	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}
	public String getDanjia() {
		return danjia;
	}
	public void setDanjia(String danjia) {
		this.danjia = danjia;
	}
	public String getZczt() {
		return zczt;
	}
	public void setZczt(String zczt) {
		this.zczt = zczt;
	}

	
}
