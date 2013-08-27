<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="catalogType.title" /></title>
<meta name="menu" content="CatalogMenu" />
</head>
<div class="span2">
	<h2>
		<fmt:message key="catalogType.heading" />
	</h2>
	<p>
		<fmt:message key="catalogType.message" />
	</p>
</div>
<div class="span7">
	<spring:bind path="catalogType.*">
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

	<form:form commandName="catalogType" method="post" action="catalogType" onsubmit="return onFormSubmit(this)" id="catalogType" cssClass="well form-horizontal">
		<form:hidden path="id" />
		<input type="hidden" name="from" value="<c:out value="${param.from}"/>" />
		<input type="hidden" name="action"/>

		<c:if test="${catalogType.id == null }">
			<spring:bind path="catalogType.code">
				<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
					<appfuse:label styleClass="control-label" key="catalogType.code" />
					<div class="controls">
						<form:input path="code" id="code" cssClass="input-xlarge" maxlength="50" />
						<form:errors path="code" cssClass="help-inline" />
					</div>
				</div>
			</spring:bind>
		</c:if>
		<c:if test="${catalogType.id != null }">
			<div class="control-group">
				<appfuse:label styleClass="control-label" key="catalogType.code" />
				<div class="controls">
					<span class="input-xlarge uneditable-input"><c:out value="${catalogType.code}" /></span>
					<form:hidden path="code"/>
				</div>
			</div>
		</c:if>
		
		<spring:bind path="catalogType.name">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="catalogType.name" />
				<div class="controls">
					<form:input path="name" id="name" cssClass="input-xlarge" maxlength="50" />
					<form:errors path="name" cssClass="help-inline" />
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
		return validateCatalogType(theForm);
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
<v:javascript formName="catalogType" staticJavascript="false" />
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>