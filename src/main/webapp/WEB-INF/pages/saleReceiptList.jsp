<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="saleReceiptList.title" /></title>
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
		<fmt:message key="saleReceiptList.heading" />
	</h2>

	<form method="get" action="${ctx}/saleReceiptList" id="searchForm" class="well form-horizontal">
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group">
				<label class="control-label" for="saleOrder.saleOrderNo"><fmt:message key="saleOrder.saleOrderNo" />:</label>
				<div class="controls">
					<input type="text" class="input-medium" name="saleOrder.saleOrderNo" id="saleOrder.saleOrderNo" value="${param['saleOrder.saleOrderNo']}" placeholder="" />
				</div>
			</div>
		</div>
		<div class="span6">
			<div class="control-group">
				<label class="control-label" for="saleOrder.customer.name"><fmt:message key="saleReceipt.saleOrder.customer.name" />:</label>
				<div class="controls">
					<input type="text" class="input-xlarge" name="saleOrder.customer.name" id="saleOrder.customer.name" value="${param['saleOrder.customer.name']}" placeholder="" />
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group">
				<label class="control-label" for="receiptDateFrom"><fmt:message key="saleReceipt.receiptDateFrom" />:</label>
				<div class="controls">
					<div class="input-append date" id="receiptDateFromDatepicker">
						<input type="text" class="input-medium" name="receiptDateFrom" value="<c:out value='${param.receiptDateFrom}'/>"><span class="add-on"><i class="icon-th"></i></span>
					</div>
				</div>
			</div>
		</div>
		<div class="span6">
			<div class="control-group">
				<label class="control-label" for="receiptDateTo"><fmt:message key="saleReceipt.receiptDateTo" />:</label>
				<div class="controls">
					<div class="input-append date" id="receiptDateToDatepicker">
						<input type="text" class="input-medium" name="receiptDateTo" value="<c:out value='${param.receiptDateTo}'/>"><span class="add-on"><i class="icon-th"></i></span>
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
	<form method="post" action="${ctx}/saleReceiptList" id="deleteForm">
		<input type="hidden" name="cancelReason"/>
		<c:if test="${not empty saleReceiptList }">
			<div class="control-group pull-right">
				<fmt:message key="label.showPagination" />
				&nbsp;
				<%=request.getAttribute("psSelect")%>
			</div>
		</c:if>
		<div id="actions">
			<a class="btn btn-primary" href="<c:url value='/saleReceipt?method=Add&from=list'/>"> <i class="icon-plus icon-white"></i> <fmt:message key="button.add" />
			</a>

			<button id="button.delete" class="btn" type="submit" onclick="return validateDelete()">
				<i class="icon-remove"></i>
				<fmt:message key="button.cancel" />
			</button>

			<a class="btn" href="<c:url value='/mainMenu'/>"> <i class="icon-ok"></i> <fmt:message key="button.done" /></a>

		</div>
		<display:table name="saleReceiptList" cellspacing="0" cellpadding="0" requestURI="" id="saleReceipt" pagesize="${ps}" class="table table-condensed table-striped table-hover table-bordered" export="true" size="resultSize" partialList="true" sort="external">
			<display:column title="<input type='checkbox' name='chkSelectAll' id='chkSelectAll'/>" headerClass="span1" class="span1">
				<input type="checkbox" id="checkbox" name="checkbox" value="<c:out value='${saleReceipt.id}'/>" />
			</display:column>
			<display:column property="receiptNo" url="/saleReceipt?from=list" paramId="id" paramProperty="id" escapeXml="true" sortable="true" titleKey="saleReceipt.receiptNo" sortName="receiptNo" />
			<display:column property="saleOrder.saleOrderNo" escapeXml="true" sortable="true" titleKey="saleReceipt.saleOrder.saleOrderNo" sortName="saleOrder.saleOrderNo" />
			<display:column property="saleOrder.customer.name" escapeXml="true" sortable="true" titleKey="saleReceipt.saleOrder.customer.name" sortName="saleOrder.customer.name" />
			<display:column property="receiptDate" escapeXml="false" sortable="true" titleKey="saleReceipt.receiptDate" sortName="receiptDate" format="{0, date, dd/MM/yyyy HH:mm}" />
			<display:column property="receiptAmount" escapeXml="false" sortable="true" titleKey="saleReceipt.receiptAmount" sortName="receiptAmount" format="{0, number, #,##0.##}" />
			<display:setProperty name="export.csv" value="true"></display:setProperty>
			<display:setProperty name="export.excel" value="true"></display:setProperty>
			<display:setProperty name="export.xml" value="false"></display:setProperty>
			<display:setProperty name="export.pdf" value="true"></display:setProperty>
			<display:setProperty name="export.excel.filename" value="SaleReceipt.xls" />
			<display:setProperty name="export.csv.filename" value="SaleReceipt.csv" />
			<display:setProperty name="export.pdf.filename" value="SaleReceipt.pdf" />
		</display:table>
	</form>
