<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'en_EN'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="content"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <title><fmt:message key="title"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css"/>
</head>
<body>
<c:import url="/jsp/fragments/navbar.jsp"/>

<div>
    <c:if test="${not empty pageContext.errorData.requestURI}">
        <div>Request from ${pageContext.errorData.requestURI} is failed</div>
    </c:if>
    <c:if test="${not empty pageContext.errorData.servletName}">
        <div>Servlet name or type: ${pageContext.errorData.servletName}</div>
    </c:if>
    <c:if test="${not empty pageContext.errorData.throwable}">
        <div>Exception: ${pageContext.errorData.throwable}</div>
    </c:if>
    <c:if test="${errorMessage != null}">
        <div>Message: ${errorMessage}</div>
    </c:if>
    <c:if test="${pageContext.errorData.statusCode != 0}">
        <div>Status code: ${pageContext.errorData.statusCode}</div>
    </c:if>
    <div>a href="${pageContext.request.contextPath}/jsp/pages/home.jsp">Go home</a></div>
</div>
</body>
</html>
