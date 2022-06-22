<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="content"/>

<html lang="en">
<head>
    <title>MusicBox</title>
    <c:import url="/jsp/fragments/head.jsp"/>
</head>
<body>

<div class="container f-col h-100 p-5">
    <div class="card f-col justify-content-center align-items-center h-100 pt-3 pb-3 mb-0 bg-dark">
        <h2 class="title text-center mb-3">
            Error
        </h2>

        <c:if test="${pageContext.errorData.statusCode != 0}">
            <div class="mb-3">
                <h4 class="text-danger text-center mb-1">
                    Status code
                </h4>
                <div class="text-info text-center">
                        ${pageContext.errorData.statusCode}
                </div>
            </div>
        </c:if>
        <c:if test="${not empty pageContext.errorData.throwable}">
            <div class="mb-3">
                <h4 class="text-danger text-center mb-1">
                    Exception
                </h4>
                <div class="text-info text-center">
                        ${pageContext.errorData.throwable}
                </div>
            </div>
        </c:if>
        <c:if test="${not empty msg}">
            <div class="mb-3">
                <h4 class="text-danger text-center mb-1">
                    Message
                </h4>
                <div class="text-info text-center">
                        ${msg}
                </div>
            </div>
        </c:if>
        <a class="btn btn-lg" href="${pageContext.request.contextPath}/controller?command=home-page">
            Home
        </a>
    </div>
</div>

</body>
</html>
