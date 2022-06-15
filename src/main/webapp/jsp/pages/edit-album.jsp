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
    <div class="card col-xl-4 col-md-8 col-lg-6 pt-3 pb-3 bg-dark">
        <c:if test="${album != null}">
            <img class="card-img-top" src="/img/album/${album.getPicture()}" alt="Album picture">
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/controller?command=album-save">
            <div class="form-outline mb-4">
                <label for="albumName">
                    <fmt:message key="edit.album.label.name"/>
                </label>
                <input type="text" id="albumName" name="name" required class="form-control form-control-lg"
                <c:if test="${album != null}">
                       value="${album.getName()}"
                </c:if>>
            </div>

            <div class="file-drop-area">
                <label for="picture">
                    <fmt:message key="edit.album.choose.picture"/>
                </label>
                <input class="file-input" id="picture" type="file" name="picture">
            </div>

            <button type="submit" class="btn btn-sm w-100 mt-3">
                <fmt:message key="edit.album.save"/>
            </button>
        </form>
        <c:if test="${album != null}">
            <form method="post" action="${pageContext.request.contextPath}/controller?command=album-delete">
                <input type="hidden" name="albumid" value="${album.getId()}"/>
                <button type="submit" class="btn btn-sm w-100 mt-2">
                    <fmt:message key="edit.album.delete"/>
                </button>
            </form>
        </c:if>
    </div>
</div>

</body>
</html>
