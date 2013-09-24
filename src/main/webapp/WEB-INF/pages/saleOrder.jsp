<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="saleOrder.title" /></title>
<meta name="menu" content="SaleOrderMenu" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/scripts/datepicker/css/bootstrap-datetimepicker.min.css'/>" />
<script type="text/javascript" src="<c:url value='/scripts/datepicker/js/bootstrap-datetimepicker.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery-lookup.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery-ajaxDisplaytag.js'/>"></script>

</head>
<div class="span2">
	<h2>
		<fmt:message key="saleOrder.heading" />
	</h2>
	<p>
		<fmt:message key="saleOrder.documentNumber.documentNo" />
	</p>
	<p>
	<c:out value="${saleOrder.documentNumber.documentNo}" />
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
		<form:hidden path="status" />
		<input type="hidden" name="from" value="<c:out value="${param.from}"/>" />
		<form:hidden path="documentNumber.documentNo" />
		<input type="hidden" name="cancelReason"/>
		<input type="hidden" name="action"/>
	
		<div class="well">
			<fieldset>
				<legend>
				<fmt:message key="saleOrder.customer.name"/>
				</legend>
				<spring:bind path="saleOrder.customer.name">
					<c:choose>
						<c:when test="${saleOrder.documentNumber.documentNo == null }">
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<div class="controls">
									<form:input path="customer.name" id="customer.name" cssClass="input-large" maxlength="50" autocomplete="off" />
									<form:errors path="customer.name" cssClass="help-inline" />
									<form:hidden path="customer.id"/>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
								<div class="controls">
									<span class="input-large uneditable-input"><c:out value="${saleOrder.customer.name}" /></span>
									<form:hidden path="customer.name"/>
									<form:errors path="customer.name" cssClass="help-inline" />
									<form:hidden path="customer.id"/>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
					
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
		

		<c:choose>
			<c:when test="${saleOrder.documentNumber.documentNo == null }">
				<div id="actions">
					<input type="hidden" name="addcatalog">
					<button id="addDetailBtn" class="btn btn-primary" type="button" >
						<i class="icon-plus icon-white"></i>
						<fmt:message key="button.add" />
					</button>
					<button id="deleteDetailBtn" class="btn" type="button">
						<i class="icon-trash"></i>
						<fmt:message key="button.delete" />
					</button>
				</div>
			</c:when>
		</c:choose>
		
		
		<spring:bind path="saleOrder.saleOrderItems">
			<div id="tableDiv" class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				
			</div>
		</spring:bind>
		
		<c:choose>
			<c:when test="${saleOrder.status == 'A' }">
				<div class="well">
					<div class="row-fluid">
						<div class="span6">
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
						</div>
						<div class="span6">
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
						</div>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="well">
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<appfuse:label styleClass="control-label" key="saleOrder.paymentType" />
								<div class="controls">
									<span class="input-medium uneditable-input"><tags:labelValue value="${saleOrder.paymentType}" list="${paymentTypeList}"/> </span>
								</div>
							</div>
						</div>
						<div class="span6">
							<div class="control-group">
								<appfuse:label styleClass="control-label" key="saleOrder.deliveryDate" />
								<div class="controls">
									<span class="input-medium uneditable-input"><fmt:formatDate value="${saleOrder.deliveryDate}" pattern="dd/MM/yyyy HH:mm:ss" /></span>
								</div>
							</div>
						</div>
					</div>
					<c:if test="${saleOrder.status == 'C' }">
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<appfuse:label styleClass="control-label" key="saleOrder.cancelReason" />
								<div class="controls">
									<span class="input-xlarge uneditable-input"><c:out value="${saleOrder.cancelReason }"/> </span>
								</div>
							</div>
						</div>
					</div>
					</c:if>
				</div>
			</c:otherwise>
		</c:choose>

		<fieldset class="form-actions text-center">
			<button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
				<i class="icon-ok icon-white"></i>
				<fmt:message key="button.save" />
			</button>

			<c:if test="${saleOrder.id != null}">
				<c:choose>
					<c:when test="${saleOrder.paymentStatus != '3' }">
						<a class="btn btn-info" href="<c:url value='/saleReceipt?method=Add&from=list&saleOrderNo=${saleOrder.documentNumber.documentNo}'/>">
						<i class="icon-tag"></i>
						<fmt:formatNumber value="${saleOrder.totalPrice - saleOrder.paymentPaid }" pattern="#,##0.00"/>
						</a>
					</c:when>
				</c:choose>
				
				<c:choose>
					<c:when test="${saleOrder.status == 'A' }">
						<button type="button" class="btn" name="print" onclick="bCancel=true;printDialog('<c:url value="/saleOrder/download?id=${saleOrder.id}" />')">
							<i class="icon-print"></i>
							<fmt:message key="button.saleOrder.print" />
						</button>
						<button type="submit" class="btn" name="delete" onclick="bCancel=true;return validateCancel()">
							<i class="icon-trash"></i>
							<fmt:message key="button.delete" />
						</button>
						<button type="button" class="btn" name="deliveryBtn" onclick="bCancel=true;return delivery()">
							<i class="icon-tasks"></i>
							<fmt:message key="button.delivery" />
						</button>
					</c:when>
					<c:when test="${saleOrder.status == 'D' }">
						<button type="submit" class="btn disabled" name="delete" disabled="disabled">
							<i class="icon-trash"></i>
							<fmt:message key="button.delete" />
						</button>
						<button type="button" class="btn disabled" name="deliveryBtn" disabled="disabled">
							<i class="icon-tasks"></i>
							<fmt:message key="button.delivery" />
						</button>
					</c:when>
				</c:choose>
			</c:if>
			

			<button type="submit" class="btn" name="cancel" onclick="bCancel=true">
				<i class="icon-remove"></i>
				<fmt:message key="button.cancel" />
			</button>
		</fieldset>
	</form:form>

