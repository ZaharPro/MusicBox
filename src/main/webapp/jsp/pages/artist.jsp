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
    <div class="card col f-col h-100 pt-3 pb-3 mb-0 bg-dark">
        <div class="row align-items-center pt-3 pb-3">
            <div class="col-lg-2 col-md-2">
                <c:choose>
                    <c:when test="${artist != null && artist.getAvatar() != null}">
                        <img class="card-img" src="${pageContext.request.contextPath}/file/img/${artist.getAvatar()}">
                    </c:when>
                    <c:otherwise>
                        <img class="card-img" src="${pageContext.request.contextPath}/system/img/default-artist.png">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-lg-10 col-md-10 d-flex justify-content-between">
                <h2 class="title">
                    ${artist.getName()}
                </h2>
                <c:choose>
                    <c:when test="${like == false}">
                        <c:set var="cmd" value="user-mark-liked-artist" scope="request"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="cmd" value="user-unmark-liked-artist" scope="request"/>
                    </c:otherwise>
                </c:choose>
                <form method="post"
                      action="${pageContext.request.contextPath}/controller?command=${cmd}">
                    <input type="hidden" name="artistid" value="${artist.getId()}"/>
                    <input type="hidden" name="trackpage" value="${trackpsr.getPage()}"/>
                    <input type="hidden" name="albumpage" value="${albumpsr.getPage()}"/>

                    <div class="btn-group btn-group-sm">
                        <button type="submit" class="btn w-100">
                            <c:choose>
                                <c:when test="${like == false}">
                                    <fmt:message key="artist.mark.liked"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="artist.unmark.liked"/>
                                </c:otherwise>
                            </c:choose>
                        </button>
                        <ct:access role="admin">
                            <a class="btn w-100 ml-1"
                               href="${pageContext.request.contextPath}/controller?command=edit-artist-page&artistid=${artist.getId()}">
                                <fmt:message key="artist.edit"/>
                            </a>
                        </ct:access>
                    </div>
                </form>
            </div>
        </div>
        <c:if test="${trackpsr.hasElements()}">
            <div class="col f-col h-100 pt-3 pb-3 mb-0">
                <h2 class="title text-center mb-2">
                    <fmt:message key="tracks.title"/>
                </h2>
                <div class="f-col h-100">
                    <div class="list-group list-group-flush bg-light h-100 mb-2">
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
                    <c:set var="command"
                           value="artist-get-by-id&artistid=${artist.getId()}&albumpage=${albumpsr.getPage()}"
                           scope="request"/>
                    <c:import url="/jsp/fragments/page-navigation.jsp"/>
                </div>
            </div>
        </c:if>
        <c:if test="${albumpsr.hasElements()}">
            <div class="col f-col h-100 pt-3 pb-3 mb-0">
                <h2 class="title text-center mb-2">
                    <fmt:message key="albums.title"/>
                </h2>
                <div class="f-col h-100">
                    <div class="list-group list-group-flush bg-light h-100 mb-2">
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
                    <c:set var="command"
                           value="artist-get-by-id&artistid=${artist.getId()}&trackpage=${trackpsr.getPage()}"
                           scope="request"/>
                    <c:import url="/jsp/fragments/page-navigation.jsp"/>
                </div>
            </div>
        </c:if>
    </div>
</div>

</body>
</html>
