package com.elitech.human.resource.vo.rest;

import org.springframework.http.HttpStatus;

import com.elitech.human.resource.constant.SysStatus;

public class ResponseVO {

	private String code;
	private String msg;
    private Object data;

    public ResponseVO () {}
    
    /**
     * 前端顯示成功訊息時使用
     * 
     * @create by Adam
     * @create date: Nov 5, 2017
     *
     * @param status
     */
    public ResponseVO (SysStatus status) {
    	this.code = status.getCode();
    	this.msg = status.getMsg();
    }
    
    public ResponseVO (SysStatus status, Object data) {
    	this.code = status.getCode();
    	this.msg = status.getMsg();
    	this.data = data;
    }
    
    public ResponseVO (Object data) {
    	this.code = HttpStatus.OK.toString();
    	this.data = data;
    }
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
