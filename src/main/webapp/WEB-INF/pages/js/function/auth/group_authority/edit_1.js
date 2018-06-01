//# sourceURL=edit_1.js
var dataList = [];

$(function() {
	var data = JsonUtil.fromJson(dataJson);
	
	$('label#roleId').html(data.roleId);
	InitUtil.initTable('dataTable', wrapCheckType(data.buttons), setting());
	InitUtil.checkAllBox('dataTable', '_checkAll', setCheckAllEvent);
	setTableClickEvent('dataTable');
	decidedCheckAll();
});

function setTableClickEvent (tableName) {
	var table = $('table#' + tableName).DataTable();
	$('table#dataTable tbody').on('click', 'td:first-child', function () {
		var checked = $(this).find('input').prop('checked');
		var row = table.row($(this)).data();
		checked ? pushFunction(row) : removeFunction(row);
	});
}

function setCheckAllEvent (thiz) {
	var checkAll = $(thiz).prop('checked');
	var $table = $('table#dataTable');
	var table = $table.DataTable();
	
	if (checkAll) {
		var $checkeds = $table.find('input[type=checkbox]:checked');
		$.each($checkeds, function () {
			var row = table.row($(this).closest('tr')).data();
			
			if (row == undefined) {return;}
			
			removeFunction(row);
			pushFunction(row);
		});
	} else {
		var $nonCheckeds = $table.find('input[type=checkbox]:not(:checked)');
		$.each($nonCheckeds, function () {
			var row = table.row($(this).closest('tr')).data();
			
			if (row == undefined) {return;}
			
			removeFunction(row);
		});
	}
}

function pushFunction (row) {
	var roleId = $('label#roleId').html();
	var buttonId = row.buttonId;
	var isTaked = false;
	
	$.each(dataList, function (i) {
		var buttonId2 = this.buttonId;
		isTaked = buttonId == buttonId2 ? true : false;
	});

	if (!isTaked) {
		row.roleId = roleId;
		dataList.push(row);	
	}
}

function removeFunction (row) {
	var buttonId = row.buttonId;
	$.each(dataList, function (i) {
		var buttonId2 = this.buttonId;
		if (buttonId == buttonId2) {
			dataList.splice(i, 1);
		}
	});
}

function wrapCheckType (buttons) {
	var checked = [];
	$.each(buttons, function () {
		var buttonType = this.buttonType;
		if (buttonType == 'checked') {
			this.buttonType = '<center><input type="checkbox" checked /></center>';
			pushFunction(this);
		}
		checked.push(this);
	});
	
	return checked;
}

function decidedCheckAll () {
	var $body = $('table#dataTable tbody');
	var checkedSize = $body.find('input[type=checkbox]:checked').length;
	var inputSize = $body.find('input').length;
	
	if (checkedSize == inputSize) {
		$('input#_checkAll').prop('checked', true);
	}
}

function edit (action) {
	var data = {
		roleId: $('label#roleId').html(),
		dataList: dataList
	}
	
	JsonUtil.post(action, data, function (resData) {
		bootbox.alert({
			size: 'small',
			message: resData.msg, 
			callback: function () {
				dataList = [];
				$('table#dataTable').DataTable().destroy();
				InitUtil.initTable('dataTable', wrapCheckType(resData.data), setting());
				InitUtil.checkAllBox('dataTable', '_checkAll', setCheckAllEvent);
				decidedCheckAll();
			}
		});
	});
}

function setting () {
	return [ // 欄位設定
         	{orderable: false, targets: [0]}, // 選擇關閉哪個欄位排序功能(  )
			{targets: '_all', render: function (data, type, row, mate) {
				return data;
			}},
			{
				targets: [0],
				width: '4%',
				title: '<center><input id="_checkAll" type="checkbox" /></center>',
				data: 'buttonType', 
				className: 'dt-nowrap', 
				defaultContent: '<center><input type="checkbox" /></center>'
			},
			{targets: [1], title: '功能名稱', data: 'functionName', className: 'dt-nowrap'}, 
			{targets: [2], title: '頁面名稱', data: 'pageType', className: 'dt-nowrap'}, 
			{targets: [3], title: '按鈕名稱', data: 'buttonName', className: 'dt-nowrap'}
		];
}

/** Button Interface
 ================================================================*/
function update (action) {
	switch (action) {
		case '/Function/GroupAuthority/editFlow/edit':
			edit(action);
			break;
	}
}

function lastStep (action) {
	var data = JsonUtil.fromJson(queryCondition);
	ViewResolver.refresh(action + '/queryList', data);
}