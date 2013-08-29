<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="jobOrder.title" /></title>
<meta name="menu" content="JobOrderMenu" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/scripts/datepicker/css/bootstrap-datetimepicker.min.css'/>" />
<script type="text/javascript" src="<c:url value='/scripts/datepicker/js/bootstrap-datetimepicker.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery-lookup.js'/>"></script>


</head>
<div class="span2">
	<h2>
		<fmt:message key="jobOrder.heading" />
	</h2>
	<p>
		<fmt:message key="jobOrder.message" />
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

	<form:form commandName="jobOrder" method="post" action="jobOrder" onsubmit="return onFormSubmit(this)" id="jobOrder" >
		<form:hidden path="id" />
		<input type="hidden" name="from" value="<c:out value="${param.from}"/>" />
		<input type="hidden" name="action"/>

		<div class="well row">
			<div class="row-fluid">
				<div class="control-group">
					<appfuse:label styleClass="control-label" key="jobOrder.id" />
					<div class="controls">
						<span class="input-medium uneditable-input"><c:out value="${jobOrder.id}" /></span>
						<form:hidden path="id" />
					</div>
				</div>
			</div>
			
			<div class="row-fluid">
				<spring:bind path="jobOrder.employee.id">
					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
						<appfuse:label styleClass="control-label" key="jobOrder.employee.id" />
						<div class="controls">
							<form:input path="employee.fullname" id="employee.fullname"  cssClass="input-xlarge" maxlength="255" autocomplete="off"/>
							<form:errors path="employee.id" cssClass="help-inline" />
						</div>
					</div>
				</spring:bind>
			</div>
			
			<div class="row-fluid">
				<span class="span6">
					<spring:bind path="jobOrder.startDate">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<appfuse:label styleClass="control-label" key="jobOrder.startDate" />
							<div class="controls">
								<div class="input-append date" id="startDateDatepicker">
									<form:input path="startDate" id="startDate" cssClass="input-medium" maxlength="50" /><span class="add-on"><i class="icon-th"></i></span>
								</div>
								<form:errors path="startDate" cssClass="help-inline" />
							</div>
						</div>
					</spring:bind>
				</span>
				<span class="span6">
					<spring:bind path="jobOrder.endDate">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<appfuse:label styleClass="control-label" key="jobOrder.endDate" />
							<div class="controls">
								<div class="input-append date" id="endDateDatepicker">
									<form:input path="endDate" id="endDate" cssClass="input-medium" maxlength="50" /><span class="add-on"><i class="icon-th"></i></span>
								</div>
								<form:errors path="endDate" cssClass="help-inline" />
							</div>
						</div>
					</spring:bind>
				</span>
			</div>
			<div class="row-fluid">
				<span class="span6">
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
				</span>
				<span class="span6">
					<spring:bind path="jobOrder.actualEndDate">
						<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
							<appfuse:label styleClass="control-label" key="jobOrder.actualEndDate" />
							<div class="controls">
								<div class="input-append date" id="actualEndDateDatepicker">
									<form:input path="actualEndDate" id="actualEndDate" cssClass="input-medium" maxlength="50" /><span class="add-on"><i class="icon-th"></i></span>
								</div>
								<form:errors path="actualEndDate" cssClass="help-inline" />
							</div>
						</div>
					</spring:bind>
				</span>
			</div>
		</div>
		
		
		<div class="row-fluid">
			<div class="span6">
				<img id="image" src="<c:url value='/img/thumbnail/catalog/${jobOrder.saleOrderItem.catalog.id}?t=400'/>" class="img-polaroid"/>
			</div>
			<div class="span6">
				<display:table name="catalogItemList" cellspacing="0" cellpadding="0" requestURI="" id="catalogItem" class="table table-condensed table-striped table-hover table-bordered">
					<display:column titleKey="catalogItem.invItem.code" sortable="true" class="span3">
						<c:out value="${catalogItem.invItem.code}"/>
					</display:column>
					<display:column property="invItem.name" escapeXml="true" sortable="true" titleKey="catalogItem.invItem.name" sortName="invItem.name" />
					<display:column property="qty" sortable="true" titleKey="catalogItem.qty" sortName="qty" format="{0,number,#,##0.##}"/>
				</display:table>
			</div>
		</div>
		
		
		<fieldset class="form-actions text-center">
			<button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
				<i class="icon-ok icon-white"></i>
				<fmt:message key="button.save" />
			</button>

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
		return validateJobOrder(theForm);
	}
	
	$(function() {
		$('#startDateDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
		
		$('#endDateDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
		
		$('#actualEndDateDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
	});
	
	
	$(document).ready(function () {
		$('input[name="employee.fullname"]').lookup({
			type: 'employee',
			displayProperty: function (json) {
				return json.fullname;
			},
			selectProperty: function (json) {
				return json.fullname;
			},
			btnSearchCondition: function () {
				return {id: $('input[name="employee.id"]').val()};	
			},
			handler: function (json) {
				if (json) {
					$('input[name="employee.id"]').val(json.id);
				} else {
					$('input[name="employee.fullname"]').val('');
					$('input[name="employee.id"]').val('');
				}
			}
		});
	});
	
</script>
<v:javascript formName="jobOrder" staticJavascript="false" />
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>