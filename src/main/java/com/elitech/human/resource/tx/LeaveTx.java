package com.elitech.human.resource.tx;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elitech.human.resource.service.HumanService;
import com.elitech.human.resource.service.LeaveDayService;
import com.elitech.human.resource.service.LeaveService;
import com.elitech.human.resource.service.SettingService;
import com.elitech.human.resource.util.DateUtil;
import com.elitech.human.resource.util.MailUtil;
import com.elitech.human.resource.vo.form.applyleave.ApplyLeaveVO;
import com.elitech.human.resource.vo.form.dept.DepartmentVO;
import com.elitech.human.resource.vo.form.employee.EmployeeVO;
import com.elitech.human.resource.vo.form.searchform.FormSearchDeleteVO;
import com.elitech.human.resource.vo.form.searchform.FormSearchEditVO;
import com.elitech.human.resource.vo.form.searchform.FormSearchTableVO;
import com.elitech.human.resource.vo.form.signprocess.SignProcessEditVO;
import com.elitech.human.resource.vo.form.signprocess.SignProcessTableVO;
import com.elitech.human.resource.vo.other.ParamVO;
import com.elitech.human.resource.vo.redis.RedisVO;

/**
 * 
 * @create by Adam
 */
@Service
public class LeaveTx {
	
	@Autowired
	private MailUtil mailUtil;
	
	@Autowired
	private HumanService humanService;
	
	@Autowired
	private LeaveService leaveService;
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private LeaveDayService leaveDayService;
	
	@Transactional
	public ApplyLeaveVO findEmployee (String empId) throws IOException {
		return leaveService.findEmployee(empId);
	}

	@Transactional
	public List<Map<String, Object>> findLeaveTypesByGender (String gender, String emptype) throws IOException {
		return leaveService.findLeaveTypesByGender(gender, emptype);
	}

	@Transactional
	public Map<String, Object> findLeaveMaster (String empId, String currentYear) throws IOException {
		return leaveDayService.findLeaveMaster(empId, currentYear);
	}

	@Transactional
	public ApplyLeaveVO findLeaveDetail (String empId, String leavetypeId) {
		return leaveDayService.findLeaveDetail(empId, leavetypeId);
	}

	@Transactional
	public List<ApplyLeaveVO> findOtherEmployees (String empId) throws Exception {
		return leaveService.findOtherEmployees(empId);
	}

	@Transactional
	public List<FormSearchTableVO> findLeaveRecords (String empId) throws IOException {
		return leaveService.findLeaveRecords(empId);
	}

	@Transactional
	public List<SignProcessTableVO> findSigningLeaveRecords (String empId) throws IOException {
		return leaveService.findSigningLeaveRecords(empId);
	}

	@Transactional
	public String findDocument (String leaveNo) {
		return leaveService.findDocument(leaveNo);
	}

	@Transactional
	public long findExpire (String leavetypeId, String emptype) {
		return leaveService.findExpire(leavetypeId, emptype);
	}

	@Transactional
	public void addLeaveRecord (ApplyLeaveVO applyLeave) throws IOException {
		leaveService.addLeaveRecord(applyLeave);
		
		String empId = applyLeave.getEmpId();
		String leaveNo = applyLeave.getLeaveNo();
		String leavetypeId = applyLeave.getLeavetypeId();
		String years = DateUtil.getCurrentYear();
		long leaveDays = applyLeave.getLeavedays();
		double leaveHours = applyLeave.getLeavehours();
		String approveStatus = applyLeave.getApproveStatus();
		
		leaveDayService.minusHours(empId, leaveNo, leavetypeId, Long.valueOf(years), leaveDays, leaveHours);
		leaveService.addProcessFlow(empId, leaveNo, approveStatus);
	}

	@Transactional
	public void addPaidLeave (List<Map<String, Object>> leaveList, RedisVO user) throws NumberFormatException, IOException {
		List<ParamVO> params = settingService.findParamByKey("compensatory_leave");
		ParamVO param = params.get(0);
		
		for (Map<String, Object> paidLeave : leaveList) {
			String empId = MapUtils.getString(paidLeave, "員工編號");
			String years = MapUtils.getString(paidLeave, "年度");
			String transDate = MapUtils.getString(paidLeave, "發生日期");
			String note = MapUtils.getString(paidLeave, "事由");
			String hours = MapUtils.getString(paidLeave, "加班時數");
			String leavetypeId = param.getValue(); 
			String createUser = user.getAccountId();
			
			EmployeeVO employee = humanService.findEmployee(empId);
			if (employee == null) {continue;}// 如果不是已存在的員工則不新增補休相關時數 
			
			leaveDayService.addHours(
					empId, "", leavetypeId, Long.valueOf(years), createUser, 
					DateUtil.toDate(transDate), note, Double.valueOf(hours));
		}
	}

