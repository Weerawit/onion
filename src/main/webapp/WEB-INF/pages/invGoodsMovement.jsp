<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="invGoodsMovement.title" /></title>
<meta name="menu" content="InvMenu" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/scripts/datepicker/css/bootstrap-datetimepicker.min.css'/>" />
<script type="text/javascript" src="<c:url value='/scripts/datepicker/js/bootstrap-datetimepicker.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery-lookup.js'/>"></script>
</head>
<div class="span2">
	<h2>
		<fmt:message key="invGoodsMovement.heading" />
	</h2>
	<p>
		<fmt:message key="invGoodsMovement.message" />
	</p>
</div>
<div class="span7">
	<spring:bind path="invGoodsMovement.*">
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

	<form:form commandName="invGoodsMovement" method="post" action="${ctx}/invGoodsMovement/save" onsubmit="return onFormSubmit(this)" id="invGoodsMovement" cssClass="well form-horizontal">
		<form:hidden path="id" />
		<input type="hidden" name="from" value="<c:out value="${param.from}"/>" />
		<input type="hidden" name="action"/>

<%-- 		<c:if test="${invGoodsMovement.id == null }"> --%>
<%-- 			<spring:bind path="invGoodsMovement.runningNo"> --%>
<%-- 				<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}"> --%>
<%-- 					<appfuse:label styleClass="control-label" key="invGoodsMovement.runningNo" /> --%>
<!-- 					<div class="controls"> -->
<%-- 						<form:input path="runningNo" id="runningNo" cssClass="input-medium" maxlength="50" /> --%>
<%-- 						<form:errors path="runningNo" cssClass="help-inline" /> --%>
<!-- 					</div> -->
<!-- 				</div> -->
<%-- 			</spring:bind> --%>
<%-- 		</c:if> --%>
		<c:choose>
			<c:when test="${invGoodsMovement.runningNo != null }">
				<%-- since we have runningNo, readonly --%>
				<div class="control-group">
					<appfuse:label styleClass="control-label" key="invGoodsMovement.runningNo" />
					<div class="controls">
						<span class="input-medium uneditable-input"><c:out value="${invGoodsMovement.runningNo}" /></span>
						<form:hidden path="runningNo"/>
					</div>
				</div>
				
				<div class="control-group">
					<appfuse:label styleClass="control-label" key="invGoodsMovement.owner" />
					<div class="controls">
						<span class="input-xlarge uneditable-input"><c:out value="${invGoodsMovement.owner}" /></span>
					</div>
				</div>
				
				<div class="control-group">
					<appfuse:label styleClass="control-label" key="invGoodsMovement.movementType" />
					<div class="controls">
						<span class="input-medium uneditable-input"><tags:labelValue value="${invGoodsMovement.movementType}" list="${movementTypeList}"/> </span>
					</div>
				</div>
				
				<div class="control-group">
					<appfuse:label styleClass="control-label" key="invGoodsMovement.movementDate" />
					<div class="controls">
						<span class="input-medium uneditable-input">
						<fmt:formatDate value="${invGoodsMovement.movementDate}" pattern="dd/MM/yyyy HH:mm:ss"/>
						</span>
					</div>
				</div>
				
				<div class="control-group">
					<appfuse:label styleClass="control-label" key="invGoodsMovement.memo" />
					<div class="controls">
						<textarea class="input-xlarge uneditable-input" readonly="readonly">
						<c:out value="${invGoodsMovement.memo}" />
						</textarea>
					</div>
				</div>
			
			</c:when>
			<c:otherwise>
				<%-- form input --%>
			
				<spring:bind path="invGoodsMovement.owner">
					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
						<appfuse:label styleClass="control-label" key="invGoodsMovement.owner" />
						<div class="controls">
							<form:input path="owner" id="owner" cssClass="input-xlarge" maxlength="255" autocomplete="off"/>
							<form:errors path="owner" cssClass="help-inline" />
						</div>
					</div>
				</spring:bind>
				
				<spring:bind path="invGoodsMovement.movementType">
					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
						<appfuse:label styleClass="control-label" key="invGoodsMovement.movementType" />
						<div class="controls">
							<form:select path="movementType">
								<form:option value=""></form:option>
								<form:options items="${movementTypeList}" itemLabel="label" itemValue="value"/>
							</form:select>
							<form:errors path="movementType" cssClass="help-inline" />
						</div>
					</div>
				</spring:bind>
				
				<spring:bind path="invGoodsMovement.movementDate">
					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
						<appfuse:label styleClass="control-label" key="invGoodsMovement.movementDate" />
						<div class="controls">
							<div class="input-append date" id="movementDateDatepicker">
								<form:input path="movementDate" id="movementDate" cssClass="input-medium" maxlength="50" /><span class="add-on"><i class="icon-th"></i></span>
							</div>
							<form:errors path="movementDate" cssClass="help-inline" />
						</div>
					</div>
				</spring:bind>
				
				<spring:bind path="invGoodsMovement.memo">
					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
						<appfuse:label styleClass="control-label" key="invGoodsMovement.memo" />
						<div class="controls">
							<form:textarea path="memo" id="memo" cssClass="input-xlarge"/>
							<form:errors path="memo" cssClass="help-inline" />
						</div>
					</div>
				</spring:bind>
			
			</c:otherwise>
		</c:choose>
		
		
		
