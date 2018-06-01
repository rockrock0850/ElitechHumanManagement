<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>

<div id='content' class='content'>
	<div class='row'>
		<div class='col-md-12'>
			<div class='panel panel-default'>
				<div class='panel-heading'>
					<label>發生錯誤</label>
				</div>
				<div class='panel-body'>
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-2'>
								<label>錯誤代碼:</label>
							</div>
							<div class='col-md-1'>
								<label>${exception.code}</label>
							</div>
						</div>
					</div>
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-2'>
								<label>錯誤訊息:</label>
							</div>
							<div class='col-md-10'>
								<label>${exception.msg}</label>
							</div>
						</div>
					</div>
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-12'>
								<label>錯誤發生位置:</label>
							</div>
						</div>
					</div>
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-12'>
								<label>${exception.target}</label>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>