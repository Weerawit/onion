<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="invStockList.title" /></title>
<meta name="menu" content="InvMenu" />
</head>

<c:if test="${not empty searchError}">
	<div class="alert alert-error fade in">
		<a href="#" data-dismiss="alert" class="close">&times;</a>
		<c:out value="${searchError}" />
	</div>
</c:if>

<div class="span12">
	<h2>
		<fmt:message key="invStockList.heading" />
	</h2>

	<form method="get" action="${ctx}/invStockList" id="searchForm" class="well form-horizontal">
		<div class="control-group">
			<label class="control-label" for="code"><fmt:message key="invStock.invItem.code" />:</label>
			<div class="controls">
				<input type="text" class="input-medium" name="invItem.code" id="code" value="${param['invItem.code']}" placeholder="" />
			</div>
		</div>
		
		<div class="control-group">
			<div class="controls">

				<button id="button.search" class="btn" type="submit">
					<i class="icon-search"></i>
					<fmt:message key="button.search" />
				</button>

				<button id="button.reset" class="btn" type="button" onclick="clearForm($('#searchForm'))">
					<i class="icon-remove"></i>
					<fmt:message key="button.reset" />
				</button>
			</div>
		</div>
	</form>
	<form method="post" action="${ctx}/invStockList" id="deleteForm">
	<c:if test="${not empty invStockList }">
	<div class="control-group pull-right">
		<fmt:message key="label.showPagination" />
		&nbsp;
		<%=request.getAttribute("psSelect")%>
	</div>
	</c:if>	
	<div id="actions">

		<a class="btn" href="<c:url value='/mainMenu'/>"> <i class="icon-ok"></i> <fmt:message key="button.done" /></a>
		
	</div>
	<display:table name="invStockList" cellspacing="0" cellpadding="0" requestURI="" id="invStock"  pagesize="${ps}" class="table table-condensed table-striped table-hover table-bordered" export="true" size="resultSize" partialList="true" sort="external">
		<display:column property="invItem.code" url="/invStock?from=list" paramId="id" paramProperty="invItemId" escapeXml="true" sortable="true" titleKey="invStock.invItem.code" sortName="invItem.code" />
		<display:column property="invItem.name" escapeXml="true" sortable="true" titleKey="invStock.invItem.name" sortName="invItem.name" />
		<display:column property="qty" sortable="true" titleKey="invStock.qty" sortName="qty" format="{0,number,#,##0.##}"/>
		<display:column property="qtyAvailable" sortable="true" titleKey="invStock.qtyAvailable" sortName="qtyAvailable" format="{0,number,#,##0.##}"/>
		<display:setProperty name="export.csv" value="true"></display:setProperty>
		<display:setProperty name="export.excel" value="true"></display:setProperty>
		<display:setProperty name="export.xml" value="false"></display:setProperty>
		<display:setProperty name="export.pdf" value="true"></display:setProperty>
		<display:setProperty name="export.excel.filename" value="InvStock.xls" />
		<display:setProperty name="export.csv.filename" value="InvStock.csv" />
		<display:setProperty name="export.pdf.filename" value="InvStock.pdf" />
	</display:table>
	
	</form>
</div>
