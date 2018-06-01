package com.elitech.human.resource.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期處理 工具類別
 * 
 * @create by Adam
 */
public class DateUtil {
	
	/**
	 * yyyyMMdd
	 */
	public static final String DATE_1 = "yyyyMMdd";

	/**
	 * yyyy-MM-dd
	 */
	public static final String DATE_2 = "yyyy-MM-dd";

	/**
	 * yyyy/MM/dd
	 */
	public static final String DATE_3 = "yyyy/MM/dd";

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATE_TIME_1 = "yyyy-MM-dd HH:mm:ss";

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATE_TIME_2 = "yyyyMMddHHmmss";

	/**
	 * MM/dd
	 */
	public static final String DATE_OF_MONTH_DAY_1 = "MM/dd";
	
	/**
	 * 格式化日期<br>
	 * 預設格式: yyyy-MM-dd
	 * 
	 * @param dt
	 * @return String
	 */
	public static String fromDate(Date dt) {
		return fromDate(dt, DATE_2);
	}
	
	/**
	 * 格式化日期
	 * 
	 * @param dt
	 * @param pattern
	 * @return String
	 */
	public static String fromDate(Date dt, String pattern) {
		if(dt == null) {
			return StringUtils.EMPTY;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(dt); 
	}
	
	/**
     * 將日期字串轉為Date物件<br>
     * default: yyyy-MM-dd HH:mm:ss
     * 
     * @param dateTime
     * @return Date
     */
    public static Date toDateTime(String dateTime) {
        return toDate(dateTime, DATE_TIME_1);
    }

    /**
     * 將日期字串轉為Date物件<br>
     * default: yyyy-MM-dd
     * 
     * @create by Adam
     * @create date: Dec 7, 2017
     *
     * @param dateStr
     * @return
     */
	public static Date toDate (String dateStr) {
		return toDate(dateStr, DATE_2);
	}

    /**
     * 將日期字串轉為Date物件
     * 
     * @param date 日期字串
     * @param date_format 輸入的字串為甚麼樣的格式
     * @return Date
     */
    public static Date toDate(String date, String date_format) {
		if (StringUtils.isBlank(date)) {
			return null;
		}

        try {
        	SimpleDateFormat format = new SimpleDateFormat(date_format);
            Date s_date = format.parse(date);
            
            return s_date;
        } catch (ParseException e) {
        	e.printStackTrace();
        }
        
        return new Date();
    }
    
    /**
     * 取得當下系統時間毫秒
     * 
     * @create by Adam
     * @create date: Apr 21, 2017
     *
     * @return String
     */
    public static String getMillisTime() {
    	return String.valueOf(System.currentTimeMillis());
    }
	
	/**
	 * 取得年度最後一天
	 * 
	 * @create by Adam
	 * @create date: Dec 7, 2017
	 *
	 * @return
	 */
	public static Date getLastDateOfYear () {
		 Calendar calendar = Calendar.getInstance();
		 int currentYear = Integer.valueOf(getCurrentYear());
		 calendar.set(Calendar.YEAR, currentYear + 1);
		 calendar.set(Calendar.DAY_OF_YEAR, 0);
		 
		 return calendar.getTime();
	}
	
	/**
	 * 取得當年年分字串
	 * 
	 * @create by Adam
	 * @create date: Dec 7, 2017
	 *
	 * @return
	 */
	public static String getCurrentYear () {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		return String.valueOf(year);
	}
	
	/**
	 * 取得當年年分字串
	 * 
	 * @create by Adam
	 * @create date: Dec 7, 2017
	 *
	 * @return
	 */
	public static String getYear (Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int year = cal.get(Calendar.YEAR);
		return String.valueOf(year);
	}

	/**
	 * 取得當年度的6/15日
	 * 
	 * @create by Adam
	 * @create date: Jan 2, 2018
	 *
	 * @return
	 */
	public static Date getHalfYear () {
		String year = DateUtil.getCurrentYear();
		return DateUtil.toDate(year + "-06-30");
	}
	
	/**
	 * 將兩個日期相減併回傳相差日期
	 * 
	 * @create by Adam
	 * @create date: Dec 7, 2017
	 *
	 * @param d1
	 * @param d2
	 * @return 相差天數
	 */
	public static double minus (Date d1, Date d2) {
		long diff = Math.abs(d2.getTime() - d1.getTime());
		long diffDays = (diff / (24 * 60 * 60 * 1000)) + 1;
		
		return diffDays;
	}
  
}
