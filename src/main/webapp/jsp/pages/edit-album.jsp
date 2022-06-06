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
    <form method="post" action="${pageContext.request.contextPath}/controller?command=album-save">
        <label for="albumName"></label>
        <input type="text" id="albumName" name="name" required
        <c:if test="${album != null}">
               value="${album.name}"
        </c:if>>

        <label for="albumPicture"></label>
        <input type="text" id="albumPicture" name="picture" required
        <c:if test="${album != null}">
               value="${album.picture}"
        </c:if>>

        <input type="submit">
        <c:if test="${album != null}">
            <form method="post" action="${pageContext.request.contextPath}/controller?command=album-delete">
                <input type="hidden" name="albumid" value="${album.id}"/>
                <input type="submit" value="Delete">
            </form>
        </c:if>
    </form>
</div>

</body>
</html>
