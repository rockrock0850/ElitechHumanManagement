//# sourceURL=list_1.js
$(function() {
	InitUtil.initTable('dataTable', JsonUtil.fromJson(dataList), 
			[ // 欄位設定
   	         	{orderable: false, targets: [0]}, // 選擇關閉哪個欄位排序功能(  )
				{targets: '_all', render: function (data, type, full, mate) {
					return data;
				}},
				{targets: [0], title: '群組帳號', data: 'roleId', className: 'dt-nowrap'}, 
				{targets: [1], title: '群組名稱', data: 'roleName', className: 'dt-nowrap'}, 
				{targets: [2], title: '狀態', data: 'statusDisplay', className: 'dt-nowrap'}
			]);
	setTableClickEvent('dataTable');
});

function setTableClickEvent (tableName) {
	var table = $('table#' + tableName).DataTable();
	$('table#dataTable tbody').on('click', 'td', function () {
		var row = table.row($(this)).data();
		ViewResolver.refresh('/Function/UserRoleMap/editFlow', row);
	});
}

/** Button Interface
 ================================================================*/
function lastStep (action) {
	ViewResolver.refresh(action);
}