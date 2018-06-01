package com.elitech.human.resource.controller.function.leave;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
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
import com.elitech.human.resource.util.JsonUtil;
import com.elitech.human.resource.vo.exception.LogicException;
import com.elitech.human.resource.vo.form.signprocess.SignProcessEditVO;
import com.elitech.human.resource.vo.form.signprocess.SignProcessTableVO;
import com.elitech.human.resource.vo.redis.RedisVO;
import com.elitech.human.resource.vo.rest.ResponseVO;

/**
 * 簽核作業
 * 
 * @create by Adam
 * @create date: Oct 23, 2017
 */
@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/Function/SignProcess")
public class SignProcessController extends BaseController {
	
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
		
		List<SignProcessTableVO> dataList =  leaveTx.findSigningLeaveRecords(empId);
		
		return new ModelAndView("leave.sign_process.list_1", "dataList", JsonUtil.toJson(dataList));
	}
	
	@Page(type = Page.UPDATE)
	@RequestMapping(value = "/editFlow", method = RequestMethod.POST)
	public ModelAndView editFlow (HttpServletRequest request, @RequestBody SignProcessTableVO tableVO) throws Exception {
		SignProcessEditVO editVO = new SignProcessEditVO();
		BeanUtils.copyProperties(editVO, tableVO);
		
		String leaveNo = tableVO.getLeaveNo();
		String document = leaveTx.findDocument(leaveNo);
		editVO.setDocument(document);
		
		return new ModelAndView("leave.sign_process.edit_1", "signProcess", JsonUtil.toJson(editVO));
	}

	@RequestMapping(value = "/pass", method = RequestMethod.POST)
	public ResponseVO pass (HttpServletRequest request, @RequestBody SignProcessEditVO editVO) throws Exception {
		leaveTx.signPass(editVO);
		return new ResponseVO(SysStatus.SUCCESS_1);
	}

	@RequestMapping(value = "/reject", method = RequestMethod.POST)
	public ResponseVO reject (HttpServletRequest request, @RequestBody SignProcessEditVO editVO) throws Exception {
		leaveTx.signFailed(editVO);
		return new ResponseVO(SysStatus.SUCCESS_1);
	}
	
	@Override
	@ExceptionHandler({Exception.class, LogicException.class})
	protected Object handleExceptionMsg (HttpServletRequest req, Object e) {
		return super.handleExceptionMsg(req, e);
	}
	
}