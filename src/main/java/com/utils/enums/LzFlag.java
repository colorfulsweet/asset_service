package com.utils.enums;
/**
 * 发放回收流转标志
 * @author Sookie
 *
 */
public enum LzFlag {
	/**
	 * 出库
	 */
	CK(1),
	/**
	 * 流转
	 */
	LZ(2),
	/**
	 * 回收
	 */
	HS(3);
	
	private Integer flag; //状态标识
	private LzFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getFlag() {
		return this.flag;
	}
}
