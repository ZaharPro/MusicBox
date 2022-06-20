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
            <div class="col-lg-10 col-md-10 d-flex justify-content-between">
                <h2 class="title">
                    ${album.getName()}
                </h2>
            </div>
        </div>
        <div class="col f-col align-items-center h-100">
            <div class="col f-col align-items-center h-100">
                <h2 class="title text-center> mb-3">
                    ${track.getName()}
                </h2>
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
                    <div class="btn-group btn-group-sm">
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
                        <ct:access role="admin">
                            <a class="btn w-100 ml-1"
                               href="${pageContext.request.contextPath}/controller?command=edit-track-page&trackid=${track.getId()}">
                                <fmt:message key="track.edit"/>
                            </a>
                        </ct:access>
                    </div>
                </form>
            </div>
            <audio controls>
                <source src="${pageContext.request.contextPath}/file/audio/${track.getAudio()}" type="audio/mpeg">
            </audio>
        </div>
    </div>
</div>

</body>
</html>
