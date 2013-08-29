<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="mainMenu.title"/></title>
    <meta name="menu" content="MainMenu"/>
</head>
<body class="home">

<div class="span12">
	<h2><fmt:message key="mainMenu.heading"/></h2>
	<p><fmt:message key="mainMenu.message"/></p>
	
	<ul class="glassList">
	    <li>
	        <a href="<c:url value='/userform'/>"><fmt:message key="menu.user"/></a>
	    </li>
	    <li>
	        <a href="<c:url value='/fileupload'/>"><fmt:message key="menu.selectFile"/></a>
	    </li>
	</ul>
</div>
</body>
