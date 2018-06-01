//# sourceURL=header.js
$(function() {});

function resetPassword () {
	ViewResolver.refresh("/Common/resetPassword");
}

function logout () {
	JsonUtil.post('/Entries/Logout', '', function (resData) {
		window.location.reload();
	});
}