</div>


<div class="modal hide fade" id="cancelReasonDialog">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3><fmt:message key="saleReceipt.cancelReason"/></h3>
	</div>
	<div class="modal-body">
		<div class="control-group">
		<div class="controls">
			<textarea class="input-xlarge" name="cancelReasonArea" id="cancelReasonArea"></textarea>
		</div>
		</div>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn" data-dismiss="modal"><fmt:message key="button.close"/></a> <a class="btn btn-primary" onclick="cancel()"><fmt:message key="button.save"/></a>
	</div>
</div>



<script type="text/javascript">
	function validateDelete() {
		var form = document.forms['deleteForm'];
		if (!hasChecked(form.checkbox)) {
			alert('<fmt:message key="global.errorNoCheckboxSelectForDelete"/>');
			return false;
		}
		$('#cancelReasonDialog').show(function () {
			$(this).find('.control-group').removeClass('error');
    			$(this).find('.help-inline').remove();
		});
		$('#cancelReasonDialog').modal();
		return false;
	}
	
	function cancel() {
		var form = document.forms['deleteForm'];
		//since cancelReasonArea is not in form, using get(0) to convert to read object
		if (checkRequired($('#cancelReasonArea').get(0), '<tags:validateMessage errorKey="errors.required" field="saleReceipt.cancelReason"/>')) {
			form["cancelReason"].value = $('#cancelReasonArea').val();
			form.submit();
		}
	}
	
	<c:if test="${not empty saleReceiptList}">
	$(document).ready(function() {
		$("#chkSelectAll").click(function() {
			toggleCheckAll(this, document.forms['deleteForm'].checkbox);
		});
	});
	</c:if>

	$(document).ready(function() {
		$('input[name="saleOrder.customer.name"]').lookup({
			type : 'customer',
			displayProperty : function(json) {
				return json.name;
			},
			selectProperty : 'name',
			btnSearchCondition : function() {
				return {
					name : $('input[name="saleOrder.customer.name"]').val()
				};
			},
			handler : function(json) {
				if (json) {
				} else {
					$('input[name="saleOrder.customer.name"]').val('');
				}
			}
		});
	});

	$(function() {

		var st = $('#receiptDateFromDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
		var ed = $('#receiptDateToDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
		st.on('show', function(e) {
			var stObj = st.data('datetimepicker');
			var edObj = ed.data('datetimepicker');
			if ($('input[name="receiptDateTo"]').val() != '') {
				stObj.setEndDate(edObj.getDate());
			} else {
				stObj.setEndDate(null);
			}
		});
		st.on('changeDate', function(e) {
			var edObj = ed.data('datetimepicker');
			if (null != edObj.getDate()) {
				if (null != e.date && e.date.valueOf() > edObj.getDate().valueOf()) {
					edObj.setDate(null);
				}
			}
		});
		ed.on('show', function(e) {
			var stObj = st.data('datetimepicker');
			var edObj = ed.data('datetimepicker');
			if ($('input[name="receiptDateFrom"]').val() != '') {
				edObj.setStartDate(stObj.getDate());
			} else {
				edObj.setStartDate(null);
			}
		});
		ed.on('changeDate', function(e) {
			var stObj = st.data('datetimepicker');
			if (null != stObj.getDate()) {
				if (null != e.date && e.date.valueOf() < stObj.getDate().valueOf()) {
					stObj.setDate(null);
				}
			}
		});
	});
</script>

