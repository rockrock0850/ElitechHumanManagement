<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<script>
//Global parameters
var queryCondition = '${queryCondition}';
var employeeJson = '${employee}';
</script>
<script type="text/javascript" src="${contextPath}/pages/js/function/human/emp_management/edit_1.js?v=${now}"></script>

<form id='editEmployeeForm' class="form-center">
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-search"></i>員工基本資料修改
		</div>
		<div class="card-body overflow_x">
			<div class='row'>
				<div class='col-md-6'>
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
					<div class="form-group">
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>員工別</label>
							</div>
							<div class='col-md-5'>
								<div class="form-group">
									<select class='form-control' name='emptype'></select>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>員工狀態</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='jobstatus'></select>
								</div>
							</div>
						</div>
					</div><div class="form-group">
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>婚姻</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='marriage'></select>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>學歷</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='education'></select>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>生日</label>
							</div>
							<div class='col-md-5'>
								<div id='birthday' class="input-group date datepicker" data-provide="birthday">
								    <input type="text" class="form-control" name='birthday' />
								    <div class="input-group-addon">
								        <span class="glyphicon glyphicon-th"></span>
								    </div>
								</div> 
							</div>
						</div>
					</div>
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>學校名稱</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='schoolename' />
							</div>
						</div>
					</div>
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>住家電話</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='tel' placeholder="xx-xxxxxxxx" />
							</div>
						</div>
					</div>
				</div>
				<div class='col-md-6'>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>員工姓名</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='empCname' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>英文姓名</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='empEname' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>性別</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='gender'></select>
								</div>
							</div>
						</div>
					</div>
					<div id='servedColumn' class="form-group">
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>役畢</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='served'></select>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>職稱</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='jobtitle'></select>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group app_hidden">
						<div class='row'>
							<div class='col-md-12'>&nbsp;</div>
							<div class='col-md-12'>&nbsp;</div>
						</div>
					</div>
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>系所</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='subject' />
							</div>
						</div>
					</div>
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>行動電話</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='phone' placeholder="xxxx-xxxxxx" />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class='row full_width_align'>
				<div class='col-md-12 col-xs-6'>
					<div class="form-group">
					    <div class="row">
					        <div class="col-md-4">
				                <label class="form_label">通訊地址</label>
					        </div>
					        <div class="col-md-2">
					            <div class="md-form">
					                <input id="form51" type="text" class="form-control" name='zipcode' placeholder="郵遞區號" />
					            </div>
					        </div>
					         <div class="col-md-5">
					            <div class="md-form">
					                <input id="form61" type="text" class="form-control" name='address' placeholder="地址" />
					            </div>
					        </div>
					    </div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>電子郵件</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='email' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>公司郵件</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='companyEmail' />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class='row'>
				<div class='col-md-6'>
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>緊急連絡人</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='emergencycontact' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>部門編號</label>
							</div>
							<div class='col-md-4'>
								<input class="form-control" type="text" name='departmentId' />
							</div>
							<div class='col-md-2'>
								<a class="btn btn-primary" href='javascript: showAllDepts();'>查詢</a>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>到職日期</label>
							</div>
							<div class='col-md-5'>
								<div id='onBoard' class="input-group date datepicker" data-provide="onBoard">
								    <input type="text" class="form-control" name='onBoard' />
								    <div class="input-group-addon">
								        <span class="glyphicon glyphicon-th"></span>
								    </div>
								</div> 
							</div>
						</div>
					</div>
				</div>
				<div class='col-md-6'>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>緊急連絡人電話</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='emergencytel' placeholder="xxxx-xxxxxx" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-4'>
								<label class='form_label'>駐點位置代碼</label>
							</div>
							<div class='col-md-4'>
								<input class="form-control" type="text" name='locationId' />
							</div>
							<div class='col-md-2'>
								<a class="btn btn-primary" href='javascript: showAllLocations();'>查詢</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
<%@ include file='/WEB-INF/pages/module/search_modal.jsp' %>