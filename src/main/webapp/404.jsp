<%@ include file="/common/taglibs.jsp"%>

<page:applyDecorator name="default">

<head>
    <title><fmt:message key="404.title"/></title>
    <meta name="heading" content="<fmt:message key='404.title'/>"/>
</head>
<div class="container" style="height: 400px">
<p>
    <fmt:message key="404.message">
        <fmt:param><c:url value="/mainMenu"/></fmt:param>
    </fmt:message>
</p>
</div>
</page:applyDecorator>