</div>

<div class="modal hide fade" id="cancelReasonDialog">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3><fmt:message key="saleOrder.cancelReason"/></h3>
	</div>
	<div class="modal-body">
		<div class="control-group">
		<div class="controls">
			<textarea class="input-xlarge" name="cancelReasonArea" id="cancelReasonArea"></textarea>
		</div>
		</div>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn" data-dismiss="modal"><fmt:message key="button.close"/></a> <a class="btn btn-primary" onclick="cancel()"><fmt:message key="button.save"/></a>
	</div>
</div>

<script type="text/javascript">
<!-- This is here so we can exclude the selectAll call when roles is hidden -->
	function onFormSubmit(theForm) {
		return validateSaleOrder(theForm);
	}
	
	function validateCancel() {
		$('#cancelReasonDialog').show(function () {
			$(this).find('.control-group').removeClass('error');
    			$(this).find('.help-inline').remove();
		});
		$('#cancelReasonDialog').modal();
		
		return false;
	}
	
	function cancel() {
		var form = document.forms['saleOrder'];
		//since cancelReasonArea is not in form, using get(0) to convert to read object
		if (checkRequired($('#cancelReasonArea').get(0), '<tags:validateMessage errorKey="errors.required" field="saleOrder.cancelReason"/>')) {
			form["cancelReason"].value = $('#cancelReasonArea').val();
			form['action'].value="delete";
			form.submit();
		}
	}
	
	function delivery() {
		var form = document.forms['saleOrder'];
		form['action'].value="delivery";
		form.submit();
	}
	
	<c:if test="${saleOrder.status == 'A' }">
	$(function() {
		var st = $('#deliveryDateDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
	});
	</c:if>
	
	
	<c:if test="${saleOrder.documentNumber.documentNo == null }">
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
	</c:if>

	
	
	$(document).ready(function () {
		$('#tableDiv').ajaxDisplaytag({
			url : '${ctx}/saleOrder/displayTable',
			loadedHandler : function () {
				var self = this;
				
				var fnLoad = function() {
					self.pageLoadedHandler.call(self);
				};
				
				var getLink = function() {
					return self.getLink();	
				};
				<c:if test="${saleOrder.documentNumber.documentNo == null }">
				//register popup
				$('input[name="catalog.name"]').each(function(i) {
					$(this).lookup({
						type: 'catalog',
						displayProperty: function (json) {
							return json.name;
						},
						selectProperty: function (json) {
							return json.name;
						},
						btnSearchCondition: function () {
							return {code: $('input[name="catalog.code"]:eq(' + i + ')').val()};	
						},
						handler: function (json) {
							if (json) {
								$('#image' + i).prop('src', "<c:url value='/img/thumbnail/catalog/'/>" + json.id + "?t=100");
								$('input[name="pricePerUnit"]:eq(' + i + ')').val(json.finalPrice);
								$('input[name="catalog.code"]:eq(' + i + ')').val(json.code);
								//index of whole list
								var index = $('input[name="checkbox"]:eq(' + i + ')').val();
								var url = '${ctx}/saleOrder/updateRow' + getLink();
								$.post(url, {'index' : index, 'qty' : 1, 'pricePerUnit' : json.finalPrice, 'catalog.code' : json.code}, function(data) {
									$('#tableDiv').html(data);
									fnLoad();
								});
							} else {
								$('#image' + i).prop('src', "<c:url value='/img/thumbnail/catalog/'/>" + 0 + "?t=100");
								$('input[name="pricePerUnit"]:eq(' + i + ')').val('');
								$('input[name="catalog.code"]:eq(' + i + ')').val('');
								//index of whole list
								var index = $('input[name="checkbox"]:eq(' + i + ')').val();
								var url = '${ctx}/saleOrder/updateRow' + getLink();
								$.post(url, {'index' : index, 'qty' : 1, 'pricePerUnit' : '', 'catalog.code' : ''}, function(data) {
									$('#tableDiv').html(data);
									fnLoad();
								});
							}
						}
					});
				});
				
				$('#saleOrderItemForm input[name="chkSelectAll"]').click(function() {
					toggleCheckAll(this, $('#saleOrderItemForm input[name="checkbox"]'));
				});
				
				//register button add & delete
				$('#addDetailBtn').off();
				$('#addDetailBtn').on('click', function () {
					$.post('${ctx}/saleOrder/addRow?ajax=true', $.extend({}, getLink()), function(data) {
						$('#tableDiv').html(data);
						fnLoad();
					});
				});
				$('#deleteDetailBtn').off();
				$('#deleteDetailBtn').on('click', function () {
					
					if (!hasChecked($('#saleOrderItemForm input[name="checkbox"]'))) {
						alert('<fmt:message key="global.errorNoCheckboxSelectForDelete"/>');
						return false;
					}
					confirmMessage('<fmt:message key="global.confirm.delete"/>', function(result) {
						if (result) {
							var url = '${ctx}/saleOrder/deleteRow' + getLink();
							$.post(url, $('#saleOrderItemForm').serialize(), function(data) {
								$('#tableDiv').html(data);
								fnLoad();
							});			
						}
					});
				});
				
				//calculate qty
				$('#saleOrderItemForm input[name="qty"], #saleOrderItemForm input[name="pricePerUnit"]').each(function(i) {
					$(this).on('focusout', function() {
						var url = '${ctx}/saleOrder/updateRow' + getLink();
						$.post(url, $('#saleOrderItemForm').serialize(), function(data) {
							$('#tableDiv').html(data);
							fnLoad();
						});
					});
				});
				
				</c:if>
			}
		});
		
	});
</script>
<v:javascript formName="saleOrder" staticJavascript="false" />
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>