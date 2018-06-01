package com.elitech.human.resource.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elitech.human.resource.constant.Resources;
import com.elitech.human.resource.dao.jdbc.JdbcDAO;
import com.elitech.human.resource.dao.jdbc.criteria.Conditions;
import com.elitech.human.resource.dao.jpa.DepartmentRepo;
import com.elitech.human.resource.dao.jpa.EmployeeRepo;
import com.elitech.human.resource.dao.jpa.LocationRepo;
import com.elitech.human.resource.dao.jpa.LoginUserRepo;
import com.elitech.human.resource.pojo.Department;
import com.elitech.human.resource.pojo.Employee;
import com.elitech.human.resource.pojo.Location;
import com.elitech.human.resource.pojo.LoginUser;
import com.elitech.human.resource.util.BeanUtil;
import com.elitech.human.resource.util.DateUtil;
import com.elitech.human.resource.util.ResourceUtil;
import com.elitech.human.resource.vo.form.dept.DepartmentVO;
import com.elitech.human.resource.vo.form.employee.EmployeeVO;
import com.elitech.human.resource.vo.form.location.LocationVO;
import com.elitech.human.resource.vo.redis.RedisVO;

/**
 * 
 * @create by Adam
 */
@Service
public class HumanService {
	
	@Autowired
	private JdbcDAO jdbcDao;
	
	@Autowired
	private DepartmentRepo departmentRepo;
	
	@Autowired
	private LocationRepo locationRepo;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private LoginUserRepo loginUserRepo;

	public EmployeeVO findEmployee (String empId) {
		Employee pojo = employeeRepo.findOne(empId);
		
		if (pojo == null) {return null;}
		
		EmployeeVO employee = new EmployeeVO();
		BeanUtils.copyProperties(pojo, employee);
		
		return employee;
	}

