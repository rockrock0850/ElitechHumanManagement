package com.elitech.human.resource.constant;

/**
 * 定義專案資源路徑常數
 * 
 * @create by Adam
 */
public enum Resources {

	// Properties root
	PROP("properties."),
	
	// SQL directory
	SQL_BUTTON("/sql", "/button", ".sql"),
	SQL_DEPARTMENT("/sql", "/department", ".sql"),
	SQL_EMPLOYEE("/sql", "/employee", ".sql"),
	SQL_FUNCTION("/sql", "/function", ".sql"),
	SQL_LEAVE_RECORD("/sql", "/leave_record", ".sql"),
	SQL_LEAVE_SETTING("/sql", "/leave_setting", ".sql"),
	SQL_LEAVE_TYPE("/sql", "/leave_type", ".sql"),
	SQL_LEAVEDAY("/sql", "/leaveday", ".sql"),
	SQL_LOCATION("/sql", "/location", ".sql"),
	SQL_LOGIN_USER("/sql", "/login_user", ".sql"),
	SQL_MENU("/sql", "/menu", ".sql"),
	SQL_PARAM("/sql", "/param", ".sql"),
	SQL_PERMISSION_ROLE("/sql", "/permission_role", ".sql"),
	SQL_PERMISSION_USER("/sql", "/permission_user", ".sql"),
	SQL_PROCESS_FLOW("/sql", "/process_flow", ".sql"),
	SQL_USER_ROLE("/sql", "/user_role", ".sql"),
	SQL_USER_ROLE_MAPPING("/sql", "/user_role_mapping", ".sql");

	private String root;
	private String dir;
	private String extension;

	/**
	 * @create by Adam
	 * @create date: Nov 16, 2017
	 *
	 * @param root
	 */
	private Resources (String root) {
		this.root = root;
	}
	
	/**
	 * @create by Adam
	 * @create date: Nov 16, 2017
	 *
	 * @param root
	 * @param dir
	 * @param extension
	 */
	private Resources (String root, String dir, String extension) {
		this.root = root;
		this.dir = dir;
		this.extension = extension;
	}
	
	public String getRoot () {
		return this.root;
	}
	
	public String getDir () {
		return this.dir;
	}
	
	public String getExtension () {
		return this.extension;
	}
	
}


