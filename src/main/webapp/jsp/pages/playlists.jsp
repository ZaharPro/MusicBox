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

<div class="container d-flex flex-column h-100 pt-3 pb-3">
    <div class="col card d-flex flex-column h-100 pt-3 pb-3 mb-0 bg-dark">
        <h4 class="card-title text-center">
            <fmt:message key="playlists.title"/>
        </h4>
        <c:choose>
            <c:when test="${playlistpsr.hasElements()}">
                <div class="d-flex flex-column justify-content-between h-100">
                    <div class="list-group list-group-flush bg-light">
                        <c:forEach items="${playlistpsr.getElements()}" var="playlist">
                            <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                               href="${pageContext.request.contextPath}/controller?command=playlist-get-by-id&playlistid=${playlist.getId()}">
                                    ${playlist.getName()}

                                <img class="img-fluid d-block" style="max-width: 2.5rem"
                                     src="/img/playlist/${playlist.getPicture()}"
                                     alt="Playlist picture">
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
                <div class="d-flex flex-column justify-content-center h-100">
                    <h4 class="card-title text-center">
                        <fmt:message key="playlists.not.found"/>
                    </h4>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>