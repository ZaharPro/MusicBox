<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'en_EN'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="content"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <title>MusicBox</title>
    <c:import url="/jsp/head.jsp"/>
</head>
<body>

<section class="d-flex justify-content-center align-items-center" style="min-height: 100vh">
    <div class="col-xl-4 col-md-8 col-lg-6 pt-3 pb-3 bg-semitransparent">
        <h2 class="lead font-weight-normal mb-4 me-3">
            Request fail
        </h2>
        <c:if test="${not empty pageContext.errorData.requestURI}">
            <p class="mb-3">Request from ${pageContext.errorData.requestURI} is failed</p>
        </c:if>
        <c:if test="${not empty pageContext.errorData.servletName}">
            <p class="mb-3">Servlet name or type: ${pageContext.errorData.servletName}</p>
        </c:if>
        <c:if test="${not empty pageContext.errorData.throwable}">
            <p class="mb-3">Exception: ${pageContext.errorData.throwable}</p>
        </c:if>
        <c:if test="${errorMessage != null}">
            <p class="mb-3">Message: ${errorMessage}</p>
        </c:if>
        <c:if test="${pageContext.errorData.statusCode != 0}">
            <p class="mb-3">Status code: ${pageContext.errorData.statusCode}</p>
        </c:if>
        <button type="button" class="btn btn-primary btn-lg"
                style="padding-left: 2.5rem; padding-right: 2.5rem;">
            <a href="${pageContext.request.contextPath}/controller?command=home-page">Go home</a>
        </button>
    </div>
</section>

</body>
</html>
