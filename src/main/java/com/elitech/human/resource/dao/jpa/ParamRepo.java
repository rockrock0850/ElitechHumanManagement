package com.elitech.human.resource.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elitech.human.resource.pojo.ParamId;

/**
 * 
 * @create by Adam
 */
public interface ParamRepo extends JpaRepository<com.elitech.human.resource.pojo.Param, ParamId> {

	@Query("SELECT T1 FROM Param T1 WHERE T1.id.key = :key AND T1.status = '1' ORDER BY T1.seq ASC")
	public List<com.elitech.human.resource.pojo.Param> findByKey (@Param("key") String key);

}


