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
import com.elitech.human.resource.vo.form.searchform.FormSearchDeleteVO;
import com.elitech.human.resource.vo.form.searchform.FormSearchEditVO;
import com.elitech.human.resource.vo.form.searchform.FormSearchTableVO;
import com.elitech.human.resource.vo.redis.RedisVO;
import com.elitech.human.resource.vo.rest.ResponseVO;

/**
 * 已申請表單
 * 
 * @create by Adam
 * @create date: Oct 23, 2017
 */
@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/Function/FormSearch")
public class FormSearchController extends BaseController {
	
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
		
		List<FormSearchTableVO> dataList =  leaveTx.findLeaveRecords(empId);
		
		return new ModelAndView("leave.form_search.list_1", "dataList", JsonUtil.toJson(dataList));
	}
	
	@Page(type = Page.UPDATE)
	@RequestMapping(value = "/editFlow", method = RequestMethod.POST)
	public ModelAndView editFlow (HttpServletRequest request, @RequestBody FormSearchTableVO tableVO) throws Exception {
		FormSearchEditVO editVO = new FormSearchEditVO();
		BeanUtils.copyProperties(editVO, tableVO);
		
		String approveStatus = editVO.getApproveStatus();
		editVO.setEditableMsg(getEditableMsg(approveStatus));
		
		String leaveNo = tableVO.getLeaveNo();
		String document = leaveTx.findDocument(leaveNo);
		editVO.setDocument(document);
		
		String emptype = tableVO.getEmptype();
		String leavetypeId = tableVO.getLeavetypeId();
		long expire = leaveTx.findExpire(leavetypeId, emptype);
		editVO.setExpire(expire);
		
		String empId = tableVO.getEmpId();
		boolean editable = leaveTx.isEditable(empId, leaveNo);
		editVO.setEditable(editable);
		
		return new ModelAndView("leave.form_search.edit_1", "formSearchEditVO", JsonUtil.toJson(editVO));
	}

	@RequestMapping(value = "/editFlow/edit", method = RequestMethod.POST)
	public ResponseVO edit (HttpServletRequest request, @RequestBody FormSearchEditVO editVO) throws Exception {
		leaveTx.editLeaveRecord(editVO);
		return new ResponseVO(SysStatus.SUCCESS_1);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseVO delete (HttpServletRequest request, @RequestBody FormSearchDeleteVO deleteVO) throws Exception {
		leaveTx.deleteLeaveRecord(deleteVO);
		return new ResponseVO(SysStatus.SUCCESS_3);
	}
	
	@Override
	@ExceptionHandler({Exception.class, LogicException.class})
	protected Object handleExceptionMsg (HttpServletRequest req, Object e) {
		return super.handleExceptionMsg(req, e);
	}

	private String getEditableMsg (String status) {
		String msg = "";
		switch (status) {
			case "close":
				msg = "此請假單已結案，無法進行異動";
				break;

			case "cancel":
				msg = "請假單已取消";
				break;

			default:
				msg = "審核期間無法刪除或修改";
				break;
		}
		
		return msg;
	}
	
}