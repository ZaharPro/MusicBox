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

<div class="container f-col h-100 pt-3 pb-3">
    <div class="col card pt-3 pb-3 m-0 bg-dark">
        <h2 class="title">
            <fmt:message key="user.title"/>
        </h2>

        <h4 class="text-primary mt-1">
            <fmt:message key="user.login"/>
            <span class="text-info h6">
                ${user.getLogin()}
            </span>
        </h4>

        <h4 class="text-primary mt-1">
            <fmt:message key="user.email"/>
            <span class="text-info h6">
                ${user.getEmail()}
            </span>
        </h4>

        <c:if test="${user.getBanned() == true}">
            <h4 class="text-primary mt-1">
                <fmt:message key="user.status"/>
                <span class="text-danger h6">
                    <fmt:message key="user.banned"/>
                </span>
            </h4>
        </c:if>

        <ct:access role="admin">
            <h4 class="text-primary mt-1">
                <fmt:message key="user.tools"/>
            </h4>
            <div class="list-group list-group-flush bg-transparent">
                <a class="list-group-item list-group-item-action"
                   href="${pageContext.request.contextPath}/controller?command=edit-track-page">
                    <fmt:message key="user.create.track"/>
                </a>
                <a class="list-group-item list-group-item-action"
                   href="${pageContext.request.contextPath}/controller?command=edit-album-page">
                    <fmt:message key="user.create.album"/>
                </a>
                <a class="list-group-item list-group-item-action"
                   href="${pageContext.request.contextPath}/controller?command=edit-artist-page">
                    <fmt:message key="user.create.artist"/>
                </a>
            </div>
        </ct:access>

        <h4 class="text-primary mt-1">
            <fmt:message key="user.my.data"/>
        </h4>
        <div class="list-group list-group-flush bg-transparent">
            <a class="list-group-item list-group-item-action"
               href="${pageContext.request.contextPath}/controller?command=user-get-liked-tracks">
                <fmt:message key="user.show.liked.tracks"/>
            </a>
            <a class="list-group-item list-group-item-action"
               href="${pageContext.request.contextPath}/controller?command=user-get-liked-albums">
                <fmt:message key="user.show.liked.albums"/>
            </a>
            <a class="list-group-item list-group-item-action"
               href="${pageContext.request.contextPath}/controller?command=user-get-liked-artists">
                <fmt:message key="user.show.liked.artists"/>
            </a>
            <a class="list-group-item list-group-item-action"
               href="${pageContext.request.contextPath}/controller?command=user-get-playlists">
                <fmt:message key="user.show.playlists"/>
            </a>
        </div>

        <h4 class="text-primary mt-1">
            <fmt:message key="user.settings"/>
        </h4>
        <div class="list-group list-group-flush bg-transparent">
            <a class="list-group-item list-group-item-action bg-light border-0"
               href="${pageContext.request.contextPath}/controller?command=change-password-page">
                <fmt:message key="user.change.password"/>
            </a>
        </div>
    </div>
</div>

</body>
</html>
