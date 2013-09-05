<%@page import="com.google.gson.JsonElement"%>
<%@page import="com.worldbestsoft.model.SaleOrder"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="com.google.gson.Gson"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="saleOrderList.title" /></title>
<meta name="decorator" content="popup"/>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/scripts/datepicker/css/bootstrap-datetimepicker.min.css'/>" />
<script type="text/javascript" src="<c:url value='/scripts/datepicker/js/bootstrap-datetimepicker.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery-lookup.js'/>"></script>
</head>

<c:if test="${not empty searchError}">
	<div class="alert alert-error fade in">
		<a href="#" data-dismiss="alert" class="close">&times;</a>
		<c:out value="${searchError}" />
	</div>
</c:if>

<div class="span12">
	<h2>
		<fmt:message key="saleOrderList.heading" />
	</h2>

	<form method="get" action="${ctx}/popup/saleOrder" id="searchForm" class="well form-horizontal">
		<div class="row-fluid">
			<div class="span5">
				<div class="control-group">
					<label class="control-label" for="saleOrderNo"><fmt:message key="saleOrder.saleOrderNo" />:</label>
					<div class="controls">
						<input type="text" class="input-medium" name="saleOrderNo" id="saleOrderNo" value="${param.saleOrderNo}" placeholder="" />
					</div>
				</div>
			</div>
			<div class="span5">
				<div class="control-group">
					<label class="control-label" for="customer.name"><fmt:message key="saleOrder.customer.name" />:</label>
					<div class="controls">
						<input type="text" class="input-xlarge" name="customer.name" id="customer.name" value="${param['customer.name']}" placeholder="" />
					</div>
				</div>
			</div>
		</div>	
		<div class="row-fluid">
			<div class="span5">
				<div class="control-group">
					<label class="control-label" for="deliveryDateFrom"><fmt:message key="saleOrder.deliveryDateFrom" />:</label>
					<div class="controls">
						<div class="input-append date" id="deliveryDateFromDatepicker">
							<input type="text" class="input-medium" name="deliveryDateFrom" value="<c:out value='${param.deliveryDateFrom}'/>"><span class="add-on"><i class="icon-th"></i></span>
						</div>
					</div>
				</div>
			</div>
			<div class="span5">
				<div class="control-group">
					<label class="control-label" for="deliveryDateTo"><fmt:message key="saleOrder.deliveryDateTo" />:</label>
					<div class="controls">
						<div class="input-append date" id="deliveryDateToDatepicker">
							<input type="text" class="input-medium" name="deliveryDateTo" value="<c:out value='${param.deliveryDateTo}'/>"><span class="add-on"><i class="icon-th"></i></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span5">
				<div class="control-group">
						<label for="saleOrder.status" class="control-label"><fmt:message key="saleOrder.status" /></label>
						<div class="controls">
							<select id=status name="status">
								<option value=""></option>
								<c:forEach items="${saleOrderStatusList}" var="saleOrderStatus">
									<option value="${saleOrderStatus.value}" ${(saleOrderStatus.value == param['status']) ? 'selected' : ''}>${saleOrderStatus.label}</option>
								</c:forEach>
							</select>
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
	<c:if test="${not empty saleOrderList }">
	<div class="control-group pull-right">
		<fmt:message key="label.showPagination" />
		&nbsp;
		<%=request.getAttribute("psSelect")%>
	</div>
	</c:if>	
	<display:table name="saleOrderList" cellspacing="0" cellpadding="0" requestURI="" id="saleOrder"  pagesize="${ps}" class="table table-condensed table-striped table-hover table-bordered" export="false" size="resultSize" partialList="true" sort="external">
		<display:column title="" headerClass="span1" class="span1">
			<%
				SaleOrder saleOrderObj = (SaleOrder) saleOrder;
				Gson gson = new GsonBuilder().create();
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("id", saleOrderObj.getId().toString());
				jsonObject.addProperty("saleOrderNo", saleOrderObj.getSaleOrderNo());
				jsonObject.addProperty("totalPrice", saleOrderObj.getTotalPrice().toString());
				jsonObject.addProperty("paymentPaid", saleOrderObj.getPaymentPaid().toString());
				jsonObject.addProperty("status", saleOrderObj.getStatus());
				jsonObject.addProperty("cancelReason", saleOrderObj.getCancelReason());
				jsonObject.addProperty("paymentStatus", saleOrderObj.getPaymentStatus());
				jsonObject.addProperty("deliveryDate", saleOrderObj.getDeliveryDate().toString());
				
				JsonObject customerElement = new JsonObject();
				customerElement.getAsJsonObject().addProperty("id", saleOrderObj.getCustomer().getId().toString());
				customerElement.getAsJsonObject().addProperty("name", saleOrderObj.getCustomer().getName());
				customerElement.getAsJsonObject().addProperty("customerType", saleOrderObj.getCustomer().getCustomerType());
				customerElement.getAsJsonObject().addProperty("shipingAddress", saleOrderObj.getCustomer().getShipingAddress());
				customerElement.getAsJsonObject().addProperty("billingAddress", saleOrderObj.getCustomer().getBillingAddress());
				customerElement.getAsJsonObject().addProperty("contactName", saleOrderObj.getCustomer().getContactName());
				customerElement.getAsJsonObject().addProperty("contactTel", saleOrderObj.getCustomer().getContactTel());
				customerElement.getAsJsonObject().addProperty("memo", saleOrderObj.getCustomer().getMemo());
				jsonObject.add("customer", customerElement);
				String json = gson.toJson(jsonObject);
			%>
			<input type="radio" id="radio" name="radio" value='<%=json%>' />
		</display:column>
		<display:column property="id" escapeXml="true" sortable="true" titleKey="saleOrder.id" sortName="id" />
		<display:column property="saleOrderNo" url="/saleOrder?from=list" paramId="id" paramProperty="id" escapeXml="true" sortable="true" titleKey="saleOrder.saleOrderNo" sortName="saleOrderNo" />
		<display:column property="customer.name" escapeXml="true" sortable="true" titleKey="saleOrder.customer.name" sortName="customer.name" />
		<display:column property="totalPrice" escapeXml="true" sortable="true" titleKey="saleOrder.totalPrice" sortName="totalPrice" />
	</display:table>
