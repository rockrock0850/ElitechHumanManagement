package com.elitech.human.resource.dao.jpa;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elitech.human.resource.pojo.LoginUser;

/**
 * 
 * @create by Adam
 */
public interface LoginUserRepo extends JpaRepository<LoginUser, String> {

	@Modifying
	@Query("UPDATE LoginUser T1 SET " + 
			"T1.userName = :accountName, " + 
			"T1.email = :email, " + 
			"T1.modifyUser = :modifyUser, " + 
			"T1.modifyTime = :modifyTime " + 
			"WHERE " + 
			"T1.userId = :accountId")
	public void update (
			@Param("accountId") String accountId, 
			@Param("email") String email, 
			@Param("accountName") String accountName, 
			@Param("modifyUser") String modifyUser, 
			@Param("modifyTime") Date modifyTime);

	@Modifying
	@Query("UPDATE LoginUser T1 SET " + 
			"T1.status = :status, " + 
			"T1.modifyUser = :modifyUser, " + 
			"T1.modifyTime = :modifyTime " + 
			"WHERE " + 
			"T1.userId = :accountId")
	public void updateUserStatus (
			@Param("accountId") String accountId, 
			@Param("status") String status, 
			@Param("modifyUser") String modifyUser, 
			@Param("modifyTime") Date modifyTime);
	
}


