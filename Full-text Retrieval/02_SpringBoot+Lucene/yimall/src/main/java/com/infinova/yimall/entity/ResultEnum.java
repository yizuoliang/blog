package com.infinova.yimall.entity;

/**
 * 返回参数枚举
 * 
 * @author yizl
 *
 */
public enum ResultEnum {
	UNKNOW_ERROR(-1, "未知错误"),
	SUCCESS(0, "成功"),
	PASSWORD_ERROR(10001, "用户名或密码错误"),
    PARAMETER_ERROR(10002, "参数错误");

	/**
	 * 返回code
	 */
	private Integer code;
	/**
	 * 返回message
	 */
	private String msg;

	ResultEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
