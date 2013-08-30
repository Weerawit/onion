<%@page import="com.worldbestsoft.model.Customer"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="com.google.gson.Gson"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="customerList.title" /></title>
<meta name="decorator" content="popup"/>
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

	<form method="get" action="${ctx}/popup/customer" id="searchForm" class="well form-horizontal">
		<div class="row">
			<div class="span5">
				<div class="control-group">
					<label for="customerType" class="control-label"><fmt:message key="customer.customerType" /></label>
					<div class="controls">
						<select id=customerType name="customerType">
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
	<c:if test="${not empty customerList }">
	<div class="control-group pull-right">
		<fmt:message key="label.showPagination" />
		&nbsp;
		<%=request.getAttribute("psSelect")%>
	</div>
	</c:if>	
	
	<display:table name="customerList" cellspacing="0" cellpadding="0" requestURI="" id="customer"  pagesize="${ps}" class="table table-condensed table-striped table-hover table-bordered" export="false" size="resultSize" partialList="true" sort="external">
		<display:column title="" headerClass="span1" class="span1">
			<%
				Customer customerObj = (Customer) customer;
				Gson gson = new GsonBuilder().create();
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("id", customerObj.getId().toString());
				jsonObject.addProperty("name", customerObj.getName());
				jsonObject.addProperty("customerType", customerObj.getCustomerType());
				jsonObject.addProperty("shipingAddress", customerObj.getShipingAddress());
				jsonObject.addProperty("billingAddress", customerObj.getBillingAddress());
				jsonObject.addProperty("contactName", customerObj.getContactName());
				jsonObject.addProperty("contactTel", customerObj.getContactTel());
				jsonObject.addProperty("memo", customerObj.getMemo());
				String json = gson.toJson(jsonObject);
			%>
			<input type="radio" id="radio" name="radio" value='<%=json%>' />
		</display:column>
		<display:column property="name" escapeXml="true" sortable="true" titleKey="customer.name" sortName="name" />
		<display:column property="contactName" escapeXml="true" sortable="true" titleKey="customer.contactName" sortName="contactName" />
		<display:column property="contactTel" escapeXml="true" sortable="true" titleKey="customer.contactTel" sortName="contactTel" />
	</display:table>
	
</div>
