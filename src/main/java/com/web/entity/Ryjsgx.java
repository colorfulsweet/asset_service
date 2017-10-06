package com.web.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 人员-角色关系表
 * @author 夏夜梦星辰
 *
 */
@Entity
@Table(name="ryjsgx")
public class Ryjsgx implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="idGen",strategy="uuid")
	@GeneratedValue(generator="idGen")
	private String uuid;
	
	@Column(name="fk_bgr_id")
	private String fkBgrId ;//varchar(32) NOT NULL COMMENT '保管人ID',
	
	@Column(name="fk_ryjs_id")
	private String fkRyjsId ;//varchar(32) NOT NULL COMMENT '角色ID'

	public String getFkBgrId() {
		return fkBgrId;
	}

	public void setFkBgrId(String fkBgrId) {
		this.fkBgrId = fkBgrId;
	}

	public String getFkRyjsId() {
		return fkRyjsId;
	}

	public void setFkRyjsId(String fkRyjsId) {
		this.fkRyjsId = fkRyjsId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
