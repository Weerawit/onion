<%@ include file="/common/taglibs.jsp"%>
<display:table name="catalogItemList" cellspacing="0" cellpadding="0" requestURI="" id="catalogItem" class="table table-condensed table-striped table-hover table-bordered">
	<display:column titleKey="catalogItem.invItem.code" sortable="true" class="span3">
		<c:out value="${catalogItem.invItem.code}" />
	</display:column>
	<display:column property="invItem.name" escapeXml="true" sortable="true" titleKey="catalogItem.invItem.name" sortName="invItem.name" />
	<display:column property="qty" sortable="true" titleKey="catalogItem.qty" sortName="qty" format="{0,number,#,##0.##}" />
</display:table>
