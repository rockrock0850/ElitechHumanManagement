//# sourceURL=find_1.js
$(function() {
	InitUtil.select('status', 'status');
});

function queryList (action) {
	var data = form2object('findGroupAccountForm');
	ViewResolver.refresh(action, data);
}

function addFlow (action) {
	ViewResolver.refresh(action);
}

/** Button Interface
 ================================================================*/
function query (action) {
	switch (action) {
		case '/Function/GroupAccount/queryList':
			queryList(action);
			break;
	}
}

function insert (action) {
	switch (action) {
		case '/Function/GroupAccount/addFlow':
			addFlow(action);
			break;
	}
}