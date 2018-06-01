package com.elitech.human.resource.vo.form.dept;

/**
 * 
 * @create by Adam
 */
public class DepartmentVO {

	private String empId;
	private String empCname;
	private String empEname;
	private String email;
	private String phone;
	private String departmentId;
	private String departmentName;
	private String parentDepartmentId;
	private String departmentManager;

	public String getDepartmentId () {
		return departmentId;
	}

	public void setDepartmentId (String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName () {
		return departmentName;
	}

	public void setDepartmentName (String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentManager () {
		return departmentManager;
	}

	public void setDepartmentManager (String departmentManager) {
		this.departmentManager = departmentManager;
	}

	public String getParentDepartmentId () {
		return parentDepartmentId;
	}

	public void setParentDepartmentId (String parentDepartmentId) {
		this.parentDepartmentId = parentDepartmentId;
	}

	public String getEmpId () {
		return empId;
	}

	public void setEmpId (String empId) {
		this.empId = empId;
	}

	public String getEmpCname () {
		return empCname;
	}

	public void setEmpCname (String empCname) {
		this.empCname = empCname;
	}

	public String getEmpEname () {
		return empEname;
	}

	public void setEmpEname (String empEname) {
		this.empEname = empEname;
	}

	public String getEmail () {
		return email;
	}

	public void setEmail (String email) {
		this.email = email;
	}

	public String getPhone () {
		return phone;
	}

	public void setPhone (String phone) {
		this.phone = phone;
	}

}
