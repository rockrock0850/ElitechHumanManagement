//# sourceURL=edit_1.js
var doc = '';
var workingTime = '';
var offTime = '';
var lunchTime = '';
var editable = true;
var editableMsg = '';
	
$(function() {
	var leaveRecord = JsonUtil.fromJson(editVO);
	workingTime = !leaveRecord.workingTime ? '09' : leaveRecord.workingTime;
	offTime = !leaveRecord.offTime ? '18' : leaveRecord.offTime;
	lunchTime = !leaveRecord.lunchTime ? 1 : leaveRecord.lunchTime;
	doc = leaveRecord.document;
	
	var hours = getHourOptions();
	InitUtil.select('beginhour', '', hours);
	InitUtil.select('endhour', '', hours);
	InitUtil.select('beginmin', 'minus');
	InitUtil.select('endmin', 'minus');
	
	updateColumns(leaveRecord);
	setApplyLeaveTimes();
});

function upload () {
	$('input[name=document]').trigger('click');
}

function getHourOptions () {
	var hours = [];
	for (var i = parseInt(workingTime); i < parseInt(offTime) + 1; i++) {
		var hour = {};
		var size = i.toString().length;
		
		size < 2 ? hour['0' + i] = '0' + i : hour[i] = '' + i
		hours.push(hour);
	}
	
	return hours;
}

function setApplyLeaveTimes () {
	var targets = 
		'input[name=begindate],select[name=beginhour],select[name=beginmin],' +
		'input[name=enddate],select[name=endhour],select[name=endmin]';
	
	$(targets).unbind('change').change(function () {
		var begindate = $('input[name=begindate]').val();
		var enddate = $('input[name=enddate]').val();
		var beginhour = parseInt($('select[name=beginhour]').val());
		var endhour = parseInt($('select[name=endhour]').val());
		var beginmin = parseInt($('select[name=beginmin]').val());
		var endmin = parseInt($('select[name=endmin]').val());
		
		if (!begindate || !beginhour || !enddate || !endhour) {return;}
		
		var minutes = $('select[name=beginmin],select[name=endmin]');
		$.each(minutes, function () {
			var $this = $(this);
			var val = $this.val();
			!val ? $this.val('00') : $this.val(val);
		});
		setSummaryTimes(begindate, enddate, beginhour, endhour, beginmin, endmin);
	});
}

//計算共幾日幾時欄位值
function setSummaryTimes (begindate, enddate, beginhour, endhour, beginmin, endmin) {
	/*
	 *   - beginLeaveHours( 起始日放假時間 ) = 絕對值|下班時間 - 請假起始時間|
	 *   	P.S.若起始時間小於等於 "11" 要減掉 "午餐時間"
	 *   
	 *   - endLeaveHours( 結束日放假時間 ) = 絕對值|請假結束時間 - 上班時間|
	 *   	P.S.若結束時間大於等於 "13" 要減掉 "午餐時間"
	 *   
	 *   - minusHours( 去掉起始與結束日的放假時數 )                                              
	 */
	
	if (beginhour == endhour) {
		bootbox.alert({
			size: 'small',
			message: '結束時間設定錯誤',
			callback: function () {
				$('a#leavedays,a#leavehours').html('0');
				$('input#leavedays,input#leavehours').val('');
			}
		});
		
		return;
	}
	
	var beginLeaveHours = 0;
	var endLeaveHours = 0;
	var days =  0;
	var hours = 0;
	var minusHours = getMinusHours(begindate, enddate);
	
	if (minusHours < 0) {// 如果只有1天, 則直接計算今日請假總時數
		hours = (endhour + getHours(endmin)) - (beginhour + getHours(beginmin))
		if (endhour >= 13) {hours -= lunchTime;}// 結束時間大於等於13要減掉午餐時間
	} else {
		beginLeaveHours = getBeginLeaveHours(beginhour, beginmin, minusHours); 
		endLeaveHours = getEndLeaveHours(endhour, endmin, minusHours);         
		days =  Math.floor((beginLeaveHours + minusHours + endLeaveHours) / 8);
		hours = beginLeaveHours + minusHours + endLeaveHours - (days * 8);     
	}

	$('a#leavedays').html(days);
	$('a#leavehours').html(hours);
	$('input[name=leavedays]').val(days);
	$('input[name=leavehours]').val(hours);
}

function getBeginLeaveHours (beginhour, beginmin) {
	var beginLeaveHours = Math.abs(offTime - (beginhour + getHours(beginmin)));
	if (beginhour <= 11) {beginLeaveHours -= lunchTime;}// 起始時間小於等於11要減掉午餐時間
	
	return beginLeaveHours;
}

