package com.elitech.human.resource.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 定義頁面型態取得功能按鈕<br>
 * 預設值=查詢
 * 
 * @create by Adam
 * @create date: Nov 16, 2017
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Page {
	
	/**
	 * 查詢頁面
	 */
	public final String QUERY = "Q";

	/**
	 * 更新頁面
	 */
	public final String UPDATE = "U";

	/**
	 * 新增頁面
	 */
	public final String INSERT = "I";

	/**
	 * 刪除頁面
	 */
	public final String DELETE = "D";

	/* 功能客製化頁面
	============================================== */
	/*
	 * 群組權限維護
	 */
	
	/**
	 * 群組權限管理
	 */
	public final String GROUP_AUTHORITY_CUSTOM_1 = "GA_C_1";

	/**
	 * 使用者權限管理
	 */
	public final String USER_AUTHORITY_CUSTOM_1 = "UA_C_1";

	/**
	 * 補修轉入作業
	 */
	public final String PAID_LEAVE_CUSTOM_1 = "PL_C_1";
	
	/**
	 * 預設值=查詢
	 * @return
	 */
	public String type() default QUERY;
}
