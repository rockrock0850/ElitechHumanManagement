package com.elitech.human.resource.controller.function.auth;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.elitech.human.resource.annotations.Page;
import com.elitech.human.resource.constant.SysStatus;
import com.elitech.human.resource.controller.base.BaseController;
import com.elitech.human.resource.tx.AuthTx;
import com.elitech.human.resource.tx.LoginTx;
import com.elitech.human.resource.util.JsonUtil;
import com.elitech.human.resource.vo.exception.LogicException;
import com.elitech.human.resource.vo.form.groupauthority.GroupAuthorityVO;
import com.elitech.human.resource.vo.form.userrole.UserRoleMapEditVO;
import com.elitech.human.resource.vo.form.userrole.UserRoleMapTableVO;
import com.elitech.human.resource.vo.redis.RedisVO;
import com.elitech.human.resource.vo.rest.ResponseVO;

/**
 * 使用者角色設定
 * 
 * @create by Adam
 * @create date: Oct 23, 2017
 */
@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/Function/UserRoleMap")
public class UserRoleMapController extends BaseController {
	
	@Autowired
	private AuthTx authTx;
	
	@Autowired
	private LoginTx loginTx;
	
	@Autowired
	private HttpSession session;
	
	@PostConstruct
	public void init() {}
	
	@Page
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView index (HttpServletRequest request) throws Exception {
		return new ModelAndView("auth.user_role.find_1");
	}

	@RequestMapping(value = "/queryList", method = RequestMethod.POST)
	public ModelAndView queryList (HttpServletRequest request, @RequestBody GroupAuthorityVO tableVO) throws Exception {
		List<GroupAuthorityVO> accounts = authTx.findGroupAccounts(tableVO);
		session.setAttribute("queryCondition", tableVO);
		
		return new ModelAndView("auth.user_role.list_1", "dataList", JsonUtil.toJson(accounts));
	}
	
	@Page(type = Page.UPDATE)
	@RequestMapping(value = "/editFlow", method = RequestMethod.POST)
	public ModelAndView editFlow (HttpServletRequest request, @RequestBody GroupAuthorityVO role) throws Exception {
		String roleId = role.getRoleId();
		List<UserRoleMapTableVO> userRoleMaps = authTx.findUserRoles(roleId);

		JSONObject data = new JSONObject();
		data.put("roleId", roleId);
		data.put("userRoleMaps", userRoleMaps);
		
		return new ModelAndView("auth.user_role.edit_1", "data", data.toString());
	}
	
	@RequestMapping(value = "/editFlow/edit", method = RequestMethod.POST)
	public ResponseVO edit (HttpServletRequest request, @RequestBody UserRoleMapEditVO editVO) throws Exception {
		String sessionId = (String) session.getAttribute("sessionId");
		RedisVO user = loginTx.findLoginInfo(sessionId);
		authTx.editUserRole(editVO, user);
		
		return new ResponseVO(SysStatus.SUCCESS_1);
	}
	
	@Override
	@ExceptionHandler({Exception.class, LogicException.class})
	protected Object handleExceptionMsg (HttpServletRequest req, Object e) {
		return super.handleExceptionMsg(req, e);
	}
	
}