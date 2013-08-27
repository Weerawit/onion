<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="catalogList.title" /></title>
<meta name="menu" content="CatalogMenu" />
</head>

<c:if test="${not empty searchError}">
	<div class="alert alert-error fade in">
		<a href="#" data-dismiss="alert" class="close">&times;</a>
		<c:out value="${searchError}" />
	</div>
</c:if>

<div class="span12">
	<h2>
		<fmt:message key="catalogList.heading" />
	</h2>

	<form method="get" action="${ctx}/catalogList" id="searchForm" class="well form-horizontal">
		<div class="control-group">
			<label for="catalog.catalogType.code" class="control-label"><fmt:message key="catalog.catalogType.code" /></label>
			<div class="controls">
				<select id=catalogType.code name="catalogType.code">
					<option value=""></option>
					<c:forEach items="${catalogTypeList}" var="catalogType">
						<option value="${catalogType.code}" ${(catalogType.code == param['catalogType.code']) ? 'selected' : ''}>${catalogType.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="code"><fmt:message key="catalog.code" />:</label>
			<div class="controls">
				<input type="text" class="input-medium" name="code" id="code" value="${param.code}" placeholder="" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="name"><fmt:message key="catalog.name" />:</label>
			<div class="controls">
				<input type="text" class="input-xlarge" name="name" id="name" value="${param.name}" placeholder="" />
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
	<form method="post" action="${ctx}/catalogList" id="deleteForm">
	<c:if test="${not empty catalogList }">
	<div class="control-group pull-right">
		<fmt:message key="label.showPagination" />
		&nbsp;
		<%=request.getAttribute("psSelect")%>
	</div>
	</c:if>	
	<div id="actions">
		<a class="btn btn-primary" href="<c:url value='/catalog?method=Add&from=list'/>"> <i class="icon-plus icon-white"></i> <fmt:message key="button.add" />
		</a>

		<button id="button.delete" class="btn" type="submit"  onclick="return validateDelete()">
			<i class="icon-trash"></i>
			<fmt:message key="button.delete" />
		</button>

		<a class="btn" href="<c:url value='/mainMenu'/>"> <i class="icon-ok"></i> <fmt:message key="button.done" /></a>
		
	</div>
	<display:table name="catalogList" cellspacing="0" cellpadding="0" requestURI="" id="catalog"  pagesize="${ps}" class="table table-condensed table-striped table-hover table-bordered" export="true" size="resultSize" partialList="true" sort="external">
		<display:column title="<input type='checkbox' name='chkSelectAll' id='chkSelectAll'/>" headerClass="span1" class="span1">
			<input type="checkbox" id="checkbox" name="checkbox" value="<c:out value='${catalog.id}'/>" />
		</display:column>
		<display:column property="code" url="/catalog?from=list" paramId="id" paramProperty="id" escapeXml="true" sortable="true" titleKey="catalog.code" sortName="code" />
		<display:column property="name" escapeXml="true" sortable="true" titleKey="catalog.name" sortName="name" />
		<display:setProperty name="export.csv" value="true"></display:setProperty>
		<display:setProperty name="export.excel" value="true"></display:setProperty>
		<display:setProperty name="export.xml" value="false"></display:setProperty>
		<display:setProperty name="export.pdf" value="true"></display:setProperty>
		<display:setProperty name="export.excel.filename" value="Catalog.xls" />
		<display:setProperty name="export.csv.filename" value="Catalog.csv" />
		<display:setProperty name="export.pdf.filename" value="Catalog.pdf" />
	</display:table>
	
	</form>
</div>


<script type="text/javascript">
	function validateDelete() {
		var form = document.forms['deleteForm'];
		if (!hasChecked(form.checkbox)) {
			alert('<fmt:message key="global.errorNoCheckboxSelectForDelete"/>');
			return false;
		}
		confirmMessage('<fmt:message key="global.confirm.delete"/>', function(result) {
			if (result) {
				form.submit();
			}
		});
		return false;
	}

	<c:if test="${not empty catalogList}">
	$(document).ready(function() {
		$("#chkSelectAll").click(function() {
			toggleCheckAll(this, document.forms['deleteForm'].checkbox);
		});
	});
	</c:if>
</script>

