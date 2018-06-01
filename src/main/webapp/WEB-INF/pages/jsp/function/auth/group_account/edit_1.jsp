<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<script>
//Global parameters
var queryCondition = '${queryCondition}';
var accountJson = '${account}';
</script>
<script type="text/javascript" src="${contextPath}/pages/js/function/auth/group_account/edit_1.js?v=${now}"></script>

<form id='editGroupAccountForm' class="form-center">
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-search"></i>群組帳號修改
		</div>
		<div class="card-body overflow_x">
			<div class='row'>
				<div class='col-md-6'>
					<div class='form-group'>
						<div class='row'>
							<div class='col-3'>
								<label class='form_label'>群組代號</label>
							</div>
							<div class='col-2'>
								<label id='roleId'></label>
								<input class="form-control hidden" type="text" name='roleId' />
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
									<select id='status' class='form-control' name='status'>
										<option value=''>請選擇</option>
										<option value='1'>啟用</option>
										<option value='0'>停用</option>
									</select>
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
								<input id='roleName' class="form-control" type="text" name='roleName' />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>