//# sourceURL=list_1.js
$(function() {
	InitUtil.initTable('dataTable', JsonUtil.fromJson(dataList), setting());
	setTableClickEvent('dataTable');
});

function setTableClickEvent (tableName) {
	var table = $('table#' + tableName).DataTable();
	$('table#dataTable tbody').on('click', 'td', function () {
		var row = table.row($(this)).data();
		ViewResolver.refresh('/Function/UserAuthority/editFlow', row);
	});
}

function setting () {
	return [ // 欄位設定
	         	{orderable: false, targets: [0]}, // 選擇關閉哪個欄位排序功能(  )
				{targets: '_all', render: function (data, type, full, mate) {
					return data;
				}},
				{targets: [0], title: '使用者帳號', data: 'userId', className: 'dt-nowrap'}, 
				{targets: [1], title: '使用者名稱', data: 'userName', className: 'dt-nowrap'}, 
				{targets: [2], title: '狀態', data: 'statusDisplay', className: 'dt-nowrap'}
			];
}

/** Button Interface
 ================================================================*/
function lastStep (action) {
	ViewResolver.refresh(action);
}