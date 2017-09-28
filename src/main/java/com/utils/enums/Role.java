package com.utils.enums;

public enum Role {
	MA("MA", "材料员"),
	MK("MK", "保管员");
	
	private Role(String code, String name) {
		this.code = code;
		this.name = name;
	}
	private String code;
	private String name;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
