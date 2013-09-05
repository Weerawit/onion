<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="invStock.title" /></title>
<meta name="menu" content="InvMenu" />
</head>
<div class="span2">
	<h2>
		<fmt:message key="invStock.heading" />
	</h2>
	<p>
		<fmt:message key="invStock.message" />
	</p>
</div>
<div class="span7">
	<spring:bind path="invStock.*">
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

	<form:form commandName="invStock" method="post" action="invStock" onsubmit="return onFormSubmit(this)" id="invStock" cssClass="well form-horizontal">
		<form:hidden path="id" />
		<input type="hidden" name="from" value="<c:out value="${param.from}"/>" />
	
		<spring:bind path="invStock.invItem.code">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="invStock.invItem.code" />
				<div class="controls">
					<form:hidden path="invItem.code" id="invItem.code" maxlength="20"/>
					<span class="input-medium uneditable-input"><c:out value="${invStock.invItem.name}" /></span>
					<form:errors path="invItem.code" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<div class="control-group">
			<appfuse:label styleClass="control-label" key="invStock.oldQty" />
			<div class="controls">
				<span class="input-medium uneditable-input"><fmt:formatNumber value="${invStock.qty }" pattern="#,##0.##"/></span>
			</div>
		</div>
		
		<div class="control-group">
			<appfuse:label styleClass="control-label" key="invStock.oldQtyAvailable" />
			<div class="controls">
				<span class="input-medium uneditable-input"><fmt:formatNumber value="${invStock.qtyAvailable }" pattern="#,##0.##"/></span>
			</div>
		</div>
		
		<spring:bind path="invStock.qty">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="invStock.qtyAdjust" />
				<div class="controls">
					<input type="text" name="qty" id="qty" class="input-medium" maxlength="10" placeholder="+/-"/>
					<form:errors path="qty" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<fieldset class="form-actions">
			<button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
				<i class="icon-ok icon-white"></i>
				<fmt:message key="button.save" />
			</button>

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
		return validateInvStock(theForm);
	}
</script>
<v:javascript formName="invStock" staticJavascript="false" />
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>