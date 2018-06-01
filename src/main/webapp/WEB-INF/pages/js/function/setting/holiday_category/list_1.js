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
		ViewResolver.refresh('/Function/HolidayCategory/editFlow', row);
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
				{targets: [1], title: '假別代號', data: 'leavetypeId', className: 'dt-nowrap'}, 
				{targets: [2], title: '假別名稱', data: 'leaveName', className: 'dt-nowrap'}, 
				{targets: [3], title: '計假單位', data: 'unitDisplay', className: 'dt-nowrap'}, 
				{targets: [4], title: '員工類別', data: 'emptypeDisplay', className: 'dt-nowrap'},
				{targets: [5], title: '適用性別', data: 'genderDisplay', className: 'dt-nowrap'}, 
				{targets: [6], title: '申請限制( 天 )', data: 'expire', className: 'dt-nowrap'},
				{targets: [7], title: '參考年資', data: 'isYearsDisplay', className: 'dt-nowrap'}, 
				{targets: [8], title: '代理限制', data: 'isSubstituteDisplay', className: 'dt-nowrap'},
				{targets: [9], title: '附件需求 ', data: 'isDocumentDisplay', className: 'dt-nowrap'}
			];
}

/** Button Interface
 ================================================================*/
function del (action) {
	switch (action) {
		case '/Function/HolidayCategory/delete':
			delet(action);
			break;
	}
}

function lastStep (action) {
	ViewResolver.refresh(action);
}