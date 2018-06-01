//# sourceURL=init_util.js
var InitUtil = function () {
	
	/**
	 * 從資料庫取得或客製option值
	 * name: 元件的name屬性值
	 * key: 資料庫param資料表內之欄位值
	 * options: 客製化之option值
	 * P.S. 若給定options值, 可不給定key值
	 */
	var select = function (name, key, options) {
		var $select = $('select[name=' + name + ']');
		$select.html('').append('<option value="">請選擇</option>');
		
		if (options) {
			$.each(options, function () {
				var key = Object.keys(this)[0];
				$select.append('<option value="' + key + '">' + this[key] + '</option>');
			});
			
			return;
		}
		
		var param = {key: key}
		JsonUtil.post('/HtmlElement/select', param, function (resData) {
			var contents = resData.data;
			$.each(contents, function () {
				$select.append('<option value="' + this.value + '">' + this.display + '</option>');
			});
		}, false);
	}
	
	var checkAllBox = function (tableName, checkboxName, event) {
		$('input#' + checkboxName).unbind('click').click(function() {
			var $this = $(this);
			var table = $('table#' + tableName);
			var $checkboxs = $('table#' + tableName).find('input[type=checkbox]');
			
			if($this.prop("checked")) {
				$checkboxs.each(function() {
					$(this).prop("checked", true);
				}); 
			} else {
				$checkboxs.each(function() {
					$(this).prop("checked", false);
				});
			};
			
			if (event) {event(this);}
		});
	}
	
	var initTable = function (tableName, jsonArray, columnDefs) {
		var table = $('table#' + tableName).DataTable({
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
			order: [[1, 'asc']],// 給定預設排序欄位, 才不會跑版
			info: true, // 頁腳信息
			autoWidth: false, // 自動寬度
			//responsive: true,
			data: jsonArray, // 注入資料
			columnDefs: columnDefs
		});
	}
	
	var datePicker = function (name, option) {
		if (!name) {
			var dialog = bootbox.dialog({
			    title: '初始化失敗!',
			    message: 'name: ' + name
			});
			
			return;
		}
		
		var $target = $('div#' + name);
		
		if (!option) {
			option = {
				format: 'yyyy-mm-dd', // 日期格式
				//startDate: formData.begin, 可選起始日期+時間
				//endDate: formData.end, 可選結束日期+時間
				//pickerPosition: "top-left", 設定視窗彈出位置
				autoclose: true // 選完日期自動關閉日曆
				//todayBtn: true   顯示[今天]按鈕
			};
		}
		
		$target.datepicker('destroy');
		$target.datepicker(option);
	}
	
	var yearSelector = function (name) {
		if (!name) {
			var dialog = bootbox.dialog({
			    title: '初始化失敗!',
			    message: 'name: ' + name
			});

			return;
		}
		
	    for (i = new Date().getFullYear(); i > 1900; i--){
	        $('select#' + name).append($('<option />').val(i).html(i));
	    }
	}
	
	var monthSelector = function (name) {
		if (!name) {
			var dialog = bootbox.dialog({
			    title: '初始化失敗!',
			    message: 'name: ' + name
			});

			return;
		}
		
	    for (i = 1; i < 13; i++){
	        $('select#' + name).append($('<option />').val(i).html(i));
	    }
	}
	
	var daySelector = function (yearName, monthName, dayName) {
		if (!yearName || !monthName || !dayName) {
			var dialog = bootbox.dialog({
			    title: '初始化失敗!',
			    message: 'yearName: ' + yearName + ', monthName: ' + monthName + ', dayName: ' + dayName
			});

			return;
		}
		
	    updateNumberOfDays(yearName, monthName, dayName); 

	    $('select#' + yearName + ', ' + 'select#' + monthName).change(function(){
	        updateNumberOfDays(yearName, monthName, dayName); 
	    });
	}

	/* Private
	 ============================================== */
	var updateNumberOfDays = function (yearName, monthName, dayName) {
	    $('select#' + dayName).html('');
	    month = $('select#' + monthName).val();
	    year = $('select#' + yearName).val();
	    days = daysInMonth(month, year);
	
	    for(i = 1; i < days+1 ; i++){
	        $('select#' + dayName).append($('<option />').val(i).html(i));
	    }
	}
	
	var daysInMonth = function (month, year) {
	    return new Date(year, month, 0).getDate();
	}
	
	return {
		datePicker: datePicker,
		yearSelector: yearSelector,
		monthSelector: monthSelector,
		daySelector: daySelector,
		initTable: initTable,
		checkAllBox: checkAllBox,
		select: select
	}
	
}();