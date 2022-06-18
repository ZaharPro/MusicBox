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

<div class="container d-flex d-flex flex-column h-100 pt-3 pb-3">
    <div class="col card pt-0 pb-3 mb-0 d-flex flex-column h-100 bg-dark">
        <div class="row pt-3 pb-3" style="border-bottom: 1px solid #dd2476;">
            <div class="col-lg-2 col-md-2">
                <img class="card-img" src="${pageContext.request.contextPath}/file/img/${album.getPicture()}" alt="Album picture">
            </div>
            <div class="col-lg-10 col-md-10 d-flex justify-content-between align-items-center">
                <h2 class="card-title">
                    ${album.getName()}
                </h2>
                <div class="btn-group">
                    <c:choose>
                        <c:when test="${like == false}">
                            <form method="post" class="m-0"
                                  action="${pageContext.request.contextPath}/controller?command=user-mark-liked-album">
                                <input type="hidden" name="albumid" value="${album.getId()}"/>
                                <input type="hidden" name="trackpage" value="${trackpsr.getPage()}"/>
                                <button type="submit" class="btn btn-sm">
                                    <fmt:message key="album.mark.liked"/>
                                </button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form method="post" class="m-0"
                                  action="${pageContext.request.contextPath}/controller?command=user-unmark-liked-album">
                                <input type="hidden" name="albumid" value="${album.getId()}"/>
                                <input type="hidden" name="trackpage" value="${trackpsr.getPage()}"/>
                                <button type="submit" class="btn btn-sm">
                                    <fmt:message key="album.unmark.liked"/>
                                </button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                    <ct:access role="admin">
                        <div>
                            <a class="btn btn-sm ml-1"
                               href="${pageContext.request.contextPath}/controller?command=edit-album-page&albumid=${album.getId()}">
                                <fmt:message key="album.edit"/>
                            </a>
                        </div>
                    </ct:access>
                </div>
            </div>
        </div>
        <h4 class="card-title text-center mt-3">
            <fmt:message key="tracks.title"/>
        </h4>
        <c:choose>
            <c:when test="${trackpsr.hasElements()}">
                <div class="d-flex flex-column justify-content-between h-100">
                    <div class="list-group list-group-flush bg-light">
                        <c:forEach items="${trackpsr.getElements()}" var="track">
                            <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                               href="${pageContext.request.contextPath}/controller?command=track-get-by-id&trackid=${track.getId()}&trackpage=${trackpsr.getPage()}">
                                    ${track.getName()}
                            </a>
                        </c:forEach>
                    </div>
                    <c:set var="page" value="${trackpsr.getPage()}" scope="request"/>
                    <c:set var="maxpage" value="${trackpsr.getMaxPage()}" scope="request"/>
                    <c:set var="pagename" value="trackpage" scope="request"/>
                    <c:set var="command" value="track-get" scope="request"/>
                    <c:import url="/jsp/fragments/page-navigation.jsp"/>
                </div>
            </c:when>
            <c:otherwise>
                <div class="d-flex flex-column justify-content-center h-100">
                    <h4 class="card-title text-center">
                        <fmt:message key="tracks.not.found"/>
                    </h4>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>
