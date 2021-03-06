<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="invGoodsReceipt.title" /></title>
<meta name="menu" content="InvMenu" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/scripts/datepicker/css/bootstrap-datetimepicker.min.css'/>" />
<script type="text/javascript" src="<c:url value='/scripts/datepicker/js/bootstrap-datetimepicker.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery-lookup.js'/>"></script>
</head>
<div class="span2">
	<h2>
		<fmt:message key="invGoodsReceipt.heading" />
	</h2>
	<p>
		<fmt:message key="invGoodsReceipt.message" />
	</p>
</div>
<div class="span10">
	<spring:bind path="invGoodsReceipt.*">
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

	<form:form commandName="invGoodsReceipt" method="post" action="${ctx}/invGoodsReceipt/save" onsubmit="return onFormSubmit(this)" id="invGoodsReceipt" cssClass="well form-horizontal">
		<form:hidden path="id" />
		<input type="hidden" name="from" value="<c:out value="${param.from}"/>" />
		<input type="hidden" name="action"/>
<%-- 		<c:if test="${invGoodsReceipt.id == null }"> --%>
<%-- 			<spring:bind path="invGoodsReceipt.documentNumber.documentNo"> --%>
<%-- 				<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}"> --%>
<%-- 					<appfuse:label styleClass="control-label" key="invGoodsReceipt.documentNumber.documentNo" /> --%>
<!-- 					<div class="controls"> -->
<%-- 						<form:input path="documentNumber.documentNo" id="documentNumber.documentNo" cssClass="input-medium" maxlength="50" /> --%>
<%-- 						<form:errors path="documentNumber.documentNo" cssClass="help-inline" /> --%>
<!-- 					</div> -->
<!-- 				</div> -->
<%-- 			</spring:bind> --%>
<%-- 		</c:if> --%>
		<c:choose>
			<c:when test="${invGoodsReceipt.documentNumber.documentNo != null }">
				<%-- since we have documentNumber.documentNo, readonly --%>
				<div class="control-group">
					<appfuse:label styleClass="control-label" key="invGoodsReceipt.documentNumber.documentNo" />
					<div class="controls">
						<span class="input-medium uneditable-input"><c:out value="${invGoodsReceipt.documentNumber.documentNo}" /></span>
						<form:hidden path="documentNumber.documentNo"/>
					</div>
				</div>
				
				<div class="control-group">
					<appfuse:label styleClass="control-label" key="invGoodsReceipt.supplier.code" />
					<div class="controls">
						<span class="input-medium uneditable-input"><c:out value="${invGoodsReceipt.supplier.name}" /></span>
					</div>
				</div>
				<div class="control-group">
					<appfuse:label styleClass="control-label" key="invGoodsReceipt.receiptDate" />
					<div class="controls">
						<span class="input-medium uneditable-input">
						<fmt:formatDate value="${invGoodsReceipt.receiptDate}" pattern="dd/MM/yyyy HH:mm:ss"/>
						</span>
					</div>
				</div>
				
				<div class="control-group">
					<appfuse:label styleClass="control-label" key="invGoodsReceipt.memo" />
					<div class="controls">
						<textarea class="input-xlarge uneditable-input" readonly="readonly">
						<c:out value="${invGoodsReceipt.memo}" />
						</textarea>
					</div>
				</div>
			
			</c:when>
			<c:otherwise>
				<%-- form input --%>
				
				<spring:bind path="invGoodsReceipt.receiptType">
					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
						<appfuse:label styleClass="control-label" key="invGoodsReceipt.receiptType" />
						<div class="controls">
							<form:select path="receiptType">
								<form:option value=""></form:option>
								<form:options items="${goodsReceiptTypeList}" itemLabel="label" itemValue="value"/>
							</form:select>
							<form:errors path="receiptType" cssClass="help-inline" />
						</div>
					</div>
				</spring:bind>
			
				<spring:bind path="invGoodsReceipt.supplier.code">
					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
						<appfuse:label styleClass="control-label" key="invGoodsReceipt.supplier.code" />
						<div class="controls">
							<form:hidden path="supplier.code" id="supplier.code" maxlength="20"/>
							<form:input path="supplier.name" id="supplier.name" cssClass="input-medium" maxlength="20" autocomplete="off"  data-lookup-key-value="${invGoodsReceipt.supplier.name}"/>
							<form:errors path="supplier.code" cssClass="help-inline" />
						</div>
					</div>
				</spring:bind>
				
				<spring:bind path="invGoodsReceipt.receiptDate">
					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
						<appfuse:label styleClass="control-label" key="invGoodsReceipt.receiptDate" />
						<div class="controls">
							<div class="input-append date" id="receiptDateDatepicker">
								<form:input path="receiptDate" id="receiptDate" cssClass="input-medium" maxlength="50" /><span class="add-on"><i class="icon-th"></i></span>
							</div>
							<form:errors path="receiptDate" cssClass="help-inline" />
						</div>
					</div>
				</spring:bind>
				
				<spring:bind path="invGoodsReceipt.memo">
					<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
						<appfuse:label styleClass="control-label" key="invGoodsReceipt.memo" />
						<div class="controls">
							<form:textarea path="memo" id="memo" cssClass="input-xlarge"/>
							<form:errors path="memo" cssClass="help-inline" />
						</div>
					</div>
				</spring:bind>
			
			</c:otherwise>
		</c:choose>
		
		
		
