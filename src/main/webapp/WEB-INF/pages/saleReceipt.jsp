<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="saleReceipt.title" /></title>
<meta name="menu" content="SaleOrderMenu" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/scripts/datepicker/css/bootstrap-datetimepicker.min.css'/>" />
<script type="text/javascript" src="<c:url value='/scripts/datepicker/js/bootstrap-datetimepicker.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery-lookup.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery-ajaxDisplaytag.js'/>"></script>
</head>
<div class="span2">
	<h2>
		<fmt:message key="saleReceipt.heading" />
	</h2>
	<p>
		<fmt:message key="saleReceipt.receiptNo" />
	</p>
	<p>
	<c:out value="${saleReceipt.receiptNo}" />
	</p>
</div>
<div class="span10">
	<spring:bind path="saleReceipt.*">
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

	<form:form commandName="saleReceipt" method="post" action="saleReceipt" onsubmit="return onFormSubmit(this)" id="saleReceipt">
		<form:hidden path="id" />
		<input type="hidden" name="from" value="<c:out value="${param.from}"/>" />
		<form:hidden path="receiptType" />
		<form:hidden path="receiptNo" />
		<input type="hidden" name="cancelReason"/>
		<input type="hidden" name="action"/>
		<%--
		<spring:bind path="saleReceipt.receiptType">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="saleReceipt.receiptType" />
				<div class="controls">
					<form:select path="receiptType">
						<form:option value=""></form:option>
						<form:options items="${receiptTypeList}" itemLabel="label" itemValue="value" />
					</form:select>
					<form:errors path="receiptType" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		

		<c:if test="${saleReceipt.id != null }">
			<div class="control-group">
				<appfuse:label styleClass="control-label" key="saleReceipt.receiptNo" />
				<div class="controls">
					<span class="input-medium uneditable-input"><c:out value="${saleReceipt.receiptNo}" /></span>
					<form:hidden path="receiptNo" />
				</div>
			</div>
		</c:if>
		 --%>
		<div class="well">
			<c:choose>
				<c:when test="${saleReceipt.id != null }">
					<div class="control-group">
						<appfuse:label styleClass="control-label" key="saleReceipt.saleOrder.saleOrderNo" />
						<div class="controls">
							<span class="input-xlarge uneditable-input"><c:out value="${saleReceipt.saleOrder.saleOrderNo}" /></span>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<spring:bind path="saleReceipt.saleOrder.saleOrderNo">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<appfuse:label styleClass="control-label" key="saleReceipt.saleOrder.saleOrderNo" />
							<div class="controls">
								<form:input path="saleOrder.saleOrderNo" name="saleOrder.saleOrderNo" cssClass="input-xlarge" maxlength="255" autocomplete="off" />
								<form:errors path="saleOrder.saleOrderNo" cssClass="help-inline" />
							</div>
						</div>
					</spring:bind>
				</c:otherwise>
			</c:choose>
		
			<fieldset>
				<legend>
					<fmt:message key="saleReceipt.saleOrder.customer" />
				</legend>
				<div class="row-fluid">
					<label id="customerName"><c:out value="${saleReceipt.saleOrder.customer.name }" /></label>
				</div>
				<div class="row-fluid">
					<fieldset class="span6">
						<legend>
							<appfuse:label styleClass="control-label" key="saleReceipt.saleOrder.customer.contactName" />
						</legend>
						<label id="contactName"><c:out value="${saleReceipt.saleOrder.customer.contactName }" /></label>
					</fieldset>
					<fieldset class="span6">
						<legend>
							<appfuse:label styleClass="control-label" key="saleReceipt.saleOrder.customer.contactTel" />
						</legend>
						<label id="contactTel"><c:out value="${saleReceipt.saleOrder.customer.contactTel }" /></label>
					</fieldset>
				</div>
				<div class="row-fluid">
					<fieldset class="span6">
						<legend>
							<appfuse:label styleClass="control-label" key="saleReceipt.saleOrder.customer.billingAddress" />
						</legend>
						<label id="billingAddress"><c:out value="${saleReceipt.saleOrder.customer.billingAddress }" /></label>
					</fieldset>
					<fieldset class="span6">
						<legend>
							<appfuse:label styleClass="control-label" key="saleReceipt.saleOrder.customer.shipingAddress" />
						</legend>
						<label id="shipingAddress"><c:out value="${saleReceipt.saleOrder.customer.shipingAddress }" /></label>
					</fieldset>
				</div>
			</fieldset>
		</div>
		
		<fieldset>
			<legend><fmt:message key="saleReceipt.paymentHistory"/> </legend>
			<div id="tableDiv">
					
			</div>
		</fieldset>

		<div class="well form-horizontal">
			<div class="tabbable">
				<ul class="nav nav-tabs" id="receiptTypeTab">
					<li><a href="#tab1" data-toggle="tab"><fmt:message key="receiptType.1" /></a></li>
					<li><a href="#tab2" data-toggle="tab"><fmt:message key="receiptType.2" /></a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="tab1">
						
					</div>
					<div class="tab-pane" id="tab2">
						<c:choose>
							<c:when test="${saleReceipt.id != null }">
								<div class="control-group">
									<appfuse:label styleClass="control-label" key="saleReceipt.chequeNo" />
									<div class="controls">
										<span class="input-medium uneditable-input"><c:out value="${saleReceipt.chequeNo}" /></span>
									</div>
								</div>
								<div class="control-group">
									<appfuse:label styleClass="control-label" key="saleReceipt.chequeBank" />
									<div class="controls">
										<span class="input-medium uneditable-input"><c:out value="${saleReceipt.chequeBank}" /></span>
									</div>
								</div>
								<div class="control-group">
									<appfuse:label styleClass="control-label" key="saleReceipt.chequeDate" />
									<div class="controls">
										<span class="input-medium uneditable-input"><fmt:formatDate value="${saleReceipt.chequeDate}" pattern="dd/MM/yyyy HH:mm:ss"/></span>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<spring:bind path="saleReceipt.chequeNo">
									<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
										<appfuse:label styleClass="control-label" key="saleReceipt.chequeNo" />
										<div class="controls">
											<form:input path="chequeNo" id="chequeNo" cssClass="input-medium"  maxlength="50"/>
											<form:errors path="chequeNo" cssClass="help-inline" />
										</div>
									</div>
								</spring:bind>
								<spring:bind path="saleReceipt.chequeBank">
									<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
										<appfuse:label styleClass="control-label" key="saleReceipt.chequeBank" />
										<div class="controls">
											<form:input path="chequeBank" id="chequeBank" cssClass="input-medium" maxlength="50"/>
											<form:errors path="chequeBank" cssClass="help-inline" />
										</div>
									</div>
								</spring:bind>
								<spring:bind path="saleReceipt.chequeDate">
									<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
										<appfuse:label styleClass="control-label" key="saleReceipt.chequeDate" />
										<div class="controls">
											<div class="input-append date" id="chequeDateDatepicker">
												<form:input path="chequeDate" id="chequeDate" cssClass="input-medium" maxlength="50" />
												<span class="add-on"><i class="icon-th"></i></span>
											</div>
											<form:errors path="chequeDate" cssClass="help-inline" />
										</div>
									</div>
								</spring:bind>
							</c:otherwise>
						</c:choose>	
					</div>
					
					<c:choose>
						<c:when test="${saleReceipt.id != null }">
							<div class="control-group">
								<appfuse:label styleClass="control-label" key="saleReceipt.receiptAmount" />
								<div class="controls">
									<span class="input-medium uneditable-input"><fmt:formatNumber value="${saleReceipt.receiptAmount}" pattern="#,##0.00" /></span>
								</div>
							</div>
							<div class="control-group">
								<appfuse:label styleClass="control-label" key="saleReceipt.receiptDate" />
								<div class="controls">
									<span class="input-medium uneditable-input"><fmt:formatDate value="${saleReceipt.receiptDate }" pattern="dd/MM/yyyy HH:mm:ss"/></span>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<spring:bind path="saleReceipt.receiptAmount">
								<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
									<appfuse:label styleClass="control-label" key="saleReceipt.receiptAmount" />
									<div class="controls">
										<form:input path="receiptAmount" id="receiptAmount" cssClass="input-medium" />
										<form:errors path="receiptAmount" cssClass="help-inline" />
									</div>
								</div>
							</spring:bind>
							<spring:bind path="saleReceipt.receiptDate">
								<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
									<appfuse:label styleClass="control-label" key="saleReceipt.receiptDate" />
									<div class="controls">
										<div class="input-append date" id="receiptDateDatepicker">
											<form:input path="receiptDate" id="receiptDate" cssClass="input-medium" maxlength="50" />
											<span class="add-on"><i class="icon-th"></i></span>
										</div>
										<form:errors path="receiptDate" cssClass="help-inline" />
									</div>
								</div>
							</spring:bind>
						</c:otherwise>
					</c:choose>	
				</div>
			</div>
			<fieldset class="form-actions">
				<c:if test="${saleReceipt.id == null }">
					<button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
						<i class="icon-ok icon-white"></i>
						<fmt:message key="button.save" />
					</button>
				</c:if>

				<c:if test="${param.from == 'list' and param.method != 'Add'}">
					<button type="submit" class="btn" name="delete" onclick="bCancel=true;return validateCancel()">
						<i class="icon-remove"></i>
						<fmt:message key="button.cancel" />
					</button>
				</c:if>

				<button type="submit" class="btn" name="cancel" onclick="bCancel=true">
					<i class="icon-ok"></i>
					<fmt:message key="button.done" />
				</button>
			</fieldset>
		</div>
	</form:form>
