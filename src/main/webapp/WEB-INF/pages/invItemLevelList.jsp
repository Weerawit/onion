<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="invItemLevelList.title" /></title>
<meta name="menu" content="InvMenu" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/scripts/datepicker/css/bootstrap-datetimepicker.min.css'/>" />
<script type="text/javascript" src="<c:url value='/scripts/jquery-lookup.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/datepicker/js/bootstrap-datetimepicker.min.js'/>"></script>
</head>

<c:if test="${not empty searchError}">
	<div class="alert alert-error fade in">
		<a href="#" data-dismiss="alert" class="close">&times;</a>
		<c:out value="${searchError}" />
	</div>
</c:if>

<div class="span12">
	<h2>
		<fmt:message key="invItemLevelList.heading" />
	</h2>

	<form method="get" action="${ctx}/invItemLevelList" id="searchForm" class="well form-horizontal">
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label" for="documentNumber.documentNo"><fmt:message key="invItemLevel.documentNumber.documentNo" />:</label>
					<div class="controls">
						<input type="text" class="input-xlarge" name="documentNumber.documentNo" id="documentNumber.documentNo" value="${param['documentNumber.documentNo']}" placeholder="" />
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group">
					<label class="control-label" for="code"><fmt:message key="invItemLevel.invItem.name" />:</label>
					<div class="controls">
						<input type="hidden" id="invItem.code" value="${param['invItem.code']}" /> 
						<input type="text" class="input-medium" name="invItem.name" id="invItem.name" value="${param['invItem.name']}" placeholder="" />
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group">
					<label class="control-label" for="transactionDateFrom"><fmt:message key="invItemLevel.transactionDateFrom" />:</label>
					<div class="controls">
						<div class="input-append date" id="transactionDateFromDatepicker">
							<input type="text" class="input-medium" name="transactionDateFrom" value="<c:out value='${param.transactionDateFrom}'/>"><span class="add-on"><i class="icon-th"></i></span>
						</div>
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="control-group">
					<label class="control-label" for="transactionDateTo"><fmt:message key="invItemLevel.transactionDateTo" />:</label>
					<div class="controls">
						<div class="input-append date" id="transactionDateToDatepicker">
							<input type="text" class="input-medium" name="transactionDateTo" value="<c:out value='${param.transactionDateTo}'/>"><span class="add-on"><i class="icon-th"></i></span>
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
	<c:if test="${not empty invItemLevelList }">
		<div class="control-group pull-right">
			<fmt:message key="label.showPagination" />
			&nbsp;
			<%=request.getAttribute("psSelect")%>
		</div>
	</c:if>
	<div id="actions">
		<a class="btn" href="<c:url value='/mainMenu'/>"> <i class="icon-ok"></i> <fmt:message key="button.done" /></a>
	</div>
	<display:table name="invItemLevelList" cellspacing="0" cellpadding="0" requestURI="" id="invItemLevel" pagesize="${ps}" class="table table-condensed table-striped table-hover table-bordered" export="true" size="resultSize" partialList="true" sort="external">
		<display:column property="invItem.code" escapeXml="true" sortable="true" titleKey="invItemLevel.invItem.code" sortName="invItem.code" />
		<display:column property="invItem.name" escapeXml="true" sortable="true" titleKey="invItemLevel.invItem.name" sortName="invItem.name" />
		<display:column escapeXml="true" sortable="true" titleKey="invItemLevel.transactionDate" sortName="transactionDate" defaultorder="descending">
			<fmt:formatDate value="${invItemLevel.transactionDate}" pattern="dd/MM/yyyy HH:mm:ss" />
		</display:column>
		<display:column property="qtyAdjust" sortable="true" titleKey="invItemLevel.qtyAdjust" sortName="qtyAdjust" format="{0,number,#,##0.##}" />
		<display:column property="qtyAvailableAdjust" sortable="true" titleKey="invItemLevel.qtyAvailableAdjust" sortName="qtyAvailableAdjust" format="{0,number,#,##0.##}" />
		<display:column  sortable="true" titleKey="invItemLevel.transactionType" sortName="transactionType">
			<tags:labelValue value="${invItemLevel.transactionType}" list="${itemStockTransactionTypeList}"/>
		</display:column>
		<display:column  sortable="true" titleKey="invItemLevel.refType" sortName="refType">
			<tags:labelValue value="${invItemLevel.refType}" list="${refTypeList}"/>
		</display:column>
		<display:column sortable="true" titleKey="invItemLevel.documentNumber.documentNo" sortName="documentNumber.documentNo" media="html">
			<c:choose>
				<c:when test="${invItemLevel.refType == 'SA' }">
					<a href='<c:url value="/saleOrder?saleOrderNo=${invItemLevel.documentNumber.documentNo }"/>'><c:out value="${invItemLevel.documentNumber.documentNo}"/></a>
				</c:when>
				<c:when test="${invItemLevel.refType == 'JB' }">
					<a href='<c:url value="/jobOrder?jobOrderNo=${invItemLevel.documentNumber.documentNo }"/>'><c:out value="${invItemLevel.documentNumber.documentNo}"/></a>
				</c:when>
				<c:when test="${invItemLevel.refType == 'GM' }">
					<a href='<c:url value="/invGoodsMovement?goodsMovementNo=${invItemLevel.documentNumber.documentNo }"/>'><c:out value="${invItemLevel.documentNumber.documentNo}"/></a>
				</c:when>
				<c:when test="${invItemLevel.refType == 'GR' }">
					<a href='<c:url value="/invGoodsReceipt?goodsReceiptNo=${invItemLevel.documentNumber.documentNo }"/>'><c:out value="${invItemLevel.documentNumber.documentNo}"/></a>
				</c:when>
				<c:otherwise>
					<c:out value="${invItemLevel.documentNumber.documentNo}"/>
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:setProperty name="export.excel.filename" value="invItemLevel.xls" />
		<display:setProperty name="export.csv.filename" value="invItemLevel.csv" />
		<display:setProperty name="export.pdf.filename" value="invItemLevel.pdf" />
	</display:table>

</div>

<script type="text/javascript">
$(document).ready(function () {
	$('input[name="invItem.name"]').lookup({
		type: 'item',
		displayProperty: function (json) {
			return json.code + ' : ' + json.name;
		},
		selectProperty: 'name',
		btnSearchCondition: function () {
			return {code: $('input[name="invItem.code"]').val()};	
		},
		handler: function (json) {
			if (json) {
				$('input[name="invItem.code"]').val(json.code);
			} else {
				$('input[name="invItem.code"]').val('');
				$('input[name="invItem.name"]').val('');
			}
		}
	});
});

	$(function() {

		var st = $('#transactionDateFromDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
		var ed = $('#transactionDateToDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
		st.on('show', function(e) {
			var stObj = st.data('datetimepicker');
			var edObj = ed.data('datetimepicker');
			if ($('input[name="transactionDateTo"]').val() != '') {
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
			if ($('input[name="transactionDateFrom"]').val() != '') {
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

