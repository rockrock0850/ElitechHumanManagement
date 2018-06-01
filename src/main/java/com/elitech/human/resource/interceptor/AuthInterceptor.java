package com.elitech.human.resource.interceptor;

import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.elitech.human.resource.tx.LoginTx;
import com.elitech.human.resource.util.AesCryptUtil;
import com.elitech.human.resource.util.ApiUtil;
import com.elitech.human.resource.util.PropUtil;
import com.elitech.human.resource.util.RedirectUtil;
import com.elitech.human.resource.vo.redis.ButtonVO;
import com.elitech.human.resource.vo.redis.MenuVO;
import com.elitech.human.resource.vo.redis.PermissionsVO;
import com.elitech.human.resource.vo.redis.RedisVO;

public class AuthInterceptor implements HandlerInterceptor {
	
	@Autowired
	private LoginTx tx;
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		String sessionId = "";
		String data = (String) request.getParameter("data");

		if (StringUtils.isBlank(data)) {
			sessionId = (String) session.getAttribute("sessionId");
		} else {
			sessionId = AesCryptUtil.decrypt(data);
		}
		
		boolean isLoggedIn = tx.isLoggedIn(sessionId);
		if (!isLoggedIn) {
			String sourceRoot = ApiUtil.getDomain(request);
			login(sourceRoot, response);
			
			return false;
		}
		
		session.setAttribute("sessionId", sessionId);

		RedisVO user = tx.findLoginInfo(sessionId);
		PermissionsVO permissions = user.getPermissions();
		List<MenuVO> menus = permissions.getMenus();
		String redisTimeout = PropUtil.getProperty("redis.timeout");
		
		if (menus == null || menus.isEmpty()) {
			RedisVO redisVO = findUserPermission(user);
			tx.saveLoginInfo(sessionId, redisVO, Integer.valueOf(redisTimeout));	
		} else {
			tx.setExpire(sessionId, Integer.valueOf(redisTimeout));
		}
		
		return true;
	}
	
	private RedisVO findUserPermission (RedisVO user) throws Exception {
		String userId = user.getAccountId();
		List<MenuVO> menus = tx.findUserMenus(userId);
		List<ButtonVO> buttons = tx.findUserButtons(userId);
		
		PermissionsVO permissions = new PermissionsVO();
		permissions.setMenus(menus);
		permissions.setButtons(buttons);
		user.setPermissions(permissions);
		
		return user;
	}

	private void login (String sourceRoot, HttpServletResponse response) throws Exception {
		String appId = PropUtil.getProperty("app.id");
		String appKey = AesCryptUtil.getEncryptAppKey();
		
		JSONObject json = new JSONObject();
		json.put("sourceRoot", sourceRoot);
		json.put("appId", appId);
		json.put("appKey", appKey);
		
		byte[] b = Base64.getEncoder().encode(json.toString().getBytes());
		String data = new String(b, "utf-8");
		
		new RedirectUtil(response, sourceRoot + "/ElitechLoginManagement/Entries/LoginManagement", data);
	}

}
