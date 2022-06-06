<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <title><fmt:message key="title"/></title>
    <c:import url="/jsp/head.jsp"/>
</head>
<body>
<c:import url="/jsp/fragments/navbar.jsp"/>

<section class="container">
    <c:if test="${not empty artistlist}">
        <ul class="list-group col-xl-4 col-md-8 col-lg-6 pt-3 pb-3 bg-light">
            <c:forEach items="${artistlist}" var="artist" varStatus="counter">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <a href="${pageContext.request.contextPath}/controller?command=artist-get-by-id"
                       onsubmit="<c:set var="artistid" value="${artist.getId()}" scope="request"/>">
                            ${artist.getName()}
                    </a>
                    <div class="image-parent">
                        <img src="${artist.getAvatar()}" class="img-fluid" alt="quixote">
                    </div>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</section>
</body>
</html>
