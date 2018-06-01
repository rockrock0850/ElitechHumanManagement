<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<script>
//Global parameters
var queryCondition = '${queryCondition}';
var deptJson = '${dept}';
</script>
<script type="text/javascript" src="${contextPath}/pages/js/function/human/dept_management/edit_1.js?v=${now}"></script>

<form id='editDeptManagementForm' class="form-center">
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-search"></i>部門新增
		</div>
		<div class="card-body overflow_x">
			<div class='row'>
				<div class='col-md-6'>
					<div class='form-group'>
						<div class='row'>
							<div class='col-3'>
								<label class='form_label'>部門編號</label>
							</div>
							<div class='col-2'>
								<label id='departmentId'></label>
								<input class="form-control hidden" type="text" name='departmentId' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>上層部門編號</label>
							</div>
							<div class='col-md-4'>
								<input class="form-control" type="text" name='parentDepartmentId' />
							</div>
							<div class='col-md-2'>
								<a class="btn btn-primary" href='javascript: showOtherDepts();'>查詢</a>
							</div>
						</div>
					</div>
				</div>
				<div class='col-md-6'>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>部門名稱</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='departmentName' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>部門主管</label>
							</div>
							<div class='col-md-4'>
								<input class="form-control" type="text" name='departmentManager' />
							</div>
							<div class='col-md-2'>
								<a class="btn btn-primary" href='javascript: showAllManagers();'>查詢</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
<%@ include file='/WEB-INF/pages/module/search_modal.jsp' %>