<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="jobOrderList.title" /></title>
<meta name="menu" content="JobOrderMenu" />
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
		<fmt:message key="jobOrderList.heading" />
	</h2>

	<form method="get" action="${ctx}/jobOrderList" id="searchForm" class="well form-horizontal">
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group">
				<label class="control-label" for="employee.name"><fmt:message key="jobOrder.employee.name" />:</label>
				<div class="controls">
					<input type="hidden" name="employee.id" id="employee.id" value="${param['employee.id']}" placeholder="" />
					<input type="text" class="input-xlarge" name="employee.name" id="employee.name" value="${param['employee.name']}" placeholder="" autocomplete="off" data-lookup-key-value="${param['employee.name']}"/>
				</div>
			</div>
		</div>
		<div class="span6">
			<div class="control-group">
				<appfuse:label styleClass="control-label" key="jobOrder.saleOrder.documentNumber.documentNo" />
				<div class="controls">
					<input type="text" class="input-xlarge" name="saleOrder.documentNumber.documentNo" id="saleOrder.documentNumber.documentNo" value="${param['saleOrder.documentNumber.documentNo']}" placeholder="" autocomplete="off" data-lookup-key-value="${param['saleOrder.documentNumber.documentNo']}"/>
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group">
				<label class="control-label" for="catalog.code"><fmt:message key="jobOrder.catalog.code" />:</label>
				<div class="controls">
					<input type="hidden" name="catalog.code" id="catalog.code" value="${param['catalog.code']}" placeholder="" />
					<input type="text" class="input-xlarge" name="catalog.name" id="catalog.name" value="${param['catalog.name']}" placeholder="" autocomplete="off"/>
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group">
				<label class="control-label" for="startDateFrom"><fmt:message key="jobOrder.startDateFrom" />:</label>
				<div class="controls">
					<div class="input-append date" id="startDateFromDatepicker">
						<input type="text" class="input-medium" name="startDateFrom" value="<c:out value='${param.startDateFrom}'/>"><span class="add-on"><i class="icon-th"></i></span>
					</div>
				</div>
			</div>
		</div>
		<div class="span6">
			<div class="control-group">
				<label class="control-label" for="startDateTo"><fmt:message key="jobOrder.startDateTo" />:</label>
				<div class="controls">
					<div class="input-append date" id="startDateToDatepicker">
						<input type="text" class="input-medium" name="startDateTo" value="<c:out value='${param.startDateTo}'/>"><span class="add-on"><i class="icon-th"></i></span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group">
				<label class="control-label" for="targetEndDateFrom"><fmt:message key="jobOrder.targetEndDateFrom" />:</label>
				<div class="controls">
					<div class="input-append date" id="targetEndDateFromDatepicker">
						<input type="text" class="input-medium" name="targetEndDateFrom" value="<c:out value='${param.targetEndDateFrom}'/>"><span class="add-on"><i class="icon-th"></i></span>
					</div>
				</div>
			</div>
		</div>
		<div class="span6">
			<div class="control-group">
				<label class="control-label" for="targetEndDateTo"><fmt:message key="jobOrder.targetEndDateTo" />:</label>
				<div class="controls">
					<div class="input-append date" id="targetEndDateToDatepicker">
						<input type="text" class="input-medium" name="targetEndDateTo" value="<c:out value='${param.targetEndDateTo}'/>"><span class="add-on"><i class="icon-th"></i></span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group">
				<label for="status" class="control-label"><fmt:message key="jobOrder.status" /></label>
				<div class="controls">
					<select id="status" name="status">
						<option value=""></option>
						<c:forEach items="${jobOrderStatusList}" var="jobOrderStatus">
							<option value="${jobOrderStatus.value}" ${(jobOrderStatus.value == param['status']) ? 'selected' : ''}>${jobOrderStatus.label}</option>
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
	<form method="post" action="${ctx}/jobOrderList" id="deleteForm">
	<input type="hidden" name="cancelReason"/>
	<c:if test="${not empty jobOrderList }">
	<div class="control-group pull-right">
		<fmt:message key="label.showPagination" />
		&nbsp;
		<%=request.getAttribute("psSelect")%>
	</div>
	</c:if>	
	<div id="actions">
		<a class="btn btn-primary" href="<c:url value='/jobOrder?method=Add&from=list'/>"> <i class="icon-plus icon-white"></i> <fmt:message key="button.add" />
		</a>
		
		<security:authorize ifAnyGranted="ROLE_MANAGER">
		<button id="button.delete" class="btn" type="submit"  onclick="return validateDelete()">
			<i class="icon-trash"></i>
			<fmt:message key="button.delete" />
		</button>
		</security:authorize>

		<a class="btn" href="<c:url value='/mainMenu'/>"> <i class="icon-ok"></i> <fmt:message key="button.done" /></a>
		
	</div>
	<display:table name="jobOrderList" cellspacing="0" cellpadding="0" requestURI="" id="jobOrder"  pagesize="${ps}" class="table table-condensed table-striped table-hover table-bordered" export="true" size="resultSize" partialList="true" sort="external">
		<security:authorize ifAnyGranted="ROLE_MANAGER">
		<display:column title="<input type='checkbox' name='chkSelectAll' id='chkSelectAll'/>" class="span1" style="width: 10px" media="html">
			<input type="checkbox" id="checkbox" name="checkbox" value="<c:out value='${jobOrder.id}'/>" />
		</display:column>
		</security:authorize>
		<display:column property="documentNumber.documentNo" url="/jobOrder?from=list" paramId="id" paramProperty="id" escapeXml="true" sortable="true" titleKey="jobOrder.documentNumber.documentNo" sortName="documentNumber.documentNo" />
		<display:column property="catalog.code" escapeXml="true" sortable="true" titleKey="jobOrder.catalog.code" sortName="catalog.code" />
		<display:column escapeXml="true" sortable="true" titleKey="jobOrder.employee.name" sortName="employee.firstName" >
			<c:out value="${jobOrder.employee.firstName} ${jobOrder.employee.lastName }"/>
		</display:column>
		<display:column property="startDate" escapeXml="false" sortable="true" titleKey="jobOrder.startDate" sortName="startDate" format="{0, date, dd/MM/yyyy}" />
		<display:column property="targetEndDate" escapeXml="false" sortable="true" titleKey="jobOrder.targetEndDate" sortName="targetEndDate" format="{0, date, dd/MM/yyyy}" />
		<display:column escapeXml="false" sortable="true" titleKey="jobOrder.status" sortName="status" >
			<tags:labelValue value="${jobOrder.status}" list="${jobOrderStatusList}"/> 
		</display:column>
		<display:setProperty name="export.excel.filename" value="JobOrder.xls" />
		<display:setProperty name="export.csv.filename" value="JobOrder.csv" />
		<display:setProperty name="export.pdf.filename" value="JobOrder.pdf" />
	</display:table>
	
	</form>
