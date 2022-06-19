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

<div class="container f-col h-100 pt-3 pb-3">
    <div class="col card pt-3 pb-3 m-0 bg-dark">
        <h2 class="title">
            <fmt:message key="user.title"/>
        </h2>

        <h4 class="text-primary mt-1">
            <fmt:message key="user.login"/>
            <span class="text-info h6">
                ${user.getLogin()}
            </span>
        </h4>

        <h4 class="text-primary mt-1">
            <fmt:message key="user.email"/>
            <span class="text-info h6">
                ${user.getEmail()}
            </span>
        </h4>

        <c:if test="${user.getBanned() == true}">
            <h4 class="text-primary mt-1">
                <fmt:message key="user.status"/>
                <span class="text-danger h6">
                    <fmt:message key="user.banned"/>
                </span>
            </h4>
        </c:if>

        <h4 class="text-primary mt-1">
            <fmt:message key="user.registration"/>
            <span class="text-info h6">
                ${user.getRegistration()}
            </span>
        </h4>

        <h4 class="text-primary mt-1">
            <fmt:message key="user.role"/>
            <span class="text-info h6">
                ${role}
            </span>
        </h4>
        <h4 class="text-primary mt-1">
            <fmt:message key="user.ban.title"/>
        </h4>
        <form method="post" action="${pageContext.request.contextPath}/controller?command=user-set-ban">
            <input type="hidden" name="userid" value="${user.getId()}"/>
            <c:choose>
                <c:when test="${user.getBanned() == true}">
                    <input type="hidden" value="false"/>
                    <button type="submit" class="btn btn-danger">
                        <fmt:message key="user.unban"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <input type="hidden" value="true"/>
                    <button type="submit" class="btn btn-danger">
                        <fmt:message key="user.ban"/>
                    </button>
                </c:otherwise>
            </c:choose>
        </form>

        <h4 class="text-primary mt-1">
            <fmt:message key="user.data"/>
        </h4>
        <div class="list-group list-group-flush bg-transparent">
            <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
               href="${pageContext.request.contextPath}/controller?command=user-get-liked-tracks&userid=${user.getId()}">
                <fmt:message key="user.show.liked.tracks"/>
            </a>
            <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
               href="${pageContext.request.contextPath}/controller?command=user-get-liked-albums&userid=${user.getId()}">
                <fmt:message key="user.show.liked.albums"/>
            </a>
            <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
               href="${pageContext.request.contextPath}/controller?command=user-get-liked-artists&userid=${user.getId()}">
                <fmt:message key="user.show.liked.artists"/>
            </a>
            <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
               href="${pageContext.request.contextPath}/controller?command=user-get-playlists&userid=${user.getId()}">
                <fmt:message key="user.show.playlists"/>
            </a>
        </div>

        <h4 class="text-primary mt-1">
            <fmt:message key="user.settings"/>
        </h4>
        <div class="list-group list-group-flush bg-transparent">
            <a class="list-group-item list-group-item-action bg-light border-0"
               href="${pageContext.request.contextPath}/controller?command=change-password-page&userid=${user.getId()}">
                <fmt:message key="user.change.password"/>
            </a>
        </div>
    </div>
</div>

</body>
</html>
