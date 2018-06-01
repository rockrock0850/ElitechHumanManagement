<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>
<jsp:useBean id="now" class="java.util.Date" />
<script type="text/javascript" src="${contextPath}/pages/js/layout/material/header.js?v=${now}"></script>

<ul id='header' class="navbar-nav ml-auto">
	<li class="nav-item app_hidden">
		<a class="nav-link mr-lg-2">${userName}&nbsp;您好~</a>
	</li>
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle mr-lg-2" id="messagesDropdown" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> 
			<i class="fa fa-address-card-o"></i>.
			<span class="d-lg-none">基本資料</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="messagesDropdown">
			<div class="dropdown-divider"></div>
			<a class="dropdown-item" href="javascript: resetPassword();">
				<i class="fa fa-fw fa-sign-out"></i>修改密碼
			</a>
		</div>
	</li>
	<li class="nav-item">
		<a class="nav-link" data-toggle="modal" data-target="#logoutModal" data-backdrop="false"> 
			<i class="fa fa-fw fa-sign-out"></i>登出
		</a>
	</li>
</ul>

<!-- Logout Modal -->
<div id="logoutModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="logoutModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title"></h5>
				<button class="close" type="button" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
			</div>
			<div class="modal-body" style='text-align: center;'>
				<label>確定登出!?</label>
			</div>
			<div class="modal-footer">
				<a class="btn btn-secondary" href="#" data-dismiss="modal">取消</a>
				<a class="btn btn-primary" href="javascript: logout();">登出</a>
			</div>
		</div>
	</div>
</div>