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
    <div class="col card pt-0 pb-3 mb-0 d-flex d-flex flex-column h-100 bg-dark">
        <div class="row pt-3 pb-3" style="border-bottom: 1px solid #dd2476;">
            <div class="col-lg-2 col-md-2">
                <c:choose>
                    <c:when test="${artist != null}">
                        <img class="card-img" src="${pageContext.request.contextPath}/file/img/${artist.getAvatar()}"
                             alt="Artist avatar">
                    </c:when>
                    <c:otherwise>
                        <img class="card-img" src="${pageContext.request.contextPath}/system/img/artist-default.png"
                             alt="Artist avatar">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-lg-10 col-md-10">
                <form method="post" class="row"
                      action="${pageContext.request.contextPath}/controller?command=artist-save"
                      enctype="multipart/form-data">
                    <c:if test="${artist != null}">
                        <input type="hidden" name="artistid" value="${artist.getId()}">
                    </c:if>
                    <div class="form-outline col-3 mb-4">
                        <label for="artistName">
                            <fmt:message key="edit.artist.label.name"/>
                        </label>
                        <input type="text" id="artistName" name="name" required class="form-control form-control-lg"
                        <c:if test="${artist != null}">
                               value="${artist.getName()}"
                        </c:if>>
                    </div>

                    <div class="file-drop-area col-6 d-flex flex-column justify-content-center align-items-center p-3"
                         style="border: 1px solid #dd2476; border-radius: 2px;">
                        <label for="avatar">
                            <fmt:message key="edit.artist.choose.avatar"/>
                        </label>
                        <input class="file-input w-100 h-100" id="picture" type="file" name="avatar"
                               accept=".png, .jpg, .jpeg, .gif">
                    </div>

                    <div class="btn-group-lg col-3 d-flex flex-column w-100">
                        <button type="submit" class="btn btn-lg w-100">
                            <fmt:message key="edit.artist.save"/>
                        </button>
                        <c:if test="${artist != null}">
                            <a class="btn btn-lg w-100"
                               href="${pageContext.request.contextPath}/controller?command=artist-delete&artistid=${artist.getId()}">
                                <fmt:message key="edit.artist.delete"/>
                            </a>
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
        <c:if test="${artist != null}">
            <h4 class="card-title text-center mt-3">
                <fmt:message key="tracks.title"/>
            </h4>
            <c:choose>
                <c:when test="${trackpsr.hasElements()}">
                    <div class="d-flex flex-column justify-content-between h-100" style="min-height: 25rem">
                        <div class="list-group list-group-flush bg-light">
                            <c:forEach items="${trackpsr.getElements()}" var="track" varStatus="status">
                                <c:choose>
                                    <c:when test="${trackpsr.getFlags().get(status.index)}">
                                        <c:set var="command" value="artist-remove-track" scope="request"/>
                                        <c:set var="text" value="remove" scope="request"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="command" value="artist-add-track" scope="request"/>
                                        <c:set var="text" value="add" scope="request"/>
                                    </c:otherwise>
                                </c:choose>
                                <form method="post"
                                      class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                                      action="${pageContext.request.contextPath}/controller?command=${command}">
                                    <span class="text-dark">${track.getName()}</span>
                                    <input type="hidden" name="artistid" value="${artist.getId()}">
                                    <input type="hidden" name="trackid" value="${track.getId()}">
                                    <input type="hidden" name="trackpage" value="${trackpsr.getPage()}">
                                    <button type="submit" class="btn btn-sm">
                                            ${text}
                                    </button>
                                </form>
                            </c:forEach>
                        </div>
                        <c:set var="page" value="${trackpsr.getPage()}" scope="request"/>
                        <c:set var="maxpage" value="${trackpsr.getMaxPage()}" scope="request"/>
                        <c:set var="pagename" value="trackpage" scope="request"/>
                        <c:set var="command" value="edit-artist-page&artistid=${artist.getId()}" scope="request"/>
                        <c:import url="/jsp/fragments/page-navigation.jsp"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="d-flex flex-column justify-content-center h-100" style="min-height: 25rem">
                        <h4 class="card-title text-center">
                            <fmt:message key="tracks.not.found"/>
                        </h4>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:if>
    </div>
</div>

</body>
</html>
