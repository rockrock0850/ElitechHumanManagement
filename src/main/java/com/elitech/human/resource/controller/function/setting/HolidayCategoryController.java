package com.elitech.human.resource.controller.function.setting;

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
import com.elitech.human.resource.tx.LoginTx;
import com.elitech.human.resource.tx.SettingTx;
import com.elitech.human.resource.util.JsonUtil;
import com.elitech.human.resource.vo.exception.LogicException;
import com.elitech.human.resource.vo.form.holidaycategory.HolidayCategoryVO;
import com.elitech.human.resource.vo.redis.RedisVO;
import com.elitech.human.resource.vo.rest.ResponseVO;

/**
 * 假別資料維護
 * 
 * @create by Adam
 * @create date: Oct 23, 2017
 */
@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/Function/HolidayCategory")
public class HolidayCategoryController extends BaseController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private SettingTx tx;
	
	@Autowired
	private LoginTx loginTx;
	
	@PostConstruct
	public void init() {}
	
	@Page
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView index (HttpServletRequest request) throws Exception {
		return new ModelAndView("set.holiday_category.find_1");
	}
	
	@Page(type = Page.DELETE)
	@RequestMapping(value = "/queryList", method = RequestMethod.POST)
	public ModelAndView queryList (HttpServletRequest request, @RequestBody HolidayCategoryVO category) throws Exception {
		List<HolidayCategoryVO> categorys = tx.findHolidayCategorys(category);
		session.setAttribute("queryCondition", category);
		
		return new ModelAndView("set.holiday_category.list_1", "dataList", JsonUtil.toJson(categorys));
	}
	
	@Page(type = Page.INSERT)
	@RequestMapping(value = "/addFlow", method = RequestMethod.POST)
	public ModelAndView addFlow (HttpServletRequest request) throws Exception {
		return new ModelAndView("set.holiday_category.add_1");
	}
	
	@RequestMapping(value = "/addFlow/add", method = RequestMethod.POST)
	public ResponseVO add (HttpServletRequest request, @RequestBody HolidayCategoryVO category) throws Exception {
		String leaveTypeId = category.getLeavetypeId();
		String emptype = category.getEmptype();
		HolidayCategoryVO category2 = tx.findHolidayCategory(leaveTypeId, emptype);
		if (category2 != null) {
			throw new LogicException(SysStatus.HOLIDAY_CATEGORY_ERROR_1);
		}
		
		RedisVO user = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));
		tx.addHolidayCategory(category, user);
		
		return new ResponseVO(SysStatus.SUCCESS_2);
	}
	
	@Page(type = Page.UPDATE)
	@RequestMapping(value = "/editFlow", method = RequestMethod.POST)
	public ModelAndView editFlow (HttpServletRequest request, @RequestBody HolidayCategoryVO category) throws Exception {
		return new ModelAndView("set.holiday_category.edit_1", "category", JsonUtil.toJson(category));
	}

	@RequestMapping(value = "/editFlow/edit", method = RequestMethod.POST)
	public ResponseVO edit (HttpServletRequest request, @RequestBody HolidayCategoryVO category) throws Exception {
		String leaveTypeId = category.getLeavetypeId();
		String emptype = category.getEmptype();
		String oldEmptype = category.getOldEmptype();
		HolidayCategoryVO category2 = tx.findHolidayCategory(leaveTypeId, emptype);
		if (category2 != null && !StringUtils.equals(emptype, oldEmptype)) {
			throw new LogicException(SysStatus.HOLIDAY_CATEGORY_ERROR_1);
		}
		
		RedisVO user = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));
		tx.editHolidayCategory(category, user);
		
		HolidayCategoryVO queryCondition = (HolidayCategoryVO) super.getQueryCondition(session, HolidayCategoryVO.class);

		return new ResponseVO(SysStatus.SUCCESS_1, queryCondition);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseVO delete (HttpServletRequest request, @RequestBody List<HolidayCategoryVO> categorys) throws Exception {
		tx.deleteHolidayCategoryBatch(categorys);
		
		HolidayCategoryVO category = (HolidayCategoryVO) super.getQueryCondition(session, HolidayCategoryVO.class);
		List<HolidayCategoryVO> resultList = tx.findHolidayCategorys(category);
		
		return new ResponseVO(SysStatus.SUCCESS_3, resultList);
	}
	
	@Override
	@ExceptionHandler({Exception.class, LogicException.class})
	protected Object handleExceptionMsg (HttpServletRequest req, Object e) {
		return super.handleExceptionMsg(req, e);
	}
	
}