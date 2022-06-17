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
    <c:set var="command" value="user-get" scope="request"/>
</c:if>

<div class="container d-flex flex-column h-100 pt-3 pb-3">
    <div class="col card d-flex flex-column h-100 pt-3 pb-3 mb-0 bg-dark">
        <h4 class="card-title text-center">
            <fmt:message key="users.title"/>
        </h4>
        <c:choose>
            <c:when test="${userpsr.hasElements()}">
                <div class="d-flex flex-column justify-content-between h-100">
                    <div class="list-group list-group-flush bg-light">
                        <div class="list-group-item d-flex justify-content-between align-items-center">
                            <span class="col-3">
                                <fmt:message key="users.login"/>
                            </span>
                            <span class="col-3">
                                <fmt:message key="users.email"/>
                            </span>
                            <span class="col-1">
                                <fmt:message key="users.banned"/>
                            </span>
                            <span class="col-2">
                                <fmt:message key="users.role"/>
                            </span>
                            <span class="col-3">
                                <fmt:message key="users.registration"/>
                            </span>
                        </div>
                        <c:forEach items="${userpsr.getElements()}" var="user" varStatus="status">
                            <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                               href="${pageContext.request.contextPath}/controller?command=user-get-by-id&userid=${user.getId()}">
                                <span class="col-3">${user.getLogin()}</span>
                                <span class="col-3">${user.getEmail()}</span>
                                <span class="col-1">${user.getBanned()}</span>
                                <span class="col-2">${userpsr.getRoles().get(status)}</span>
                                <span class="col-3">${user.getRegistration()}</span>
                            </a>
                        </c:forEach>
                    </div>
                    <c:set var="page" value="${userpsr.getPage()}" scope="request"/>
                    <c:set var="maxpage" value="${userpsr.getMaxPage()}" scope="request"/>
                    <c:set var="pagename" value="userpage" scope="request"/>
                    <c:import url="/jsp/fragments/page-navigation.jsp"/>
                </div>
            </c:when>
            <c:otherwise>
                <div class="d-flex flex-column justify-content-center h-100">
                    <h4 class="card-title text-center">
                        <fmt:message key="users.not.found"/>
                    </h4>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>