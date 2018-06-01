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
import com.elitech.human.resource.vo.form.userauthority.UserAuthorityVO;
import com.elitech.human.resource.vo.redis.ButtonVO;
import com.elitech.human.resource.vo.redis.RedisVO;
import com.elitech.human.resource.vo.rest.ResponseVO;

/**
 * 使用者權限管理
 * 
 * @create by Adam
 * @create date: Oct 23, 2017
 */
@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/Function/UserAuthority")
public class UserAuthorityController extends BaseController {
	
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
		return new ModelAndView("auth.user_authority.find_1");
	}

	@RequestMapping(value = "/queryList", method = RequestMethod.POST)
	public ModelAndView queryList (HttpServletRequest request, @RequestBody UserAuthorityVO account) throws Exception {
		List<UserAuthorityVO> accounts = tx.findUserAccounts(account);
		session.setAttribute("queryCondition", account);
		
		return new ModelAndView("auth.user_authority.list_1", "dataList", JsonUtil.toJson(accounts));
	}
	
	@Page(type = Page.USER_AUTHORITY_CUSTOM_1)
	@RequestMapping(value = "/editFlow", method = RequestMethod.POST)
	public ModelAndView editFlow (HttpServletRequest request, @RequestBody UserAuthorityVO user) throws Exception {
		String userId = user.getUserId();
		RedisVO userInfo = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));
		List<ButtonVO> buttons = tx.findUserFunctions(userId, userInfo);

		JSONObject data = new JSONObject();
		data.put("userId", userId);
		data.put("buttons", buttons);
		
		return new ModelAndView("auth.user_authority.edit_1", "data", data.toString());
	}
	
	@RequestMapping(value = "/editFlow/edit", method = RequestMethod.POST)
	public ResponseVO edit (HttpServletRequest request, @RequestBody UserAuthorityVO data) throws Exception {
		String userId = data.getUserId();
		List<UserAuthorityVO> users = data.getDataList();
		String sessionId = (String) session.getAttribute("sessionId");
		RedisVO userInfo = loginTx.findLoginInfo(sessionId);
		
		tx.updateUserAuthority(userId, users, userInfo.getAccountId());
		
		List<ButtonVO> buttons = loginTx.findUserButtons(userInfo.getAccountId());
		userInfo.getPermissions().setButtons(buttons);
		loginTx.saveLoginInfo(sessionId, userInfo, 5);
		
		buttons = tx.findUserFunctions(userId, userInfo);
		
		return new ResponseVO(SysStatus.SUCCESS_1, buttons);
	}
	
	@Override
	@ExceptionHandler({Exception.class, LogicException.class})
	protected Object handleExceptionMsg (HttpServletRequest req, Object e) {
		return super.handleExceptionMsg(req, e);
	}
	
}