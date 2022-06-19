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

<c:if test="${command == null}">
    <c:set var="command" value="playlist-get" scope="request"/>
</c:if>

<div class="container f-col h-100 pt-2 pb-2">
    <div class="card col f-col h-100 pt-3 pb-3 mb-0 bg-dark">
        <h4 class="title text-center mb-2">
            <fmt:message key="playlists.title"/>
        </h4>
        <c:choose>
            <c:when test="${playlistpsr.hasElements()}">
                <div class="f-col h-100">
                    <div class="list-group list-group-flush bg-light h-100 mb-2">
                        <c:forEach items="${playlistpsr.getElements()}" var="playlist">
                            <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                               href="${pageContext.request.contextPath}/controller?command=playlist-get-by-id&playlistid=${playlist.getId()}">
                                    ${playlist.getName()}
                                <c:choose>
                                    <c:when test="${playlist != null && playlist.getPicture()}">
                                        <img class="img-fluid col-2"
                                             src="${pageContext.request.contextPath}/file/img/${playlist.getPicture()}"
                                             alt="Playlist picture">
                                    </c:when>
                                    <c:otherwise>
                                        <img class="img-fluid col-2"
                                             src="${pageContext.request.contextPath}/system/img/playlist-default.png"
                                             alt="Playlist picture">
                                    </c:otherwise>
                                </c:choose>
                            </a>
                        </c:forEach>
                    </div>
                    <c:set var="page" value="${playlistpsr.getPage()}" scope="request"/>
                    <c:set var="maxpage" value="${playlistpsr.getMaxPage()}" scope="request"/>
                    <c:set var="pagename" value="playlistpage" scope="request"/>
                    <c:set var="command" value="${command}" scope="request"/>
                    <c:import url="/jsp/fragments/page-navigation.jsp"/>
                </div>
            </c:when>
            <c:otherwise>
                <div class="col f-col justify-content-center h-100">
                    <h4 class="title text-center mb-2">
                        <fmt:message key="playlists.empty"/>
                    </h4>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>