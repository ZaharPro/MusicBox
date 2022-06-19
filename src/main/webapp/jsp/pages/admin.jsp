<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="custom-tags" %>

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

<div class="container f-col h-100 pt-2 pb-2">
    <div class="col card pt-3 pb-3 m-0 bg-dark">
        <h2 class="title text-center>">
            <fmt:message key="admin.title"/>
        </h2>

        <div class="list-group list-group-flush bg-transparent">
            <a class="list-group-item list-group-item-action"
               href="${pageContext.request.contextPath}/controller?command=edit-track-page">
                <fmt:message key="admin.add.track"/>
            </a>
            <a class="list-group-item list-group-item-action"
               href="${pageContext.request.contextPath}/controller?command=edit-album-page">
                <fmt:message key="admin.add.album"/>
            </a>
            <a class="list-group-item list-group-item-action"
               href="${pageContext.request.contextPath}/controller?command=edit-artist-page">
                <fmt:message key="admin.add.artist"/>
            </a>
            <a class="list-group-item list-group-item-action"
               href="${pageContext.request.contextPath}/controller?command=user-get">
                <fmt:message key="admin.users"/>
            </a>
        </div>
    </div>
</div>

</body>
</html>
