<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="invItemList.title" /></title>
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
		<fmt:message key="invItemList.heading" />
	</h2>

	<form method="get" action="${ctx}/invItemList" id="searchForm" class="well form-horizontal">
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group">
				<label for="invItem.invItemGroup.code" class="control-label"><fmt:message key="invItem.invItemGroup.code" /></label>
				<div class="controls">
					<select id="invItemGroup.code" name="invItemGroup.code">
						<option value=""></option>
						<c:forEach items="${invItemGroupList}" var="invItemGroup">
							<option value="${invItemGroup.code}" ${(invItemGroup.code == param['invItemGroup.code']) ? 'selected' : ''}>${invItemGroup.code} : ${invItemGroup.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group">
				<label class="control-label" for="code"><fmt:message key="invItem.code" />:</label>
				<div class="controls">
					<input type="text" class="input-medium" name="code" id="code" value="${param.code}" placeholder="" />
				</div>
			</div>
		</div>
		<div class="span6">
			<div class="control-group">
				<label class="control-label" for="name"><fmt:message key="invItem.name" />:</label>
				<div class="controls">
					<input type="text" class="input-xlarge" name="name" id="name" value="${param.name}" placeholder="" />
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
	<form method="post" action="${ctx}/invItemList" id="deleteForm">
	<c:if test="${not empty invItemList }">
	<div class="control-group pull-right">
		<fmt:message key="label.showPagination" />
		&nbsp;
		<%=request.getAttribute("psSelect")%>
	</div>
	</c:if>	
	<div id="actions">
		<a class="btn btn-primary" href="<c:url value='/invItem?method=Add&from=list'/>"> <i class="icon-plus icon-white"></i> <fmt:message key="button.add" />
		</a>

		<button id="button.delete" class="btn" type="submit" onclick="return validateDelete()">
			<i class="icon-trash"></i>
			<fmt:message key="button.delete" />
		</button>

		<a class="btn" href="<c:url value='/mainMenu'/>"> <i class="icon-ok"></i> <fmt:message key="button.done" /></a>
		
	</div>
	<display:table name="invItemList" cellspacing="0" cellpadding="0" requestURI="" id="invItem"  pagesize="${ps}" class="table table-condensed table-striped table-hover table-bordered" export="true" size="resultSize" partialList="true" sort="external">
		<display:column title="<input type='checkbox' name='chkSelectAll' id='chkSelectAll'/>" class="span1" style="width: 10px" media="html">
			<input type="checkbox" id="checkbox" name="checkbox" value="<c:out value='${invItem.id}'/>" />
		</display:column>
		<display:column property="code" url="/invItem?from=list" paramId="id" paramProperty="id" escapeXml="true" sortable="true" titleKey="invItem.code" sortName="code" />
		<display:column property="name" escapeXml="true" sortable="true" titleKey="invItem.name" sortName="name" />
		<display:setProperty name="export.excel.filename" value="InvItem.xls" />
		<display:setProperty name="export.csv.filename" value="InvItem.csv" />
		<display:setProperty name="export.pdf.filename" value="InvItem.pdf" />
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

	<c:if test="${not empty invItemList}">
	$(document).ready(function() {
		$("#chkSelectAll").click(function() {
			toggleCheckAll(this, document.forms['deleteForm'].checkbox);
		});
	});
	</c:if>
</script>