</div>


<script type="text/javascript">
	$(document).ready(function () {
		$('input[name="customer.name"]').lookup({
			type: 'customer',
			displayProperty: function (json) {
				return json.name;
			},
			selectProperty: 'name',
			btnSearchCondition: function () {
				return {name: $('input[name="customer.name"]').val()};	
			},
			handler: function (json) {
				if (json) {
					//$('input[name="invItem.code"]').val(json.code);
				} else {
					//$('input[name="invItem.code"]').val('');
					$('input[name="customer.name"]').val('');
				}
			}
		});
	});
	
	$(function() {

		var st = $('#deliveryDateFromDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
		var ed = $('#deliveryDateToDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
		st.on('show', function(e) {
			var stObj = st.data('datetimepicker');
			var edObj = ed.data('datetimepicker');
			if ($('input[name="deliveryDateTo"]').val() != '') {
				stObj.setEndDate(edObj.getDate());
			} else {
				stObj.setEndDate(null);
			}
		});
		st.on('changeDate', function(e) {
			var edObj = ed.data('datetimepicker');
			if (null != edObj.getDate()) {
				if (null != e.date
						&& e.date.valueOf() > edObj.getDate().valueOf()) {
					edObj.setDate(null);
				}
			}
		});
		ed.on('show', function(e) {
			var stObj = st.data('datetimepicker');
			var edObj = ed.data('datetimepicker');
			if ($('input[name="deliveryDateFrom"]').val() != '') {
				edObj.setStartDate(stObj.getDate());
			} else {
				edObj.setStartDate(null);
			}
		});
		ed.on('changeDate', function(e) {
			var stObj = st.data('datetimepicker');
			if (null != stObj.getDate()) {
				if (null != e.date
						&& e.date.valueOf() < stObj.getDate().valueOf()) {
					stObj.setDate(null);
				}
			}
		});
	});
</script>

