<%@ include file="/common/taglibs.jsp"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	<h3>
		<fmt:message key="downloadDialog.title"></fmt:message>
	</h3>
</div>
<div class="modal-body">
	<div class="container-fluid">
		<%@ include file="/common/messages.jsp"%>
		<form action="" id="downloadDialog" name="downloadDialog" class="well form-horizontal">
			<div class="row-fluid">
				<div class="control-group">
					<label for="radio" class="control-label"><fmt:message key="downloadDialog.downloadType" /></label>
					<div class="controls">
						<label class="radio">
							<input type="radio" id="radioPdf" name="radio" value='pdf' />
							<fmt:message key="downloadDialog.pdf" />
						</label>
						<label class="radio">
							<input type="radio" id="radioXls" name="radio" value='xls' />
							<fmt:message key="downloadDialog.xls" />
						</label>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
<div class="modal-footer">
	<a href="#" id="printBtn" class="btn btn-primary"><fmt:message key="button.print" /></a>
	<button type="button" data-dismiss="modal" class="btn">
		<fmt:message key="button.close" />
	</button>
</div>
<script>
	var validateDownloadDialog = function() {
		var form = document.forms['downloadDialog'];
		return checkRequired(form['radio'], '<tags:validateMessage errorKey="errors.required" field="downloadDialog.downloadType"/>');
	}
</script>
