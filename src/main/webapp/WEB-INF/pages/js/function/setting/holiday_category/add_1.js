//# sourceURL=add_1.js
$(function() {
	InitUtil.select('unit', 'unit');
	InitUtil.select('gender', 'gender');
	InitUtil.select('emptype', 'emptype');
	InitUtil.select('isYears', 'is_years');
	InitUtil.select('isDocument', 'is_document');
	InitUtil.select('isSubstitute', 'is_substitute');
});

function add (action) {
	var result = Validator.input('addHolidayCategoryForm', ['documentMsg','status','gender'], {
		leavetypeId: '假別代號',
		unit: '計假單位',
		expire: '申請限制',
		isDocument: '附件需求',
		leaveName: '假別名稱',
		emptype: '適用員工別',
		isYears: '參考年資',
		isSubstitute: '代理限制'
	}, true);
	
	if (!result) {return;}
	
	var data = form2object('addHolidayCategoryForm');
	JsonUtil.post(action, data, function (resData) {
		bootbox.alert({
			size: 'small',
			message: resData.msg, 
			callback: function () {
				var $form = $('form#addHolidayCategoryForm');
				var inputs = $form.find('input,select');
				
				$.each(inputs, function () {$(this).val('');});
				$('input[name=status]').val('1');
			}
		});
	});
}

/** Button Interface
 ================================================================*/
function insert (action) {
	switch (action) {
		case '/Function/HolidayCategory/addFlow/add':
			add(action);
			break;
	}
}

function lastStep (action) {
	ViewResolver.refresh(action);
}