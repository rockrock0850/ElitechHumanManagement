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
		ViewResolver.refresh('/Function/DeptManagement/editFlow', row);
	});
}

function delet (action) {
	var $table = $('table#dataTable');
	var table = $table.DataTable();
	var checkboxs = $table.find('input[type=checkbox]:checked');
	var dataList = [];
	
	$.each(checkboxs, function () {
		var $row = $(this).closest('tr');
		var data = table.row($row).data();
		
		if (data == undefined) {return;}
		
		var status = data.status;
		data.status = setStatus(status, true);;
		dataList.push(data);
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
				{targets: [1], title: '部門編號', data: 'departmentId', className: 'dt-nowrap'}, 
				{targets: [2], title: '部門名稱', data: 'departmentName', className: 'dt-nowrap'}, 
				{targets: [3], title: '上層部門編號 ', data: 'parentDepartmentId', className: 'dt-nowrap'}, 
				{targets: [4], title: '部門主管 ', data: 'departmentManager', className: 'dt-nowrap'}
			];
}

function setStatus (status, isNum) {
	if (isNum) {
		status = (status == '1' || status == '啟用') ? '1' : '0';
	} else {
		status = (status == '1' || status == '啟用') ? '啟用' : '停用';
	}
	
	return status;
}

/** Button Interface
 ================================================================*/
function del (action) {
	switch (action) {
		case '/Function/DeptManagement/delete':
			delet(action);
			break;
	}
}

function lastStep (action) {
	ViewResolver.refresh(action);
}