function getEndLeaveHours (endhour, endmin) {
	var endLeaveHours = Math.abs(workingTime - (endhour + getHours(endmin)));
	if (endhour >= 13) {endLeaveHours -= lunchTime;}// 結束時間大於等於13要減掉午餐時間
	
	return endLeaveHours;
}

function minusWeekend (minusDays, begindate, enddate) {
	while (begindate <= enddate) {
	    var weekend = begindate.getDay();// day of the week 0-6
	     
	    if (weekend == 0 || weekend == 6) {// 週六週日
	    	minusDays -= 1;
    	}
		
		begindate.setDate(begindate.getDate() + 1);
	}
     
    return minusDays;
}

//分鐘轉小時
function getHours (minutes) {
	if (!minutes) {minutes = 0;}
	return minutes / 60;
}

//取得相差日之放假時數
function getMinusHours (begindate, enddate) {
	begindate = new Date(begindate);
	enddate = new Date(enddate);
	
	var minusDays = (enddate - begindate) / 86400000;// 換算兩日期之間共有多少天
	minusDays = minusWeekend(minusDays, begindate, enddate);// 減去假日
	
	var minusHours = (minusDays - 1) * 8;
	
	return minusHours;
}

function updateColumns (leaveRecord) {
	var leaveNo = leaveRecord.leaveNo;
	var process = leaveRecord.process;
	var leaveName = leaveRecord.leaveName;
	var leavetypeId = leaveRecord.leavetypeId;
	var empId = leaveRecord.empId;
	var empCname = leaveRecord.empCname;
	var departmentName = leaveRecord.departmentName;
	var departmentId = leaveRecord.departmentId;
	var jobtitleName = leaveRecord.jobtitleName;
	var reason = leaveRecord.reason;
	var substitute = leaveRecord.substitute;
	var processDisplay = leaveRecord.processDisplay;
	var approveStatusDisplay = leaveRecord.approveStatusDisplay;
	var begindate = leaveRecord.begindate;
	var enddate = leaveRecord.enddate;
	var beginhour = leaveRecord.beginhour;
	var beginmin = leaveRecord.beginmin;
	var endhour = parseInt(leaveRecord.endhour);
	var endmin = parseInt(leaveRecord.endmin);
	var expire = leaveRecord.expire;
	var emptype = leaveRecord.emptype;
	var isDocument = leaveRecord.isDocument;
	var isSubstitute = leaveRecord.isSubstitute;
	editable = leaveRecord.editable;
	editableMsg = leaveRecord.editableMsg;

	if (!doc) {$('div#document').hide();}
	$('input[name=leaveNo]').val(leaveNo);
	$('label#processDisplay').html(processDisplay);
	$('input[name=process]').val(process);
	$('label#approveStatusDisplay').html(approveStatusDisplay);
	$('label#empId').html(empId);
	$('input[name=empId]').val(empId);
	$('label#departmentName').html(departmentName);
	$('input[name=departmentName]').val(departmentName);
	$('label#empCname').html(empCname);
	$('input[name=empCname]').val(empCname);
	$('label#jobtitleName').html(jobtitleName);
	$('input[name=jobtitleName]').val(jobtitleName);
	$('input[name=substitute]').val(substitute);
	$('textarea[name=reason]').val(reason);
	$('input[name=begindate]').val(begindate.split(' ')[0]);
	$('input[name=enddate]').val(enddate.split(' ')[0]);
	$('select[name=beginhour]').val(beginhour);
	$('select[name=endhour]').val(endhour);
	$('select[name=beginmin]').val(beginmin == 0 ? '00' : beginmin);
	$('select[name=endmin]').val(endmin == 0 ? '00' : endmin);
	$('input[name=leavetypeId]').val(leavetypeId);
	$('label#leaveName').html(leaveName);
	$('input[name=departmentId]').val(departmentId);
	$('input[name=emptype]').val(emptype);
	
	initAttaches(isSubstitute, isDocument);
	setDatePicker(expire);
	setSummaryTimes(
			begindate, enddate, parseInt(beginhour), parseInt(endhour), parseInt(beginmin), parseInt(endmin));
	isEditable();
}

function initAttaches (isSubstitute, isDocument) {
	isSubstitute == 1 ? $('div#isSubstitute').show() : $('div#isSubstitute').hide();
	isDocument == 1 ? $('div#isDocument').show() : $('div#isDocument').hide();
}

