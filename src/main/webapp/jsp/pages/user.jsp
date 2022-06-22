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
    <c:import url="/jsp/fragments/head.jsp"/>
</head>
<body>
<c:import url="/jsp/fragments/navbar.jsp"/>

<div class="container f-col h-100 pt-2 pb-2">
    <div class="card f-col h-100 pt-3 pb-3 mb-0 bg-dark">
        <h2 class="title text-center mb-3">
            <fmt:message key="user.title"/>
        </h2>
        <c:choose>
            <c:when test="${user != null}">
                <div class="f-col align-items-center">
                    <div class="col-auto">
                        <div class="row mb-3">
                            <h4 class="col-6 text-primary">
                                <fmt:message key="user.login"/>
                            </h4>
                            <div class="col-6 text-info">
                                    ${user.getLogin()}
                            </div>
                        </div>
                        <div class="row mb-3">
                            <h4 class="col-6 text-primary">
                                <fmt:message key="user.email"/>
                            </h4>
                            <div class="col-6 text-info">
                                    ${user.getEmail()}
                            </div>
                        </div>
                        <div class="row mb-3">
                            <h4 class="col-6 text-primary">
                                <fmt:message key="user.registration"/>
                            </h4>
                            <div class="col-6 text-info">
                                    ${user.getRegistration()}
                            </div>
                        </div>
                        <div class="row mb-3">
                            <h4 class="col-6 text-primary">
                                <fmt:message key="user.role"/>
                            </h4>
                            <div class="col-6 text-info">
                                    ${role}
                            </div>
                        </div>
                        <div class="row mb-3 align-items-center">
                            <h4 class="col-6 text-primary">
                                <fmt:message key="user.status"/>
                            </h4>
                            <div class="col-3 text-info">
                                <c:choose>
                                    <c:when test="${user.getBanned() == true}">
                                        <fmt:message key="user.banned"/>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="user.active"/>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="col-3">
                                <form method="post"
                                      action="${pageContext.request.contextPath}/controller?command=user-set-ban">
                                    <input type="hidden" name="userid" value="${user.getId()}"/>
                                    <c:choose>
                                        <c:when test="${user.getBanned() == true}">
                                            <input type="hidden" value="false"/>
                                            <button type="submit" class="btn btn-sm w-100">
                                                <fmt:message key="user.unban"/>
                                            </button>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="hidden" value="true"/>
                                            <button type="submit" class="btn btn-sm w-100">
                                                <fmt:message key="user.ban"/>
                                            </button>
                                        </c:otherwise>
                                    </c:choose>
                                </form>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col f-col align-items-center">
                                <div class="row justify-content-center mt-1">
                                    <a class="f-col justify-content-center img-link img-link-sm"
                                       style="background-image: url('/system/img/home-track.png');"
                                       href="${pageContext.request.contextPath}/controller?command=user-get-liked-tracks&userid=${user.getId()}">
                                        <fmt:message key="user.tracks"/>
                                    </a>
                                    <a class="f-col justify-content-center img-link img-link-sm ml-1"
                                       style="background-image: url('/system/img/home-album.png');"
                                       href="${pageContext.request.contextPath}/controller?command=user-get-liked-albums&userid=${user.getId()}">
                                        <fmt:message key="user.albums"/>
                                    </a>
                                </div>
                                <div class="row justify-content-center mt-1">
                                    <a class="f-col justify-content-center img-link img-link-sm"
                                       style="background-image: url('/system/img/home-artist.png');"
                                       href="${pageContext.request.contextPath}/controller?command=user-get-liked-artists&userid=${user.getId()}">
                                        <fmt:message key="user.artists"/>
                                    </a>
                                    <a class="f-col justify-content-center img-link img-link-sm ml-1"
                                       style="background-image: url('/system/img/home-playlist.png');"
                                       href="${pageContext.request.contextPath}/controller?command=user-get-playlists&userid=${user.getId()}">
                                        <fmt:message key="user.playlists"/>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="f-col justify-content-center h-100">
                    <h4 class="title text-center mb-2">
                        <fmt:message key="user.not.found"/>
                    </h4>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>
