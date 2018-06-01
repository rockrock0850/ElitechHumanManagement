//# sourceURL=find_1.js
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

function queryList (action) {
	var data = form2object('findDeptManagementForm');
	ViewResolver.refresh(action, data);
}

function addFlow (action) {
	ViewResolver.refresh(action);
}

/** Button Interface
 ================================================================*/
function query (action) {
	switch (action) {
		case '/Function/DeptManagement/queryList':
			queryList(action);
			break;
	}
}

function insert (action) {
	switch (action) {
		case '/Function/DeptManagement/addFlow':
			addFlow(action);
			break;
	}
}