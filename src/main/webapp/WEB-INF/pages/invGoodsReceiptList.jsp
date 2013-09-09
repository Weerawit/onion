<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="invGoodsReceiptList.title" /></title>
<meta name="menu" content="InvMenu" />
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
		<fmt:message key="invGoodsReceiptList.heading" />
	</h2>

	<form method="get" action="${ctx}/invGoodsReceiptList" id="searchForm" class="well form-horizontal">
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group">
				<label for="invGoodsReceipt.receiptType" class="control-label"><fmt:message key="invGoodsReceipt.receiptType" /></label>
				<div class="controls">
					<select id="receiptType" name="receiptType">
						<option value=""></option>
						<c:forEach items="${goodsReceiptTypeList}" var="goodsReceiptType">
							<option value="${goodsReceiptType.value}" ${(goodsReceiptType.value == param['receiptType']) ? 'selected' : ''}>${goodsReceiptType.label}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group">
				<label for="invGoodsReceipt.supplier.code" class="control-label"><fmt:message key="invGoodsReceipt.supplier.code" /></label>
				<div class="controls">
					<input type="hidden" name="supplier.code" id="supplier.code"/>
					<input type="text" name="supplier.name" id="supplier.name" class="input-medium" maxlength="20" autocomplete="off"  data-lookup-key-value="${param['supplier.name']}" value="${param['supplier.name']}"/>
				</div>
			</div>
		</div>
		<div class="span6">	
			<div class="control-group">
				<label class="control-label" for="documentNumber.documentNo"><fmt:message key="invGoodsReceipt.documentNumber.documentNo" />:</label>
				<div class="controls">
					<input type="text" class="input-xlarge" name="documentNumber.documentNo" id="documentNumber.documentNo" value="${param['documentNumber.documentNo']}" placeholder="" />
				</div>
			</div>
		</div>
	</div>	
	<div class="row-fluid">
		<div class="span6">	
			<div class="control-group">
				<label class="control-label" for="receiptDateFrom"><fmt:message key="invGoodsReceipt.receiptDateFrom" />:</label>
				<div class="controls">
					<div class="input-append date" id="receiptDateFromDatepicker">
						<input type="text" class="input-medium" name="receiptDateFrom" value="<c:out value='${param.receiptDateFrom}'/>"><span class="add-on"><i class="icon-th"></i></span>
					</div>
				</div>
			</div>
		</div>
		<div class="span6">
			<div class="control-group">
				<label class="control-label" for="receiptDateTo"><fmt:message key="invGoodsReceipt.receiptDateTo" />:</label>
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
	<c:if test="${not empty invGoodsReceiptList }">
	<div class="control-group pull-right">
		<fmt:message key="label.showPagination" />
		&nbsp;
		<%=request.getAttribute("psSelect")%>
	</div>
	</c:if>	
	<div id="actions">
		<a class="btn btn-primary" href="<c:url value='/invGoodsReceipt?method=Add&from=list'/>"> <i class="icon-plus icon-white"></i> <fmt:message key="button.add" />
		</a>

		<a class="btn" href="<c:url value='/mainMenu'/>"> <i class="icon-ok"></i> <fmt:message key="button.done" /></a>
		
	</div>
	<display:table name="invGoodsReceiptList" cellspacing="0" cellpadding="0" requestURI="" id="invGoodsReceipt"  pagesize="${ps}" class="table table-condensed table-striped table-hover table-bordered" export="true" size="resultSize" partialList="true" sort="external">
		<display:column property="id" url="/invGoodsReceipt?from=list" paramId="id" paramProperty="id" escapeXml="true" sortable="true" titleKey="invGoodsReceipt.id" sortName="id" />
		<display:column property="documentNumber.documentNo" escapeXml="true" sortable="true" titleKey="invGoodsReceipt.documentNumber.documentNo" sortName="documentNumber.documentNo" />
		<display:column escapeXml="true" sortable="true" titleKey="invGoodsReceipt.receiptType" sortName="receiptType" >
			<tags:labelValue value="${invGoodsReceipt.receiptType}" list="${goodsReceiptTypeList}"></tags:labelValue>
		</display:column>
		<display:column escapeXml="true" sortable="true" titleKey="invGoodsReceipt.receiptDate" sortName="receiptDate" defaultorder="descending">
			<fmt:formatDate value="${invGoodsReceipt.receiptDate}" pattern="dd/MM/yyyy HH:mm:ss" />
		</display:column>
		<display:column property="supplier.name" escapeXml="true" sortable="true" titleKey="invGoodsReceipt.supplier.name" sortName="supplier.name" />
		<display:column property="totalCost" sortable="true" titleKey="invGoodsReceipt.totalCost" sortName="totalCost" format="{0,number,#,##0.##}"/>
		<display:setProperty name="export.csv" value="true"></display:setProperty>
		<display:setProperty name="export.excel" value="true"></display:setProperty>
		<display:setProperty name="export.xml" value="false"></display:setProperty>
		<display:setProperty name="export.pdf" value="true"></display:setProperty>
		<display:setProperty name="export.excel.filename" value="InvGoodsReceipt.xls" />
		<display:setProperty name="export.csv.filename" value="InvGoodsReceipt.csv" />
		<display:setProperty name="export.pdf.filename" value="InvGoodsReceipt.pdf" />
	</display:table>
	
</div>

<script type="text/javascript">
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
				if (null != e.date
						&& e.date.valueOf() > edObj.getDate().valueOf()) {
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
				if (null != e.date
						&& e.date.valueOf() < stObj.getDate().valueOf()) {
					stObj.setDate(null);
				}
			}
		});
	});
	
	$(document).ready(function () {
		$('input[name="supplier.name"]').lookup({
			type: 'supplier',
			displayProperty: function (json) {
				return json.code + ' : ' + json.name;
			},
			selectProperty: 'name',
			btnSearchCondition: function () {
				return {code: $('input[name="supplier.code"]').val()};	
			},
			handler: function (json) {
				if (json) {
					$('input[name="supplier.code"]').val(json.code);
				} else {
					$('input[name="supplier.code"]').val('');
					$('input[name="supplier.name"]').val('');
				}
			}
		});
	});
</script>

