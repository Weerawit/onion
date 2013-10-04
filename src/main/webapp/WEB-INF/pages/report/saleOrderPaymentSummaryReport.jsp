<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="saleOrderPaymentSummaryReport.title" /></title>
<meta name="menu" content="ReportMenu" />
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
		<fmt:message key="saleOrderPaymentSummaryReport.heading" />
	</h2>

	<form method="get" action="${ctx}/report/saleOrderPaymentSummaryReport" id="searchForm" class="well form-horizontal">
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group">
				<label class="control-label" for="documentNumber.documentNo"><fmt:message key="saleOrder.documentNumber.documentNo" />:</label>
				<div class="controls">
					<input type="text" class="input-medium" name="documentNumber.documentNo" id="documentNumber.documentNo" value="${param['documentNumber.documentNo']}" placeholder="" />
				</div>
			</div>
		</div>
		<div class="span6">
			<div class="control-group">
				<label class="control-label" for="customer.name"><fmt:message key="saleOrder.customer.name" />:</label>
				<div class="controls">
					<input type="text" class="input-xlarge" name="customer.name" id="customer.name" value="${param['customer.name']}" placeholder="" autocomplete="off" data-lookup-key-value="${param['customer.name']}"/>
				</div>
			</div>
		</div>
	</div>	
	<div class="row-fluid">
		<div class="span6">	
			<div class="control-group">
				<label class="control-label" for="deliveryDateFrom"><fmt:message key="saleOrder.deliveryDateFrom" />:</label>
				<div class="controls">
					<div class="input-append date" id="deliveryDateFromDatepicker">
						<input type="text" class="input-medium" name="deliveryDateFrom" value="<c:out value='${param.deliveryDateFrom}'/>"><span class="add-on"><i class="icon-th"></i></span>
					</div>
				</div>
			</div>
		</div>
		<div class="span6">	
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
	<c:if test="${not empty saleOrderPaymentSummaryReportList }">
	<div class="control-group pull-right">
		<fmt:message key="label.showPagination" />
		&nbsp;
		<%=request.getAttribute("psSelect")%>
	</div>
	</c:if>	
	<div id="actions">
		<a class="btn" href="<c:url value='/mainMenu'/>"> <i class="icon-ok"></i> <fmt:message key="button.done" /></a>
	</div>
	<jsp:useBean id="decorator" class="org.displaytag.decorator.TotalTableDecorator"/>
	<fmt:message key="saleOrderPaymentSummaryReport.subtotalLabel" var="subtotalLabel"/>
	<jsp:setProperty  property="subtotalLabel" name="decorator" value="${subtotalLabel}"/>
	<fmt:message key="saleOrderPaymentSummaryReport.totalLabel" var="totalLabel"/>
	<jsp:setProperty property="totalLabel" name="decorator" value="${totalLabel }"/>
	<display:table name="saleOrderPaymentSummaryReportList" cellspacing="0" cellpadding="0" requestURI="" id="saleOrder"  pagesize="${ps}" varTotals="totals" class="table table-condensed table-striped table-hover table-bordered" export="true" size="resultSize" partialList="true" sort="external">
		<display:column property="customerName" escapeXml="true" sortable="true" titleKey="saleOrder.customer.name" sortName="c.name" group="1"/>
		<display:column property="saleOrderNo" escapeXml="true" sortable="true" titleKey="saleOrder.documentNumber.documentNo" sortName="d.document_no" />
		<display:column property="nonPaidAmount" escapeXml="false" sortable="true" titleKey="saleOrderPaymentSummaryReport.nonPaidAmount" sortName="nonPaidAmount" format="{0,number,#,##0.00}" total="true"/>
		<display:setProperty name="export.excel.filename" value="SaleOrderPaymentSummaryReport.xls" />
		<display:setProperty name="export.csv.filename" value="SaleOrderPaymentSummaryReport.csv" />
		<display:setProperty name="export.pdf.filename" value="SaleOrderPaymentSummaryReport.pdf" />
		<display:setProperty name="decorator.media.html"  value="decorator" />
		
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

