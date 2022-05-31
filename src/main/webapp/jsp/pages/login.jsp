<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <title><fmt:message key="title"/></title>
    <c:import url="/jsp/head.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css"/>
</head>
<body>

<c:import url="/jsp/fragments/navbar.jsp"/>

<body>
<div class="background">
    <div class="shape"></div>
    <div class="shape"></div>
</div>
<form method="POST" name="loginForm" action="${pageContext.request.contextPath}/controller?command=login">
    <h3><fmt:message key="login.title"/></h3>

    <label for="loginInput"><fmt:message key="login.input.login"/></label>
    <input type="text" id="loginInput" minlength="8" maxlength="32" name="login" required>

    <label for="passwordInput"><fmt:message key="login.input.password"/></label>
    <input type="password" id="passwordInput" name="password" minlength="8" maxlength="32" required>

    <label for="passwordCheckbox"><fmt:message key="login.checkbox.password"/></label>
    <input type="checkbox" id="passwordCheckbox" onclick="togglePasswordCheckbox()">

    <c:if test="${errorMessage != null}">
        <div><fmt:message key="login.error.${errorMessage}"/></div>
    </c:if>
    <button type="submit" name="Log in"><fmt:message key="login.button.submit"/></button>
</form>
<script>
    const passwordInput = document.getElementById('passwordInput');

    function togglePasswordCheckbox() {
        toggleCheckBox(passwordInput);
    }

    function toggleCheckBox(input) {
        input.type =
            input.type === "password" ?
                "text" :
                "password";
    }
</script>
</body>
</html>
