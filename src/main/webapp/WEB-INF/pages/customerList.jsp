<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="customerList.title" /></title>
<meta name="menu" content="CustomerMenu" />
</head>

<c:if test="${not empty searchError}">
	<div class="alert alert-error fade in">
		<a href="#" data-dismiss="alert" class="close">&times;</a>
		<c:out value="${searchError}" />
	</div>
</c:if>

<div class="span12">
	<h2>
		<fmt:message key="customerList.heading" />
	</h2>

	<form method="get" action="${ctx}/customerList" id="searchForm" class="well form-horizontal">
		<div class="row">
			<div class="span5">
				<div class="control-group">
					<label for="customerType" class="control-label"><fmt:message key="customer.customerType" /></label>
					<div class="controls">
						<select id="customerType" name="customerType">
							<option value=""></option>
							<c:forEach items="${customerTypeList}" var="customerType">
								<option value="${customerType.value}" ${(customerType.value == param['customerType']) ? 'selected' : ''}>${customerType.label}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			<div class="span5">
				<div class="control-group">
					<label class="control-label" for="name"><fmt:message key="customer.name" />:</label>
					<div class="controls">
						<input type="text" class="input-xlarge" name="name" id="name" value="${param.name}" placeholder="" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="span5">
				<div class="control-group">
					<label class="control-label" for="contactName"><fmt:message key="customer.contactName" />:</label>
					<div class="controls">
						<input type="text" class="input-xlarge" name="contactName" id="contactName" value="${param.contactName}" placeholder="" />
					</div>
				</div>
			</div>
			<div class="span5">
				<div class="control-group">
					<label class="control-label" for="contactTel"><fmt:message key="customer.contactTel" />:</label>
					<div class="controls">
						<input type="text" class="input-xlarge" name="contactTel" id="contactTel" value="${param.contactTel}" placeholder="668" />
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
	<form method="post" action="${ctx}/customerList" id="deleteForm">
	<c:if test="${not empty customerList }">
	<div class="control-group pull-right">
		<fmt:message key="label.showPagination" />
		&nbsp;
		<%=request.getAttribute("psSelect")%>
	</div>
	</c:if>	
	<div id="actions">
		<a class="btn btn-primary" href="<c:url value='/customer?method=Add&from=list'/>"> <i class="icon-plus icon-white"></i> <fmt:message key="button.add" />
		</a>

		<button id="button.delete" class="btn" type="submit" onclick="return validateDelete()">
			<i class="icon-trash"></i>
			<fmt:message key="button.delete" />
		</button>

		<a class="btn" href="<c:url value='/mainMenu'/>"> <i class="icon-ok"></i> <fmt:message key="button.done" /></a>
		
	</div>
	<display:table name="customerList" cellspacing="0" cellpadding="0" requestURI="" id="customer"  pagesize="${ps}" class="table table-condensed table-striped table-hover table-bordered" export="true" size="resultSize" partialList="true" sort="external">
		<display:column title="<input type='checkbox' name='chkSelectAll' id='chkSelectAll'/>" class="span1" style="width: 10px" media="html">
			<input type="checkbox" id="checkbox" name="checkbox" value="<c:out value='${customer.id}'/>" />
		</display:column>
		<display:column property="id" url="/customer?from=list" paramId="id" paramProperty="id" escapeXml="true" sortable="true" titleKey="customer.id" sortName="id" />
		<display:column property="name" escapeXml="true" sortable="true" titleKey="customer.name" sortName="name" />
		<display:column property="contactName" escapeXml="true" sortable="true" titleKey="customer.contactName" sortName="contactName" />
		<display:column property="contactTel" escapeXml="true" sortable="true" titleKey="customer.contactTel" sortName="contactTel" />
		<display:setProperty name="export.excel.filename" value="Customer.xls" />
		<display:setProperty name="export.csv.filename" value="Customer.csv" />
		<display:setProperty name="export.pdf.filename" value="Customer.pdf" />
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


	<c:if test="${not empty customerList}">
	$(document).ready(function() {
		$("#chkSelectAll").click(function() {
			toggleCheckAll(this, document.forms['deleteForm'].checkbox);
		});
	});
	</c:if>
</script>

