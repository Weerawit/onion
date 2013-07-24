<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="employee.title" /></title>
<meta name="menu" content="EmployeeMenu" />
</head>
<div class="span2">
	<h2>
		<fmt:message key="employee.heading" />
	</h2>
	<p>
		<fmt:message key="employee.message" />
	</p>
</div>
<div class="span7">
	<spring:bind path="employee.*">
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

	<form:form commandName="employee" method="post" action="employee" onsubmit="return onFormSubmit(this)" id="employee" cssClass="well form-horizontal">
		<input type="hidden" name="from" value="<c:out value="${param.from}"/>" />

		<c:if test="${employee.id != null }">
			<div class="control-group">
				<appfuse:label styleClass="control-label" key="employee.id" />
				<div class="controls">
					<span class="input-xlarge uneditable-input"><c:out value="${employee.id}" /></span>
					<form:hidden path="id"/>
				</div>
			</div>
		</c:if>
		
		<spring:bind path="employee.firstName">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="employee.firstName" />
				<div class="controls">
					<form:input path="firstName" id="firstName" cssClass="input-xlarge" maxlength="50" />
					<form:errors path="firstName" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="employee.lastName">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="employee.lastName" />
				<div class="controls">
					<form:input path="lastName" id="lastName" cssClass="input-xlarge" maxlength="50" />
					<form:errors path="lastName" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="employee.nickName">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="employee.nickName" />
				<div class="controls">
					<form:input path="nickName" id="nickName" cssClass="input-large" maxlength="50" />
					<form:errors path="nickName" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="employee.idCardNo">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="employee.idCardNo" />
				<div class="controls">
					<form:input path="idCardNo" id="idCardNo" cssClass="input-large" maxlength="50" />
					<form:errors path="idCardNo" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		
		<spring:bind path="employee.age">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="employee.age" />
				<div class="controls">
					<form:input path="age" id="age" cssClass="input-large" maxlength="10" />
					<form:errors path="age" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="employee.wage">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="employee.wage" />
				<div class="controls">
					<form:input path="wage" id="wage" cssClass="input-large" maxlength="10" />
					<form:errors path="wage" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		
		<spring:bind path="employee.address">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="employee.address" />
				<div class="controls">
					<form:textarea path="address" id="address" cssClass="input-xlarge"/>
					<form:errors path="address" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="employee.memo">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="employee.memo" />
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
		return validateEmployee(theForm);
	}
</script>
<v:javascript formName="employee" staticJavascript="false" />
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>