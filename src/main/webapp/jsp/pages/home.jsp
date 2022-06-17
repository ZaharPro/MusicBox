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

<div class="container pt-2 pb-2">
    <div class="row justify-content-center align-items-center">
        <div class="col-7">
            <div class="row">
                <div class="col-lg-6 col-md-6 card p-3 mb-lg-0 mb-md-0">
                    <a href="${pageContext.request.contextPath}/controller?command=track-get">
                        <img class="card-img" src="${pageContext.request.contextPath}/img/home-track.png"
                             alt="Track img">
                        <div class="card-img-overlay d-flex justify-content-center align-items-center">
                            <h2 class="card-title font-weight-bold text-white" style="text-shadow: 0 0 5px black;">
                                <fmt:message key="home.tracks"/>
                            </h2>
                        </div>
                    </a>
                </div>
                <div class="col-lg-6 col-md-6 card p-3 mb-lg-0 mb-md-0">
                    <a href="${pageContext.request.contextPath}/controller?command=album-get">
                        <img class="card-img" src="${pageContext.request.contextPath}/img/home-album.png"
                             alt="Album img">
                        <div class="card-img-overlay d-flex justify-content-center align-items-center">
                            <h2 class="card-title font-weight-bold text-white" style="text-shadow: 0 0 5px black;">
                                <fmt:message key="home.albums"/>
                            </h2>
                        </div>
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-6 col-md-6 card p-3 mb-lg-0 mb-md-0">
                    <a href="${pageContext.request.contextPath}/controller?command=artist-get">
                        <img class="card-img" src="${pageContext.request.contextPath}/img/home-artist.png"
                             alt="Artist img">
                        <div class="card-img-overlay d-flex justify-content-center align-items-center">
                            <h2 class="card-title font-weight-bold text-white" style="text-shadow: 0 0 5px black;">
                                <fmt:message key="home.artists"/>
                            </h2>
                        </div>
                    </a>
                </div>
                <div class="col-lg-6 col-md-6 card p-3 mb-lg-0 mb-md-0">
                    <a href="${pageContext.request.contextPath}/controller?command=playlist-get">
                        <img class="card-img" src="${pageContext.request.contextPath}/img/home-playlist.png"
                             alt="Playlist img">
                        <div class="card-img-overlay d-flex justify-content-center align-items-center">
                            <h2 class="card-title font-weight-bold text-white" style="text-shadow: 0 0 5px black;">
                                <fmt:message key="home.playlists"/>
                            </h2>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

