//# sourceURL=add_1.js
$(function() {
	InitUtil.select('workingTime', 'hours');
	InitUtil.select('offTime', 'hours');
	InitUtil.select('lunchTime', 'break');
});

function add (action) {
	var result = Validator.input('addLocationForm', [], {
		locationId: '駐點位置代碼',
		locationName: '駐點名稱',
		sales: '業務',
		pm: '駐點PM'
	}, true);
	
	if (!result) {return;}
	
	var data = form2object('addLocationForm');
	JsonUtil.post(action, data, function (resData) {
		bootbox.alert({
			size: 'small',
			message: resData.msg, 
			callback: function () {
				var inputs = $('form#addLocationForm').find('input,select');
				$.each(inputs, function () {$(this).val('');});
			}
		});
	});
}

function showAllSales () {
	JsonUtil.post('/Common/findOtherEmployees', '', function (resData) {
		SearchModal.init('業務編號查詢', resData.data, 
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
			$('input[name=sales]').val(empId);
			SearchModal.hide();
		});
		
		SearchModal.show();
	});
}

function showAllPMs () {
	JsonUtil.post('/Common/findOtherEmployees', '', function (resData) {
		SearchModal.init('業務編號查詢', resData.data, 
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
			$('input[name=pm]').val(empId);
			SearchModal.hide();
		});
		
		SearchModal.show();
	});
}

/** Button Interface
 ================================================================*/
function insert (action) {
	switch (action) {
		case '/Function/LocationManagement/addFlow/add':
			add(action);
			break;
	}
}

function lastStep (action) {
	ViewResolver.refresh(action);
}