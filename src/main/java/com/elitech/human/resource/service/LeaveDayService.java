package com.elitech.human.resource.service;

import java.io.IOException;
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
import com.elitech.human.resource.dao.jpa.EmployeeRepo;
import com.elitech.human.resource.dao.jpa.LeaveSettingRepo;
import com.elitech.human.resource.dao.jpa.LeaveTypeRepo;
import com.elitech.human.resource.dao.jpa.LeavedayDRepo;
import com.elitech.human.resource.pojo.LeaveSetting;
import com.elitech.human.resource.pojo.LeavedayD;
import com.elitech.human.resource.pojo.Leavetype;
import com.elitech.human.resource.util.ResourceUtil;
import com.elitech.human.resource.vo.form.applyleave.ApplyLeaveVO;
import com.elitech.human.resource.vo.form.employee.EmployeeVO;
import com.elitech.human.resource.vo.form.holidaycategory.HolidayCategoryVO;

/**
 * 
 * @create by Adam
 */
@Service
public class LeaveDayService {
	
	@Autowired
	private LeaveTypeRepo leaveTypeRepo;
	
	@Autowired
	private LeaveSettingRepo leaveSettingRepo;
	
	@Autowired
	private LeavedayDRepo leavedayDRepo;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private JdbcDAO jdbcDao;
	
	@Autowired
	private JdbcDAO jdbcDAO;

	public void initLeaveDays (String empId, String empType, double workedYears, String currentYear, String userId) throws IOException {
		clearLeavedayM(empId, Long.valueOf(currentYear));// 清除leavedayM內的資料
		
		List<Leavetype> leaveTypes = leaveTypeRepo.findByEmpType(empType);
		for (Leavetype leavetype : leaveTypes) {
			String leaveTypeId = leavetype.getId().getLeavetypeId();
			List<LeaveSetting> leaveSettings = leaveSettingRepo.findYearLeave(empType, leaveTypeId, currentYear, workedYears);
			
			if (CollectionUtils.isEmpty(leaveSettings)) {continue;}// 沒匹配到該假別的放假規定, 則不更新該假別相關放假天數
			
			LeaveSetting leaveSetting = leaveSettings.get(0);
			String leaveYear = leaveSetting.getLeaveYear();// 年度
			double years1 = leaveSetting.getYears();// 規定年資
			
			if (workedYears >= years1) {
				long leaveDays = leaveSetting.getLeaveDays();// 規定年度放假天數
				long leaveHours = getLeaveHours(leaveDays);
				
				mergeLeaveMaster(empId, Long.valueOf(leaveYear), leaveHours, leaveTypeId);
				addLeaveDetail("", empId, leaveTypeId, Long.valueOf(leaveYear), leaveHours, userId, "A", new Date(), "");
			}
		}
	}

	public EmployeeVO findByAction (String empId, String action) {
		List<LeavedayD> pojos = leavedayDRepo.findByAction(empId, action);
		
		if (CollectionUtils.isEmpty(pojos)) {return null;}
		
		EmployeeVO emp = new EmployeeVO();
		BeanUtils.copyProperties(pojos.get(0), emp);
		
		return emp;
	}

