package com.elitech.human.resource.controller.function.leave;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
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
import com.elitech.human.resource.tx.LeaveTx;
import com.elitech.human.resource.tx.LoginTx;
import com.elitech.human.resource.util.DateUtil;
import com.elitech.human.resource.util.JsonUtil;
import com.elitech.human.resource.vo.exception.LogicException;
import com.elitech.human.resource.vo.form.applyleave.ApplyLeaveVO;
import com.elitech.human.resource.vo.redis.RedisVO;
import com.elitech.human.resource.vo.rest.ResponseVO;

/**
 * 請假申請
 * 
 * @create by Adam
 * @create date: Oct 23, 2017
 */
@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/Function/ApplyLeave")
public class ApplyLeaveController extends BaseController {
	
	@Autowired
	private LeaveTx leaveTx;
	
	@Autowired
	private LoginTx loginTx;
	
	@Autowired
	private HttpSession session;
	
	@PostConstruct
	public void init() {}
	
	@Page
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView index (HttpServletRequest request) throws Exception {
		RedisVO user = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));
		String empId = user.getAccountId();
		ApplyLeaveVO applyLeave = leaveTx.findEmployee(empId);
		
		String emptype = applyLeave.getEmptype();
		String gender = applyLeave.getGender();
		List<Map<String, Object>> leavetypes = leaveTx.findLeaveTypesByGender(gender, emptype);
		applyLeave.setLeavetypes(leavetypes);
		
		Map<String, Object> resultMap = leaveTx.findLeaveMaster(empId, DateUtil.getCurrentYear());
		applyLeave.setLeaveMaster(resultMap);
		
		return new ModelAndView("leave.apply_leave.add_1", "applyLeave", JsonUtil.toJson(applyLeave));
	}
	
	@RequestMapping(value = "/addFlow/add", method = RequestMethod.POST)
	public ResponseVO add (HttpServletRequest request, @RequestBody ApplyLeaveVO applyLeave) throws Exception {
		String empId = applyLeave.getEmpId();
		String leavetypeId = applyLeave.getLeavetypeId();
		Map<String, Object> dataMap = leaveTx.findLeaveMaster(empId, DateUtil.getCurrentYear());
		
		if (MapUtils.isEmpty(dataMap)) {
			throw new LogicException(SysStatus.APPLY_LEAVE_ERROR_1);
		}

		double totalLeaveHours = getTotalLeaveHours(applyLeave);
		double leaveHours = MapUtils.getDoubleValue(dataMap, leavetypeId);
		if (totalLeaveHours > leaveHours) {
			throw new LogicException(SysStatus.APPLY_LEAVE_ERROR_1);
		}
		leaveTx.addLeaveRecord(applyLeave);
		
		ApplyLeaveVO applyLeave2 = leaveTx.findEmployee(empId);
		String emptype = applyLeave2.getEmptype();
		long leaveDays = applyLeave.getLeavedays();
		String substitute = applyLeave.getSubstitute();
		
		applyLeave.setProcess(getProcess(emptype, leaveDays, substitute));
		applyLeave.setApproveStatus("sign");
		leaveTx.editLeaveRecord(applyLeave);
		
		return new ResponseVO(SysStatus.SUCCESS_2);
	}
	
	@Override
	@ExceptionHandler({Exception.class, LogicException.class})
	protected Object handleExceptionMsg (HttpServletRequest req, Object e) {
		return super.handleExceptionMsg(req, e);
	}
	
	private double getTotalLeaveHours (ApplyLeaveVO applyLeave) {
		long days = applyLeave.getLeavedays();
		double hours = applyLeave.getLeavehours();
		hours = days * 8 + hours;
		
		return hours;
	}

	private String getProcess (String emptype, long leaveDays, String substitute) {
		String process = "";

		switch (emptype) {
			case "1":// 外派
				process = leaveDays > 3 ? "manager" : "hr";
				break;
				
			case "2":// 內部
				process = "manager";
				break;
		}

		if (!StringUtils.isBlank(substitute)) {process = "substitute";}
		
		return process;
	}
	
}