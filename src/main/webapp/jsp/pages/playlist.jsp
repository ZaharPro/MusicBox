<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<%--<jsp:useBean id="playlist" scope="request" type="com.epam.musicbox.entity.Playlist"/>
<jsp:useBean id="tracks" scope="request" type="java.util.List"/>
<jsp:useBean id="liked" scope="request" type="java.lang.Boolean"/>--%>

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
            <a href="${pageContext.request.contextPath}/controller?command=user-like-playlist"
               onsubmit="<c:set var="trackid" value="${playlist.id}" scope="request"/>">
                    Like
            </a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/controller?command=user-cancel-like-playlist"
               onsubmit="<c:set var="trackid" value="${playlist.id}" scope="request"/>">
                    Like
            </a>
        </c:otherwise>
    </c:choose>

    <c:if test="${tracks != null}">
        <ul>
            <c:forEach items="${tracks}" var="track">
                <li>
                    <a href="${pageContext.request.contextPath}/controller?command=track-get-by-id"
                       onsubmit="<c:set var="trackid" value="${track.id}" scope="request"/>">
                            ${track.name}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</div>
</body>
</html>
