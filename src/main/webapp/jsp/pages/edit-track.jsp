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

<div class="container f-col h-100 pt-2 pb-2">
    <div class="card col f-col h-100 pt-3 pb-3 mb-0 bg-dark">
        <div class="row pt-3 pb-3">
            <div class="col-lg-2 col-md-2">
                <c:choose>
                    <c:when test="${album != null && album.getPicture() != null}">
                        <img class="card-img" src="${pageContext.request.contextPath}/file/img/${album.getPicture()}">
                    </c:when>
                    <c:otherwise>
                        <img class="card-img" src="${pageContext.request.contextPath}/system/img/home-album.png">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-lg-10 col-md-10">
                <h4 class="title">
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
        <form method="post" class="row pt-3 pb-3"
              action="${pageContext.request.contextPath}/controller?command=track-save"
              enctype="multipart/form-data">
            <c:if test="${track != null}">
                <input type="hidden" name="trackid" value="${track.getId()}">
            </c:if>
            <div class="form-outline col-3">
                <label for="trackName" class="title h4">
                    <fmt:message key="edit.track.enter.name"/>
                </label>
                <input type="text" id="trackName" name="name" required
                <c:if test="${track != null}">
                       value="${track.getName()}"
                </c:if>>
            </div>
            <div class="col-6 f-col h-100">
                <div class="file-drop-area h-100 p-3">
                    <label for="picture" class="text-center">
                        <fmt:message key="edit.track.upload.track"/>
                    </label>
                    <input class="file-input w-100" id="picture" type="file" name="audio" accept=".wav, .mp3">
                </div>
                <c:if test="${track != null}">
                    <audio controls class="w-100 mt-2 p-0">
                        <source src="${pageContext.request.contextPath}/file/audio/${track.getAudio()}"
                                type="audio/mpeg">
                    </audio>
                </c:if>
            </div>

            <c:choose>
                <c:when test="${album != null}">
                    <input type="hidden" name="albumid" value="${album.getId()}">
                    <input type="hidden" name="albumpage" value="${albumpsr.getPage()}">
                    <div class="btn-group-lg col-3 f-col">
                        <button type="submit" class="btn w-100">
                            <fmt:message key="edit.track.save"/>
                        </button>
                        <c:if test="${track != null}">
                            <a class="btn w-100 mt-2"
                               href="${pageContext.request.contextPath}/controller?command=track-delete&trackid=${track.getId()}">
                                <fmt:message key="edit.track.delete"/>
                            </a>
                        </c:if>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="col-3 f-col">
                        <h4 class="text-danger text-center col-3 d-block">
                            <fmt:message key="edit.track.select.album"/>
                        </h4>
                        <a class="btn w-100 mt-2"
                           href="${pageContext.request.contextPath}/controller?command=edit-album-page">
                            <fmt:message key="admin.add.album"/>
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </form>
        <div class="col f-col h-100 pt-3 pb-3 mb-0">
            <h4 class="title text-center mb-2">
                <fmt:message key="albums.title"/>
            </h4>
            <c:choose>
                <c:when test="${albumpsr.hasElements()}">
                    <div class="f-col h-100">
                        <div class="list-group list-group-flush bg-light h-100 mb-2">
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
                    <div class="col f-col justify-content-center h-100">
                        <h4 class="title text-center mb-2">
                            <fmt:message key="albums.empty"/>
                        </h4>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

</body>
</html>
