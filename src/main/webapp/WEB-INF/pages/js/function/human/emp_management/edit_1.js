//# sourceURL=edit_1.js
var ignore = [];
var validate = {};

$(function() {
	InitUtil.datePicker('birthday');
	InitUtil.datePicker('onBoard');
	InitUtil.select('emptype', 'emptype');
	InitUtil.select('jobstatus', 'jobstatus');
	InitUtil.select('education', 'education');
	InitUtil.select('gender', 'gender');
	InitUtil.select('jobtitle', 'jobtitle');
	InitUtil.select('marriage', 'marriage');
	InitUtil.select('served', 'served');
	setGenderChangeEvent();
	updateColumns('editEmployeeForm');
});

function setGenderChangeEvent () {
	$('select[name=gender]').change(function () {
		var gender = $(this).val();
		var $servedColumn = $('div#servedColumn');
		
		gender == 2 ? $servedColumn.hide() : $servedColumn.show();
		if (gender == 2) {
			$servedColumn.hide();
			ignore = ['locationId', 'served'];
			validate = {
					empId: '員工編號',
					email: '電子郵件',
					companyEmail: '公司郵件',
					empCname: '員工姓名',
					empEname: '英文姓名',
					gender: '性別',
					emptype: '員工別',
					birthday: '生日',
					marriage: '婚姻',
					departmentId: '部門編號',
					jobstatus: '員工狀態',
					jobtitle: '職稱',
					onBoard: '到職日期',
					education: '學歷',
					schoolename: '學校名稱',
					subject: '系所',
					tel: '住家電話',
					phone: '行動電話',
					zipcode: '郵遞區號',
					address: '地址',
					emergencycontact: '緊急連絡人',
					emergencytel: '緊急連絡人電話'
				}
		} else {
			$servedColumn.show();
			ignore = ['locationId'];
			validate = {
					empId: '員工編號',
					email: '電子郵件',
					companyEmail: '公司郵件',
					empCname: '員工姓名',
					empEname: '英文姓名',
					gender: '性別',
					emptype: '員工別',
					birthday: '生日',
					marriage: '婚姻',
					served: '役畢',
					departmentId: '部門編號',
					jobstatus: '員工狀態',
					jobtitle: '職稱',
					onBoard: '到職日期',
					education: '學歷',
					schoolename: '學校名稱',
					subject: '系所',
					tel: '住家電話',
					phone: '行動電話',
					zipcode: '郵遞區號',
					address: '地址',
					emergencycontact: '緊急連絡人',
					emergencytel: '緊急連絡人電話'
				}
		}
	});
}

function updateColumns (name) {
	var employee = JsonUtil.fromJson(employeeJson);
	var $form = $('form#' + name);
	var $label = $form.find('label#empId');
	var $empId = $form.find('input[name=empId]');
	var $emptype = $form.find('select[name=emptype]');
	var $jobstatus = $form.find('select[name=jobstatus]');
	var $marriage = $form.find('select[name=marriage]');
	var $education = $form.find('select[name=education]');
	var $birthday = $form.find('input[name=birthday]');
	var $schoolename = $form.find('input[name=schoolename]');
	var $tel = $form.find('input[name=tel]');
	var $empCname = $form.find('input[name=empCname]');
	var $empEname = $form.find('input[name=empEname]');
	var $gender = $form.find('select[name=gender]');
	var $served = $form.find('select[name=served]');
	var $jobtitle = $form.find('select[name=jobtitle]');
	var $subject = $form.find('input[name=subject]');
	var $phone = $form.find('input[name=phone]');
	var $zipcode = $form.find('input[name=zipcode]');
	var $address = $form.find('input[name=address]');
	var $email = $form.find('input[name=email]');
	var $companyEmail = $form.find('input[name=companyEmail]');
	var $emergencycontact = $form.find('input[name=emergencycontact]');
	var $onBoard = $form.find('input[name=onBoard]');
	var $emergencytel = $form.find('input[name=emergencytel]');
	var $locationId = $form.find('input[name=locationId]');
	var $departmentId = $form.find('input[name=departmentId]');
	
	$label.html(employee.empId);
	$empId.val(employee.empId);
	$emptype.val(employee.emptype);
	$jobstatus.val(employee.jobstatus);
	$marriage.val(employee.marriage);
	$education.val(employee.education);
	$birthday.val(employee.birthday.split(' ')[0]);
	$schoolename.val(employee.schoolename);
	$tel.val(employee.tel);
	$empCname.val(employee.empCname);
	$empEname.val(employee.empEname);
	$gender.val(employee.gender).change();
	$served.val(employee.served);
	$jobtitle.val(employee.jobtitle);
	$subject.val(employee.subject);
	$phone.val(employee.phone);
	$zipcode.val(employee.zipcode);
	$address.val(employee.address);
	$email.val(employee.email);
	$companyEmail.val(employee.companyEmail);
	$emergencycontact.val(employee.emergencycontact);
	$onBoard.val(employee.onBoard.split(' ')[0]);
	$emergencytel.val(employee.emergencytel);
	$locationId.val(employee.locationId);
	$departmentId.val(employee.departmentId);
	
	if (employee.isUsed) {
		$onBoard.attr('disabled', true);
	}
}

