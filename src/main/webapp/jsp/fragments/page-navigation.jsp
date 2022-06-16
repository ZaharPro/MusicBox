<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="custom-tags" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="locale"/>

<%
    int deltaIndex = 2;
    int currentPage = (int) request.getAttribute("page");
    int maxPage = (int) request.getAttribute("maxpage");
    int from = Math.max(currentPage - deltaIndex, 1);
    int to = Math.min(currentPage + deltaIndex, maxPage);
    int deltaf = currentPage - from;
    int deltat = to - currentPage;
    if (deltaf != deltat) {
        deltaf = deltat = Math.min(deltaf, deltat);
        from = currentPage - deltaf;
        to = currentPage + deltat;
    }
    request.setAttribute("from", from);
    request.setAttribute("to", to);
%>

<nav aria-label="Page navigation" class="d-flex justify-content-center w-100">
    <ul class="pagination bg-light mb-0">
        <li class="page-item">
            <a class="page-link" aria-label="Start"
               href="${pageContext.request.contextPath}/controller?command=${command}&${pagename}=1">
                <fmt:message key="page.nav.start"/>
            </a>
        </li>

        <c:choose>
            <c:when test="${from == to}">
                <li class="page-item active">
                    <div class="page-link">
                            ${page}<span class="sr-only">(current)</span>
                    </div>
                </li>
            </c:when>
            <c:otherwise>
                <c:forEach begin="${from}" end="${to}" var="i">
                    <c:choose>
                        <c:when test="${page == i}">
                            <li class="page-item active">
                                <div class="page-link">
                                        ${i}<span class="sr-only">(current)</span>
                                </div>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/controller?command=${command}&${pagename}=${i}">
                                        ${i}
                                </a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <li class="page-item">
            <a class="page-link" aria-label="End"
               href="${pageContext.request.contextPath}/controller?command=${command}&${pagename}=${maxpage}">
                <fmt:message key="page.nav.end"/>
            </a>
        </li>
    </ul>
</nav>