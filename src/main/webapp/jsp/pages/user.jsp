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
<c:import url="/jsp/fragments/navbar.jsp"/>
<div>
    <p>${user.getLogin()}</p>
    <p>${user.getEmail()}</p>
    <c:if test="${user.getBanned() == true}">
        <p>User banned</p>
    </c:if>
    <c:if test="${admin != null}">
        <p>${user.getRegistration()}</p>
        <p>${role}</p>

        <form method="post" action="${pageContext.request.contextPath}/controller?command=user-set-ban">
            <input type="hidden" name="userid" value="${user.getId()}"/>

            <input type="checkbox" value="false" id="banCheckbox"/>
            <label for="banCheckbox">
                Ban user
            </label>

            <input type="submit" value="Show user">
        </form>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/controller?command=user-get-liked-tracks">
        <input type="hidden" name="userid" value="${user.getId()}"/>
        <input type="submit" value="Show liked tracks">
    </form>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=user-get-liked-albums">
        <input type="hidden" name="userid" value="${user.getId()}"/>
        <input type="submit" value="Show liked albums">
    </form>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=user-get-liked-artists">
        <input type="hidden" name="userid" value="${user.getId()}"/>
        <input type="submit" value="Show liked artists">
    </form>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=user-get-playlists">
        <input type="hidden" name="userid" value="${user.getId()}"/>
        <input type="submit" value="Show playlists">
    </form>

</div>
</body>
</html>
