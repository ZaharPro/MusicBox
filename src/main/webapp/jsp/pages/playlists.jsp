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

<c:if test="${not empty playlists}">
    <ul>
        <c:forEach items="${playlists}" var="playlist">
            <li>
                <img src="/img/playlist${playlist.getPicture()}" alt="Playlist picture"/>
                <form method="post" action="${pageContext.request.contextPath}/controller?command=playlist-get-by-id">
                    <input type="hidden" name="playlistid" value="${playlist.getId()}"/>
                    <input type="submit" value="${playlist.getName()}">
                </form>
            </li>
        </c:forEach>
    </ul>
</c:if>

</body>
</html>