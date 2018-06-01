package com.elitech.human.resource.util;

import java.io.IOException;

import com.elitech.human.resource.constant.Resources;

/**
 * 取得專案資源檔工具
 * 
 * @create by Adam
 */
public class ResourceUtil {

	public static String get (Resources resources, String name) throws IOException {
		StringBuilder path = new StringBuilder();
		path.append(resources.getRoot());
		path.append(resources.getDir());
		path.append("/" + name);
		path.append(resources.getExtension());
		
		String fileStr = FileUtil.readFile(path);
		
		return fileStr;
	}
	
}


