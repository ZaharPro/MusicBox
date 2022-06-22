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
        <div class="row pt-3 pb-3">
            <div class="col-lg-2 col-md-2">
                <c:choose>
                    <c:when test="${album != null && album.getPicture() != null}">
                        <img class="card-img"
                             src="${pageContext.request.contextPath}/file/img/${album.getPicture() != null}">
                    </c:when>
                    <c:otherwise>
                        <img class="card-img" src="${pageContext.request.contextPath}/system/img/album-default.png">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-lg-10 col-md-10 f-col h-100">
                <form method="post" class="row"
                      action="${pageContext.request.contextPath}/controller?command=album-save"
                      enctype="multipart/form-data">
                    <c:if test="${album != null}">
                        <input type="hidden" name="albumid" value="${album.getId()}">
                    </c:if>
                    <div class="form-outline col-3">
                        <label for="albumName" class="title h4">
                            <fmt:message key="edit.album.enter.name"/>
                        </label>
                        <input type="text" id="albumName" name="name" required
                               class="form-control form-control-lg w-100"
                        <c:if test="${album != null}">
                               value="${album.getName()}"
                        </c:if>>
                    </div>

                    <div class="col-6">
                        <div class="file-drop-area h-100 p-3">
                            <label for="picture" class="text-center">
                                <fmt:message key="edit.album.upload.picture"/>
                            </label>
                            <input class="file-input w-100" id="picture" type="file" name="picture"
                                   accept=".png, .jpg, .jpeg, .gif">
                        </div>
                    </div>

                    <div class="btn-group-lg col-3 f-col">
                        <button type="submit" class="btn w-100">
                            <fmt:message key="edit.album.save"/>
                        </button>
                        <c:if test="${album != null}">
                            <a class="btn w-100 mt-2"
                               href="${pageContext.request.contextPath}/controller?command=album-delete&albumid=${album.getId()}">
                                <fmt:message key="edit.album.delete"/>
                            </a>
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>