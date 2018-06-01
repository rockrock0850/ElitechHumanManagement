package com.elitech.human.resource.controller.function.human;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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
import com.elitech.human.resource.tx.HumanTx;
import com.elitech.human.resource.tx.LoginTx;
import com.elitech.human.resource.util.JsonUtil;
import com.elitech.human.resource.vo.exception.LogicException;
import com.elitech.human.resource.vo.form.applyleave.ApplyLeaveVO;
import com.elitech.human.resource.vo.form.dept.DepartmentVO;
import com.elitech.human.resource.vo.redis.RedisVO;
import com.elitech.human.resource.vo.rest.ResponseVO;

/**
 * 部門基本資料維護
 * 
 * @create by Adam
 * @create date: Oct 23, 2017
 */
@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/Function/DeptManagement")
public class DepartmentController extends BaseController {
	
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
		return new ModelAndView("hr.dept_management.find_1");
	}
	
	@Page(type = Page.DELETE)
	@RequestMapping(value = "/queryList", method = RequestMethod.POST)
	public ModelAndView queryList (HttpServletRequest request, @RequestBody DepartmentVO dept) throws Exception {
		List<DepartmentVO> accounts = tx.findDepartments(dept);
		session.setAttribute("queryCondition", dept);
		
		return new ModelAndView("hr.dept_management.list_1", "dataList", JsonUtil.toJson(accounts));
	}
	
	@Page(type = Page.INSERT)
	@RequestMapping(value = "/addFlow", method = RequestMethod.POST)
	public ModelAndView addFlow (HttpServletRequest request) throws Exception {
		return new ModelAndView("hr.dept_management.add_1");
	}
	
	@RequestMapping(value = "/addFlow/add", method = RequestMethod.POST)
	public ResponseVO add (HttpServletRequest request, @RequestBody DepartmentVO dept) throws Exception {
		String departmentId = dept.getDepartmentId();
		DepartmentVO vo = tx.findDepartment(departmentId);
		if (vo != null) {
			throw new LogicException(SysStatus.DEPT_ERROR_1);
		}

		String parentDeptId = dept.getParentDepartmentId();
		if (StringUtils.isNotBlank(parentDeptId)) {
			vo = tx.findDepartment(parentDeptId);
			if (vo == null) {
				throw new LogicException(SysStatus.DEPT_ERROR_2);
			}
		}

		String deptManger = dept.getDepartmentManager();
		if (StringUtils.isNotBlank(deptManger)) {
			ApplyLeaveVO applyLeaveVO = tx.findDeptManager(deptManger);
			if (applyLeaveVO == null) {
				throw new LogicException(SysStatus.DEPT_ERROR_3);
			}
		}
		
		RedisVO user = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));
		tx.addDepartment(dept, user);
		
		return new ResponseVO(SysStatus.SUCCESS_2);
	}
	
	@Page(type = Page.UPDATE)
	@RequestMapping(value = "/editFlow", method = RequestMethod.POST)
	public ModelAndView editFlow (HttpServletRequest request, @RequestBody DepartmentVO dept) throws Exception {
		return new ModelAndView("hr.dept_management.edit_1", "dept", JsonUtil.toJson(dept));
	}

	@RequestMapping(value = "/editFlow/edit", method = RequestMethod.POST)
	public ResponseVO edit (HttpServletRequest request, @RequestBody DepartmentVO dept) throws Exception {
		String parentDeptId = dept.getParentDepartmentId();
		DepartmentVO dept2;
		
		if (StringUtils.isNotBlank(parentDeptId)) {
			dept2 = tx.findDepartment(parentDeptId);
			if (dept2 == null) {
				throw new LogicException(SysStatus.DEPT_ERROR_2);
			}
		}
		
		String deptManger = dept.getDepartmentManager();
		if (StringUtils.isNotBlank(deptManger)) {
			ApplyLeaveVO applyLeaveVO = tx.findDeptManager(deptManger);
			if (applyLeaveVO == null) {
				throw new LogicException(SysStatus.DEPT_ERROR_3);
			}
		}
		
		RedisVO user = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));
		tx.editDeptByDeptId(dept, user);
		
		DepartmentVO queryCondition = (DepartmentVO) super.getQueryCondition(session, DepartmentVO.class);

		return new ResponseVO(SysStatus.SUCCESS_1, queryCondition);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseVO delete (HttpServletRequest request, @RequestBody List<DepartmentVO> depts) throws Exception {
		String deptId = tx.haveChildDept(depts);
		if (deptId != null) {
			String msg = SysStatus.DEPT_ERROR_4.getMsg();
			msg = StringUtils.replace(msg, "${deptId}", deptId);
			
			throw new LogicException("999", msg);
		}
		
		tx.deleteDepartmentBatch(depts);

		DepartmentVO queryCondition = (DepartmentVO) super.getQueryCondition(session, DepartmentVO.class);
		List<DepartmentVO> resultList = tx.findDepartments(queryCondition);
		
		return new ResponseVO(SysStatus.SUCCESS_3, resultList);
	}
	
	@Override
	@ExceptionHandler({Exception.class, LogicException.class})
	protected Object handleExceptionMsg (HttpServletRequest req, Object e) {
		return super.handleExceptionMsg(req, e);
	}
	
}