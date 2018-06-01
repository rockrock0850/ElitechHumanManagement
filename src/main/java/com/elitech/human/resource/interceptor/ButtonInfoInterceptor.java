package com.elitech.human.resource.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.elitech.human.resource.tx.LoginTx;
import com.elitech.human.resource.util.JsonUtil;
import com.elitech.human.resource.vo.redis.ButtonVO;
import com.elitech.human.resource.vo.redis.RedisVO;

public class ButtonInfoInterceptor implements HandlerInterceptor {
	
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
		List<ButtonVO> buttons = user.getPermissions().getButtons();
		request.setAttribute("buttons", JsonUtil.toJson(buttons));
		
		return true;
	}

}
