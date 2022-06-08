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

<c:if test="${not empty users}">
    <ul>
        <c:forEach items="${users}" var="user">
            <li>
                <p>${user.getLogin()}</p>
                <p>${user.getEmail()}</p>
                <p>${user.getBanned()}</p>
                <p>${user.getRegistration()}</p>
                <form method="post" action="${pageContext.request.contextPath}/controller?command=user-get-by-id">
                    <input type="hidden" name="userid" value="${user.getId()}"/>
                    <input type="submit" value="Show user">
                </form>
            </li>
        </c:forEach>
    </ul>
</c:if>

</body>
</html>