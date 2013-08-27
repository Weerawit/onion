<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="userList.title" /></title>
<meta name="menu" content="AdminMenu" />
</head>

<c:if test="${not empty searchError}">
	<div class="alert alert-error fade in">
		<a href="#" data-dismiss="alert" class="close">&times;</a>
		<c:out value="${searchError}" />
	</div>
</c:if>

<div class="span10">
	<h2>
		<fmt:message key="userList.heading" />
	</h2>

	<form method="get" action="${ctx}/admin/users" id="searchForm" class="well form-horizontal">
		<div class="control-group">
			<label class="control-label" for="username"><fmt:message key="user.username" />:</label>
			<div class="controls">
				<input type="text" class="input-xlarge" name="username" id="username" value="${param.username}" placeholder="<fmt:message key="search.enterTerms"/>" />
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

	<form method="post" action="${ctx}/admin/users" id="deleteForm">
		<div id="actions">
			<a class="btn btn-primary" href="<c:url value='/userform?method=Add&from=list'/>"> <i class="icon-plus icon-white"></i> <fmt:message key="button.add" />
			</a>

			<button id="button.delete" class="btn" type="submit" onclick="return validateDelete()">
				<i class="icon-trash"></i>
				<fmt:message key="button.delete" />
			</button>

			<a class="btn" href="<c:url value='/mainMenu'/>"> <i class="icon-ok"></i> <fmt:message key="button.done" /></a>
		</div>
		<display:table name="userList" cellspacing="0" cellpadding="0" requestURI="" defaultsort="1" id="users" pagesize="25" class="table table-condensed table-striped table-hover table-bordered" export="false" size="resultSize" partialList="true" sort="external">
			<display:column title="<input type='checkbox' name='chkSelectAll' id='chkSelectAll'/>" headerClass="span1" class="span1">
				<input type="checkbox" id="checkbox" name="checkbox" value="<c:out value='${users.id}'/>" />
			</display:column>
			<display:column property="username" escapeXml="true" sortable="true" titleKey="user.username" url="/userform?from=list" paramId="id" paramProperty="id" sortName="username"/>
			<display:column sortProperty="enabled" sortable="true" titleKey="user.enabled" media="html" headerClass="span1" class="span1" sortName="enabled">
				<input type="checkbox" disabled="disabled" <c:if test="${users.enabled}">checked="checked"</c:if> />
			</display:column>
			<display:column property="enabled" titleKey="user.enabled" media="csv xml excel pdf" />

			<display:setProperty name="paging.banner.item_name">
				<fmt:message key="userList.user" />
			</display:setProperty>
			<display:setProperty name="paging.banner.items_name">
				<fmt:message key="userList.users" />
			</display:setProperty>

			<display:setProperty name="export.excel.filename" value="User List.xls" />
			<display:setProperty name="export.csv.filename" value="User List.csv" />
			<display:setProperty name="export.pdf.filename" value="User List.pdf" />
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
	<c:if test="${not empty userList}">
	$(document).ready(function() {
		$("#chkSelectAll").click(function() {
			toggleCheckAll(this, document.forms['deleteForm'].checkbox);
		});
	});
	</c:if>
</script>
