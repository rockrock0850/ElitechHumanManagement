package com.elitech.human.resource.dao.jdbc.criteria;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * JDBC DAO的查詢條件物件化類別
 * 
 * @create by Adam
 */
public class Conditions {
	
	public static final String AND = "AND";
	public static final String OR = "OR";

	private List<Pair> pairs;
	private StringBuilder condition;
	private boolean needWhere;

	/**
	 * 條件句物件初始化
	 * 
	 * @create by Adam
	 * @create date: Nov 24, 2017
	 *
	 */
	public Conditions () {
		pairs = new ArrayList<>();
		condition = new StringBuilder();
	}
	
	/**
	 * @create by Adam
	 * @create date: Nov 24, 2017
	 *
	 * @param needWhere 若需要組條件式=true, 否則=false
	 */
	public Conditions (boolean needWhere) {
		pairs = new ArrayList<>();
		this.needWhere = needWhere;
		
		if (needWhere) {
			condition = new StringBuilder("WHERE ");
		} else {
			condition = new StringBuilder();
		}
	}
	
	public void equal (String column, String value) {
		put(column, value, "=");
	}
	
	public void like (String column, String value) {
		put(column, value, "LIKE");
	}
	
	public void unEqual (String column, String value) {
		put(column, value, "<>");
	}

	public void notNull (String column) {
		put(column, null, "IS NOT NULL");
	}

	public void isNull (String column) {
		put(column, null, "IS NULL");
	}
	
	/**
	 * 左括弧
	 * 
	 * @create by Adam
	 * @create date: Nov 17, 2017
	 *
	 */
	public void leftPT () {
		condition.append(" (");
	}
	
	/**
	 * 左括弧
	 * 
	 * @create by Adam
	 * @create date: Nov 17, 2017
	 *
	 * @param link 連結符號 ( AND、OR... )
	 */
	
	public void leftPT (String operators) {
		condition.append(" " + StringUtils.upperCase(operators) + " (");
	}
	
	/**
	 * 右括弧
	 * 
	 * @create by Adam
	 * @create date: Nov 17, 2017
	 */
	
	public void RightPT () {
		condition.append(") ");
	}
	
	/**
	 * 右括弧
	 * 
	 * @create by Adam
	 * @create date: Nov 17, 2017
	 *
	 * @param link 連結符號 ( AND、OR... )
	 */
	
	public void RightPT (String operators) {
		condition.append(") " + StringUtils.upperCase(operators) + " ");
	}
	
	/**
	 * 將組裝好的條件句參數AND起來
	 * 
	 * @create by Adam
	 * @create date: Nov 19, 2017
	 *
	 */
	public void and () {
		combine("AND");
	}
	
	/**
	 * 將組裝好的條件句參數OR起來
	 * 
	 * @create by Adam
	 * @create date: Nov 19, 2017
	 *
	 */
	public void or () {
		combine("OR");
	}
	
	/**
	 * 宣告條件句結束
	 * 
	 * @create by Adam
	 * @create date: Nov 19, 2017
	 *
	 */
	public void done () {
		combine("");
	}
	
	public String get (String sqlText) {
		sqlText = StringUtils.replace(sqlText, "${CONDITIONS}", condition.toString());
		pairs.clear();
		
		if (needWhere) {
			condition = new StringBuilder("WHERE ");
		} else {
			condition = new StringBuilder();
		}
		
		return sqlText;
	}
	
	private void put (String column, String value, String matchType) {
		pairs.add(new Pair(column, value, matchType));
	}

	private void combine (String combine) {
		int size = pairs.size();
		for (int i = 0; i < size; i++) {
			Pair pair = pairs.get(i);
			String column = pair.getColumn();
			String value = pair.getValue();
			String type = pair.getMatchType();
			
			if (i == size-1) {
				handleMatchType(column, type, value);
			} else {
				handleMatchType(column, type, value);
				condition.append(" " + StringUtils.upperCase(combine) + " ");
			}
		}
		pairs = new ArrayList<>();
	}

	private void handleMatchType (String column, String type, String value) {
		if (StringUtils.equals(type, "LIKE")) {
			condition.append(column + " " + type + " '%" + value + "%'");	
		} else if (StringUtils.startsWith(type, "IS")) {
			condition.append(column + " " + type);
		} else {
			condition.append(column + " " + type + " '" + value + "'");	
		}
	}
	
}


