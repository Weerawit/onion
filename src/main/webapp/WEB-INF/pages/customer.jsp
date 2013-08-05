<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="customer.title" /></title>
<meta name="menu" content="CustomerMenu" />
</head>
<div class="span2">
	<h2>
		<fmt:message key="customer.heading" />
	</h2>
	<p>
		<fmt:message key="customer.message" />
	</p>
</div>
<div class="span7">
	<spring:bind path="customer.*">
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

	<form:form commandName="customer" method="post" action="customer" onsubmit="return onFormSubmit(this)" id="customer" cssClass="well form-horizontal">
		<input type="hidden" name="from" value="<c:out value="${param.from}"/>" />

		<c:if test="${customer.id != null }">
			<div class="control-group">
				<appfuse:label styleClass="control-label" key="customer.id" />
				<div class="controls">
					<span class="input-xlarge uneditable-input"><c:out value="${customer.id}" /></span>
					<form:hidden path="id"/>
				</div>
			</div>
		</c:if>
		
		<spring:bind path="customer.name">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="customer.name" />
				<div class="controls">
					<form:input path="name" id="name" cssClass="input-xlarge" maxlength="255" />
					<form:errors path="name" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="customer.customerType">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="customer.customerType" />
				<div class="controls">
					<form:select path="customerType">
						<form:option value=""></form:option>
						<form:options items="${customerTypeList}" itemLabel="label" itemValue="value"/>
					</form:select>
					<form:errors path="customerType" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="customer.contactName">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="customer.contactName" />
				<div class="controls">
					<form:input path="contactName" id="contactName" cssClass="input-large" maxlength="50" />
					<form:errors path="contactName" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="customer.contactTel">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="customer.contactTel" />
				<div class="controls">
					<form:input path="contactTel" id="contactTel" cssClass="input-large" maxlength="50" placeholder="668"/>
					<form:errors path="contactTel" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		
		<spring:bind path="customer.shipingAddress">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="customer.shipingAddress" />
				<div class="controls">
					<form:textarea path="shipingAddress" id="shipingAddress" cssClass="input-xlarge"/>
					<form:errors path="shipingAddress" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="customer.billingAddress">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="customer.billingAddress" />
				<div class="controls">
					<form:textarea path="billingAddress" id="billingAddress" cssClass="input-xlarge"/>
					<form:errors path="billingAddress" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="customer.memo">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="customer.memo" />
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
		return validateCustomer(theForm);
	}
</script>
<v:javascript formName="customer" staticJavascript="false" />
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>