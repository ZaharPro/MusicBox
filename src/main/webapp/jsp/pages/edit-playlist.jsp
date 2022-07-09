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
    <c:import url="/jsp/fragments/head.jsp"/>
</head>
<body>
<c:import url="/jsp/fragments/navbar.jsp"/>

<div class="container f-col h-100 py-2">
    <div class="card col f-col h-100 py-3 mb-0 bg-dark">
        <div class="row align-items-center py-3">
            <div class="col-lg-2 col-md-2">
                <c:choose>
                    <c:when test="${playlist != null && playlist.getPicture() != null}">
                        <img class="card-img"
                             src="${pageContext.request.contextPath}/file/img/${playlist.getPicture()}">
                    </c:when>
                    <c:otherwise>
                        <img class="card-img" src="${pageContext.request.contextPath}/system/img/default-playlist.png">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-lg-10 col-md-10 f-col h-100">
                <form method="post" class="row h-100"
                      action="${pageContext.request.contextPath}/controller?command=playlist-save"
                      enctype="multipart/form-data">
                    <c:if test="${playlist != null}">
                        <input type="hidden" name="playlistid" value="${playlist.getId()}">
                    </c:if>
                    <div class="form-outline col-3 f-col justify-content-center">
                        <label for="playlistName" class="title h4">
                            <fmt:message key="edit.playlist.enter.name"/>
                        </label>
                        <input type="text" id="playlistName" name="name" class="form-control form-control-lg w-100"
                               required minlength="4" maxlength="64" pattern="[A-Za-z\\d\\[\\]() -]+"
                        <c:if test="${playlist != null}">
                               value="${playlist.getName()}"
                        </c:if>>
                    </div>

                    <div class="col-6">
                        <div class="file-drop-area p-3">
                            <script src="${pageContext.request.contextPath}/js/file-upload.js"></script>
                            <span class="choose-file-button"><fmt:message key="edit.file.btn"/></span>
                            <span class="file-message"><fmt:message key="edit.file.msg"/></span>
                            <input class="file-input" type="file" name="picture"
                                   accept=".png, .jpg, .jpeg, .gif">
                        </div>
                    </div>

                    <div class="btn-group btn-group-sm offset-1 col-2 f-col justify-content-center">
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
            <div class="col f-col h-100 py-3 mb-0">
                <h2 class="title text-center mb-2">
                    <fmt:message key="tracks.title"/>
                </h2>
                <c:choose>
                    <c:when test="${trackpsr.hasElements()}">
                        <div class="f-col h-100">
                            <div class="list-group list-group-flush bg-light h-100 mb-2">
                                <c:forEach items="${trackpsr.getElements()}" var="track" varStatus="status">
                                    <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                                       href="${pageContext.request.contextPath}/controller?command=track-get-by-id&trackid=${track.getId()}&trackpage=${trackpsr.getPage()}">
                                            ${track.getName()}
                                        <c:choose>
                                            <c:when test="${trackpsr.getFlags()[status.index]}">
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
                                                    <c:when test="${trackpsr.getFlags()[status.index]}">
                                                        <fmt:message key="edit.playlist.remove"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <fmt:message key="edit.playlist.add"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </button>
                                        </form>
                                    </a>
                                </c:forEach>
                            </div>
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
                            <h2 class="title text-center mb-2">
                                <fmt:message key="tracks.empty"/>
                            </h2>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
    </div>
</div>

</body>
</html>
