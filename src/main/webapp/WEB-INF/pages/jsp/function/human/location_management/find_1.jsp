<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<script type="text/javascript" src="${contextPath}/pages/js/function/human/location_management/find_1.js?v=${now}"></script>

<form id='findLocationForm' class="form-center">
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-search"></i>駐點基本資料查詢
		</div>
		<div class="card-body overflow_x">
			<div class='row'>
				<div class='col-md-6'>
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>駐點位置代碼</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='locationId' />
							</div>
						</div>
					</div>
				</div>
				<div class='col-md-6'>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>駐點名稱</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='locationName' />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class='row full_width_align'>
				<div class='col-md-12'>
					<div class="form-group">
					    <div class="row">
					        <div class="col-md-4">
				                <label class="form_label">業務</label>
					        </div>
							<div class='col-md-2'>
								<input class="form-control" type="text" name='sales' />
							</div>
							<div class='col-md-1'>
								<a class="btn btn-primary" href='javascript: showAllSales();'>查詢</a>
							</div>
					    </div>
					</div>
				</div>
			</div>
			<div class='row'>
				<div class='col-md-6'>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-4'>
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
							<div class='col-md-4'>
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
							<div class='col-md-4'>
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