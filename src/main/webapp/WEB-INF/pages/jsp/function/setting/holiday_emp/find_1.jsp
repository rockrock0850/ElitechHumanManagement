<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<script type="text/javascript" src="${contextPath}/pages/js/function/setting/holiday_emp/find_1.js?v=${now}"></script>

<form id='findHolidayEmpForm' class="form-center">
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-search"></i>員工假別可休天數查詢
		</div>
		<div class="card-body overflow_x">
			<div class='row'>
				<div class='col-md-6'>
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
				</div>
				<div class='col-md-6'></div>
			</div>
		</div>
	</div>
</form>