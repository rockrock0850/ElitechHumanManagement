<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<script>
//Global parameters
var queryCondition = '${queryCondition}';
var holidaysJson = '${holidays}';
</script>
<script type="text/javascript" src="${contextPath}/pages/js/function/setting/holiday_emp/edit_1.js?v=${now}"></script>

<div class="card mb-3">
	<div class="card-header">
		<i class="fa fa-search"></i>員工假別可休天數修改
	</div>
	<div class="card-body overflow_x">
		<form id='editHolidayEmpHeaderForm' class="form-center">
			<div class='row'>
				<div class='col-md-6'>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>適用員工別</label>
							</div>
							<div class='col-md-5'>
								<div class="form-group">
									<select id='emptype' class='form-control' name='emptype'></select>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class='col-md-6'>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>年度資料</label>
							</div>
							<div class='col-md-3'>
								<input class="form-control" type="text" name='leaveYear' />
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<form id='editHolidayEmpContentForm' style='overflow-x: auto'></form>
	</div>
</div>