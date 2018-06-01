<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
	<meta charset="UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
	<meta name="description" content="Responsive Multi-Level Menu: Space-saving drop-down menu with subtle effects" />
	<meta name="keywords" content="multi-level menu, mobile menu, responsive, space-saving, drop-down menu, css, jquery" />
	<meta name="author" content="Codrops" />
	
	<link rel="stylesheet" type="text/css" href="dataTable/library/1.10.12/css/jquery.dataTables.ui.css"/>
	<link rel="stylesheet" type="text/css" href="custom/elitech.css" />
	<link rel="stylesheet" type="text/css" href="bootstrap/3.3.5/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="tympanus/20130508/component.css" />
	<link rel="stylesheet" type="text/css" href="dataTable/library/custom/css/style-bootstrap-custom.css"/>
	<link rel="stylesheet" type="text/css" href="dataTable/library/custom/css/style.css"/>
	<link rel="stylesheet" type="text/css" href="dataTable/library/custom/css/theme-default.css"/>
	
	<script src="jquery/1.12.0/js/jquery.js"></script>
	<script src="bootstrap/3.3.5/bootstrap.js"></script>
	<script src="tympanus/20130508/modernizr.custom.js"></script>
	<script src="tympanus/20130508/jquery.dlmenu.js"></script>
	<script type="text/javascript" src="dataTable/library/1.10.12/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="dataTable/library/1.10.12/js/DT_bootstrap.js"></script>
	
	<script type="text/javascript" src="index.js"></script>
	
	<title></title>
</head>
<body>
<div id='header' class='header' style='text-align: center;'>
	<h2>Header</h2>
</div>
<div id='menu' class='menu'>
	<div class="demo clearfix">
		<div id="dl-menu" class="dl-menuwrapper">
			<button class="dl-trigger">Open Menu</button>
			<ul class="dl-menu">
				<li>
					<a href="#">人事資源</a>
					<ul class="dl-submenu">
						<li>
							<a href="#">人事管理</a>
							<ul class="dl-submenu">
								<li>
									<a href="#">組織管理</a>
								</li>
								<li>
									<a href="#">員工管理</a>
									<ul class="dl-submenu">
										<li>
											<a href="#">新進</a>
										</li>
										<li>
											<a href="#">調派</a>
										</li>
										<li>
											<a href="#">離退</a>
										</li>
										<li>
											<a href="#">留停</a>
										</li>
									</ul>
								</li>
								<li>
									<a href="#">批次作業</a>
									<ul class="dl-submenu">
										<li>
											<a href="#">新進</a>
										</li>
										<li>
											<a href="#">更新</a>
										</li>
									</ul>
								</li>
							</ul>
						</li>
						<li>
							<a href="#">權限設定</a>
							<ul class="dl-submenu">
								<li>
									<a href="#">群組權限管理</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="#">屬性設定</a>
							<ul class="dl-submenu">
								<li>
									<a href="#">職稱資料維護</a>
								</li>
								<li>
									<a href="#">員工別維護</a>
								</li>
								<li>
									<a href="#">學歷類別管理</a>
								</li>
							</ul>
						</li>
					</ul>
				</li>
				<li>
					<a href="#">請假管理</a>
					<ul class="dl-submenu">
						<li>
							<a href="#">查詢</a>
							<ul class="dl-submenu">
								<li>
									<a href="#">請假明細查詢</a>
								</li>
								<li>
									<a href="#">年度休假天數查詢</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="#">假日管理</a>
							<ul class="dl-submenu">
								<li>
									<a href="#">假日行事曆</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="#">屬性設定</a>
							<ul class="dl-submenu">
								<li>
									<a href="#">假別管理</a>
								</li>
							</ul>
						</li>
					</ul>
				</li>
				<li>
					<a href="#">個人管理</a>
					<ul class="dl-submenu">
						<li>
							<a href="#">簽核作業</a>
							<ul class="dl-submenu">
								<li>
									<a href="#">待簽核表單</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="#">申請業務</a>
							<ul class="dl-submenu">
								<li>
									<a href="#">請假申請</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="#">個人資料維護</a>
						</li>
						<li>
							<a href="#">表單查詢</a>
						</li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</div>
<div id='content' class='content'>
	<div class='form-group'>
		<div class='row'>
			<button id='saveButton' type="button" class="btn btn-default pull-right">儲存</button>
		</div>
	</div>
	
	<div id='result'>
		<div class='form-group'>
			<table id="dataTable" class="table table-striped table-bordered table-hover">
				<thead></thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
</div>
<div id='footer' class='footer' style='text-align: center;'><h2>Footer</h2></div>
</body>
</html>