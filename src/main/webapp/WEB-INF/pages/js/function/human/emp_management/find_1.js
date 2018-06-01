//# sourceURL=find_1.js
$(function() {
	InitUtil.select('emptype', 'emptype');
	InitUtil.select('jobstatus', 'jobstatus');
});

function queryList (action) {
	var data = form2object('findEmpManagementForm');
	ViewResolver.refresh(action, data);
}

function addFlow (action) {
	ViewResolver.refresh(action);
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

/** Button Interface
 ================================================================*/
function query (action) {
	switch (action) {
		case '/Function/EmpManagement/queryList':
			queryList(action);
			break;
	}
}

function insert (action) {
	switch (action) {
		case '/Function/EmpManagement/addFlow':
			addFlow(action);
			break;
	}
}