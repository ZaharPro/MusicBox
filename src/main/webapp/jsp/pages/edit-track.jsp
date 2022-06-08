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
               value="${track.getName()}"
        </c:if>>

        <label for="trackPath"></label>
        <input type="text" id="trackPath" name="path" required
        <c:if test="${track != null}">
               value="${track.getPath()}"
        </c:if>>

        <c:if test="${track != null}">
            <audio controls>
                <source src="${track.getPath()}" type="audio/mpeg">
            </audio>
        </c:if>

        <c:if test="${album != null}">
            <c:set var="albumid" value="${album.getId()}" scope="request"/>
            <div>
                <p>${album.getName()}</p>
                <img src="/img/album${album.getPicture()}" alt="Album picture"/>
            </div>
        </c:if>

        <c:if test="${album != null}">
            <input type="hidden" name="albumid" value="${album.getId()}"/>
            <input type="submit">
        </c:if>
    </form>
    <c:if test="${track != null}">
        <form method="post" action="${pageContext.request.contextPath}/controller?command=track-delete">
            <input type="hidden" name="trackid" value="${track.getId()}"/>
            <input type="submit" form="editTrackForm" value="Delete">
        </form>
    </c:if>
    <p>Choose album</p>
    <c:if test="${not empty albums}">
        <ul>
            <c:forEach items="${albums}" var="album">
                <li>
                    <form method="post" action="${pageContext.request.contextPath}/controller?command=edit-track-page">
                        <input type="hidden" name="trackid"
                                <c:if test="${track != null}">
                                    value="${track.getId()}"
                                </c:if>/>
                        <input type="hidden" name="albumid" value="${album.getId()}"/>
                        <input type="hidden" name="albumpage" value="${albumpage}"/>
                        <input type="submit" value="${album.getName()}">
                    </form>
                </li>
            </c:forEach>
        </ul>
    </c:if>

</div>

</body>
</html>
