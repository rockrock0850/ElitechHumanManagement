package com.elitech.human.resource.util;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * 
 * @create by Adam
 */
public class BeanUtil {
	
	/**
	 * 將物件轉成Map<br>
	 * P.S. 遇到Date型態請自行手動塞值
	 * 
	 * @create by Adam
	 * @create date: Dec 13, 2017
	 *
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> fromObj (Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		
//		SimpleModule module = new SimpleModule();
//		module.addSerializer(Date.class, new CustomDateSerializer());
//		mapper.registerModule(module);
		
		return mapper.convertValue(obj, Map.class);
	}
	
	/**
	 * 複製串列<br>
	 * P.S. 不支援巢狀物件
	 * 
	 * @create by Adam
	 * @create date: Nov 9, 2017
	 *
	 * @param froms 來源串列
	 * @param clazz 目標串列內容物件
	 * @return List<目標物件>
	 * @throws Exception
	 */
	public static <T>List<T> copyList (List<?> froms, Class<T> clazz) throws Exception {
		String[] ignore = {};
		return copyList(froms, clazz, ignore);
	}
	
	/**
	 * 
	 * 
	 * @create by Adam
	 * @create date: Nov 9, 2017
	 *
	 * @param froms 來源串列
	 * @param clazz 目標串列內容物件
	 * @param ignore 忽略的欄位名稱
	 * @return List<目標物件>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T>List<T> copyList (List<?> froms, Class<T> clazz, String[] ignore) throws Exception {
		if (froms == null || froms.isEmpty()) {return Collections.EMPTY_LIST;}
		
		List<T> tos = new ArrayList<>();
		
		for (Object from : froms) {
			Object to = clazz.newInstance();
			BeanUtils.copyProperties(from, to, ignore);
	        tos.add((T) to);
		}
		
		return tos;
	}
	
	/*
	 * 
	 */
	private static class CustomDateSerializer extends StdSerializer<Date> {
	 
	    /**
		 * 
		 */
		private static final long serialVersionUID = 567776610111042781L;

		public CustomDateSerializer() {
	        this(null);
	    }
	 
	    @SuppressWarnings("unchecked")
		public CustomDateSerializer(Class<?> t) {
	        super((Class<Date>) t);
	    }
	     
	    @Override
	    public void serialize (Date value, JsonGenerator gen, SerializerProvider arg2) throws IOException, JsonProcessingException {
	    	// gen.writeObject(value);
	    }
	    
	}
	
	/**
	 * 取得空白或null欄位名稱
	 * 
	 * @create by Adam
	 * @create date: Nov 9, 2017
	 *
	 * @param source
	 * @return
	 */
	public static String[] getBlankProp (Object source) {
		BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null){
				emptyNames.add(pd.getName());
				continue;
			}

			String str = srcValue.toString();
			if (StringUtils.isEmpty(str)) {
				emptyNames.add(pd.getName());
			}
		}
		String[] result = new String[emptyNames.size()];
		
		return emptyNames.toArray(result);
	}
	
	/**
	 * 取得null欄位名稱
	 * 
	 * @create by Adam
	 * @create date: Nov 9, 2017
	 *
	 * @param source
	 * @return
	 */
	public static String[] getNullProp (Object source) {
		BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null){
				emptyNames.add(pd.getName());
			}
		}
		String[] result = new String[emptyNames.size()];
		
		return emptyNames.toArray(result);
	}
	
}


