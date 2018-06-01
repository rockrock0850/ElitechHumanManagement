package com.elitech.human.resource.aop;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.elitech.human.resource.annotations.Page;
import com.elitech.human.resource.util.JsonUtil;

@Aspect
@Controller
public class PageAspact {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Around("execution(* com.elitech.human.resource.controller..*.*(..))")
	public Object around (ProceedingJoinPoint joinPoint) throws Throwable {
		HttpSession session = null;
		
		Object[] argList = joinPoint.getArgs();
		for(Object arg : argList){
			if(arg instanceof HttpServletRequest){
				HttpServletRequest request = (HttpServletRequest) arg;
				session = request.getSession();
			}
		}
		
		Object proceed = joinPoint.proceed();

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Page page = method.getAnnotation(Page.class);
		
		if (proceed == null || page == null) {return proceed;}
		
		if(proceed instanceof ModelAndView){
			ModelAndView mv = (ModelAndView) proceed;
			Map<String, Object> map = mv.getModel();
			map.put("pageType", page.type());
			handleQueryCondition(map, page.type(), session);
		}
		
		return proceed;
	}
	
	// 傳遞搜尋條件, 如果page type = Q則清除條件
	private void handleQueryCondition (Map<String, Object> map, String type, HttpSession session) {
		if (StringUtils.equals(type, "Q")) {
			session.removeAttribute("queryCondition");
		} else {
			Object condition = session.getAttribute("queryCondition");
			map.put("queryCondition", JsonUtil.toJson(condition));
		}
	}

	@AfterThrowing(
		      pointcut = "execution(* com.elitech.human.resource..*.*(..))",
		      throwing = "error")
	public void afterThrow(JoinPoint joinPoint, Throwable error){
		try {
		} catch (Throwable t) {
			log.error(t, t);
		}
	}
}
