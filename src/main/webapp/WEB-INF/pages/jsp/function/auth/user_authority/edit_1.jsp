<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<script>
// Global parameters
var queryCondition = '${queryCondition}';
var dataJson = '${data}';
</script>
<script type="text/javascript" src="${contextPath}/pages/js/function/auth/user_authority/edit_1.js?v=${now}"></script>

<form class="form-center">
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-table"></i>使用者權限修改
		</div>
		<div class="card-body overflow_x">
			<div class='row'>
				<div class='col-5'>
					<label>使用者代號</label>
				</div>
				<div class='col-7'>
					<label id='userId' class='app_offset_60'></label>
				</div>
			</div>
			<hr>
			<table id="dataTable" class="table table-striped table-bordered table-hover">
				<thead></thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
</form>