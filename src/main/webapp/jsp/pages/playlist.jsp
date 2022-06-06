<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <title><fmt:message key="title"/></title>
    <c:import url="/jsp/head.jsp"/>
</head>
<body>
<c:import url="/jsp/fragments/navbar.jsp"/>

<div>
    <img src="/img/playlist${playlist.picture}" alt="Playlist picture"/>
    <p>${playlist.name}</p>
    <c:choose>
        <c:when test="${liked == false}">
            <form method="post" action="${pageContext.request.contextPath}/controller?command=user-like-playlist">
                <input type="hidden" name="playlistid" value="${playlist.id}"/>
                <input type="hidden" name="trackpage" value="${trackpage}"/>
                <input type="submit" value="Like">
            </form>
        </c:when>
        <c:otherwise>
            <form method="post" action="${pageContext.request.contextPath}/controller?command=user-cancel-like-playlist">
                <input type="hidden" name="playlistid" value="${playlist.id}"/>
                <input type="hidden" name="trackpage" value="${trackpage}"/>
                <input type="submit" value="Cancel like">
            </form>
        </c:otherwise>
    </c:choose>

    <c:if test="${tracks != null}">
        <ul>
            <c:forEach items="${tracks}" var="track">
                <li>
                    <form method="post" action="${pageContext.request.contextPath}/controller?command=track-get-by-id">
                        <input type="hidden" name="trackid" value="${track.id}"/>
                        <input type="submit" value="${track.name}">
                    </form>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</div>
</body>
</html>
