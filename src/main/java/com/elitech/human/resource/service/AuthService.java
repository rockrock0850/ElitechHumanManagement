package com.elitech.human.resource.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elitech.human.resource.constant.Resources;
import com.elitech.human.resource.dao.jdbc.JdbcDAO;
import com.elitech.human.resource.dao.jdbc.criteria.Conditions;
import com.elitech.human.resource.dao.jpa.PermissionRoleRepo;
import com.elitech.human.resource.dao.jpa.PermissionUserRepo;
import com.elitech.human.resource.dao.jpa.UserRoleMapRepo;
import com.elitech.human.resource.dao.jpa.UserRoleRepo;
import com.elitech.human.resource.pojo.PermissionRole;
import com.elitech.human.resource.pojo.PermissionRoleId;
import com.elitech.human.resource.pojo.PermissionUser;
import com.elitech.human.resource.pojo.PermissionUserId;
import com.elitech.human.resource.pojo.UserRole;
import com.elitech.human.resource.pojo.UserRoleMapping;
import com.elitech.human.resource.pojo.UserRoleMappingId;
import com.elitech.human.resource.util.BeanUtil;
import com.elitech.human.resource.util.ResourceUtil;
import com.elitech.human.resource.vo.form.groupaccount.GroupAccountVO;
import com.elitech.human.resource.vo.form.groupauthority.GroupAuthorityVO;
import com.elitech.human.resource.vo.form.userauthority.UserAuthorityVO;
import com.elitech.human.resource.vo.form.userrole.UserRoleMapTableVO;
import com.elitech.human.resource.vo.redis.ButtonVO;
import com.elitech.human.resource.vo.redis.RedisVO;

/**
 * 
 * @create by Adam
 */
@Service
public class AuthService {
	
	@Autowired
	private JdbcDAO jdbcDao;
	
	@Autowired
	private UserRoleRepo userRoleRepo;
	
	@Autowired
	private PermissionRoleRepo permissionRoleRepo;
	
	@Autowired
	private PermissionUserRepo permissionUserRepo;
	
	@Autowired
	private UserRoleMapRepo userRoleMapRepo;
	
