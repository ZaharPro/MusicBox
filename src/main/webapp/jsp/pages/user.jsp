<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="custom-tags" %>

<!DOCTYPE html>
<fmt:setLocale value="${locale}" scope="session"/>
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
    <ct:access role="admin">
        <p>${user.getRegistration()}</p>
        <p>${role}</p>

        <c:if test="${admin != user.getId()}">
            <form method="post" action="${pageContext.request.contextPath}/controller?command=user-set-ban">
                <input type="hidden" name="userid" value="${user.getId()}"/>
                <input type="submit" value="Ban user">
                <label for="banCheckbox"></label>
                <input type="checkbox" value="false" id="banCheckbox"/>
            </form>
        </c:if>
        <a href="${pageContext.request.contextPath}/controller?command=edit-artist-page">
            <fmt:message key="user.create.artist"/>
        </a>
        <a href="${pageContext.request.contextPath}/controller?command=edit-album-page">
            <fmt:message key="user.create.album"/>
        </a>
        <a href="${pageContext.request.contextPath}/controller?command=edit-track-page">
            <fmt:message key="user.create.track"/>
        </a>
    </ct:access>

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

    <a href="${pageContext.request.contextPath}/controller?command=change-password-page">
        Change password
    </a>
</div>
</body>
</html>
