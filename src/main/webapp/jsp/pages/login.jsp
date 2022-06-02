<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <title><fmt:message key="title"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css"/>
</head>
<body>
<header class="header">
    <div class="container">
        <h1 class="header-logo">
            <a href="${pageContext.request.contextPath}/controller?command=loginpage">
                <fmt:message key="navbar.logo"/>
            </a>
        </h1>
    </div>
</header>

<div class="background">
    <div class="shape"></div>
    <div class="shape"></div>
</div>

<form method="POST" action="${pageContext.request.contextPath}/controller?command=login"
      class="auth-form">
    <h3><fmt:message key="login.title"/></h3>

    <label for="loginInput"><fmt:message key="login.input.login"/></label>
    <input type="text" id="loginInput" name="login" minlength="8" maxlength="32" required>

    <label for="passwordInput"><fmt:message key="login.input.password"/></label>
    <input type="password" id="passwordInput" name="password" minlength="8" maxlength="32" required>

    <label for="passwordCheckbox"><fmt:message key="login.checkbox.password"/></label>
    <input type="checkbox" id="passwordCheckbox" onclick="togglePasswordCheckbox()">

    <c:if test="${errorMessage != null}">
        <div><fmt:message key="login.error.${errorMessage}"/></div>
    </c:if>
    <button type="submit" class="active"><fmt:message key="login.button.submit"/></button>
    <a href="${pageContext.request.contextPath}/controller?command=signuppage">
        <fmt:message key="login.button.signup"/>
    </a>
</form>

<script src="../../js/login.js"></script>
</body>
</html>
