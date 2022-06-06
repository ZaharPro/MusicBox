<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <title><fmt:message key="title"/></title>
    <c:import url="/jsp/head.jsp"/>
</head>
<body>

<section class="d-flex justify-content-center align-items-center" style="min-height: 100vh">
    <div class="col-xl-4 col-md-8 col-lg-6 pt-3 pb-3 bg-light">
        <form method="POST" name="loginForm" action="${pageContext.request.contextPath}/controller?command=login">
            <h2 class="lead font-weight-normal mb-4 me-3">
                <fmt:message key="login.title"/>
            </h2>

            <div class="form-outline mb-4">
                <label class="form-label" for="loginInput">
                    <fmt:message key="login.input.login"/>
                </label>
                <input type="text" id="loginInput" placeholder="Enter login" name="login"
                       class="form-control form-control-lg"/>
            </div>

            <div class="form-outline mb-3">
                <label class="form-label" for="passwordInput">
                    <fmt:message key="login.input.password"/>
                </label>
                <input type="password" id="passwordInput" placeholder="Enter password" name="password"
                       class="form-control form-control-lg"/>
            </div>

            <div class="d-flex justify-content-between align-items-center">
                <div class="form-check mb-0">
                    <input class="form-check-input me-2" type="checkbox" value="" id="passwordCheckbox"
                           onclick="togglePasswordCheckbox()"/>
                    <label class="form-check-label" for="passwordCheckbox">
                        <fmt:message key="login.checkbox.password"/>
                    </label>
                </div>
                <a href="${pageContext.request.contextPath}/controller?command=forgot-password" class="text-body">
                    <fmt:message key="login.button.forgot.pass"/>
                </a>
            </div>

            <div class="text-center text-lg-start mt-4 pt-2">
                <button type="submit" class="btn btn-primary btn-lg">
                    <fmt:message key="login.button.submit"/>
                </button>
                <p class="small fw-bold mt-2 pt-1 mb-0">
                    <fmt:message key="login.button.label"/>
                    <a href="${pageContext.request.contextPath}/controller?command=sign-up-page"
                       class="link-danger">
                        <fmt:message key="login.button.signup"/>
                    </a>
                </p>
            </div>

            <c:if test="${sessionScope.errorMessage != null}">
                <div><fmt:message key="login.error.${sessionScope.errorMessage}"/></div>
            </c:if>
        </form>
    </div>
</section>

<script src="../../js/login.js"></script>
</body>
</html>
