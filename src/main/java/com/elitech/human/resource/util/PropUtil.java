package com.elitech.human.resource.util;

import java.util.ResourceBundle;

import com.elitech.human.resource.constant.Resources;

/**
 * 屬性檔讀取工具
 * 
 * @create by Adam
 * @create date: Nov 5, 2017
 */
public class PropUtil{
	
	/**
	 * 取得預設屬性資料<br>
	 * 預設取得system屬性檔內的資料
	 * 
	 * @param prop
	 * @return
	 */
	public static String getProperty(String prop){
		return getProperty("system", prop);
	}
	
	/**
	 * 取得屬性資料
	 * 
	 * @create by Adam
	 * @create date: Oct 31, 2017
	 *
	 * @param fileName 檔案名稱
	 * @param prop
	 * @return
	 */
	public static String getProperty(String fileName, String prop){
		StringBuilder path = new StringBuilder();
		path.append(Resources.PROP.getRoot());
		path.append(fileName);
		
		return ResourceBundle.getBundle(path.toString()).getString(prop);
	}
	
}