function edit (action) {
	var result = Validator.input('editEmployeeForm', ignore, validate, true);

	result = Validator.english('editEmployeeForm', 'empId', '員工編號', result);
	result = Validator.english('editEmployeeForm', 'empEname', '英文姓名', result);
	result = Validator.telephone('editEmployeeForm', 'tel', '住家電話', result);
	result = Validator.phone('editEmployeeForm', 'phone', '行動電話', result);
	result = Validator.email('editEmployeeForm', 'email', '電子郵件', result);
	result = Validator.email('editEmployeeForm', 'companyEmail', '公司郵件', result);
	result = Validator.zipcode('editEmployeeForm', 'zipcode', '郵遞區號', result);
	result = Validator.phone('editEmployeeForm', 'emergencytel', '緊急連絡人電話', result);
	result = Validator.length('editEmployeeForm', 'departmentId', '部門編號', 5, result);
	
	if (!result) {return;}
	
	var data = form2object('editEmployeeForm');
	JsonUtil.post(action, data, function (resData) {
		bootbox.alert({
			size: 'small',
			message: resData.msg, 
			callback: function () {
				ViewResolver.refresh('/Function/EmpManagement/queryList', resData.data);
			}
		});
	});
}

function showAllDepts () {
	var dept = form2object('editEmployeeForm');
	JsonUtil.post('/Common/findOtherDepts', dept, function (resData) {
		SearchModal.init('上層部門編號查詢', resData.data, 
				[// 欄位設定
		         	{orderable: false, targets: [0]}, // 選擇關閉哪個欄位排序功能(  )
					{targets: '_all', render: function (data, type, row, mate) {
						return data;
					}},
					{targets: [0], title: '部門編號', data: 'departmentId', className: 'dt-nowrap'}, 
					{targets: [1], title: '部門名稱', data: 'departmentName', className: 'dt-nowrap'}, 
					{targets: [2], title: '上層部門編號 ', data: 'parentDepartmentId', className: 'dt-nowrap'}, 
					{targets: [3], title: '部門主管 ', data: 'departmentManager', className: 'dt-nowrap'}
				]
		);
		
		SearchModal.setTableClickEvent(function (row) {
			var departmentId = row.departmentId;
			$('input[name=departmentId]').val(departmentId);
			SearchModal.hide();
		});
		
		SearchModal.show();
	});
}

function showAllLocations () {
	JsonUtil.post('/Common/findAllLocations', '', function (resData) {
		SearchModal.init('駐點位置查詢', resData.data, 
				[// 欄位設定
		         	{orderable: false, targets: [0]}, // 選擇關閉哪個欄位排序功能(  )
					{targets: '_all', render: function (data, type, row, mate) {
						return data;
					}},
					{targets: [0], title: '駐點位置代碼', data: 'locationId', className: 'dt-nowrap'}, 
					{targets: [1], title: '駐點名稱', data: 'locationName', className: 'dt-nowrap'}, 
					{targets: [2], title: '業務 ', data: 'sales', className: 'dt-nowrap'}, 
					{targets: [3], title: '駐點PM ', data: 'pm', className: 'dt-nowrap'}
				]
		);

		SearchModal.setTableClickEvent(function (row) {
			var locationId = row.locationId;
			$('input[name=locationId]').val(locationId);
			SearchModal.hide();
		});
		
		SearchModal.show();
	});
}

/** Button Interface
 ================================================================*/
function update (action) {
	switch (action) {
		case '/Function/EmpManagement/editFlow/edit':
			edit(action);
			break;
	}
}

function lastStep (action) {
	var data = JsonUtil.fromJson(queryCondition);
	ViewResolver.refresh(action + '/queryList', data);
}