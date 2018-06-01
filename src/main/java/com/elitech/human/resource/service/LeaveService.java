package com.elitech.human.resource.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elitech.human.resource.constant.Resources;
import com.elitech.human.resource.dao.jdbc.JdbcDAO;
import com.elitech.human.resource.dao.jdbc.criteria.Conditions;
import com.elitech.human.resource.dao.jpa.EmployeeRepo;
import com.elitech.human.resource.dao.jpa.LeaveRecordRepo;
import com.elitech.human.resource.dao.jpa.LeaveTypeRepo;
import com.elitech.human.resource.dao.jpa.ParamRepo;
import com.elitech.human.resource.dao.jpa.ProcessFlowRepo;
import com.elitech.human.resource.pojo.Employee;
import com.elitech.human.resource.pojo.Leaverecord;
import com.elitech.human.resource.pojo.Leavetype;
import com.elitech.human.resource.pojo.LeavetypeId;
import com.elitech.human.resource.pojo.Param;
import com.elitech.human.resource.pojo.ProcessFlow;
import com.elitech.human.resource.pojo.ProcessFlowId;
import com.elitech.human.resource.util.BeanUtil;
import com.elitech.human.resource.util.DateUtil;
import com.elitech.human.resource.util.MailUtil;
import com.elitech.human.resource.util.ResourceUtil;
import com.elitech.human.resource.vo.form.applyleave.ApplyLeaveVO;
import com.elitech.human.resource.vo.form.searchform.FormSearchTableVO;
import com.elitech.human.resource.vo.form.signprocess.SignProcessTableVO;

/**
 * 
 * @create by Adam
 */
@Service
public class LeaveService {
	
	@Autowired
	private MailUtil mailUtil;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private LeaveRecordRepo leaveRecordRepo;
	
	@Autowired
	private ProcessFlowRepo processFlowRepo;
	
	@Autowired
	private ParamRepo paramRepo;
	
	@Autowired
	private LeaveTypeRepo leavetypeRepo;
	
	@Autowired
	private JdbcDAO jdbcDao;

