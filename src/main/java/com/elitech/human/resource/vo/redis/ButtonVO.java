package com.elitech.human.resource.vo.redis;

/**
 * 
 * @create by Adam
 */
public class ButtonVO {

	private String buttonId;
	private String buttonName;
	private String functionId;
	private String functionName;
	private String pageType;
	private long seq;
	private String action;
	private String buttonType;

	public String getButtonId () {
		return buttonId;
	}

	public void setButtonId (String buttonId) {
		this.buttonId = buttonId;
	}

	public String getButtonName () {
		return buttonName;
	}

	public void setButtonName (String buttonName) {
		this.buttonName = buttonName;
	}

	public String getFunctionId () {
		return functionId;
	}

	public void setFunctionId (String functionId) {
		this.functionId = functionId;
	}

	public long getSeq () {
		return seq;
	}

	public void setSeq (long seq) {
		this.seq = seq;
	}

	public String getAction () {
		return action;
	}

	public void setAction (String action) {
		this.action = action;
	}

	public String getPageType () {
		return pageType;
	}

	public void setPageType (String pageType) {
		this.pageType = pageType;
	}

	public String getButtonType () {
		return buttonType;
	}

	public void setButtonType (String buttonType) {
		this.buttonType = buttonType;
	}

	public String getFunctionName () {
		return functionName;
	}

	public void setFunctionName (String functionName) {
		this.functionName = functionName;
	}

}
