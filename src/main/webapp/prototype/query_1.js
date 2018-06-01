//# sourceURL=employee_management.js
$(function() {
	InitUtil.datePicker('.datepicker');
	InitUtil.yearSelector('years');
	InitUtil.monthSelector('months');
	InitUtil.daySelector('years', 'months', 'days');

	initTable();
	setClickEvent();
});

function setClickEvent () {
	$('button#submitButton').click(function () {
		setBirth();
		setServed();
		setLDAP();
		$('form#createEmpForm').submit();
	});
}

function setLDAP () {
	var ldap = $('input[id^=ldap]:checked').val();
	$('input#ldap').val(ldap);
}

function setServed () {
	var served = $('input[id^=served]:checked').val();
	$('input#served').val(served);
}

function setBirth () {
	var birthYear = $('select#years').val();
	var birthMonth = $('select#months').val();
	var birthDay = $('select#days').val();
	var birth = birthYear + '-' + birthMonth + '-' + birthDay;
	
	$('input#birth').val(birth);
}

function uploadPhoto () {
	$('input#photo').trigger('click');
}

// 讀取選擇的照片轉成圖片顯示出來
function readURL (input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#blah')
                .attr('src', e.target.result)
                .width(100)
                .height(100);
        };

        reader.readAsDataURL(input.files[0]);

        $('#blah').removeAttr('style');
        $('div#photo').removeClass('well');
    }
}

function setUpdateData (row) {
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
function initTable () {
	var table = $('table#dataTable').DataTable({
		scrollX: true, // 是否開啟scroll bar x
		dom:
			'<"dataTables_wrapper container-fluid dt-bootstrap4"' + 
			'<"row"<"col-sm-12 col-md-6"<"pull-left"f>><"col-sm-12 col-md-6"<"pull-right"l>>>' +
			'<"row"<"col-sm-12"t>>' +
			'<"row"<"col-sm-12 col-md-5"i><"col-sm-12 col-md-7"p>>' + 
			'>',
		pagingType: "full_numbers",
		language: {
			paginate: {
				next: '→',
				previous: '←',
				first: '●',
				last: '●'
			}
		},
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
			{targets: [1], title: '現職公司', data: 'depName', className: 'dt-nowrap'}, 
			{targets: [2], title: '部門', data: 'empNumber', className: 'dt-nowrap'}, 
			{targets: [3], title: '職位名稱 ', data: 'empName', className: 'dt-nowrap'}, 
			{targets: [4], title: '開始時間', data: 'leaveCategory', className: 'dt-nowrap'}, 
			{targets: [5], title: '主管', data: 'agent', className: 'dt-nowrap'}
		]
	});
}

//取得資料表的資料
function getData () {
	var data = [];
	for(var i = 0; i < 50; i++) {
		var object = {};

		object.rowId = i + '1234'; 
		object.depName = 'HR'; 
		object.empNumber = 'Red'; 
		object.empName = '人事'; 
		object.leaveCategory = '2017-01-01~2018-01-01'; 
		object.agent = 'Helen';
		
		data.push(object);
	}
	
	for(var i = 0; i < 20; i++) {
		var object = {};

		object.rowId = i + '4321'; 
		object.depName = 'HR'; 
		object.empNumber = 'Adam'; 
		object.empName = '人事'; 
		object.leaveCategory = '2015-01-01~2018-12-01'; 
		object.agent = 'Adnrew'; 

		data.push(object);
	}
	
	return data;
}