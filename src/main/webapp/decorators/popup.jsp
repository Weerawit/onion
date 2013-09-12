<%@ include file="/common/taglibs.jsp"%>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3><decorator:title /> </h3>
	</div>
	<div class="modal-body">
		<div class="container-fluid">
			<%@ include file="/common/messages.jsp"%>
			<div class="row-fluid">
				<decorator:body />
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<a href="#" id="selectBtn" class="btn btn-primary"><fmt:message key="button.selectPopup"/></a>
		<button type="button" data-dismiss="modal" class="btn"><fmt:message key="button.close"/></button>
	</div>
<%=(request.getAttribute("scripts") != null) ? request.getAttribute("scripts") : ""%>
