//# sourceURL=edit_1.js
$(function() {
	InitUtil.select('unit', 'unit');
	InitUtil.select('gender', 'gender');
	InitUtil.select('emptype', 'emptype');
	InitUtil.select('isYears', 'is_years');
	InitUtil.select('isDocument', 'is_document');
	InitUtil.select('isSubstitute', 'is_substitute');
	updateColumns('editHolidayCategoryForm');
});

function updateColumns (name) {
	var category = JsonUtil.fromJson(categoryJson);
	var $form = $('form#' + name);
	var $label = $form.find('label#leavetypeId');
	var $leavetypeId = $form.find('input[name=leavetypeId]');
	var $expire = $form.find('input[name=expire]');
	var $leaveName = $form.find('input[name=leaveName]');
	var $documentMsg = $form.find('input[name=documentMsg]');
	var $emptype = $form.find('select[name=emptype]');
	var $oldEmptype = $form.find('input[name=oldEmptype]');
	var $isYears = $form.find('select[name=isYears]');
	var $isSubstitute = $form.find('select[name=isSubstitute]');
	var $unit = $form.find('select[name=unit]');
	var $gender = $form.find('select[name=gender]');
	var $isDocument = $form.find('select[name=isDocument]');
	
	$label.html(category.leavetypeId);
	$leavetypeId.val(category.leavetypeId);
	$leaveName.val(category.leaveName);
	$documentMsg.val(category.documentMsg);
	$expire.val(category.expire);
	$unit.val(category.unit);
	$gender.val(category.gender);
	$emptype.val(category.emptype);
	$oldEmptype.val(category.emptype);
	$isYears.val(category.isYears);
	$isDocument.val(category.isDocument);
	$isSubstitute.val(category.isSubstitute);
}

function edit (action) {
	var result = Validator.input('editHolidayCategoryForm', ['documentMsg','gender','oldEmptype'], {
		unit: '計假單位',
		expire: '申請限制',
		isDocument: '附件需求',
		leaveName: '假別名稱',
		emptype: '適用員工別',
		isYears: '參考年資',
		isSubstitute: '代理限制'
	}, true);
	
	if (!result) {return;}
	
	var data = form2object('editHolidayCategoryForm');
	JsonUtil.post(action, data, function (resData) {
		bootbox.alert({
			size: 'small',
			message: resData.msg, 
			callback: function () {
				ViewResolver.refresh('/Function/HolidayCategory/queryList', resData.data);
			}
		});
	});
}

/** Button Interface
 ================================================================*/
function update (action) {
	switch (action) {
		case '/Function/HolidayCategory/editFlow/edit':
			edit(action);
			break;
	}
}

function lastStep (action) {
	var data = JsonUtil.fromJson(queryCondition);
	ViewResolver.refresh(action + '/queryList', data);
}