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
        <c:if test="${artist != null}">
            <img class="card-img-top" src="/img/artist/${artist.getAvatar()}" alt="Album avatar">
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/controller?command=artist-save">
            <div class="form-outline mb-4">
                <label for="artistName">
                    <fmt:message key="edit.artist.label.name"/>
                </label>
                <input type="text" id="artistName" name="name" required class="form-control form-control-lg"
                <c:if test="${artist != null}">
                       value="${artist.getName()}"
                </c:if>>
            </div>

            <div class="file-drop-area">
                <label for="avatar">
                    <fmt:message key="edit.artist.choose.avatar"/>
                </label>
                <input class="file-input" id="avatar" type="file" name="avatar">
            </div>

            <button type="submit" class="btn btn-sm w-100 mt-3">
                <fmt:message key="edit.artist.save"/>
            </button>
        </form>
        <c:if test="${artist != null}">
            <form method="post" action="${pageContext.request.contextPath}/controller?command=artist-delete">
                <input type="hidden" name="artistid" value="${artist.getId()}"/>
                <button type="submit" class="btn btn-sm w-100 mt-2">
                    <fmt:message key="edit.artist.delete"/>
                </button>
            </form>
        </c:if>

        <c:if test="${not empty tracks}">
            <h5 class="font-weight-normal mt-2 me-3">
                <fmt:message key="edit.artist.choose.track"/>
            </h5>
            <ul class="list-group list-group-flush bg-light">
                <c:choose>
                    <c:when test="${artist == null}">
                        <c:forEach items="${tracks}" var="track">
                            <a class="list-group-item list-group-item-action d-flex justify-content-sm-between"
                               href="${pageContext.request.contextPath}/controller?command=edit-artist-page&trackid=${track.getId()}&trackpage=${trackpage}">
                                    ${track.getName()}
                            </a>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${tracks}" var="track">
                            <a class="list-group-item list-group-item-action d-flex justify-content-sm-between"
                               href="${pageContext.request.contextPath}/controller?command=edit-artist-page&artistid=${artist.getId()}&trackid=${track.getId()}&trackpage=${trackpage}">
                                    ${track.getName()}
                            </a>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </ul>
        </c:if>
    </div>
</div>

</body>
</html>
</jsp:root>