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
    <c:set var="command" value="album-get" scope="request"/>
</c:if>

<div class="container f-col h-100 py-2">
    <div class="card col f-col h-100 py-3 mb-0 bg-dark">
        <c:choose>
            <c:when test="${albumpsr.hasElements()}">
                <h2 class="title text-center mb-2">
                    <fmt:message key="albums.title"/>
                    <span class="h6 text-info">(${albumpsr.getCount()})</span>
                </h2>
                <div class="f-col h-100">
                    <div class="list-group list-group-flush bg-light h-100 mb-2">
                        <c:forEach items="${albumpsr.getElements()}" var="album">
                            <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                               href="${pageContext.request.contextPath}/controller?command=album-get-by-id&albumid=${album.getId()}">
                                    ${album.getName()}
                                <c:choose>
                                    <c:when test="${album != null && album.getPicture() != null}">
                                        <img class="img-fluid col-1"
                                             src="${pageContext.request.contextPath}/file/img/${album.getPicture()}">
                                    </c:when>
                                    <c:otherwise>
                                        <img class="img-fluid col-1"
                                             src="${pageContext.request.contextPath}/system/img/default-album.png">
                                    </c:otherwise>
                                </c:choose>
                            </a>
                        </c:forEach>
                    </div>
                    <c:set var="page" value="${albumpsr.getPage()}" scope="request"/>
                    <c:set var="maxpage" value="${albumpsr.getMaxPage()}" scope="request"/>
                    <c:set var="pagename" value="albumpage" scope="request"/>
                    <c:set var="command" value="${command}" scope="request"/>
                    <c:import url="/jsp/fragments/page-navigation.jsp"/>
                </div>
            </c:when>
            <c:otherwise>
                <h2 class="title text-center mb-2">
                    <fmt:message key="albums.title"/>
                </h2>
                <div class="col f-col justify-content-center h-100">
                    <h4 class="text-info text-center mb-2">
                        <fmt:message key="albums.empty"/>
                    </h4>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>