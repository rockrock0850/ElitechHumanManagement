package com.elitech.human.resource.vo.other;

/**
 * 
 * @create by Adam
 */
public class ParamVO {

	private String key;
	private String value;
	private String status;
	private String display;

	public String getStatus () {
		return status;
	}

	public void setStatus (String status) {
		this.status = status;
	}

	public String getDisplay () {
		return display;
	}

	public void setDisplay (String display) {
		this.display = display;
	}

	public String getKey () {
		return key;
	}

	public void setKey (String key) {
		this.key = key;
	}

	public String getValue () {
		return value;
	}

	public void setValue (String value) {
		this.value = value;
	}

}
