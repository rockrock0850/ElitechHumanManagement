//# sourceURL=find_1.js
$(function() {
});

function queryList (action) {
	var data = form2object('findUserAuthorityForm');
	ViewResolver.refresh(action, data);
}

/** Button Interface
 ================================================================*/
function query (action) {
	switch (action) {
		case '/Function/UserAuthority/queryList':
			queryList(action);
			break;
	}
}