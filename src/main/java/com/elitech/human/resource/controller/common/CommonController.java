package com.elitech.human.resource.controller.common;

import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.elitech.human.resource.controller.base.BaseController;
import com.elitech.human.resource.tx.HumanTx;
import com.elitech.human.resource.tx.LeaveTx;
import com.elitech.human.resource.tx.LoginTx;
import com.elitech.human.resource.util.AesCryptUtil;
import com.elitech.human.resource.util.ApiUtil;
import com.elitech.human.resource.util.PropUtil;
import com.elitech.human.resource.util.RedirectUtil;
import com.elitech.human.resource.vo.exception.LogicException;
import com.elitech.human.resource.vo.form.applyleave.ApplyLeaveVO;
import com.elitech.human.resource.vo.form.dept.DepartmentVO;
import com.elitech.human.resource.vo.form.location.LocationVO;
import com.elitech.human.resource.vo.redis.RedisVO;
import com.elitech.human.resource.vo.rest.ResponseVO;

/**
 * 共用Action
 * 
 * @create by Adam
 * @create date: Oct 23, 2017
 */
@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/Common")
public class CommonController extends BaseController {

	@Autowired
	private LoginTx loginTx;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private LeaveTx leaveTx;

	@Autowired
	private HumanTx humanTx;
	
	@PostConstruct
	public void init() {}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public void resetPassword (HttpServletRequest request, HttpServletResponse response) throws Exception {
		String appId = PropUtil.getProperty("app.id");
		String appKey = AesCryptUtil.getEncryptAppKey();
		String sourceRoot = ApiUtil.getDomain(request);

		RedisVO user = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));
		String empId = user.getAccountId();
		
		JSONObject json = new JSONObject();
		json.put("appId", appId);
		json.put("appKey", appKey);
		json.put("accountId", empId);
		json.put("sourceRoot", sourceRoot);
		byte[] b = Base64.getEncoder().encode(json.toString().getBytes());
		String data = new String(b, "utf-8");
		
		new RedirectUtil(response, sourceRoot + "/ElitechLoginManagement/Entries/ResetPassword", data);
	}

	@RequestMapping(value = "/findOtherEmployees", method = RequestMethod.POST)
	public ResponseVO findOtherEmployees (HttpServletRequest request) throws Exception {
		RedisVO user = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));
		String empId = user.getAccountId();
		List<ApplyLeaveVO> applyLeaves = leaveTx.findOtherEmployees(empId);
		
		return new ResponseVO(applyLeaves);
	}
	
	@RequestMapping(value = "/findAllDepts", method = RequestMethod.POST)
	public ResponseVO findAllDepts (HttpServletRequest request) throws Exception {
		List<DepartmentVO> depts = humanTx.findAllDepartments();
		return new ResponseVO(depts);
	}
	
	@RequestMapping(value = "/findOtherDepts", method = RequestMethod.POST)
	public ResponseVO findOtherDepts (HttpServletRequest request, @RequestBody DepartmentVO dept) throws Exception {
		List<DepartmentVO> depts = humanTx.findOtherDepartments(dept);
		return new ResponseVO(depts);
	}
	
	@RequestMapping(value = "/findAllLocations", method = RequestMethod.POST)
	public ResponseVO findAllLocations (HttpServletRequest request) throws Exception {
		List<LocationVO> locations = humanTx.findAllSales();
		return new ResponseVO(locations);
	}

	@Override
	@ExceptionHandler({Exception.class, LogicException.class})
	protected Object handleExceptionMsg (HttpServletRequest req, Object e) {
		return super.handleExceptionMsg(req, e);
	}
	
}