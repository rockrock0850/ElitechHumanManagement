//# sourceURL=loader.js
var Loader = function () {
	
	var show = function () {
		$('div#loader').show();
	}
	
	var hide = function () {
		$('div#loader').hide();
	}
	
	return {
		show: show,
		hide: hide
	}
}();