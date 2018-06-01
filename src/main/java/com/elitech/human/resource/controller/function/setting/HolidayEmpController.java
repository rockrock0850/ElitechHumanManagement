package com.elitech.human.resource.controller.function.setting;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
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
import com.elitech.human.resource.vo.form.holidayemp.HolidayEmpVO;
import com.elitech.human.resource.vo.redis.RedisVO;
import com.elitech.human.resource.vo.rest.ResponseVO;

/**
 * 員工假別可休天數管理
 * 
 * @create by Adam
 * @create date: Oct 23, 2017
 */
@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/Function/HolidayEmp")
public class HolidayEmpController extends BaseController {
	
	@Autowired
	private SettingTx settingTx;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private LoginTx loginTx;
	
	@PostConstruct
	public void init() {}
	
	@Page
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView index (HttpServletRequest request) throws Exception {
		return new ModelAndView("set.holiday_emp.find_1");
	}
	
	@Page(type = Page.DELETE)
	@RequestMapping(value = "/queryList", method = RequestMethod.POST)
	public ModelAndView queryList (HttpServletRequest request, @RequestBody HolidayEmpVO holiday) throws Exception {
		List<HolidayEmpVO> holidays = settingTx.findHolidayEmps(holiday, true);
		session.setAttribute("queryCondition", holiday);
		
		return new ModelAndView("set.holiday_emp.list_1", "dataList", JsonUtil.toJson(holidays));
	}
	
	@Page(type = Page.INSERT)
	@RequestMapping(value = "/addFlow", method = RequestMethod.POST)
	public ModelAndView addFlow (HttpServletRequest request) throws Exception {
		return new ModelAndView("set.holiday_emp.add_1");
	}
	
	@RequestMapping(value = "/addFlow/add", method = RequestMethod.POST)
	public ResponseVO add (HttpServletRequest request, @RequestBody Map<String, Object> dataMap) throws Exception {
		String emptype = MapUtils.getString(dataMap, "emptype");
		String leaveYear = MapUtils.getString(dataMap, "leaveYear");
		
		List<HolidayEmpVO> holidays = settingTx.findHolidayEmp(emptype, leaveYear);
		if (!CollectionUtils.isEmpty(holidays)) {
			throw new LogicException(SysStatus.HOLIDAY_EMP_ERROR_1);
		}

		RedisVO user = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));
		settingTx.addHolidayEmp(dataMap, user);
		
		return new ResponseVO(SysStatus.SUCCESS_2);
	}
	
	@Page(type = Page.UPDATE)
	@RequestMapping(value = "/editFlow", method = RequestMethod.POST)
	public ModelAndView editFlow (HttpServletRequest request, @RequestBody HolidayEmpVO holiday) throws Exception {
		List<HolidayEmpVO> holidays = settingTx.findHolidayEmps(holiday, false);
		return new ModelAndView("set.holiday_emp.edit_1", "holidays", JsonUtil.toJson(holidays));
	}

	@RequestMapping(value = "/editFlow/edit", method = RequestMethod.POST)
	public ResponseVO edit (HttpServletRequest request, @RequestBody Map<String, Object> dataMap) throws Exception {
		String emptype = MapUtils.getString(dataMap, "emptype");
		String leaveYear = MapUtils.getString(dataMap, "leaveYear");

		HolidayEmpVO holiday = new HolidayEmpVO();
		holiday.setEmptype(emptype);
		holiday.setLeaveYear(leaveYear);
		
		settingTx.deleteHolidayEmp(holiday);

		RedisVO user = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));
		settingTx.addHolidayEmp(dataMap, user);
		
		HolidayEmpVO queryCondition = (HolidayEmpVO) super.getQueryCondition(session, HolidayEmpVO.class);
		
		return new ResponseVO(SysStatus.SUCCESS_1, queryCondition);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseVO delete (HttpServletRequest request, @RequestBody List<HolidayEmpVO> holidays) throws Exception {
		HolidayEmpVO holiday = settingTx.inUse(holidays);
		
		if (holiday != null) {
			double years = holiday.getYears();
			String emptypeDisplay = holiday.getEmptypeDisplay();
			
			throw new LogicException("999", (long) years + "年度之[" + emptypeDisplay + "]假別已在使用中, 故不可刪除");
		}
		
		settingTx.deleteHolidayEmpBatch(holidays);
		
		holiday = (HolidayEmpVO) super.getQueryCondition(session, HolidayEmpVO.class);
		List<HolidayEmpVO> resultList = settingTx.findHolidayEmps(holiday, true);
		
		return new ResponseVO(SysStatus.SUCCESS_3, resultList);
	}
	
	@RequestMapping(value = "/getCategorys", method = RequestMethod.POST)
	public ResponseVO getLeaveTypes (HttpServletRequest request, @RequestBody HolidayEmpVO holiday) throws Exception {
		List<HolidayCategoryVO> categorys = settingTx.findHolidayCategorys(holiday);
		return new ResponseVO(categorys);
	}
	
	@Override
	@ExceptionHandler({Exception.class, LogicException.class})
	protected Object handleExceptionMsg (HttpServletRequest req, Object e) {
		return super.handleExceptionMsg(req, e);
	}
	
}