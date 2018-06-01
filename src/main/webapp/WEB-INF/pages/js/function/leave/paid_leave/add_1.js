//# sourceURL=add_1.js
$(function() {});

function upload () {
	$('input[name=document]').trigger('click');
}

// 讀取選擇的檔案並儲存起來
function read (input) {
	if (!input.files && !input.files[0]) {return;}

	var $fileName = $('input#fileName');
	try {
		var file = input.files[0];
		var name = file.name;
		
		var result = Validator.file(file, 'Excel', ['xlsx', 'xls'], true);
		
		if (!result) {throw new Exception();}
		
		FileUtil.readAsFileData('excel', file);
		$fileName.val(name);
	} catch (e) {
		$fileName.val('');
		$('input[name=document]').val('');
	}
}

function add (action) {
	var result = Validator.input('addPaidLeaveForm', [], {
		document: '檔案'
	}, true);
	
	if (!result) {return;}
	
	var file = FileUtil.getFileData();
	FileUtil.upload(action, file, function (resData) {
		bootbox.alert({
			size: 'small',
			message: resData.msg, 
			callback: function () {
				ViewResolver.refresh('/Function/PaidLeave');
			}
		});
	});
}

/** Button Interface
 ================================================================*/
function insert (action) {
	switch (action) {
		case '/Function/PaidLeave/addFlow/add':
			add(action);
			break;
	}
}

function lastStep (action) {
	ViewResolver.refresh(action);
}