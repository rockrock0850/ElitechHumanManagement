package com.elitech.human.resource.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elitech.human.resource.pojo.Button;

/**
 * 
 * @create by Adam
 */
public interface ButtonRepo extends JpaRepository<Button, String> {

	@Query("SELECT T1 FROM Button T1 WHERE T1.functionId = :functionId ORDER BY T1.seq ASC")
	public List<Button> findButtons (@Param("functionId") String functionId);

	@Query("SELECT T1 FROM Button T1 WHERE T1.functionId = :functionId AND T1.buttonId = :buttonId")
	public Button findOne (@Param("functionId") String functionId, @Param("buttonId") String buttonId);
	
}


