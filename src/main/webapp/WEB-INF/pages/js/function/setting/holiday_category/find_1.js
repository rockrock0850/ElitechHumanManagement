//# sourceURL=find_1.js
$(function() {
	InitUtil.select('unit', 'unit');
	InitUtil.select('gender', 'gender');
	InitUtil.select('emptype', 'emptype');
	InitUtil.select('isYears', 'is_years');
});

function queryList (action) {
	var data = form2object('findHolidayCategoryForm');
	ViewResolver.refresh(action, data);
}

function addFlow (action) {
	ViewResolver.refresh(action);
}

/** Button Interface
 ================================================================*/
function query (action) {
	switch (action) {
		case '/Function/HolidayCategory/queryList':
			queryList(action);
			break;
	}
}

function insert (action) {
	switch (action) {
		case '/Function/HolidayCategory/addFlow':
			addFlow(action);
			break;
	}
}