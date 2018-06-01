package com.elitech.human.resource.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elitech.human.resource.pojo.Function;

/**
 * 
 * @create by Adam
 */
public interface FunctionRepo extends JpaRepository<Function, String> {

	@Query("SELECT T1 FROM Function T1 WHERE T1.menuId = :menuId AND T1.status = '1' ORDER BY T1.seq ASC")
	public List<Function> findFunctions (@Param("menuId") String menuId);
	
}


