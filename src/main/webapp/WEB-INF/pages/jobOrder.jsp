<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="jobOrder.title" /></title>
<meta name="menu" content="JobOrderMenu" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/scripts/datepicker/css/bootstrap-datetimepicker.min.css'/>" />
<script type="text/javascript" src="<c:url value='/scripts/datepicker/js/bootstrap-datetimepicker.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery-lookup.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery-ajaxDisplaytag.js'/>"></script>

</head>
<div class="span2">
	<h2>
		<fmt:message key="jobOrder.heading" />
	</h2>
	<p>
		<fmt:message key="jobOrder.documentNumber.documentNo" />
	</p>
	<p>
	<c:out value="${jobOrder.documentNumber.documentNo}" />
	</p>
</div>
<div class="span10">
	<spring:bind path="jobOrder.*">
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

	<form:form commandName="jobOrder" method="post" action="jobOrder" onsubmit="return onFormSubmit(this)" id="jobOrder">
		<input type="hidden" name="from" value="<c:out value="${param.from}"/>" />
		<form:hidden path="documentNumber.documentNo" />
		<input type="hidden" name="cancelReason"/>
		<input type="hidden" name="action"/>
		<form:hidden path="id" />
		<div class="well row">
			<c:choose>
				<c:when test="${jobOrder.id != null }">
					<div class="row-fluid">
						 <div class="span6">
							<div class="control-group">
								<appfuse:label styleClass="control-label" key="jobOrder.catalog.name" />
								<div class="controls">
									<form:hidden path="catalog.code"/>
									<form:hidden path="catalog.name"/>
									<span class="input-xlarge uneditable-input"><c:out value="${jobOrder.catalog.name}" /></span>
								</div>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="row-fluid">
						<%--
						<div class="span6">
							<div class="control-group">
								<appfuse:label styleClass="control-label" key="jobOrder.runningNo" />
								<div class="controls">
									<span class="input-medium uneditable-input"><c:out value="${jobOrder.runningNo}" /></span>
									<form:hidden path="runningNo" />
								</div>
							</div>
						</div>
						 --%>
						 <div class="span6">
							<spring:bind path="jobOrder.catalog.name">
								<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
									<appfuse:label styleClass="control-label" key="jobOrder.catalog.name" />
									<div class="controls">
										<form:hidden path="catalog.code"/>
										<form:input path="catalog.name" name="catalog.name" cssClass="input-xlarge" maxlength="255" autocomplete="off" />
										<form:errors path="catalog.name" cssClass="help-inline" />
									</div>
								</div>
							</spring:bind>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
			

			<div class="row-fluid">
				<div class="span6">
					<spring:bind path="jobOrder.employee.id">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<appfuse:label styleClass="control-label" key="jobOrder.employee.id" />
							<div class="controls">
								<form:input path="employee.fullname" id="employee.fullname" cssClass="input-xlarge" maxlength="255" autocomplete="off" />
								<form:hidden path="employee.id" />
								<form:errors path="employee.id" cssClass="help-inline" />
							</div>
						</div>
					</spring:bind>
				</div>
				<div class="span6">
					<spring:bind path="jobOrder.saleOrder.documentNumber.documentNo">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<appfuse:label styleClass="control-label" key="jobOrder.saleOrder.documentNumber.documentNo" />
							<div class="controls">
								<form:input path="saleOrder.documentNumber.documentNo" name="saleOrder.documentNumber.documentNo" cssClass="input-xlarge" maxlength="255" autocomplete="off" />
								<form:errors path="saleOrder.documentNumber.documentNo" cssClass="help-inline" />
							</div>
						</div>
					</spring:bind>
				</div>
			</div>
			<div class="row-fluid">
				<div class="span6">
					<spring:bind path="jobOrder.qty">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<appfuse:label styleClass="control-label" key="jobOrder.qty" />
							<div class="controls">
								<form:input path="qty" name="qty" cssClass="input-small" maxlength="255" autocomplete="off" />
								<form:errors path="qty" cssClass="help-inline" />
							</div>
						</div>
					</spring:bind>
				</div>
				<div class="span6">
					<spring:bind path="jobOrder.cost">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<appfuse:label styleClass="control-label" key="jobOrder.cost" />
							<div class="controls">
								<form:input path="cost" name="cost" cssClass="input-small" maxlength="255" autocomplete="off" />
								<form:errors path="cost" cssClass="help-inline" />
							</div>
						</div>
					</spring:bind>
				</div>
			</div>

			<div class="row-fluid">
				<div class="span6">
					<spring:bind path="jobOrder.startDate">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<appfuse:label styleClass="control-label" key="jobOrder.startDate" />
							<div class="controls">
								<div class="input-append date" id="startDateDatepicker">
									<form:input path="startDate" id="startDate" cssClass="input-medium" maxlength="50" />
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
								<form:errors path="startDate" cssClass="help-inline" />
							</div>
						</div>
					</spring:bind>
				</div>
				<div class="span6">
					<spring:bind path="jobOrder.targetEndDate">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<appfuse:label styleClass="control-label" key="jobOrder.targetEndDate" />
							<div class="controls">
								<div class="input-append date" id="targetEndDateDatepicker">
									<form:input path="targetEndDate" id="targetEndDate" cssClass="input-medium" maxlength="50" />
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
								<form:errors path="targetEndDate" cssClass="help-inline" />
							</div>
						</div>
					</spring:bind>
				</div>
			</div>
			<div class="row-fluid">
			<%--
				<div class="span6">
					<spring:bind path="jobOrder.status">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<appfuse:label styleClass="control-label" key="jobOrder.status" />
							<div class="controls">
								<form:select path="status">
									<form:option value=""></form:option>
									<form:options items="${jobOrderStatusList}" itemLabel="label" itemValue="value" />
								</form:select>
								<form:errors path="status" cssClass="help-inline" />
							</div>
						</div>
					</spring:bind>
				</div>
			 --%>
				<div class="span6">
					<spring:bind path="jobOrder.actualEndDate">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<appfuse:label styleClass="control-label" key="jobOrder.actualEndDate" />
							<div class="controls">
								<div class="input-append date" id="actualEndDateDatepicker">
									<form:input path="actualEndDate" id="actualEndDate" cssClass="input-medium" maxlength="50" />
									<span class="add-on"><i class="icon-th"></i></span>
								</div>
								<form:errors path="actualEndDate" cssClass="help-inline" />
							</div>
						</div>
					</spring:bind>
				</div>
			</div>
			<c:if test="${jobOrder.status == 'C' }">
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group">
							<appfuse:label styleClass="control-label" key="jobOrder.cancelReason" />
							<div class="controls">
								<span class="input-xlarge uneditable-input"><c:out value="${jobOrder.cancelReason }"/> </span>
							</div>
						</div>
					</div>
				</div>
			</c:if>
		</div>


		<div class="row-fluid">
			<div class="media">
				<img id="catalogImage" src="<c:url value='/img/thumbnail/catalog/${jobOrder.catalog.id}?t=400'/>" class="img-polaroid pull-left" />
				<div class="media-body">
					<h4 class="media-heading">
						<fmt:message key="jobOrder.material" />
					</h4>
					<div id="tableDiv">
				
					</div>
				</div>
			</div>
		</div>


		<fieldset class="form-actions text-center">
			<button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
				<i class="icon-ok icon-white"></i>
				<fmt:message key="button.save" />
			</button>
			
			<c:choose>
				<c:when test="${jobOrder.status != 'C' && jobOrder.status != 'DONE' }">
					<button type="submit" class="btn" name="delete" onclick="bCancel=true;return validateCancel()">
						<i class="icon-trash"></i>
						<fmt:message key="button.delete" />
					</button>
				</c:when>
			</c:choose>

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
		<h3><fmt:message key="jobOrder.cancelReason"/></h3>
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
		return validateJobOrder(theForm);
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
		var form = document.forms['jobOrder'];
		//since cancelReasonArea is not in form, using get(0) to convert to read object
		if (checkRequired($('#cancelReasonArea').get(0), '<tags:validateMessage errorKey="errors.required" field="saleOrder.cancelReason"/>')) {
			form["cancelReason"].value = $('#cancelReasonArea').val();
			form['action'].value="delete";
			form.submit();
		}
	}
	
	$(function() {
		$('#startDateDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});

		$('#targetEndDateDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});

		$('#actualEndDateDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
	});

	$(document).ready(function() {
		$('#tableDiv').ajaxDisplaytag({
			url : '${ctx}/jobOrder/displayTable',
			params : {'catalog.code' : '<c:out value="${jobOrder.catalog.code}"/>'}
		});
		
		$('input[name="employee.fullname"]').lookup({
			type : 'employee',
			displayProperty : function(json) {
				return json.fullname;
			},
			selectProperty : function(json) {
				return json.fullname;
			},
			btnSearchCondition : function() {
				return {
					id : $('input[name="employee.id"]').val()
				};
			},
			handler : function(json) {
				if (json) {
					$('input[name="employee.id"]').val(json.id);
				} else {
					$('input[name="employee.fullname"]').val('');
					$('input[name="employee.id"]').val('');
				}
			}
		});
		
		$('input[name="saleOrder.documentNumber.documentNo"]').lookup({
			type : 'saleOrder',
			displayProperty : function(json) {
				return json.saleOrderNo;
			},
			selectProperty : function(json) {
				return json.saleOrderNo;
			},
			btnSearchCondition : function() {
				return {
					'documentNumber.documentNo' : $('input[name="saleOrder.documentNumber.documentNo"]').val()
				};
			},
			handler : function(json) {
				if (json) {
					$('input[name="saleOrder.documentNumber.documentNo"]').val(json.saleOrderNo);
				} else {
					$('input[name="saleOrder.documentNumber.documentNo"]').val('');
				}
			}
		});
		
		<c:if test="${jobOrder.id == null }">
		$('input[name="catalog.name"]').lookup({
			type : 'catalog',
			displayProperty : function(json) {
				return json.name;
			},
			selectProperty : function(json) {
				return json.name;
			},
			btnSearchCondition : function() {
				return {
					code : $('input[name="catalog.code"]').val()
				};
			},
			handler : function(json) {
				if (json) {
					$('#catalogImage').prop('src', "<c:url value='/img/thumbnail/catalog/'/>" + json.id + "?t=400");
					$('input[name="catalog.code"]').val(json.code);
					
					$('#tableDiv').ajaxDisplaytag({
						url : '${ctx}/jobOrder/displayTable',
						params : {'catalog.code' : json.code}
					});

				} else {
					$('#catalogImage').prop('src', "<c:url value='/img/thumbnail/catalog/'/>" + 0 + "?t=400");
					$('input[name="catalog.code"]').val('');
					
					$('#tableDiv').ajaxDisplaytag({
						url : '${ctx}/jobOrder/displayTable',
						params : {'catalog.code' : ''}
					});
				}
			}
		});
		</c:if>
	});
</script>
<v:javascript formName="jobOrder" staticJavascript="false" />
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>