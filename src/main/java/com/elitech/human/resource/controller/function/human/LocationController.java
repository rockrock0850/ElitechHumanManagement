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
import com.elitech.human.resource.constant.SysStatus;
import com.elitech.human.resource.controller.base.BaseController;
import com.elitech.human.resource.tx.HumanTx;
import com.elitech.human.resource.tx.LoginTx;
import com.elitech.human.resource.util.JsonUtil;
import com.elitech.human.resource.vo.exception.LogicException;
import com.elitech.human.resource.vo.form.location.LocationVO;
import com.elitech.human.resource.vo.redis.RedisVO;
import com.elitech.human.resource.vo.rest.ResponseVO;

/**
 * 駐點基本資料
 * 
 * @create by Adam
 * @create date: Oct 23, 2017
 */
@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/Function/LocationManagement")
public class LocationController extends BaseController {
	
	@Autowired
	private HumanTx tx;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private LoginTx loginTx;
	
	@PostConstruct
	public void init() {}
	
	@Page
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView index (HttpServletRequest request) throws Exception {
		return new ModelAndView("hr.location_management.find_1");
	}
	
	@Page(type = Page.DELETE)
	@RequestMapping(value = "/queryList", method = RequestMethod.POST)
	public ModelAndView queryList (HttpServletRequest request, @RequestBody LocationVO location) throws Exception {
		List<LocationVO> locations = tx.findLocations(location);
		session.setAttribute("queryCondition", location);
		
		return new ModelAndView("hr.location_management.list_1", "dataList", JsonUtil.toJson(locations));
	}
	
	@Page(type = Page.INSERT)
	@RequestMapping(value = "/addFlow", method = RequestMethod.POST)
	public ModelAndView addFlow (HttpServletRequest request) throws Exception {
		return new ModelAndView("hr.location_management.add_1");
	}
	
	@RequestMapping(value = "/addFlow/add", method = RequestMethod.POST)
	public ResponseVO add (HttpServletRequest request, @RequestBody LocationVO location) throws Exception {
		String locationId = location.getLocationId();
		LocationVO location2 = tx.findLocation(locationId);
		if (location2 != null) {
			throw new LogicException(SysStatus.LOCATION_MANAGEMENT_ERROR_1);
		}
		
		String locationName = location.getLocationName();
		location2 = tx.findLocationByLocationName(locationName);
		if (location2 != null) {
			throw new LogicException(SysStatus.LOCATION_MANAGEMENT_ERROR_2);
		}
		
		RedisVO user = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));
		tx.addLocation(location, user);
		
		return new ResponseVO(SysStatus.SUCCESS_2);
	}
	
	@Page(type = Page.UPDATE)
	@RequestMapping(value = "/editFlow", method = RequestMethod.POST)
	public ModelAndView editFlow (HttpServletRequest request, @RequestBody LocationVO location) throws Exception {
		return new ModelAndView("hr.location_management.edit_1", "location", JsonUtil.toJson(location));
	}

	@RequestMapping(value = "/editFlow/edit", method = RequestMethod.POST)
	public ResponseVO edit (HttpServletRequest request, @RequestBody LocationVO location) throws Exception {
		RedisVO user = loginTx.findLoginInfo((String) session.getAttribute("sessionId"));
		tx.editLocation(location, user);
		
		LocationVO queryCondition = (LocationVO) super.getQueryCondition(session, LocationVO.class);

		return new ResponseVO(SysStatus.SUCCESS_1, queryCondition);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseVO delete (HttpServletRequest request, @RequestBody List<LocationVO> locations) throws Exception {
		tx.deleteLocationBatch(locations);
		
		LocationVO location = (LocationVO) super.getQueryCondition(session, LocationVO.class);
		List<LocationVO> resultList = tx.findLocations(location);
		
		return new ResponseVO(SysStatus.SUCCESS_3, resultList);
	}
	
	@Override
	@ExceptionHandler({Exception.class, LogicException.class})
	protected Object handleExceptionMsg (HttpServletRequest req, Object e) {
		return super.handleExceptionMsg(req, e);
	}
	
}