package com.elitech.human.resource.controller.function;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.elitech.human.resource.controller.base.BaseController;
import com.elitech.human.resource.tx.LeaveTx;
import com.elitech.human.resource.tx.LoginTx;
import com.elitech.human.resource.vo.exception.LogicException;
import com.elitech.human.resource.vo.form.searchform.FormSearchTableVO;
import com.elitech.human.resource.vo.form.signprocess.SignProcessTableVO;
import com.elitech.human.resource.vo.redis.RedisVO;

/**
 * 首頁
 * 
 * @create by Adam
 * @create date: Oct 23, 2017
 */
@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/Function/DashBoard")
public class DashBoardController extends BaseController {
	
	@Autowired
	private LoginTx loginTx;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private LeaveTx leaveTx;

	@PostConstruct
	public void init() {}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView index (HttpServletRequest request) throws Exception {
		RedisVO user = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));
		String empId = user.getAccountId();
		
		List<FormSearchTableVO> formSearchs =  leaveTx.findLeaveRecords(empId);
		List<SignProcessTableVO> signProcesss =  leaveTx.findSigningLeaveRecords(empId);
		
		JSONObject data = new JSONObject();
		data.put("formSearchs", formSearchs);
		data.put("signProcesss", signProcesss);
		
		return new ModelAndView("index.dash_board.list_1", "data", data.toString());
	}
	
	@Override
	@ExceptionHandler({Exception.class, LogicException.class})
	protected Object handleExceptionMsg (HttpServletRequest req, Object e) {
		return super.handleExceptionMsg(req, e);
	}
	
}