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

<div class="container d-flex justify-content-center mt-3">
    <div class="card bg-dark col pt-3 pb-3">
        <c:choose>
            <c:when test="${not empty artists}">
                <div class="list-group list-group-flush bg-light">
                    <c:forEach items="${artists}" var="artist">
                        <a class="list-group-item list-group-item-action d-flex justify-content-sm-between"
                           href="${pageContext.request.contextPath}/controller?command=artist-get-by-id&artistid=${artist.getId()}&artistpage=${artistpage}">
                                ${artist.getName()}

                            <img class="img-fluid d-block" style="max-width: 2.5rem"
                                 src="/img/artist/${artist.getAvatar()}"
                                 alt="Artist avatar">
                        </a>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <h2 class="lead font-weight-normal mt-4 mb-4 me-3">
                    <fmt:message key="artists.not.found"/>
                </h2>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>