//# sourceURL=edit_1.js
$(function() {
	updateColumns('editDeptManagementForm');
});

function updateColumns (name) {
	var dept = JsonUtil.fromJson(deptJson);
	var $form = $('form#' + name);
	var $label = $form.find('label#departmentId');
	var $departmentId = $form.find('input[name=departmentId]');
	var $parentDepartmentId = $form.find('input[name=parentDepartmentId]');
	var $departmentName = $form.find('input[name=departmentName]');
	var $departmentManager = $form.find('input[name=departmentManager]');

	$label.html(dept.departmentId);
	$departmentId.val(dept.departmentId);
	$parentDepartmentId.val(dept.parentDepartmentId);
	$departmentName.val(dept.departmentName);
	$departmentManager.val(dept.departmentManager);
}

function showOtherDepts () {
	var dept = form2object('editDeptManagementForm');
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
			$('input[name=parentDepartmentId]').val(departmentId);
			SearchModal.hide();
		});
		
		SearchModal.show();
	});
}

function showAllManagers () {
	JsonUtil.post('/Common/findOtherEmployees', '', function (resData) {
		SearchModal.init('部門主管查詢', resData.data, 
				[// 欄位設定
		         	{orderable: false, targets: [0]}, // 選擇關閉哪個欄位排序功能(  )
					{targets: '_all', render: function (data, type, row, mate) {
						return data;
					}},
					{targets: [0], title: '員工編號', data: 'empId', className: 'dt-nowrap'}, 
					{targets: [1], title: '員工姓名', data: 'empCname', className: 'dt-nowrap'}, 
					{targets: [2], title: '英文姓名 ', data: 'empEname', className: 'dt-nowrap'}, 
					{targets: [3], title: '電子郵件 ', data: 'email', className: 'dt-nowrap'}, 
					{targets: [4], title: '手機號碼 ', data: 'phone', className: 'dt-nowrap'}
				]
		);

		SearchModal.setTableClickEvent(function (row) {
			var empId = row.empId;
			$('input[name=departmentManager]').val(empId);
			SearchModal.hide();
		});
		
		SearchModal.show();
	});
}

function edit (action) {
	var result = Validator.input('editDeptManagementForm', 
			['parentDepartmentId', 'departmentManager'], 
			{
				departmentName: '部門名稱'
			}, true);

	result = Validator.length('editDeptManagementForm', 'departmentId', '部門編號', 5, result);
	result = validParentDeptId(result);
	
	if (!result) {return;}
	
	var data = form2object('editDeptManagementForm');
	JsonUtil.post(action, data, editCallback);
}

function editCallback (resData) {
	bootbox.alert({
		size: 'small',
		message: resData.msg, 
		callback: function () {
			ViewResolver.refresh('/Function/DeptManagement/queryList', resData.data);
		}
	});
}

function validParentDeptId (result) {
	if (!result) {return false;}
	
	var deptId = $('input[name=departmentId]').val();
	var parent = $('input[name=parentDepartmentId]').val();
	
	if (deptId != parent) {return true;}

	bootbox.alert({
		size: 'middle',
		message: '上層部門編號不可與本部門編號相同!'
	});
	
	return false;
}

/** Button Interface
 ================================================================*/
function update (action) {
	switch (action) {
		case '/Function/DeptManagement/editFlow/edit':
			edit(action);
			break;
	}
}

function lastStep (action) {
	var data = JsonUtil.fromJson(queryCondition);
	ViewResolver.refresh(action + '/queryList', data);
}