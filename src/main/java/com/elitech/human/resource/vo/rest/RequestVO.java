package com.elitech.human.resource.vo.rest;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.elitech.human.resource.util.AesCryptUtil;
import com.elitech.human.resource.util.ApiUtil;
import com.elitech.human.resource.util.PropUtil;

public class RequestVO {

	private String data;
	private String domain;
	private JSONObject json;
	
	public RequestVO () throws Exception {
		String appId = PropUtil.getProperty("app.id");
		String appKey = AesCryptUtil.getEncryptAppKey();

		json = new JSONObject();
		json.put("appId", appId);
		json.put("appKey", appKey);
	}
	
	public RequestVO (String domain) throws Exception {
		this();
		this.domain = domain;
	}
	
	public RequestVO (HttpServletRequest request) throws Exception {
		this();
		this.domain = ApiUtil.getDomain(request);
	}
	
	public void put (String name, Object value) {
		json.put(name, value);
	}
	
	public String getData() throws Exception {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void done() {
		this.data = json.toString();
	}

	public String getDomain () {
		return domain;
	}

	public void setDomain (String domain) {
		this.domain = domain;
	}
	
}
