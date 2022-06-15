<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="custom-tags" %>

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
        <img class="card-img-top" src="/img/artist/${artist.getAvatar()}" alt="Artist avatar">
        <div class="card-body">
            <h5 class="card-title">${artist.getName()}</h5>
            <c:choose>
                <c:when test="${like == false}">
                    <form method="post" action="${pageContext.request.contextPath}/controller?command=user-like-artist">
                        <input type="hidden" name="albumid" value="${artist.getId()}"/>
                        <input type="hidden" name="trackpage" value="${trackpage}"/>
                        <input type="hidden" name="albumpage" value="${albumpage}"/>
                        <button type="submit" class="btn btn-sm w-100">
                            <fmt:message key="artist.like"/>
                        </button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form method="post"
                          action="${pageContext.request.contextPath}/controller?command=user-cancel-like-artist">
                        <input type="hidden" name="albumid" value="${artist.getId()}"/>
                        <input type="hidden" name="trackpage" value="${trackpage}"/>
                        <input type="hidden" name="albumpage" value="${albumpage}"/>
                        <button type="submit" class="btn btn-sm w-100">
                            <fmt:message key="artist.cancel.like"/>
                        </button>
                    </form>
                </c:otherwise>
            </c:choose>
            <ct:access role="admin">
                <a class="btn btn-sm w-100 mt-1"
                   href="${pageContext.request.contextPath}/controller?command=edit-artist-page&artistid=${artist.getId()}">
                    <fmt:message key="artist.edit"/>
                </a>
            </ct:access>
        </div>
        <c:if test="${not empty tracks}">
            <div class="list-group list-group-flush bg-light">
                <c:forEach items="${tracks}" var="track">
                    <a class="list-group-item list-group-item-action"
                       href="${pageContext.request.contextPath}/controller?command=track-get-by-id&trackid=${track.getId()}">
                            ${track.getName()}
                    </a>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${not empty albums}">
            <div class="list-group list-group-flush bg-light">
                <c:forEach items="${albums}" var="album">
                    <a class="list-group-item list-group-item-action d-flex justify-content-sm-between"
                       href="${pageContext.request.contextPath}/controller?command=album-get-by-id&albumid=${album.getId()}">
                            ${album.getName()}

                        <img class="rounded mx-auto d-block" src="/img/album/${album.getPicture()}"
                             alt="Album picture">
                    </a>
                </c:forEach>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>
