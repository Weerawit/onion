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
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/lib/bootstrap-responsive-2.2.1.min.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/style.css'/>" />

<script type="text/javascript" src="<c:url value='/scripts/lib/jquery-1.8.2.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/lib/bootstrap-2.2.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/lib/plugins/jquery.cookie.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/script.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/util.js'/>"></script>
<decorator:head />


</head>
<body <decorator:getProperty property="body.id" writeEntireProperty="true"/> <decorator:getProperty property="body.class" writeEntireProperty="true"/>>
	<c:set var="currentMenu" scope="request">
		<decorator:getProperty property="meta.menu" />
	</c:set>

	<div class="container-fluid">
		<%@ include file="/common/messages.jsp"%>
		<div class="row-fluid">
			<decorator:body />
		</div>
	</div>

	<div id="footer">
		<span class="left"><img src="<c:url value='/images/logo2.png'/>" /> </span> <span class="right"><fmt:message key="webapp.version" /> <c:if test="${pageContext.request.remoteUser != null}">
            | <fmt:message key="user.status" /> ${pageContext.request.remoteUser}
            </c:if> &copy; <fmt:message key="copyright.year" /> <a href="<fmt:message key="company.url"/>"><fmt:message key="company.name" /></a> </span>
	</div>
	<%=(request.getAttribute("scripts") != null) ? request.getAttribute("scripts") : ""%>
</body>
</html>
