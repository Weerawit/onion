<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="invGoodsMovementList.title" /></title>
<meta name="menu" content="InvMenu" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/scripts/datepicker/css/bootstrap-datetimepicker.min.css'/>" />
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
		<fmt:message key="invGoodsMovementList.heading" />
	</h2>

	<form method="get" action="${ctx}/invGoodsMovementList" id="searchForm" class="well form-horizontal">
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group">
				<label for="invGoodsMovement.movementType" class="control-label"><fmt:message key="invGoodsMovement.movementType" /></label>
				<div class="controls">
					<select id=invGoodsMovement.movementType name="invGoodsMovement.movementType">
						<option value=""></option>
						<c:forEach items="${movementTypeList}" var="movementType">
							<option value="${movementType.value}" ${(movementType.value == param['movementType']) ? 'selected' : ''}>${movementType.label}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group">
				<label class="control-label" for="runningNo"><fmt:message key="invGoodsMovement.runningNo" />:</label>
				<div class="controls">
					<input type="text" class="input-xlarge" name="runningNo" id="runningNo" value="${param.runningNo}" placeholder="" />
				</div>
			</div>
		</div>		
		<div class="span6">	
			<div class="control-group">
				<label class="control-label" for="owner"><fmt:message key="invGoodsMovement.owner" />:</label>
				<div class="controls">
					<input type="text" class="input-xlarge" name="owner" id="owner" value="${param.owner}" placeholder="" />
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span6">	
			<div class="control-group">
				<label class="control-label" for="movementDateFrom"><fmt:message key="invGoodsMovement.movementDateFrom" />:</label>
				<div class="controls">
					<div class="input-append date" id="movementDateFromDatepicker">
						<input type="text" class="input-medium" name="movementDateFrom" value="<c:out value='${param.movementDateFrom}'/>"><span class="add-on"><i class="icon-th"></i></span>
					</div>
				</div>
			</div>
		</div>
		<div class="span6">	
			<div class="control-group">
				<label class="control-label" for="movementDateTo"><fmt:message key="invGoodsMovement.movementDateTo" />:</label>
				<div class="controls">
					<div class="input-append date" id="movementDateToDatepicker">
						<input type="text" class="input-medium" name="movementDateTo" value="<c:out value='${param.movementDateTo}'/>"><span class="add-on"><i class="icon-th"></i></span>
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
	<c:if test="${not empty invGoodsMovementList }">
	<div class="control-group pull-right">
		<fmt:message key="label.showPagination" />
		&nbsp;
		<%=request.getAttribute("psSelect")%>
	</div>
	</c:if>	
	<div id="actions">
		<a class="btn btn-primary" href="<c:url value='/invGoodsMovement?method=Add&from=list'/>"> <i class="icon-plus icon-white"></i> <fmt:message key="button.add" />
		</a>

		<a class="btn" href="<c:url value='/mainMenu'/>"> <i class="icon-ok"></i> <fmt:message key="button.done" /></a>
		
	</div>
	<display:table name="invGoodsMovementList" cellspacing="0" cellpadding="0" requestURI="" id="invGoodsMovement"  pagesize="${ps}" class="table table-condensed table-striped table-hover table-bordered" export="true" size="resultSize" partialList="true" sort="external">
		<display:column property="id" url="/invGoodsMovement?from=list" paramId="id" paramProperty="id" escapeXml="true" sortable="true" titleKey="invGoodsMovement.id" sortName="id" />
		<display:column property="runningNo" escapeXml="true" sortable="true" titleKey="invGoodsMovement.runningNo" sortName="runningNo" />
		<display:column escapeXml="true" sortable="true" titleKey="invGoodsMovement.movementDate" sortName="movementDate" defaultorder="descending">
			<fmt:formatDate value="${invGoodsMovement.movementDate}" pattern="dd/MM/yyyy HH:mm:ss" />
		</display:column>
		<display:column property="owner" escapeXml="true" sortable="true" titleKey="invGoodsMovement.owner" sortName="owner" />
		<display:setProperty name="export.csv" value="true"></display:setProperty>
		<display:setProperty name="export.excel" value="true"></display:setProperty>
		<display:setProperty name="export.xml" value="false"></display:setProperty>
		<display:setProperty name="export.pdf" value="true"></display:setProperty>
		<display:setProperty name="export.excel.filename" value="InvGoodsMovement.xls" />
		<display:setProperty name="export.csv.filename" value="InvGoodsMovement.csv" />
		<display:setProperty name="export.pdf.filename" value="InvGoodsMovement.pdf" />
	</display:table>
	
</div>

<script type="text/javascript">
	$(function() {

		var st = $('#movementDateFromDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
		var ed = $('#movementDateToDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
		st.on('show', function(e) {
			var stObj = st.data('datetimepicker');
			var edObj = ed.data('datetimepicker');
			if ($('input[name="movementDateTo"]').val() != '') {
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
			if ($('input[name="movementDateFrom"]').val() != '') {
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

