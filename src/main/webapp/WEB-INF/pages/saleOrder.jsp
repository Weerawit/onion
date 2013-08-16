<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="saleOrder.title" /></title>
<meta name="menu" content="SaleOrderMenu" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/scripts/datepicker/css/bootstrap-datetimepicker.min.css'/>" />
<script type="text/javascript" src="<c:url value='/scripts/datepicker/js/bootstrap-datetimepicker.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery-lookup.js'/>"></script>

</head>
<div class="span2">
	<h2>
		<fmt:message key="saleOrder.heading" />
	</h2>
	<p>
		<fmt:message key="saleOrder.message" />
	</p>
</div>
<div class="span10">
	<spring:bind path="saleOrder.*">
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

	<form:form commandName="saleOrder" method="post" action="saleOrder" onsubmit="return onFormSubmit(this)" id="saleOrder">
		<form:hidden path="id" />
		<input type="hidden" name="from" value="<c:out value="${param.from}"/>" />

		<%--<c:if test="${saleOrder.id != null }"> --%>
			<div class="control-group">
				<appfuse:label styleClass="control-label" key="saleOrder.saleOrderNo" />
				<div class="controls">
					<span class="input-medium uneditable-input"><c:out value="${saleOrder.saleOrderNo}" /></span>
					<form:hidden path="saleOrderNo" />
				</div>
			</div>
		<%--</c:if>--%>
		
		<div class="well row-fluid">
			<fieldset>
				<legend>
				<fmt:message key="saleOrder.customer.name"/>
				</legend>
				<spring:bind path="saleOrder.customer.name">
					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
						<div class="controls">
							<form:input path="customer.name" id="customer.name" cssClass="input-large" maxlength="50" autocomplete="off" />
							<form:errors path="customer.name" cssClass="help-inline" />
							<form:hidden path="customer.id"/>
						</div>
					</div>
					<%--
					<dl class="dl-horizontal">
						<dt><appfuse:label styleClass="control-label" key="saleOrder.customer.contactName" /></dt>
						<dd><label id="contactName"></label></dd>
						<dt><appfuse:label styleClass="control-label" key="saleOrder.customer.contactTel" /></dt>
						<dd><label id="contactTel"></label></dd>
						<dt><appfuse:label styleClass="control-label" key="saleOrder.customer.billingAddress" /></dt>
						<dd><address id="billingAddress"></address></dd>
						<dt><appfuse:label styleClass="control-label" key="saleOrder.customer.shipingAddress" /></dt>
						<dd><address id="shipingAddress"></address></dd>
					</dl>
					 --%>
					<div class="row-fluid">
						<fieldset class="span6">
							<legend>
							<appfuse:label styleClass="control-label" key="saleOrder.customer.contactName" />
							</legend>
							<label id="contactName"><c:out value="${saleOrder.customer.contactName }"/></label>
						</fieldset>
						<fieldset class="span6">
							<legend>
							<appfuse:label styleClass="control-label" key="saleOrder.customer.contactTel" />
							</legend>
							<label id="contactTel"><c:out value="${saleOrder.customer.contactTel }"/></label>
						</fieldset>
					</div>
					<div class="row-fluid">
						<fieldset class="span6">
							<legend>
							<appfuse:label styleClass="control-label" key="saleOrder.customer.billingAddress" />
							</legend>
							<label id="billingAddress"><c:out value="${saleOrder.customer.billingAddress }"/></label>
						</fieldset>
						<fieldset class="span6">
							<legend>
							<appfuse:label styleClass="control-label" key="saleOrder.customer.shipingAddress" />
							</legend>
							<label id="shipingAddress"><c:out value="${saleOrder.customer.shipingAddress }"/></label>
						</fieldset>
					</div>
				</spring:bind>
			</fieldset>
		</div>
		
		<div class="row-fluid">
			<span class="span6">
				<spring:bind path="saleOrder.paymentType">
				<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
					<appfuse:label styleClass="control-label" key="saleOrder.paymentType" />
					<div class="controls">
						<form:select path="paymentType">
							<form:option value=""></form:option>
							<form:options items="${paymentTypeList}" itemLabel="label" itemValue="value" />
						</form:select>
						<form:errors path="paymentType" cssClass="help-inline" />
					</div>
				</div>
			</spring:bind>
			</span>
			<span class="span6">
				<spring:bind path="saleOrder.deliveryDate">
				<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
					<appfuse:label styleClass="control-label" key="saleOrder.deliveryDate" />
					<div class="controls">
						<div class="input-append date" id="deliveryDateDatepicker">
							<form:input path="deliveryDate" id="deliveryDate" cssClass="input-medium" maxlength="50" /><span class="add-on"><i class="icon-th"></i></span>
						</div>
						<form:errors path="deliveryDate" cssClass="help-inline" />
					</div>
				</div>
			</spring:bind>
			</span>
		</div>
		<div class="row-fluid">
			<span class="span6">
				<spring:bind path="saleOrder.totalPrice">
					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
						<appfuse:label styleClass="control-label" key="saleOrder.totalPrice" />
						<div class="controls">
							<form:input path="totalPrice" id="totalPrice" cssClass="input-medium" maxlength="10" />
							<form:errors path="totalPrice" cssClass="help-inline" />
						</div>
					</div>
				</spring:bind>
			</span>
			<span class="span6">
				<spring:bind path="saleOrder.status">
					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
						<appfuse:label styleClass="control-label" key="saleOrder.status" />
						<div class="controls">
							<form:select path="status">
								<form:option value=""></form:option>
								<form:options items="${saleOrderStatusList}" itemLabel="label" itemValue="value" />
							</form:select>
							<form:errors path="status" cssClass="help-inline" />
						</div>
					</div>
				</spring:bind>
			</span>
		</div>

		
		<div id="actions">
		
			<input type="hidden" name="addcatalog">
		
			<button id="button.add" class="btn btn-primary" type="submit" onclick="bCancel=true;$('#saleOrder').attr('action', '${ctx}/saleOrder/addDetail');">
				<i class="icon-plus icon-white"></i>
				<fmt:message key="button.add" />
			</button>
			
			<button id="button.delete" class="btn" type="submit" onclick="bCancel=true;return (validateDelete(document.forms['saleOrder'].checkbox) && $('#saleOrder').attr('action', '${ctx}/saleOrder/deleteDetail'));">
				<i class="icon-trash"></i>
				<fmt:message key="button.delete" />
			</button>
			
		</div>
		<display:table name="saleOrderItemList" cellspacing="0" cellpadding="0" requestURI="" id="saleOrderItem" class="table table-condensed table-striped table-hover table-bordered">
			<display:column title="<input type='checkbox' name='chkSelectAll' id='chkSelectAll'/>" headerClass="span1" class="span1">
				<input type="checkbox" id="checkbox" name="checkbox" value="<c:out value='${saleOrderItem_rowNum - 1}'/>" />
			</display:column>
			<display:column titleKey="saleOrderItem.invItem.code" sortable="true">
				<button class="btn btn-link" type="submit" onclick="bCancel=true;$('#saleOrder').attr('action', '${ctx}/saleOrder/editDetail?&id=${ saleOrderItem_rowNum - 1}');">
					<c:out value="${saleOrderItem.invItem.code}"/>
				</button>
			</display:column>


			<display:column property="invItem.name" escapeXml="true" sortable="true" titleKey="saleOrderItem.invItem.name" sortName="invItem.name" />
			<display:column property="qty" sortable="true" titleKey="saleOrderItem.qty" sortName="qty" format="{0,number,#,##0.##}"/>
		</display:table>


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
		return validateSaleOrder(theForm);
	}
	
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
	
	$(function() {
		var st = $('#deliveryDateDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
	});

	<c:if test="${not empty saleOrderItemList}">
	$(document).ready(function() {
		$("#chkSelectAll").click(function() {
			toggleCheckAll(this, document.forms['saleOrder'].checkbox);
		});
	});
	</c:if>
	
	
	$(document).ready(function () {
		$('input[name="customer.name"]').lookup({
			type: 'customer',
			displayProperty: function (json) {
				return json.name;
			},
			selectProperty: function (json) {
				$('#contactName').text(json.contactName);
				$('#contactTel').text(json.contactTel);
				$('#billingAddress').text(json.billingAddress);
				$('#shipingAddress').text(json.shipingAddress);
				return json.name;
			},
			btnSearchCondition: function () {
				return {name: $('input[name="customer.name"]').val()};	
			},
			handler: function (json) {
				if (json) {
					$('input[name="customer.id"]').val(json.id);
					$('#contactName').text(json.contactName);
					$('#contactTel').text(json.contactTel);
					$('#billingAddress').text(json.billingAddress);
					$('#shipingAddress').text(json.shipingAddress);
				} else {
					$('input[name="customer.id"]').val('');
					$('input[name="customer.name"]').val('');
					$('#contactName').text('');
					$('#contactTel').text('');
					$('#billingAddress').text('');
					$('#shipingAddress').text('');
				}
			}
		});
	});

	
	
	$(document).ready(function () {
		$('input[name="addcatalog"]').lookup({
			type: 'catalog',
			displayProperty: function (json) {
				return json.name;
			},
			selectProperty: function (json) {
				alert(json.id);
				return json.name;
			},
			btnSearch: '<a class="btn btn-primary"> <i class="icon-plus icon-white"></i><fmt:message key="button.add" /></a>',
			appendBtnAndText: false,
			btnSearchCondition: function () {
				return {};	
			},
			handler: function (json) {
				if (json) {
					//$('input[name="invItem.code"]').val(json.code);
				} else {
					//$('input[name="invItem.code"]').val('');
					//$('input[name="customer.name"]').val('');
				}
			}
		});
	});
</script>
<v:javascript formName="saleOrder" staticJavascript="false" />
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>