package com.elitech.human.resource.vo.form.groupauthority;

import java.util.List;

/**
 * 
 * @create by Adam
 */
public class GroupAuthorityVO {

	private String roleId;
	private String functionId;
	private String buttonId;
	private String roleName;
	private String status;
	private String statusDisplay;
	private List<GroupAuthorityVO> dataList;

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

	public List<GroupAuthorityVO> getDataList () {
		return dataList;
	}

	public void setDataList (List<GroupAuthorityVO> dataList) {
		this.dataList = dataList;
	}

	public String getStatusDisplay () {
		return statusDisplay;
	}

	public void setStatusDisplay (String statusDisplay) {
		this.statusDisplay = statusDisplay;
	}

}
