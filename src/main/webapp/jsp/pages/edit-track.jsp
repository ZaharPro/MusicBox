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

    <link rel="stylesheet" href="https://bootstraptema.ru/plugins/2015/audio-touch/audio-touch.css"/>
    <c:import url="/jsp/fragments/head.jsp"/>
</head>
<body>
<c:import url="/jsp/fragments/navbar.jsp"/>

<div class="container f-col h-100 pt-2 pb-2">
    <div class="card col f-col h-100 pt-3 pb-3 mb-0 bg-dark">
        <div class="row justify-content-center">
            <c:choose>
                <c:when test="${album != null}">
                    <c:choose>
                        <c:when test="${album.getPicture() != null}">
                            <a class="f-col justify-content-center img-link img-link-sm m-1"
                               style='background-image: url("/file/img/${album.getPicture()}");'
                               href="${pageContext.request.contextPath}/controller?command=album-get-by-id&albumid=${album.getId()}">
                                    ${album.getName()}
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a class="f-col justify-content-center img-link img-link-sm m-1"
                               style="background-image: url('/system/img/default-album.png')"
                               href="${pageContext.request.contextPath}/controller?command=album-get-by-id&albumid=${album.getId()}">
                                    ${album.getName()}
                            </a>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <a class="f-col justify-content-center img-link img-link-sm m-1"
                       style="background-image: url('/system/img/default-album.png')" href="#">
                        <fmt:message key="edit.track.album.not.selected"/>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
        <form method="post" class="row pt-3 pb-3"
              action="${pageContext.request.contextPath}/controller?command=track-save"
              enctype="multipart/form-data">
            <c:if test="${track != null}">
                <input type="hidden" name="trackid" value="${track.getId()}">
            </c:if>
            <div class="form-outline col-3 f-col justify-content-center">
                <label for="trackName" class="title h4">
                    <fmt:message key="edit.track.enter.name"/>
                </label>
                <input type="text" id="trackName" name="name" class="form-control form-control-lg w-100"
                       required minlength="4" maxlength="32" pattern="[\w\d:.'-]+"
                <c:if test="${track != null}">
                       value="${track.getName()}"
                </c:if>>
            </div>
            <div class="col-7 f-col h-100">
                <div class="file-drop-area f-col justify-content-center align-items-center h-100 p-3">
                    <label for="picture" class="h5">
                        <fmt:message key="edit.track.upload.track"/>
                    </label>
                    <input class="file-input" id="picture" type="file" name="audio" accept=".wav, .mp3">
                </div>
            </div>

            <c:choose>
                <c:when test="${album != null}">
                    <input type="hidden" name="albumid" value="${album.getId()}">
                    <input type="hidden" name="albumpage" value="${albumpsr.getPage()}">
                    <div class="btn-group btn-group-sm col-2 f-col justify-content-center">
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
                        <h4 class="text-danger text-center">
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
        <c:if test="${track != null && track.getAudio() != null}">
            <div class="row justify-content-center my-3">
                <audio class="audioplayer" preload="auto" controls>
                    <source src="${pageContext.request.contextPath}/file/audio/${track.getAudio()}" type="audio/mpeg">
                </audio>
            </div>
            <script src="https://bootstraptema.ru/plugins/2015/audio-touch/audio-touch.js"></script>
            <script>
                $(function () {
                    $('audio').audioPlayer();
                });
            </script>
        </c:if>
        <div class="col f-col h-100 pt-3 pb-3 mb-0">
            <h2 class="title text-center mb-2">
                <fmt:message key="albums.title"/>
            </h2>
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
                        <h2 class="title text-center mb-2">
                            <fmt:message key="albums.empty"/>
                        </h2>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

</body>
</html>
