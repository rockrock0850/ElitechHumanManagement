//# sourceURL=json_util.js
var JsonUtil = function () {

	var post = function (action, data, success, async) {
		Loader.show();
		
		$.ajax({
			method: "post",
			url: contextPath + action,
			contentType: "application/json;charset=utf-8;",
			data: JSON.stringify(data),
			async: async,
			success: function (resData) {
				var code = resData.code;
				if (code == undefined) {window.location.reload();}
				success(resData);
			},
			error: function (xhr) {
				ErrorUtil.alert(xhr);
			},
			complete: function () {
				Loader.hide();
			}
		});
	}
	
	var fromJson = function (json) {
		if (!json) {return '';}
		
		var obj = JSON.parse(json);
		return obj;
	}

	var toJson = function (obj) {
		if (!obj) {return '';}
		
		var json = JSON.stringify(obj);
		return json;
	}
	
	return {
		post: post,
		fromJson: fromJson,
		toJson: toJson
	}
	
}();