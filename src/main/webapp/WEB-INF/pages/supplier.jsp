<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="supplier.title" /></title>
<meta name="menu" content="InvMenu" />
</head>
<div class="span2">
	<h2>
		<fmt:message key="supplier.heading" />
	</h2>
	<p>
		<fmt:message key="supplier.message" />
	</p>
</div>
<div class="span7">
	<spring:bind path="supplier.*">
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

	<form:form commandName="supplier" method="post" action="supplier" onsubmit="return onFormSubmit(this)" id="supplier" cssClass="well form-horizontal">
		<form:hidden path="id" />
		<input type="hidden" name="from" value="<c:out value="${param.from}"/>" />
		<input type="hidden" name="action"/>

		<c:if test="${supplier.id == null }">
			<spring:bind path="supplier.code">
				<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
					<appfuse:label styleClass="control-label" key="supplier.code" />
					<div class="controls">
						<form:input path="code" id="code" cssClass="input-medium" maxlength="10" />
						<form:errors path="code" cssClass="help-inline" />
					</div>
				</div>
			</spring:bind>
		</c:if>
		<c:if test="${supplier.id != null }">
			<div class="control-group">
				<appfuse:label styleClass="control-label" key="supplier.code" />
				<div class="controls">
					<span class="input-medium uneditable-input"><c:out value="${supplier.code}" /></span>
					<form:hidden path="code"/>
				</div>
			</div>
		</c:if>
		
		<spring:bind path="supplier.name">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="supplier.name" />
				<div class="controls">
					<form:input path="name" id="name" cssClass="input-xlarge" maxlength="50" />
					<form:errors path="name" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="supplier.address">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="supplier.address" />
				<div class="controls">
					<form:textarea path="address" id="address" cssClass="input-xlarge"/>
					<form:errors path="address" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="supplier.contactName">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="supplier.contactName" />
				<div class="controls">
					<form:input path="contactName" id="contactName" cssClass="input-xlarge" maxlength="50" />
					<form:errors path="contactName" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="supplier.contactTel">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="supplier.contactTel" />
				<div class="controls">
					<form:input path="contactTel" id="contactTel" cssClass="input-xlarge" maxlength="50" placeholder="66xxxxxxxxx"/>
					<form:errors path="contactTel" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		

		<fieldset class="form-actions">
			<button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
				<i class="icon-ok icon-white"></i>
				<fmt:message key="button.save" />
			</button>

			<c:if test="${param.from == 'list' and param.method != 'Add'}">
				<button type="submit" class="btn" name="delete" onclick="bCancel=true;return deleteThis()">
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
		return validateSupplier(theForm);
	}
	
	function deleteThis() {
		var form = document.forms[0];
		 confirmMessage(msgDelConfirm, function(result) {
			 if (result) {
				 form['action'].value="delete";
				 form.submit();
			 }
		 });
		 return false;
	}
</script>
<v:javascript formName="supplier" staticJavascript="false" />
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>