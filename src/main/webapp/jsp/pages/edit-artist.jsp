<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<%--<jsp:useBean id="artist" scope="request" type="com.epam.musicbox.entity.Artist"/>--%>

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
               value="${artist.name}"
        </c:if>>

        <label for="artistAvatar"></label>
        <input type="text" id="artistAvatar" name="avatar" required
        <c:if test="${artist != null}">
               value="${artist.avatar}"
        </c:if>>

        <input type="submit">
        <c:if test="${artist != null}">
            <a href="${pageContext.request.contextPath}/controller?command=artist-delete"
               onsubmit="<c:set var="artistid" value="${artist.id}" scope="request"/>">
                Delete
            </a>
        </c:if>
    </form>
</div>

</body>
</html>
