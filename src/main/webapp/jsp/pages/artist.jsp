<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<%--<jsp:useBean id="artist" scope="request" type="com.epam.musicbox.entity.Artist"/>
<jsp:useBean id="albums" scope="request" type="java.util.List"/>
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
    <img src="/img/artist${artist.avatar}" alt="Artist picture"/>
    <p>${artist.name}</p>
    <c:choose>
        <c:when test="${liked == false}">
            <a href="${pageContext.request.contextPath}/controller?command=user-like-artist"
               onsubmit="<c:set var="trackid" value="${artist.id}" scope="request"/>">
                    Like
            </a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/controller?command=user-cancel-like-artist"
               onsubmit="<c:set var="trackid" value="${artist.id}" scope="request"/>">
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

    <c:if test="${albums != null}">
        <ul>
            <c:forEach items="${albums}" var="album">
                <li>
                    <a href="${pageContext.request.contextPath}/controller?command=album-get-by-id"
                       onsubmit="<c:set var="albumid" value="${album.id}" scope="request"/>">
                            ${album.name}
                    </a>
                    <img src="/img/album${album.picture}" alt="Album picture"/>
                </li>
            </c:forEach>
        </ul>
    </c:if>

</div>
</body>
</html>
