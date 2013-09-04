<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="invItem.title" /></title>
<meta name="menu" content="InvMenu" />
</head>
<div class="span2">
	<h2>
		<fmt:message key="invItem.heading" />
	</h2>
	<p>
		<fmt:message key="invItem.message" />
	</p>
</div>
<div class="span10">
	<spring:bind path="invItem.*">
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

	<form:form commandName="invItem" method="post" action="invItem" onsubmit="return onFormSubmit(this)" id="invItem" cssClass="well form-horizontal">
		<form:hidden path="id" />
		<input type="hidden" name="from" value="<c:out value="${param.from}"/>" />
		<input type="hidden" name="action"/>

		<c:if test="${invItem.id == null }">
			<spring:bind path="invItem.code">
				<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
					<appfuse:label styleClass="control-label" key="invItem.code" />
					<div class="controls">
						<form:input path="code" id="code" cssClass="input-medium" maxlength="10" />
						<form:errors path="code" cssClass="help-inline" />
					</div>
				</div>
			</spring:bind>
		</c:if>
		<c:if test="${invItem.id != null }">
			<div class="control-group">
				<appfuse:label styleClass="control-label" key="invItem.code" />
				<div class="controls">
					<span class="input-medium uneditable-input"><c:out value="${invItem.code}" /></span>
					<form:hidden path="code"/>
				</div>
			</div>
		</c:if>
		
		<spring:bind path="invItem.invItemGroup.code">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="invItem.invItemGroup.code" />
				<div class="controls">
					<form:select path="invItemGroup.code">
						<form:option value=""></form:option>
						<form:options items="${invItemGroupList}" itemLabel="name" itemValue="code"/>
					</form:select>
					<form:errors path="invItemGroup.code" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="invItem.name">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="invItem.name" />
				<div class="controls">
					<form:input path="name" id="name" cssClass="input-xlarge" maxlength="50" />
					<form:errors path="name" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="invItem.description">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="invItem.description" />
				<div class="controls">
					<form:textarea path="description" id="description" cssClass="input-xlarge"/>
					<form:errors path="description" cssClass="help-inline" />
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
		return validateInvItem(theForm);
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
<v:javascript formName="invItem" staticJavascript="false" />
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>