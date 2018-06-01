<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<script type="text/javascript" src="${contextPath}/pages/js/function/human/query_1.js?v=${now}"></script>

<form id='createEmpForm' class="form-center" action="" method="post" enctype="multipart/form-data">
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-table"></i>經歷
		</div>
		<div class="card-body overflow_x">
			<table id="dataTable" class="table table-striped table-bordered table-hover">
				<thead></thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-table"></i>基本資料
		</div>
		<div class="card-body overflow_x">
			<div class='row'>
				<div class='col-md-6'>
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-3 col-xs-5'>
								<img id="blah" src="${contextPath}/pages/img/default_photo.gif" alt="上傳大頭照" />
							</div>
							<div class='col-md-7 col-xs-5 upload_button'>
								<a class='btn btn-default btn-primary pull-right' type='button' href='javascript: uploadPhoto();'>上傳照片</a>
							</div>
						</div>
						<input id='photo' class="form-control-file hidden" type="file" name='photo' onchange="readURL(this);" />
					</div>
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-3'>
								<i class="fa fa-star color_red" aria-hidden="true"></i>
								<label class='form_label'>員工編號</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='empId' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<i class="fa fa-star color_red" aria-hidden="true"></i>
								<label class='form_label'>中文姓名</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='chiName' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>英文姓名</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='engName' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>生日</label>
							</div>
							<div class='col-md-3 birth_year'>
								<select id='years' class='form-control'></select>
							</div>
							<div class='col-md-3 birth_month'>
								<select id='months' class='form-control'></select>
							</div>
							<div class='col-md-3 birth_day'>
								<select id='days' class='form-control'></select>
							</div>
							<input id='birth' class='hidden' type="text" name='birth' />
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>性別</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='sex'>
										<option value=''>請選擇</option>
										<option value='0'>男</option>
										<option value='1'>女</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>婚姻</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='marriage'>
										<option value=''>請選擇</option>
										<option value='0'>已婚</option>
										<option value='1'>未婚</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>役畢</label>
							</div>
							<div class='col-md-3'>
								<div class="radio radio-info">
								    <input id='servedY' type="radio" value='Y' checked />
								    <label for="servedY">是</label>
								</div>
							</div>
							<div class='col-md-3'>
								<div class="radio radio-info">
								    <input id='servedN' type="radio" value='N' />
								    <label for="servedN">否</label>
								</div>
							</div>
						    <input id='served' class='hidden' type="text" name="served">
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<i class="fa fa-star color_red" aria-hidden="true"></i>
								<label class='form_label'>員工類別</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='empTypeId'>
										<option value=''>請選擇</option>
										<option value='0'>類別1</option>
										<option value='1'>類別2</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>學歷</label>
							</div>
							<div class='col-md-4'>
								<div class="form-group">
									<select class='form-control' name='eduId'>
										<option value=''>請選擇</option>
										<option value='0'>國小</option>
										<option value='1'>國中</option>
										<option value='2'>高中</option>
										<option value='3'>大學</option>
										<option value='4'>四技二專</option>
										<option value='5'>碩士</option>
										<option value='6'>博士</option>
									</select>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class='col-md-6'>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>學校名稱</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='schoolName' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>系所</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='subject' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>住家電話</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='phone' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>行動電話</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='cellphone' />
							</div>
						</div>
					</div>
					<div class="form-group">
					    <div class="row">
					        <div class="col-md-3">
				                <label class="form_label">通訊地址</label>
					        </div>
					        <div class="col-md-3">
					            <div class="md-form">
					                <input id="form51" type="text" class="form-control" name='zipcode' placeholder="郵遞區號" />
					            </div>
					        </div>
					    </div>
					</div>
					<div class="form-group">
					    <div class="row">
					        <div class="col-md-3"></div>
					        <div class="col-md-7">
					            <div class="md-form">
					                <input id="form61" type="text" class="form-control" name='address' placeholder="地址" />
					            </div>
					        </div>
					    </div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<i class="fa fa-star color_red" aria-hidden="true"></i>
								<label class='form_label'>電子郵件</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='email' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>緊急聯絡人</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='emergencyContact' />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class='row'>
							<div class='col-md-3'>
								<label class='form_label'>連絡電話</label>
							</div>
							<div class='col-md-7'>
								<input class="form-control" type="text" name='emergencyPhone' />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="card mb-3">
		<div class="card-header">
			<i class="fa fa-table"></i>年資
		</div>
		<div class="card-body overflow_x">
			<div class="form-group">
				<div class='row'>
					<div class='col-md-2'>
						<label class='pull-right'>到職日期</label>
					</div>
					<div class='col-md-5'>
						<div class="input-group date datepicker" data-provide="datepicker">
						    <input type="text" class="form-control" name='onBoard' />
						    <div class="input-group-addon">
						        <span class="glyphicon glyphicon-th"></span>
						    </div>
						</div> 
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class='row'>
					<div class='col-md-3'>
						<label class='pull-right'>休假(特休)多給天數</label>
					</div>
					<div class='col-md-6'>
						<input type="text" name='bonusDay' />
						<label class='pull-right'>天</label>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>