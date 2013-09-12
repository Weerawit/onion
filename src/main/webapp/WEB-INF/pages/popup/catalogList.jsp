<%@page import="com.worldbestsoft.model.Catalog"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="com.google.gson.Gson"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="catalogList.title" /></title>
<meta name="decorator" content="popup"/>
</head>

<c:if test="${not empty searchError}">
	<div class="alert alert-error fade in">
		<a href="#" data-dismiss="alert" class="close">&times;</a>
		<c:out value="${searchError}" />
	</div>
</c:if>

<div class="span12">
	<form method="get" action="${ctx}/popup/catalog" id="searchForm" class="well form-horizontal">
	<div class="row-fluid">
		<div class="span5">
			<div class="control-group">
				<label for="catalog.catalogType.code" class="control-label"><fmt:message key="catalog.catalogType.code" /></label>
				<div class="controls">
					<select id="catalogType.code" name="catalogType.code">
						<option value=""></option>
						<c:forEach items="${catalogTypeList}" var="catalogType">
							<option value="${catalogType.code}" ${(catalogType.code == param['catalogType.code']) ? 'selected' : ''}>${catalogType.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span5">
			<div class="control-group">
				<label class="control-label" for="code"><fmt:message key="catalog.code" />:</label>
				<div class="controls">
					<input type="text" class="input-medium" name="code" id="code" value="${param.code}" placeholder="" />
				</div>
			</div>
		</div>
		<div class="span5">
			<div class="control-group">
				<label class="control-label" for="name"><fmt:message key="catalog.name" />:</label>
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
	
	<c:if test="${not empty catalogList }">
	<div class="control-group pull-right">
		<fmt:message key="label.showPagination" />
		&nbsp;
		<%=request.getAttribute("psSelect")%>
	</div>
	</c:if>	
	
	<display:table name="catalogList" cellspacing="0" cellpadding="0" requestURI="" id="catalog"  pagesize="${ps}" class="table table-condensed table-striped table-hover table-bordered" export="false" size="resultSize" partialList="true" sort="external">
		<display:column title="" headerClass="span1" class="span1">
			<%
				Catalog catalogObj = (Catalog) catalog;
				Gson gson = new GsonBuilder().create();
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("id", catalogObj.getId().toString());
				jsonObject.addProperty("code", catalogObj.getCode());
				jsonObject.addProperty("name", catalogObj.getName());
				jsonObject.addProperty("finalPrice", catalogObj.getFinalPrice());
				String json = gson.toJson(jsonObject);
			%>
			<input type="radio" id="radio" name="radio" value='<%=json%>' />
		</display:column>
		<display:column titleKey="catalog.img" sortable="false">
			<img id="image" src="<c:url value='/img/thumbnail/catalog/${catalog.id}?t=100'/>" class="img-polaroid"/>
		</display:column>
		<display:column property="code" escapeXml="true" sortable="true" titleKey="catalog.code" sortName="code" />
		<display:column property="name" escapeXml="true" sortable="true" titleKey="catalog.name" sortName="name" />
		<display:column property="finalPrice"  sortable="true" titleKey="catalog.finalPrice" sortName="finalPrice" format="{0,number,#,###.00}" />
		<display:column property="invItem.invStock.qtyAvailable" escapeXml="false" sortable="true" titleKey="invItem.invStock.qtyAvailable" sortName="invItem.invStock.qtyAvailable" format="{0,number, #,##0.##}"/>
	</display:table>
</div>


