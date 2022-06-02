<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css"/>

<header class="header">
    <div class="container">
        <h1 class="header-logo">
            <a href="${pageContext.request.contextPath}/controller?command=homepage">
                <fmt:message key="navbar.logo"/>
            </a>
        </h1>
        <form method="POST" id="headerSearchForm"
              action="${pageContext.request.contextPath}/controller?command=search" class="header-search-form">
            <input type="text" name="name" class="header-search-input">
            <button type="submit" class="header-search-btn">
                <fmt:message key="navbar.search.button"/>
            </button>
        </form>
        <div class="header-menu">
            <a href="${pageContext.request.contextPath}/controller?command=logout" class="header-menu-item">
                <fmt:message key="navbar.logout"/>
            </a>
        </div>
    </div>
</header>
