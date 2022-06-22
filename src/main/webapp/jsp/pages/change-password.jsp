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
    <div class="col-xl-4 col-md-8 col-lg-6 pt-3 pb-3 bg-light">
        <form method="post" action="${pageContext.request.contextPath}/controller?command=change-password">
            <h2 class="lead font-weight-normal mb-4 me-3">
                <fmt:message key="ch.pass.title"/>
            </h2>

            <div class="form-outline mb-3">
                <label class="form-label" for="oldPasswordInput">
                    <fmt:message key="ch.pass.old.pass"/>
                </label>
                <input type="password" id="oldPasswordInput" placeholder="Enter old password" name="oldpass"
                       class="form-control form-control-lg"/>
            </div>
            <div class="form-check mb-4">
                <input class="form-check-input me-2" type="checkbox" id="oldPasswordCheckbox" value=""
                       minlength="8" maxlength="32" onclick="toggleOldPasswordCheckbox()"/>
                <label class="form-check-label" for="oldPasswordCheckbox">
                    <fmt:message key="ch.pass.show.old.pass"/>
                </label>
            </div>

            <div class="form-outline mb-3">
                <label class="form-label" for="newPasswordInput">
                    <fmt:message key="ch.pass.new.pass"/>
                </label>
                <input type="password" id="newPasswordInput" placeholder="Enter password" name="newpass"
                       class="form-control form-control-lg"/>
            </div>
            <div class="form-check mb-4">
                <input class="form-check-input me-2" type="checkbox" id="newPasswordCheckbox" value=""
                       minlength="8" maxlength="32" onclick="toggleNewPasswordCheckbox()"/>
                <label class="form-check-label" for="newPasswordCheckbox">
                    <fmt:message key="ch.pass.show.new.pass"/>
                </label>
            </div>

            <div class="form-outline mb-3">
                <label class="form-label" for="repeatPasswordInput">
                    <fmt:message key="ch.pass.repeat.pass"/>
                </label>
                <input type="password" id="repeatPasswordInput" placeholder="Enter password"
                       class="form-control form-control-lg"/>
                <div id="invalidRepPassLabel" class="email-error d-none text-danger">
                    <fmt:message key="ch.pass.passwords.must.match"/>
                </div>
            </div>
            <div class="form-check mb-4">
                <input class="form-check-input me-2" type="checkbox" id="repeatPasswordCheckbox" value=""
                       minlength="8" maxlength="32" onclick="toggleRepeatPasswordCheckbox()"/>
                <label class="form-check-label" for="repeatPasswordCheckbox">
                    <fmt:message key="ch.pass.show.repeat.pass"/>
                </label>
            </div>

            <div class="text-center text-lg-start mt-4 pt-2">
                <button type="submit" class="btn btn-lg">
                    <fmt:message key="ch.pass.submit"/>
                </button>
            </div>

            <c:if test="${msg != null}">
                <p class="text-center text-danger mt-1">${msg}</p>
            </c:if>
        </form>
    </div>
</div>

<script src="../../js/change-password.js"></script>
</body>
</html>