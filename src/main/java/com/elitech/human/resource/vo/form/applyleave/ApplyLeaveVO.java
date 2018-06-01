package com.elitech.human.resource.vo.form.applyleave;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * @create by Adam
 */
public class ApplyLeaveVO {

	private String leaveNo;
	private String leavetypeId;
	private String reason;
	private Date begindate;
	private String beginhour;
	private String beginmin;
	private Date enddate;
	private String endhour;
	private String endmin;
	private long leavedays;
	private double leavehours;
	private String substitute;
	private String document;
	private String process;
	private String approveStatus;
	private Date closeTime;
	private String empId;
	private String departmentId;
	private String jobtitle;
	private String gender;
	private String empCname;
	private String jobtitleName;
	private String departmentName;
	private String workingTime;
	private String offTime;
	private String lunchTime;
	private String email;
	private String empEname;
	private String phone;
	private String attache;
	private String emptype;
	private List<Map<String, Object>> leavetypes;
	private Map<String, Object> leaveMaster;

	public String getLeaveNo () {
		return leaveNo;
	}

	public void setLeaveNo (String leaveNo) {
		this.leaveNo = leaveNo;
	}

	public String getEmpId () {
		return empId;
	}

	public void setEmpId (String empId) {
		this.empId = empId;
	}

	public String getDepartmentName () {
		return departmentName;
	}

	public void setDepartmentName (String departmentName) {
		this.departmentName = departmentName;
	}

	public String getJobtitleName () {
		return jobtitleName;
	}

	public void setJobtitleName (String jobtitleName) {
		this.jobtitleName = jobtitleName;
	}

	public String getLeavetypeId () {
		return leavetypeId;
	}

	public void setLeavetypeId (String leavetypeId) {
		this.leavetypeId = leavetypeId;
	}

	public String getReason () {
		return reason;
	}

	public void setReason (String reason) {
		this.reason = reason;
	}

	public Date getBegindate () {
		return begindate;
	}

	public void setBegindate (Date begindate) {
		this.begindate = begindate;
	}

	public String getBeginhour () {
		return beginhour;
	}

	public void setBeginhour (String beginhour) {
		this.beginhour = beginhour;
	}

	public String getBeginmin () {
		return beginmin;
	}

	public void setBeginmin (String beginmin) {
		this.beginmin = beginmin;
	}

	public Date getEnddate () {
		return enddate;
	}

	public void setEnddate (Date enddate) {
		this.enddate = enddate;
	}

	public String getEndhour () {
		return endhour;
	}

	public void setEndhour (String endhour) {
		this.endhour = endhour;
	}

	public String getEndmin () {
		return endmin;
	}

	public void setEndmin (String endmin) {
		this.endmin = endmin;
	}

	public long getLeavedays () {
		return leavedays;
	}

	public void setLeavedays (long leavedays) {
		this.leavedays = leavedays;
	}

	public double getLeavehours () {
		return leavehours;
	}

	public void setLeavehours (double leavehours) {
		this.leavehours = leavehours;
	}

	public String getSubstitute () {
		return substitute;
	}

	public void setSubstitute (String substitute) {
		this.substitute = substitute;
	}

	public String getDocument () {
		return document;
	}

	public void setDocument (String document) {
		this.document = document;
	}

	public String getProcess () {
		return process;
	}

	public void setProcess (String process) {
		this.process = process;
	}

	public String getApproveStatus () {
		return approveStatus;
	}

	public void setApproveStatus (String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public Date getCloseTime () {
		return closeTime;
	}

	public void setCloseTime (Date closeTime) {
		this.closeTime = closeTime;
	}

	public String getEmpCname () {
		return empCname;
	}

	public void setEmpCname (String empCname) {
		this.empCname = empCname;
	}

	public List<Map<String, Object>> getLeavetypes () {
		return leavetypes;
	}

	public void setLeavetypes (List<Map<String, Object>> leaveTypes) {
		this.leavetypes = leaveTypes;
	}

	public String getGender () {
		return gender;
	}

	public void setGender (String gender) {
		this.gender = gender;
	}

	public Map<String, Object> getLeaveMaster () {
		return leaveMaster;
	}

	public void setLeaveMaster (Map<String, Object> resultMap) {
		this.leaveMaster = resultMap;
	}

	public String getDepartmentId () {
		return departmentId;
	}

	public void setDepartmentId (String departmentId) {
		this.departmentId = departmentId;
	}

	public String getJobtitle () {
		return jobtitle;
	}

	public void setJobtitle (String jobtitle) {
		this.jobtitle = jobtitle;
	}

	public String getWorkingTime () {
		return workingTime;
	}

	public void setWorkingTime (String workingTime) {
		this.workingTime = workingTime;
	}

	public String getOffTime () {
		return offTime;
	}

	public void setOffTime (String offTime) {
		this.offTime = offTime;
	}

	public String getLunchTime () {
		return lunchTime;
	}

	public void setLunchTime (String lunchTime) {
		this.lunchTime = lunchTime;
	}

	public String getEmail () {
		return email;
	}

	public void setEmail (String email) {
		this.email = email;
	}

	public String getEmpEname () {
		return empEname;
	}

	public void setEmpEname (String empEname) {
		this.empEname = empEname;
	}

	public String getPhone () {
		return phone;
	}

	public void setPhone (String phone) {
		this.phone = phone;
	}

	public String getAttache () {
		return attache;
	}

	public void setAttache (String attache) {
		this.attache = attache;
	}

	public String getEmptype () {
		return emptype;
	}

	public void setEmptype (String emptype) {
		this.emptype = emptype;
	}

}
