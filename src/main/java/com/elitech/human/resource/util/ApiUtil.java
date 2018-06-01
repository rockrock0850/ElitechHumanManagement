package com.elitech.human.resource.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMethod;

import com.elitech.human.resource.constant.Api;
import com.elitech.human.resource.constant.SysStatus;
import com.elitech.human.resource.vo.exception.LogicException;
import com.elitech.human.resource.vo.rest.RequestVO;
import com.elitech.human.resource.vo.rest.ResponseVO;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @create by Adam
 */
public class ApiUtil {
	
	private static Logger log = Logger.getLogger(ApiUtil.class);

	/**
	 * 
	 * @param api
	 * @param data
	 * @param requestMethod
	 * @return
	 * @throws Exception
	 */
	public static ResponseVO call (Api api, RequestVO reqVO, RequestMethod requestMethod) throws Exception {
		URL obj = null;
		HttpURLConnection con = null;
		DataOutputStream wr = null;
		
		String jsonData = reqVO.getData();
		String base64Str = Base64.getEncoder().encodeToString(jsonData.getBytes());
		reqVO.setData(base64Str);

		String action = api.getAction();
		String domain = reqVO.getDomain();
		String url = domain + action;
		String method = requestMethod.toString();
		String reqData = JsonUtil.toJson(reqVO);
		
		switch (StringUtils.upperCase(method)) {
			case "POST":
				obj = new URL(url);
				con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod(method);
				con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				con.setDoOutput(true);
				
				wr = new DataOutputStream(con.getOutputStream());
				wr.write(reqData.getBytes("utf-8"));
				wr.flush();
				wr.close();
				
				break;
				
			case "GET":
				obj = new URL(url + "/" + reqData);
				con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod(method);
				con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				
				break;
				
			case "PUT":
				obj = new URL(url);
				con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod(method);
				con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				con.setDoOutput(true);
				
				wr = new DataOutputStream(con.getOutputStream());
				wr.write(reqData.getBytes("utf-8"));
				wr.flush();
				wr.close();
				
				break;
				
			case "DELETE":
				obj = new URL(url + "/" + reqData);
				con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod(method);
				con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				
				break;
		}
	
		int responseCode = con.getResponseCode();
		log.info("Response Code : " + responseCode);
		
		if (responseCode != 200) {
			throw new LogicException(SysStatus.UNKNOWN_ERROR);
		}
		
		ResponseVO responseVO = new ResponseVO();
		StringBuffer result = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			result.append(inputLine);
		}
		in.close();
		
		responseVO = new ObjectMapper().readValue(result.toString(), ResponseVO.class);
		
		return responseVO;
	}

	public static String getDomain (HttpServletRequest request) {
		String schema = request.getScheme();
		String domain = request.getServerName();
		int port = request.getServerPort();
		String sourceRoot = schema + "://" + domain + ":" + String.valueOf(port);
		
		return sourceRoot;
	}
	
}


