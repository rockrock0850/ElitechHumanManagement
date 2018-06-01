package com.elitech.human.resource.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elitech.human.resource.pojo.ProcessFlow;
import com.elitech.human.resource.pojo.ProcessFlowId;

/**
 * 
 * @create by Adam
 */
public interface ProcessFlowRepo extends JpaRepository<ProcessFlow, ProcessFlowId> {

	@Query("SELECT MAX(T1.id.seq) FROM ProcessFlow T1 WHERE T1.id.leaveNo = :leaveNo")
	public Long findLastSeq (@Param("leaveNo") String leaveNo);

	@Modifying
	@Query("UPDATE ProcessFlow T1 SET " + 
			"T1.approveStatus = :approveStatus, " + 
			"T1.notes = :note " + 
			"WHERE " + 
			"T1.id.seq = :seq AND " +
			"T1.id.empId = :empId AND " +
			"T1.id.leaveNo = :leaveNo")
	public void updateByPrimaryKey (
			@Param("seq") long seq, 
			@Param("empId") String empId, 
			@Param("leaveNo") String leaveNo, 
			@Param("approveStatus") String approveStatus, 
			@Param("note") String note);

	@Query("SELECT T1 FROM ProcessFlow T1 WHERE T1.id.leaveNo = :leaveNo AND T1.id.seq = 0")
	public ProcessFlow findFirstFlow (@Param("leaveNo") String leaveNo);

	@Query("SELECT T1.id.empId FROM ProcessFlow T1 WHERE T1.id.leaveNo = :leaveNo AND T1.id.seq = :seq")
	public String findPreFlowEmpId (@Param("seq") long seq, @Param("leaveNo") String leaveNo);
	
}


