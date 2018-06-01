package com.elitech.human.resource.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * 重新導頁工具
 * 
 * @create by Adam
 */
public class RedirectUtil {
	private Map<String, String> parameterMap = new HashMap<String, String>();
	private HttpServletResponse response;

	/**
	 * 使用POST的方式重新導向其他系統或功能
	 * 
	 * @create by Adam
	 * @create date: Nov 5, 2017
	 *
	 * @param response
	 * @param url
	 * @param jsonData 
	 * @throws IOException
	 */
	public RedirectUtil (HttpServletResponse response, String url, String jsonData) throws IOException {
	   this.response = response;
	   setParameter("data", jsonData);
	   send(url);
	}

	private void setParameter (String key, String value) {
		this.parameterMap.put(key, value);
	}

	private void send (String url) throws IOException {
		String html = getRedirectHtml(url);
		this.response.setContentType("text/html");
		PrintWriter out = this.response.getWriter();
		out.println(html);
		out.flush();
		out.close();
	}

	private String getRedirectHtml (String url) {
		StringBuilder html = new StringBuilder();
		html.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		html.append("<HTML>");
		html.append(" <HEAD><TITLE>sender</TITLE></HEAD>");
		html.append(" <BODY>");
		html.append("<form name=\"redirectForm\" action=\"" + url + "\" method=\"post\">");
		
		Iterator<String> it = this.parameterMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			html.append("<input type=\"hidden\" name=\"" + key + "\" value=\"" + this.parameterMap.get(key) + "\"/>");
		}
		
		html.append("</from>");
		html.append("<script>window.document.redirectForm.submit();</script> ");
		html.append(" </BODY>");
		html.append("</HTML>");
		
		return html.toString();
	}
}
