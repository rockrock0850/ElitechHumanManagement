//# sourceURL=menu.js
var breadcrumbs = [];

$(function() {
	$('div#menu li a').unbind('click').click(function () {
		var $this = $(this);
		var spans = $this.find('span');
		
		if (spans.length != 0) {
			var $span = $(spans[0]);
			breadcrumbs = [];
			
			var breadcrumb = {};
			breadcrumb.name = $.trim($span.html());
			breadcrumbs.push(breadcrumb);
			
			return;
		}
		
		var breadcrumb = {};
		breadcrumb.name = $.trim($this.html());
		breadcrumbs.push(breadcrumb);
		
		var path = $this.attr('path');
		if (path && path.startsWith('/')) {
			var functionId = $this.attr('functionId');
			
			var data = {};
			data.breadcrumbs = JsonUtil.toJson(breadcrumbs);
			data.functionId = functionId;
			data.path = path;
			ViewResolver.formSubmit(path, data);
		}
	});
});