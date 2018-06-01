package com.elitech.human.resource.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elitech.human.resource.pojo.PermissionUser;
import com.elitech.human.resource.pojo.PermissionUserId;

/**
 * 
 * @create by Adam
 */
public interface PermissionUserRepo extends JpaRepository<PermissionUser, PermissionUserId> {

	@Query("SELECT T1 FROM PermissionUser T1 WHERE " + 
			"T1.id.userId = :userId AND " + 
			"T1.id.functionId = :functionId AND " + 
			"T1.id.buttonId = :buttonId")
	public PermissionUser findOne (
			@Param("userId") String userId, 
			@Param("functionId") String functionId, 
			@Param("buttonId") String buttonId);

	@Modifying
	@Query("DELETE FROM PermissionUser T1 WHERE T1.id.userId = :userId")
	public void delete (@Param("userId") String userId);

}


