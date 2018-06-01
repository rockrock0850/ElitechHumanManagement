//# sourceURL=user_buttons.js
function setButton (buttonBody, functionId, pageType, buttons) {
	if (!pageType) {
		$('div#top,div#bottom').hide();
		return;
	}
	
	$.each(buttons, function () {
		setFlow(buttonBody, functionId, pageType, this);
	});
	setStatic(buttonBody, pageType);
	$('div#top,div#bottom').removeAttr('style');
}

function setStatic (buttonBody, type) {
	var funtion = $('div#container').attr('function');
	
	switch (type) {
		case 'Q':
			// Nothing to do
			break;
			
		case 'U':
			appendStatic(buttonBody, funtion, '取消');
			break;
			
		case 'I':
			appendStatic(buttonBody, funtion, '重新查詢');
			break;
			
		case 'D':
			appendStatic(buttonBody, funtion, '重新查詢');
			break;

		case 'GA_C_1':
			appendStatic(buttonBody, funtion, '取消');
			break;

		case 'UA_C_1':
			appendStatic(buttonBody, funtion, '取消');
			break;

		case 'PL_C_1':
			// Nothing to do
			break;
			
		default:
			appendStatic(buttonBody, funtion, '重新查詢');
			break;
	} 
}

function setFlow (buttonBody, id, type, obj) {
	var buttonId = obj.buttonId;
	var buttonName = obj.buttonName;
	var buttonType = obj.buttonType;
	var functionId = obj.functionId;
	var pageType = obj.pageType;
	var action = obj.action;
	
	if (id == functionId && type == pageType) {
		$('div#' + buttonBody).append(
				'<a id="' + buttonId + '" class="btn btn-primary" href="javascript: ' + buttonType + '(\'' + action + '\');">' + buttonName + '</a>');
	}
}

function appendStatic (buttonBody, funtion, name) {
	$('div#' + buttonBody).append(
			'<a class="btn btn-primary" href="javascript: lastStep(\'' + funtion + '\');">' + name + '</a>');
}