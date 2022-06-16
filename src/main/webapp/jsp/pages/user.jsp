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

<div class="container flex-col h-100 pt-3 pb-3">
    <div class="col card pt-3 pb-3 m-0 flex-col h-100 bg-dark">
        <h2 class="card-title">
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
                <a class="page-link border-0 p-1 bg-transparent" href="${user.getEmail()}">
                    ${user.getEmail()}
                </a>
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

        <ct:access role="admin">
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

            <c:choose>
                <c:when test="${admin == user.getId()}">
                    <h4 class="text-primary mt-1">
                        <fmt:message key="admin.tools"/>
                    </h4>
                    <div class="list-group list-group-flush bg-transparent">
                        <a class="list-group-item list-group-item-action"
                           href="${pageContext.request.contextPath}/controller?command=edit-album-page">
                            <fmt:message key="user.create.artist"/>
                        </a>
                        <a class="list-group-item list-group-item-action"
                           href="${pageContext.request.contextPath}/controller?command=edit-album-page">
                            <fmt:message key="user.create.album"/>
                        </a>
                        <a class="list-group-item list-group-item-action"
                           href="${pageContext.request.contextPath}/controller?command=edit-track-page">
                            <fmt:message key="user.create.track"/>
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <h4 class="text-primary mt-1">
                        <fmt:message key="admin.ban.title"/>
                    </h4>
                    <form method="post" action="${pageContext.request.contextPath}/controller?command=user-set-ban">
                        <input type="hidden" name="userid" value="${user.getId()}"/>
                        <c:choose>
                            <c:when test="${user.getBanned() == true}">
                                <input type="hidden" value="false"/>
                                <button type="submit" class="btn btn-sm btn-danger">
                                    <fmt:message key="user.unban"/>
                                </button>
                            </c:when>
                            <c:otherwise>
                                <input type="hidden" value="true"/>
                                <button type="submit" class="btn btn-sm btn-danger">
                                    <fmt:message key="user.ban"/>
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </form>

                    <h4 class="text-primary mt-1">
                        <fmt:message key="user.data"/>
                    </h4>
                    <div class="list-group list-group-flush bg-transparent">
                        <a class="list-group-item list-group-item-action"
                           href="${pageContext.request.contextPath}/controller?command=user-get-liked-tracks&userid=${user.getId()}">
                            <fmt:message key="user.show.liked.tracks"/>
                        </a>
                        <a class="list-group-item list-group-item-action"
                           href="${pageContext.request.contextPath}/controller?command=user-get-liked-albums&userid=${user.getId()}">
                            <fmt:message key="user.show.liked.albums"/>
                        </a>
                        <a class="list-group-item list-group-item-action"
                           href="${pageContext.request.contextPath}/controller?command=user-get-liked-artists&userid=${user.getId()}">
                            <fmt:message key="user.show.liked.artists"/>
                        </a>
                        <a class="list-group-item list-group-item-action"
                           href="${pageContext.request.contextPath}/controller?command=user-get-playlists&userid=${user.getId()}">
                            <fmt:message key="user.show.playlists"/>
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </ct:access>

        <c:if test="${admin == null or admin == user.getId()}">
            <h4 class="text-primary mt-1">
                <fmt:message key="user.my.data"/>
            </h4>
            <div class="list-group list-group-flush bg-transparent">
                <a class="list-group-item list-group-item-action"
                   href="${pageContext.request.contextPath}/controller?command=user-get-liked-tracks">
                    <fmt:message key="user.show.liked.tracks"/>
                </a>
                <a class="list-group-item list-group-item-action"
                   href="${pageContext.request.contextPath}/controller?command=user-get-liked-albums">
                    <fmt:message key="user.show.liked.albums"/>
                </a>
                <a class="list-group-item list-group-item-action"
                   href="${pageContext.request.contextPath}/controller?command=user-get-liked-artists">
                    <fmt:message key="user.show.liked.artists"/>
                </a>
                <a class="list-group-item list-group-item-action"
                   href="${pageContext.request.contextPath}/controller?command=user-get-playlists">
                    <fmt:message key="user.show.playlists"/>
                </a>
            </div>

            <h4 class="text-primary mt-1">
                <fmt:message key="user.settings"/>
            </h4>
            <div class="list-group list-group-flush bg-transparent">
                <a class="list-group-item list-group-item-action bg-light border-0"
                   href="${pageContext.request.contextPath}/controller?command=change-password-page">
                    <fmt:message key="user.change.password"/>
                </a>
            </div>
        </c:if>
    </div>
</div>

</body>
</html>
