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
    <form method="post" id="editTrackForm" action="${pageContext.request.contextPath}/controller?command=track-save">
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

        <c:choose>
            <c:when test="${album != null}">
                <input type="hidden" name="albumid" value="${album.id}"/>
                <input type="submit">
            </c:when>
            <c:otherwise>
                <p>Choose album</p>
            </c:otherwise>
        </c:choose>
    </form>
    <c:if test="${track != null}">
        <form method="post" action="${pageContext.request.contextPath}/controller?command=track-delete">
            <input type="hidden" name="trackid" value="${track.id}"/>
            <input type="submit" form="editTrackForm" value="Delete">
        </form>
    </c:if>
    <c:if test="${albums != null}">
        <ul>
            <c:forEach items="${albums}" var="album">
                <li>
                    <form method="post" action="${pageContext.request.contextPath}/controller?command=edit-track-page">
                        <input type="hidden" name="trackid"
                                <c:if test="${track != null}">
                                    value="${track.id}"
                                </c:if>/>
                        <input type="hidden" name="albumid" value="${album.id}"/>
                        <input type="submit" value="${album.name}">
                    </form>
                </li>
            </c:forEach>
        </ul>
    </c:if>

</div>

</body>
</html>
