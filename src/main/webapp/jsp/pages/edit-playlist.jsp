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
                    <c:when test="${playlist != null && playlist.getPicture()}">
                        <img class="card-img" src="${pageContext.request.contextPath}/file/img/${playlist.getPicture()}"
                             alt="Playlist picture">
                    </c:when>
                    <c:otherwise>
                        <img class="card-img" src="${pageContext.request.contextPath}/system/img/playlist-default.png"
                             alt="Playlist picture">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-lg-10 col-md-10 f-col h-100">
                <form method="post" class="row"
                      action="${pageContext.request.contextPath}/controller?command=playlist-save"
                      enctype="multipart/form-data">
                    <c:if test="${playlist != null}">
                        <input type="hidden" name="playlistid" value="${playlist.getId()}">
                    </c:if>
                    <div class="form-outline col-3">
                        <label for="playlistName" class="title h4">
                            <fmt:message key="edit.playlist.enter.name"/>
                        </label>
                        <input type="text" id="playlistName" name="name" required
                               class="form-control form-control-lg w-100"
                        <c:if test="${playlist != null}">
                               value="${playlist.getName()}"
                        </c:if>>
                    </div>

                    <div class="col-6">
                        <div class="file-drop-area h-100 p-3">
                            <label for="picture" class="text-center">
                                <fmt:message key="edit.playlist.upload.picture"/>
                            </label>
                            <input class="file-input w-100" id="picture" type="file" name="picture"
                                   accept=".png, .jpg, .jpeg, .gif">
                        </div>
                    </div>

                    <div class="btn-group-lg col-3 f-col">
                        <button type="submit" class="btn w-100">
                            <fmt:message key="edit.playlist.save"/>
                        </button>
                        <c:if test="${playlist != null}">
                            <a class="btn w-100 mt-2"
                               href="${pageContext.request.contextPath}/controller?command=playlist-delete&playlistid=${playlist.getId()}">
                                <fmt:message key="edit.playlist.delete"/>
                            </a>
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
        <c:if test="${playlist != null}">
            <div class="col f-col h-100 pt-3 pb-3 mb-0">
                <h4 class="title text-center mb-2">
                    <fmt:message key="tracks.title"/>
                </h4>
                <c:choose>
                    <c:when test="${trackpsr.hasElements()}">
                        <div class="f-col h-100">
                            <ul class="list-group list-group-flush bg-light h-100 mb-2">
                                <c:forEach items="${trackpsr.getElements()}" var="track" varStatus="status">
                                    <li class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                                            ${track.getName()}
                                        <c:choose>
                                            <c:when test="${trackpsr.getFlags().get(status.index)}">
                                                <c:set var="cmd" value="playlist-remove-track" scope="request"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="cmd" value="playlist-add-track" scope="request"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <form method="post"
                                              action="${pageContext.request.contextPath}/controller?command=${cmd}">
                                            <input type="hidden" name="playlistid" value="${playlist.getId()}">
                                            <input type="hidden" name="trackid" value="${track.getId()}">
                                            <input type="hidden" name="trackpage" value="${trackpsr.getPage()}">
                                            <button type="submit" class="btn btn-sm">
                                                <c:choose>
                                                    <c:when test="${trackpsr.getFlags().get(status.index)}">
                                                        <fmt:message key="edit.playlist.remove"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <fmt:message key="edit.playlist.add"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </button>
                                        </form>
                                    </li>
                                </c:forEach>
                            </ul>
                            <c:set var="page" value="${trackpsr.getPage()}" scope="request"/>
                            <c:set var="maxpage" value="${trackpsr.getMaxPage()}" scope="request"/>
                            <c:set var="pagename" value="trackpage" scope="request"/>
                            <c:set var="command" value="edit-playlist-page&playlistid=${playlist.getId()}"
                                   scope="request"/>
                            <c:import url="/jsp/fragments/page-navigation.jsp"/>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="col f-col justify-content-center h-100">
                            <h4 class="title text-center mb-2">
                                <fmt:message key="tracks.empty"/>
                            </h4>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
    </div>
</div>

</body>
</html>
