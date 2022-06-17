<%@ page import="com.epam.musicbox.controller.Parameter" %>
<%@ page import="com.epam.musicbox.entity.Album" %>
<%@ page import="com.epam.musicbox.entity.Track" %>
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

<div class="container d-flex flex-column h-100 pt-3 pb-3">
    <div class="col card pt-0 pb-3 mb-0 d-flex flex-column h-100 bg-dark">
        <div class="row pt-3 pb-3" style="border-bottom: 1px solid #dd2476;">
            <div class="col-lg-2 col-md-2">
                <c:choose>
                    <c:when test="${album != null}">
                        <img class="card-img" src="/img/album/${album.getPicture()}" alt="Album picture">
                    </c:when>
                    <c:otherwise>
                        <img class="card-img" src="/img/album/default.png" alt="Album picture">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-lg-10 col-md-10 d-flex justify-content-between align-items-center">
                <h4 class="card-title">
                    <c:choose>
                        <c:when test="${album != null}">
                            ${album.getName()}
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="edit.track.album.not.selected"/>
                        </c:otherwise>
                    </c:choose>
                </h4>
            </div>
        </div>
        <form method="post" class="row p-3 h-100" style="border-bottom: 1px solid #dd2476; min-height: 10rem"
              action="${pageContext.request.contextPath}/controller?command=track-save">
            <div class="form-outline col-3">
                <label for="trackName" class="card-title h4">
                    <fmt:message key="edit.track.name"/>
                </label>
                <input type="text" id="trackName" name="name" required
                <c:if test="${track != null}">
                       value="${track.getName()}"
                </c:if>>
            </div>
            <div class="col-6 d-flex flex-column">
                <div class="file-drop-area p-3" style="border: 1px solid #dd2476; border-radius: 2px;">
                    <label for="picture">
                        <fmt:message key="edit.album.choose.picture"/>
                    </label>
                    <input class="file-input" id="picture" type="file" name="picture">
                </div>
                <c:if test="${track != null}">
                    <audio controls class="w-100 mt-2 p-0">
                        <source src="/audio/${track.getPath()}" type="audio/mpeg">
                    </audio>
                </c:if>
            </div>

            <c:choose>
                <c:when test="${album != null}">
                    <div class="btn-group-lg col-3 d-flex flex-columnumn w-100">
                        <button type="submit" class="btn btn-lg w-100">
                            <fmt:message key="edit.track.save"/>
                        </button>
                        <c:if test="${track != null}">
                            <a class="btn btn-lg w-100 mt-2"
                               href="${pageContext.request.contextPath}/controller?command=track-delete&trackid=${track.getId()}">
                                <fmt:message key="edit.track.delete"/>
                            </a>
                        </c:if>
                    </div>
                </c:when>
                <c:otherwise>
                        <span class="text-danger text-center col-3 d-block">
                            <fmt:message key="edit.track.choose.album"/>
                        </span>
                </c:otherwise>
            </c:choose>
        </form>
        <h4 class="card-title text-center mt-3">
            <fmt:message key="albums.title"/>
        </h4>
        <c:choose>
            <c:when test="${albumpsr.hasElements()}">
                <div class="d-flex flex-column justify-content-between h-100" style="min-height: 25rem">
                    <div class="list-group list-group-flush bg-light">
                        <c:forEach items="${albumpsr.getElements()}" var="album" varStatus="status">
                            <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                               href="${pageContext.request.contextPath}/controller?command=${albumchoosecommand}&albumid=${album.getId()}">
                                    ${album.getName()}
                            </a>
                        </c:forEach>
                    </div>
                    <c:set var="page" value="${albumpsr.getPage()}" scope="request"/>
                    <c:set var="maxpage" value="${albumpsr.getMaxPage()}" scope="request"/>
                    <c:set var="pagename" value="albumpage" scope="request"/>
                    <c:set var="command" value="${navcommand}" scope="request"/>

                    <c:import url="/jsp/fragments/page-navigation.jsp"/>
                </div>
            </c:when>
            <c:otherwise>
                <div class="d-flex flex-column justify-content-center h-100" style="min-height: 25rem">
                    <h4 class="card-title text-center">
                        <fmt:message key="albums.not.found"/>
                    </h4>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>
