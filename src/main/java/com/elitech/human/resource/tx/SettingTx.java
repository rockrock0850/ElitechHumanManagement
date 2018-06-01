package com.elitech.human.resource.tx;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elitech.human.resource.service.LeaveDayService;
import com.elitech.human.resource.service.SettingService;
import com.elitech.human.resource.vo.form.holidaycategory.HolidayCategoryVO;
import com.elitech.human.resource.vo.form.holidayemp.HolidayEmpVO;
import com.elitech.human.resource.vo.redis.RedisVO;

/**
 * 
 * @create by Adam
 */
@Service
public class SettingTx {
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private LeaveDayService leaveDayService;

	@Transactional
	public List<HolidayCategoryVO> findHolidayCategorys (HolidayEmpVO holiday) throws IOException {
		return settingService.findHolidayCategorys(holiday);
	}

	@Transactional
	public List<HolidayCategoryVO> findHolidayCategorys (HolidayCategoryVO category) throws IOException {
		return settingService.findHolidayCategorys(category);
	}

	@Transactional
	public HolidayCategoryVO findHolidayCategory (String leaveTypeId, String emptype) {
		return settingService.findHolidayCategory(leaveTypeId, emptype);
	}

	@Transactional
	public List<HolidayEmpVO> findHolidayEmps (HolidayEmpVO holiday, boolean duplicatable) throws Exception {
		return settingService.findHolidayEmps(holiday, duplicatable);
	}

	@Transactional
	public List<HolidayEmpVO> findHolidayEmp (String emptype, String leaveYear) {
		return settingService.findHolidayEmp(emptype, leaveYear);
	}

	@Transactional
	public void deleteHolidayCategoryBatch (List<HolidayCategoryVO> categorys) throws IOException {
		settingService.deleteHolidayCategoryBatch(categorys);
	}

	@Transactional
	public void deleteHolidayEmp (HolidayEmpVO holiday) throws IOException {
		settingService.deleteHolidayEmp(holiday);
	}

	@Transactional
	public void deleteHolidayEmpBatch (List<HolidayEmpVO> holidays) throws IOException {
		settingService.deleteHolidayEmpBatch(holidays);
	}

	@Transactional
	public void addHolidayCategory (HolidayCategoryVO category, RedisVO user) {
		settingService.addHolidayCategory(category, user);
		leaveDayService.addLeaveMasterColumn(category);
	}

	@Transactional
	public void addHolidayEmp (Map<String, Object> dataMap, RedisVO user) throws IOException {
		settingService.addHolidayEmp(dataMap, user);
	}

	@Transactional
	public void editHolidayCategory (HolidayCategoryVO category, RedisVO user) {
		settingService.editHolidayCategory(category, user);
	}

	@Transactional
	public HolidayEmpVO inUse (List<HolidayEmpVO> holidays) {
		String action = "U";
		
		HolidayEmpVO holiday = null;
		for (HolidayEmpVO h : holidays) {
			String years = h.getLeaveYear();
			String emptype = h.getEmptype();
			String emptypeDisplay = h.getEmptypeDisplay();
			double y = leaveDayService.inUse(emptype, years, action);
			
			if (y > 0) {
				holiday = new HolidayEmpVO();
				holiday.setYears(y);
				holiday.setEmptypeDisplay(emptypeDisplay);
				
				break;
			}
		}
		
		return holiday;
	}

}


