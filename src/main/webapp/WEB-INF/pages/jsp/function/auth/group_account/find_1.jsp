<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<script type="text/javascript" src="${contextPath}/pages/js/function/auth/group_account/find_1.js?v=${now}"></script>

<form id='findGroupAccountForm' class="form-center">
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-search"></i>群組帳號查詢
		</div>
		<div class="card-body overflow_x">
			<div class='row'>
				<div class='col-md-6'>
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>群組代號</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='roleId' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>狀態</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='status'></select>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class='col-md-6'>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>群組名稱</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='roleName' />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>