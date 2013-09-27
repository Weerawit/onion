<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="mainMenu.title" /></title>
<meta name="menu" content="MainMenu" />
</head>
<body class="home">

	<div class="span12">
		<h2>
			<fmt:message key="mainMenu.heading" />
		</h2>
		<p>
			<fmt:message key="mainMenu.message" />
		</p>
		<menu:useMenuDisplayer name="Velocity" config="mainMenu.vm" permissions="rolesAdapter">
			<menu:displayMenu name="SaleOrderMenu" />
			<menu:displayMenu name="JobOrderMenu" />
			<menu:displayMenu name="InvMenu" />
		</menu:useMenuDisplayer>
	</div>
</body>
