<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="catalogItem.title" /></title>
<meta name="menu" content="CatalogMenu" />
<script type="text/javascript" src="<c:url value='/scripts/jquery-lookup.js'/>"></script>
</head>
<div class="span2">
	<h2>
		<fmt:message key="catalogItem.heading" />
	</h2>
	<p>
		<fmt:message key="catalogItem.message" />
	</p>
</div>
<div class="span7">
	<spring:bind path="catalogItem.*">
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

	<form:form commandName="catalogItem" method="post" action="catalogItem" onsubmit="return onFormSubmit(this)" id="catalogItem" cssClass="well form-horizontal">
		<input type="hidden" name="rowNum" value="<c:out value="${param.id}"/>" />
		<input type="hidden" name="from" value="<c:out value="${param.from}"/>" />
		<%--store invGoodsMovement id in request to go back to previous page --%>
		<input type="hidden" name="catalog.id" value="<c:out value="${catalogItem.catalog.id}"/>" />

		

		
		<spring:bind path="catalogItem.invItem.code">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="catalogItem.invItem.name" />
				<div class="controls">
					<form:hidden path="invItem.code" id="invItem.code" maxlength="20"/>
					<form:input path="invItem.name" id="invItem.name" cssClass="input-medium" maxlength="20" autocomplete="off"  data-lookup-key-value="${catalogItem.invItem.name}"/>
					<form:errors path="invItem.code" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="catalogItem.name">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="catalogItem.name" />
				<div class="controls">
					<form:input path="name" id="name" cssClass="input-medium" maxlength="50" />
					<form:errors path="name" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="catalogItem.qty">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="catalogItem.qty" />
				<div class="controls">
					<form:input path="qty" id="qty" cssClass="input-medium" maxlength="10" />
					<form:errors path="qty" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>



		<fieldset class="form-actions">

	
		
			<button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
				<i class="icon-ok icon-white"></i>
				<fmt:message key="button.save" />
			</button>

			<c:if test="${catalogItem.id != null}">
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
		return validateCatalogItem(theForm);
	}

	$(document).ready(function () {
		$('input[name="invItem.name"]').lookup({
			type: 'item',
			displayProperty: function (json) {
				return json.code + ' : ' + json.name;
			},
			selectProperty: 'name',
			btnSearchCondition: function () {
				return {code: $('input[name="invItem.code"]').val()};	
			},
			handler: function (json) {
				if (json) {
					$('input[name="invItem.code"]').val(json.code);
				} else {
					$('input[name="invItem.code"]').val('');
					$('input[name="invItem.name"]').val('');
				}
			}
		});
	});
</script>
<v:javascript formName="catalogItem" staticJavascript="false" />
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>