package com.elitech.human.resource.vo.exception;

import com.elitech.human.resource.constant.SysStatus;

public class LogicException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 328203399395350605L;
	
	private String code;
	private String msg;
	private String target;

	public LogicException(SysStatus status) {
		this.code = status.getCode();
		this.msg = status.getMsg();
	}

	public LogicException(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public LogicException(String code, String msg, String target) {
		this.code = code;
		this.msg = msg;
		this.target = target;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
	public String getTarget () {
		return target;
	}

}