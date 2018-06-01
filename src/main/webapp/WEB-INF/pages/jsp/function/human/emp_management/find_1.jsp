<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<script type="text/javascript" src="${contextPath}/pages/js/function/human/emp_management/find_1.js?v=${now}"></script>

<form id='findEmpManagementForm' class="form-center">
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-search"></i>員工基本資料查詢
		</div>
		<div class="card-body overflow_x">
			<div class='row'>
				<div class='col-md-6'>
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>員工編號</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='empId' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>員工別</label>
							</div>
							<div class='col-md-5'>
								<div class="form-group">
									<select class='form-control' name='emptype'></select>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>員工狀態</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='jobstatus'></select>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class='col-md-6'>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>員工姓名</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='empCname' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>部門編號</label>
							</div>
							<div class='col-md-4'>
								<input class="form-control" type="text" name='departmentId' />
							</div>
							<div class='col-md-2'>
								<a class="btn btn-primary" href='javascript: showAllDepts();'>查詢</a>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>駐點位置代碼</label>
							</div>
							<div class='col-md-4'>
								<input class="form-control" type="text" name='locationId' />
							</div>
							<div class='col-md-2'>
								<a class="btn btn-primary" href='javascript: showAllLocations();'>查詢</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
<%@ include file='/WEB-INF/pages/module/search_modal.jsp' %>