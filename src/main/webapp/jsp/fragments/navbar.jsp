<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<header class="container-fluid d-flex bg-dark">
    <nav class="navbar navbar-expand-md navbar-dark bg-transparent mb-2 container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/controller?command=home-page">
            <h3 class="mb-0">
                <fmt:message key="navbar.logo"/>
            </h3>
        </a>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggler"
                aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarToggler">
            <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=home-page">
                        <fmt:message key="navbar.home"/>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=user-page">
                        <fmt:message key="navbar.profile"/>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=logout">
                        <fmt:message key="navbar.logout"/>
                    </a>
                </li>
            </ul>
            <form class="form-inline my-2 my-lg-0" method="POST"
                  action="${pageContext.request.contextPath}/controller?command=search">
                <input class="form-control mr-sm-2" type="search" name="name" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">
                    <fmt:message key="navbar.search.button"/>
                </button>
            </form>
        </div>
    </nav>
</header>