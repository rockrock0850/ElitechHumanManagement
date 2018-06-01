package com.elitech.human.resource.dao.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elitech.human.resource.pojo.LeavedayD;

/**
 * 
 * @create by Adam
 */
public interface LeavedayDRepo extends JpaRepository<LeavedayD, Long> {

	@Query("SELECT T1 FROM LeavedayD T1 WHERE T1.empId = :empId AND T1.action = :action")
	public List<LeavedayD> findByAction (@Param("empId") String empId, @Param("action") String action);

	@Modifying
	@Query("DELETE FROM LeavedayD T1 WHERE T1.empId = :empId")
	public void deleteByEmpId (@Param("empId") String empId);

	@Query("SELECT T1 FROM LeavedayD T1 WHERE T1.empId = :empId AND T1.leavetypeId = :leavetypeId")
	public LeavedayD findHours (@Param("empId") String empId, @Param("leavetypeId") String leavetypeId);

	@Modifying
	@Query("UPDATE LeavedayD T1 SET T1.hours = :leavehours,T1.modifyUser = :userId,T1.modifyTime = :modifyTime WHERE T1.id.seq = :seq")
	public void update (
			@Param("seq") long seq, 
			@Param("leavehours") double leavehours, 
			@Param("userId") String userId, 
			@Param("modifyTime") Date modifyTime);

	@Query("SELECT T1 FROM LeavedayD T1 WHERE T1.years = :years AND T1.action = :action")
	public List<LeavedayD> findByUsed (@Param("years") long years, @Param("action") String action);
	
}


