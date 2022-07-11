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
        <form method="post" name=signUpForm" action="${pageContext.request.contextPath}/controller?command=sign-up">
            <h2 class="lead font-weight-normal mb-4 me-3">
                <fmt:message key="signup.title"/>
            </h2>

            <div class="form-outline mb-4">
                <label class="form-label" for="signupInput">
                    <fmt:message key="signup.login"/>
                </label>
                <input type="text" id="signupInput" placeholder="Enter signup" name="login"
                       minlength="4" maxlength="32" pattern="[A-Za-z\d ]+"
                       class="form-control form-control-lg"/>
            </div>

            <div class="form-outline mb-3">
                <label class="form-label" for="passwordInput">
                    <fmt:message key="signup.pass"/>
                </label>
                <input type="password" id="passwordInput" placeholder="Enter password" name="password"
                       minlength="8" maxlength="32" pattern="[A-Za-z\d@$!%*#?&]+"
                       class="form-control form-control-lg"/>
            </div>
            <div class="form-check mb-4">
                <input class="form-check-input me-2" type="checkbox" id="passwordCheckbox" value=""
                       onclick="togglePasswordCheckbox()"/>
                <label class="form-check-label" for="passwordCheckbox">
                    <fmt:message key="signup.show.pass"/>
                </label>
            </div>

            <div class="form-outline mb-3">
                <label class="form-label" for="repeatPasswordInput">
                    <fmt:message key="signup.repeat.pass"/>
                </label>
                <input type="password" id="repeatPasswordInput" placeholder="Enter password"
                       minlength="8" maxlength="32" pattern="[A-Za-z\d@$!%*#?&]+"
                       class="form-control form-control-lg"/>
                <div id="invalidRepPassLabel" class="email-error d-none text-danger">
                    <fmt:message key="signup.passwords.must.match"/>
                </div>
            </div>
            <div class="form-check mb-4">
                <input class="form-check-input me-2" type="checkbox" id="repeatPasswordCheckbox" value=""
                       onclick="toggleRepeatPasswordCheckbox()"/>
                <label class="form-check-label" for="repeatPasswordCheckbox">
                    <fmt:message key="signup.show.repeat.pass"/>
                </label>
            </div>


            <div class="form-outline mb-4">
                <label class="form-label" for="emailInput">
                    <fmt:message key="signup.email"/>
                </label>
                <input type="email" id="emailInput" placeholder="Enter email" name="email"
                       minlength="5" maxlength="64" class="form-control form-control-lg"/>
            </div>

            <div class="text-center text-lg-start mt-4 pt-2">
                <button type="submit" class="btn btn-lg">
                    <fmt:message key="signup.submit"/>
                </button>
            </div>

            <c:if test="${msg != null}">
                <p class="text-center text-danger mt-1">${msg}</p>
            </c:if>
        </form>
    </div>
</div>

<script src="../../js/sign-up.js"></script>
</body>
</html>