	public DepartmentVO findDepartment (String departmentId) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_DEPARTMENT, "FIND_DEPT_LIST");
		
		Conditions conditions = new Conditions(true);
		conditions.equal("T1.department_id", departmentId);
		conditions.done();
		sqlText = conditions.get(sqlText);
		
		List<DepartmentVO> depts = jdbcDao.queryForList(sqlText, DepartmentVO.class);
		
		return CollectionUtils.isEmpty(depts) ? null : depts.get(0);
	}

	public DepartmentVO findDeptManager (String deptManger) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_DEPARTMENT, "FIND_DEPT_LIST");
		
		Conditions conditions = new Conditions(true);
		conditions.equal("T1.department_manager", deptManger);
		conditions.done();
		sqlText = conditions.get(sqlText);
		
		List<DepartmentVO> depts = jdbcDao.queryForList(sqlText, DepartmentVO.class);
		
		return CollectionUtils.isEmpty(depts) ? null : depts.get(0);
	}

	public List<EmployeeVO> findEmployees (EmployeeVO employee) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_EMPLOYEE, "FIND_EMP_LIST");
		String empId = employee.getEmpId();
		String emptype = employee.getEmptype();
		String jobstatus = employee.getJobstatus();
		String empCname = employee.getEmpCname();
		String departmentId = employee.getDepartmentId();
		String locationId = employee.getLocationId();

		Conditions conditions;
		if (StringUtils.isBlank(empId) && StringUtils.isBlank(emptype) && 
				StringUtils.isBlank(jobstatus) && StringUtils.isBlank(empCname) && 
				StringUtils.isBlank(departmentId) && StringUtils.isBlank(locationId)) {// 預設撈全部
			conditions = new Conditions();
			conditions.done();
		} else {
			conditions = new Conditions(true);
			
			if (StringUtils.isNotBlank(empId)) {
				conditions.equal("T1.emp_id", empId);
			}
			if (StringUtils.isNotBlank(emptype)) {
				conditions.equal("T1.emptype", emptype);
			}
			if (StringUtils.isNotBlank(jobstatus)) {
				conditions.equal("T1.jobstatus", jobstatus);
			}
			if (StringUtils.isNotBlank(empCname)) {
				conditions.like("T1.emp_cname", empCname);
			}
			if (StringUtils.isNotBlank(departmentId)) {
				conditions.equal("T1.department_id", departmentId);
			}
			if (StringUtils.isNotBlank(locationId)) {
				conditions.equal("T1.location_id", locationId);
			}
			
			conditions.and();
		}
		sqlText = conditions.get(sqlText);
		
		return jdbcDao.queryForList(sqlText, EmployeeVO.class);
	}

	public List<DepartmentVO> findDepartments (DepartmentVO dept) throws Exception {
		String sqlText = ResourceUtil.get(Resources.SQL_DEPARTMENT, "FIND_DEPT_LIST");
		String departmentId = dept.getDepartmentId();
		String departmentName = dept.getDepartmentName();
		String pDepartment = dept.getParentDepartmentId();

		Conditions conditions;
		if (StringUtils.isBlank(departmentId) && StringUtils.isBlank(departmentName) && StringUtils.isBlank(pDepartment)) {// 預設撈全部
			conditions = new Conditions();
			conditions.done();
		} else {
			conditions = new Conditions(true);
			if (StringUtils.isNotBlank(departmentId)) {
				conditions.like("T1.department_id", departmentId);
			}
			if (StringUtils.isNotBlank(departmentName)) {
				conditions.like("T1.department_name", departmentName);
			}
			if (StringUtils.isNotBlank(pDepartment)) {
				conditions.like("T1.parent_department_id", pDepartment);
			}
			conditions.and();
		}
		sqlText = conditions.get(sqlText);
		
		return jdbcDao.queryForList(sqlText, DepartmentVO.class);
	}

	public List<DepartmentVO> findAllDepartments () throws Exception {
		List<Department> pojos = departmentRepo.findAll();
		return BeanUtil.copyList(pojos, DepartmentVO.class);
	}

	public List<DepartmentVO> findOtherDepartments (DepartmentVO dept) throws Exception {
		String id = dept.getDepartmentId();
		List<Department> pojos = departmentRepo.findAll(id);
		
		return BeanUtil.copyList(pojos, DepartmentVO.class);
	}

	public List<LocationVO> findLocations (LocationVO location) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_LOCATION, "FIND_LOCATION_LIST");
		String locationId = location.getLocationId();
		String locationName = location.getLocationName();
		String sales = location.getSales();
		String workingTime = location.getWorkingTime();
		String offTime = location.getOffTime();
		double lunchTime = location.getLunchTime();

		Conditions conditions;
		if (StringUtils.isBlank(locationId) && StringUtils.isBlank(locationName) && 
				StringUtils.isBlank(sales) && StringUtils.isBlank(workingTime) && 
				StringUtils.isBlank(offTime) && lunchTime <= 0) {// 預設撈全部
			conditions = new Conditions();
			conditions.done();
		} else {
			conditions = new Conditions(true);
			if (StringUtils.isNotBlank(locationId)) {
				conditions.like("T1.location_id", locationId);
			}
			if (StringUtils.isNotBlank(locationName)) {
				conditions.like("T1.location_name", locationName);
			}
			if (StringUtils.isNotBlank(sales)) {
				conditions.like("T1.sales", sales);
			}
			if (StringUtils.isNotBlank(workingTime)) {
				conditions.equal("T1.working_time", workingTime);
			}
			if (StringUtils.isNotBlank(offTime)) {
				conditions.equal("T1.off_time", offTime);
			}
			if (lunchTime > 0) {
				conditions.equal("T1.lunch_time", String.valueOf(lunchTime));
			}
			conditions.and();
			conditions.done();
		}
		sqlText = conditions.get(sqlText);
		
		return jdbcDao.queryForList(sqlText, LocationVO.class);
	}

	public LocationVO findLocation (String locationId) {
		Location pojo = locationRepo.findOne(locationId);
		
		if (pojo == null) {return null;}
		
		LocationVO location = new LocationVO();
		BeanUtils.copyProperties(pojo, location);
		
		return location;
	}

	public LocationVO findLocationByLocationName (String locationName) {
		Location pojo = locationRepo.findOneByLocationName(locationName);
		
		if (pojo == null) {return null;}
		
		LocationVO location = new LocationVO();
		BeanUtils.copyProperties(pojo, location);
		
		return location;
	}

	public List<LocationVO> findAllSales () throws Exception {
		List<Location> pojos = locationRepo.findAll();
		return BeanUtil.copyList(pojos, LocationVO.class);
	}

	public List<DepartmentVO> findEmpExceptSelf (String empId) throws Exception {
		List<Employee> pojos = employeeRepo.findOtherEmployees(empId);
		return BeanUtil.copyList(pojos, DepartmentVO.class);
	}

	public void deleteDepartmentBatch (List<DepartmentVO> depts) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_DEPARTMENT, "DELETE_DEPARTMENT");

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (DepartmentVO dept : depts) {
			Map<String, Object> map = (Map<String, Object>) BeanUtil.fromObj(dept);
			dataList.add(map);
		}
		
		jdbcDao.updateBatch(sqlText, dataList);
	}

	public void deleteEmployeeBatch (List<EmployeeVO> employees) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_EMPLOYEE, "DELETE_EMPLOYEE");

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (EmployeeVO employee : employees) {
			Map<String, Object> map = (Map<String, Object>) BeanUtil.fromObj(employee);
			dataList.add(map);
		}
		
		jdbcDao.updateBatch(sqlText, dataList);
	}

	public void deleteLocationBatch (List<LocationVO> locations) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_LOCATION, "DELETE_LOCATION");

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (LocationVO location : locations) {
			Map<String, Object> map = (Map<String, Object>) BeanUtil.fromObj(location);
			dataList.add(map);
		}
		
		jdbcDao.updateBatch(sqlText, dataList);
	}

	public void addEmployee (EmployeeVO employee, RedisVO user) {
		if (employee != null) {
			String userId = user.getAccountId();
			String onBoard = employee.getOnBoard();
			String birthday = employee.getBirthday();
			
			Employee pojo = new Employee();
			BeanUtils.copyProperties(employee, pojo);
			
			pojo.setOnBoard(DateUtil.toDate(onBoard));
			pojo.setBirthday(DateUtil.toDate(birthday));
			pojo.setCreateTime(new Date());
			pojo.setCreateUser(userId);
			
			employeeRepo.save(pojo);	
		}
	}

	public void addLoginUser (
			String empId, String empCname, String email, String status, String createUser, Date createTime) {
		LoginUser pojo = new LoginUser();
		pojo.setUserId(empId);
		pojo.setUserName(empCname);
		pojo.setEmail(email);
		pojo.setStatus(status);
		pojo.setCreateUser(createUser);
		pojo.setCreateTime(createTime);
		loginUserRepo.save(pojo);
	}

	public void addDepartment (DepartmentVO dept, RedisVO user) {
		if (dept != null) {
			String userId = user.getAccountId();
			
			Department pojo = new Department();
			BeanUtils.copyProperties(dept, pojo);
			
			pojo.setCreateTime(new Date());
			pojo.setCreateUser(userId);
			departmentRepo.save(pojo);	
		}
	}

	public void addLocation (LocationVO location, RedisVO user) {
		if (location != null) {
			String userId = user.getAccountId();
			
			Location pojo = new Location();
			BeanUtils.copyProperties(location, pojo);
			
			pojo.setCreateTime(new Date());
			pojo.setCreateUser(userId);
			locationRepo.save(pojo);
		}
	}

	public void editDeptByDeptId (DepartmentVO dept, RedisVO user) {
		String deptId = dept.getDepartmentId();
		String deptName = dept.getDepartmentName();
		String parentDeptId = dept.getParentDepartmentId();
		String deptManager = dept.getDepartmentManager();
		String modifyUser = user.getAccountId();
		Date modifyTime = new Date();
		
		departmentRepo.updateByDeptId(deptId, deptName, parentDeptId, deptManager, modifyUser, modifyTime);
	}

	public void editEmployee (EmployeeVO employee, RedisVO user) {
		String empId = employee.getEmpId();
		String emptype = employee.getEmptype();
		String jobstatus = employee.getJobstatus();
		String marriage = employee.getMarriage();
		String education = employee.getEducation();
		String schoolename = employee.getSchoolename();
		String tel = employee.getTel();
		String empCname = employee.getEmpCname();
		String empEname = employee.getEmpEname();
		String gender = employee.getGender();
		String served = employee.getServed();
		String jobtitle = employee.getJobtitle();
		String subject = employee.getSubject();
		String phone = employee.getPhone();
		String zipcode = employee.getZipcode();
		String address = employee.getAddress();
		String email = employee.getEmail();
		String companyEmail = employee.getCompanyEmail();
		String emergencycontact = employee.getEmergencycontact();
		String emergencytel = employee.getEmergencytel();
		String departmentId = employee.getDepartmentId();
		String locationId = employee.getLocationId();
		String onBoard = employee.getOnBoard();
		String birthday = employee.getBirthday();
		String modifyUser = user.getAccountId();
		Date modifyTime = new Date();
		
		employeeRepo.updateByEmpId(
				empId, emptype, jobstatus, marriage, education, schoolename, locationId, departmentId, 
				emergencytel, emergencycontact, companyEmail, email, address, zipcode, phone, subject, jobtitle, 
				served, tel, empCname, empEname, gender, DateUtil.toDate(birthday), DateUtil.toDate(onBoard), modifyUser, modifyTime);
	}

	public void editLocation (LocationVO location, RedisVO user) {
		String locationId = location.getLocationId();
		String locationName = location.getLocationName();
		String pm = location.getPm();
		String sales = location.getSales();
		String workingTime = location.getWorkingTime();
		String offTime = location.getOffTime();
		double lunchTime = location.getLunchTime();
		String modifyUser = user.getAccountId();
		Date modifyTime = new Date();
		
		locationRepo.updateByLocationId(locationId, locationName, pm, sales, workingTime, offTime, lunchTime, modifyUser, modifyTime);
	}

	public void editLoginUserStatus (String empId, String status, String modifyUser, Date modifyTime) {
		status = StringUtils.equals(status, "1") ? "1" : "0";
		loginUserRepo.updateUserStatus(empId, status, modifyUser, modifyTime);
	}

	public String haveChildDept (List<DepartmentVO> depts) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_DEPARTMENT, "FIND_DEPT_LIST");
		
		Conditions conditions = new Conditions(true);
		
		for (DepartmentVO department : depts) {
			String deptId = department.getDepartmentId();
			conditions.equal("T1.parent_department_id", deptId);
			conditions.done();
			String complete = conditions.get(sqlText);
			List<DepartmentVO> cilds = jdbcDao.queryForList(complete, DepartmentVO.class);
			
			if (!CollectionUtils.isEmpty(cilds)) {return deptId;}
		}
		
		return null;
	}
	
}

