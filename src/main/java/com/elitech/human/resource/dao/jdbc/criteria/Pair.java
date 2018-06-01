package com.elitech.human.resource.dao.jdbc.criteria;

/**
 * 客製化Condition類別的參數組成資料類別
 * 
 * @create by Adam
 */
public class Pair {

	private String column;
	private String value;
	private String matchType;

	public Pair (String column, String value, String matchType) {
		this.column = column;
		this.value = value;
		this.matchType = matchType;
	}

	public String getColumn () {
		return column;
	}

	public void setColumn (String column) {
		this.column = column;
	}

	public String getValue () {
		return value;
	}

	public void setValue (String value) {
		this.value = value;
	}

	public String getMatchType () {
		return matchType;
	}

	public void setMatchType (String matchType) {
		this.matchType = matchType;
	}

}
