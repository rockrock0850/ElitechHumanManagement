<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<script>
// Global parameters
var dataList = '${dataList}';
</script>
<script type="text/javascript" src="${contextPath}/pages/js/function/setting/holiday_category/list_1.js?v=${now}"></script>

<form class="form-center">
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-table"></i>假別資料瀏覽
		</div>
		<div class="card-body overflow_x">
			<table id="dataTable" class="table table-striped table-bordered table-hover">
				<thead></thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
</form>