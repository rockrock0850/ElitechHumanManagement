//# sourceURL=edit_1.js
var doc = '';
	
$(function() {
	updateColumns();
});

function updateColumns () {
	var signProcess = JsonUtil.fromJson(signProcessJson);
	var leaveNo = signProcess.leaveNo;
	var processDisplay = signProcess.processDisplay;
	var process = signProcess.process;
	var approveStatusDisplay = signProcess.approveStatusDisplay
	var approveStatus = signProcess.approveStatus;
	var empId = signProcess.empId;
	var departmentName = signProcess.departmentName;
	var leavetypeId = signProcess.leavetypeId;
	var leaveName = signProcess.leaveName;
	var empCname = signProcess.empCname;
	var jobtitleName = signProcess.jobtitleName;
	var begindate = signProcess.begindate;
	var beginhour = signProcess.beginhour;
	var beginmin = signProcess.beginmin;
	var enddate = signProcess.enddate;
	var endhour = signProcess.endhour;
	var endmin = signProcess.endmin;
	var leavedays = signProcess.leavedays;
	var leavehours = signProcess.leavehours;
	var reason = signProcess.reason;
	var substitute = signProcess.substitute;
	var emptype = signProcess.emptype;
	var departmentId = signProcess.departmentId;
	var isDocument = signProcess.isDocument;
	var isSubstitute = signProcess.isSubstitute;
	doc = signProcess.document;

	if (!doc) {$('div#document').hide();}
	$('input[name=leaveNo]').val(leaveNo);
	$('label#processDisplay').html(processDisplay);
	$('input[name=process]').val(process);
	$('label#approveStatusDisplay').html(approveStatusDisplay);
	$('input[name=approveStatus]').val(approveStatus);
	$('label#empId').html(empId);
	$('input[name=empId]').val(empId);
	$('label#departmentName').html(departmentName);
	$('input[name=departmentName]').val(departmentName);
	$('label#leaveName').html(leaveName);
	$('input[name=leavetypeId]').val(leavetypeId);
	$('label#empCname').html(empCname);
	$('input[name=empCname]').val(empCname);
	$('label#jobtitleName').html(jobtitleName);
	$('input[name=jobtitleName]').val(jobtitleName);
	$('label#substitute').html(substitute);
	$('input[name=substitute]').val(substitute);
	$('input[name=begindate]').val(begindate.split(' ')[0]);
	$('input[name=beginhour]').val(beginhour);
	$('input[name=beginmin]').val(beginmin);
	$('input[name=enddate]').val(enddate.split(' ')[0]);
	$('input[name=endhour]').val(endhour);
	$('input[name=endmin]').val(endmin);
	$('a#leavedays').html(leavedays);
	$('input[name=leavedays]').val(leavedays);
	$('a#leavehours').html(leavehours);
	$('input[name=leavehours]').val(leavehours);
	$('textarea[name=reason]').val(reason);
	$('input[name=emptype]').val(emptype);
	$('input[name=departmentId]').val(departmentId);
	
	initAttaches(isSubstitute, isDocument);
}

function initAttaches (isSubstitute, isDocument) {
	isSubstitute == 1 ? $('div#isSubstitute').show() : $('div#isSubstitute').hide();
	isDocument == 1 ? $('div#isDocument').show() : $('div#isDocument').hide();
}

function pass (action) {
	var data = form2object('editSignProcess');
	
	JsonUtil.post(action, data, function (resData) {
		bootbox.alert({
			size: 'small',
			message: resData.msg, 
			callback: function () {
				ViewResolver.refresh('/Function/SignProcess');
			}
		});
	});
}

function reject (action) {
	var data = form2object('editSignProcess');
	
	JsonUtil.post(action, data, function (resData) {
		bootbox.alert({
			size: 'small',
			message: resData.msg, 
			callback: function () {
				ViewResolver.refresh('/Function/SignProcess');
			}
		});
	});
	
}

function showDocument () {
	ImageModal.init('附件', doc);
    ImageModal.show();
}

/** Button Interface
 ================================================================*/
function update (action) {
	switch (action) {
		case '/Function/SignProcess/pass':
			pass(action);
			break;
			
		case '/Function/SignProcess/reject':
			reject(action);
			break;
	}
}

function lastStep (action) {
	ViewResolver.refresh(action);
}