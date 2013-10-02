<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<html lang="en">
<head>
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" href="<c:url value="/images/favicon.ico"/>" />
<title><decorator:title /> | <fmt:message key="webapp.name" /></title>

<%--     <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/lib/bootstrap-2.2.1.min.css'/>" /> --%>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/lib/bootstrap.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/lib/bootstrap-modal.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/lib/bootstrap-responsive-2.3.2.min.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/style.css'/>" />

<script type="text/javascript" src="<c:url value='/scripts/lib/jquery-1.8.2.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/lib/bootstrap-2.3.2.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/lib/plugins/jquery.cookie.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/script.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/util.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/lib/bootstrap-modal.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/lib/bootstrap-modalmanager.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/lib/bootbox.js'/>"></script>
<script>var ctx = "${pageContext.request.contextPath}"</script>
<decorator:head />


</head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>
    <c:set var="currentMenu" scope="request"><decorator:getProperty property="meta.menu"/></c:set>

    <div class="navbar navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container-fluid">
                <%-- For smartphones and smaller screens --%>
                <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="brand" href="<c:url value='/'/>"><fmt:message key="webapp.name"/></a>
                <%@ include file="/common/menu.jsp" %>
                 <div id="switchLocale"><a href="<c:url value='/?locale=en'/>"><fmt:message key="language.en"/> </a> | <a href="<c:url value='/?locale=th'/>"><fmt:message key="language.th"/></a>
                 </div>
                 <%--<c:if test="${pageContext.request.locale.language != 'en'}">
                </c:if> --%>
            </div>
        </div>
    </div>

	<div class="container-fluid">
		<%@ include file="/common/messages.jsp"%>
		<div class="row-fluid">
			<decorator:body />
		</div>
	</div>

	<div id="footer">
		<span class="right"><fmt:message key="webapp.version" /> <c:if test="${pageContext.request.remoteUser != null}">
            | <fmt:message key="user.status" /> ${pageContext.request.remoteUser}
            </c:if> &copy; <fmt:message key="copyright.year" /> <a href="<fmt:message key="company.url"/>"><fmt:message key="company.name" /></a> </span>
	</div>
	
	<div id="printDialog" class="modal hide fade" tabindex="-1"></div>
	
	<%=(request.getAttribute("scripts") != null) ? request.getAttribute("scripts") : ""%>
	<script type="text/javascript">
		function logout(ele) {
			confirmMessage('<fmt:message key="user.confirmLogout"/>', function(result) {
				if (result) {
					window.location = $(ele).prop('href');
				}
			});
			return false;
		}

		$(document).ready(function() {
			$('select[name=ps]').on('change', function() {
				changePageSize($('select[name=ps]')); 
			})
		});
		
		/**
		$(document).ajaxError(function( event, request, settings ) {
			alert(settings.url);
		});
		$(document).ajaxSuccess(function( event, xhr, settings ) {
			alert(settings.url);
		});
		*/
	</script>
</body>
</html>
