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

<li class="nav-item dropdown btn btn-sm" id="localeChooser">
    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
       aria-haspopup="true" aria-expanded="false">
        Change language
    </a>
    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
        <form class="dropdown-item" method="POST"
              action="${pageContext.request.contextPath}/controller?command=change-locale">
            <input type="hidden" name="locale" value="en_EN">
            <button class="btn btn-sm" type="submit">
                <fmt:message key="navbar.locale.en"/>
            </button>
        </form>
        <form class="dropdown-item" method="POST"
              action="${pageContext.request.contextPath}/controller?command=change-locale">
            <input type="hidden" name="locale" value="ru_RU">
            <button class="btn btn-sm" type="submit">
                <fmt:message key="navbar.locale.ru"/>
            </button>
        </form>
    </div>
</li>

<script>
    const navbarToggle = document.getElementById('navbarToggle');
    const localeChooser = document.getElementById('localeChooser');
    navbarToggle.appendChild(localeChooser);
</script>

</body>

</html>
