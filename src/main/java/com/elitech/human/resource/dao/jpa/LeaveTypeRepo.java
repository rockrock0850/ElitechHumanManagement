package com.elitech.human.resource.dao.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elitech.human.resource.pojo.Leavetype;
import com.elitech.human.resource.pojo.LeavetypeId;

/**
 * 
 * @create by Adam
 */
public interface LeaveTypeRepo extends JpaRepository<Leavetype, LeavetypeId> {
	
	@Query("SELECT T1 FROM Leavetype T1 WHERE T1.leaveName = :leaveName")
	public List<Leavetype> findOneByLeaveName(@Param("leaveName") String leaveName);

	@Modifying
	@Query("UPDATE Leavetype T1 SET " + 
			"T1.leaveName = :leaveName, " + 
			"T1.unit = :unit, " +
			"T1.id.emptype = :emptype, " + 
			"T1.gender = :gender, " + 
			"T1.expire = :expire, " + 
			"T1.isYears = :isYears, " + 
			"T1.isSubstitute = :isSubstitute, " + 
			"T1.isDocument = :isDocument, " + 
			"T1.documentMsg = :documentMsg, " + 
			"T1.modifyUser = :modifyUser, " + 
			"T1.modifyTime = :modifyTime WHERE " + 
			"T1.id.leavetypeId = :leavetypeId AND " + 
			"T1.id.emptype = :emptype")
	public void updateByLeavetypeId (
			@Param("leavetypeId") String leavetypeId, 
			@Param("leaveName") String leaveName, 
			@Param("unit") String unit, 
			@Param("emptype") String emptype, 
			@Param("gender") String gender, 
			@Param("expire") Long expire, 
			@Param("isYears") String isYears,
			@Param("isSubstitute") String isSubstitute, 
			@Param("isDocument") String isDocument, 
			@Param("documentMsg") String documentMsg, 
			@Param("modifyUser") String modifyUser, 
			@Param("modifyTime") Date modifyTime);
	
	@Query("SELECT T1 FROM Leavetype T1 WHERE T1.id.emptype = :empType ORDER BY T1.id.leavetypeId ASC")
	public List<Leavetype> findByEmpType (@Param("empType") String empType);
	
}


