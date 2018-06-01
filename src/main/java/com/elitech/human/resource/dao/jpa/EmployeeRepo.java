package com.elitech.human.resource.dao.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elitech.human.resource.pojo.Employee;

/**
 * 
 * @create by Adam
 */
public interface EmployeeRepo extends JpaRepository<Employee, String> {

	@Modifying
	@Query("UPDATE Employee T1 SET " + 
			"T1.emptype = :emptype, " + 
			"T1.jobstatus = :jobstatus, " + 
			"T1.marriage = :marriage, " + 
			"T1.education = :education, " + 
			"T1.schoolename = :schoolename, " + 
			"T1.locationId = :locationId, " + 
			"T1.departmentId = :departmentId, " + 
			"T1.emergencytel = :emergencytel, " + 
			"T1.emergencycontact = :emergencycontact, " + 
			"T1.companyEmail = :companyEmail, " + 
			"T1.email = :email, " + 
			"T1.address = :address, " + 
			"T1.zipcode = :zipcode, " + 
			"T1.phone = :phone, " + 
			"T1.subject = :subject, " + 
			"T1.jobtitle = :jobtitle, " + 
			"T1.served = :served, " + 
			"T1.tel = :tel, " +
			"T1.empCname = :empCname, " + 
			"T1.empEname = :empEname, " + 
			"T1.gender = :gender, " + 
			"T1.birthday = :birthday, " + 
			"T1.onBoard = :onBoard, " +
			"T1.modifyUser = :modifyUser, " + 
			"T1.modifyTime = :modifyTime WHERE " + 
			"T1.empId = :empId")
	public void updateByEmpId (
			@Param("empId") String empId, 
			@Param("emptype") String emptype, 
			@Param("jobstatus") String jobstatus, 
			@Param("marriage") String marriage, 
			@Param("education") String education, 
			@Param("schoolename") String schoolename,  
			@Param("locationId") String locationId, 
			@Param("departmentId") String departmentId, 
			@Param("emergencytel") String emergencytel, 
			@Param("emergencycontact") String emergencycontact, 
			@Param("companyEmail") String companyEmail, 
			@Param("email") String email, 
			@Param("address") String address, 
			@Param("zipcode") String zipcode, 
			@Param("phone") String phone, 
			@Param("subject") String subject, 
			@Param("jobtitle") String jobtitle, 
			@Param("served") String served, 
			@Param("tel") String tel, 
			@Param("empCname") String empCname, 
			@Param("empEname") String empEname, 
			@Param("gender") String gender, 
			@Param("birthday") Date birthday, 
			@Param("onBoard") Date onBoard,
			@Param("modifyUser") String modifyUser, 
			@Param("modifyTime") Date modifyTime);

	@Query("SELECT T1 FROM Employee T1 WHERE T1.empId <> :empId ORDER BY T1.empId ASC")
	public List<Employee> findOtherEmployees (@Param("empId") String empId);
	
}


