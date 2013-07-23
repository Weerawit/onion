<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="invGoodsReceiptItem.title" /></title>
<meta name="menu" content="InvMenu" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/scripts/datepicker/css/bootstrap-datetimepicker.min.css'/>" />
<script type="text/javascript" src="<c:url value='/scripts/datepicker/js/bootstrap-datetimepicker.min.js'/>"></script>

</head>
<div class="span2">
	<h2>
		<fmt:message key="invGoodsReceiptItem.heading" />
	</h2>
	<p>
		<fmt:message key="invGoodsReceiptItem.message" />
	</p>
</div>
<div class="span7">
	<spring:bind path="invGoodsReceiptItem.*">
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

	<form:form commandName="invGoodsReceiptItem" method="post" action="invGoodsReceiptItem" onsubmit="return onFormSubmit(this)" id="invGoodsReceiptItem" cssClass="well form-horizontal">
		<input type="hidden" name="rowNum" value="<c:out value="${param.id}"/>" />
		<input type="hidden" name="from" value="<c:out value="${param.from}"/>" />

<%-- 		<c:if test="${invGoodsReceiptItem.id == null }"> --%>
<%-- 			<spring:bind path="invGoodsReceiptItem.runningNo"> --%>
<%-- 				<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}"> --%>
<%-- 					<appfuse:label styleClass="control-label" key="invGoodsReceiptItem.runningNo" /> --%>
<!-- 					<div class="controls"> -->
<%-- 						<form:input path="runningNo" id="runningNo" cssClass="input-xlarge" maxlength="50" /> --%>
<%-- 						<form:errors path="runningNo" cssClass="help-inline" /> --%>
<!-- 					</div> -->
<!-- 				</div> -->
<%-- 			</spring:bind> --%>
<%-- 		</c:if> --%>
<%-- 		<c:if test="${invGoodsReceiptItem.id != null }"> --%>
<!-- 			<div class="control-group"> -->
<%-- 				<appfuse:label styleClass="control-label" key="invGoodsReceiptItem.runningNo" /> --%>
<!-- 				<div class="controls"> -->
<%-- 					<span class="input-xlarge uneditable-input"><c:out value="${invGoodsReceiptItem.runningNo}" /></span> --%>
<%-- 					<form:hidden path="runningNo"/> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<%-- 		</c:if> --%>
		
		<spring:bind path="invGoodsReceiptItem.invItem">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="invGoodsReceiptItem.invItem.code" />
				<div class="controls">
					<form:select path="invItem.code">
						<form:option value=""></form:option>
						<form:options items="${invItemList}" itemLabel="name" itemValue="code"/>
					</form:select>
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="invGoodsReceiptItem.qty">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="invGoodsReceiptItem.qty" />
				<div class="controls">
					<form:input path="qty" id="qty" cssClass="input-xlarge" maxlength="50" />
					<form:errors path="qty" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="invGoodsReceiptItem.unitPrice">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="invGoodsReceiptItem.unitPrice" />
				<div class="controls">
					<form:input path="unitPrice" id="unitPrice" cssClass="input-xlarge" maxlength="50" />
					<form:errors path="unitPrice" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="invGoodsReceiptItem.unitType">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="invGoodsReceiptItem.unitType" />
				<div class="controls">
					<form:input path="unitType" id="unitType" cssClass="input-xlarge" maxlength="50" />
					<form:errors path="unitType" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="invGoodsReceiptItem.memo">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="invGoodsReceiptItem.memo" />
				<div class="controls">
					<form:textarea path="memo" id="memo" cssClass="input-xlarge"/>
					<form:errors path="memo" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		

		<fieldset class="form-actions">
			<button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
				<i class="icon-ok icon-white"></i>
				<fmt:message key="button.save" />
			</button>

			<c:if test="${param.from == 'list' and param.method != 'Add'}">
				<button type="submit" class="btn" name="delete" onclick="bCancel=true;return confirmMessage(msgDelConfirm)">
					<i class="icon-trash"></i>
					<fmt:message key="button.delete" />
				</button>
			</c:if>

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
		return validateinvGoodsReceiptItem(theForm);
	}
	$(function() {

		var st = $('#receiptDateDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
	});
</script>
<v:javascript formName="invGoodsReceiptItem" staticJavascript="false" />
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>