//# sourceURL=error_util.js
var ErrorUtil = function () {

	var alert = function (xhr) {
		var msg;
		try {
			var exception = JSON.parse(xhr.responseText);
			bootbox.alert({
				size: 'large',
				message: exception.msg
			});
		} catch (e) {
			bootbox.alert({
				size: 'large',
				message: xhr.responseText
			});
		}
	}
	
	return {
		alert: alert
	}
}();