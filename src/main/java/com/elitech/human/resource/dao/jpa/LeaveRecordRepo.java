package com.elitech.human.resource.dao.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elitech.human.resource.pojo.Leaverecord;
import com.elitech.human.resource.vo.form.searchform.FormSearchTableVO;

/**
 * 
 * @create by Adam
 */
public interface LeaveRecordRepo extends JpaRepository<Leaverecord, String> {

	@Query("SELECT MAX(T1.leaveNo) FROM Leaverecord T1 WHERE T1.leaveNo LIKE :leaveNo%")
	public String findLastLeaveNo (@Param("leaveNo") String today);
	
	@Query("SELECT T1 FROM Leaverecord T1 WHERE T1.empId = :empId")
	public List<FormSearchTableVO> findByEmpId (@Param("empId") String empId);

	@Modifying
	@Query("UPDATE Leaverecord T1 SET " + 
			"T1.begindate = :begindate, " + 
			"T1.beginhour = :beginhour, " + 
			"T1.beginmin = :beginmin, " + 
			"T1.enddate = :enddate, " + 
			"T1.endhour = :endhour, " + 
			"T1.endmin = :endmin, " + 
			"T1.leavedays = :leavedays, " + 
			"T1.leavehours = :leavehours, " + 
			"T1.substitute = :substitute, " + 
			"T1.process = :nextProcess, " + 
			"T1.approveStatus = :approveStatus, " +
			"T1.reason = :reason, " + 
			"T1.document = :document, " +
			"T1.modifyUser = :modifyUser, " + 
			"T1.modifyTime = :modifyTime " +  
			"WHERE T1.leaveNo = :leaveNo")
	public void updateByLeaveNo (
			@Param("leaveNo") String leaveNo, 
			@Param("begindate") Date begindate, 
			@Param("beginhour") String beginhour, 
			@Param("beginmin") String beginmin, 
			@Param("enddate") Date enddate, 
			@Param("endhour") String endhour, 
			@Param("endmin") String endmin,
			@Param("leavedays") long leavedays, 
			@Param("leavehours") double leavehours, 
			@Param("substitute") String substitute, 
			@Param("nextProcess") String nextProcess, 
			@Param("approveStatus") String approveStatus, 
			@Param("reason") String reason, 
			@Param("document") String document,
			@Param("modifyUser") String modifyUser,
			@Param("modifyTime") Date modifyTime);

	@Modifying
	@Query("UPDATE Leaverecord T1 SET " + 
			"T1.process = :process, " + 
			"T1.approveStatus = :approveStatus, " + 
			"T1.modifyUser = :modifyUser, " + 
			"T1.modifyTime = :modifyTime " + 
			"WHERE T1.leaveNo = :leaveNo")
	public void updateByLeaveNo (
			@Param("leaveNo") String leaveNo, 
			@Param("process") String process, 
			@Param("approveStatus") String approveStatus,
			@Param("modifyUser") String modifyUser, 
			@Param("modifyTime") Date modifyTime);

	@Modifying
	@Query("UPDATE Leaverecord T1 SET " + 
			"T1.process = :process, " + 
			"T1.closeTime = :closeTime, " + 
			"T1.approveStatus = :approveStatus, " + 
			"T1.modifyUser = :modifyUser, " + 
			"T1.modifyTime = :modifyTime " +  
			"WHERE T1.leaveNo = :leaveNo")
	public void updateByLeaveNo (
			@Param("leaveNo") String leaveNo, 
			@Param("process") String process, 
			@Param("approveStatus") String approveStatus, 
			@Param("closeTime") Date closeTime, 
			@Param("modifyUser") String modifyUser, 
			@Param("modifyTime") Date modifyTime);


	@Modifying
	@Query("UPDATE Leaverecord T1 SET " + 
			"T1.approveStatus = :approveStatus, " + 
			"T1.modifyUser = :modifyUser, " + 
			"T1.modifyTime = :modifyTime " +  
			"WHERE T1.leaveNo = :leaveNo")
	public void updateByLeaveNo (
			@Param("leaveNo") String leaveNo, 
			@Param("approveStatus") String approveStatus, 
			@Param("modifyUser") String modifyUser, 
			@Param("modifyTime") Date modifyTime);
	
}


