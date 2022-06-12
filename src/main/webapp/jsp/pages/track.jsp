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

<div>
    <img src="/img/album${album.getPicture()}" alt="Album picture"/>
    <p>${album.getName()}</p>

    <p>${track.getName()}</p>
    <p>${track.getPath()}</p>

    <audio controls>
        <source src="${track.getPath()}" type="audio/mpeg">
    </audio>

    <c:choose>
        <c:when test="${like == false}">
            <form method="post" action="${pageContext.request.contextPath}/controller?command=user-like-track">
                <input type="hidden" name="trackid" value="${track.getId()}"/>
                <input type="hidden" name="albumid" value="${album.getId()}"/>
                <input type="submit" value="Like">
            </form>
        </c:when>
        <c:otherwise>
            <form method="post" action="${pageContext.request.contextPath}/controller?command=user-cancel-like-track">
                <input type="hidden" name="trackid" value="${track.getId()}"/>
                <input type="hidden" name="albumid" value="${album.getId()}"/>
                <input type="submit" value="Cancel like">
            </form>
        </c:otherwise>
    </c:choose>

    <c:if test="${admin != null}">
        <form method="post" action="${pageContext.request.contextPath}/controller?command=edit-track-page">
            <input type="hidden" name="trackid" value="${track.getId()}"/>
            <input type="submit" value="Edit">
        </form>
    </c:if>

</div>
</body>
</html>
