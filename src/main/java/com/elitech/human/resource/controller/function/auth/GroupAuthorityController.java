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
import com.elitech.human.resource.vo.redis.ButtonVO;
import com.elitech.human.resource.vo.redis.RedisVO;
import com.elitech.human.resource.vo.rest.ResponseVO;

/**
 * 群組權限管理
 * 
 * @create by Adam
 * @create date: Oct 23, 2017
 */
@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/Function/GroupAuthority")
public class GroupAuthorityController extends BaseController {
	
	@Autowired
	private AuthTx tx;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private LoginTx loginTx;
	
	@PostConstruct
	public void init() {}
	
	@Page
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView index (HttpServletRequest request) throws Exception {
		return new ModelAndView("auth.group_authority.find_1");
	}

	@RequestMapping(value = "/queryList", method = RequestMethod.POST)
	public ModelAndView queryList (HttpServletRequest request, @RequestBody GroupAuthorityVO account) throws Exception {
		List<GroupAuthorityVO> accounts = tx.findGroupAccounts(account);
		session.setAttribute("queryCondition", account);
		
		return new ModelAndView("auth.group_authority.list_1", "dataList", JsonUtil.toJson(accounts));
	}
	
	@Page(type = Page.GROUP_AUTHORITY_CUSTOM_1)
	@RequestMapping(value = "/editFlow", method = RequestMethod.POST)
	public ModelAndView editFlow (HttpServletRequest request, @RequestBody GroupAuthorityVO role) throws Exception {
		String roleId = role.getRoleId();
		RedisVO user = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));
		List<ButtonVO> buttons = tx.findRoleFunctions(roleId, user);

		JSONObject data = new JSONObject();
		data.put("roleId", roleId);
		data.put("buttons", buttons);
		
		return new ModelAndView("auth.group_authority.edit_1", "data", data.toString());
	}
	
	@RequestMapping(value = "/editFlow/edit", method = RequestMethod.POST)
	public ResponseVO edit (HttpServletRequest request, @RequestBody GroupAuthorityVO data) throws Exception {
		String roleId = data.getRoleId();
		List<GroupAuthorityVO> roles = data.getDataList();
		String sessionId = (String) session.getAttribute("sessionId");
		RedisVO user = loginTx.findLoginInfo(sessionId);
		
		tx.updateGroupAuthority(roleId, roles, user.getAccountId());
		
		List<ButtonVO> buttons = loginTx.findUserButtons(user.getAccountId());
		user.getPermissions().setButtons(buttons);
		loginTx.saveLoginInfo(sessionId, user, 5);
		
		buttons = tx.findRoleFunctions(roleId, user);
		
		return new ResponseVO(SysStatus.SUCCESS_1, buttons);
	}
	
	@Override
	@ExceptionHandler({Exception.class, LogicException.class})
	protected Object handleExceptionMsg (HttpServletRequest req, Object e) {
		return super.handleExceptionMsg(req, e);
	}
	
}