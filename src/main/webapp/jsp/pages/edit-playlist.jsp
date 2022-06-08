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
    <form method="post" action="${pageContext.request.contextPath}/controller?command=playlist-save">
        <label for="playlistName"></label>
        <input type="text" id="playlistName" name="name" required
        <c:if test="${playlist != null}">
               value="${playlist.getName()}"
        </c:if>>

        <label for="playlistPath"></label>
        <input type="text" id="playlistPath" name="path" required
        <c:if test="${playlist != null}">
               value="${playlist.getPath()}"
        </c:if>>

        <c:if test="${track != null}">
            <c:set var="trackid" value="${track.getId()}" scope="request"/>
            <div>
                <p>${track.getName()}</p>
                <audio controls>
                    <source src="${track.getPath()}" type="audio/mpeg">
                </audio>
            </div>
        </c:if>

        <c:if test="${track != null}">
            <input type="hidden" name="trackid" value="${track.getId()}"/>
            <input type="submit" value="Submit">
        </c:if>
    </form>
    <c:if test="${playlist != null}">
        <form method="post" action="${pageContext.request.contextPath}/controller?command=playlist-delete">
            <input type="hidden" name="playlistid" value="${playlist.getId()}"/>
            <input type="submit" value="Delete">
        </form>
    </c:if>
    <p>Choose track</p>
    <c:if test="${not empty tracks}">
        <ul>
            <c:forEach items="${tracks}" var="track">
                <li>
                    <form method="post"
                          action="${pageContext.request.contextPath}/controller?command=edit-playlist-page">
                        <input type="hidden" name="playlistid"
                                <c:if test="${playlist != null}">
                                    value="${playlist.getId()}"
                                </c:if>/>
                        <input type="hidden" name="trackid" value="${track.getId()}"/>
                        <input type="hidden" name="trackpage" value="${trackpage}"/>
                        <input type="submit" value="${track.getName()}">
                    </form>
                </li>
            </c:forEach>
        </ul>
    </c:if>

</div>

</body>
</html>
