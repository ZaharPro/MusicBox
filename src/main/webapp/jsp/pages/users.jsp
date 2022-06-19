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

<div class="container f-col h-100 pt-3 pb-3">
    <div class="card col f-col h-100 pt-3 pb-3 mb-0 bg-dark">
        <h4 class="title text-center">
            <fmt:message key="users.title"/>
        </h4>
        <c:choose>
            <c:when test="${userpsr.hasElements()}">
                <div class="f-col h-100">
                    <table class="table table-striped table-hover">
                        <thead class="thead-dark">
                        <tr>
                            <th scope="col"><fmt:message key="users.login"/></th>
                            <th scope="col"><fmt:message key="users.email"/></th>
                            <th scope="col"><fmt:message key="users.banned"/></th>
                            <th scope="col"><fmt:message key="users.role"/></th>
                            <th scope="col"><fmt:message key="users.registration"/>></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${userpsr.getElements()}" var="user" varStatus="status">
                            <tr onclick="window.location='${pageContext.request.contextPath}/controller?command=user-get-by-id&userid=${user.getId()}';">
                                <td>${user.getLogin()}</td>
                                <td>${user.getEmail()}</td>
                                <td>${user.getBanned()}</td>
                                <td>${userpsr.getRoles().get(status)}</td>
                                <td>${user.getRegistration()}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <c:set var="page" value="${userpsr.getPage()}" scope="request"/>
                    <c:set var="maxpage" value="${userpsr.getMaxPage()}" scope="request"/>
                    <c:set var="pagename" value="userpage" scope="request"/>
                    <c:set var="command" value="${command}" scope="request"/>
                    <c:import url="/jsp/fragments/page-navigation.jsp"/>
                </div>
            </c:when>
            <c:otherwise>
                <div class="col f-col justify-content-center h-100">
                    <h4 class="title text-center">
                        <fmt:message key="not.found"/>
                    </h4>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>