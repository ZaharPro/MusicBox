<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <div class="card f-col justify-content-center align-items-center h-100 pt-3 pb-3 mb-0 bg-dark">
        <div class="row">
            <div class="col f-col align-items-center">
                <div class="row justify-content-center">
                    <a class="f-col justify-content-center img-link img-link-lg m-1"
                       style="background-image: url('/system/img/home-track.png');"
                       href="${pageContext.request.contextPath}/controller?command=user-get-liked-tracks">
                        <fmt:message key="user.tracks"/>
                    </a>
                    <a class="f-col justify-content-center img-link img-link-lg m-1"
                       style="background-image: url('/system/img/home-album.png');"
                       href="${pageContext.request.contextPath}/controller?command=user-get-liked-albums">
                        <fmt:message key="user.albums"/>
                    </a>
                </div>
                <div class="row justify-content-center">
                    <a class="f-col justify-content-center img-link img-link-lg m-1"
                       style="background-image: url('/system/img/home-artist.png');"
                       href="${pageContext.request.contextPath}/controller?command=user-get-liked-artists">
                        <fmt:message key="user.artists"/>
                    </a>
                    <a class="f-col justify-content-center img-link img-link-lg m-1"
                       style="background-image: url('/system/img/home-playlist.png');"
                       href="${pageContext.request.contextPath}/controller?command=user-get-playlists">
                        <fmt:message key="user.playlists"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

