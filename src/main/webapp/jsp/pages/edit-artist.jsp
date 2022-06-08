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
    <form method="post" action="${pageContext.request.contextPath}/controller?command=artist-save">
        <label for="artistName"></label>
        <input type="text" id="artistName" name="name" required
        <c:if test="${artist != null}">
               value="${artist.getName()}"
        </c:if>>

        <label for="artistAvatar"></label>
        <input type="text" id="artistAvatar" name="avatar" required
        <c:if test="${artist != null}">
               value="${artist.getAvatar()}"
        </c:if>>

        <input type="submit">
    </form>
    <c:if test="${artist != null}">
        <form method="post" action="${pageContext.request.contextPath}/controller?command=artist-delete">
            <input type="hidden" name="artistid" value="${artist.getId()}"/>
            <input type="submit" value="Delete">
        </form>
    </c:if>

    <c:if test="${not empty tracks}">
        <p>Choose track</p>
        <ul>
            <c:forEach items="${tracks}" var="track">
                <li>
                    <form method="post"
                          action="${pageContext.request.contextPath}/controller?command=edit-artist-page">
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
