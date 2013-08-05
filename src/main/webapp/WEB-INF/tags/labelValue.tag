<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="list" required="true"  type="java.util.Collection"%>
<%@ attribute name="value" required="true" %>
<c:forEach items="${list}" var="labelValue">
	<c:if test="${labelValue.value eq value}">
		<c:out value="${labelValue.label}"></c:out>
	</c:if>
</c:forEach>