<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="saleOrderList.title" /></title>
<meta name="menu" content="SaleOrderMenu" />
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

	<form method="get" action="${ctx}/saleOrderList" id="searchForm" class="well form-horizontal">
		<div class="control-group">
			<label class="control-label" for="saleOrderNo"><fmt:message key="saleOrder.saleOrderNo" />:</label>
			<div class="controls">
				<input type="text" class="input-medium" name="saleOrderNo" id="saleOrderNo" value="${param.saleOrderNo}" placeholder="" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="customer.name"><fmt:message key="saleOrder.customer.name" />:</label>
			<div class="controls">
				<input type="text" class="input-xlarge" name="customer.name" id="customer.name" value="${param['customer.name']}" placeholder="" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="deliveryDateFrom"><fmt:message key="saleOrder.deliveryDateFrom" />:</label>
			<div class="controls">
				<div class="input-append date" id="deliveryDateFromDatepicker">
					<input type="text" class="input-medium" name="deliveryDateFrom" value="<c:out value='${param.deliveryDateFrom}'/>"><span class="add-on"><i class="icon-th"></i></span>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="deliveryDateTo"><fmt:message key="saleOrder.deliveryDateTo" />:</label>
			<div class="controls">
				<div class="input-append date" id="deliveryDateToDatepicker">
					<input type="text" class="input-medium" name="deliveryDateTo" value="<c:out value='${param.deliveryDateTo}'/>"><span class="add-on"><i class="icon-th"></i></span>
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
	<form method="post" action="${ctx}/saleOrderList" id="deleteForm" onSubmit="return validateDelete(this.checkbox)">
	<c:if test="${not empty saleOrderList }">
	<div class="control-group pull-right">
		<fmt:message key="label.showPagination" />
		&nbsp;
		<%=request.getAttribute("psSelect")%>
	</div>
	</c:if>	
	<div id="actions">
		<a class="btn btn-primary" href="<c:url value='/saleOrder?method=Add&from=list'/>"> <i class="icon-plus icon-white"></i> <fmt:message key="button.add" />
		</a>

		<button id="button.delete" class="btn" type="submit">
			<i class="icon-trash"></i>
			<fmt:message key="button.delete" />
		</button>

		<a class="btn" href="<c:url value='/mainMenu'/>"> <i class="icon-ok"></i> <fmt:message key="button.done" /></a>
		
	</div>
	<display:table name="saleOrderList" cellspacing="0" cellpadding="0" requestURI="" id="saleOrder"  pagesize="${ps}" class="table table-condensed table-striped table-hover table-bordered" export="true" size="resultSize" partialList="true" sort="external">
		<display:column title="<input type='checkbox' name='chkSelectAll' id='chkSelectAll'/>" headerClass="span1" class="span1">
			<input type="checkbox" id="checkbox" name="checkbox" value="<c:out value='${saleOrder.id}'/>" />
		</display:column>
		<display:column property="id" url="/saleOrder?from=list" paramId="id" paramProperty="id" escapeXml="true" sortable="true" titleKey="saleOrder.id" sortName="id" />
		<display:column property="saleOrderNo" escapeXml="true" sortable="true" titleKey="saleOrder.saleOrderNo" sortName="saleOrderNo" />
		<display:column property="customer.name" escapeXml="true" sortable="true" titleKey="saleOrder.customer.name" sortName="customer.name" />
		<display:column property="totalPrice" escapeXml="false" sortable="true" titleKey="saleOrder.totalPrice" sortName="totalPrice" format="{0,number,#,##0.00}"/>
		
		<display:column escapeXml="false" sortable="true" titleKey="saleOrder.paymentPaid" sortName="paymentPaid">
			<c:choose>
				<c:when test="${saleOrder.paymentStatus != '3' }">
					<a class="btn btn-small btn-primary" href="<c:url value='/saleReceipt?method=Add&from=list&saleOrderNo=${saleOrder.saleOrderNo}'/>"><fmt:formatNumber value="${saleOrder.totalPrice - saleOrder.paymentPaid }" pattern="#,##0.00"/></a>
				</c:when>
				<c:otherwise>
					<fmt:formatNumber value="${saleOrder.paymentPaid }" pattern="#,##0.00"/>
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:setProperty name="export.csv" value="true"></display:setProperty>
		<display:setProperty name="export.excel" value="true"></display:setProperty>
		<display:setProperty name="export.xml" value="false"></display:setProperty>
		<display:setProperty name="export.pdf" value="true"></display:setProperty>
		<display:setProperty name="export.excel.filename" value="SaleOrder.xls" />
		<display:setProperty name="export.csv.filename" value="SaleOrder.csv" />
		<display:setProperty name="export.pdf.filename" value="SaleOrder.pdf" />
	</display:table>
	
	</form>
</div>


<script type="text/javascript">
	function validateDelete(checkbox) {

		if (!hasChecked(checkbox)) {
			alert('<fmt:message key="global.errorNoCheckboxSelectForDelete"/>');
			return false;
		}
		if (confirmMessage('<fmt:message key="global.confirm.delete"/>')) {
			return true;
		} else {
			return false;
		}
	}

	<c:if test="${not empty saleOrderList}">
	$(document).ready(function() {
		$("#chkSelectAll").click(function() {
			toggleCheckAll(this, document.forms['deleteForm'].checkbox);
		});
	});
	</c:if>
	
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

