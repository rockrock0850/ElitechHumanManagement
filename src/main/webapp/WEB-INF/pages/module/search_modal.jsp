<%@ page pageEncoding="UTF-8" %>

<!-- Search Modal -->
<div id="searchModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="searchModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 id='title' class="modal-title"></h5>
				<button class="close" type="button" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
			</div>
			<div class="modal-body">
				<table id="searchModalTable" class="table table-striped table-bordered table-hover"></table>
			</div>
			<div class="modal-footer">
				<a class="btn btn-primary" href="#" data-dismiss="modal">確定</a>
			</div>
		</div>
	</div>
</div>

<script>//# sourceURL=search_modal.js
$(function () {
	SearchModal.setOnCloseEvent();
});

var SearchModal = function () {
	var $modal = $('div#searchModal');
	
	var init = function (title, dataList, setting) {
		$modal.find('h5#title').html(title);
		InitUtil.initTable('searchModalTable', dataList, setting);	
	}
	
	var show = function () {
		$modal.find('table[role=grid]').removeAttr('style');
		$modal.find('.dataTables_scrollHeadInner').removeAttr('style');
		$modal.modal('show');
	}
	
	var hide = function () {
		$modal.modal('hide');
	}
	
	var setOnCloseEvent = function (event) {
		$modal.unbind('hidden.bs.modal').on('hidden.bs.modal', function () {
			var $table = $('table#searchModalTable');
			$table.DataTable().destroy();
			$table.html('');
			if (event) {event();}
		})
	}
	
	var setTableClickEvent = function (event) {
		var table = $modal.find('table#searchModalTable').DataTable();
		$modal.find('table#searchModalTable tbody').unbind('click').on('click', 'tr', function () {
			var row = table.row($(this)).data();
			event(row);
		});
	}
	
	return {
		init: init,
		show: show,
		hide: hide,
		setOnCloseEvent: setOnCloseEvent,
		setTableClickEvent: setTableClickEvent
	}
}();
</script>