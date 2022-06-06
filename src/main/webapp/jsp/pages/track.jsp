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
    <img src="/img/album${album.picture}" alt="Album picture"/>
    <p>${album.name}</p>

    <p>${track.name}</p>
    <p>${track.path}</p>

    <audio controls>
        <source src="${track.path}" type="audio/mpeg">
    </audio>

    <c:choose>
        <c:when test="${liked == false}">
            <form method="post" action="${pageContext.request.contextPath}/controller?command=user-like-track">
                <input type="hidden" name="trackid" value="${track.id}"/>
                <input type="hidden" name="albumid" value="${album.id}"/>
                <input type="submit" value="Like">
            </form>
        </c:when>
        <c:otherwise>
            <form method="post" action="${pageContext.request.contextPath}/controller?command=user-cancel-like-track">
                <input type="hidden" name="trackid" value="${track.id}"/>
                <input type="hidden" name="albumid" value="${album.id}"/>
                <input type="submit" value="Cancel like">
            </form>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
