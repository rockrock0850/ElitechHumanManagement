//# sourceURL=list_1.js
$(function() {
	var data = JsonUtil.fromJson(dataJson);
	var signProcesss = data.signProcesss;
	var formSearchs = data.formSearchs;

	InitUtil.initTable('signProcessTable', signProcesss, 
			[ // 欄位設定
	         	{orderable: false, targets: []}, // 選擇關閉哪個欄位排序功能(  )
				{targets: '_all', render: function (data, type, row, mate) {
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

	InitUtil.initTable('formSearchTable', formSearchs, 
			[ // 欄位設定
	         	{orderable: false, targets: []}, // 選擇關閉哪個欄位排序功能(  )
				{targets: '_all', render: function (data, type, row, mate) {
					var workingTime = row.workingTime;
					if (workingTime == undefined) {
						row.workingTime = '';
						row.offTime = '';
						row.lunchTime = '';
					}
					
					return data;
				}},
				{targets: [0], title: '請假單編號', data: 'leaveNo', className: 'dt-nowrap'}, 
				{targets: [1], title: '假別', data: 'leaveName', className: 'dt-nowrap'}, 
				{targets: [2], title: '申請時間 ', data: 'createTime', className: 'dt-nowrap'}, 
				{targets: [3], title: '起始時間', data: 'begin', className: 'dt-nowrap'}, 
				{targets: [4], title: '結束時間', data: 'end', className: 'dt-nowrap'}, 
				{targets: [5], title: '目前程序', data: 'processDisplay', className: 'dt-nowrap'}
			]);
	setSignProcessTableClickEvent();
	setFormSearchTableClickEvent();
});

function setSignProcessTableClickEvent () {
	var $table = $('table#signProcessTable');
	var table = $table.DataTable();
	
	$table.find('tbody').on('click', 'td:not(:first-child)', function () {
		// 不得已之下才在這邊設定functionId, 一般會在點選menu的時候設定
		$('div#container').attr('functionId', 'F000014');
		 
		var row = table.row($(this)).data();
		ViewResolver.refresh('/Function/SignProcess/editFlow', row);
	});
}

function setFormSearchTableClickEvent () {
	var $table = $('table#formSearchTable');
	var table = $table.DataTable();
	
	$table.find('tbody').on('click', 'td:not(:first-child)', function () {
		// 不得已之下才在這邊設定functionId, 一般會在點選menu的時候設定
		$('div#container').attr('functionId', 'F000015');
		
		var row = table.row($(this)).data();
		ViewResolver.refresh('/Function/FormSearch/editFlow', row);
	});
}