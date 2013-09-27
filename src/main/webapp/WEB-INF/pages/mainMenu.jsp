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
		<p>
			<security:authorize ifAnyGranted="IS_AUTHENTICATED_REMEMBERED">IS_AUTHENTICATED_REMEMBERED</security:authorize>
		</p>
		<menu:useMenuDisplayer name="Velocity" config="mainMenu.vm" permissions="rolesAdapter">
			<menu:displayMenu name="UserMenu" />
			<menu:displayMenu name="SaleOrderMenu" />
			<menu:displayMenu name="JobOrderMenu" />
			<menu:displayMenu name="CatalogMenu" />
			<menu:displayMenu name="InvMenu" />
			<menu:displayMenu name="EmployeeMenu" />
			<menu:displayMenu name="CustomerMenu" />
			<menu:displayMenu name="AdminMenu" />
		</menu:useMenuDisplayer>
	</div>
</body>
