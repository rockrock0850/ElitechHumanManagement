//# sourceURL=index.js
var updateData = [];

$(function() {
	$('#dl-menu').dlmenu();
	
	initTable();
	setButtonClickEvent();
});

var setButtonClickEvent = function () {
	
	// 將在table上更動過的row data存起來
	$('button#saveButton').click(function () {
		var table = $('table#dataTable').DataTable();
		var $inputs = $('div#result').find('input');
		var length = $inputs.length;
		
		if (length < 2) {return;}
		
		$.each($inputs, function () {
			var $this = $(this);
			var $row = $(this).parent();
			var value = $this.val();
			var $td = $this.parent();
			var tableElement = $this.attr('aria-controls');
			
			if (tableElement != undefined) {return;}
			
			table.cell($td).data(value).draw();
			
			var row = table.row($row).data();
			setUpdateData(row);
		});
		
		console.log(updateData);
	});
}

var setUpdateData = function (row) {
	var rowId = row.rowId;
	
	if (updateData.length == 0) {
		updateData.push(row);
	} else {
		var size = updateData.length;
		
		$.each(updateData, function (i) {
			var storedId = this.rowId;
			
			if (storedId == rowId) {return false;}
			
			if (i == size-1) {updateData.push(row);}
		});
	}
}

//初始化DataTable
var initTable = function() {
	var table = $('table#dataTable').DataTable({
		dom:
			'<"row"<"pull-left"f><"pull-right"l>>' +
			'<"row"<"scrollX"t>>' +
			'<"row"<"pull-left"i><"pull-right"p>>',
		paging: true, // 翻頁功能
		lengthChange: true, // 改變每頁顯示數據數量
		pageLength: 10, // 顯示10筆換下一頁
		searching: true, // 過濾功能
		ordering: true, // 排序功能
		info: true, // 頁腳信息
		autoWidth: false, // 自動寬度
		//responsive: true,
		data: getData(), // 注入資料
		columnDefs: [ // 欄位設定
			{orderable: false, targets: [0]}, // 選擇關閉哪個欄位排序功能(  )
			{targets: '_all', render: function (data, type, full, mate) {
				// console.log(data);
				return data;
			}},
			{targets: [0], title: '', data: 'rowId', className: 'hidden'}, 
			{targets: [1], title: '部門名稱', data: 'depName', className: 'dt-nowrap'}, 
			{targets: [2], title: '員工編號', data: 'empNumber', className: 'dt-nowrap'}, 
			{targets: [3], title: '員工姓名 ', data: 'empName', className: 'dt-nowrap'}, 
			{targets: [4], title: '假別', data: 'leaveCategory', className: 'dt-nowrap'}, 
			{targets: [5], title: '代理人', data: 'agent', className: 'dt-nowrap'}, 
			{targets: [6], title: '起訖時間', data: 'period', className: 'dt-nowrap'}, 
			{targets: [7], title: '日數', data: 'days', className: 'dt-nowrap'}, 
			{targets: [8], title: '證明', data: 'prove', className: 'dt-nowrap'}, 
			{targets: [9], title: '事由', data: 'cause', className: 'dt-nowrap'}, 
			{targets: [10], title: '備註', data: 'note', className: 'dt-nowrap'},
		]
	});
	
	setTableClickEvent();
}

var setTableClickEvent = function () {
	$('table#dataTable tbody').on('click', 'td:not(:first-child)', function () {
		var $this = $(this);
		var childrens = $this.children().length;
	    var $row = $this.parent();
	    var table = $('table#dataTable').DataTable();
	    var cell = table.cell(this).data();
	    var column = $row.children().index($this);
	    var rowId = table.row($row).data().rowId;
	    var fieldName = rowId + '-' + column;

		if (childrens > 0) {return;}
	    
	    $this.html('').html('<input id="' + fieldName + '" name="' + fieldName + '" value="' + cell + '" />');
	    $('input#' + fieldName).focus();
	 
	    //console.log(table.cell(this));
	});
}

//取得資料表的資料
var getData = function() {
	var data = [];
	for(var i = 0; i < 5; i++) {
		var object = {};

		object.rowId = i + '1234'; 
		object.depName = 'HR'; 
		object.empNumber = 'Red'; 
		object.empName = '人事'; 
		object.leaveCategory = '特休'; 
		object.agent = 'Helen'; 
		object.period = '2017-08-01~2017-09-01'; 
		object.days = '31'; 
		object.prove = '不需要'; 
		object.cause = '不需要'; 
		object.note = '申請中'; 
		data.push(object);
	}
	
	for(var i = 0; i < 20; i++) {
		var object = {};

		object.rowId = i + '4321'; 
		object.depName = 'HR'; 
		object.empNumber = 'Adam'; 
		object.empName = '人事'; 
		object.leaveCategory = '特休'; 
		object.agent = 'Adnrew'; 
		object.period = '2018-08-01~2018-09-01'; 
		object.days = '31'; 
		object.prove = '不需要'; 
		object.cause = '不需要'; 
		object.note = '未申請'; 
		data.push(object);
	}
	
	return data;
}