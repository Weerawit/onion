<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="invGoodsReceiptItem.title" /></title>
<meta name="menu" content="InvMenu" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/scripts/datepicker/css/bootstrap-datetimepicker.min.css'/>" />
<script type="text/javascript" src="<c:url value='/scripts/jquery-lookup.js'/>"></script>
</head>
<div class="span2">
	<h2>
		<fmt:message key="invGoodsReceiptItem.heading" />
	</h2>
	<p>
		<fmt:message key="invGoodsReceiptItem.message" />
	</p>
</div>
<div class="span10">
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
		<%--store invGoodsReceipt id in request to go back to previous page --%>
		<input type="hidden" name="invGoodsReceipt.id" value="<c:out value="${invGoodsReceiptItem.invGoodsReceipt.id}"/>" />

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

		<c:choose>
			<c:when test="${invGoodsReceiptItem.invGoodsReceipt.runningNo != null }">
				<%--readonly --%>
				<div class="control-group">
					<appfuse:label styleClass="control-label" key="invGoodsReceiptItem.invItem.code" />
					<div class="controls">
						<span class="input-medium uneditable-input"><c:out value="${invGoodsReceiptItem.invItem.name}" /></span>
					</div>
				</div>

				<div class="control-group">
					<appfuse:label styleClass="control-label" key="invGoodsReceiptItem.qty" />
					<div class="controls">
						<span class="input-medium uneditable-input"><c:out value="${invGoodsReceiptItem.qty}" /></span>
					</div>
				</div>

				<div class="control-group">
					<appfuse:label styleClass="control-label" key="invGoodsReceiptItem.unitPrice" />
					<div class="controls">
						<span class="input-medium uneditable-input"><c:out value="${invGoodsReceiptItem.unitPrice}" /></span>
					</div>
				</div>

				<div class="control-group">
					<appfuse:label styleClass="control-label" key="invGoodsReceiptItem.memo" />
					<div class="controls">
						<textarea class="input-xlarge uneditable-input" readonly="readonly">
					<c:out value="${invGoodsReceiptItem.memo}" />
					</textarea>
					</div>
				</div>

			</c:when>
			<c:otherwise>
				<%--form input --%>
				<spring:bind path="invGoodsReceiptItem.invItem.code">
					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
						<appfuse:label styleClass="control-label" key="invGoodsReceiptItem.invItem.code" />
						<div class="controls">
							<form:hidden path="invItem.code" id="invItem.code" maxlength="20"/>
							<form:input path="invItem.name" id="invItem.name" cssClass="input-medium" maxlength="20" autocomplete="off"  data-lookup-key-value="${invGoodsReceiptItem.invItem.name}"/>
							<form:errors path="invItem.code" cssClass="help-inline" />
						</div>
					</div>
				</spring:bind>

				<spring:bind path="invGoodsReceiptItem.qty">
					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
						<appfuse:label styleClass="control-label" key="invGoodsReceiptItem.qty" />
						<div class="controls">
							<form:input path="qty" id="qty" cssClass="input-medium" maxlength="10" />
							<form:errors path="qty" cssClass="help-inline" />
						</div>
					</div>
				</spring:bind>

				<spring:bind path="invGoodsReceiptItem.unitPrice">
					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
						<appfuse:label styleClass="control-label" key="invGoodsReceiptItem.unitPrice" />
						<div class="controls">
							<form:input path="unitPrice" id="unitPrice" cssClass="input-medium" maxlength="10" />
							<form:errors path="unitPrice" cssClass="help-inline" />
						</div>
					</div>
				</spring:bind>

				<%-- 		<spring:bind path="invGoodsReceiptItem.unitType"> --%>
				<%-- 			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}"> --%>
				<%-- 				<appfuse:label styleClass="control-label" key="invGoodsReceiptItem.unitType" /> --%>
				<%-- 				<div class="controls"> --%>
				<%-- 					<form:input path="unitType" id="unitType" cssClass="input-xlarge" maxlength="50" /> --%>
				<%-- 					<form:errors path="unitType" cssClass="help-inline" /> --%>
				<%-- 				</div> --%>
				<%-- 			</div> --%>
				<%-- 		</spring:bind> --%>

				<spring:bind path="invGoodsReceiptItem.memo">
					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
						<appfuse:label styleClass="control-label" key="invGoodsReceiptItem.memo" />
						<div class="controls">
							<form:textarea path="memo" id="memo" cssClass="input-xlarge" />
							<form:errors path="memo" cssClass="help-inline" />
						</div>
					</div>
				</spring:bind>
			</c:otherwise>
		</c:choose>


		<fieldset class="form-actions">

			<c:choose>
				<c:when test="${invGoodsReceiptItem.invGoodsReceipt.documentNumber.documentNo == null}">
					<button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
						<i class="icon-ok icon-white"></i>
						<fmt:message key="button.save" />
					</button>

					<c:if test="${invGoodsReceiptItem.id != null}">
						<button type="submit" class="btn" name="delete" onclick="bCancel=true;return confirmMessage(msgDelConfirm)">
							<i class="icon-trash"></i>
							<fmt:message key="button.delete" />
						</button>
					</c:if>
				</c:when>
				<c:otherwise>

					<button type="submit" class="btn btn-primary" name="cancel" onclick="bCancel=true">
						<i class="icon-ok icon-white"></i>
						<fmt:message key="button.done" />
					</button>

				</c:otherwise>
			</c:choose>


			<button type="submit" class="btn" name="cancel" onclick="bCancel=true">
				<i class="icon-arrow-left"></i>
				<fmt:message key="button.back" />
			</button>
		</fieldset>
	</form:form>
</div>
<script type="text/javascript">
<!-- This is here so we can exclude the selectAll call when roles is hidden -->
	function onFormSubmit(theForm) {
		return validateInvGoodsReceiptItem(theForm);
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
<v:javascript formName="invGoodsReceiptItem" staticJavascript="false" />
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>