<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css"/>

<header>
    <div class="container">
        <h1 class="logo">
            <a href="${pageContext.request.contextPath}/controller?command=homepage">
                <fmt:message key="navbar.logo"/>
            </a>
        </h1>
        <div class="menu">
            <a href="${pageContext.request.contextPath}/controller?command=homepage" class="menuitem active">
                <fmt:message key="navbar.home"/>
            </a>
            <a href="${pageContext.request.contextPath}/controller?command=loginpage" class="menuitem">
                <fmt:message key="navbar.login"/>
            </a>
            <a href="${pageContext.request.contextPath}/controller?command=singuppage" class="menuitem">
                <fmt:message key="navbar.singup"/>
            </a>
        </div>
    </div>
</header>
