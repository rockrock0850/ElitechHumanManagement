<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<script type="text/javascript" src="${contextPath}/pages/js/function/setting/holiday_category/find_1.js?v=${now}"></script>

<form id='findHolidayCategoryForm' class="form-center">
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-search"></i>假別資料查詢
		</div>
		<div class="card-body overflow_x">
			<div class='row'>
				<div class='col-md-6'>
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>假別代號</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='leavetypeId' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>計假單位</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='unit'></select>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>適用性別</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='gender'></select>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class='col-md-6'>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>假別名稱</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='leaveName' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>適用員工別</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='emptype'></select>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>參考年資</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='isYears'></select>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>