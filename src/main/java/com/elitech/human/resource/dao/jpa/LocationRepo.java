package com.elitech.human.resource.dao.jpa;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elitech.human.resource.pojo.Location;

/**
 * 
 * @create by Adam
 */
public interface LocationRepo extends JpaRepository<Location, String> {

	@Query("SELECT T1 FROM Location T1 WHERE T1.locationName = :locationName")
	public Location findOneByLocationName (@Param("locationName") String locationName);

	@Modifying
	@Query("UPDATE Location T1 SET " + 
			"T1.sales = :sales, " + 
			"T1.locationName = :locationName, " + 
			"T1.pm = :pm, " + 
			"T1.workingTime = :workingTime, " + 
			"T1.offTime = :offTime, " + 
			"T1.lunchTime = :lunchTime, " + 
			"T1.modifyUser = :modifyUser, " + 
			"T1.modifyTime = :modifyTime WHERE " + 
			"T1.locationId = :locationId")
	public void updateByLocationId (
			@Param("locationId") String locationId, 
			@Param("locationName") String locationName, 
			@Param("pm") String pm, 
			@Param("sales") String sales, 
			@Param("workingTime") String workingTime, 
			@Param("offTime") String offTime, 
			@Param("lunchTime") double lunchTime, 
			@Param("modifyUser") String modifyUser, 
			@Param("modifyTime") Date modifyTime);
	
}