function setDatePicker (expire) {
	var d = new Date();
	var absExpire = Math.abs(expire);
	
	d.setDate(d.getDate() + absExpire);
	InitUtil.datePicker('begindate', {
		format: 'yyyy-mm-dd', // 日期格式
		startDate: d,// 可選起始日期+時間
		daysOfWeekDisabled: '0,6',
		autoclose: true // 選完日期自動關閉日曆
	});
	InitUtil.datePicker('endDate', {
		format: 'yyyy-mm-dd', // 日期格式
		startDate: d,// 可選起始日期+時間
		daysOfWeekDisabled: '0,6',
		autoclose: true // 選完日期自動關閉日曆
	});
}

function isEditable () {
	if (!editable) {
		$('input[name=begindate]').prop('disabled', true);
		$('select[name=beginhour]').prop('disabled', true);
		$('select[name=beginmin]').prop('disabled', true);
		$('input[name=enddate]').prop('disabled', true);
		$('select[name=endhour]').prop('disabled', true);
		$('select[name=endmin]').prop('disabled', true);
		$('textarea[name=reason]').prop('disabled', true);
		$('input[name=substitute]').prop('disabled', true);
		$('div#isSubstitute').find('a').hide();
		$('div#isDocument').hide();
	}
}

function edit (action) {
	if (!editable) {
		bootbox.alert({
			size: 'small',
			message: editableMsg
		});
		
		return;
	}
	
	var result = Validator.input('editGroupAccountForm', 
			['process','approveStatus','empId','departmentName','empCname','jobtitleName','leavedays','leavehours','reason','substitute','document'], 
			{
				begindate: '起始日期',
				beginhour: '起始時間',
				enddate: '結束日期',
				endhour: '結束時間',
				leavetypeId: '假別'
			}, true);
	
	if (!result) {return;}
	
	var data = form2object('editFormSearch');
	var attachement = FileUtil.getBase64();
	
	data.document = data.document ? attachement.split(',')[1] : doc;
	
	JsonUtil.post(action, data, function (resData) {
		bootbox.alert({
			size: 'small',
			message: resData.msg, 
			callback: function () {
				ViewResolver.refresh('/Function/FormSearch');
			}
		});
	});
}

function delet (action) {
	if (!editable) {
		bootbox.alert({
			size: 'small',
			message: editableMsg
		});
		
		return;
	}
	
	var data = form2object('editFormSearch');
	JsonUtil.post(action, data, function (resData) {
		bootbox.alert({
			size: 'small',
			message: resData.msg, 
			callback: function () {
				ViewResolver.refresh('/Function/FormSearch');
			}
		});
	});
}

// 讀取選擇的檔案並儲存起來
function read (input) {
	if (!input.files && !input.files[0]) {return;}

	var $fileName = $('input#fileName');
	try {
		var file = input.files[0];
		var name = file.name;
		
		FileUtil.readAsBase64(file);
		$fileName.val(name);
	} catch (e) {
		$fileName.val('');
	}
}

function showDocument () {
	ImageModal.init('附件', doc);
    ImageModal.show();
}

function showAllAgencys () {
	JsonUtil.post('/Common/findOtherEmployees', '', function (resData) {
		SearchModal.init('代理人查詢', resData.data, 
				[// 欄位設定
		         	{orderable: false, targets: [0]}, // 選擇關閉哪個欄位排序功能(  )
					{targets: '_all', render: function (data, type, row, mate) {
						return data;
					}},
					{targets: [0], title: '員工編號', data: 'empId', className: 'dt-nowrap'}, 
					{targets: [1], title: '員工姓名', data: 'empCname', className: 'dt-nowrap'}, 
					{targets: [2], title: '英文姓名 ', data: 'empEname', className: 'dt-nowrap'}, 
					{targets: [3], title: '電子郵件 ', data: 'email', className: 'dt-nowrap'}, 
					{targets: [4], title: '手機號碼 ', data: 'phone', className: 'dt-nowrap'}
				]
		);
		
		SearchModal.setTableClickEvent(function (row) {
			var empId = row.empId;
			$('input[name=substitute]').val(empId);
			SearchModal.hide();
		});
		
		SearchModal.show();
	});
}

/** Button Interface
 ================================================================*/
function update (action) {
	switch (action) {
		case '/Function/FormSearch/editFlow/edit':
			edit(action);
			break;
	}
}

function del (action) {
	switch (action) {
		case '/Function/FormSearch/delete':
			delet(action);
			break;
	}
}

function lastStep (action) {
	ViewResolver.refresh(action);
}