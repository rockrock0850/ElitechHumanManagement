package com.elitech.human.resource.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elitech.human.resource.constant.Resources;
import com.elitech.human.resource.dao.jdbc.JdbcDAO;
import com.elitech.human.resource.dao.jdbc.criteria.Conditions;
import com.elitech.human.resource.dao.jpa.LeaveSettingRepo;
import com.elitech.human.resource.dao.jpa.LeaveTypeRepo;
import com.elitech.human.resource.dao.jpa.ParamRepo;
import com.elitech.human.resource.pojo.LeaveSetting;
import com.elitech.human.resource.pojo.Leavetype;
import com.elitech.human.resource.pojo.LeavetypeId;
import com.elitech.human.resource.pojo.Param;
import com.elitech.human.resource.util.BeanUtil;
import com.elitech.human.resource.util.ResourceUtil;
import com.elitech.human.resource.vo.form.holidaycategory.HolidayCategoryVO;
import com.elitech.human.resource.vo.form.holidayemp.HolidayEmpVO;
import com.elitech.human.resource.vo.other.ParamVO;
import com.elitech.human.resource.vo.redis.RedisVO;

/**
 * 
 * @create by Adam
 */
@Service
public class SettingService {
	
	@Autowired
	private JdbcDAO jdbcDao;
	
	@Autowired
	private LeaveTypeRepo leaveTypeRepo;
	
	@Autowired
	private LeaveSettingRepo leaveSettingRepo;
	
	@Autowired
	private ParamRepo paramRepo;

	public HolidayCategoryVO findHolidayCategory (String leaveTypeId, String emptype) {
		LeavetypeId id = new LeavetypeId();
		id.setEmptype(emptype);
		id.setLeavetypeId(leaveTypeId);
		Leavetype pojo = leaveTypeRepo.findOne(id);
		
		if (pojo == null) {return null;}

		HolidayCategoryVO vo = new HolidayCategoryVO();
		BeanUtils.copyProperties(pojo, vo);
		
		return vo;
	}

	public List<HolidayEmpVO> findHolidayEmp (String emptype, String leaveYear) {
		List<LeaveSetting> pojos = leaveSettingRepo.findByEmpTypeAndLeaveYear(emptype, leaveYear);
		
		if (CollectionUtils.isEmpty(pojos)) {return null;}
		
		List<HolidayEmpVO> holidays = new ArrayList<>();
		for (LeaveSetting pojo : pojos) {
			HolidayEmpVO holiday = new HolidayEmpVO();
			
			String leavetypeId = pojo.getLeavetypeId();
			emptype = pojo.getEmptype();
			leaveYear = pojo.getLeaveYear();

			holiday.setLeavetypeId(leavetypeId);
			holiday.setEmptype(emptype);
			holiday.setLeaveYear(leaveYear);
			holidays.add(holiday);
		}
		
		return holidays;
	}

	public List<HolidayCategoryVO> findHolidayCategorys (HolidayEmpVO holiday) throws IOException {
		String emptype = holiday.getEmptype();
		HolidayCategoryVO category = new HolidayCategoryVO();
		category.setEmptype(emptype);
		
		return findHolidayCategorys(category);
	}

