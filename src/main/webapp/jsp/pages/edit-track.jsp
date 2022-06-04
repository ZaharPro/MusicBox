<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<%--<jsp:useBean id="track" scope="request" type="com.epam.musicbox.entity.Track"/>
<jsp:useBean id="album" scope="request" type="com.epam.musicbox.entity.Album"/>--%>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <title><fmt:message key="title"/></title>
    <c:import url="/jsp/head.jsp"/>
</head>
<body>
<c:import url="/jsp/fragments/navbar.jsp"/>

<div>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=track-save">
        <label for="trackName"></label>
        <input type="text" id="trackName" name="name" required
        <c:if test="${track != null}">
               value="${track.name}"
        </c:if>>

        <label for="trackPath"></label>
        <input type="text" id="trackPath" name="path" required
        <c:if test="${track != null}">
               value="${track.path}"
        </c:if>>

        <c:if test="${album != null}">
            <c:set var="albumid" value="${album.id}" scope="request"/>
            <div>
                <p>${album.name}</p>
                <img src="/img/album${album.picture}" alt="Album picture"/>
            </div>
        </c:if>

        <a href="${pageContext.request.contextPath}/controller?command=album-get"
           onsubmit="
           <c:set var="nextcommand" value="edit-track-page" scope="request"/>
           <c:set var="trackid" value="${track.id}" scope="request"/>
                   <c:set var="albumid" value="${track.albumId}" scope="request"/>
                   <c:set var="page" value="0" scope="request"/>">
            Set album
        </a>

        <c:choose>
            <c:when test="${album != null}">
                <input type="submit">
                <c:if test="${track != null}">
                    <a href="${pageContext.request.contextPath}/controller?command=track-delete"
                       onsubmit="<c:set var="trackid" value="${track.id}" scope="request"/>">
                        Delete
                    </a>
                </c:if>
            </c:when>
            <c:otherwise>
                <p>Choose album</p>
            </c:otherwise>
        </c:choose>
    </form>
</div>

</body>
</html>
