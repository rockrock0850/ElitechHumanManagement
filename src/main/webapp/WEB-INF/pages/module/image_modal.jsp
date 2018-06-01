<%@ page pageEncoding="UTF-8" %>

<!-- Image Modal -->
<div id="imageModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="searchModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 id='title' class="modal-title"></h5>
				<button class="close" type="button" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
			</div>
			<div class="modal-body">
				<img id="img" alt="附件" />
			</div>
			<div class="modal-footer">
				<a class="btn btn-primary" href="#" data-dismiss="modal">確定</a>
			</div>
		</div>
	</div>
</div>

<script>//# sourceURL=imgae_modal.js
$(function () {
	ImageModal.setOnCloseEvent();
});

var ImageModal = function () {
	var $modal = $('div#imageModal');
	
	var init = function (title, base64Str) {
	    var htmlStr = "data:image/gif;base64," + base64Str;
   		$modal.find('img#img')
   			.attr('src', htmlStr)
   			.width('auto')
   			.height('auto');
		$modal.find('h5#title').html(title);
	}
	
	var show = function () {
		$modal.modal('show');
	}
	
	var hide = function () {
		$modal.modal('hide');
	}
	
	var setOnCloseEvent = function (event) {
		$modal.unbind('hidden.bs.modal').on('hidden.bs.modal', function () {
			$modal.find('img#img').attr('src', '');
			if (event) {event();}
		})
	}
	
	return {
		init: init,
		show: show,
		hide: hide,
		setOnCloseEvent: setOnCloseEvent
	}
}();
</script>