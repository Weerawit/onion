<%@ include file="/common/taglibs.jsp"%>
<display:table name="saleReceiptList" cellspacing="0" cellpadding="0" requestURI="" id="saleReceipt"  class="table table-condensed table-striped table-hover table-bordered" export="false" pagesize="10" partialList="false">
	<display:column property="receiptNo" escapeXml="true" sortable="true" titleKey="saleReceipt.receiptNo" sortName="receiptNo" />
	<display:column property="saleOrder.saleOrderNo" escapeXml="true" sortable="true" titleKey="saleReceipt.saleOrder.saleOrderNo" sortName="saleOrder.saleOrderNo" />
	<display:column property="receiptDate" escapeXml="false" sortable="true" titleKey="saleReceipt.receiptDate" sortName="receiptDate"  format="{0, date, dd/MM/yyyy HH:mm}"/>
	<display:column escapeXml="false" sortable="true" titleKey="saleReceipt.status" sortName="status" >
		<tags:labelValue value="${saleReceipt.status}" list="${saleReceiptStatusList}"/> 
	</display:column>
	<display:column property="receiptAmount" escapeXml="false" sortable="true" titleKey="saleReceipt.receiptAmount" sortName="receiptAmount"  format="{0, number, #,##0.##}"/>
	
	<display:footer>
		<tr>
			<td ><appfuse:label styleClass="control-label" key="saleOrder.totalPrice" /></td>
			<td ><fmt:formatNumber value="${saleOrder.totalPrice }" pattern="#,##0.##"/></td>
			<td colspan="2"><appfuse:label styleClass="control-label" key="saleOrder.paymentPaid" /></td>
			<td class="span2 offset10">
				<fmt:formatNumber value="${saleOrder.paymentPaid }" pattern="#,##0.##"/>
			</td>
		</tr>
	</display:footer>
</display:table>