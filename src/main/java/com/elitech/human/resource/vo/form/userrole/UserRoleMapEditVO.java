package com.elitech.human.resource.vo.form.userrole;

import java.util.List;

import com.elitech.human.resource.vo.form.userauthority.UserAuthorityVO;

/**
 * 
 * @create by Adam
 */
public class UserRoleMapEditVO {

	private String roleId;
	private List<UserAuthorityVO> users;

	public List<UserAuthorityVO> getUsers () {
		return users;
	}

	public void setUsers (List<UserAuthorityVO> users) {
		this.users = users;
	}

	public String getRoleId () {
		return roleId;
	}

	public void setRoleId (String roleId) {
		this.roleId = roleId;
	}

}
