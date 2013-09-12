<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="catalog.title" /></title>
<meta name="menu" content="CatalogMenu" />
<script type="text/javascript" src="<c:url value='/scripts/lib/plugins/jquery.ui.widget.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/upload/jquery.iframe-transport.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/upload/jquery.fileupload.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/upload/jquery.fileupload-process.js'/>"></script>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/scripts/upload/css/jquery.fileupload-ui.css'/>" />
<noscript><link rel="stylesheet" href="<c:url value='/scripts/upload/css/jquery.fileupload-ui-noscript.css'/>"/></noscript>

</head>
<div class="span2">
	<h2>
		<fmt:message key="catalog.heading" />
	</h2>
	<p>
		<fmt:message key="catalog.message" />
	</p>
</div>
<div class="span10">
	<spring:bind path="catalog.*">
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

	<form:form commandName="catalog" method="post" action="catalog" onsubmit="return onFormSubmit(this)" id="catalog" cssClass="well form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="invItem.id" />
		<input type="hidden" name="from" value="<c:out value="${param.from}"/>" />
		<form:hidden path="filename"/>
		<input type="hidden" name="action"/>

		<c:if test="${catalog.id == null }">
			<spring:bind path="catalog.code">
				<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
					<appfuse:label styleClass="control-label" key="catalog.code" />
					<div class="controls">
						<form:input path="code" id="code" cssClass="input-medium" maxlength="10" />
						<form:errors path="code" cssClass="help-inline" />
					</div>
				</div>
			</spring:bind>
		</c:if>
		<c:if test="${catalog.id != null }">
			<div class="control-group">
				<appfuse:label styleClass="control-label" key="catalog.code" />
				<div class="controls">
					<span class="input-medium uneditable-input"><c:out value="${catalog.code}" /></span>
					<form:hidden path="code" />
				</div>
			</div>
		</c:if>

		<spring:bind path="catalog.catalogType.code">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="catalog.catalogType.code" />
				<div class="controls">
					<form:select path="catalogType.code">
						<form:option value=""></form:option>
						<form:options items="${catalogTypeList}" itemLabel="name" itemValue="code" />
					</form:select>
					<form:errors path="catalogType.code" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="catalog.name">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="catalog.name" />
				<div class="controls">
					<form:input path="name" id="name" cssClass="input-xlarge" maxlength="50" />
					<form:errors path="name" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="catalog.finalPrice">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="catalog.finalPrice" />
				<div class="controls">
					<form:input path="finalPrice" id="finalPrice" cssClass="input-medium" maxlength="10" />
					<form:errors path="finalPrice" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="catalog.estPrice">
			<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
				<appfuse:label styleClass="control-label" key="catalog.estPrice" />
				<div class="controls">
					<form:input path="estPrice" id="estPrice" cssClass="input-medium" maxlength="10" />
					<form:errors path="estPrice" cssClass="help-inline" />
				</div>
			</div>
		</spring:bind>
		<div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
			<appfuse:label styleClass="control-label" key="catalog.img" />
			<div class="controls">
				<div class="controls-row">
					<img id="image" src="<c:url value='/img/thumbnail/catalog/${catalog.id}?t=200'/>" class="img-polaroid"/>
				</div>
				<div class="controls-row">
					<span class="btn btn-success fileinput-button" id="btnUpload" data-loading-text="<fmt:message key='catalog.uploading'/>">
						<input id="fileupload" type="file" name="file">
						<i class="icon-upload icon-white"></i> <span><fmt:message key="button.selectUpload"/></span>
					</span>
					
					<div id="progress" class="progress hide">
  						<div id="progress-bar" class="bar" style="width: 0%; height : 15px"></div>
					</div>
					
			</div>
		</div>

		<div id="actions">
		
			<button id="button.add" class="btn btn-primary" type="submit" onclick="bCancel=true;$('#catalog').attr('action', '${ctx}/catalog/addDetail');">
				<i class="icon-plus icon-white"></i>
				<fmt:message key="button.add" />
			</button>
			
			<button id="button.delete" class="btn" type="submit" onclick="bCancel=true;return validateDelete()">
				<i class="icon-trash"></i>
				<fmt:message key="button.delete" />
			</button>
			
		</div>
		<display:table name="catalogItemList" cellspacing="0" cellpadding="0" requestURI="" id="catalogItem" class="table table-condensed table-striped table-hover table-bordered">
			<display:column title="<input type='checkbox' name='chkSelectAll' id='chkSelectAll'/>" class="span1" style="width: 10px">
				<input type="checkbox" id="checkbox" name="checkbox" value="<c:out value='${catalogItem_rowNum - 1}'/>" />
			</display:column>
			<display:column titleKey="catalogItem.invItem.code" sortable="true">
				<button class="btn btn-link" type="submit" onclick="bCancel=true;$('#catalog').attr('action', '${ctx}/catalog/editDetail?&id=${ catalogItem_rowNum - 1}');">
					<c:out value="${catalogItem.invItem.code}"/>
				</button>
			</display:column>


			<display:column property="invItem.name" escapeXml="true" sortable="true" titleKey="catalogItem.invItem.name" sortName="invItem.name" />
			<display:column property="qty" sortable="true" titleKey="catalogItem.qty" sortName="qty" format="{0,number,#,##0.##}"/>
		</display:table>


		<fieldset class="form-actions">
			<button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
				<i class="icon-ok icon-white"></i>
				<fmt:message key="button.save" />
			</button>

			<c:if test="${param.from == 'list' and param.method != 'Add'}">
				<button type="submit" class="btn" name="delete" onclick="bCancel=true;return deleteThis()">
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
		return validateCatalog(theForm);
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
		var form = document.forms['catalog'];
		if (!hasChecked(form.checkbox)) {
			alert('<fmt:message key="global.errorNoCheckboxSelectForDelete"/>');
			return false;
		}
		confirmMessage('<fmt:message key="global.confirm.delete"/>', function(result) {
			if (result) {
				$('#catalog').attr('action', '${ctx}/catalog/deleteDetail');
				form.submit();
			}
		});
		return false;
	}
	
	<c:if test="${not empty catalogItemList}">
	$(document).ready(function() {
		$("#chkSelectAll").click(function() {
			toggleCheckAll(this, document.forms['catalog'].checkbox);
		});
	});
	</c:if>

	
	$(document).ready(function() {
		$('#fileupload').fileupload({
			dataType : 'json',
			maxFileSize : 5000000,
			acceptFileTypes : /(\.|\/)(gif|jpe?g|png)$/i,
			/* add: function (e, data) {
				$('#btnUpload').removeClass('hidden');
				$('#btnUpload').on('click', function() {
					data.submit();
				});
			}, */
			send : function(e, data) {
				$('#progress').show();
//				$('#btnUpload').button('loading');
			},
			fail : function(e, data) {
				alert('fail to upload : ' + data.textStatus);
//				$('#btnUpload').button('reset')
				$( "#progress").hide( 'slow', function() {
					$('#progress-bar').css('width', '0%');						
				} );
			},
			done : function(e, data) {
				//$('#btnUpload').addClass('hidden');
				if (data.result) {
					$('#image').prop('src', data.result.files[0].thumbnailUrl);
					$('#filename').val(data.result.files[0].name);
//					$('#btnUpload').button('reset')
					$( "#progress").hide( 'slow', function() {
						$('#progress-bar').css('width', '0%');						
					} );

				}
			},
			progress :  function(e, data) {
				var progress = parseInt(data.loaded / data.total * 100, 10);
				$('#progress-bar').css('width', progress + '%');
			}
		});

		$('#fileupload').fileupload('option', {
			url : 'catalog/upload',
			// Enable image resizing, except for Android and Opera,
			// which actually support image resizing, but fail to
			// send Blob objects via XHR requests:
			disableImageResize : /Android(?!.*Chrome)|Opera/.test(window.navigator.userAgent),
			maxFileSize : 5000000,
			acceptFileTypes : /(\.|\/)(gif|jpe?g|png)$/i
		});
		
		
		if ($('#filename').val() != '') {
			$('#image').prop('src', "<c:url value='/img/thumbnail/'/>" + $('#filename').val() + "?t=200");
		}
	});

	
</script>
<v:javascript formName="catalog" staticJavascript="false" />
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>