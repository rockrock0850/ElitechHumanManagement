package com.elitech.human.resource.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elitech.human.resource.constant.Resources;
import com.elitech.human.resource.dao.jdbc.JdbcDAO;
import com.elitech.human.resource.dao.jpa.ButtonRepo;
import com.elitech.human.resource.dao.jpa.EmployeeRepo;
import com.elitech.human.resource.dao.jpa.FunctionRepo;
import com.elitech.human.resource.dao.jpa.LoginUserRepo;
import com.elitech.human.resource.dao.jpa.MenuRepo;
import com.elitech.human.resource.dao.redis.LoginRedisRepo;
import com.elitech.human.resource.pojo.Button;
import com.elitech.human.resource.pojo.Employee;
import com.elitech.human.resource.pojo.Function;
import com.elitech.human.resource.pojo.LoginUser;
import com.elitech.human.resource.pojo.Menu;
import com.elitech.human.resource.util.BeanUtil;
import com.elitech.human.resource.util.ResourceUtil;
import com.elitech.human.resource.vo.other.PermissionVO;
import com.elitech.human.resource.vo.redis.ButtonVO;
import com.elitech.human.resource.vo.redis.FunctionVO;
import com.elitech.human.resource.vo.redis.MenuVO;
import com.elitech.human.resource.vo.redis.RedisVO;

/**
 * 
 * @create by Adam
 */
@Service
public class LoginService {

	@Autowired
	private LoginRedisRepo loginRedisRepo;

	@Autowired
	private MenuRepo menuRepo;

	@Autowired
	private FunctionRepo functionRepo;

	@Autowired
	private JdbcDAO jdbcDao;

	@Autowired
	private ButtonRepo buttonRepo;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private LoginUserRepo loginUserRepo;
	
	public boolean isLoggedIn (String key) {
		if (StringUtils.isBlank(key)) {
			return false;
		}
		RedisVO vo = loginRedisRepo.find(key);

		return vo == null ? false : true;
	}

	public void setExpire (String key, int expireTime) {
		loginRedisRepo.setExpire(key, expireTime);
	}

	public RedisVO findLoginInfo (String sessionId) {
		return loginRedisRepo.find(sessionId);
	}

	public void saveLoginInfo (String key, RedisVO redisVO, int expireTime) {
		String accountId = redisVO.getAccountId();
		String accountName = redisVO.getAccountName();
		
		loginRedisRepo.save(key, redisVO);
		loginRedisRepo.setExpire(key, expireTime);
		
		Employee pojo = employeeRepo.findOne(accountId);
		String email = pojo.getEmail();

		LoginUser loginUser = loginUserRepo.findOne(accountId);
		
		if (loginUser == null) {
			loginUser = new LoginUser();
			loginUser.setUserId(accountId);
			loginUser.setUserName(accountName);
			loginUser.setEmail(email);
			loginUser.setStatus("1");
			loginUser.setCreateUser(accountId);
			loginUser.setCreateTime(new Date());
			loginUserRepo.save(loginUser);
			
			return;
		}
		
		loginUserRepo.update(accountId, email, accountName, accountId, new Date());
	}

	public List<ButtonVO> findUserButtons (String userId) throws Exception {
		List<ButtonVO> buttons = new ArrayList<>();
		String sqlText = ResourceUtil.get(Resources.SQL_PERMISSION_USER, "FIND_USER_PERMISSION");

		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);

		List<PermissionVO> permissions = jdbcDao.queryForList(sqlText, params, PermissionVO.class);
		for (PermissionVO permission : permissions) {
			String functionId = permission.getFunctionId();
			String buttonId = permission.getButtonId();

			Button pojo = buttonRepo.findOne(functionId, buttonId);
			ButtonVO vo = new ButtonVO();
			
			if (pojo != null) {
				BeanUtils.copyProperties(pojo, vo);	
				buttons.add(vo);
			}
		}
		
		Collections.sort(buttons, (a, b) -> a.getSeq() < b.getSeq() ? -1 : 0);

