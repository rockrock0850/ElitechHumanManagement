//# sourceURL=container.js
$(function() {
	ViewResolver.refresh(func);
});

function index () {
	var path = '/Entries/DashBoard';
	var data = {
		breadcrumbs : ''
	};
	
	ViewResolver.formSubmit(path, data);
}