package com.utils.enums;
/**
 * 发放回收流转标志
 * @author Sookie
 *
 */
public enum LzType {
	/**
	 * 出库
	 */
	CK("出库", 1),
	/**
	 * 流转
	 */
	LZ("流转", 2),
	/**
	 * 回收
	 */
	HS("回收", 3);
	
	private String name; //名称
	private Integer flag; //状态标识
	private LzType(String name, Integer flag) {
		this.name = name;
		this.flag = flag;
	}
	public Integer getFlag() {
		return this.flag;
	}
	public String getName() {
		return this.name;
	}
}
