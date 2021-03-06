<%@page import="com.worldbestsoft.model.Supplier"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="com.google.gson.Gson"%>
<%@ include file="/common/taglibs.jsp"%>


<head>
<title><fmt:message key="supplierList.title" /></title>
<meta name="decorator" content="popup"/>
</head>

<c:if test="${not empty searchError}">
	<div class="alert alert-error fade in">
		<a href="#" data-dismiss="alert" class="close">&times;</a>
		<c:out value="${searchError}" />
	</div>
</c:if>

<div class="span12">
	<form method="get" action="${ctx}/popup/supplierList" id="searchForm" class="well form-horizontal">
	<div class="row-fluid">
		<div class="span5">
			<div class="control-group">
				<label class="control-label" for="code"><fmt:message key="supplier.code" />:</label>
				<div class="controls">
					<input type="text" class="input-medium" name="code" id="code" value="${param.code}" placeholder="" />
				</div>
			</div>
		</div>
		<div class="span5">
			<div class="control-group">
				<label class="control-label" for="name"><fmt:message key="supplier.name" />:</label>
				<div class="controls">
					<input type="text" class="input-xlarge" name="name" id="name" value="${param.name}" placeholder="" />
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span5">
			<div class="control-group">
				<label class="control-label" for="name"><fmt:message key="supplier.contactName" />:</label>
				<div class="controls">
					<input type="text" class="input-xlarge" name="contactName" id="contactName" value="${param.contactName}" placeholder="" />
				</div>
			</div>
		</div>
		<div class="span5">
			<div class="control-group">
				<label class="control-label" for="name"><fmt:message key="supplier.contactTel" />:</label>
				<div class="controls">
					<input type="text" class="input-xlarge" name="contactTel" id="contactTel" value="${param.contactTel}" placeholder="66xxxxxxxxx" />
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
	<c:if test="${not empty supplierList }">
	<div class="control-group pull-right">
		<fmt:message key="label.showPagination" />
		&nbsp;
		<%=request.getAttribute("psSelect")%>
	</div>
	</c:if>	
	<display:table name="supplierList" cellspacing="0" cellpadding="0" requestURI="" id="supplier"  pagesize="${ps}" class="table table-condensed table-striped table-hover table-bordered" export="false" size="resultSize" partialList="true" sort="external">
		<display:column title="" headerClass="span1" class="span1">
			<%
				Supplier supplierObj = (Supplier) supplier;
				Gson gson = new GsonBuilder().create();
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("id", supplierObj.getId().toString());
				jsonObject.addProperty("code", supplierObj.getCode());
				jsonObject.addProperty("name", supplierObj.getName());
				jsonObject.addProperty("address", supplierObj.getAddress());
				jsonObject.addProperty("contactName", supplierObj.getContactName());
				jsonObject.addProperty("contactTel", supplierObj.getContactTel());
				String json = gson.toJson(jsonObject);
			%>
			<input type="radio" id="radio" name="radio" value='<%=json%>' />
		</display:column>
		<display:column property="code"  escapeXml="true" sortable="true" titleKey="supplier.code" sortName="code" />
		<display:column property="name" escapeXml="true" sortable="true" titleKey="supplier.name" sortName="name" />
		<display:column property="contactName" escapeXml="true" sortable="true" titleKey="supplier.contactName" sortName="contactName" />
		<display:column property="contactTel" escapeXml="true" sortable="true" titleKey="supplier.contactTel" sortName="contactTel" />
	</display:table>
	
</div>
