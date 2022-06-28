<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="custom-tags" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="locale"/>

<header class="container-fluid d-flex bg-dark">
    <nav class="navbar navbar-expand-lg navbar-dark bg-transparent mb-2 container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/controller?command=home-page">
            <h3 class="mb-0">
                <fmt:message key="navbar.logo"/>
            </h3>
        </a>

        <button class="navbar-toggle d-lg-none btn" type="button" data-toggle="collapse" data-target="#navbarToggle"
                aria-controls="navbarToggle" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggle-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarToggle">
            <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=home-page">
                        <fmt:message key="navbar.home"/>
                    </a>
                </li>
                <ct:access role="guest">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=login-page">
                            <fmt:message key="navbar.login"/>
                        </a>
                    </li>
                </ct:access>
                <ct:access role="user">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=profile-page">
                            <fmt:message key="navbar.profile"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=logout">
                            <fmt:message key="navbar.logout"/>
                        </a>
                    </li>
                </ct:access>
                <ct:access role="admin">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=profile-page">
                            <fmt:message key="navbar.profile"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=admin-page">
                            <fmt:message key="navbar.admin"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=logout">
                            <fmt:message key="navbar.logout"/>
                        </a>
                    </li>
                </ct:access>
            </ul>
            <ct:access role="not-guest">
                <form class="form-inline my-2 my-lg-0 mr-1" method="post"
                      action="${pageContext.request.contextPath}/controller?command=search">
                    <input class="form-control mr-sm-2" type="search" name="name" aria-label="Search" value="${name}">
                    <button class="btn" type="submit">
                        <fmt:message key="navbar.search"/>
                    </button>
                </form>
            </ct:access>

            <div class="dropdown" id="localeChooser">
                <a class="btn" href="#" id="navbarDropdown" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <ct:locale-label/>
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <form method="post"
                          action="${pageContext.request.contextPath}/controller?command=change-locale">
                        <input type="hidden" name="locale" value="en_EN">
                        <button class="dropdown-item text-center px-2" type="submit">
                            <fmt:message key="navbar.locale.en"/>
                        </button>
                    </form>
                    <form method="post"
                          action="${pageContext.request.contextPath}/controller?command=change-locale">
                        <input type="hidden" name="locale" value="ru_RU">
                        <button class="dropdown-item text-center px-2" type="submit">
                            <fmt:message key="navbar.locale.ru"/>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </nav>
</header>