<%-- 		<c:if test="${not empty invGoodsReceiptItemList }"> --%>
<!-- 		<div class="control-group pull-right"> -->
<%-- 			<fmt:message key="label.showPagination" /> --%>
<!-- 			&nbsp; -->
<%-- 			<%=request.getAttribute("psSelect")%> --%>
<!-- 		</div> -->
<%-- 		</c:if>	 --%>
		
		<c:if test="${invGoodsReceipt.documentNumber.documentNo == null}">
			<div id="actions">
			
				<button id="button.add" class="btn btn-primary" type="submit" onclick="bCancel=true;$('#invGoodsReceipt').attr('action', '${ctx}/invGoodsReceipt/addDetail');">
					<i class="icon-plus icon-white"></i>
					<fmt:message key="button.add" />
				</button>
				
				<button id="button.delete" class="btn" type="submit" onclick="bCancel=true;return validateDelete();">
					<i class="icon-trash"></i>
					<fmt:message key="button.delete" />
				</button>
				
			</div>
		</c:if>
		<display:table name="invGoodsReceiptItemList" cellspacing="0" cellpadding="0" requestURI="" id="invGoodsReceiptItem" class="table table-condensed table-striped table-hover table-bordered">
			<c:if test="${invGoodsReceipt.documentNumber.documentNo == null }">
				<display:column title="<input type='checkbox' name='chkSelectAll' id='chkSelectAll'/>" class="span1" style="width: 10px" media="html">
					<input type="checkbox" id="checkbox" name="checkbox" value="<c:out value='${invGoodsReceiptItem_rowNum - 1}'/>" />
				</display:column>
				<display:column titleKey="invGoodsReceiptItem.invItem.code" sortable="true">
					<button class="btn btn-link" type="submit" onclick="bCancel=true;$('#invGoodsReceipt').attr('action', '${ctx}/invGoodsReceipt/editDetail?&id=${ invGoodsReceiptItem_rowNum - 1}');">
						<c:out value="${invGoodsReceiptItem.invItem.code}"/>
					</button>
				</display:column>
			</c:if>
			<c:if test="${invGoodsReceipt.documentNumber.documentNo != null }">
				<display:column titleKey="invGoodsReceiptItem.invItem.code" sortable="true">
					<a class="btn btn-link" href="${ctx}/invGoodsReceiptItem?from=list&id=${invGoodsReceiptItem_rowNum - 1}"><c:out value="${invGoodsReceiptItem.invItem.code}"/></a>
				</display:column>
			</c:if>
			<display:column property="invItem.name" escapeXml="true" sortable="true" titleKey="invGoodsReceiptItem.invItem.name" sortName="invItem.name" />
			<display:column property="qty" sortable="true" titleKey="invGoodsReceiptItem.qty" sortName="qty" format="{0,number,#,##0.##}"/>
			<display:column property="unitPrice" sortable="true" titleKey="invGoodsReceiptItem.unitPrice" sortName="unitPrice" format="{0,number,#,##0.##}"/>
			<display:setProperty name="export.excel.filename" value="InvGoodsReceipt.xls" />
			<display:setProperty name="export.csv.filename" value="InvGoodsReceipt.csv" />
			<display:setProperty name="export.pdf.filename" value="InvGoodsReceipt.pdf" />
			<display:footer>
				<tr>
				<c:choose>
				<c:when test="${invGoodsReceipt.documentNumber.documentNo == null}">
					<td colspan="4"><fmt:message key="invGoodsReceipt.totalCost"/></td>
				</c:when>
				<c:otherwise>
					<td colspan="3"><fmt:message key="invGoodsReceipt.totalCost"/></td>
				</c:otherwise>
				</c:choose>
					<td><fmt:formatNumber value="${invGoodsReceipt.totalCost}" pattern="#,##0.##"></fmt:formatNumber></td>
				<tr>
			</display:footer>
		</display:table>
		

		<fieldset class="form-actions">
			<c:choose>
				<c:when test="${invGoodsReceipt.documentNumber.documentNo == null}">
					<button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
						<i class="icon-ok icon-white"></i>
						<fmt:message key="button.save" />
					</button>
					
					<button type="submit" class="btn btn-warning" name="stockSave" onclick="bCancel=false;return saveToStock()">
						<i class="icon-ok icon-white"></i>
						<fmt:message key="button.saveToStock" />
					</button>
					
					<c:if test="${invGoodsReceipt.id != null}">
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
				<i class="icon-arrow-left"></i>
				<fmt:message key="button.back" />
			</button>
		</fieldset>
	</form:form>
