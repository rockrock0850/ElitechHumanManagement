//# sourceURL=find_1.js
$(function() {
	InitUtil.select('emptype', 'emptype');
});

function queryList (action) {
	var data = form2object('findHolidayEmpForm');
	ViewResolver.refresh(action, data);
}

function addFlow (action) {
	ViewResolver.refresh(action);
}

/** Button Interface
 ================================================================*/
function query (action) {
	switch (action) {
		case '/Function/HolidayEmp/queryList':
			queryList(action);
			break;
	}
}

function insert (action) {
	switch (action) {
		case '/Function/HolidayEmp/addFlow':
			addFlow(action);
			break;
	}
}