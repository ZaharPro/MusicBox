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
    <c:set var="command" value="artist-get" scope="request"/>
</c:if>

<div class="container d-flex flex-column h-100 pt-3 pb-3">
    <div class="col card d-flex flex-column h-100 pt-3 pb-3 mb-0 bg-dark">
        <h4 class="card-title text-center">
            <fmt:message key="artists.title"/>
        </h4>
        <c:choose>
            <c:when test="${artistpsr.hasElements()}">
                <div class="d-flex flex-column justify-content-between h-100">
                    <div class="list-group list-group-flush bg-light">
                        <c:forEach items="${artistpsr.getElements()}" var="artist">
                            <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                               href="${pageContext.request.contextPath}/controller?command=artist-get-by-id&artistid=${artist.getId()}">
                                    ${artist.getName()}

                                <img class="img-fluid d-block" style="max-width: 2.5rem"
                                     src="/img/artist/${artist.getAvatar()}"
                                     alt="Artist avatar">
                            </a>
                        </c:forEach>
                    </div>
                    <c:set var="page" value="${artistpsr.getPage()}" scope="request"/>
                    <c:set var="maxpage" value="${artistpsr.getMaxPage()}" scope="request"/>
                    <c:set var="pagename" value="artistpage" scope="request"/>
                    <c:set var="command" value="${command}" scope="request"/>
                    <c:import url="/jsp/fragments/page-navigation.jsp"/>
                </div>
            </c:when>
            <c:otherwise>
                <div class="d-flex flex-column justify-content-center h-100">
                    <h4 class="card-title text-center">
                        <fmt:message key="artists.not.found"/>
                    </h4>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>