<%-- 		<c:if test="${not empty invGoodsMovementItemList }"> --%>
<!-- 		<div class="control-group pull-right"> -->
<%-- 			<fmt:message key="label.showPagination" /> --%>
<!-- 			&nbsp; -->
<%-- 			<%=request.getAttribute("psSelect")%> --%>
<!-- 		</div> -->
<%-- 		</c:if>	 --%>
		
		<c:if test="${invGoodsMovement.runningNo == null}">
			<div id="actions">
			
				<button id="button.add" class="btn btn-primary" type="submit" onclick="bCancel=true;$('#invGoodsMovement').attr('action', '${ctx}/invGoodsMovement/addDetail');">
					<i class="icon-plus icon-white"></i>
					<fmt:message key="button.add" />
				</button>
				
				<button id="button.delete" class="btn" type="submit" onclick="bCancel=true;return validateDelete();">
					<i class="icon-trash"></i>
					<fmt:message key="button.delete" />
				</button>
				
			</div>
		</c:if>
		<display:table name="invGoodsMovementItemList" cellspacing="0" cellpadding="0" requestURI="" id="invGoodsMovementItem" class="table table-condensed table-striped table-hover table-bordered">
			<c:if test="${invGoodsMovement.runningNo == null }">
				<display:column title="<input type='checkbox' name='chkSelectAll' id='chkSelectAll'/>" headerClass="span1" class="span1">
					<input type="checkbox" id="checkbox" name="checkbox" value="<c:out value='${invGoodsMovementItem_rowNum - 1}'/>" />
				</display:column>
				<display:column titleKey="invGoodsMovementItem.invItem.code" sortable="true">
					<button class="btn btn-link" type="submit" onclick="bCancel=true;$('#invGoodsMovement').attr('action', '${ctx}/invGoodsMovement/editDetail?&id=${ invGoodsMovementItem_rowNum - 1}');">
						<c:out value="${invGoodsMovementItem.invItem.code}"/>
					</button>
				</display:column>
			</c:if>
			<c:if test="${invGoodsMovement.runningNo != null }">
				<display:column titleKey="invGoodsMovementItem.invItem.code" sortable="true">
					<a class="btn btn-link" href="${ctx}/invGoodsMovementItem?from=list&id=${invGoodsMovementItem_rowNum - 1}"><c:out value="${invGoodsMovementItem.invItem.code}"/></a>
				</display:column>
			</c:if>
			<display:column property="invItem.name" escapeXml="true" sortable="true" titleKey="invGoodsMovementItem.invItem.name" sortName="invItem.name" />
			<display:column property="qty" sortable="true" titleKey="invGoodsMovementItem.qty" sortName="qty" format="{0,number,#,##0.##}"/>
			<display:setProperty name="export.csv" value="true"></display:setProperty>
			<display:setProperty name="export.excel" value="true"></display:setProperty>
			<display:setProperty name="export.xml" value="false"></display:setProperty>
			<display:setProperty name="export.pdf" value="true"></display:setProperty>
			<display:setProperty name="export.excel.filename" value="InvGoodsMovement.xls" />
			<display:setProperty name="export.csv.filename" value="InvGoodsMovement.csv" />
			<display:setProperty name="export.pdf.filename" value="InvGoodsMovement.pdf" />
		</display:table>
		

		<fieldset class="form-actions">
			<c:choose>
				<c:when test="${invGoodsMovement.runningNo == null}">
					<button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
						<i class="icon-ok icon-white"></i>
						<fmt:message key="button.save" />
					</button>
					
					<button type="submit" class="btn btn-warning" name="stockSave" onclick="bCancel=false; return saveToStock()">
						<i class="icon-ok icon-white"></i>
						<fmt:message key="button.saveToStock" />
					</button>
					
					<c:if test="${invGoodsMovement.id != null}">
						<button type="submit" class="btn" name="delete" onclick="bCancel=true;return deleteThis()">
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
				<i class="icon-remove"></i>
				<fmt:message key="button.cancel" />
			</button>
		</fieldset>
	</form:form>
</div>
<script type="text/javascript">
<!-- This is here so we can exclude the selectAll call when roles is hidden -->
	function onFormSubmit(theForm) {	
		return validateInvGoodsMovement(theForm);
	}
	<c:if  test="${invGoodsMovement.runningNo == null }">
	$(function() {

		var st = $('#movementDateDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
	});
	
	$(document).ready(function () {
		$('input[name="owner"]').lookup({
			type: 'employee',
			displayProperty: function (json) {
				return json.firstName + ' ' + json.lastName;
			},
			selectProperty: function (json) {
				return json.firstName + ' ' + json.lastName;
			},
			btnSearchCondition: function () {
				return {firstName: $('input[name="owner"]').val()};	
			},
			handler: function (json) {
				if (json) {
				} else {
					$('input[name="owner"]').val('');
				}
			}
		});
	});
	</c:if>
	
	function saveToStock() {
		var form = document.forms[0];
		 confirmMessage('<fmt:message key="invGoodsMovement.confirm.saveToStock"/>', function(result) {
			 if (result) {
				 form['action'].value="saveToStock";
				 form.submit();
			 }
		 });
		 return false;
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
	
	function validateDelete() {
		var form = document.forms['invGoodsMovement'];
		if (!hasChecked(form.checkbox)) {
			alert('<fmt:message key="global.errorNoCheckboxSelectForDelete"/>');
			return false;
		}
		confirmMessage('<fmt:message key="global.confirm.delete"/>', function(result) {
			if (result) {
				$('#invGoodsMovement').attr('action', '${ctx}/invGoodsMovement/deleteDetail');
				form.submit();
			}
		});
		return false;
	}
	<c:if test="${not empty invGoodsMovementItemList}">
	$(document).ready(function() {
		$("#chkSelectAll").click(function() {
			toggleCheckAll(this, document.forms['invGoodsMovement'].checkbox);
		});
	});
	</c:if>
	
</script>
<v:javascript formName="invGoodsMovement" staticJavascript="false" />
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>