	public ApplyLeaveVO findEmployee (String empId) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_EMPLOYEE, "FIND_EMP");
		
		Conditions conditions = new Conditions(true);
		conditions.equal("T1.EMP_ID", empId);
		conditions.done();
		
		sqlText = conditions.get(sqlText);
		
		return jdbcDao.queryForBean(sqlText, ApplyLeaveVO.class);
	}

	public List<ApplyLeaveVO> findOtherEmployees (String empId) throws Exception {
		List<Employee> pojos = employeeRepo.findOtherEmployees(empId);
		return BeanUtil.copyList(pojos, ApplyLeaveVO.class);
	}

	public long findExpire (String leavetypeId, String emptype) {
		LeavetypeId id = new LeavetypeId();
		id.setEmptype(emptype);
		id.setLeavetypeId(leavetypeId);
		Leavetype pojo = leavetypeRepo.findOne(id);
		
		return pojo == null ? null : pojo.getExpire();
	}

	public List<Map<String, Object>> findLeaveTypesByGender (String gender, String emptype) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_LEAVE_TYPE, "FIND_LEAVE_TYPE_BY_GENDER");
		
		Conditions conditions;
		if (StringUtils.equals(gender, "2")) {
			conditions = new Conditions(true);
			conditions.equal("T1.EMPTYPE", emptype);
			conditions.done();
		} else {
			conditions = new Conditions(true);
			conditions.leftPT();
			conditions.unEqual("T1.GENDER", "2");
			conditions.isNull("T1.GENDER");
			conditions.or();
			conditions.RightPT(Conditions.AND);
			conditions.equal("T1.EMPTYPE", emptype);
			conditions.done();
		}
		sqlText = conditions.get(sqlText);
		
		return jdbcDao.queryForList(sqlText);
	}

	public String findHrEmpId (String key) {
		List<Param> pojos = paramRepo.findByKey(key);
		
		if (CollectionUtils.isEmpty(pojos)) {
			return null;
		}
		
		Param pojo = pojos.get(0);
		String value = pojo.getId().getValue();
		
		return value;
	}

	public String findProcessCreator (String leaveNo) {
		ProcessFlow pojo = processFlowRepo.findFirstFlow(leaveNo);
		return pojo == null ? null : pojo.getId().getEmpId();
	}

	public List<FormSearchTableVO> findLeaveRecords (String empId) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_LEAVE_RECORD, "FIND_LEAVE_RECORD_LIST");
		
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("empId", empId);
		
		List<FormSearchTableVO> dataList = jdbcDao.queryForList(sqlText, dataMap, FormSearchTableVO.class);
		for (FormSearchTableVO dataTableVO : dataList) {
			String begindate = dataTableVO.getBegindate();
			String beginhour = dataTableVO.getBeginhour();
			String beginmin = dataTableVO.getBeginmin();
			String enddate = dataTableVO.getEnddate();
			String endhour = dataTableVO.getEndhour();
			String endmin = dataTableVO.getEndmin();
			String createTime = dataTableVO.getCreateTime();
			
			String[] sp = StringUtils.split(createTime, ".");
			String begin = begindate + " " + beginhour + ":" + beginmin;
			String end = enddate + " " + endhour + ":" + endmin;
			
			dataTableVO.setCreateTime(sp[0]);
			dataTableVO.setBegin(begin);
			dataTableVO.setEnd(end);
		}
		
		return dataList;
	}

	public List<SignProcessTableVO> findSigningLeaveRecords (String empId) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_LEAVE_RECORD, "FIND_SIGNING_RECORD_LIST");
		
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("empId", empId);
		
		List<SignProcessTableVO> dataList = jdbcDao.queryForList(sqlText, dataMap, SignProcessTableVO.class);
		for (SignProcessTableVO dataTableVO : dataList) {
			String begindate = dataTableVO.getBegindate();
			String beginhour = dataTableVO.getBeginhour();
			String beginmin = dataTableVO.getBeginmin();
			String enddate = dataTableVO.getEnddate();
			String endhour = dataTableVO.getEndhour();
			String endmin = dataTableVO.getEndmin();
			String createTime = dataTableVO.getCreateTime();
			String[] sp = StringUtils.split(createTime, ".");
			String begin = begindate + " " + beginhour + ":" + beginmin;
			String end = enddate + " " + endhour + ":" + endmin;
			
			dataTableVO.setCreateTime(sp[0]);
			dataTableVO.setBegin(begin);
			dataTableVO.setEnd(end);
		}
		
		return dataList;
	}

	public String findDocument (String leaveNo) {
		Leaverecord pojo = leaveRecordRepo.findOne(leaveNo);
		String document = pojo.getDocument();
		
		return document;
	}

	public long findLastFlowKey (String empId, String leaveNo) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_PROCESS_FLOW, "FIND_LAST_FLOW");
		
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("empId", empId);
		dataMap.put("leaveNo", leaveNo);
		dataMap = jdbcDao.queryForMap(sqlText, dataMap);
		
		return MapUtils.getLongValue(dataMap, "seq");
	}

	public String findPreFlowExecutor (String leaveNo, String process, String substitute, String departmentId) {
		
		
		return processFlowRepo.findPreFlowEmpId(1, leaveNo);
	}

	public void addLeaveRecord (ApplyLeaveVO applyLeave) throws IOException {
		String today = DateUtil.fromDate(new Date(), DateUtil.DATE_1);
		String leaveNo = leaveRecordRepo.findLastLeaveNo(today);
		
		if (StringUtils.isEmpty(leaveNo)) {
			leaveNo = DateUtil.fromDate(new Date(), DateUtil.DATE_1) + "0000";
		} else {
			leaveNo = String.valueOf(Long.valueOf(leaveNo) + 1);
		}
		applyLeave.setLeaveNo(leaveNo);
		
		String sqlText = ResourceUtil.get(Resources.SQL_LEAVE_RECORD, "INSERT_LEAVE_RECORD");
		String empId = applyLeave.getEmpId();
		Date beginDate = applyLeave.getBegindate();
		Date endDate = applyLeave.getEnddate();
		
		Map<String, Object> dataMap = (Map<String, Object>) BeanUtil.fromObj(applyLeave);
		dataMap.put("createUser", empId);
		dataMap.put("createTime", new Date());
		dataMap.put("begindate", beginDate);
		dataMap.put("enddate", endDate);
		
		jdbcDao.update(sqlText, dataMap);
	}

	public void addProcessFlow (String empId, String leaveNo, String approveStatus) {
		Long seq = processFlowRepo.findLastSeq(leaveNo);
		seq = seq == null ? 0 : seq + 1;
		
		ProcessFlowId id = new ProcessFlowId();
		id.setEmpId(empId);
		id.setLeaveNo(leaveNo);
		id.setSeq(seq);
		
		ProcessFlow pojo = new ProcessFlow();
		pojo.setId(id);
		pojo.setApproveStatus(approveStatus);
		pojo.setTransDate(new Date());
		
		processFlowRepo.save(pojo);
		
		String email = employeeRepo.findOne(empId).getCompanyEmail();
		mailUtil.sendFlowChangeEvent(email);
	}

	public void editLeaveRecord (ApplyLeaveVO applyLeave) {
		String empId = applyLeave.getEmpId();
		String leaveNo = applyLeave.getLeaveNo();
		String process = applyLeave.getProcess();
		String approveStatus = applyLeave.getApproveStatus();
		
		leaveRecordRepo.updateByLeaveNo(leaveNo, process, approveStatus, empId, new Date());
	}

	public void editLeaveRecord (
			String leaveNo, Date begindate, String beginhour, String beginmin, Date enddate, 
			String endhour, String endmin, long leavedays, double leavehours, String substitute, 
			String approveStatus, String reason, String document, String userId, String nextProcess) {
		leaveRecordRepo.updateByLeaveNo(
				leaveNo, begindate, beginhour, beginmin, enddate, endhour, endmin, leavedays, 
				leavehours, substitute, nextProcess, approveStatus, reason, document, userId, new Date());
	}

	public void editLeaveRecord (String leaveNo, String process, String approveStatus, String userId) {
		leaveRecordRepo.updateByLeaveNo(leaveNo, process, approveStatus, userId, new Date());
	}

	public void editLeaveRecord (String leaveNo, String process, String approveStatus, Date closeTime, String userId) {
		leaveRecordRepo.updateByLeaveNo(leaveNo, process, approveStatus, closeTime, userId, new Date());
	}

	public void editLeaveRecord (String leaveNo, String approveStatus, String userId) throws IllegalAccessException, InvocationTargetException {
		leaveRecordRepo.updateByLeaveNo(leaveNo, approveStatus, userId, new Date());
	}

	public void editProcessFlow (long seq, String empId, String leaveNo, String approveStatus, String note) {
		processFlowRepo.updateByPrimaryKey(seq, empId, leaveNo, approveStatus, note);
	}

	public boolean isEditable (String empId, String leaveNo) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_PROCESS_FLOW, "IS_EDITABLE");
		
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("empId", empId);
		dataMap.put("leaveNo", leaveNo);
		dataMap = jdbcDao.queryForMap(sqlText, dataMap);
		
		int count = MapUtils.getInteger(dataMap, "count");
		
		return count > 0 ? true : false;
	}

	// TODO 待釐清
	public boolean isEditable2 (String empId, String leaveNo) throws IOException {
		String sqlText = ResourceUtil.get(Resources.SQL_PROCESS_FLOW, "IS_EDITABLE");
		
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("empId", empId);
		dataMap.put("leaveNo", leaveNo);
		List<Map<String, Object>> dataList = jdbcDao.queryForList(sqlText, dataMap);
		
		dataMap = dataList.get(0);
		int count1 = MapUtils.getInteger(dataMap, "count");// 檢查是否被駁回到自己身上

		dataMap = dataList.get(1);
		int count2 = MapUtils.getInteger(dataMap, "count");// 檢查是否為未被簽核過之假單
		
		return (count1 > 0 || count2 <= 2) ? true : false;
	}
	
}

