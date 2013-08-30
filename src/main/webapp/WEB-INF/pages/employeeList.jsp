<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="employeeList.title" /></title>
<meta name="menu" content="EmployeeMenu" />
</head>

<c:if test="${not empty searchError}">
	<div class="alert alert-error fade in">
		<a href="#" data-dismiss="alert" class="close">&times;</a>
		<c:out value="${searchError}" />
	</div>
</c:if>

<div class="span12">
	<h2>
		<fmt:message key="employeeList.heading" />
	</h2>

	<form method="get" action="${ctx}/employeeList" id="searchForm" class="well form-horizontal">
		<div class="row">
			<div class="span5">
				<div class="control-group">
					<label class="control-label" for="firstName"><fmt:message key="employee.firstName" />:</label>
					<div class="controls">
						<input type="text" class="input-xlarge" name="firstName" id="firstName" value="${param.firstName}" placeholder="" />
					</div>
				</div>
			</div>
			<div class="span5">
				<div class="control-group">
					<label class="control-label" for="lastName"><fmt:message key="employee.lastName" />:</label>
					<div class="controls">
						<input type="text" class="input-xlarge" name="lastName" id="lastName" value="${param.lastName}" placeholder="" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="span5">
				<div class="control-group">
					<label class="control-label" for="nickName"><fmt:message key="employee.nickName" />:</label>
					<div class="controls">
						<input type="text" class="input-xlarge" name="nickName" id="nickName" value="${param.nickName}" placeholder="" />
					</div>
				</div>
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
	<form method="post" action="${ctx}/employeeList" id="deleteForm">
	<c:if test="${not empty employeeList }">
	<div class="control-group pull-right">
		<fmt:message key="label.showPagination" />
		&nbsp;
		<%=request.getAttribute("psSelect")%>
	</div>
	</c:if>	
	<div id="actions">
		<a class="btn btn-primary" href="<c:url value='/employee?method=Add&from=list'/>"> <i class="icon-plus icon-white"></i> <fmt:message key="button.add" />
		</a>

		<button id="button.delete" class="btn" type="submit" onclick="return validateDelete()">
			<i class="icon-trash"></i>
			<fmt:message key="button.delete" />
		</button>

		<a class="btn" href="<c:url value='/mainMenu'/>"> <i class="icon-ok"></i> <fmt:message key="button.done" /></a>
		
	</div>
	<display:table name="employeeList" cellspacing="0" cellpadding="0" requestURI="" id="employee"  pagesize="${ps}" class="table table-condensed table-striped table-hover table-bordered" export="true" size="resultSize" partialList="true" sort="external">
		<display:column title="<input type='checkbox' name='chkSelectAll' id='chkSelectAll'/>" headerClass="span1" class="span1">
			<input type="checkbox" id="checkbox" name="checkbox" value="<c:out value='${employee.id}'/>" />
		</display:column>
		<display:column property="id" url="/employee?from=list" paramId="id" paramProperty="id" escapeXml="true" sortable="true" titleKey="employee.id" sortName="id" />
		<display:column property="firstName" escapeXml="true" sortable="true" titleKey="employee.firstName" sortName="firstName" />
		<display:column property="lastName" escapeXml="true" sortable="true" titleKey="employee.lastName" sortName="lastName" />
		<display:column property="nickName" escapeXml="true" sortable="true" titleKey="employee.nickName" sortName="nickName" />
		<display:setProperty name="export.csv" value="true"></display:setProperty>
		<display:setProperty name="export.excel" value="true"></display:setProperty>
		<display:setProperty name="export.xml" value="false"></display:setProperty>
		<display:setProperty name="export.pdf" value="true"></display:setProperty>
		<display:setProperty name="export.excel.filename" value="Employee.xls" />
		<display:setProperty name="export.csv.filename" value="Employee.csv" />
		<display:setProperty name="export.pdf.filename" value="Employee.pdf" />
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

	<c:if test="${not empty employeeList}">
	$(document).ready(function() {
		$("#chkSelectAll").click(function() {
			toggleCheckAll(this, document.forms['deleteForm'].checkbox);
		});
	});
	</c:if>
</script>

