<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<script type="text/javascript" src="${contextPath}/pages/js/function/auth/user_authority/find_1.js?v=${now}"></script>

<form id='findUserAuthorityForm' class="form-center">
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-search"></i>使用者權限查詢
		</div>
		<div class="card-body overflow_x">
			<div class='row'>
				<div class='col-md-6'>
					<div class='form-group'>
						<div class='row'>
							<div class='col-5'>
								<label class='form_label'>使用者代號</label>
							</div>
							<div class='col-7'>
								<input class="form-control" type="text" name='userId' />
							</div>
						</div>
					</div>
				</div>
				<div class='col-md-6'>
					<div class="form-group">
						<div class='row'>
							<div class='col-5'>
								<label class='form_label'>使用者名稱</label>
							</div>
							<div class='col-7'>
								<input class="form-control" type="text" name='userName' />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>