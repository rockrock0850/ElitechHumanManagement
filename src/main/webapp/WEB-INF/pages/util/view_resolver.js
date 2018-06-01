//# sourceURL=view_resolver.js
var ViewResolver = function () {
	
	var refresh = function (path, data) {
		if (!path) {return;}
		if (!data) {data = '';}

		Loader.show();
		
		$.ajax({
			method: "post",
			url: contextPath + path,
			contentType: "application/json;charset=utf-8;",
			data: JSON.stringify(data),
			async: true,
			success: function (resData) {
				$('div#container').html('').html(resData);
			},
			error: function (xhr) {
				ErrorUtil.alert(xhr);
			},
			complete: function () {
				Loader.hide();
			}
		});
	}
	
	var formSubmit = function (action, data) {
		var keys = Object.keys(data);
		var $menu = $('div#menu');
		$menu.append("<form id='postForm' class='hidden' action='' method='post'></form>");
		
		var $postForm = $('form#postForm');
		$postForm.attr('action', contextPath + action);
		$.each(keys, function () {
			$postForm.append("<input name='" + this + "' value='" + data[this] + "' />");
		});
		$postForm.submit();
	}
	
	return {
		refresh: refresh,
		formSubmit: formSubmit
	}
}();