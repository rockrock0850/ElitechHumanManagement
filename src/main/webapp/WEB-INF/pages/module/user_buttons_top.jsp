<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>
<script type="text/javascript" src="${contextPath}/pages/js/layout/material/user_buttons.js?v=${now}"></script>

<div id='top' class="card mb-3 user_button_top" style='display: none;'>
	<div class="card-header"></div>
</div>

<script>//# sourceURL=user_buttons_top.js
$(function () {
	var buttons = JsonUtil.fromJson('${buttons}');
	var functionId = $('div#container').attr('functionId');
	var pageType = $('div#content').attr('pageType');
	setButton('top .card-header', functionId, pageType, buttons);
});
</script>