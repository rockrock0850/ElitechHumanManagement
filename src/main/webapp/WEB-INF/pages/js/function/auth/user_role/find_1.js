//# sourceURL=find_1.js
$(function() {
});

function queryList (action) {
	var data = form2object('findGroupAuthorityForm');
	ViewResolver.refresh(action, data);
}

/** Button Interface
 ================================================================*/
function query (action) {
	switch (action) {
		case '/Function/UserRoleMap/queryList':
			queryList(action);
			break;
	}
}