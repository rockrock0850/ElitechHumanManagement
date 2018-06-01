//# sourceURL=File_util.js
var FileUtil = function () {
	var base64Str = '';
	var fileData = {};

	/**
	 * 將檔案轉成base64字串並存起來
	 */
	var readAsBase64 = function (file) {
		if (!file) {return '';}

	    var reader = new FileReader();
	    reader.onload = function (e) {
	    	// readAsDataURL的call back function
	    	// e.target.result( 取得轉成base64之後的圖片資料 )
	    	base64Str = e.target.result;
	    }
		reader.readAsDataURL(file);
	}
	
	/**
	 * 取得Base64格式字串
	 */
	var getBase64 = function () {
		return base64Str;
	}
	
	/**
	 * 將檔案轉成File物件
	 */
	var readAsFileData = function (name, f) {
		fileData = new FormData();
		
	    if ($.isArray(f)) {
		    $.each(f, function(i, file) {
		    	fileData.append(name, file);
		    });
	    } else {
	    	fileData.append(name, f);
	    }
	}
	
	/**
	 * 取得File
	 */
	var getFileData = function () {
		return fileData;
	}
	
	var upload = function (action, fileObj, success) {
		Loader.show();
		
		$.ajax({
			type: "post",
			url: contextPath + action,
			data: fileObj,
			cache: false,
			async: false,
			contentType: false,
			processData: false,
			success: function (resData) {
				var code = resData.code;
				
				if (code == undefined) {
					bootbox.alert({
						size: 'small',
						message: '發生未知錯誤', 
					});
				} else {
					success(resData);
				}
			},
			error: function (xhr) {
				ErrorUtil.alert(xhr);
			},
			complete: function () {
				Loader.hide();
			}
		});
	}
	
	return {
		readAsBase64: readAsBase64,
		getBase64: getBase64,
		readAsFileData: readAsFileData,
		getFileData: getFileData,
		upload: upload
	}
	
}();