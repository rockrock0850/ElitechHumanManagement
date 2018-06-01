//# sourceURL=add_1.js
$(function() {
	InitUtil.select('status', 'status');
});

function add (action) {
	var result = Validator.input('addGroupAccountForm', [], {
		roleId: '群組代號',
		status: '狀態',
		roleName: '群組名稱'
	}, true);
	
	if (!result) {return;}
	
	var data = form2object('addGroupAccountForm');
	JsonUtil.post(action, data, function (resData) {
		bootbox.alert({
			size: 'small',
			message: resData.msg, 
			callback: function () {
				var $form = $('form#addGroupAccountForm');
				var inputs = $form.find('input');
				var selects = $form.find('select');
				
				$.each(inputs, function () {$(this).val('');});
				$.each(selects, function () {$(this).val('')});
			}
		});
	});
}

/** Button Interface
 ================================================================*/
function insert (action) {
	switch (action) {
		case '/Function/GroupAccount/addFlow/add':
			add(action);
			break;
	}
}

function lastStep (action) {
	ViewResolver.refresh(action);
}