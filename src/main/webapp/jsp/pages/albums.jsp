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

<div class="container flex-col h-100 pt-3 pb-3">
    <div class="col card flex-col h-100 pt-3 pb-3 mb-0 bg-dark">
        <h4 class="card-title text-center">
            <fmt:message key="albums.title"/>
        </h4>
        <c:choose>
            <c:when test="${albumpsr.hasElements()}">
                <div class="flex-col justify-content-between h-100">
                    <div class="list-group list-group-flush bg-light">
                        <c:forEach items="${albumpsr.getElements()}" var="album">
                            <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                               href="${pageContext.request.contextPath}/controller?command=album-get-by-id&albumid=${album.getId()}&albumpage=${albumpsr.getPage()}">
                                    ${album.getName()}

                                <img class="img-fluid d-block" style="max-width: 2.5rem"
                                     src="/img/album/${album.getPicture()}"
                                     alt="Album picture">
                            </a>
                        </c:forEach>
                    </div>
                    <c:set var="page" value="${albumpsr.getPage()}" scope="request"/>
                    <c:set var="maxpage" value="${albumpsr.getMaxPage()}" scope="request"/>
                    <c:set var="pagename" value="albumpage" scope="request"/>
                    <c:set var="command" value="album-get" scope="request"/>
                    <c:import url="/jsp/fragments/page-navigation.jsp"/>
                </div>
            </c:when>
            <c:otherwise>
                <div class="flex-col justify-content-center h-100">
                    <h4 class="card-title text-center">
                        <fmt:message key="albums.not.found"/>
                    </h4>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>