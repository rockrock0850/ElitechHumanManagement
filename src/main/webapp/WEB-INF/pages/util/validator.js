//# sourceURL=validator.js
var Validator = function () {

	var file = function (file, msg, allowedExtensions, result) {
		if (!result) {return false;}
		
		var name = file.name.toLowerCase();
		var indexOfExtension = name.lastIndexOf('.') + 1;
        var extension = name.substring(indexOfExtension);

	    if ($.inArray(extension, allowedExtensions) == -1) {
			bootbox.alert({
				size: 'small',
				message: '請選擇' + msg + '類型的檔案'
			});
	    	
	    	return false;
    	}
	    
	    return true;
	}
	
	var length = function (form, name, colName, len, result) {
		if (!result) {return false;}
		
		var val = $('form#' + form).find('input[name=' + name + ']').val();
		if (val.length <= len) {return true;}
		
		bootbox.alert({
			size: 'small',
			message: colName + '必須少於' + len + '字元'
		});
		
		return false
	}
	
	/**
	 * 檢查欄位內為全英文數字及部分特殊符號
	 */
	var english = function (form, name, colName, result) {
		if (!result) {return false;}
		return regularMatch(form, name, colName, /^\w+([-+.]\w+)*$/);
	}
	
	/**
	 * 家用電話( 包含區碼: 前2~4位, 必須用'-'號隔開 )
	 */
	var telephone = function (form, name, colName, result) {
		if (!result) {return false;}
		return regularMatch(form, name, colName, /(^0[2-8]|^037|^049|^082|^089|^0826|^0836)-[0-9]{7,8}$/);
	}
	
	/**
	 * 行動電話( 前四碼必須用'-'號隔開 )
	 */
	var phone = function (form, name, colName, result, rule) {
		if (!result) {return false;}
		return regularMatch(form, name, colName, /^09[0-9]{2}-[0-9]{6}$/);
	}

	var email = function (form, name, colName, result) {
		if (!result) {return false;}
		return regularMatch(form, name, colName, /^\w+([-+.´]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/);
	}
	
	/**
	 * 檢核郵遞區號格式( 5碼數字 )
	 */
	var zipcode = function (form, name, colName, result) {
		if (!result) {return false;}
		return regularMatch(form, name, colName, /^[0-9]{5}$/);
	}
	
	/**
	 * 檢查表單內所有的輸入欄位是否為空值或空字串
	 */
	var input = function (formName, ignore, msgMap, result) {
		if (!result) {return false;}
		
		var $form = $('form#' + formName);
		var inputs = $form.find('input,select');
		var keys = Object.keys(msgMap);
		var colName = '';

		// Inputs
		$.each(inputs, function () {
			var $this = $(this);
			var name = $this.attr('name');
			
			if (!name) {return;}
			if ($.inArray(name, ignore) >= 0) {return;}
			
			var val = $this.val();
			if (!val) {
				colName = name;
				return false;
			}
		});
		
		if (colName) {
			$.each(keys, function (i) {
				if (colName == this) {
					bootbox.alert({
						size: 'small',
						message: '請輸入' + msgMap[this]
					});
					
					return false;
				}
				
				if (i == keys.length-1) {
					bootbox.alert({
						size: 'small',
						message: '未知欄位名稱 [' + colName + '], 請設定ignore參數'
					});
				}
			});
		}
		
		return colName == '' ? true : false;
	}
	
	/**
	 * 檢查表單內的輸入欄位是否至少輸入一個項目
	 */
	var atLeastOne = function (formName, result) {
		if (!result) {return false;}
		
		var inputs = $('form#' + formName).find('input,select');

		// Inputs
		$.each(inputs, function () {
			var val = $(this).val();
			result = val ? true : false;
			if (result) {return false;}
		});

		if (!result) {
			bootbox.alert({
				size: 'small',
				message: '請至少輸入一個查詢條件!'
			});
		}
		
		return result;
	}
	
	var equals = function (t1, t2, msgMap, result) {
		if (!result) {return false;}
		
		if (!t1 || !t2) {return false;}
		
		if (t1 == t2) {return true;}

		var keys = Object.keys(msgMap);
		bootbox.alert({
			size: 'small',
			message: msgMap[keys[0]] + '欄位與' + msgMap[keys[1]] + '欄位不符'
		});
		
		return false;
	}
	
	var regularMatch = function (form, name, colName, rule) {
		var val = $('form#' + form).find('input[name=' + name + ']').val();
		
		if (rule.test(val)) {return true;}
		
		bootbox.alert({
			size: 'small',
			message: colName + '格式錯誤'
		});
		
		return false;
	}
	
	return {
		input: input,
		equals: equals,
		atLeastOne: atLeastOne,
		telephone: telephone,
		phone: phone,
		email: email,
		zipcode: zipcode,
		regularMatch: regularMatch,
		english: english,
		length: length,
		file: file
	}
	
}();