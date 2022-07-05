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

<div class="container f-col h-100 pt-2 pb-2">
    <div class="card col f-col h-100 pt-3 pb-3 mb-0 bg-dark">
        <div class="row align-items-center pt-3 pb-3">
            <div class="col-lg-2 col-md-2">
                <c:choose>
                    <c:when test="${artist != null && artist.getAvatar() != null}">
                        <img class="card-img" src="${pageContext.request.contextPath}/file/img/${artist.getAvatar()}">
                    </c:when>
                    <c:otherwise>
                        <img class="card-img" src="${pageContext.request.contextPath}/system/img/default-artist.png">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-lg-10 col-md-10 f-col h-100">
                <form method="post" class="row h-100"
                      action="${pageContext.request.contextPath}/controller?command=artist-save"
                      enctype="multipart/form-data">
                    <c:if test="${artist != null}">
                        <input type="hidden" name="artistid" value="${artist.getId()}">
                    </c:if>
                    <div class="form-outline col-3 mb-4">
                        <label for="artistName" class="title h4">
                            <fmt:message key="edit.artist.enter.name"/>
                        </label>
                        <input type="text" id="artistName" name="name" required minlength="4" maxlength="32"
                               pattern="[\w\d:.'-]+" class="form-control form-control-lg w-100"
                        <c:if test="${artist != null}">
                               value="${artist.getName()}"
                        </c:if>>
                    </div>

                    <div class="col-7">
                        <div class="file-drop-area h-100 p-3">
                            <label for="avatar" class="text-center">
                                <fmt:message key="edit.artist.upload.avatar"/>
                            </label>
                            <input class="file-input w-100" id="avatar" type="file" name="avatar"
                                   accept=".png, .jpg, .jpeg, .gif">
                        </div>
                    </div>

                    <div class="btn-group btn-group-sm col-2 f-col justify-content-center">
                        <button type="submit" class="btn w-100">
                            <fmt:message key="edit.artist.save"/>
                        </button>
                        <c:if test="${artist != null}">
                            <a class="btn w-100 mt-2"
                               href="${pageContext.request.contextPath}/controller?command=artist-delete&artistid=${artist.getId()}">
                                <fmt:message key="edit.artist.delete"/>
                            </a>
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
        <c:if test="${artist != null}">
            <div class="col f-col h-100 pt-3 pb-3 mb-0">
                <h2 class="title text-center mb-2">
                    <fmt:message key="tracks.title"/>
                </h2>
                <c:choose>
                    <c:when test="${trackpsr.hasElements()}">
                        <div class="f-col h-100">
                            <ul class="list-group list-group-flush bg-light h-100 mb-2">
                                <c:forEach items="${trackpsr.getElements()}" var="track" varStatus="status">
                                    <li class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                                            ${track.getName()}
                                        <c:choose>
                                            <c:when test="${trackpsr.getFlags()[status.index]}">
                                                <c:set var="cmd" value="artist-remove-track" scope="request"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="cmd" value="artist-add-track" scope="request"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <form method="post"
                                              action="${pageContext.request.contextPath}/controller?command=${cmd}">
                                            <input type="hidden" name="artistid" value="${artist.getId()}">
                                            <input type="hidden" name="trackid" value="${track.getId()}">
                                            <input type="hidden" name="trackpage" value="${trackpsr.getPage()}">
                                            <button type="submit" class="btn btn-sm">
                                                <c:choose>
                                                    <c:when test="${trackpsr.getFlags()[status.index]}">
                                                        <fmt:message key="edit.artist.remove"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <fmt:message key="edit.artist.add"/>
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
                            <c:set var="command" value="edit-artist-page&artistid=${artist.getId()}" scope="request"/>
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
