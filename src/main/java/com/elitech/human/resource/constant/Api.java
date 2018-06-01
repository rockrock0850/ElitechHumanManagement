package com.elitech.human.resource.constant;

/**
 * 定義API路徑常數
 * 
 * @create by Adam
 */
public enum Api {

	// 登入系統的 API
	
	/**
	 * 新增使用者: /ElitechLoginManagement/Rest/addAccount
	 */
	ADD_ACCOUNT("/ElitechLoginManagement/Rest/addAccount"), 
	
	/**
	 * 更新使用者狀態: /ElitechLoginManagement/Rest/editAccount
	 */
	EDIT_ACCOUNT("/ElitechLoginManagement/Rest/editAccount");

	private String action;

	private Api (String action) {
		this.action = action;
	}

	public String getAction () {
		return action;
	}
	
}


