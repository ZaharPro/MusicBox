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

<div class="container flex-col h-100 pt-3 pb-3">
    <div class="col card pt-0 pb-3 mb-0 flex-col h-100 bg-dark">
        <div class="row pt-3 pb-3" style="border-bottom: 1px solid #dd2476;">
            <div class="col-lg-2 col-md-2">
                <img class="card-img" src="/img/artist/${artist.getAvatar()}" alt="Artist avatar">
            </div>
            <div class="col-lg-10 col-md-10 d-flex justify-content-between align-items-center">
                <h2 class="card-title">
                    ${artist.getName()}
                </h2>
                <div class="btn-group">
                    <c:choose>
                        <c:when test="${like == false}">
                            <form method="post" class="m-0"
                                  action="${pageContext.request.contextPath}/controller?command=user-mark-liked-artist">
                                <input type="hidden" name="artistid" value="${artist.getId()}"/>
                                <input type="hidden" name="trackpage" value="${trackpsr.getPage()}"/>
                                <button type="submit" class="btn btn-sm">
                                    <fmt:message key="artist.mark.liked"/>
                                </button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form method="post" class="m-0"
                                  action="${pageContext.request.contextPath}/controller?command=user-unmark-liked-artist">
                                <input type="hidden" name="artistid" value="${artist.getId()}"/>
                                <input type="hidden" name="trackpage" value="${trackpsr.getPage()}"/>
                                <button type="submit" class="btn btn-sm">
                                    <fmt:message key="artist.unmark.liked"/>
                                </button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                    <ct:access role="admin">
                        <div>
                            <a class="btn btn-sm ml-1"
                               href="${pageContext.request.contextPath}/controller?command=edit-artist-page&artistid=${artist.getId()}">
                                <fmt:message key="artist.edit"/>
                            </a>
                        </div>
                    </ct:access>
                </div>
            </div>
        </div>
        <h4 class="card-title text-center mt-3">
            <fmt:message key="tracks.title"/>
        </h4>
        <c:choose>
            <c:when test="${trackpsr.hasElements()}">
                <div class="flex-col justify-content-between h-100">
                    <div class="list-group list-group-flush bg-light">
                        <c:forEach items="${trackpsr.getElements()}" var="track">
                            <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                               href="${pageContext.request.contextPath}/controller?command=track-get-by-id&trackid=${track.getId()}&trackpage=${trackpsr.getPage()}">
                                    ${track.getName()}
                            </a>
                        </c:forEach>
                    </div>
                    <c:set var="page" value="${trackpsr.getPage()}" scope="request"/>
                    <c:set var="maxpage" value="${trackpsr.getMaxPage()}" scope="request"/>
                    <c:set var="pagename" value="trackpage" scope="request"/>
                    <c:set var="command" value="track-get" scope="request"/>
                    <c:import url="/jsp/fragments/page-navigation.jsp"/>
                </div>
            </c:when>
            <c:otherwise>
                <div class="flex-col justify-content-center h-100">
                    <h4 class="card-title text-center">
                        <fmt:message key="tracks.not.found"/>
                    </h4>
                </div>
            </c:otherwise>
        </c:choose>
        <span class="w-100 m-0 pt-0" style="border-bottom: 1px solid #dd2476;"></span>
        <h4 class="card-title text-center mt-3">
            <fmt:message key="albums.title"/>
        </h4>
        <c:choose>
            <c:when test="${albumpsr.hasElements()}">
                <div class="flex-col justify-content-between h-100">
                    <div class="list-group list-group-flush bg-light">
                        <c:forEach items="${albumpsr.getElements()}" var="album">
                            <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                               href="${pageContext.request.contextPath}/controller?command=album-get-by-id&albumid=${album.getId()}&albumpage=${albumpsr.getPage()}">
                                    ${album.getName()}
                            </a>
                        </c:forEach>
                    </div>
                    <c:set var="page" value="${albumpsr.getPage()}" scope="request"/>
                    <c:set var="maxpage" value="${albumpsr.getMaxPage()}" scope="request"/>
                    <c:set var="pagename" value="albumpage" scope="request"/>
                    <c:set var="command" value="album-get" scope="request"/>
                    <c:import url="/jsp/fragments/page-navigation.jsp"/>
                </div>
            </c:when>
            <c:otherwise>
                <div class="flex-col justify-content-center h-100">
                    <h4 class="card-title text-center">
                        <fmt:message key="albums.not.found"/>
                    </h4>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>
