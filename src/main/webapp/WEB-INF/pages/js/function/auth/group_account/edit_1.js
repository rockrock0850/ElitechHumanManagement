//# sourceURL=edit_1.js
$(function() {
	InitUtil.select('status', 'status');
	updateColumns('editGroupAccountForm');
});

function updateColumns (name) {
	var account = JsonUtil.fromJson(accountJson);
	var $form = $('form#' + name);
	var $label = $form.find('label#roleId');
	var $roleId = $form.find('input[name=roleId]');
	var $roleName = $form.find('input#roleName');
	var $status = $form.find('select#status');
	
	$label.html(account.roleId);
	$roleId.val(account.roleId);
	$roleName.val(account.roleName);
	$status.val(account.status);
}

function edit (action) {
	var result = Validator.input('editGroupAccountForm', [], {
		roleId: '群組代號',
		status: '狀態',
		roleName: '群組名稱'
	}, true);
	
	if (!result) {return;}
	
	var data = form2object('editGroupAccountForm');
	JsonUtil.post(action, data, editCallback);
}

function editCallback (resData) {
	bootbox.alert({
		size: 'small',
		message: resData.msg, 
		callback: function () {
			ViewResolver.refresh('/Function/GroupAccount/queryList', resData.data);
		}
	});
}

/** Button Interface
 ================================================================*/
function update (action) {
	switch (action) {
		case '/Function/GroupAccount/editFlow/edit':
			edit(action);
			break;
	}
}

function lastStep (action) {
	var data = JsonUtil.fromJson(queryCondition);
	ViewResolver.refresh(action + '/queryList', data);
}