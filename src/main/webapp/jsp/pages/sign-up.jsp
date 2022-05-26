<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <c:import url="/jsp/fragments/head.jsp"/>
    <title><fmt:message key="label.title"/></title>
</head>
<body>

<c:import url="/jsp/fragments/navbar.jsp"/>

<div class="container py-3">
    <div class="row flex-column">
        <form name="signupForm" method="POST" action="${pageContext.request.contextPath}/controller?command=sign-up"
              class="flex-box col-md-6">
            <h1><fmt:message key="signup.reg"/></h1>
            <div class="mb-3">
                <span class="form-label"><fmt:message key="signup.login"/></span>
                <input type="text" class="form-control" name="login" id="loginRegForm" minlength="8" maxlength="32"
                       required>
                <span class="login-error d-none text-danger"><fmt:message key="signup.error.login.already"/></span>
            </div>
            <div class="mb-3">
                <span class="form-label"><fmt:message key="signup.pass"/></span>
                <input type="password" name="password" id="currentPass" minlength="8" maxlength="32"
                       class="form-control password" required>
                <input type="checkbox" onclick="showPass()"> <fmt:message key="settings.current.password.show"/>
            </div>
            <div class="mb-3">
                <span class="form-label"><fmt:message key="signup.repeat.pass"/></span>
                <input type="password" class="form-control password" minlength="8" maxlength="32" required>
                <span class="password-error d-none text-danger"><fmt:message key="signup.repeat.pass.error"/></span>
            </div>
            <div class="mb-3">
                <span class="form-label"><fmt:message key="signup.email"/></span>
                <input type="email" name="email" id="emailRegForm" minlength="5" maxlength="64" class="form-control"
                       required>
                <span class="email-error d-none text-danger"><fmt:message key="signup.error.email.already"/></span>
            </div>
            <h3 class="text-danger error-message">
                <fmt:message key="login.error.${errorMessage}"/>
            </h3>
            <button type="submit" class="btn btn-primary"><fmt:message key="signup.btn.submit"/></button>
        </form>
    </div>
</div>
</body>
</html>
