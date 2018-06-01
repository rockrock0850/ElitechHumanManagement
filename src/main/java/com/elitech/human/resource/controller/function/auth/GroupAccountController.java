package com.elitech.human.resource.controller.function.auth;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
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
import com.elitech.human.resource.vo.form.groupaccount.GroupAccountVO;
import com.elitech.human.resource.vo.redis.RedisVO;
import com.elitech.human.resource.vo.rest.ResponseVO;

/**
 * 群組帳號維護
 * 
 * @create by Adam
 * @create date: Oct 23, 2017
 */
@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/Function/GroupAccount")
public class GroupAccountController extends BaseController {
	
	@Autowired
	private AuthTx tx;
	
	@Autowired
	private LoginTx loginTx;
	
	@Autowired
	private HttpSession session;
	
	@PostConstruct
	public void init() {}
	
	@Page
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView index (HttpServletRequest request) throws Exception {
		return new ModelAndView("auth.group_account.find_1");
	}
	
	@Page(type = Page.DELETE)
	@RequestMapping(value = "/queryList", method = RequestMethod.POST)
	public ModelAndView queryList (HttpServletRequest request, @RequestBody GroupAccountVO account) throws Exception {
		List<GroupAccountVO> accounts = tx.findGroupAccounts(account);
		session.setAttribute("queryCondition", account);
		
		return new ModelAndView("auth.group_account.list_1", "dataList", JsonUtil.toJson(accounts));
	}
	
	@Page(type = Page.INSERT)
	@RequestMapping(value = "/addFlow", method = RequestMethod.POST)
	public ModelAndView addFlow (HttpServletRequest request) throws Exception {
		return new ModelAndView("auth.group_account.add_1");
	}
	
	@RequestMapping(value = "/addFlow/add", method = RequestMethod.POST)
	public ResponseVO add (HttpServletRequest request, @RequestBody GroupAccountVO account) throws Exception {
		String roleId = account.getRoleId();
		List<GroupAccountVO> vos = tx.findGroupAccounts(roleId);
		if (!CollectionUtils.isEmpty(vos)) {
			throw new LogicException(SysStatus.GROUP_ACCOUNT_ERROR_1);
		}
		
		String roleName = account.getRoleName();
		GroupAccountVO vo = tx.findGroupAccountByRoleName(roleName);
		if (vo != null) {
			throw new LogicException(SysStatus.GROUP_ACCOUNT_ERROR_2);
		}
		
		RedisVO user = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));
		tx.addGroupAccount(account, user);
		
		return new ResponseVO(SysStatus.SUCCESS_2);
	}
	
	@Page(type = Page.UPDATE)
	@RequestMapping(value = "/editFlow", method = RequestMethod.POST)
	public ModelAndView editFlow (HttpServletRequest request, @RequestBody GroupAccountVO account) throws Exception {
		return new ModelAndView("auth.group_account.edit_1", "account", JsonUtil.toJson(account));
	}

	@RequestMapping(value = "/editFlow/edit", method = RequestMethod.POST)
	public ResponseVO edit (HttpServletRequest request, @RequestBody GroupAccountVO account) throws Exception {
		RedisVO user = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));
		tx.editGroupAccount(account, user);
		
		GroupAccountVO queryCondition = (GroupAccountVO) super.getQueryCondition(session, GroupAccountVO.class);

		return new ResponseVO(SysStatus.SUCCESS_1, queryCondition);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseVO delete (HttpServletRequest request, @RequestBody List<GroupAccountVO> accounts) throws Exception {
		tx.deleteGroupAccountBatch(accounts);
		
		GroupAccountVO account = (GroupAccountVO) super.getQueryCondition(session, GroupAccountVO.class);
		List<GroupAccountVO> resultList = tx.findGroupAccounts(account);
		
		return new ResponseVO(SysStatus.SUCCESS_3, resultList);
	}
	
	@Override
	@ExceptionHandler({Exception.class, LogicException.class})
	protected Object handleExceptionMsg (HttpServletRequest req, Object e) {
		return super.handleExceptionMsg(req, e);
	}
	
}