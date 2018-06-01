<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>
<jsp:useBean id="now" class="java.util.Date" />
<script type="text/javascript" src="${contextPath}/pages/js/layout/material/breadcrumb.js?v=${now}"></script>

<ol id='breadcrumb' class="breadcrumb">
	<li class="breadcrumb-item"><a href="${contextPath}/Entries/DashBoard">首頁</a></li>
	<c:forEach var='breadcrumb' items='${breadcrumbs}' varStatus="status">
		<li class="breadcrumb-item active">${breadcrumb.name}</li>
	</c:forEach>
</ol>