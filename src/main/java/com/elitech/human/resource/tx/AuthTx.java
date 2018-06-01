package com.elitech.human.resource.tx;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elitech.human.resource.service.AuthService;
import com.elitech.human.resource.vo.form.groupaccount.GroupAccountVO;
import com.elitech.human.resource.vo.form.groupauthority.GroupAuthorityVO;
import com.elitech.human.resource.vo.form.userauthority.UserAuthorityVO;
import com.elitech.human.resource.vo.form.userrole.UserRoleMapEditVO;
import com.elitech.human.resource.vo.form.userrole.UserRoleMapTableVO;
import com.elitech.human.resource.vo.redis.ButtonVO;
import com.elitech.human.resource.vo.redis.RedisVO;

/**
 * 
 * @create by Adam
 */
@Service
public class AuthTx {
	
	@Autowired
	private AuthService authService;

	@Transactional
	public List<UserAuthorityVO> findUserAccounts (UserAuthorityVO user) throws IOException {
		return authService.findUserAccounts(user);
	}

	@Transactional
	public List<GroupAccountVO> findGroupAccounts (String roleId) throws IOException {
		return authService.findGroupAccounts(roleId);
	}

	@Transactional
	public GroupAccountVO findGroupAccountByRoleName (String roleName) {
		return authService.findGroupAccountByRoleName(roleName);
	}

	@Transactional
	public List<GroupAuthorityVO> findGroupAccounts (GroupAuthorityVO role) throws IOException {
		return authService.findGroupAccounts(role);
	}

	@Transactional
	public List<GroupAccountVO> findGroupAccounts (GroupAccountVO account) throws IOException {
		return authService.findGroupAccounts(account);
	}

	@Transactional
	public List<ButtonVO> findUserFunctions (String userId, RedisVO userInfo) throws Exception {
		return authService.findUserFunctions(userId, userInfo);
	}

	@Transactional
	public List<ButtonVO> findRoleFunctions (String roleId, RedisVO userInfo) throws Exception {
		return authService.findRoleFunctions(roleId, userInfo);
	}

	@Transactional
	public List<UserRoleMapTableVO> findUserRoles (String roleId) throws IOException {
		return authService.findUserRoles(roleId);
	}

	@Transactional
	public void deleteGroupAccountBatch (List<GroupAccountVO> vos) throws IOException {
		authService.deleteGroupAccountBatch(vos);
	}

	@Transactional
	public void addGroupAccount (GroupAccountVO vo, RedisVO user) {
		authService.addGroupAccount(vo, user);
	}

	@Transactional
	public void editGroupAccount (GroupAccountVO account, RedisVO user) {
		authService.editGroupAccount(account, user);
	}

	@Transactional
	public void updateGroupAuthority (String roleId, List<GroupAuthorityVO> roles, String userId) throws IOException {
		authService.editGroupAuthority(roleId, roles, userId);
	}

	@Transactional
	public void updateUserAuthority (String userId, List<UserAuthorityVO> users, String userInfoId) throws IOException {
		authService.editUserAuthority(userId, users, userInfoId);
	}

	@Transactional
	public void editUserRole (UserRoleMapEditVO editVO, RedisVO user) {
		String roleId = editVO.getRoleId();
		authService.deleteUserRole(roleId);
		
		String createUser = user.getAccountId();
		List<UserAuthorityVO> userRoles = editVO.getUsers();
		
		for (UserAuthorityVO userRole : userRoles) {
			String userId = userRole.getUserId();
			authService.addUserRole(roleId, userId, createUser);
		}
	}
	
}