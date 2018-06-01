package com.elitech.human.resource.dao.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elitech.human.resource.pojo.Department;

/**
 * 
 * @create by Adam
 */
public interface DepartmentRepo extends JpaRepository<Department, String> {

	@Modifying
	@Query("UPDATE Department T1 SET " + 
			"T1.departmentName = :deptName, " + 
			"T1.parentDepartmentId = :parentDeptId, " + 
			"T1.departmentManager = :deptManager, " + 
			"T1.modifyUser = :modifyUser, " + 
			"T1.modifyTime = :modifyTime WHERE " + 
			"T1.departmentId = :deptId")
	public void updateByDeptId (
			@Param("deptId") String deptId, 
			@Param("deptName") String deptName, 
			@Param("parentDeptId") String parentDeptId, 
			@Param("deptManager") String deptManager, 
			@Param("modifyUser") String modifyUser, 
			@Param("modifyTime") Date modifyTime);

	@Query("SELECT T1 FROM Department T1 WHERE T1.departmentId <> :id")
	public List<Department> findAll (@Param("id") String id);

}


