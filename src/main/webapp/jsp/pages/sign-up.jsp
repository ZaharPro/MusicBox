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

<div class="background">
    <div class="shape"></div>
    <div class="shape"></div>
</div>

<form method="POST" action="${pageContext.request.contextPath}/controller?command=singup">

    <h3><fmt:message key="singup.title"/></h3>

    <label for="loginInput"><fmt:message key="singup.input.login"/></label>
    <input type="text" id="loginInput" minlength="8" maxlength="32" required>

    <label for="passwordInput"><fmt:message key="singup.input.password"/></label>
    <input type="password" id="passwordInput" minlength="8" maxlength="32" required>

    <label for="passwordCheckbox"><fmt:message key="singup.checkbox.password"/></label>
    <input type="checkbox" id="passwordCheckbox" onclick="togglePasswordCheckbox()">

    <label for="repeatPasswordInput"><fmt:message key="singup.input.repeatPassword"/></label>
    <input type="password" id="repeatPasswordInput" minlength="8" maxlength="32" required>

    <label for="repeatPasswordCheckbox"><fmt:message key="singup.checkbox.repeatPassword"/></label>
    <input type="checkbox" id="repeatPasswordCheckbox" onclick="toggleRepeatPasswordCheckbox()">

    <label for="emailInput"><fmt:message key="signup.input.email"/></label>
    <input type="email" id="emailInput" minlength="5" maxlength="64" required>

    <c:if test="${errorMessage != null}">
        <div><fmt:message key="singup.error.${errorMessage}"/></div>
    </c:if>
    <button type="submit"><fmt:message key="signup.button.submit"/></button>
</form>

<script>
    const passwordInput = document.getElementById('passwordInput');
    const repeatPasswordInput = document.getElementById('repeatPasswordInput');

    function togglePasswordCheckbox() {
        toggleCheckBox(passwordInput);
    }

    function toggleRepeatPasswordCheckbox() {
        toggleCheckBox(repeatPasswordInput);
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