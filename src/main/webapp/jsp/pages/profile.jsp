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

<div class="container f-col h-100 pt-2 pb-2">
    <div class="card f-col align-items-center h-100 pt-3 pb-3 mb-0 bg-dark">
        <h2 class="title text-center mb-3">
            <fmt:message key="profile.title"/>
        </h2>
        <div class="col-auto">
            <div class="d-flex mb-3">
                <h4 class="text-primary mr-5">
                    <fmt:message key="user.login"/>
                </h4>
                <div class="text-info">
                    ${user.getLogin()}
                </div>
            </div>
            <div class="d-flex mb-3">
                <h4 class="text-primary mr-5">
                    <fmt:message key="user.email"/>
                </h4>
                <div class="text-info">
                    ${user.getEmail()}
                </div>
            </div>
            <c:choose>
                <c:when test="${user.getBanned() == true}">
                    <div class="d-flex mb-5 align-items-center">
                        <h4 class="text-primary mr-5">
                            <fmt:message key="user.status"/>
                        </h4>
                        <div class="text-info">
                            <fmt:message key="user.banned"/>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="d-flex mb-3 justify-content-center">
                        <a class="btn btn-sm"
                           href="${pageContext.request.contextPath}/controller?command=change-password-page">
                            <fmt:message key="user.ch.pass"/>
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
            <div class="row">
                <div class="col f-col align-items-center">
                    <div class="row justify-content-center mt-1">
                        <a class="f-col justify-content-center img-link img-link-sm"
                           style="background-image: url('/system/img/home-track.png');"
                           href="${pageContext.request.contextPath}/controller?command=user-get-liked-tracks">
                            <fmt:message key="user.tracks"/>
                        </a>
                        <a class="f-col justify-content-center img-link img-link-sm ml-1"
                           style="background-image: url('/system/img/home-album.png');"
                           href="${pageContext.request.contextPath}/controller?command=user-get-liked-albums">
                            <fmt:message key="user.albums"/>
                        </a>
                    </div>
                    <div class="row justify-content-center mt-1">
                        <a class="f-col justify-content-center img-link img-link-sm"
                           style="background-image: url('/system/img/home-artist.png');"
                           href="${pageContext.request.contextPath}/controller?command=user-get-liked-artists">
                            <fmt:message key="user.artists"/>
                        </a>
                        <a class="f-col justify-content-center img-link img-link-sm ml-1"
                           style="background-image: url('/system/img/home-playlist.png');"
                           href="${pageContext.request.contextPath}/controller?command=user-get-playlists">
                            <fmt:message key="user.playlists"/>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