	@Transactional
	public void editLeaveRecord (ApplyLeaveVO applyLeave) throws IOException {
		leaveService.editLeaveRecord(applyLeave);

		String leaveNo = applyLeave.getLeaveNo();
		String process = applyLeave.getProcess();
		String substitute = applyLeave.getSubstitute();
		String departmentId = applyLeave.getDepartmentId();
		String empId = getFlowExecutorEmpId(leaveNo, process, substitute, departmentId);
		
		String approveStatus = applyLeave.getApproveStatus();
		leaveService.addProcessFlow(empId, leaveNo, approveStatus);
	}

	@Transactional
	public void editLeaveRecord (FormSearchEditVO formSearch) throws IOException {
		String empId = formSearch.getEmpId();
		String leaveNo = formSearch.getLeaveNo();
		String begindate = formSearch.getBegindate();
		String beginhour = formSearch.getBeginhour();
		String beginmin = formSearch.getBeginmin();
		String enddate = formSearch.getEnddate();
		String endhour = formSearch.getEndhour();
		String endmin = formSearch.getEndmin();
		long leavedays = formSearch.getLeavedays();
		double leavehours = formSearch.getLeavehours();
		String substitute = formSearch.getSubstitute();
		String document = formSearch.getDocument();
		String leavetypeId = formSearch.getLeavetypeId();
		String years = DateUtil.getCurrentYear();
		String reason = formSearch.getReason();
		String emptype = formSearch.getEmptype();
		String process = formSearch.getProcess();
		String nextProcess = getNextProcess(process, emptype, leavedays, substitute);

		String approveStatus = "sign";
		leaveService.editLeaveRecord(
				leaveNo, DateUtil.toDate(begindate), beginhour, beginmin, DateUtil.toDate(enddate), 
				endhour, endmin, leavedays, leavehours, substitute, 
				approveStatus, reason, document, empId, nextProcess);
		
		leaveDayService.editHours(empId, leaveNo, leavetypeId, Long.valueOf(years), leavedays, leavehours);

		approveStatus = "approve";
		long seq = leaveService.findLastFlowKey(empId, leaveNo);
		leaveService.editProcessFlow(seq, empId, leaveNo, approveStatus, "");

		String departmentId = formSearch.getDepartmentId();
		empId = getFlowExecutorEmpId(leaveNo, nextProcess, substitute, departmentId);
		leaveService.addProcessFlow(empId, leaveNo, "sign");
	}

	@Transactional
	public void signPass (SignProcessEditVO signProcess) throws IOException, IllegalAccessException, InvocationTargetException {
		String leaveNo = signProcess.getLeaveNo();
		String process = signProcess.getProcess();
		String substitute = signProcess.getSubstitute();
		String emptype = signProcess.getEmptype();
		String departmentId = signProcess.getDepartmentId();
		long leavedays = signProcess.getLeavedays();
		
		if (StringUtils.equals(process, "hr")) {
			closeFlow(leaveNo, process);
			return;
		}
		
		nextFlow(leaveNo, departmentId, substitute, process, emptype, leavedays);
	}

	@Transactional
	public void signFailed (SignProcessEditVO signProcess) throws IOException, IllegalAccessException, InvocationTargetException {
		String leaveNo = signProcess.getLeaveNo();
		String emptype = signProcess.getEmptype();
		long leavedays = signProcess.getLeavedays();
		String process = signProcess.getProcess();
		String note = signProcess.getNote();
		String substitute = signProcess.getSubstitute();
		String departmentId = signProcess.getDepartmentId();
		String empId = getFlowExecutorEmpId(leaveNo, process, substitute, departmentId);
		
		String preProcess = getPreProcess(substitute, process, emptype, leavedays);
		String approveStatus = "reject";
		leaveService.editLeaveRecord(leaveNo, preProcess, approveStatus, empId);

		empId = getFlowExecutorEmpId(leaveNo, process, substitute, departmentId);
		long seq = leaveService.findLastFlowKey(empId, leaveNo);
		leaveService.editProcessFlow(seq, empId, leaveNo, approveStatus, note);

		empId = getFlowExecutorEmpId(leaveNo, preProcess, substitute, departmentId);
		approveStatus = getFailedApproveStatus(leaveNo, empId);
		leaveService.addProcessFlow(empId, leaveNo, approveStatus);
	}

	@Transactional
	public void deleteLeaveRecord (FormSearchDeleteVO deleteVO) throws NumberFormatException, IOException {
		String empId = deleteVO.getEmpId();
		String leaveNo = deleteVO.getLeaveNo();
		String leavetypeId = deleteVO.getLeavetypeId();
		String years = DateUtil.getCurrentYear();
		String process = "close";
		String approveStatus = "cancel";
		
		leaveService.editLeaveRecord(leaveNo, process, approveStatus, new Date(), empId);
		leaveDayService.addHours(empId, leaveNo, leavetypeId, Long.valueOf(years), empId, new Date(), "", 0);

		long seq = leaveService.findLastFlowKey(empId, leaveNo);
		approveStatus = "close";
		leaveService.editProcessFlow(seq, empId, leaveNo, approveStatus, "");
	}

