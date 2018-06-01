package com.elitech.human.resource.dao.jpa;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elitech.human.resource.pojo.UserRole;

/**
 * 
 * @create by Adam
 */
public interface UserRoleRepo extends JpaRepository<UserRole, String> {

	@Modifying
	@Query("UPDATE UserRole T1 SET " + 
			"T1.roleName = :roleName, " + 
			"T1.status = :status, " + 
			"T1.modifyUser = :modifyUser, " + 
			"T1.modifyTime = :modifyTime WHERE " + 
			"T1.roleId = :roleId")
	public void updateByRoleId (
			@Param("roleId") String roleId, 
			@Param("roleName") String roleName, 
			@Param("status") String status, 
			@Param("modifyUser") String modifyUser,
			@Param("modifyTime") Date modifyTime);
	
	@Query("SELECT T1 FROM UserRole T1 WHERE T1.roleName = :roleName")
	public UserRole findOneByRoleName (@Param("roleName") String roleName);
	
}


