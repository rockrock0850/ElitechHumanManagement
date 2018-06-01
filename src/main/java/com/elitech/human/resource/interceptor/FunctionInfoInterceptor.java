package com.elitech.human.resource.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.elitech.human.resource.tx.LoginTx;
import com.elitech.human.resource.util.JsonUtil;
import com.elitech.human.resource.vo.redis.RedisVO;

public class FunctionInfoInterceptor implements HandlerInterceptor {
	
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
		String key = (String) session.getAttribute("sessionId");
		RedisVO user = tx.findLoginInfo(key);
		request.setAttribute("menus", user.getPermissions().getMenus());
		
		String functionId = request.getParameter("functionId");
		List<Map<String, Object>> breadcrumbs = getBreadcrumbs(request);
		String title = MapUtils.getString(getTitle(breadcrumbs), "name", "首頁");

		request.setAttribute("userName", user.getAccountName());
		request.setAttribute("title", title);
		request.setAttribute("functionId", functionId);
		request.setAttribute("breadcrumbs", breadcrumbs);
		
		return true;
	}

	private List<Map<String, Object>> getBreadcrumbs (HttpServletRequest request) {
		String json = request.getParameter("breadcrumbs");
		return JsonUtil.fromJsonToList(json, Map.class);
	}

	private Map<String, Object> getTitle (List<Map<String, Object>> breadcrumbs) {
		if (breadcrumbs == null) {return null;}
		int size = breadcrumbs.size();
		
		return breadcrumbs.get(size-1);
	}

}
