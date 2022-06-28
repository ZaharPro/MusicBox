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
    <c:import url="/jsp/fragments/head.jsp"/>
</head>
<body>
<c:import url="/jsp/fragments/navbar.jsp"/>

<div class="container f-col h-100 pt-2 pb-2">
    <div class="card col f-col h-100 pt-3 pb-3 mb-0 bg-dark align-items-center">
        <h2 class="title">
            <fmt:message key="admin.title"/>
        </h2>

        <h4 class="mt-3">
            <fmt:message key="admin.manage"/>
        </h4>
        <div class="btn-group btn-group-sm col-2 f-col">
            <a class="btn w-100 mt-2"
               href="${pageContext.request.contextPath}/controller?command=edit-track-page">
                <fmt:message key="admin.add.track"/>
            </a>
            <a class="btn w-100 mt-2"
               href="${pageContext.request.contextPath}/controller?command=edit-album-page">
                <fmt:message key="admin.add.album"/>
            </a>
            <a class="btn w-100 mt-2"
               href="${pageContext.request.contextPath}/controller?command=edit-artist-page">
                <fmt:message key="admin.add.artist"/>
            </a>
            <a class="btn w-100 mt-2"
               href="${pageContext.request.contextPath}/controller?command=user-get">
                <fmt:message key="admin.users"/>
            </a>
        </div>
    </div>
</div>

</body>
</html>
