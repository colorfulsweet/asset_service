package com.utils;

import java.io.Serializable;
/**
 * HTTP响应包装类
 * @author 夏夜梦星辰
 *
 */
public class ResBody implements Serializable {
	private static final long serialVersionUID = -7321788912855027539L;
	/**
	 * 状态码, 1代表正常, 0代表错误
	 */
	private Integer status;
	/**
	 * 回传的消息信息(用于页面的消息提示等)
	 */
	private String msg;
	/**
	 * 回传的数据(可被序列化为JSON字符串)
	 */
	private Object data;
	public ResBody() {}
	public ResBody(Integer status, String msg) {
		this.status = status;
		this.msg = msg;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
