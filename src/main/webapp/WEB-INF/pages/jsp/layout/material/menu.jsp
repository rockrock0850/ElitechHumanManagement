<%@ page pageEncoding="UTF-8" %>
<%@ include file='/WEB-INF/pages/jsp/layout/material/globle.jsp' %>
<jsp:useBean id="now" class="java.util.Date" />
<script type="text/javascript" src="${contextPath}/pages/js/layout/material/menu.js?v=${now}"></script>
<div id='menu'>
	<ul id="accordion" class="navbar-nav navbar-sidenav">
		<c:forEach var="menuLv1" items="${menus}" varStatus="statusLv1">
			<li class="nav-item" data-toggle="tooltip" data-placement="right" title="${menuLv1.menuName}">
				<a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#${menuLv1.menuId}-${statusLv1.index}" data-parent="#accordion">
					<i class="${menuLv1.icon}"></i>
					<span class="nav-link-text">${menuLv1.menuName}</span>
				</a>
				<ul id="${menuLv1.menuId}-${statusLv1.index}" class="sidenav-second-level collapse">
					<c:choose>
						<c:when test="${menuLv1.subMenus != null}">
							<c:forEach var='menuLv2' items='${menuLv1.subMenus}' varStatus='statusLv2'>
								<li>
									<a class="nav-link-collapse collapsed" data-toggle="collapse" href="#${menuLv2.menuId}-${statusLv2.index}">
										${menuLv2.menuName}
									</a>
									<ul id="${menuLv2.menuId}-${statusLv2.index}" class="sidenav-third-level collapse">
										<c:forEach var='function' items='${menuLv2.functions}' varStatus='statusFunc'>
											<li><a href="#" path='${function.namespace}' functionId='${function.functionId}'>${function.functionName}</a></li>
										</c:forEach>
									</ul>
								</li>
							</c:forEach>
							<c:if test="${menuLv1.functions != null}">
								<c:forEach var='function' items='${menuLv1.functions}' varStatus='statusFunc'>
									<li><a href="#" path='${function.namespace}' functionId='${function.functionId}'>${function.functionName}</a></li>
								</c:forEach>
							</c:if>
						</c:when>
						<c:otherwise>
							<c:forEach var='function' items='${menuLv1.functions}' varStatus='statusFunc'>
								<li><a href="#" path='${function.namespace}' functionId='${function.functionId}'>${function.functionName}</a></li>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</ul>
			</li>
		</c:forEach>
	</ul>
</div>