	public List<HolidayCategoryVO> findHolidayCategorys (HolidayCategoryVO category) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_LEAVE_TYPE, "FIND_LEAVE_TYPE_LIST");
		String leavetypeId = category.getLeavetypeId();
		String leaveName = category.getLeaveName();
		String unit = category.getUnit();
		String emptype = category.getEmptype();
		String gender = category.getGender();
		String isYears = category.getIsYears();

		Conditions conditions;
		if (StringUtils.isBlank(leavetypeId) && StringUtils.isBlank(leaveName) &&
				StringUtils.isBlank(unit) && StringUtils.isBlank(emptype) &&
				StringUtils.isBlank(gender) && StringUtils.isBlank(isYears)) {// 預設撈全部
			conditions = new Conditions();
			conditions.done();
		} else {
			conditions = new Conditions(true);
			if (StringUtils.isNotBlank(leavetypeId)) {
				conditions.like("T1.leavetype_id", leavetypeId);
			}
			if (StringUtils.isNotBlank(leaveName)) {
				conditions.like("T1.leave_name", leaveName);
			}
			if (StringUtils.isNotBlank(unit)) {
				conditions.equal("T1.unit", unit);
			}
			if (StringUtils.isNotBlank(emptype)) {
				conditions.equal("T1.emptype", emptype);
			}
			if (StringUtils.isNotBlank(gender)) {
				conditions.equal("T1.gender", gender);
			}
			if (StringUtils.isNotBlank(isYears)) {
				conditions.equal("T1.is_years", isYears);
			}
			conditions.and();
		}
		sqlText = conditions.get(sqlText);
		
		return jdbcDao.queryForList(sqlText, HolidayCategoryVO.class);
	}

	public List<HolidayEmpVO> findHolidayEmps (HolidayEmpVO holiday, boolean duplicatable) throws Exception {
		String sqlText = "";
		if (duplicatable) {
			sqlText = ResourceUtil.get(Resources.SQL_LEAVE_SETTING, "FIND_LEAVE_SETTING_LIST_DISTINCT");
		} else {
			sqlText = ResourceUtil.get(Resources.SQL_LEAVE_SETTING, "FIND_LEAVE_SETTING_LIST");
		}
		
		String empType = holiday.getEmptype();
		String leaveYear = holiday.getLeaveYear();

		Conditions conditions;
		if (StringUtils.isBlank(empType)) {// 預設撈全部
			conditions = new Conditions();
			conditions.done();
		} else {
			conditions = new Conditions(true);
			
			if (StringUtils.isNotBlank(empType)) {
				conditions.equal("T1.emptype", empType);
			}
			if (StringUtils.isNotBlank(leaveYear)) {
				conditions.equal("T1.leave_year", leaveYear);
			}
			
			conditions.and();
		}
		sqlText = conditions.get(sqlText);
		
		return jdbcDao.queryForList(sqlText, HolidayEmpVO.class);
	}

	public void deleteHolidayCategoryBatch (List<HolidayCategoryVO> categorys) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_LEAVE_TYPE, "DELETE_LEAVE_TYPE");

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (HolidayCategoryVO category : categorys) {
			Map<String, Object> map = (Map<String, Object>) BeanUtil.fromObj(category);
			dataList.add(map);
		}
		
		jdbcDao.updateBatch(sqlText, dataList);
	}

	public void deleteHolidayEmp (HolidayEmpVO holiday) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_LEAVE_SETTING, "DELETE_LEAVE_SETTING");
		Map<String, Object> dataMap = (Map<String, Object>) BeanUtil.fromObj(holiday);
		jdbcDao.update(sqlText, dataMap);
	}

	public void deleteHolidayEmpBatch (List<HolidayEmpVO> holidays) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_LEAVE_SETTING, "DELETE_LEAVE_SETTING");

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (HolidayEmpVO holiday : holidays) {
			Map<String, Object> map = (Map<String, Object>) BeanUtil.fromObj(holiday);
			dataList.add(map);
		}
		
		jdbcDao.updateBatch(sqlText, dataList);
	}

	public void addHolidayCategory (HolidayCategoryVO category, RedisVO user) {
		if (category != null) {
			String userId = user.getAccountId();
			
			LeavetypeId id = new LeavetypeId();
			BeanUtils.copyProperties(category, id);
			
			Leavetype pojo = new Leavetype();
			BeanUtils.copyProperties(category, pojo);
			pojo.setId(id);
			
			pojo.setCreateTime(new Date());
			pojo.setCreateUser(userId);
			leaveTypeRepo.save(pojo);	
		}
	}

	@SuppressWarnings("unchecked")
	public void addHolidayEmp (Map<String, Object> dataMap, RedisVO user) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_LEAVE_SETTING, "INSERT_LEAVE_SETTING");
		String emptype = MapUtils.getString(dataMap, "emptype");
		String leaveYear = MapUtils.getString(dataMap, "leaveYear");
		String accountId = user.getAccountId();
		Map<String, Object> data = MapUtils.getMap(dataMap, "data");

		for (String leaveTypeId : data.keySet()) {
			Map<String, Object> setting = MapUtils.getMap(data, leaveTypeId);
			int row = getSettingRow(setting.size());
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			
			for (int i = 0; i < row; i++) {
				String years = MapUtils.getString(setting, "years" + i, "");
				String leaveDays = MapUtils.getString(setting, "leaveDays" + i, "");
				String isProportional = MapUtils.getString(setting, "isProportional" + i, "");
				Double longYears = StringUtils.isBlank(years) ? 0 : Double.valueOf(years);
				long longleaveDays = StringUtils.isBlank(leaveDays) ? 0 : Long.valueOf(leaveDays);
				
				Map<String, Object> map = new HashMap<>();
				map.put("years", longYears);
				map.put("leaveDays", longleaveDays);
				map.put("isProportional", isProportional);
				map.put("leaveYear", leaveYear);
				map.put("emptype", emptype);
				map.put("leaveTypeId", leaveTypeId);
				map.put("createUser", accountId);
				map.put("createTime", new Date());
				dataList.add(map);
			}
			
			jdbcDao.updateBatch(sqlText, dataList);
		}
	}

	public void editHolidayCategory (HolidayCategoryVO category, RedisVO user) {
		String leavetypeId = category.getLeavetypeId();
		String leaveName = category.getLeaveName();
		String unit = category.getUnit();
		String emptype = category.getEmptype();
		String gender = category.getGender();
		Long expire = category.getExpire();
		String isYears = category.getIsYears();
		String isSubstitute = category.getIsSubstitute();
		String isDocument = category.getIsDocument();
		String documentMsg = category.getDocumentMsg();
		String modifyUser = user.getAccountId();
		Date modifyTime = new Date();
		
		leaveTypeRepo.updateByLeavetypeId(
				leavetypeId, leaveName, unit, emptype, gender, 
				expire, isYears, isSubstitute, isDocument, documentMsg, 
				modifyUser, modifyTime);
	}

	private int getSettingRow (int size) {
		if (size == 1) {return 1;}
		return size/3;
	}

	public List<ParamVO> findParamByKey (String string) {
		List<Param> pojos = paramRepo.findByKey("compensatory_leave");
		List<ParamVO> params = new ArrayList<>();
		for (Param pojo : pojos) {
			String key = pojo.getId().getKey();
			String value = pojo.getId().getValue();
			String status = pojo.getStatus();
			String display = pojo.getDisplay();
			
			ParamVO param = new ParamVO();
			param.setKey(key);
			param.setValue(value);
			param.setStatus(status);
			param.setDisplay(display);
			
			params.add(param);
		}
		
		return params;
	}
	
}

