<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="custom-tags" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="locale"/>

<nav aria-label="Page navigation" class="d-flex justify-content-center w-100">
    <ul class="pagination bg-light mb-0">
        <li class="page-item">
            <a class="page-link" aria-label="Start"
               href="${pageContext.request.contextPath}/controller?command=${command}&${pagename}=1">
                <fmt:message key="page.nav.start"/>
            </a>
        </li>
        <c:if test="${page > 1}">
            <li class="page-item">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/controller?command=${command}&${pagename}=${page - 1}">
                        ${page - 1}
                </a>
            </li>
        </c:if>
        <li class="page-item active">
            <div class="page-link">
                ${page}<span class="sr-only">(current)</span>
            </div>
        </li>
        <c:if test="${page < maxpage}">
            <li class="page-item">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/controller?command=${command}&${pagename}=${page + 1}">
                        ${page + 1}
                </a>
            </li>
        </c:if>
        <li class="page-item">
            <a class="page-link" aria-label="End"
               href="${pageContext.request.contextPath}/controller?command=${command}&${pagename}=${maxpage}">
                <fmt:message key="page.nav.end"/>
            </a>
        </li>
    </ul>
</nav>