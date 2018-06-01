package com.elitech.human.resource.tx;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elitech.human.resource.service.HumanService;
import com.elitech.human.resource.service.LeaveDayService;
import com.elitech.human.resource.service.LeaveService;
import com.elitech.human.resource.util.DateUtil;
import com.elitech.human.resource.vo.form.applyleave.ApplyLeaveVO;
import com.elitech.human.resource.vo.form.dept.DepartmentVO;
import com.elitech.human.resource.vo.form.employee.EmployeeVO;
import com.elitech.human.resource.vo.form.location.LocationVO;
import com.elitech.human.resource.vo.redis.RedisVO;

/**
 * 
 * @create by Adam
 */
@Service
public class HumanTx {
	
	@Autowired
	private HumanService humanService;
	
	@Autowired
	private LeaveService leaveService;
	
	@Autowired
	private LeaveDayService leaveDayService;

	@Transactional
	public EmployeeVO findEmployee (String empId) {
		return humanService.findEmployee(empId);
	}

	@Transactional
	public DepartmentVO findDepartment (String departmentId) throws IOException {
		return humanService.findDepartment(departmentId);
	}

	@Transactional
	public ApplyLeaveVO findDeptManager (String deptManger) throws IOException {
		return leaveService.findEmployee(deptManger);
	}

	@Transactional
	public List<EmployeeVO> findEmployees (EmployeeVO employee) throws IOException {
		return humanService.findEmployees(employee);
	}

	@Transactional
	public List<DepartmentVO> findDepartments (DepartmentVO dept) throws Exception {
		return humanService.findDepartments(dept);
	}

	@Transactional
	public List<DepartmentVO> findAllDepartments () throws Exception {
		return humanService.findAllDepartments();
	}

	@Transactional
	public List<DepartmentVO> findOtherDepartments (DepartmentVO dept) throws Exception {
		return humanService.findOtherDepartments(dept);
	}

	@Transactional
	public List<LocationVO> findLocations (LocationVO location) throws IOException {
		return humanService.findLocations(location);
	}

	@Transactional
	public LocationVO findLocation (String locationId) {
		return humanService.findLocation(locationId);
	}

	@Transactional
	public LocationVO findLocationByLocationName (String locationName) {
		return humanService.findLocationByLocationName(locationName);
	}

	@Transactional
	public List<DepartmentVO> findEmpExceptSelf (String empId) throws Exception {
		return humanService.findEmpExceptSelf(empId);
	}

	@Transactional
	public List<LocationVO> findAllSales () throws Exception {
		return humanService.findAllSales();
	}

	@Transactional
	public void deleteDepartmentBatch (List<DepartmentVO> depts) throws IOException {
		humanService.deleteDepartmentBatch(depts);
	}

	@Transactional
	public void deleteEmployeeBatch (List<EmployeeVO> employees) throws IOException {
		humanService.deleteEmployeeBatch(employees);
	}

	@Transactional
	public void deleteLocationBatch (List<LocationVO> locations) throws IOException {
		humanService.deleteLocationBatch(locations);
	}

	@Transactional
	public void addEmployee (EmployeeVO employee, RedisVO user) throws Exception {
		String empId = employee.getEmpId();
		String empCname = employee.getEmpCname();
		String email = employee.getEmail();
		String userId = user.getAccountId();
		String empType = employee.getEmptype();
		String onBoard = employee.getOnBoard();
		double workedYears = getActuallyWorkYears(DateUtil.toDate(onBoard));// 實際工作年資
		String currentYear = DateUtil.getCurrentYear();

		humanService.addEmployee(employee, user);
		humanService.addLoginUser(empId, empCname, email, "1", userId, new Date());
		leaveDayService.initLeaveDays(empId, empType, workedYears, currentYear, userId);
	}

	@Transactional
	public void addDepartment (DepartmentVO dept, RedisVO user) {
		humanService.addDepartment(dept, user);
	}

	@Transactional
	public void addLocation (LocationVO location, RedisVO user) {
		humanService.addLocation(location, user);
	}

	@Transactional
	public void editDeptByDeptId (DepartmentVO dept, RedisVO user) {
		humanService.editDeptByDeptId(dept, user);
	}

	@Transactional
	public void editEmployee (EmployeeVO employee, RedisVO user) throws IOException {
		String empId = employee.getEmpId();
		String status = employee.getJobstatus();
		String userId = user.getAccountId();
		String empType = employee.getEmptype();
		String onBoard = employee.getOnBoard();
		double workedYears = getActuallyWorkYears(DateUtil.toDate(onBoard));// 實際工作年資
		String currentYear = DateUtil.getCurrentYear();

		humanService.editEmployee(employee, user);
		humanService.editLoginUserStatus(empId, status, userId, new Date());
		
		if (!isLeaveDayUsed(employee)) {
			leaveDayService.deleteLeaveDByEmpId(empId);
			leaveDayService.initLeaveDays(empId, empType, workedYears, currentYear, userId);
		}
	}

	@Transactional
	public void editLocation (LocationVO location, RedisVO user) {
		humanService.editLocation(location, user);
	}

	@Transactional
	public String haveChildDept (List<DepartmentVO> depts) throws IOException {
		return humanService.haveChildDept(depts);
	}

	@Transactional
	public boolean isLeaveDayUsed (EmployeeVO employee) {
		String empId = employee.getEmpId();
		EmployeeVO emp = leaveDayService.findByAction(empId, "U");
		
		return emp == null ? false : true;
	}

	// 取得實際工作天數( 日轉年 )
	private double getActuallyWorkYears (Date onBoard) {
		Date halfYear = DateUtil.getHalfYear();
		Date lastDay = DateUtil.getLastDateOfYear();
		String currentYear = DateUtil.getCurrentYear();
		String year = DateUtil.getYear(onBoard);

		double minusDays = 0;
		if (StringUtils.equals(year, currentYear)) {// 若為今年度到職
			// 未達半年( 含06/30 )則以半年方式給假, 否則以0年方式給假
			minusDays = (halfYear.compareTo(onBoard) > 0) ? 0.5 : 0;
		} else {// 若不為今年度到職則直接以全年度方式給假
			minusDays = DateUtil.minus(onBoard, lastDay) / 360;
		}
		
		DecimalFormat df = new DecimalFormat("#.#");
		String str = df.format(minusDays);
		
		return Double.valueOf(str);
	}

}