	public List<GroupAccountVO> findGroupAccounts (String roleId) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_USER_ROLE, "FIND_USER_ROLE_LIST");
		
		Conditions conditions = new Conditions(true);
		conditions.equal("T1.role_id", roleId);
		conditions.done();
		sqlText = conditions.get(sqlText);
		
		return jdbcDao.queryForList(sqlText, GroupAccountVO.class);
	}

	public GroupAccountVO findGroupAccountByRoleName (String roleName) {
		UserRole pojo = userRoleRepo.findOneByRoleName(roleName);
		GroupAccountVO vo = new GroupAccountVO();
		if (pojo != null) {
			BeanUtils.copyProperties(pojo, vo);;
		}
		
		return pojo == null ? null : vo;
	}
	
	public List<UserAuthorityVO> findUserAccounts (UserAuthorityVO user) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_LOGIN_USER, "FIND_LOGIN_USER_LIST");
		String userId = user.getUserId();
		String userName = user.getUserName();

		Conditions conditions;
		if (StringUtils.isBlank(userId) && StringUtils.isBlank(userName)) {// 預設撈全部
			conditions = new Conditions(true);
			conditions.unEqual("T1.status", "0");
		} else {
			conditions = new Conditions(true);
			if (StringUtils.isNotBlank(userId)) {
				conditions.like("T1.user_id", userId);
			}
			if (StringUtils.isNotBlank(userName)) {
				conditions.like("T1.user_name", userName);
			}
			conditions.and();
		}
		conditions.done();
		sqlText = conditions.get(sqlText);
		
		return jdbcDao.queryForList(sqlText, UserAuthorityVO.class);
	}

	public List<UserRoleMapTableVO> findUserRoles (String roleId) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_LOGIN_USER, "FIND_LOGIN_USER_LIST");
		Conditions conditions = new Conditions(true);
		conditions.unEqual("T1.status", "0");
		conditions.done();
		sqlText = conditions.get(sqlText);
		
		List<UserRoleMapTableVO> userRoleMaps = jdbcDao.queryForList(sqlText, UserRoleMapTableVO.class);
		List<UserRoleMapping> pojos = userRoleMapRepo.findByRoleId(roleId);
		
		for (UserRoleMapping mapping : pojos) {
			String userId = mapping.getId().getUserId();
			for (UserRoleMapTableVO userRoleMap : userRoleMaps) {
				String userId2 = userRoleMap.getUserId();
				if (StringUtils.equals(userId, userId2)) {
					userRoleMap.setButtonType("checked");
				}
			}
		}
		
		return userRoleMaps;
	}

	public List<GroupAuthorityVO> findGroupAccounts (GroupAuthorityVO role) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_USER_ROLE, "FIND_USER_ROLE_LIST");
		String roleId = role.getRoleId();
		String roleName = role.getRoleName();

		Conditions conditions;
		if (StringUtils.isBlank(roleId) && StringUtils.isBlank(roleName)) {// 預設撈全部
			conditions = new Conditions();
			conditions.done();
		} else {
			conditions = new Conditions(true);
			if (StringUtils.isNotBlank(roleId)) {
				conditions.like("T1.role_id", roleId);
			}
			if (StringUtils.isNotBlank(roleName)) {
				conditions.like("T1.role_name", roleName);
			}
			conditions.and();
		}
		sqlText = conditions.get(sqlText);
		
		return jdbcDao.queryForList(sqlText, GroupAuthorityVO.class);
	}

	public List<GroupAccountVO> findGroupAccounts (GroupAccountVO account) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_USER_ROLE, "FIND_USER_ROLE_LIST");
		String roleId = account.getRoleId();
		String roleName = account.getRoleName();
		String status = account.getStatus();
		
		Conditions conditions;
		if (StringUtils.isBlank(roleId) && StringUtils.isBlank(roleName) && StringUtils.isBlank(status)) {// 預設撈全部
			conditions = new Conditions();
			conditions.done();
		} else {
			conditions = new Conditions(true);
			if (StringUtils.isNotBlank(roleId)) {
				conditions.like("T1.role_id", roleId);
			}
			if (StringUtils.isNotBlank(roleName)) {
				conditions.like("T1.role_name", roleName);
			}
			if (StringUtils.isNotBlank(status)) {
				conditions.equal("T1.status", status);
			} 
			conditions.and();
		}
		sqlText = conditions.get(sqlText);
		
		return jdbcDao.queryForList(sqlText, GroupAccountVO.class);
	}

	public List<ButtonVO> findUserFunctions (String userId, RedisVO userInfo) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_BUTTON, "FIND_BUTTONS_ALL");
		List<ButtonVO> allButtons = jdbcDao.queryForList(sqlText, ButtonVO.class);
		List<ButtonVO> userInfoButtons = userInfo.getPermissions().getButtons();
		List<ButtonVO> userButtons = new ArrayList<>();
		
		for (ButtonVO userButton : userInfoButtons) {
			String functionId = userButton.getFunctionId();
			String buttonId = userButton.getButtonId();
			
			PermissionUser pojo = permissionUserRepo.findOne(userId, functionId, buttonId);
			
			if (pojo == null) {continue;}

			PermissionUserId id = pojo.getId();
			ButtonVO button = new ButtonVO();
			BeanUtils.copyProperties(id, button);
			userButtons.add(button);
		}

		for (ButtonVO button : allButtons) {
			String buttonId = button.getButtonId();
			for (ButtonVO userButton : userButtons) {
				String userButtonId = userButton.getButtonId();
				if (StringUtils.equals(userButtonId, buttonId)) {
					button.setButtonType("checked");
				}
			}
		}
		
		return allButtons;
	}

	public List<ButtonVO> findRoleFunctions (String roleId, RedisVO userInfo) throws Exception {
		String sqlText = ResourceUtil.get(Resources.SQL_BUTTON, "FIND_BUTTONS_ALL");
		List<ButtonVO> allButtons = jdbcDao.queryForList(sqlText, ButtonVO.class);
		List<ButtonVO> userButtons = userInfo.getPermissions().getButtons();
		List<ButtonVO> roleButtons = new ArrayList<>();
		
		for (ButtonVO userButton : userButtons) {
			String functionId = userButton.getFunctionId();
			String buttonId = userButton.getButtonId();
			
			PermissionRole pojo = permissionRoleRepo.findOne(roleId, functionId, buttonId);
			
			if (pojo == null) {continue;}

			PermissionRoleId id = pojo.getId();
			ButtonVO button = new ButtonVO();
			BeanUtils.copyProperties(id, button);
			roleButtons.add(button);
		}

		for (ButtonVO button : allButtons) {
			String buttonId = button.getButtonId();
			for (ButtonVO roleButton : roleButtons) {
				String roleButtonId = roleButton.getButtonId();
				if (StringUtils.equals(roleButtonId, buttonId)) {
					button.setButtonType("checked");
				}
			}
		}
		
		return allButtons;
	}

	public void addGroupAccount (GroupAccountVO account, RedisVO user) {
		if (account != null) {
			String userId = user.getAccountId();
			
			UserRole pojo = new UserRole();
			BeanUtils.copyProperties(account, pojo);
			
			pojo.setCreateTime(new Date());
			pojo.setCreateUser(userId);
			userRoleRepo.save(pojo);	
		}
	}

	public void addUserRole (String roleId, String userId, String user) {
		UserRoleMappingId id = new UserRoleMappingId();
		id.setRoleId(roleId);
		id.setUserId(userId);
		
		UserRoleMapping pojo = new UserRoleMapping();
		pojo.setId(id);
		pojo.setCreateUser(user);
		pojo.setCreateTime(new Date());
		userRoleMapRepo.save(pojo);
	}

	public void editGroupAccount (GroupAccountVO account, RedisVO user) {
		String roleId = account.getRoleId();
		String roleName = account.getRoleName();
		String status = account.getStatus();
		String modifyUser = user.getAccountId();
		Date modifyTime = new Date();
		
		userRoleRepo.updateByRoleId(roleId, roleName, status, modifyUser, modifyTime);
	}

	public void editGroupAuthority (String roleId, List<GroupAuthorityVO> roles, String userId) throws IOException {
		permissionRoleRepo.delete(roleId);

		if (CollectionUtils.isEmpty(roles)) {return;}
		
		for (GroupAuthorityVO vo : roles) {
			PermissionRole pojo = new PermissionRole();
			
			PermissionRoleId id = new PermissionRoleId();
			BeanUtils.copyProperties(vo, id);
			pojo.setId(id);
			pojo.setCreateUser(userId);
			pojo.setCreateTime(new Date());
			
			permissionRoleRepo.save(pojo);
		}
	}

	public void editUserAuthority (String userId, List<UserAuthorityVO> users, String userInfoId) {
		permissionUserRepo.delete(userId);

		if (CollectionUtils.isEmpty(users)) {return;}
		
		for (UserAuthorityVO vo : users) {
			PermissionUserId id = new PermissionUserId();
			BeanUtils.copyProperties(vo, id);
			PermissionUser pojo = new PermissionUser();
			pojo.setId(id);
			pojo.setCreateUser(userId);
			pojo.setCreateTime(new Date());
			
			permissionUserRepo.save(pojo);
		}
	}

	public void deleteGroupAccountBatch (List<GroupAccountVO> accounts) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_USER_ROLE, "DELETE_USER_ROLE");

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (GroupAccountVO vo : accounts) {
			Map<String, Object> map = (Map<String, Object>) BeanUtil.fromObj(vo);
			dataList.add(map);
		}
		
		jdbcDao.updateBatch(sqlText, dataList);
	}

	public void deleteUserRole (String roleId) {
		userRoleMapRepo.deleteByRoleId(roleId);
	}
	
}

