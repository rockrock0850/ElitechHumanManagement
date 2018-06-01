//# sourceURL=edit_1.js
var dataList = [];

$(function() {
	var data = JsonUtil.fromJson(dataJson);
	
	$('label#roleId').html(data.roleId);
	InitUtil.initTable('dataTable', wrapCheckType(data.userRoleMaps), setting());
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
	var userId = row.userId;
	var isTaked = false;
	
	$.each(dataList, function (i) {
		var userId2 = this.userId;
		isTaked = userId == userId2 ? true : false;
	});

	if (!isTaked) {
		row.roleId = roleId;
		dataList.push(row);	
	}
}

function removeFunction (row) {
	var userId = row.userId;
	$.each(dataList, function (i) {
		var userId2 = this.userId;
		if (userId == userId2) {
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
		users: dataList
	}

	JsonUtil.post(action, data, function (resData) {
		bootbox.alert({
			size: 'small',
			message: resData.msg, 
			callback: function () {
				ViewResolver.refresh('/Function/UserRoleMap/editFlow', data);
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
			{targets: [1], title: '使用者帳號', data: 'userId', className: 'dt-nowrap'}, 
			{targets: [2], title: '使用者名稱', data: 'userName', className: 'dt-nowrap'}, 
			{targets: [3], title: '使用者狀態', data: 'statusDisplay', className: 'dt-nowrap'}
		];
}

/** Button Interface
 ================================================================*/
function update (action) {
	switch (action) {
		case '/Function/UserRoleMap/editFlow/edit':
			edit(action);
			break;
	}
}

function lastStep (action) {
	var data = JsonUtil.fromJson(queryCondition);
	ViewResolver.refresh(action + '/queryList', data);
}