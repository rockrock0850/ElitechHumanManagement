package com.elitech.human.resource.dao.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elitech.human.resource.pojo.UserRoleMapping;
import com.elitech.human.resource.pojo.UserRoleMappingId;

/**
 * 
 * @create by Adam
 */
public interface UserRoleMapRepo extends JpaRepository<UserRoleMapping, UserRoleMappingId> {

	@Query("SELECT T1 FROM UserRoleMapping T1 WHERE T1.id.roleId = :roleId")
	public List<UserRoleMapping> findByRoleId (@Param("roleId") String roleId);

	@Modifying
	@Query("UPDATE UserRoleMapping T1 SET " + 
			"T1.id.userId = :userId, " + 
			"T1.modifyUser = :modifyUser, " + 
			"T1.modifyTime = :modifyTime " + 
			"WHERE " + 
			"T1.id.roleId = :roleId AND " + 
			"T1.id.userId = :userId")
	public void update (
			@Param("roleId") String roleId, 
			@Param("userId") String userId, 
			@Param("modifyUser") String modifyUser, 
			@Param("modifyTime") Date modifyTime);

	@Modifying
	@Query("DELETE FROM UserRoleMapping T1 WHERE T1.id.roleId = :roleId")
	public void deleteByRoleId (@Param("roleId") String roleId);

}


