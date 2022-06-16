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

<div class="container flex-col h-100 pt-3 pb-3">
    <div class="col card pt-0 pb-3 mb-0 flex-col h-100 bg-dark">
        <div class="row pt-3 pb-3" style="border-bottom: 1px solid #dd2476;">
            <div class="col-lg-2 col-md-2">
                <img class="card-img" src="/img/album/${album.getPicture()}" alt="Album picture">
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
        <div class="pt-3 flex-col align-items-center h-100">
            <div class="d-flex flex-col align-items-center h-100">
                <h2 class="card-title">
                    ${track.getName()}
                </h2>
                <div class="btn-group">
                    <c:choose>
                        <c:when test="${like == false}">
                            <form method="post" class="m-0"
                                  action="${pageContext.request.contextPath}/controller?command=user-mark-liked-track">
                                <input type="hidden" name="trackid" value="${track.getId()}"/>
                                <input type="hidden" name="trackpage" value="${trackpsr.getPage()}"/>
                                <button type="submit" class="btn btn-sm">
                                    <fmt:message key="track.mark.liked"/>
                                </button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form method="post" class="m-0"
                                  action="${pageContext.request.contextPath}/controller?command=user-unmark-liked-track">
                                <input type="hidden" name="trackid" value="${track.getId()}"/>
                                <input type="hidden" name="trackpage" value="${trackpsr.getPage()}"/>
                                <button type="submit" class="btn btn-sm">
                                    <fmt:message key="track.unmark.liked"/>
                                </button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                    <ct:access role="admin">
                        <div>
                            <a class="btn btn-sm ml-1"
                               href="${pageContext.request.contextPath}/controller?command=edit-track-page&trackid=${track.getId()}">
                                <fmt:message key="track.edit"/>
                            </a>
                        </div>
                    </ct:access>
                </div>
            </div>
            <audio controls>
                <source src="${track.getPath()}" type="audio/mpeg">
            </audio>
        </div>
    </div>
</div>

</body>
</html>
