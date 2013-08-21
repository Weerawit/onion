<%@ include file="/common/taglibs.jsp"%>
<form id="saleOrderItemForm" name="saleOrderItemForm">
<display:table name="saleOrderItemList" cellspacing="0" cellpadding="0" requestURI="" id="saleOrderItem" class="table table-condensed table-striped table-hover table-bordered" export="false" pagesize="10" partialList="false">
	<display:column title="<input type='checkbox' name='chkSelectAll' id='chkSelectAll'/>" headerClass="span1" class="span1">
		<input type="checkbox" id="checkbox" name="checkbox" value="<c:out value='${saleOrderItem_rowNum - 1}'/>" />
	</display:column>
	<display:column titleKey="saleOrderItem.catalog.code" sortable="true" sortProperty="catalog.code" class="span5">
			<span class="span2">
				<img id="image<c:out value='${saleOrderItem_rowNum - 1}'/>" src="<c:url value='/img/thumbnail/catalog/${saleOrderItem.catalog.id}?t=100'/>" class="img-polaroid"/>
			</span>
			<span class="span10">
				<input type="hidden" name="catalog.code" value="<c:out value='${saleOrderItem.catalog.code }'/>"/>
				<input type="text" name="catalog.name" value="<c:out value='${saleOrderItem.catalog.name }'/>" class="span12" autocomplete="off"/>
			</span>
	</display:column>
	<display:column sortable="true" titleKey="saleOrderItem.qty" sortProperty="qty" class="span2">
		<input type="text" name="qty" value="<c:out value='${saleOrderItem.qty }'/>" class="span12" autocomplete="off"/>
	</display:column>
	<display:column sortable="true" titleKey="saleOrderItem.pricePerUnit" sortProperty="pricePerUnit" class="span2">
		<input type="text" name="pricePerUnit" value="<c:out value='${saleOrderItem.pricePerUnit }'/>" class="span12" autocomplete="off"/>
	</display:column>
	<display:column sortable="true" titleKey="saleOrderItem.price" sortProperty="price" class="span2">
		<label id="price"><fmt:formatNumber value='${saleOrderItem.price }' pattern="#,##0.##"/></label>
	</display:column>
	<display:footer>
		<tr>
			<td colspan="4"><appfuse:label styleClass="control-label" key="saleOrder.totalPrice" /></td>
			<td class="span2 offset10">
				<fmt:formatNumber value="${saleOrder.totalPrice }" pattern="#,##0.##"/>
			</td>
		</tr>
	</display:footer>
</display:table>
</form>
