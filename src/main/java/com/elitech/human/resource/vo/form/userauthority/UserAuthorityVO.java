package com.elitech.human.resource.vo.form.userauthority;

import java.util.List;

/**
 * 
 * @create by Adam
 */
public class UserAuthorityVO {

	private String userId;
	private String userName;
	private String functionId;
	private String buttonId;
	private String status;
	private String statusDisplay;
	private List<UserAuthorityVO> dataList;

	public String getStatus () {
		return status;
	}

	public void setStatus (String status) {
		this.status = status;
	}

	public String getFunctionId () {
		return functionId;
	}

	public void setFunctionId (String functionId) {
		this.functionId = functionId;
	}

	public String getButtonId () {
		return buttonId;
	}

	public void setButtonId (String buttonId) {
		this.buttonId = buttonId;
	}

	public List<UserAuthorityVO> getDataList () {
		return dataList;
	}

	public void setDataList (List<UserAuthorityVO> dataList) {
		this.dataList = dataList;
	}

	public String getUserId () {
		return userId;
	}

	public void setUserId (String userId) {
		this.userId = userId;
	}

	public String getUserName () {
		return userName;
	}

	public void setUserName (String userName) {
		this.userName = userName;
	}

	public String getStatusDisplay () {
		return statusDisplay;
	}

	public void setStatusDisplay (String statusDisplay) {
		this.statusDisplay = statusDisplay;
	}

}
