package com.elitech.human.resource.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;

public class LogUtil{
	
	private static Logger log = Logger.getLogger(LogUtil.class);
	
	/**
	 * 將物件轉成字串並列印出來
	 * 
	 * @create by Adam
	 * @create date: Oct 27, 2017
	 *
	 * @param object
	 * @return
	 */
	public static String toStringBuilder(Object object){
		try {
			if(object instanceof String || object instanceof Map || 
					object instanceof Integer || object instanceof Double || object instanceof Long){
				return object.toString();
			}
			
			if(object instanceof List){
				List<?> list = (List<?>) object;
				
				if(list == null || list.isEmpty()){
					return "";
				}
				
				/*
				 * List 最多10筆
				 */
				int size = list.size() > 10 ? 10 : list.size();
				list = list.subList(0, size);
				
				List<String> dataList = new ArrayList<>();
				for(Object o : list){
					dataList.add(toStringBuilder(o));
				}

				return StringUtils.join(dataList, ", ");
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return ReflectionToStringBuilder.toString(object, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	/**
	 * 尋找傳入Exception之錯誤發生位置 
	 * 
	 * @create by Adam
	 * @create date: Oct 27, 2017
	 *
	 * @param e
	 * @return
	 */
    public static String getExceptionClazz (Exception e) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String target = "";
		
		try {
			e.printStackTrace(new PrintStream(out));
			String stackTrace = out.toString("utf-8");
			
			target = PropUtil.getProperty("package.root");
			int begin = stackTrace.indexOf(target);
			int offset = stackTrace.substring(begin).indexOf(")")+1;
			int end = begin + offset;
			
			target = stackTrace.substring(begin, end);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		return target;
    }
    
    public static void log (Object obj) {
    	String msg = toStringBuilder(obj);
    	log.debug(msg);
    }

}
