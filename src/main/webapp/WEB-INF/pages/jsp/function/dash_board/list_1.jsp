<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<script>
// Global parameters
var dataJson = '${data}';
</script>
<script type="text/javascript" src="${contextPath}/pages/js/function/dash_board/list_1.js?v=${now}"></script>

<form class="form-center">
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-table"></i>簽核中假單
		</div>
		<div class="card-body overflow_x">
			<table id="signProcessTable" class="table table-striped table-bordered table-hover">
				<thead></thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-table"></i>已申請假單
		</div>
		<div class="card-body overflow_x">
			<table id="formSearchTable" class="table table-striped table-bordered table-hover">
				<thead></thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
</form>