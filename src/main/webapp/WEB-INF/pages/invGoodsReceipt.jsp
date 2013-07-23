<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="invGoodsReceipt.title" /></title>
<meta name="menu" content="InvMenu" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/scripts/datepicker/css/bootstrap-datetimepicker.min.css'/>" />
<script type="text/javascript" src="<c:url value='/scripts/datepicker/js/bootstrap-datetimepicker.min.js'/>"></script>

</head>
<div class="span2">
	<h2>
		<fmt:message key="invGoodsReceipt.heading" />
	</h2>
	<p>
		<fmt:message key="invGoodsReceipt.message" />
	</p>
</div>
<div class="span7">
	<spring:bind path="invGoodsReceipt.*">
		<c:if test="${not empty status.errorMessages}">
			<div class="alert alert-error fade in">
				<a href="#" data-dismiss="alert" class="close">&times;</a>
				<c:forEach var="error" items="${status.errorMessages}">
					<c:out value="${error}" escapeXml="false" />
					<br />
				</c:forEach>
			</div>
		</c:if>
	</spring:bind>

	<form:form commandName="invGoodsReceipt" method="post" action="invGoodsReceipt/save" onsubmit="return onFormSubmit(this)" id="invGoodsReceipt" cssClass="well form-horizontal">
		<form:hidden path="id" />
		<input type="hidden" name="from" value="<c:out value="${param.from}"/>" />

		<c:if test="${invGoodsReceipt.id == null }">
			<spring:bind path="invGoodsReceipt.runningNo">
				<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
					<appfuse:label styleClass="control-label" key="invGoodsReceipt.runningNo" />
					<div class="controls">
						<form:input path="runningNo" id="runningNo" cssClass="input-xlarge" maxlength="50" />
						<form:errors path="runningNo" cssClass="help-inline" />
					</div>
				</div>
			</spring:bind>
		</c:if>
		<c:if test="${invGoodsReceipt.id != null }">
			<div class="control-group">
				<appfuse:label styleClass="control-label" key="invGoodsReceipt.runningNo" />
				<div class="controls">
					<span class="input-xlarge uneditable-input"><c:out value="${invGoodsReceipt.runningNo}" /></span>
					<form:hidden path="runningNo"/>
				</div>
			</div>
		</c:if>
		
		<spring:bind path="invGoodsReceipt.supplier">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="invGoodsReceipt.supplier.code" />
				<div class="controls">
					<form:select path="supplier.code">
						<form:option value=""></form:option>
						<form:options items="${supplierList}" itemLabel="name" itemValue="code"/>
					</form:select>
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="invGoodsReceipt.receiptDate">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="invGoodsReceipt.receiptDate" />
				<div class="controls">
					<div class="input-append date" id="receiptDateDatepicker">
						<form:input path="receiptDate" id="receiptDate" cssClass="input-medium" maxlength="50" /><span class="add-on"><i class="icon-th"></i></span>
					</div>
					<form:errors path="receiptDate" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="invGoodsReceipt.memo">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="invGoodsReceipt.memo" />
				<div class="controls">
					<form:textarea path="memo" id="memo" cssClass="input-xlarge"/>
					<form:errors path="memo" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
