<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<%--<jsp:useBean id="track" scope="request" type="com.epam.musicbox.entity.Track"/>
<jsp:useBean id="album" scope="request" type="com.epam.musicbox.entity.Album"/>
<jsp:useBean id="liked" scope="request" type="java.lang.Boolean"/>--%>

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
            <a href="${pageContext.request.contextPath}/controller?command=user-like-track"
               onsubmit="<c:set var="trackid" value="${track.id}" scope="request"/>">
                Like
            </a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/controller?command=user-cancel-like-track"
               onsubmit="<c:set var="trackid" value="${track.id}" scope="request"/>">
                Like
            </a>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
