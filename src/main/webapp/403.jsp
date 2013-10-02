<%@ include file="/common/taglibs.jsp"%>

<page:applyDecorator name="default">

<head>
    <title><fmt:message key="403.title"/></title>
    <meta name="heading" content="<fmt:message key='403.title'/>"/>
</head>

<div style="height: 400px">
    <fmt:message key="403.message">
        <fmt:param><c:url value="/"/></fmt:param>
    </fmt:message>
</div>

</page:applyDecorator>