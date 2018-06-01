package com.elitech.human.resource.dao.jdbc;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

public class JdbcDAO extends NamedParameterJdbcDaoSupport {

	private Logger log = Logger.getLogger(this.getClass());

	public void alterTableAddColumn (String tableName, String columnName, String columnType) {
		super.getJdbcTemplate().execute("ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " + columnType + ";");
	}

	/**
	 * @create by Adam
	 * @create date: Nov 16, 2017
	 *
	 * @param sqlText
	 * @return
	 */
	public int update (String sqlText) {
		log.debug("\n" + sqlText);
		return super.getJdbcTemplate().update(sqlText);
	}
	
	/**
	 * @create by Adam
	 * @create date: Nov 16, 2017
	 *
	 * @param sqlText
	 * @param sqlParams
	 * @return
	 */
	public int update (String sqlText, Map<String, Object> sqlParams) {
		log.debug("\n" + sqlText);
		return super.getNamedParameterJdbcTemplate().update(sqlText, sqlParams);
	}
	
	/**
	 * @create by Adam
	 * @create date: Nov 16, 2017
	 *
	 * @param sqlText
	 * @param sqlParam
	 * @return
	 */
	public int[] updateBatch (String sqlText, List<Map<String, Object>> sqlParam) {
		log.debug("\n" + sqlText);
		
		MapSqlParameterSource[] mapParamArr = new MapSqlParameterSource[sqlParam.size()]; 
		
		for(int i = 0 ; i < sqlParam.size() ; i++) {
			Map<String, ?> m = sqlParam.get(i);
			mapParamArr[i] = new MapSqlParameterSource(m);
		}
		
		int[] effCount = 
				super.getNamedParameterJdbcTemplate().batchUpdate(sqlText, mapParamArr);
		
		return effCount;
	}
	
	/**
	 * 
	 * @param sqlText
	 * @param params
	 * @return 
	 * @return List<Map<String, Object>>
	 */
	public <T> T queryForBean(String sqlText, Class<T> clazz) {
		return (T) queryForBean(sqlText, new HashMap<String, Object>(), clazz);
	}
	
	/**
	 * 
	 * @param sqlText
	 * @param params
	 * @return 
	 * @return List<Map<String, Object>>
	 */
	public <T> T queryForBean(String sqlText, Map<String, Object> params, Class<T> clazz) {
		log.debug("\n" + sqlText);
		
		List<T> dataLs = 
				getNamedParameterJdbcTemplate().query(sqlText, params, new BeanPropertyRowMapper<T>(clazz));
		
		return CollectionUtils.isEmpty(dataLs) ? null : dataLs.get(0);
	}
	
	/**
	 * @create by Adam
	 * @create date: Nov 16, 2017
	 *
	 * @param sqlText 含有Prepared statement之SQL
	 * @param params 查詢條件參數
	 * @return
	 */
	public Map<String, Object> queryForMap(String sqlText) {
		return queryForMap(sqlText, new HashMap<String, Object>());
	}
	
	/**
	 * @create by Adam
	 * @create date: Nov 16, 2017
	 *
	 * @param sqlText 含有Prepared statement之SQL
	 * @param params 查詢條件參數
	 * @return
	 */
	public Map<String, Object> queryForMap(String sqlText, Map<String, Object> params) {
		log.debug("\n" + sqlText);
		
		List<Map<String, Object>> dataLs = getNamedParameterJdbcTemplate().queryForList(sqlText, params);
		
		int size = dataLs != null ? dataLs.size() : -1;
		
		if(dataLs != null && dataLs.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, size);
		}
		
		if(size == 1) {
			return dataLs.get(0);
		} 
		
		return null;
	}
	
	/**
	 * @create by Adam
	 * @create date: Nov 16, 2017
	 *
	 * @param sqlText 一般SQL文件
	 * @return
	 */
	public <T> List<Map<String, Object>> queryForList (String sqlText) {
		log.debug("\n" + sqlText);
		
		if (StringUtils.contains(sqlText, "${CONDITIONS}")) {
			StringUtils.replace(sqlText, "${CONDITIONS}", "");
		}
		
		List<Map<String, Object>> rtnLs = 
				super.getNamedParameterJdbcTemplate().queryForList(sqlText, new HashMap<String, Object>());
		
		if(CollectionUtils.isEmpty(rtnLs)) {
			return Collections.emptyList();
		}
		
		return rtnLs;
	}
	
	/**
	 * 
	 * @create by Adam
	 * @create date: Nov 16, 2017
	 *
	 * @param sqlText 一般SQL文件
	 * @param clazz 須轉型之類別
	 * @return
	 */
	public <T> List<T> queryForList (String sqlText, Class<T> clazz) {
		log.debug("\n" + sqlText);
		
		if (StringUtils.contains(sqlText, "${CONDITIONS}")) {
			StringUtils.replace(sqlText, "${CONDITIONS}", "");
		}
		
		List<T> rtnLs = 
				getNamedParameterJdbcTemplate().query(sqlText, new HashMap<String, Object>(), new BeanPropertyRowMapper<T>(clazz));
		
		if(CollectionUtils.isEmpty(rtnLs)) {
			return Collections.emptyList();
		}
		
		return rtnLs;
	}
	
	/**
	 * @create by Adam
	 * @create date: Nov 16, 2017
	 *
	 * @param sqlText 含有Prepared statement之SQL文件
	 * @param params 查詢條件參數
	 * @return
	 */
	public <T> List<Map<String, Object>> queryForList (String sqlText, Map<String, Object> params) {
		log.debug("\n" + sqlText);
		
		List<Map<String, Object>> rtnLs = 
				super.getNamedParameterJdbcTemplate().queryForList(sqlText, params);
		
		if(CollectionUtils.isEmpty(rtnLs)) {
			return Collections.emptyList();
		}
		
		return rtnLs;
	}
	
	/**
	 * @create by Adam
	 * @create date: Nov 16, 2017
	 *
	 * @param sqlText 含有Prepared statement之SQL文件
	 * @param params 查詢條件參數
	 * @param clazz 須轉型之類別
	 * @return
	 */
	public <T> List<T> queryForList(String sqlText, Map<String, Object> params, Class<T> clazz) {
		log.debug("\n" + sqlText);
		
		List<T> rtnLs = 
				getNamedParameterJdbcTemplate().query(sqlText, params, new BeanPropertyRowMapper<T>(clazz));
		
		if(CollectionUtils.isEmpty(rtnLs)) {
			return Collections.emptyList();
		}
		
		return rtnLs;
	}
	
}
