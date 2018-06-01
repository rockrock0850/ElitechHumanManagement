package com.elitech.human.resource.vo.form.groupaccount;

/**
 * 
 * @create by Adam
 */
public class GroupAccountVO {

	private String roleId;
	private String roleName;
	private String status;
	private String statusDisplay;

	public String getRoleId () {
		return roleId;
	}

	public void setRoleId (String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName () {
		return roleName;
	}

	public void setRoleName (String roleName) {
		this.roleName = roleName;
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

}
