//# sourceURL=add_1.js
var leavetypeIds = [];
var settingList = [];

$(function() {
	InitUtil.select('emptype', 'emptype');
	setSelectChangeEvent();
});

function setSelectChangeEvent () {
	$('select#emptype').change(function () {
		var val = $(this).val();
		var $content = $('form#addHolidayEmpContentForm');
		
		if (!val) {
			$content.html('');
			return;
		}
		
		var data = {
			emptype: val
		}
		
		JsonUtil.post('/Function/HolidayEmp/getCategorys', data, function (resData) {
			settingList = resData.data;
			genSettingTable('addHolidayEmpContentForm');
		});
	});
}

function genSettingTable (contentId) {
	var $content = $('form#' + contentId);
	
	$content.html('');
	leavetypeIds = [];
	$.each(settingList, function () {
		var leavetypeId = this.leavetypeId;
		var leaveName = this.leaveName;
		var isYears = this.isYears;
		var table = '';
		
		if (isYears > 0) {
			table = addDynamicTable(leavetypeId, leaveName);
		} else {
			table = addStaticTable(leavetypeId, leaveName);
		}
		
		leavetypeIds.push(leavetypeId);
		$content.append(table);
		$content.append('<hr>');
		
		InitUtil.select('isProportional' + leavetypeId + '0', 'is_proportional');
	});
}

function addDynamicTable (leavetypeId, leaveName) {
	var table = '<table id="' + leavetypeId + '" class="holiday_emp_table">';
	table += '<tbody>';
	table += '<tr><td id="head' + leavetypeId + '" class="verticalLine" rowspan="2">' + leaveName + '</td></tr>';
	table += '<tr style="border-top: ridge;">';
	table += '<td>年資</td>';
	table += '<td width="80">';
	table += '<input class="input" type="text" name="years' + leavetypeId + '0" />';
	table += '</td>';
	table += '<td>年,可休</td>';
	table += '<td width="100">';
	table += '<input class="input" type="text" name="leaveDays' + leavetypeId + '0" />';
	table += '</td>';
	table += '<td>天,年資未滿給假方式：</td>';
	table += '<td>';
	table += '<select class="form-control" name="isProportional' + leavetypeId + '0" width="300"></select>';
	table += '</td>';
	table += '</tr>';
	table += '</tbody>';
	table += '<tfoot>';
	table += '<tr>';
	table += '<td style="text-align: right; border-top: ridge;" colspan="7">';
	table += '<a class="btn btn-danger" href="javascript: delHolidayRow(\'' + leavetypeId + '\');" style="margin-right: 5px;"><i class="fa fa-minus" aria-hidden="true"></i></a>';
	table += '<a class="btn btn-primary" href="javascript: addHolidayRow(\'' + leavetypeId + '\');"><i class="fa fa-plus" aria-hidden="true"></i></a>';
	table += '</td>';
	table += '</tr>';
	table += '</tfoot>';
	table += '</table>';
	
	return table;
}

function addStaticTable (leavetypeId, leaveName) {
	var table = '<table id="' + leavetypeId + '" class="holiday_emp_table">';
	table += '<tbody>';
	table += '<tr style="border-top: ridge;">';
	table += '<td id="head' + leavetypeId + '" class="verticalLine" width="50">' + leaveName + '</td>';
	table += '<td width="100">';
	table += '<input class="input" type="text" name="leaveDays' + leavetypeId + '0" />';
	table += '</td>';
	table += '<td>天</td>';
	table += '</tr>';
	table += '</tbody>';
	table += '</table>';
	
	return table;
}

function addHolidayRow (leavetypeId) {
	var $table = $('table#' + leavetypeId);
	var count = $table.find('tr').length-2;
	
	var tr = '<tr>';
	tr += '<td>年資</td>';
	tr += '<td width="80"><input class="input" type="text" name="years' + leavetypeId + count + '" /></td>';
	tr += '<td>年,可休</td>';
	tr += '<td width="100"><input class="input" type="text" name="leaveDays' + leavetypeId + count + '" /></td>';
	tr += '<td>天,年資未滿給假方式：</td>';
	tr += '<td><select class="form-control" name="isProportional' + leavetypeId + count + '" width="300"></select></td>';
	tr += '</tr>';
	$table.find('tbody').append(tr);
	$table.find('td#head' + leavetypeId).attr('rowspan', count+3);
	InitUtil.select('isProportional' + leavetypeId + count, 'is_proportional');
}

function delHolidayRow (leavetypeId) {
	var $table = $('table#' + leavetypeId);
	var trs = $table.find('tr');
	var size = trs.length-2;
	var target = trs[size];
	
	if (size == 1) {return;}
	
	$(target).attr('del', true);
	$table.find('tr[del=true]').remove();
}

function add (action) {
	var result = Validator.input('addHolidayEmpHeaderForm', [], {
		emptype: '適用員工別',
		leaveYear: '年度資料'
	}, true);
	
	result = validatHolidayContentForm(result);
	
	if (!result) {return;}
	
	var data = form2object('addHolidayEmpHeaderForm');
	data = wrapperHolidayContents(data);
	
	JsonUtil.post(action, data, function (resData) {
		bootbox.alert({
			size: 'small',
			message: resData.msg, 
			callback: function () {
				genSettingTable('addHolidayEmpContentForm');
			}
		});
	});
}

function wrapperHolidayContents (data) {
	var contents = form2object('addHolidayEmpContentForm');
	var content = {};
	
	$.each(leavetypeIds, function (i, leavetypeId) {
		var obj = {};
		
		$.each(Object.keys(contents), function (i, key) {
			if (key.indexOf(leavetypeId) < 0) {return;}
			
			var val = contents[key];
			var replacement = key.replace(leavetypeId, "");
			
			obj[replacement] = val;
		});
		
		content[leavetypeId] = obj;
	});
	data.data = content;
	
	return data;
}

function validatHolidayContentForm (result) {
	if (!result) {return false;}
	
	var inputs = $('form#addHolidayEmpContentForm').find('input,select');
	var msg = '';

	$.each(inputs, function () {
		var val = $(this).val();
		if (!val) {
			msg = '請檢查是否有遺漏的假別設定欄位!';
			result = false
		}
		
		if (isNaN(val)) {
			msg = '假別設定欄位不可輸入中文!';
			result = false
		}

		if (!result) {
			bootbox.alert({
				size: 'small',
				message: msg
			});
			
			return false;
		}
	});
	
	return result;
}

/** Button Interface
 ================================================================*/
function insert (action) {
	switch (action) {
		case '/Function/HolidayEmp/addFlow/add':
			add(action);
			break;
	}
}

function lastStep (action) {
	ViewResolver.refresh(action);
}