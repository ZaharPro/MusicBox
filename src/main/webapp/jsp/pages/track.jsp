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
    <link rel="stylesheet" href="https://bootstraptema.ru/plugins/2015/audio-touch/audio-touch.css"/>
</head>
<body>
<c:import url="/jsp/fragments/navbar.jsp"/>

<div class="container f-col h-100 pt-2 pb-2">
    <div class="card col f-col h-100 align-items-center pt-3 pb-3 mb-0 bg-dark">
        <div class="row justify-content-center">
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
        </div>
        <div class="col f-col align-items-center h-100">
            <h2 class="title text-center> mt-3 mb-3">
                ${track.getName()}
            </h2>
            <div class="btn-group btn-group-sm f-col align-items-center">
                <c:choose>
                    <c:when test="${like == false}">
                        <c:set var="cmd" value="user-mark-liked-track" scope="request"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="cmd" value="user-unmark-liked-track" scope="request"/>
                    </c:otherwise>
                </c:choose>
                <form method="post"
                      action="${pageContext.request.contextPath}/controller?command=${cmd}">
                    <input type="hidden" name="trackid" value="${track.getId()}"/>
                    <button type="submit" class="btn w-100">
                        <c:choose>
                            <c:when test="${like == false}">
                                <fmt:message key="track.mark.liked"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="track.unmark.liked"/>
                            </c:otherwise>
                        </c:choose>
                    </button>
                </form>

                <c:if test="${artistpsr.hasElements()}">
                    <div class="dropdown w-100 mt-2">
                        <a class="btn w-100 dropdown-toggle" href="#" id="trackDropdown" role="button"
                           data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <fmt:message key="track.artists"/>
                        </a>
                        <div class="dropdown-menu dropdown-menu-w-100" aria-labelledby="trackDropdown">
                            <c:forEach items="${artistpsr.getElements()}" var="artist">
                                <a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/controller?command=artist-get-by-id&artistid=${artist.getId()}">
                                        ${artist.getName()}
                                </a>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
                <ct:access role="admin">
                    <a class="btn w-100 mt-2"
                       href="${pageContext.request.contextPath}/controller?command=edit-track-page&trackid=${track.getId()}">
                        <fmt:message key="track.edit"/>
                    </a>
                </ct:access>
            </div>
        </div>

        <c:if test="${track.getAudio() != null}">
            <audio class="audioplayer" preload="auto" controls>
                <source src="${pageContext.request.contextPath}/file/audio/${track.getAudio()}" type="audio/mpeg">
            </audio>
            <script src="https://bootstraptema.ru/plugins/2015/audio-touch/audio-touch.js"></script>
            <script>
                $(function () {
                    $('audio').audioPlayer();
                });
            </script>
        </c:if>
    </div>
</div>

</body>
</html>
