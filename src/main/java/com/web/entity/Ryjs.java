package com.web.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 人员角色表
 * 
 * @author Sookie
 *
 */
@Entity
@Table(name = "ryjs")
public class Ryjs implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="idGen",strategy="uuid")
	@GeneratedValue(generator="idGen")
	private String uuid;// varchar(32) NOT NULL COMMENT 'id',
	
	@Column(name="qxmc")
	private String qxmc;// varchar(32) DEFAULT NULL COMMENT '角色名称',
	
	@Column(name="qxdj")
	private String qxdj;// varchar(30) DEFAULT NULL COMMENT '权限等级',

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getQxmc() {
		return qxmc;
	}

	public void setQxmc(String qxmc) {
		this.qxmc = qxmc;
	}

	public String getQxdj() {
		return qxdj;
	}

	public void setQxdj(String qxdj) {
		this.qxdj = qxdj;
	}

}
