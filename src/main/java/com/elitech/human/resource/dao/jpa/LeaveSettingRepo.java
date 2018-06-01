package com.elitech.human.resource.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elitech.human.resource.pojo.LeaveSetting;

/**
 * 
 * @create by Adam
 */
public interface LeaveSettingRepo extends JpaRepository<LeaveSetting, Long> {

	@Query("SELECT T1 FROM LeaveSetting T1 WHERE " + 
			"T1.emptype = :emptype AND " + 
			"T1.leaveYear = :leaveYear " + 
			"ORDER BY T1.leavetypeId")
	public List<LeaveSetting> findByEmpTypeAndLeaveYear (
			@Param("emptype") String emptype, 
			@Param("leaveYear") String leaveYear);

	@Query("SELECT T1 FROM LeaveSetting T1 WHERE " + 
			"T1.emptype = :emptype AND " + 
			"T1.leavetypeId = :leaveTypeId AND " + 
			"T1.leaveYear = :year AND " + 
			"T1.years <= :workedYears " + 
			"ORDER BY T1.years DESC")
	public List<LeaveSetting> findYearLeave (
			@Param("emptype") String empType, 
			@Param("leaveTypeId") String leaveTypeId, 
			@Param("year") String year, 
			@Param("workedYears") double workedYears);

}


