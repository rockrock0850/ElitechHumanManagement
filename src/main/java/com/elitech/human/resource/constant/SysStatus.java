package com.elitech.human.resource.constant;

/**
 * 系統訊息管理類別
 * 
 * @create by Adam
 */
public enum SysStatus { 

	/**
	 * 修改成功
	 */
	SUCCESS_1("修改成功"),

	/**
	 * 新增成功
	 */
	SUCCESS_2("新增成功"),

	/**
	 * 刪除成功
	 */
	SUCCESS_3("刪除成功"),

	/**
	 * 未知的錯誤
	 */
	UNKNOWN_ERROR("999", "未知的錯誤"),
	
	/* 群組帳號維護
	======================================================= */
	/**
	 * 群組帳號重複
	 */
	GROUP_ACCOUNT_ERROR_1("999", "群組帳號重複"),

	/**
	 * 群組名稱重複
	 */
	GROUP_ACCOUNT_ERROR_2("999", "群組名稱重複"),

	/* 部門管理
	======================================================= */
	/**
	 * 部門代號重複
	 */
	DEPT_ERROR_1("999", "部門代號重複"),

	/**
	 * 查無上層部門
	 */
	DEPT_ERROR_2("999", "查無上層部門"),

	/**
	 * 查無此主管名稱
	 */
	DEPT_ERROR_3("999", "查無此主管名稱"),

	/**
	 * 部門編號: [${deptId}] 尚有從屬之部門故無法刪除，請重新操作!
	 */
	DEPT_ERROR_4("999", "部門編號: [${deptId}] 尚有從屬之部門故無法刪除，請重新操作!"), 

	/* 駐點基本資料
	======================================================= */
	/**
	 * 駐點位置代碼重複
	 */
	LOCATION_MANAGEMENT_ERROR_1("999", "駐點位置代碼重複"),
	
	/**
	 * 駐點名稱重複
	 */
	LOCATION_MANAGEMENT_ERROR_2("999", "駐點名稱重複"),

	/* 假別資料維護
	======================================================= */
	/**
	 * 假別代碼重複
	 */
	HOLIDAY_CATEGORY_ERROR_1("999", "假別代碼重複"), 

	/* 員工假別可休天數管理
	======================================================= */
	/**
	 * 年度資料已存在
	 */
	HOLIDAY_EMP_ERROR_1("999", "年度資料已存在"), 

	/* 員工基本資料管理
	======================================================= */
	/**
	 * 員工資料已存在
	 */
	EMP_MANAGEMENT_ERROR_1("999", "員工資料已存在"), 

	/* 請假單申請
	======================================================= */
	/**
	 * 請假時數超過限制
	 */
	APPLY_LEAVE_ERROR_1("999", "請假時數超過限制"), 

	/* 補休匯入作業
	======================================================= */
	/**
	 * 補休匯入檔案格式錯誤
	 */
	PAID_LEAVE_ERROR_1("999", "補休匯入檔案格式錯誤");
	
	private String code;
	private String message;
	
	private SysStatus(String message) {
		this.code = "200";
		this.message = message;
	}
	
	private SysStatus(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public String getMsg() {
		return message;
	}

	public String getCode() {
		return code;
	}
	
}


