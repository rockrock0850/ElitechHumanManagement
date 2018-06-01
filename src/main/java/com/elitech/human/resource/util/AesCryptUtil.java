package com.elitech.human.resource.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

/**
 * AES加解密工具
 * 
 * @create by Adam
 */
public class AesCryptUtil {

	/**
	 * Base64 decode -> AES decode<br>
	 * 使用預設key值解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String decrypt (String data) throws Exception {
		String iv = PropUtil.getProperty("aes.iv");
		String key = PropUtil.getProperty("aes.key");
		
		return decrypt(iv, key, data);
	}

	/**
	 * Base64 decode -> AES decode
	 * 
	 * @param iv
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String decrypt (String iv, String key, String data) throws Exception {
		if(StringUtils.isBlank(iv) || StringUtils.isBlank(key) || StringUtils.isBlank(data)){
			return "";
		}
		
		byte[] decode = Base64.getDecoder().decode(data);
		
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
		
		byte[] doFinal = cipher.doFinal(decode);
		data = new String(doFinal, "utf-8");
		
		return data;
	}

	/**
	 * AES encode -> Base64 encode<br>
	 * 使用預設key值加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public static String encrypt (String data) throws Exception {
		String iv = PropUtil.getProperty("aes.iv");
		String key = PropUtil.getProperty("aes.key");
		
		return encrypt(iv, key, data);
	}

	/**
	 * AES encode -> Base64 encode
	 * 
	 * @param iv
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public static String encrypt (String iv, String key, String data) throws Exception {
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
		
		Cipher ciper = Cipher.getInstance("AES/CBC/PKCS5Padding");
		ciper.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

		byte[] doFinal = ciper.doFinal(data.getBytes());
		data = Base64.getEncoder().encodeToString(doFinal);
		
		return data;
	}

	public static String getEncryptAppKey () throws Exception {
		String appKey = PropUtil.getProperty("app.key");
		String encryptKey = AesCryptUtil.encrypt(appKey);
		String millisTime = DateUtil.getMillisTime();
		encryptKey = millisTime + "|" + encryptKey;
		
		return encryptKey;
	}
	
}


