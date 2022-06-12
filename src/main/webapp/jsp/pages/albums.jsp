<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <title><fmt:message key="title"/></title>
    <c:import url="/jsp/head.jsp"/>
</head>
<body>
<c:import url="/jsp/fragments/navbar.jsp"/>

<c:if test="${command == null}">
    <c:set var="command" value="album-get-by-id" scope="request"/>
</c:if>

<c:if test="${not empty albums}">
    <ul>
        <c:forEach items="${albums}" var="album">
            <li>
                <img src="/img/album${album.getPicture()}" alt="Album picture"/>
                <form method="post" action="${pageContext.request.contextPath}/controller?command=${command}">
                    <input type="hidden" name="albumid" value="${album.getId()}"/>
                    <input type="hidden" name="albumpage" value="${albumpage}"/>
                    <input type="submit" value="${album.getName()}">
                </form>
            </li>
        </c:forEach>
    </ul>
</c:if>

</body>
</html>