</div>

<div class="modal hide fade" id="cancelReasonDialog">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3><fmt:message key="jobOrder.cancelReason"/></h3>
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
<security:authorize ifAnyGranted="ROLE_MANAGER">
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
</security:authorize>	
	$(document).ready(function () {
		$('input[name="employee.name"]').lookup({
			type: 'employee',
			displayProperty: function (json) {
				return json.firstName + ' ' + json.lastName;
			},
			selectProperty: function (json) {
				return json.firstName + ' ' + json.lastName;
			},
			btnSearchCondition: function () {
				return {id: $('input[name="employee.id"]').val()};	
			},
			handler: function (json) {
				if (json) {
					$('input[name="employee.id"]').val(json.id);
				} else {
					$('input[name="employee.name"]').val('');
					$('input[name="employee.id"]').val('');
				}
			}
		});
		$('input[name="saleOrder.documentNumber.documentNo"]').lookup({
			type : 'saleOrder',
			displayProperty : function(json) {
				return json.saleOrderNo;
			},
			selectProperty : function(json) {
				return json.saleOrderNo;
			},
			btnSearchCondition : function() {
				return {
					'documentNumber.documentNo' : $('input[name="saleOrder.documentNumber.documentNo"]').val()
				};
			},
			handler : function(json) {
				if (json) {
					
				} else {
					$('input[name="saleOrder.documentNumber.documentNo"]').val('');
				}
			}
		});
	});

	<c:if test="${not empty jobOrderList}">
	$(document).ready(function() {
		$("#chkSelectAll").click(function() {
			toggleCheckAll(this, document.forms['deleteForm'].checkbox);
		});
	});
	</c:if>
	
	$(function() {

		var st = $('#startDateFromDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
		var ed = $('#startDateToDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
		st.on('show', function(e) {
			var stObj = st.data('datetimepicker');
			var edObj = ed.data('datetimepicker');
			if ($('input[name="startDateTo"]').val() != '') {
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
			if ($('input[name="startDateFrom"]').val() != '') {
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
	
	$(function() {

		var st = $('#targetEndDateFromDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
		var ed = $('#targetEndDateToDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
		st.on('show', function(e) {
			var stObj = st.data('datetimepicker');
			var edObj = ed.data('datetimepicker');
			if ($('input[name="targetEndDateTo"]').val() != '') {
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
			if ($('input[name="targetEndDateFrom"]').val() != '') {
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

