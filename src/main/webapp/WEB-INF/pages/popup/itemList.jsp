<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.worldbestsoft.model.InvItem"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="com.google.gson.Gson"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="invItemList.title" /></title>
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
		<fmt:message key="invItemList.heading" />
	</h2>

	<form method="get" action="${ctx}/popup/item" id="searchForm" class="well form-horizontal">
		<div class="control-group">
			<label for="invItem.invItemGroup.code" class="control-label"><fmt:message key="invItem.invItemGroup.code" /></label>
			<div class="controls">
				<select id=invItem.invItemGroup.code name="invItem.invItemGroup.code">
					<option value=""></option>
					<c:forEach items="${invItemGroupList}" var="invItemGroup">
						<option value="${invItemGroup.code}" ${(invItemGroup.code == param['invItem.invItemGroup.code']) ? 'selected' : ''}>${invItemGroup.code} : ${invItemGroup.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="code"><fmt:message key="invItem.code" />:</label>
			<div class="controls">
				<input type="text" class="input-medium" name="code" id="code" value="${param.code}" placeholder="" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="name"><fmt:message key="invItem.name" />:</label>
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
	<c:if test="${not empty invItemList }">
	<div class="control-group pull-right">
		<fmt:message key="label.showPagination" />
		&nbsp;
		<%=request.getAttribute("psSelect")%>
	</div>
	</c:if>	
	<display:table name="invItemList" cellspacing="0" cellpadding="0" requestURI="" id="invItem"  pagesize="${ps}" class="table table-condensed table-striped table-hover table-bordered" export="false" size="resultSize" partialList="true" sort="external">
		<display:column title="" headerClass="span1" class="span1">
			<%
				InvItem invItemObj = (InvItem) invItem;
				Gson gson = new GsonBuilder().create();
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("code", invItemObj.getCode());
				jsonObject.addProperty("name", invItemObj.getName());
				String json = gson.toJson(jsonObject);
				System.out.print(json);
			%>
			<input type="radio" id="radio" name="radio" value='<%=json%>' />
		</display:column>
		<display:column property="code" escapeXml="true" sortable="true" titleKey="invItem.code" sortName="code" />
		<display:column property="name" escapeXml="true" sortable="true" titleKey="invItem.name" sortName="name" />
	</display:table>
</div>

