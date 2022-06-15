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
            <c:when test="${not empty albums}">
                <div class="list-group list-group-flush bg-light">
                    <c:forEach items="${albums}" var="album">
                        <a class="list-group-item list-group-item-action d-flex justify-content-sm-between"
                           href="${pageContext.request.contextPath}/controller?command=album-get-by-id&albumid=${album.getId()}&albumpage=${albumpage}">
                                ${album.getName()}

                            <img class="img-fluid d-block" style="max-width: 2.5rem"
                                 src="/img/album/${album.getPicture()}"
                                 alt="Album picture">
                        </a>
                    </c:forEach>
                </div>
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <c:if test="${albumpage != 1}">
                            <li class="page-item">
                                <a class="page-link" href="#" aria-label="Previous"
                                   href="${pageContext.request.contextPath}/controller?command=album-get&albumpage=${albumpage - 1}">
                                    <span aria-hidden="true">&laquo;</span>
                                    <span class="sr-only">Previous</span>
                                </a>
                            </li>
                        </c:if>
                        <c:forEach begin="1" end="${maxpage}" var="i">
                            <c:choose>
                                <c:when test="${albumpage eq i}">
                                    <li class="page-item active">
                                            ${i}
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="${pageContext.request.contextPath}/controller?command=album-get&albumpage=${i}">
                                                ${i}
                                        </a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${albumpage lt maxpage}">
                            <li class="page-item">
                                <a class="page-link" href="#" aria-label="Next"
                                   href="${pageContext.request.contextPath}/controller?command=album-get&albumpage=${albumpage + 1}">
                                    <span aria-hidden="true">&raquo;</span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </c:when>
            <c:otherwise>
                <h2 class="lead font-weight-normal mt-4 mb-4 me-3">
                    <fmt:message key="albums.not.found"/>
                </h2>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>