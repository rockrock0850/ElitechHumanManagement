//# sourceURL=list_1.js
$(function() {
	InitUtil.initTable('dataTable', JsonUtil.fromJson(dataList), setting());
	InitUtil.checkAllBox('dataTable', '_checkAll');
	setTableClickEvent('dataTable');
});

function setTableClickEvent (tableName) {
	var table = $('table#' + tableName).DataTable();
	$('table#dataTable tbody').on('click', 'td:not(:first-child)', function () {
		var row = table.row($(this)).data();
		ViewResolver.refresh('/Function/EmpManagement/editFlow', row);
	});
}

function delet (action) {
	var $table = $('table#dataTable');
	var table = $table.DataTable();
	var checkboxs = $table.find('input[type=checkbox]:checked');
	var dataList = [];
	
	$.each(checkboxs, function () {
		var $row = $(this).closest('tr');
		var row = table.row($row).data();
		
		if (row == undefined) {return;}

		dataList.push(row);
	});
	
	JsonUtil.post(action, dataList, function (resData) {
		bootbox.alert({
			size: 'small',
			message: resData.msg, 
			callback: function () {
				table.destroy();
				InitUtil.initTable('dataTable', resData.data, setting());
				InitUtil.checkAllBox('dataTable', '_checkAll');
			}
		});
	});
}

function setting () {
	return [ // 欄位設定
	         	{orderable: false, targets: [0]}, // 選擇關閉哪個欄位排序功能(  )
				{targets: '_all', render: function (data, type, full, mate) {
					return data;
				}},
				{
					targets: [0], 
					width: '4%',
					title: '<center><input id="_checkAll" type="checkbox" /></center>',
					data: null, 
					className: 'dt-nowrap', 
					defaultContent: '<center><input type="checkbox" /></center>'
				},
				{targets: [1], title: '員工編號', data: 'empId', className: 'dt-nowrap'}, 
				{targets: [2], title: '員工姓名', data: 'empCname', className: 'dt-nowrap'}, 
				{targets: [3], title: '英文姓名 ', data: 'empEname', className: 'dt-nowrap'},
				{targets: [4], title: '員工別 ', data: 'emptypeDisplay', className: 'dt-nowrap'}, 
				{targets: [5], title: '員工狀態', data: 'jobstatusDisplay', className: 'dt-nowrap'}, 
				{targets: [6], title: '部門 ', data: 'departmentName', className: 'dt-nowrap'}, 
				{targets: [7], title: '駐點位置 ', data: 'locationName', className: 'dt-nowrap'}
			];
}

/** Button Interface
 ================================================================*/
function del (action) {
	switch (action) {
		case '/Function/EmpManagement/delete':
			delet(action);
			break;
	}
}

function lastStep (action) {
	ViewResolver.refresh(action);
}