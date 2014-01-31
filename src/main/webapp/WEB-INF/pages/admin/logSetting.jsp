<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="logSetting.title" /></title>
<meta name="menu" content="AdminMenu" />
</head>

<c:if test="${not empty searchError}">
	<div class="alert alert-error fade in">
		<a href="#" data-dismiss="alert" class="close">&times;</a>
		<c:out value="${searchError}" />
	</div>
</c:if>

<div class="span12">
	<h2>
		<fmt:message key="logSetting.heading" />
	</h2>

	<form method="get" action="${ctx}/admin/logSetting" id="searchForm" class="well form-horizontal">
		<div class="control-group">
			<label class="control-label" for="logName"><fmt:message key="logSetting.logName" />:</label>
			<div class="controls">
				<input type="text" class="input-xlarge" name="logName" id="logName" value="${param.logName}" placeholder="<fmt:message key="search.enterTerms"/>" class="input-medium" />
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<button id="button.search" class="btn" type="submit">
					<i class="icon-search"></i>
					<fmt:message key="button.search" />
				</button>

				<button id="button.reset" class="btn" type="button" onclick="clearForm($('#searchForm'))">
					<i class="icon-remove"></i>
					<fmt:message key="button.reset" />
				</button>


			</div>
		</div>
	</form>

	<form method="post" action="${ctx}/admin/logSetting" id="updateForm">
		<div id="actions">
			<button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
				<i class="icon-ok icon-white"></i>
				<fmt:message key="button.save" />
			</button>
			<a class="btn" href="<c:url value='/mainMenu'/>"> <i class="icon-ok"></i> <fmt:message key="button.done" /></a>
			<button id="button.viewLog" class="btn" type="button" onclick="viewLog()">
 				<i class="con-tasks"></i>
				<fmt:message key="button.viewLog" />
 			</button>
		</div>
		<display:table name="logList" cellspacing="0" cellpadding="0" requestURI="" defaultsort="1" id="log" pagesize="25" class="table table-condensed table-striped table-hover table-bordered" export="false" sort="list">
			<display:column property="name" escapeXml="true" sortable="true" titleKey="logSetting.logName" />
			<display:column sortProperty="effectiveLevel" sortable="true" titleKey="logSetting.level" class="span1" headerClass="span1">
				<input name="log" type="hidden" id="log" value="${log.name}" />
				<select id="level" name="level">
					<c:if test="${log.name != 'root' }">
						<option value="">NULL - ${log.effectiveLevel}</option>
					</c:if>
					<c:forEach items="${levels}" var="level">
						<option value="${level}" ${(log.level == level) ? 'selected' : ''}>${level}</option>
					</c:forEach>
				</select>
			</display:column>
		</display:table>
	</form>
</div>

<script>
	function viewLog() {
		window.open('${ctx}/admin/logView?window=true', 'logView',
						'width=700,height=500,menubar=no,scrollbars=yes,status=no,toolbar=no');
	}
</script>

