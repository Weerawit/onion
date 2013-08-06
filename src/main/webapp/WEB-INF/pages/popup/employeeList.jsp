<%@page import="com.worldbestsoft.model.Employee"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="com.google.gson.Gson"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="employeeList.title" /></title>
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
		<fmt:message key="employeeList.heading" />
	</h2>

	<form method="get" action="${ctx}/popup/employee" id="searchForm" class="well form-horizontal">
		<div class="control-group">
			<label class="control-label" for="firstName"><fmt:message key="employee.firstName" />:</label>
			<div class="controls">
				<input type="text" class="input-xlarge" name="firstName" id="firstName" value="${param.firstName}" placeholder="" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="lastName"><fmt:message key="employee.lastName" />:</label>
			<div class="controls">
				<input type="text" class="input-xlarge" name="lastName" id="lastName" value="${param.lastName}" placeholder="" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="nickName"><fmt:message key="employee.nickName" />:</label>
			<div class="controls">
				<input type="text" class="input-xlarge" name="nickName" id="nickName" value="${param.nickName}" placeholder="" />
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
	<c:if test="${not empty employeeList }">
	<div class="control-group pull-right">
		<fmt:message key="label.showPagination" />
		&nbsp;
		<%=request.getAttribute("psSelect")%>
	</div>
	</c:if>	
	<display:table name="employeeList" cellspacing="0" cellpadding="0" requestURI="" id="employee"  pagesize="${ps}" class="table table-condensed table-striped table-hover table-bordered" export="false" size="resultSize" partialList="true" sort="external">
		<display:column title="" headerClass="span1" class="span1">
			<%
				Employee employeeObj = (Employee) employee;
				Gson gson = new GsonBuilder().create();
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("id", employeeObj.getId().toString());
				jsonObject.addProperty("firstName", employeeObj.getFirstName());
				jsonObject.addProperty("lastName", employeeObj.getLastName());
				jsonObject.addProperty("nickName", employeeObj.getNickName());
				jsonObject.addProperty("address", employeeObj.getAddress());
				jsonObject.addProperty("wage", employeeObj.getWage().toString());
				String json = gson.toJson(jsonObject);
			%>
			<input type="radio" id="radio" name="radio" value='<%=json%>' />
		</display:column>
		<display:column property="firstName" escapeXml="true" sortable="true" titleKey="employee.firstName" sortName="firstName" />
		<display:column property="lastName" escapeXml="true" sortable="true" titleKey="employee.lastName" sortName="lastName" />
		<display:column property="nickName" escapeXml="true" sortable="true" titleKey="employee.nickName" sortName="nickName" />
	</display:table>
</div>
