<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<script>
//Global parameters
var queryCondition = '${queryCondition}';
var locationJson = '${location}';
</script>
<script type="text/javascript" src="${contextPath}/pages/js/function/human/location_management/edit_1.js?v=${now}"></script>

<form id='editLocationForm' class="form-center">
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-search"></i>駐點基本資料修改
		</div>
		<div class="card-body overflow_x">
			<div class='row'>
				<div class='col-md-6'>
					<div class='form-group'>
						<div class='row'>
							<div class='col-5'>
								<label class='form_label'>駐點位置代碼</label>
							</div>
							<div class='col-2'>
								<label id='locationId'></label>
								<input class="form-control hidden" type="text" name='locationId' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-5'>
								<label class='form_label'>業務</label>
							</div>
							<div class='col-md-4'>
								<input class="form-control" type="text" name='sales' />
							</div>
							<div class='col-md-2'>
								<a class="btn btn-primary" href='javascript: showAllSales();'>查詢</a>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-5'>
								<label class='form_label'>上班時間</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='workingTime'></select>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-5'>
								<label class='form_label'>午休時間</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='lunchTime'></select>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class='col-md-6'>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-5'>
								<label class='form_label'>駐點名稱</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='locationName' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-5'>
								<label class='form_label'>駐點PM</label>
							</div>
							<div class='col-md-4'>
								<input class="form-control" type="text" name='pm' />
							</div>
							<div class='col-md-2'>
								<a class="btn btn-primary" href='javascript: showAllPMs();'>查詢</a>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-5'>
								<label class='form_label'>下班時間</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='offTime'></select>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
<%@ include file='/WEB-INF/pages/module/search_modal.jsp' %>