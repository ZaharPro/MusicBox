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

<c:if test="${command == null}">
    <c:set var="command" value="artist-get-by-id" scope="request"/>
</c:if>

<c:if test="${not empty artists}">
    <ul>
        <c:forEach items="${artists}" var="artist">
            <li>
                <img src="/img/artist${artist.getAvatar()}" alt="Artist picture"/>
                <form method="post" action="${pageContext.request.contextPath}/controller?command=${command}">
                    <input type="hidden" name="artistid" value="${artist.getId()}"/>
                    <input type="hidden" name="artistpage" value="${artistpage}"/>
                    <input type="submit" value="${artist.getName()}">
                </form>
            </li>
        </c:forEach>
    </ul>
</c:if>

</body>
</html>