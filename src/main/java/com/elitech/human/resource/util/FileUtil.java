package com.elitech.human.resource.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 讀取檔案工具
 * 
 * @create by Adam
 */
public class FileUtil {
	
	public static String readFile (StringBuilder path) throws IOException {
		InputStream is = FileUtil.class.getResourceAsStream(path.toString()); 
		BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
		String line = buf.readLine(); 
		StringBuilder sb = new StringBuilder(); 
		
		while(line != null){ 
			sb.append(line).append("\n"); 
			line = buf.readLine(); 
		} 
		
		String fileAsString = sb.toString();
		
		return fileAsString;
	}
	
}


