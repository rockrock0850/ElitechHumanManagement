package com.elitech.human.resource.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elitech.human.resource.pojo.Menu;

/**
 * 
 * @create by Adam
 */
public interface MenuRepo extends JpaRepository<Menu, String> {

	@Query("SELECT T1 FROM Menu T1 WHERE T1.PMenuId IS NULL ORDER BY T1.seq ASC")
	public List<Menu> findMenuLv1 ();

	@Query("SELECT T1 FROM Menu T1 WHERE T1.PMenuId = :pMenuId ORDER BY T1.seq ASC")
	public List<Menu> findSubMenu (@Param("pMenuId") String pMenuId);
	
}
