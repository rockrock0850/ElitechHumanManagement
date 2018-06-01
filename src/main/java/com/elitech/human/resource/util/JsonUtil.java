package com.elitech.human.resource.util;

import java.awt.image.BufferedImage;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * JSON格式處理工具
 * 
 * @create by Adam Yeh
 */
public class JsonUtil {

	/**
	 * 將傳入物件轉成JSON字串
	 * 
	 * @param src
	 * @return String
	 * @throws ParseException
	 */
	public static String toJson (Object src) {
		return toJson(src, DateUtil.DATE_TIME_1);
	}

	/**
	 * 將傳入物件轉成JSON字串
	 * 
	 * @param src
	 * @return String
	 * @throws ParseException
	 */
	public static String toJson (Object src, String datePattern) {
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat(datePattern);
		builder.setExclusionStrategies(new Exclusion());
		builder.disableHtmlEscaping();

		Gson gsonObj = builder.serializeNulls().create();

		return gsonObj.toJson(src);
	}

	/**
	 * 將傳JSON字串入轉成物件
	 * 
	 * @param json
	 * @param clazz
	 * @return <T>
	 */
	public static <T> T fromJson (String json, Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(json, clazz);
	}

	/**
	 * 將傳JSON字串入轉成 List of vo.
	 * 
	 * @param json
	 * @param clazz
	 * @return List<T>
	 */
	public static <T> List<T> fromJsonToList (String json, Class<?> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(json, new ParameterTypeWrapper<>(clazz));
	}

	/**
	 * 參數化型別 封裝物件
	 * 
	 */
	private static class ParameterTypeWrapper<T> implements ParameterizedType {

		private Class<?> wrapped;

		ParameterTypeWrapper (Class<?> wrapped) {
			this.wrapped = wrapped;
		}

		@Override
		public Type[] getActualTypeArguments () {
			return new Type[] { wrapped };
		}

		@Override
		public Type getRawType () {
			return List.class;
		}

		@Override
		public Type getOwnerType () {
			return null;
		}

	}

	/**
	 * 日期解析器
	 * 
	 * @author PaulChen
	 */
	@SuppressWarnings("unused")
	private static class DateDeserializer implements JsonDeserializer<Date> {

		private SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_2);

		@Override
		public Date deserialize (JsonElement jsElm, Type type, JsonDeserializationContext context) throws JsonParseException {
			String str = jsElm.getAsString();

			if (StringUtils.isNotBlank(str)) {
				try {
					return sdf.parse(str);
				} catch (Exception ignore) {
					return null;
				}
			}

			return null;
		}

	}

	/**
	 * 日期解析器( P.S. 包含「時間」的解析 )
	 * 
	 * @create by Adam
	 * @create date: Apr 28, 2017
	 *
	 */
	@SuppressWarnings("unused")
	private static class DateTimeDeserializer implements JsonDeserializer<Date> {

		private SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_TIME_1);

		@Override
		public Date deserialize (JsonElement jsElm, Type type, JsonDeserializationContext context) throws JsonParseException {
			String str = jsElm.getAsString();

			if (StringUtils.isNotBlank(str)) {
				try {
					return sdf.parse(str);
				} catch (Exception ignore) {
					return null;
				}
			}

			return null;
		}

	}

	/**
	 * BigDecimal 解析器
	 * 
	 * @author PaulChen
	 */
	@SuppressWarnings("unused")
	private static class BigDecimalDeserializer implements JsonDeserializer<BigDecimal> {

		@Override
		public BigDecimal deserialize (JsonElement jsElm, Type type, JsonDeserializationContext context) throws JsonParseException {
			String str = jsElm.getAsString();

			if (StringUtils.isNotBlank(str)) {
				try {
					Double d = Double.parseDouble(str);
					return BigDecimal.valueOf(d);
				} catch (Exception ignore) {
					return null;
				}
			}

			return null;
		}
	}

	/**
	 * JSON 排除（例外）解析器
	 * 
	 * @author PaulChen
	 */
	private static class Exclusion implements ExclusionStrategy {

		private static Set<Class<?>> clazzSt = new HashSet<>();
		{
			clazzSt.add(BufferedImage.class);
		}

		@Override
		public boolean shouldSkipField (FieldAttributes f) {
			return false;
		}

		@Override
		public boolean shouldSkipClass (Class<?> clazz) {
			return clazzSt.contains(clazz);
		}

	}

}
