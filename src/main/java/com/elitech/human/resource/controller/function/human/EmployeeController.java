package com.elitech.human.resource.controller.function.human;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.elitech.human.resource.annotations.Page;
import com.elitech.human.resource.constant.Api;
import com.elitech.human.resource.constant.SysStatus;
import com.elitech.human.resource.controller.base.BaseController;
import com.elitech.human.resource.tx.HumanTx;
import com.elitech.human.resource.tx.LoginTx;
import com.elitech.human.resource.util.AesCryptUtil;
import com.elitech.human.resource.util.ApiUtil;
import com.elitech.human.resource.util.JsonUtil;
import com.elitech.human.resource.vo.exception.LogicException;
import com.elitech.human.resource.vo.form.employee.EmployeeVO;
import com.elitech.human.resource.vo.redis.RedisVO;
import com.elitech.human.resource.vo.rest.RequestVO;
import com.elitech.human.resource.vo.rest.ResponseVO;

/**
 * 員工基本資料維護
 * 
 * @create by Adam
 * @create date: Oct 23, 2017
 */
@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/Function/EmpManagement")
public class EmployeeController extends BaseController {
	
	@Autowired
	private HumanTx tx;
	
	@Autowired
	private LoginTx loginTx;
	
	@Autowired
	private HttpSession session;
	
	@PostConstruct
	public void init() {}
	
	@Page
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView index (HttpServletRequest request) throws Exception {
		return new ModelAndView("hr.emp_management.find_1");
	}
	
	@Page(type = Page.DELETE)
	@RequestMapping(value = "/queryList", method = RequestMethod.POST)
	public ModelAndView queryList (HttpServletRequest request, @RequestBody EmployeeVO employee) throws Exception {
		List<EmployeeVO> employees = tx.findEmployees(employee);
		session.setAttribute("queryCondition", employee);
		
		return new ModelAndView("hr.emp_management.list_1", "dataList", JsonUtil.toJson(employees));
	}
	
	@Page(type = Page.INSERT)
	@RequestMapping(value = "/addFlow", method = RequestMethod.POST)
	public ModelAndView addFlow (HttpServletRequest request) throws Exception {
		return new ModelAndView("hr.emp_management.add_1");
	}
	
	@RequestMapping(value = "/addFlow/add", method = RequestMethod.POST)
	public ResponseVO add (HttpServletRequest request, @RequestBody EmployeeVO employee) throws Exception {
		String empId = employee.getEmpId();
		EmployeeVO employee2 = tx.findEmployee(empId);
		if (employee2 != null) {
			throw new LogicException(SysStatus.EMP_MANAGEMENT_ERROR_1);
		}
		
		RedisVO user = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));

		String empCName = employee.getEmpCname();
		String password = AesCryptUtil.encrypt(empId);
		String email = employee.getEmail();
		String userId = user.getAccountId();
		
		RequestVO jsonObj = new RequestVO(request);
		jsonObj.put("accountId", empId);
		jsonObj.put("accountName", empCName);
		jsonObj.put("accountPasswd", password);
		jsonObj.put("email", email);
		jsonObj.put("createUser", userId);
		jsonObj.done();
		ApiUtil.call(Api.ADD_ACCOUNT, jsonObj, RequestMethod.POST);
		
		tx.addEmployee(employee, user);
		
		return new ResponseVO(SysStatus.SUCCESS_2);
	}
	
	@Page(type = Page.UPDATE)
	@RequestMapping(value = "/editFlow", method = RequestMethod.POST)
	public ModelAndView editFlow (HttpServletRequest request, @RequestBody EmployeeVO employee) throws Exception {
		employee.setIsUsed(tx.isLeaveDayUsed(employee));
		return new ModelAndView("hr.emp_management.edit_1", "employee", JsonUtil.toJson(employee));
	}

	@RequestMapping(value = "/editFlow/edit", method = RequestMethod.POST)
	public ResponseVO edit (HttpServletRequest request, @RequestBody EmployeeVO employee) throws Exception {
		RedisVO user = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));

		String accountId = employee.getEmpId();
		String jobstatus = employee.getJobstatus();
		String accountName = employee.getEmpCname();
		String email = employee.getCompanyEmail();
		String userId = user.getAccountId();
		
		RequestVO jsonObj = new RequestVO(request);
		jsonObj.put("accountId", accountId);
		jsonObj.put("accountName", accountName);
		jsonObj.put("email", email);
		jsonObj.put("status", jobstatus);
		jsonObj.put("modifyUser", userId);
		jsonObj.done();
		ApiUtil.call(Api.EDIT_ACCOUNT, jsonObj, RequestMethod.POST);
		
		tx.editEmployee(employee, user);
		
		EmployeeVO queryCondition = (EmployeeVO) super.getQueryCondition(session, EmployeeVO.class);

		return new ResponseVO(SysStatus.SUCCESS_1, queryCondition);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseVO delete (HttpServletRequest request, @RequestBody List<EmployeeVO> employees) throws Exception {
		tx.deleteEmployeeBatch(employees);
		
		EmployeeVO employee = (EmployeeVO) super.getQueryCondition(session, EmployeeVO.class);
		List<EmployeeVO> resultList = tx.findEmployees(employee);
		
		return new ResponseVO(SysStatus.SUCCESS_3, resultList);
	}
	
	@Override
	@ExceptionHandler({Exception.class, LogicException.class})
	protected Object handleExceptionMsg (HttpServletRequest req, Object e) {
		return super.handleExceptionMsg(req, e);
	}
	
}