<%-- 		<c:if test="${not empty invGoodsReceiptItemList }"> --%>
<!-- 		<div class="control-group pull-right"> -->
<%-- 			<fmt:message key="label.showPagination" /> --%>
<!-- 			&nbsp; -->
<%-- 			<%=request.getAttribute("psSelect")%> --%>
<!-- 		</div> -->
<%-- 		</c:if>	 --%>
		
		<div id="actions">
		
			<button id="button.add" class="btn btn-primary" type="submit" onclick="bCancel=true;$('#invGoodsReceipt').attr('action', 'invGoodsReceipt/addDetail');">
				<i class="icon-plus icon-white"></i>
				<fmt:message key="button.add" />
			</button>
			
			<button id="button.delete" class="btn" type="submit" onclick="bCancel=true;$('#invGoodsReceipt').attr('action', 'invGoodsReceipt/deleteDetail');">>
				<i class="icon-trash"></i>
				<fmt:message key="button.delete" />
			</button>
			
		</div>
		<display:table name="invGoodsReceiptItemList" cellspacing="0" cellpadding="0" requestURI="" id="invGoodsReceiptItem" class="table table-condensed table-striped table-hover table-bordered">
			<display:column title="<input type='checkbox' name='chkSelectAll' id='chkSelectAll'/>" headerClass="span1" class="span1">
				<input type="checkbox" id="checkbox" name="checkbox" value="<c:out value='${invGoodsReceiptItem_rowNum - 1}'/>" />
			</display:column>
			<display:column titleKey="invGoodsReceiptItem.invItem.code" sortable="true">
				<a onclick="bCancel=true;$('#invGoodsReceipt').attr('action', 'invGoodsReceipt/editDetail?&id=${ invGoodsReceiptItem_rowNum - 1}');"><c:out value="${invGoodsReceiptItem.invItem.code}"/></a>
			</display:column>
			<display:column property="invItem.name" escapeXml="true" sortable="true" titleKey="invGoodsReceiptItem.invItem.name" sortName="invItem.name" />
			<display:column property="qty" escapeXml="true" sortable="true" titleKey="invGoodsReceiptItem.qty" sortName="qty" />
			<display:column property="unitPrice" escapeXml="true" sortable="true" titleKey="invGoodsReceiptItem.unitPrice" sortName="unitPrice" />
			<display:setProperty name="export.csv" value="true"></display:setProperty>
			<display:setProperty name="export.excel" value="true"></display:setProperty>
			<display:setProperty name="export.xml" value="false"></display:setProperty>
			<display:setProperty name="export.pdf" value="true"></display:setProperty>
			<display:setProperty name="export.excel.filename" value="InvGoodsReceipt.xls" />
			<display:setProperty name="export.csv.filename" value="InvGoodsReceipt.csv" />
			<display:setProperty name="export.pdf.filename" value="InvGoodsReceipt.pdf" />
		</display:table>
		

		<fieldset class="form-actions">
			<button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
				<i class="icon-ok icon-white"></i>
				<fmt:message key="button.save" />
			</button>
<!-- not allow to delete -->
<%-- 			<c:if test="${param.from == 'list' and param.method != 'Add'}"> --%>
<!-- 				<button type="submit" class="btn" name="delete" onclick="bCancel=true;return confirmMessage(msgDelConfirm)"> -->
<!-- 					<i class="icon-trash"></i> -->
<%-- 					<fmt:message key="button.delete" /> --%>
<!-- 				</button> -->
<%-- 			</c:if> --%>

			<button type="submit" class="btn" name="cancel" onclick="bCancel=true">
				<i class="icon-remove"></i>
				<fmt:message key="button.cancel" />
			</button>
		</fieldset>
	</form:form>
</div>
<script type="text/javascript">
<!-- This is here so we can exclude the selectAll call when roles is hidden -->
	function onFormSubmit(theForm) {	
		return validateinvGoodsReceipt(theForm);
	}
	$(function() {

		var st = $('#receiptDateDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
	});

	function validateDelete(checkbox) {

		if (!hasChecked(checkbox)) {
			alert('<fmt:message key="global.errorNoCheckboxSelectForDelete"/>');
			return false;
		}
		if (confirm('<fmt:message key="global.confirm.delete"/>')) {
			return true;
		} else {
			return false;
		}
	}

	<c:if test="${not empty invGoodsReceiptItemList}">
	$(document).ready(function() {
		$("#chkSelectAll").click(function() {
			toggleCheckAll(this, document.forms['invGoodsReceipt'].checkbox);
		});
	});
	</c:if>
</script>
<v:javascript formName="invGoodsReceipt" staticJavascript="false" />
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>