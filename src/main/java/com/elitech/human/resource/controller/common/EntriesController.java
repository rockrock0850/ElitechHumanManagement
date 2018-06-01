package com.elitech.human.resource.controller.common;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.elitech.human.resource.constant.SysStatus;
import com.elitech.human.resource.controller.base.BaseController;
import com.elitech.human.resource.tx.LoginTx;
import com.elitech.human.resource.vo.exception.LogicException;
import com.elitech.human.resource.vo.rest.ResponseVO;

/**
 * 功能控制類別
 * 
 * @create by Adam
 * @create date: Oct 23, 2017
 */
@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/Entries")
public class EntriesController extends BaseController {
	
	@Autowired
	private LoginTx tx;
	
	@Autowired
	private HttpSession session;
	
	// 功能路徑
	private final String DASH_BOARD = "/Function/DashBoard";
	private final String EMPLOYEE_MANAGEMENT = "/Function/EmpManagement";
	private final String DEPT_MANAGEMENT = "/Function/DeptManagement";
	private final String GROUP_ACCOUNT = "/Function/GroupAccount";
	private final String GROUP_AUTHORITY = "/Function/GroupAuthority";
	private final String USER_AUTHORITY = "/Function/UserAuthority";
	private final String HOLIDAY_CATEGORY = "/Function/HolidayCategory";
	private final String LOCATION_MANAGEMENT = "/Function/LocationManagement";
	private final String HOLIDAY_EMP = "/Function/HolidayEmp";
	private final String APPLY_LEAVE = "/Function/ApplyLeave";
	private final String SIGN_PROCESS = "/Function/SignProcess";
	private final String FORM_SEARCH = "/Function/FormSearch";
	private final String USER_ROLE_MAP = "/Function/UserRoleMap";
	private final String PAID_LEAVE = "/Function/PaidLeave";
	
	@PostConstruct
	public void init() {}

	@RequestMapping(value = "/DashBoard", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView DashBoard (HttpServletRequest request) throws Exception {
		return new ModelAndView("three.column.theme", "func", DASH_BOARD);
	}

	@RequestMapping(value = "/EmpManagement", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView EmpManagement (HttpServletRequest request) throws Exception {
		return new ModelAndView("three.column.theme", "func", EMPLOYEE_MANAGEMENT);
	}

	@RequestMapping(value = "/DeptManagement", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView DeptManagement (HttpServletRequest request) throws Exception {
		return new ModelAndView("three.column.theme", "func", DEPT_MANAGEMENT);
	}

	@RequestMapping(value = "/GroupAccount", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView GroupAccount (HttpServletRequest request) throws Exception {
		return new ModelAndView("three.column.theme", "func", GROUP_ACCOUNT);
	}

	@RequestMapping(value = "/GroupAuthority", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView GroupAuthority (HttpServletRequest request) throws Exception {
		return new ModelAndView("three.column.theme", "func", GROUP_AUTHORITY);
	}

	@RequestMapping(value = "/HolidayCategory", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView HolidayCategory (HttpServletRequest request) throws Exception {
		return new ModelAndView("three.column.theme", "func", HOLIDAY_CATEGORY);
	}

	@RequestMapping(value = "/UserAuthority", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView UserAuthority (HttpServletRequest request) throws Exception {
		return new ModelAndView("three.column.theme", "func", USER_AUTHORITY);
	}

	@RequestMapping(value = "/UserRoleMap", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView UserRoleMap (HttpServletRequest request) throws Exception {
		return new ModelAndView("three.column.theme", "func", USER_ROLE_MAP);
	}

	@RequestMapping(value = "/LocationManagement", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView LocationManagement (HttpServletRequest request) throws Exception {
		return new ModelAndView("three.column.theme", "func", LOCATION_MANAGEMENT);
	}

	@RequestMapping(value = "/HolidayEmp", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView HolidayEmp (HttpServletRequest request) throws Exception {
		return new ModelAndView("three.column.theme", "func", HOLIDAY_EMP);
	}

	@RequestMapping(value = "/ApplyLeave", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView ApplyLeave (HttpServletRequest request) throws Exception {
		return new ModelAndView("three.column.theme", "func", APPLY_LEAVE);
	}

	@RequestMapping(value = "/SignProcess", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView SignProcess (HttpServletRequest request) throws Exception {
		return new ModelAndView("three.column.theme", "func", SIGN_PROCESS);
	}

	@RequestMapping(value = "/FormSearch", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView FormSearch (HttpServletRequest request) throws Exception {
		return new ModelAndView("three.column.theme", "func", FORM_SEARCH);
	}

	@RequestMapping(value = "/PaidLeave", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView PaidLeave (HttpServletRequest request) throws Exception {
		return new ModelAndView("three.column.theme", "func", PAID_LEAVE);
	}

	@RequestMapping(value = "/Logout", method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseVO Logout (HttpServletRequest request) throws Exception {
		String key = (String) session.getAttribute("sessionId");
		tx.logout(key);
		
		return new ResponseVO(SysStatus.SUCCESS_1);
	}

	@Override
	@ExceptionHandler({Exception.class, LogicException.class})
	protected Object handleExceptionMsg (HttpServletRequest req, Object e) {
		return super.handleExceptionMsg(req, e);
	}
	
}