	@Transactional
	public boolean isEditable (String empId, String leaveNo) throws IOException {
		return leaveService.isEditable(empId, leaveNo);
	}

	private void closeFlow (String leaveNo, String process) throws IOException {
		String empId = leaveService.findHrEmpId(process + "_empid");
		String approveStatus = "close";
		process = "close";
		leaveService.editLeaveRecord(leaveNo, process, approveStatus, new Date(), empId);

		long seq = leaveService.findLastFlowKey(empId, leaveNo);
		leaveService.editProcessFlow(seq, empId, leaveNo, approveStatus, "");
		
		empId = leaveService.findProcessCreator(leaveNo);
		String email = humanService.findEmployee(empId).getCompanyEmail();
		mailUtil.sendCloseFlowEvent(email);
	}

	private void nextFlow (String leaveNo, String departmentId, String substitute, String process, String emptype, long leavedays) throws IOException {
		String approveStatus = "approve";
		String empId = getFlowExecutorEmpId(leaveNo, process, substitute, departmentId);
		
		long seq = leaveService.findLastFlowKey(empId, leaveNo);
		leaveService.editProcessFlow(seq, empId, leaveNo, approveStatus, "");
		
		String nextProcess = getNextProcess(process, emptype, leavedays, substitute);
		approveStatus = "sign";
		empId = getFlowExecutorEmpId(leaveNo, nextProcess, substitute, departmentId);
		leaveService.editLeaveRecord(leaveNo, nextProcess, approveStatus, empId);
		leaveService.addProcessFlow(empId, leaveNo, approveStatus);
	}

	private String getFlowExecutorEmpId (String leaveNo, String process, String substitute, String departmentId) throws IOException {
		String empId = "";
		switch (process) {
			case "hr":
				empId = leaveService.findHrEmpId(process + "_empid");
				break;

			case "manager":
				DepartmentVO department = humanService.findDepartment(departmentId);
				empId = department.getDepartmentManager();
				
				break;

			case "substitute":
				empId = substitute;
				break;

			case "new":
				empId = leaveService.findProcessCreator(leaveNo);
				break;
		}
		
		return empId;
	}

	private String getNextProcess (String process, String emptype, long leavedays, String substitute) {
		String nextProcess = "";
		switch (emptype) {
			case "1":// 外派
				if (leavedays > 3) {
					if (StringUtils.equals(process, "new")) {
						nextProcess = StringUtils.isBlank(substitute) ? "manager" : "substitute";
					} else {
						nextProcess = StringUtils.equals(process, "manager") ? "hr" : "manager";
					}
				} else {
					if (StringUtils.equals(process, "substitute")) {
						nextProcess = "hr";
					} else {
						nextProcess = StringUtils.isBlank(substitute) ? "hr" : "substitute";
					}
				}
				
				break;
				
			case "2":// 內部
				if (StringUtils.equals(process, "new")) {
					nextProcess = StringUtils.isBlank(substitute) ? "manager" : "substitute";
				} else {
					nextProcess = StringUtils.equals(process, "manager") ? "hr" : "manager";
				}
				
				break;
		}
		
		return nextProcess;
	}

	private String getPreProcess (String substitute, String process, String emptype, double leavedays) {
		String preProcess = "";
		
		switch (emptype) {
			case "1":// 外派
				if (leavedays > 3) {
					if (StringUtils.equals(process, "hr")) {
						preProcess = "manager";
					} else if (StringUtils.equals(process, "manager")) {
						preProcess = StringUtils.isBlank(substitute) ? "new" : "substitute";
					} else {
						preProcess = "new";
					}
				} else {
					if (StringUtils.equals(process, "hr")) {
						preProcess = StringUtils.isBlank(substitute) ? "new" : "substitute";
					} else {
						preProcess = "new";
					}
				}
				
				break;
				
			case "2":// 內部
				if (StringUtils.equals(process, "hr")) {
					preProcess = "manager";
				} else if (StringUtils.equals(process, "manager")) {
					preProcess = StringUtils.isBlank(substitute) ? "new" : "substitute";
				} else {
					preProcess = "new";
				}
				
				break;
		}
		
		return preProcess;
	}

	private String getFailedApproveStatus (String leaveNo, String empId) {
		String creator = leaveService.findProcessCreator(leaveNo);
		return StringUtils.equals(empId, creator) ? "" : "sign";
	}

}