	public Map<String, Object> findLeaveMaster (String empId, String years) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_LEAVEDAY, "FIND_LEAVEDAY_MASTER_LIST");
		
		Conditions conditions = new Conditions(true);
		conditions.equal("T1.EMP_ID", empId);
		conditions.equal("T1.YEARS", years);
		conditions.and();
		sqlText = conditions.get(sqlText);

		return jdbcDao.queryForMap(sqlText);
	}

	public ApplyLeaveVO findLeaveDetail (String empId, String leavetypeId) {
		LeavedayD pojo = leavedayDRepo.findHours(empId, leavetypeId);
		
		if (pojo == null) {return null;}
		
		double hours = pojo.getHours();
		
		ApplyLeaveVO applyLeave = new ApplyLeaveVO();
		applyLeave.setLeavehours(hours);
		
		return applyLeave;
	}

	public void addHours (
			String empId, String leaveNo, String leavetypeId, Long years, 
			String createUser, Date transDate, String note, double hours) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_LEAVEDAY, "FIND_LAST_LEAVE_D");
		
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("empId", empId);
		dataMap.put("leaveNo", leaveNo);
		dataMap.put("leavetypeId", leavetypeId);
		dataMap = jdbcDAO.queryForMap(sqlText, dataMap);
		
		double oldHours = MapUtils.getDoubleValue(dataMap, "hours");
		hours = hours == 0 ? oldHours : hours;
		addLeaveDetail(leaveNo, empId, leavetypeId, years, hours, createUser, "A", transDate, note);
		
		sqlText = ResourceUtil.get(Resources.SQL_LEAVEDAY, "FIND_LEAVE_MASTER");
		
		dataMap = new HashMap<>();
		dataMap.put("empId", empId);
		dataMap.put("years", years);
		dataMap = jdbcDAO.queryForMap(sqlText, dataMap);
		
		oldHours = MapUtils.getDoubleValue(dataMap, leavetypeId);
		hours += oldHours;

		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE HR.LEAVEDAY_M ");
		builder.append("SET " + leavetypeId + " = " + hours + " WHERE ");
		builder.append("EMP_ID = '" + empId + "' AND YEARS = " + years);
		jdbcDAO.update(builder.toString());
	}

	public void addLeaveMasterColumn (HolidayCategoryVO category) {
		String leaveTypeId = category.getLeavetypeId();
		jdbcDAO.alterTableAddColumn("hr.leaveday_m", leaveTypeId, "DOUBLE PRECISION");
	}

	public void editHours (String empId, String leaveNo, String leavetypeId, Long years, long leavedays, double leavehours) throws IOException {
		leavehours = getLeaveHours(leavedays) + leavehours;

		String sqlText = ResourceUtil.get(Resources.SQL_LEAVEDAY, "FIND_LAST_LEAVE_D");
		
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("empId", empId);
		dataMap.put("leaveNo", leaveNo);
		dataMap.put("leavetypeId", leavetypeId);
		dataMap = jdbcDAO.queryForMap(sqlText, dataMap);
		
		long seq = MapUtils.getLongValue(dataMap, "seq");
		double oldhours = MapUtils.getDoubleValue(dataMap, "hours");
		leavedayDRepo.update(seq, leavehours, empId, new Date());
		
		sqlText = ResourceUtil.get(Resources.SQL_LEAVEDAY, "FIND_LEAVE_MASTER");
		
		dataMap = new HashMap<>();
		dataMap.put("empId", empId);
		dataMap.put("years", years);
		dataMap = jdbcDAO.queryForMap(sqlText, dataMap);
		
		double hours = MapUtils.getDoubleValue(dataMap, leavetypeId);
		hours = (hours + oldhours) - leavehours;

		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE HR.LEAVEDAY_M ");
		builder.append("SET " + leavetypeId + " = " + hours + " WHERE ");
		builder.append("EMP_ID = '" + empId + "' AND YEARS = " + years);
		jdbcDAO.update(builder.toString());
	}

	public void deleteLeaveDByEmpId (String empId) {
		leavedayDRepo.deleteByEmpId(empId);
	}

	public void minusHours (String empId, String leaveNo, String leavetypeId, Long years, long leaveDays, double leaveHours) throws IOException {
		leaveHours = getLeaveHours(leaveDays) + leaveHours;

		addLeaveDetail(leaveNo, empId, leavetypeId, years, leaveHours, empId, "U", new Date(), "");
		
		String sqlText = ResourceUtil.get(Resources.SQL_LEAVEDAY, "FIND_LEAVE_MASTER");
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("empId", empId);
		dataMap.put("years", years);
		dataMap = jdbcDAO.queryForMap(sqlText, dataMap);
		
		double hours = MapUtils.getDoubleValue(dataMap, leavetypeId);
		hours -= leaveHours;

		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE HR.LEAVEDAY_M ");
		builder.append("SET " + leavetypeId + " = " + hours + " WHERE ");
		builder.append("EMP_ID = '" + empId + "' AND YEARS = " + years);
		jdbcDAO.update(builder.toString());
	}

	public double inUse (String emptype, String years, String action) {
		List<LeavedayD> pojos = leavedayDRepo.findByUsed(Long.valueOf(years), action);
		
		if (CollectionUtils.isEmpty(pojos)) {return 0;}
		
		double y = 0;
		for (LeavedayD leavedayD : pojos) {
			String empId = leavedayD.getEmpId();
			String emptype2 = employeeRepo.findOne(empId).getEmptype();
			if (StringUtils.equals(emptype, emptype2)) {
				y = leavedayD.getYears();
				break;
			}
		}
		
		return y;
	}

	private void addLeaveDetail (
			String leaveNo, String empId, String leaveTypeId, long years, double hours, String userId, String action, Date transDate, String note) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_LEAVEDAY, "INSERT_LEAVE_DETAIL");
		Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("empId", empId);
        dataMap.put("years", years);
        dataMap.put("transDate", transDate);
        dataMap.put("leaveTypeId", leaveTypeId);
        dataMap.put("action", action);
        dataMap.put("hours", hours);
        dataMap.put("leaveNo", leaveNo);
        dataMap.put("note", note);
        dataMap.put("createUser", userId);
        dataMap.put("createTime", new Date());
		jdbcDAO.update(sqlText, dataMap);
	}

	private void clearLeavedayM (String empId, long leaveYear) throws IOException {	
		String sqlText = ResourceUtil.get(Resources.SQL_LEAVEDAY, "FIND_LEAVE_MASTER");
		Map<String, Object> leavedayM = new HashMap<>();
		leavedayM.put("empId", empId);
		leavedayM.put("years", leaveYear);
		leavedayM = jdbcDAO.queryForMap(sqlText, leavedayM);
		
		if (leavedayM == null) {return;}

		StringBuilder builder = new StringBuilder();
		for (String leavetypeId : leavedayM.keySet()) {
			if (StringUtils.startsWith(leavetypeId, "h")) {
				builder.append("UPDATE HR.LEAVEDAY_M SET ");
				builder.append(leavetypeId + " = 0 ");
				builder.append("WHERE emp_id = '" + empId + "' AND years = " + leaveYear);
				jdbcDAO.update(builder.toString());
				
				builder.setLength(0);
			}
		}
	}

	private long getLeaveHours (long leaveDays) {
		return leaveDays * 8;
	}

	private void mergeLeaveMaster (String empId, long years, long hours, String leaveTypeId) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_LEAVEDAY, "FIND_LEAVE_MASTER");
		Map<String, Object> leavedayM = new HashMap<>();
		leavedayM.put("empId", empId);
		leavedayM.put("years", years);
		leavedayM = jdbcDAO.queryForMap(sqlText, leavedayM);
		
		StringBuilder builder;
		if (MapUtils.isEmpty(leavedayM)) {
			builder = new StringBuilder("INSERT INTO HR.LEAVEDAY_M ");
			builder.append("(emp_id, years, " + leaveTypeId + ") ");
			builder.append("VALUES ('" + empId + "', " + years + ", " + hours + ")");
			jdbcDAO.update(builder.toString());
		} else {
			builder = new StringBuilder("UPDATE HR.LEAVEDAY_M SET " + leaveTypeId + " = " + hours + " WHERE ");
			builder.append("emp_id = '" + empId + "' AND years = " + years);
			jdbcDAO.update(builder.toString());
		}
	}
	
}