</div>

<div class="modal hide fade" id="cancelReasonDialog">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3><fmt:message key="saleReceipt.cancelReason"/></h3>
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
	function validateCancel() {
		$('#cancelReasonDialog').show(function () {
			$(this).find('.control-group').removeClass('error');
    			$(this).find('.help-inline').remove();
		});
		$('#cancelReasonDialog').modal();
		
		return false;
	}
	function cancel() {
		var form = document.forms['saleReceipt'];
		//since cancelReasonArea is not in form, using get(0) to convert to read object
		if (checkRequired($('#cancelReasonArea').get(0), '<tags:validateMessage errorKey="errors.required" field="saleReceipt.cancelReason"/>')) {
			form["cancelReason"].value = $('#cancelReasonArea').val();
			form['action'].value="delete";
			form.submit();
		}
	}
	
	function onFormSubmit(theForm) {
		var valid = true;
		if ($('input[name="receiptType"]').val("2") && !bCancel) {
			valid = checkRequired(theForm['chequeNo'], '<tags:validateMessage errorKey="errors.required" field="saleReceipt.chequeNo"/>') && valid;
			valid = checkRequired(theForm['chequeBank'], '<tags:validateMessage errorKey="errors.required" field="saleReceipt.chequeBank"/>') && valid;
			valid = checkRequired(theForm['chequeDate'], '<tags:validateMessage errorKey="errors.required" field="saleReceipt.chequeDate"/>') && valid;
		}
		return validateSaleReceipt(theForm) && valid;
	}

	$(function() {
		$('#chequeDateDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
		
		$('#receiptDateDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
	});

	$(document).ready(function() {
		$('a[data-toggle="tab"]').on('shown', function(e) {
			var tab = $(e.target).attr('href');
			if (tab == '#tab1') {
				$('input[name="receiptType"]').val("1");
			} else {
				$('input[name="receiptType"]').val("2");
			}
		})
		
		<c:if test="${saleReceipt.receiptType == '1'}">
		$('#receiptTypeTab li:eq(0) a').tab('show');
		</c:if>
		
		<c:if test="${saleReceipt.receiptType == '2'}">
		$('#receiptTypeTab li:eq(1) a').tab('show');
		</c:if>


	});

	$(document).ready(function() {
		$('#tableDiv').ajaxDisplaytag({
			url : '${ctx}/saleReceipt/displayTable',
			params : {saleOrderNo : '${saleReceipt.saleOrder.saleOrderNo}'}
		});
		
		$('input[name="saleOrder.saleOrderNo"]').lookup({
			type : 'saleOrder',
			displayProperty : function(json) {
				return json.saleOrderNo;
			},
			selectProperty : function(json) {
				return json.saleOrderNo;
			},
			btnSearchCondition : function() {
				return {
					saleOrderNo : $('input[name="saleOrder.saleOrderNo"]').val()
				};
			},
			handler : function(json) {
				if (json) {
					$('input[name="saleOrder.saleOrderNo"]').val(json.saleOrderNo);
					$('#customerName').text(json.customer.name);
					$('#contactName').text(json.customer.contactName);
					$('#contactTel').text(json.customer.contactTel);
					$('#billingAddress').text(json.customer.billingAddress);
					$('#shipingAddress').text(json.customer.shipingAddress);
					
					var amount = 0;
					try {
						var paymentPaid = parseFloat(json.paymentPaid);
						var totalPrice = parseFloat(json.totalPrice);
						$('input[name="receiptAmount"]').val(totalPrice - paymentPaid);
					} catch (e) {
						
					}
					
					
					$('#tableDiv').ajaxDisplaytag({
						url : '${ctx}/saleReceipt/displayTable',
						params : {saleOrderNo : json.saleOrderNo}
					});
				} else {
					$('input[name="saleOrder.saleOrderNo"]').val('');
					$('#customerName').text('');
					$('#contactName').text('');
					$('#contactTel').text('');
					$('#billingAddress').text('');
					$('#shipingAddress').text('');
					$('input[name="receiptAmount"]').val(0);
					$('#tableDiv').ajaxDisplaytag({
						url : '${ctx}/saleReceipt/displayTable'
					});
				}
			}
		});
	});
</script>
<v:javascript formName="saleReceipt" staticJavascript="false" />
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>