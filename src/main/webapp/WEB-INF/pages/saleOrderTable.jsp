<%@ include file="/common/taglibs.jsp"%>
<form id="saleOrderItemForm" name="saleOrderItemForm">
<c:choose>
	<c:when test="${saleOrder.documentNumber.documentNo == null }">
		<display:table name="saleOrderItemList" cellspacing="0" cellpadding="0" excludedParams="*" requestURI="" id="saleOrderItem" class="table table-condensed table-striped table-hover table-bordered" export="false" pagesize="10" partialList="false">
			<display:column title="<input type='checkbox' name='chkSelectAll' id='chkSelectAll'/>" class="span1" style="width: 10px" media="html">
				<input type="checkbox" id="checkbox" name="checkbox" value="<c:out value='${saleOrderItem_rowNum - 1}'/>" />
			</display:column>
			<display:column titleKey="saleOrderItem.catalog.code" sortable="false" sortProperty="catalog.code" >
				<div class="media">
					<img id="image<c:out value='${saleOrderItem_rowNum - 1}'/>" src="<c:url value='/img/thumbnail/catalog/${saleOrderItem.catalog.id}?t=100'/>"  class="img-polaroid pull-left"/>
					<div class="media-body">
						<h4 class="media-heading">
							<input type="hidden" name="catalog.code" value="<c:out value='${saleOrderItem.catalog.code }'/>" /> <input type="text" name="catalog.name" value="<c:out value='${saleOrderItem.catalog.name }'/>" class="input-medium" autocomplete="off" />
						</h4>
						<c:if test="${saleOrderItem.catalog.code != null }">
							<fmt:message key="invItem.invStock.qtyAvailable"/> : <fmt:formatNumber value="${saleOrderItem.catalog.invItem.invStock.qtyAvailable }" pattern="#,##0.##" /> 
						</c:if>
					</div>
				</div>
			</display:column>
			<display:column sortable="false" titleKey="saleOrderItem.qty" sortProperty="qty" class="span1">
				<input type="text" name="qty" value="<c:out value='${saleOrderItem.qty }'/>" autocomplete="off" class="input-small"/>
			</display:column>
			<display:column sortable="false" titleKey="saleOrderItem.pricePerUnit" sortProperty="pricePerUnit" class="span1">
				<input type="text" name="pricePerUnit" value="<c:out value='${saleOrderItem.pricePerUnit }'/>" autocomplete="off" class="input-small"/>
			</display:column>
			<display:column sortable="false" titleKey="saleOrderItem.price" sortProperty="price" class="span1">
				<label id="price"><fmt:formatNumber value='${saleOrderItem.price }' pattern="#,##0.##" /></label>
			</display:column>
			<display:footer>
				<tr>
					<td colspan="4"><appfuse:label styleClass="control-label" key="saleOrder.totalPrice" /></td>
					<td class="span2 offset10"><fmt:formatNumber value="${saleOrder.totalPrice }" pattern="#,##0.##" /></td>
				</tr>
			</display:footer>
		</display:table>
	</c:when>
	<c:otherwise>
		<display:table name="saleOrderItemList" cellspacing="0" cellpadding="0" excludedParams="*" requestURI="" id="saleOrderItem" class="table table-condensed table-striped table-hover table-bordered" export="false" pagesize="10" partialList="false">
			<display:column titleKey="saleOrderItem.catalog.code" sortable="true" sortProperty="catalog.code" >
				<div class="media">
					<img id="image<c:out value='${saleOrderItem_rowNum - 1}'/>" src="<c:url value='/img/thumbnail/catalog/${saleOrderItem.catalog.id}?t=100'/>"  class="img-polaroid pull-left"/>
					<div class="media-body">
						<h4 class="media-heading">
							<span class="input-medium uneditable-input"><c:out value="${saleOrderItem.catalog.name}" /></span>
						</h4>
					</div>
				</div>
			</display:column>
			<display:column sortable="true" titleKey="saleOrderItem.qty" sortProperty="qty" class="span1">
				<span class="input-small uneditable-input"><fmt:formatNumber value="${saleOrderItem.qty }"  pattern="#,##0.##"/></span>
			</display:column>
			<display:column sortable="true" titleKey="saleOrderItem.pricePerUnit" sortProperty="pricePerUnit" class="span1">
				<span class="input-small uneditable-input"><fmt:formatNumber value="${saleOrderItem.pricePerUnit }"  pattern="#,##0.##"/></span>
			</display:column>
			<display:column sortable="true" titleKey="saleOrderItem.price" sortProperty="price" class="span1">
				<label id="price"><fmt:formatNumber value='${saleOrderItem.price }' pattern="#,##0.##" /></label>
			</display:column>
			<display:footer>
				<tr>
					<td colspan="3"><appfuse:label styleClass="control-label" key="saleOrder.totalPrice" /></td>
					<td class="span2 offset10"><fmt:formatNumber value="${saleOrder.totalPrice }" pattern="#,##0.##" /></td>
				</tr>
			</display:footer>
		</display:table>
	</c:otherwise>
</c:choose>
	
</form>
