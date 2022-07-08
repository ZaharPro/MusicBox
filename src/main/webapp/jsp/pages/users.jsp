<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

<c:if test="${command == null}">
    <c:set var="command" value="user-get" scope="request"/>
</c:if>

<div class="container f-col h-100 pt-2 pb-2">
    <div class="card col f-col h-100 pt-3 pb-3 mb-0 bg-dark">
        <h2 class="title text-center mb-2">
            <fmt:message key="users.title"/>
            <span class="h6 text-info">(${userpsr.getCount()})</span>
        </h2>
        <div class="d-flex justify-content-between align-items-center mb-3">
            <div class="btn-group btn-group-sm">
                <a class="btn"
                   href="${pageContext.request.contextPath}/controller?command=user-get-by-role&role=user">
                    <fmt:message key="users.only.users"/>
                </a>
                <a class="btn"
                   href="${pageContext.request.contextPath}/controller?command=user-get-by-role&role=admin">
                    <fmt:message key="users.only.admins"/>
                </a>
                <a class="btn"
                   href="${pageContext.request.contextPath}/controller?command=user-get">
                    <fmt:message key="users.all"/>
                </a>
            </div>
            <form class="form-inline" method="post"
                  action="${pageContext.request.contextPath}/controller?command=user-get-by-login">
                <input class="form-control mr-sm-2" type="search" name="login" aria-label="Search by login"
                       value="${login}">
                <button class="btn" type="submit">
                    <fmt:message key="users.search"/>
                </button>
            </form>
        </div>
        <c:choose>
            <c:when test="${userpsr.hasElements()}">
                <div class="f-col h-100">
                    <table class="table table-striped table-hover mb-auto">
                        <thead class="thead-dark">
                        <tr>
                            <th scope="col"><fmt:message key="user.login"/></th>
                            <th scope="col"><fmt:message key="user.email"/></th>
                            <th scope="col"><fmt:message key="user.status"/></th>
                            <th scope="col"><fmt:message key="user.role"/></th>
                            <th scope="col"><fmt:message key="user.registration"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${userpsr.getElements()}" var="user" varStatus="status">
                            <tr class="tr-link"
                                onclick="window.location='${pageContext.request.contextPath}/controller?command=user-get-by-id&userid=${user.getId()}';">
                                <td>${user.getLogin()}</td>
                                <td>${user.getEmail()}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.getBanned()}">
                                            <fmt:message key="user.banned"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="user.active"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${user.getRole()}</td>
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
                    <h4 class="text-info text-center mb-2">
                        <fmt:message key="users.empty"/>
                    </h4>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>