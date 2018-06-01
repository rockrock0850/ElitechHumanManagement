//# sourceURL=list_1.js
$(function() {
	InitUtil.initTable('dataTable', JsonUtil.fromJson(dataList), 
			[ // 欄位設定
	         	{orderable: false, targets: []}, // 選擇關閉哪個欄位排序功能(  )
				{targets: '_all', render: function (data, type, full, mate) {
					return data;
				}},
				{targets: [0], title: '請假單編號', data: 'leaveNo', className: 'dt-nowrap'}, 
				{targets: [1], title: '假別', data: 'leaveName', className: 'dt-nowrap'}, 
				{targets: [2], title: '申請時間 ', data: 'createTime', className: 'dt-nowrap'}, 
				{targets: [3], title: '起始時間', data: 'begin', className: 'dt-nowrap'}, 
				{targets: [4], title: '結束時間', data: 'end', className: 'dt-nowrap'}, 
				{targets: [5], title: '目前程序', data: 'processDisplay', className: 'dt-nowrap'}, 
				{targets: [6], title: '狀態', data: 'approveStatusDisplay', className: 'dt-nowrap'}
			]);
	setTableClickEvent('dataTable');
});

function setTableClickEvent (tableName) {
	var table = $('table#' + tableName).DataTable();
	$('table#dataTable tbody').on('click', 'td:not(:first-child)', function () {
		var row = table.row($(this)).data();
		ViewResolver.refresh('/Function/SignProcess/editFlow', row);
	});
}