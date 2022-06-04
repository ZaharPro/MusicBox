<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<%--<jsp:useBean id="tracks" scope="request" type="java.util.List"/>
<jsp:useBean id="nextcommand" scope="request" type="java.lang.String"/>--%>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <title><fmt:message key="title"/></title>
    <c:import url="/jsp/head.jsp"/>
</head>
<body>
<c:import url="/jsp/fragments/navbar.jsp"/>

<c:if test="${tracks != null}">
    <ul>
        <c:forEach items="${tracks}" var="track">
            <li>
                <c:choose>
                    <c:when test="${nextcommand == null}">
                        <a href="${pageContext.request.contextPath}/controller?command=track-get-by-id"
                           onsubmit="<c:set var="trackid" value="${track.id}" scope="request"/>">
                            ${track.name}
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/controller?command=${nextcommand}"
                           onsubmit="<c:set var="trackid" value="${track.id}" scope="request"/>">
                            ${track.name}
                        </a>
                    </c:otherwise>
                </c:choose>
            </li>
        </c:forEach>
    </ul>
</c:if>

</body>
</html>