</div>
<script type="text/javascript">
<!-- This is here so we can exclude the selectAll call when roles is hidden -->
	function onFormSubmit(theForm) {	
		var valid = validateInvGoodsReceipt(theForm);
		if ($('input[name="receiptType"]').val() == "200" && !bCancel) {
			valid = checkRequired(theForm['supplier.name'], '<tags:validateMessage errorKey="errors.required" field="invGoodsReceipt.supplier.code"/>') && valid;
		}
		return valid;
	}
	<c:if  test="${invGoodsReceipt.documentNumber.documentNo == null }">
	$(function() {

		var st = $('#receiptDateDatepicker').datetimepicker({
			format : "dd/MM/yyyy hh:mm:ss"
		});
	});
	
	$(document).ready(function () {
		$('input[name="supplier.name"]').lookup({
			type: 'supplier',
			displayProperty: function (json) {
				return json.code + ' : ' + json.name;
			},
			selectProperty: 'name',
			btnSearchCondition: function () {
				return {code: $('input[name="supplier.code"]').val()};	
			},
			handler: function (json) {
				if (json) {
					$('input[name="supplier.code"]').val(json.code);
				} else {
					$('input[name="supplier.code"]').val('');
					$('input[name="supplier.name"]').val('');
				}
			}
		});
	});
	</c:if>
	
	function saveToStock() {
		var form = document.forms[0];
		 confirmMessage('<fmt:message key="invGoodsReceipt.confirm.saveToStock"/>', function(result) {
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
		var form = document.forms['invGoodsReceipt'];
		if (!hasChecked(form.checkbox)) {
			alert('<fmt:message key="global.errorNoCheckboxSelectForDelete"/>');
			return false;
		}
		confirmMessage('<fmt:message key="global.confirm.delete"/>', function(result) {
			if (result) {
				$('#invGoodsReceipt').attr('action', '${ctx}/invGoodsReceipt/deleteDetail');
				form.submit();
			}
		});
		return false;
	}

	<c:if test="${not empty invGoodsReceiptItemList}">
	$(document).ready(function() {
		$("#chkSelectAll").click(function() {
			toggleCheckAll(this, document.forms['invGoodsReceipt'].checkbox);
		});
	});
	</c:if>
</script>
<v:javascript formName="invGoodsReceipt" staticJavascript="false" />
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>