//# sourceURL=add_1.js
$(function() {
});

function showAllDepts () {
	JsonUtil.post('/Common/findAllDepts', '', function (resData) {
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

function add (action) {
	var result = Validator.input('addDeptManagementForm', 
			['parentDepartmentId', 'departmentManager'], 
			{
				departmentId: '部門編號',
				departmentName: '部門名稱'
			}, true);

	result = Validator.length('addDeptManagementForm', 'departmentId', '部門編號', 5, result);
	result = Validator.length('addDeptManagementForm', 'parentDepartmentId', '上層部門編號', 5, result);
	
	if (!result) {return;}
	
	var data = form2object('addDeptManagementForm');
	JsonUtil.post(action, data, function (resData) {
		bootbox.alert({
			size: 'small',
			message: resData.msg, 
			callback: function () {
				var inputs = $('form#addDeptManagementForm').find('input,select');
				$.each(inputs, function () {$(this).val('');});
			}
		});
	});
}

/** Button Interface
 ================================================================*/
function insert (action) {
	switch (action) {
		case '/Function/DeptManagement/addFlow/add':
			add(action);
			break;
	}
}

function lastStep (action) {
	ViewResolver.refresh(action);
}