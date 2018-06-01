<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<script type="text/javascript">
// Global params
var editVO = '${formSearchEditVO}';
</script>
<script type="text/javascript" src="${contextPath}/pages/js/function/leave/form_search/edit_1.js?v=${now}"></script>

<form id='editFormSearch' class="form-center">
	<input class="form-control hidden" type="text" name='leaveNo' />
	<input class="form-control hidden" type="text" name='emptype' />
	<input class="form-control hidden" type="text" name='departmentId' />

	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-search"></i>已申請假單修改
		</div>
		<div class="card-body overflow_x">
			<div class='row'>
				<div class='col-md-6'>
					<div class='form-group'>
						<div class='row'>
							<div class='col-4'>
								<label class='form_label'>目前程序</label>
							</div>
							<div class='col-7'>
								<label id='processDisplay'></label>
								<input class="form-control hidden" type="text" name='process' />
							</div>
						</div>
					</div>
					<div class='form-group'>
						<div class='row'>
							<div class='col-4'>
								<label class='form_label'>狀態</label>
							</div>
							<div class='col-7'>
								<label id='approveStatusDisplay'></label>
								<input class="form-control hidden" type="text" name='approveStatus' value='approve' />
							</div>
						</div>
					</div>
					<div class='form-group'>
						<div class='row'>
							<div class='col-4'>
								<label class='form_label'>員工編號</label>
							</div>
							<div class='col-7'>
								<label id='empId'></label>
								<input class="form-control hidden" type="text" name='empId' />
							</div>
						</div>
					</div>
					<div class='form-group'>
						<div class='row'>
							<div class='col-4'>
								<label class='form_label'>部門</label>
							</div>
							<div class='col-7'>
								<label id='departmentName'></label>
								<input class="form-control hidden" type="text" name='departmentName' />
							</div>
						</div>
					</div>
					<div class='form-group'>
						<div class='row'>
							<div class='col-4'>
								<label class='form_label'>假別</label>
							</div>
							<div class='col-7'>
								<label id='leaveName'></label>
								<input class="form-control hidden" type="text" name='leavetypeId' />
							</div>
						</div>
					</div>
				</div>
				<div class='col-md-6'>
					<div class="form-group app_hidden">
						<div class='row'>
							<div class='col-md-12'>&nbsp;</div>
							<div class='col-md-12'>&nbsp;</div>
							<div class='col-md-12'>&nbsp;</div>
							<div class='col-md-12'>&nbsp;</div>
						</div>
					</div>
					<div class='form-group'>
						<div class='row'>
							<div class='col-4'>
								<label class='form_label'>員工姓名</label>
							</div>
							<div class='col-7'>
								<label id='empCname'></label>
								<input class="form-control hidden" type="text" name='empCname' />
							</div>
						</div>
					</div>
					<div class='form-group'>
						<div class='row'>
							<div class='col-4'>
								<label class='form_label'>職稱</label>
							</div>
							<div class='col-7'>
								<label id='jobtitleName'></label>
								<input class="form-control hidden" type="text" name='jobtitleName' />
							</div>
						</div>
					</div>
					<div class="form-group app_hidden">
						<div class='row'>
							<div class='col-md-12'>&nbsp;</div>
							<div class='col-md-12'>&nbsp;</div>
						</div>
					</div>
				</div>
			</div>
			<div class='row full_width_align'>
				<div class='col-md-12'>
					<div class="form-group">
					    <div class="row">
					        <div class="col-md-4">
				                <label class="form_label">起始時間</label>
					        </div>
							<div class='col-md-8 date_time'>
								<div class='row'>
									<div class='col-md-3'>
										<div id='begindate' class="input-group date datepicker" data-provide="begindate">
										    <input type="text" class="form-control" name='begindate' />
										    <div class="input-group-addon">
										        <span class="glyphicon glyphicon-th"></span>
										    </div>
										</div> 
									</div>
									<div class='col-md-2'>
										<select class='form-control' name='beginhour'></select>
									</div>
									<div class='col-md-1 colon'>:</div>
									<div class='col-md-2 minus'>
										<select class='form-control' name='beginmin'></select>
									</div>
								</div>
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
				                <label class="form_label">結束時間</label>
					        </div>
							<div class='col-md-8 date_time'>
								<div class='row'>
									<div class='col-md-3'>
										<div id='enddate' class="input-group date datepicker" data-provide="enddate">
										    <input type="text" class="form-control" name='enddate' />
										    <div class="input-group-addon">
										        <span class="glyphicon glyphicon-th"></span>
										    </div>
										</div>
									</div> 
									<div class='col-md-2'>
										<select class='form-control' name='endhour'></select>
									</div> 
									<div class='col-md-1 colon'>:</div>
									<div class='col-md-2 minus'>
										<select class='form-control' name='endmin'></select>
									</div> 
								</div>
							</div>
					    </div>
					</div>
				</div>
			</div>
			<div class='row full_width_align app_hidden'>
				<div class='col-md-12'>
					<div class="form-group">
					    <div class="row">
					        <div class="col-md-4">
				                <label class="form_label">共</label>
					        </div>
							<div class='col-md-8'>
								<div class='row'>
									<div class='col-md-1 color_red'>
										<a id='leavedays'>0</a>
										<input class='hidden' name='leavedays' />
									</div> 
									<div class='col-md-1'><a>日</a></div> 
									<div class='col-md-1 color_red'>
										<a id='leavehours'>0</a>
										<input class='hidden' name='leavehours' />
									</div> 
									<div class='col-md-1'><a>時</a></div> 
								</div>
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
				                <label class="form_label">事由</label>
					        </div>
							<div class='col-md-8'>
								<textarea rows="5" cols="40" style='resize: none;' name='reason'></textarea>
							</div>
					    </div>
					</div>
				</div>
			</div>
			<div id='isSubstitute' class='row full_width_align'>
				<div class='col-md-12'>
					<div class="form-group">
					    <div class="row">
					        <div class="col-md-4">
				                <label class="form_label">代理人</label>
					        </div>
							<div class='col-md-2'>
								<input class="form-control" type="text" name='substitute' />
							</div>
							<div class='col-md-1'>
								<a class="btn btn-primary" href='javascript: showAllAgencys();'>查詢</a>
							</div>
					    </div>
					</div>
				</div>
			</div>
			<div id='isDocument' class='row full_width_align'>
				<div class='col-md-12'>
					<div class="form-group">
					    <div class="row">
					        <div class="col-md-4">
				                <label class="form_label">附件名稱</label>
					        </div>
							<div class='col-md-3 pull-right'>
								<input id='fileName' class="form-control" type="text" disabled="disabled" placeholder="請選擇檔案" />
							</div>
							<div class='col-md-1'>
								<a class='btn btn-primary' href='javascript: upload();'>重新上傳</a>
							</div>
					    </div>
						<input class="form-control-file hidden" type="file" name='document' onchange="read(this);" />
					</div>
				</div>
			</div>
			<div id='document' class='row full_width_align'>
				<div class='col-md-12'>
					<div class="form-group">
					    <div class="row">
					        <div class="col-md-4">
				                <a class="form_label" href='javascript: showDocument();'>檢視原附件</a>
					        </div>
					    </div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
<%@ include file='/WEB-INF/pages/module/search_modal.jsp' %>
<%@ include file='/WEB-INF/pages/module/image_modal.jsp' %>