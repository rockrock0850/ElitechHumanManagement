package com.elitech.human.resource.controller.function.leave;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.elitech.human.resource.annotations.Page;
import com.elitech.human.resource.constant.SysStatus;
import com.elitech.human.resource.controller.base.BaseController;
import com.elitech.human.resource.tx.LeaveTx;
import com.elitech.human.resource.tx.LoginTx;
import com.elitech.human.resource.util.ExcelUtil;
import com.elitech.human.resource.vo.exception.LogicException;
import com.elitech.human.resource.vo.other.FileVO;
import com.elitech.human.resource.vo.redis.RedisVO;
import com.elitech.human.resource.vo.rest.ResponseVO;

/**
 * 補休作業
 * 
 * @create by Adam
 * @create date: Oct 23, 2017
 */
@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/Function/PaidLeave")
public class PaidLeaveController extends BaseController {
	
	@Autowired
	private LoginTx loginTx;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private LeaveTx leaveTx;
	
	@PostConstruct
	public void init() {}
	
	@Page(type = Page.PAID_LEAVE_CUSTOM_1)
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView index (HttpServletRequest request) throws Exception {
		return new ModelAndView("leave.paid_leave.add_1");
	}
	
	@RequestMapping(value = "/addFlow/add", method = RequestMethod.POST)
	public ResponseVO add (HttpServletRequest request, @RequestParam("excel") MultipartFile file) throws Exception {
		FileVO fileVO = super.toFileVO(file);
		File excel = fileVO.getFile();
		List<Map<String, Object>> dataList = ExcelUtil.parse(excel);
		
		String[] allowedColumns = {"員工編號", "年度", "發生日期", "事由", "加班時數"};
		boolean result = ExcelUtil.validate(dataList, allowedColumns);
		if (!result) {
			throw new LogicException(SysStatus.PAID_LEAVE_ERROR_1);
		}
		
		RedisVO user = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));
		
		leaveTx.addPaidLeave(dataList, user);
		
		return new ResponseVO(SysStatus.SUCCESS_2);
	}
	
	@Override
	@ExceptionHandler({Exception.class, LogicException.class})
	protected Object handleExceptionMsg (HttpServletRequest req, Object e) {
		return super.handleExceptionMsg(req, e);
	}
	
}