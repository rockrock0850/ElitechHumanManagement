<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<script type="text/javascript">
// Global params
var applyLeaveJson = '${applyLeave}';
</script>
<script type="text/javascript" src="${contextPath}/pages/js/function/leave/paid_leave/add_1.js?v=${now}"></script>

<form id='addPaidLeaveForm' class="form-center">
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-search"></i>補休轉入
		</div>
		<div class="card-body overflow_x">
			<div class='row full_width_align'>
				<div class='col-md-12'>
					<div class="form-group">
					    <div class="row">
					        <div class="col-md-4">
					        	<label class='form_label'>選擇檔案</label>
				        	</div>
							<div class='col-md-3 pull-right'>
								<input id='fileName' class="form-control" type="text" disabled="disabled" placeholder="請選擇檔案" />
							</div>
							<div class='col-md-1'>
								<a class='btn btn-primary' href='javascript: upload();'>選擇</a>
							</div>
					    </div>
						<input class="form-control-file hidden" type="file" name='document' onchange="read(this);" />
					</div>
				</div>
			</div>
		</div>
	</div>
</form>