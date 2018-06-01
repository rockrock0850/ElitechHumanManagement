package com.elitech.human.resource.vo.form.userrole;

/**
 * 
 * @create by Adam
 */
public class UserRoleMapTableVO {

	private String userId;
	private String userName;
	private String status;
	private String buttonType;
	private String statusDisplay;

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

	public String getStatus () {
		return status;
	}

	public void setStatus (String status) {
		this.status = status;
	}

	public String getStatusDisplay () {
		return statusDisplay;
	}

	public void setStatusDisplay (String statusDisplay) {
		this.statusDisplay = statusDisplay;
	}

	public String getButtonType () {
		return buttonType;
	}

	public void setButtonType (String buttonType) {
		this.buttonType = buttonType;
	}

}
