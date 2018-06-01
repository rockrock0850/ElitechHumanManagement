<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<!-- User Buttons Top -->
<%@ include file='/WEB-INF/pages/module/user_buttons_top.jsp'%>

<!-- content -->
<div id='content' pageType='${pageType}'>
	<tiles:insertAttribute name="content" />
</div>

<!-- User Buttons Bottom ( for RWD ) -->
<%@ include file='/WEB-INF/pages/module/user_buttons_bottom.jsp'%>
