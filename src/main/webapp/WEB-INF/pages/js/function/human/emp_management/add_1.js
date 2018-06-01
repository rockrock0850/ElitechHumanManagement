//# sourceURL=add_1.js
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

function showAllDepts () {
	JsonUtil.post('/Common/findAllDepts', '', function (resData) {
		SearchModal.init('部門編號查詢', resData.data, 
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

function add (action) {
	var result = Validator.input('addEmpManagementForm', ignore, validate, true);

	result = Validator.english('addEmpManagementForm', 'empId', '員工編號', result);
	result = Validator.english('addEmpManagementForm', 'empEname', '英文姓名', result);
	result = Validator.telephone('addEmpManagementForm', 'tel', '住家電話', result);
	result = Validator.phone('addEmpManagementForm', 'phone', '行動電話', result);
	result = Validator.email('addEmpManagementForm', 'email', '電子郵件', result);
	result = Validator.email('addEmpManagementForm', 'companyEmail', '公司郵件', result);
	result = Validator.zipcode('addEmpManagementForm', 'zipcode', '郵遞區號', result);
	result = Validator.phone('addEmpManagementForm', 'emergencytel', '緊急連絡人電話', result);
	result = Validator.length('addEmpManagementForm', 'departmentId', '部門編號', 5, result);
	
	if (!result) {return;}
	
	var data = form2object('addEmpManagementForm');
	JsonUtil.post(action, data, function (resData) {
		bootbox.alert({
			size: 'small',
			message: resData.msg, 
			callback: function () {
				var inputs = $('form#addEmpManagementForm').find('input,select');
				
				$.each(inputs, function () {$(this).val('');});
				InitUtil.select('emptype', 'emptype');
				InitUtil.select('jobstatus', 'jobstatus');
				InitUtil.select('education', 'education');
				InitUtil.select('gender', 'gender');
				InitUtil.select('jobtitle', 'jobtitle');
				InitUtil.select('marriage', 'marriage');
				InitUtil.select('served', 'served');
			}
		});
	});
}

/** Button Interface
 ================================================================*/
function insert (action) {
	switch (action) {
		case '/Function/EmpManagement/addFlow/add':
			add(action);
			break;
	}
}

function lastStep (action) {
	ViewResolver.refresh(action);
}