		return buttons;
	}

	public List<MenuVO> findUserMenus (String userId) throws Exception {
		String sqlText = ResourceUtil.get(Resources.SQL_PERMISSION_USER, "FIND_USER_PERMISSION");

		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);

		List<PermissionVO> permissions = jdbcDao.queryForList(sqlText, params, PermissionVO.class);
		List<String> functionIds = getFunctionIds(permissions);
		List<MenuVO> menus = findMenus(functionIds);

		return menus;
	}

	private List<MenuVO> findMenus (List<String> functionIds) throws Exception {
		List<Menu> pojos = menuRepo.findMenuLv1();
		List<MenuVO> menus = BeanUtil.copyList(pojos, MenuVO.class);
		findSubMenus(menus, functionIds);
		menus = cleanMenus(menus, true);

		return menus;
	}

	private void findSubMenus (List<MenuVO> menus, List<String> functionIds) throws Exception {
		for (int i = 0; i < menus.size(); i++) {
			MenuVO menu = menus.get(i);
			String menuId = menu.getMenuId();
			List<Menu> pojos = menuRepo.findSubMenu(menuId);

			if (pojos == null || pojos.isEmpty()) {
				findFunctions(menus, functionIds);
			} else {
				List<MenuVO> subMenus = BeanUtil.copyList(pojos, MenuVO.class);
				menu.setSubMenus(subMenus);
				findSubMenus(subMenus, functionIds);
			}
		}
	}

	private void findFunctions (List<MenuVO> menus, List<String> functionIds) throws Exception {
		for (MenuVO menu : menus) {
			String menuId = menu.getMenuId();
			List<Function> pojos = functionRepo.findFunctions(menuId);
			List<FunctionVO> functions = BeanUtil.copyList(pojos, FunctionVO.class);

			functions = verifyFunctions(functions, functionIds);
			menu.setFunctions(functions);
		}
	}

	private List<FunctionVO> verifyFunctions (List<FunctionVO> functions, List<String> functionIds) {
		List<FunctionVO> fs = new ArrayList<>();
		for (FunctionVO function : functions) {
			String functionId = function.getFunctionId();
			boolean hasFunction = hasFunction(functionId, functionIds);

			if (hasFunction) {
				fs.add(function);
			}
		}

		return fs;
	}

	private boolean hasFunction (String functionId, List<String> functionIds) {
		for (String id : functionIds) {
			if (StringUtils.equals(functionId, id)) {
				return true;
			}
		}

		return false;
	}

	private List<MenuVO> cleanMenus (List<MenuVO> menus, boolean clean) {
		MenuVO[] menuArray = getMenuArray(menus);
		for (int i = 0; i < menuArray.length; i++) {
			MenuVO subMenu = menus.get(i);
			List<MenuVO> subMenus = subMenu.getSubMenus();

			if (subMenus != null) {
				subMenus = cleanMenus(subMenus, false);
				subMenu.setSubMenus(subMenus);
			}
			
			subMenus = subMenu.getSubMenus();
			List<FunctionVO> functions = subMenu.getFunctions();
			if (CollectionUtils.isEmpty(subMenus) && CollectionUtils.isEmpty(functions)) {
				menuArray[i] = null;
			}
		}

		menus.clear();
		menus = new LinkedList<>(Arrays.asList(menuArray));
		menus.removeAll(Collections.singleton(null));	
		
		return menus;
	}
	
	private MenuVO[] getMenuArray (List<MenuVO> menus) {
		MenuVO[] array = new MenuVO[menus.size()];
		menus.toArray(array);
		
		return array;
	}

	private List<String> getFunctionIds (List<PermissionVO> permissions) {
		List<String> ids = new ArrayList<>();

		for (PermissionVO permission : permissions) {
			ids.add(permission.getFunctionId());
		}

		Set<String> distinct = new HashSet<>();
		distinct.addAll(ids);

		ids.clear();
		ids.addAll(distinct);

		return ids;
	}

	public void logout (String key) {
		loginRedisRepo.logout(key);
	}

}
