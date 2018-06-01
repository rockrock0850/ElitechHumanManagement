<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>
<!DOCTYPE html>
<html>
<head>
<%@ include file='/WEB-INF/pages/jsp/layout/material/lib.jsp' %>
<script>
//Global parameters
var func = '${func}';
</script>
<script src="${contextPath}/pages/js/layout/themes/container.js?v=${now}"></script>
<title>${title}</title>
</head>
<body id="page-top" class="fixed-nav sticky-footer bg-dark">
	<!-- Navigation-->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
		<!-- Title -->
		<a class="navbar-brand app_center" href="javascript: index();">
			<img src='${contextPath}/pages/img/company_logo.gif?v=${now}' style='height: 45px;'>
		</a>
		
		<!-- Hamburger -->
		<button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		
		<div id="navbarResponsive" class="collapse navbar-collapse">
			<!-- Menu -->
			<tiles:insertAttribute name="menu" />
			
			<!-- Header -->
			<tiles:insertAttribute name="header" />
		</div>
	</nav>
	<div class="content-wrapper">
	
		<!-- Loader -->
		<div id='loader' class="loader hidden"><img class='loading_img' src="${contextPath}/pages/img/Bars.gif?v=${now}"></div>
	
		<div class="container-fluid">
			<!-- Bread crumbs -->
			<tiles:insertAttribute name="breadcrumbs" />
			
			<!-- Container -->
			<div id='container' functionId='${functionId}' function='${func}'>
				<tiles:insertAttribute name="container" />
			</div>
		</div>
		
		<!-- Footer -->
		<tiles:insertAttribute name="footer" />
		
		<!-- Scroll to Top Button -->
		<a class="scroll-to-top rounded" href="#page-top"> <i class="fa fa-angle-up"></i></a>
	</div>
</body>
</html>