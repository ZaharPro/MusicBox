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
<c:import url="/jsp/fragments/navbar.jsp"/>

<div class="container f-col h-100 py-2">
    <div class="card col f-col h-100 py-3 mb-0 bg-dark">
        <h2 class="title text-center mb-2">
            <fmt:message key="search.title"/>
        </h2>
        <div class="d-flex justify-content-center">
            <span class="h6 text-white mr-2"><fmt:message key="search.name"/></span>
            <span class="h6 text-info">${name}</span>
        </div>
        <c:choose>
            <c:when test="${trackpsr.hasElements() or albumpsr.hasElements() or artistpsr.hasElements()}">
                <c:if test="${trackpsr.hasElements()}">
                    <div class="col f-col h-100 py-3 mb-0">
                        <h2 class="title text-center mb-2">
                            <fmt:message key="tracks.title"/>
                            <span class="h6 text-info">(${trackpsr.getCount()})</span>
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
                            <c:set var="command" value="track-get-by-name&name=${name}" scope="request"/>
                            <c:import url="/jsp/fragments/page-navigation.jsp"/>
                        </div>
                    </div>
                </c:if>
                <c:if test="${albumpsr.hasElements()}">
                    <div class="col f-col h-100 py-3 mb-0">
                        <h2 class="title text-center mb-2">
                            <fmt:message key="albums.title"/>
                            <span class="h6 text-info">(${albumpsr.getCount()})</span>
                        </h2>
                        <div class="f-col h-100">
                            <div class="list-group list-group-flush bg-light h-100 mb-2">
                                <c:forEach items="${albumpsr.getElements()}" var="album">
                                    <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                                       href="${pageContext.request.contextPath}/controller?command=album-get-by-id&albumid=${album.getId()}&albumpage=${albumpsr.getPage()}">
                                            ${album.getName()}
                                        <c:choose>
                                            <c:when test="${album != null && album.getPicture() != null}">
                                                <img class="img-fluid col-1"
                                                     src="${pageContext.request.contextPath}/file/img/${album.getPicture()}">
                                            </c:when>
                                            <c:otherwise>
                                                <img class="img-fluid col-1"
                                                     src="${pageContext.request.contextPath}/system/img/default-album.png">
                                            </c:otherwise>
                                        </c:choose>
                                    </a>
                                </c:forEach>
                            </div>
                            <c:set var="page" value="${albumpsr.getPage()}" scope="request"/>
                            <c:set var="maxpage" value="${albumpsr.getMaxPage()}" scope="request"/>
                            <c:set var="pagename" value="albumpage" scope="request"/>
                            <c:set var="command" value="album-get-by-name&name=${name}" scope="request"/>
                            <c:import url="/jsp/fragments/page-navigation.jsp"/>
                        </div>
                    </div>
                </c:if>
                <c:if test="${artistpsr.hasElements()}">
                    <div class="col f-col h-100 py-3 mb-0">
                        <h2 class="title text-center mb-2">
                            <fmt:message key="artists.title"/>
                            <span class="h6 text-info">(${artistpsr.getCount()})</span>
                        </h2>
                        <div class="f-col h-100">
                            <div class="list-group list-group-flush bg-light h-100 mb-2">
                                <c:forEach items="${artistpsr.getElements()}" var="artist">
                                    <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                                       href="${pageContext.request.contextPath}/controller?command=artist-get-by-id&artistid=${artist.getId()}&artistpage=${artistpsr.getPage()}">
                                            ${artist.getName()}
                                        <c:choose>
                                            <c:when test="${artist != null && artist.getAvatar() != null}">
                                                <img class="img-fluid col-1"
                                                     src="${pageContext.request.contextPath}/file/img/${artist.getAvatar()}">
                                            </c:when>
                                            <c:otherwise>
                                                <img class="img-fluid col-1"
                                                     src="${pageContext.request.contextPath}/system/img/default-artist.png">
                                            </c:otherwise>
                                        </c:choose>
                                    </a>
                                </c:forEach>
                            </div>
                            <c:set var="page" value="${artistpsr.getPage()}" scope="request"/>
                            <c:set var="maxpage" value="${artistpsr.getMaxPage()}" scope="request"/>
                            <c:set var="pagename" value="artistpage" scope="request"/>
                            <c:set var="command" value="artist-get-by-name&name=${name}" scope="request"/>
                            <c:import url="/jsp/fragments/page-navigation.jsp"/>
                        </div>
                    </div>
                </c:if>
            </c:when>
            <c:otherwise>
                <div class="col f-col justify-content-center h-100">
                    <h4 class="text-info text-center mb-2">
                        <fmt:message key="search.empty"/>
                    </h4>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>