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

<c:if test="${not empty artists}">
    <ul>
        <c:forEach items="${artists}" var="artist">
            <li>
                <img src="/img/artist${artist.getAvatar()}" alt="Artist picture"/>
                <form method="post" action="${pageContext.request.contextPath}/controller?command=artist-get-by-id">
                    <input type="hidden" name="artistid" value="${artist.getId()}"/>
                    <input type="submit" value="${artist.getName()}">
                </form>
            </li>
        </c:forEach>
    </ul>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=artist-get-by-name">
        <input type="hidden" name="name" value="${name}"/>
        <input type="submit" value="Show all">
    </form>
</c:if>
<c:if test="${not empty tracks}">
    <ul>
        <c:forEach items="${tracks}" var="track">
            <li>
                <form method="post" action="${pageContext.request.contextPath}/controller?command=track-get-by-id">
                    <input type="hidden" name="trackid" value="${track.getId()}"/>
                    <input type="submit" value="${track.getName()}">
                </form>
            </li>
        </c:forEach>
    </ul>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=track-get-by-name">
        <input type="hidden" name="name" value="${name}"/>
        <input type="submit" value="Show all">
    </form>
</c:if>
<c:if test="${not empty albums}">
    <ul>
        <c:forEach items="${albums}" var="album">
            <li>
                <img src="/img/album${album.getPicture()}" alt="Album picture"/>
                <form method="post" action="${pageContext.request.contextPath}/controller?command=album-get-by-id">
                    <input type="hidden" name="albumid" value="${album.getId()}"/>
                    <input type="submit" value="${album.getName()}">
                </form>
            </li>
        </c:forEach>
    </ul>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=album-get-by-name">
        <input type="hidden" name="name" value="${name}"/>
        <input type="submit" value="Show all">
    </form>
</c:if>

</body>
</html>
