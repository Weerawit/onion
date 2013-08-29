<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="field" required="true"%>
<%@ attribute name="errorKey" required="true"%>
<%@ attribute name="param1" required="false"%>
<fmt:message key="${field}" var="fieldName" />
<fmt:message key="${errorKey}">
	<fmt:param value="${fieldName }" />
	<c:if test="${param1 != ''}">
		<fmt:param value="${param1 }" />
	</c:if>
</fmt:message>