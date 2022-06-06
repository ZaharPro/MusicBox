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
        <form method="POST" name=signUpForm" action="${pageContext.request.contextPath}/controller?command=sign-up">
            <h2 class="lead font-weight-normal mb-4 me-3">
                <fmt:message key="signup.title"/>
            </h2>

            <div class="form-outline mb-4">
                <label class="form-label" for="signupInput">
                    <fmt:message key="signup.input.login"/>
                </label>
                <input type="text" id="signupInput" placeholder="Enter signup" name="login"
                       class="form-control form-control-lg" minlength="8" maxlength="32" required/>
            </div>

            <div class="form-outline mb-3">
                <label class="form-label" for="passwordInput">
                    <fmt:message key="signup.input.pass"/>
                </label>
                <input type="password" id="passwordInput" placeholder="Enter password" name="password"
                       class="form-control form-control-lg"/>
            </div>
            <div class="form-check mb-4">
                <input class="form-check-input me-2" type="checkbox" id="passwordCheckbox" value=""
                       minlength="8" maxlength="32" onclick="togglePasswordCheckbox()"/>
                <label class="form-check-label" for="passwordCheckbox">
                    <fmt:message key="signup.checkbox.pass"/>
                </label>
            </div>

            <div class="form-outline mb-3">
                <label class="form-label" for="repeatPasswordInput">
                    <fmt:message key="signup.input.pass.repeat"/>
                </label>
                <input type="password" id="repeatPasswordInput" placeholder="Enter password"
                       class="form-control form-control-lg"/>
                <span class="email-error d-none text-danger"><fmt:message key="signup.error.pass.repeat"/></span>
            </div>
            <div class="form-check mb-4">
                <input class="form-check-input me-2" type="checkbox" id="repeatPasswordCheckbox" value=""
                       minlength="8" maxlength="32" onclick="toggleRepeatPasswordCheckbox()"/>
                <label class="form-check-label" for="repeatPasswordCheckbox">
                    <fmt:message key="signup.checkbox.pass.repeat"/>
                </label>
            </div>


            <div class="form-outline mb-4">
                <label class="form-label" for="emailInput">
                    <fmt:message key="signup.input.email"/>
                </label>
                <input type="email" id="emailInput" placeholder="Enter email" name="email"
                       class="form-control form-control-lg" minlength="5" maxlength="64" required/>
            </div>

            <div class="text-center text-lg-start mt-4 pt-2">
                <button type="submit" class="btn btn-primary btn-lg">
                    <fmt:message key="signup.button.submit"/>
                </button>
            </div>

            <c:if test="${errorMessage != null}">
                <div><fmt:message key="signup.error.${errorMessage}"/></div>
            </c:if>
        </form>
    </div>
</section>

<script src="../../js/sign-up.js"></script>
</body>
</html>