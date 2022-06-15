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

<div>
    <img src="/img/playlist${playlist.getPicture()}" alt="Playlist picture"/>
    <p>${playlist.getName()}</p>
    <c:choose>
        <c:when test="${like == false}">
            <form method="post" action="${pageContext.request.contextPath}/controller?command=user-add-playlist">
                <input type="hidden" name="playlistid" value="${playlist.getId()}"/>
                <input type="hidden" name="trackpage" value="${trackpage}"/>
                <input type="submit" value="Add">
            </form>
        </c:when>
        <c:otherwise>
            <form method="post" action="${pageContext.request.contextPath}/controller?command=user-remove-playlist">
                <input type="hidden" name="playlistid" value="${playlist.getId()}"/>
                <input type="hidden" name="trackpage" value="${trackpage}"/>
                <input type="submit" value="Remove">
            </form>
        </c:otherwise>
    </c:choose>
    <ct:access role="admin">
        <a class="btn btn-sm w-100 mt-1"
           href="${pageContext.request.contextPath}/controller?command=edit-playlist-page&playlistid=${playlist.getId()}">
            <fmt:message key="playlist.edit"/>
        </a>
    </ct:access>
    <c:if test="${not empty tracks}">
        <ul class="list-group list-group-flush bg-light">
            <c:forEach items="${tracks}" var="track">
                <li class="list-group-item">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/controller?command=track-get-by-id&trackid=${track.getId()}">
                            ${track.getName()}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</div>
</body>
</html>
