package com.elitech.human.resource.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elitech.human.resource.pojo.PermissionRole;
import com.elitech.human.resource.pojo.PermissionRoleId;

/**
 * 
 * @create by Adam
 */
public interface PermissionRoleRepo extends JpaRepository<PermissionRole, PermissionRoleId> {

	@Query("SELECT T1 FROM PermissionRole T1 WHERE " + 
			"T1.id.roleId = :roleId AND " + 
			"T1.id.functionId = :functionId AND " + 
			"T1.id.buttonId = :buttonId")
	public PermissionRole findOne (
			@Param("roleId") String roleId, 
			@Param("functionId") String functionId, 
			@Param("buttonId") String buttonId);

	@Modifying
	@Query("DELETE FROM PermissionRole T1 WHERE T1.id.roleId = :roleId")
	public void delete (@Param("roleId") String roleId);

}


