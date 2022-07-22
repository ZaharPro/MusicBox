<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <title><fmt:message key="title"/></title>
    <c:import url="/jsp/fragments/head.jsp"/>
</head>
<body>

<div class="d-flex justify-content-center align-items-center h-100">
    <div class="col-xl-4 col-md-8 col-lg-6 py-3 bg-light">
        <form method="post" name="loginForm" action="${pageContext.request.contextPath}/controller?command=login">
            <h2 class="lead font-weight-normal mb-4 me-3">
                <fmt:message key="login.title"/>
            </h2>

            <div class="form-outline mb-4">
                <label class="form-label" for="loginInput">
                    <fmt:message key="login.login"/>
                </label>
                <input type="text" id="loginInput" placeholder="Enter login" name="login"
                       minlength="4" maxlength="32" pattern="[A-Za-z\d ]+"
                       class="form-control form-control-lg"/>
            </div>

            <div class="form-outline mb-3">
                <label class="form-label" for="passwordInput">
                    <fmt:message key="login.password"/>
                </label>
                <input type="password" id="passwordInput" placeholder="Enter password" name="password"
                       minlength="8" maxlength="32" pattern="[A-Za-z\d@$!%*#?&]+"
                       class="form-control form-control-lg"/>
            </div>

            <div class="form-check mb-0">
                <input class="form-check-input me-2" type="checkbox" value="" id="passwordCheckbox"
                       onclick="togglePasswordCheckbox()"/>
                <label class="form-check-label" for="passwordCheckbox">
                    <fmt:message key="login.show.pass"/>
                </label>
            </div>

            <div class="text-center text-lg-start mt-4 pt-2">
                <button type="submit" class="btn btn-lg">
                    <fmt:message key="login.submit"/>
                </button>
                <p class="small fw-bold mt-2 pt-1 mb-0">
                    <fmt:message key="login.signup.question"/>
                    <a href="${pageContext.request.contextPath}/controller?command=sign-up-page"
                       class="link-danger">
                        <fmt:message key="login.signup"/>
                    </a>
                </p>
            </div>

            <c:if test="${msg ne null}">
                <p class="text-center text-danger mt-1">${msg}</p>
            </c:if>
        </form>
    </div>
</div>

<script src="../../js/login.js"></script>
</body>
</html>
