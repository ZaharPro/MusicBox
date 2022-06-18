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
                    <c:when test="${album != null}">
                        <img class="card-img" src="${pageContext.request.contextPath}/file/img/${album.getPicture()}"
                             alt="Album picture">
                    </c:when>
                    <c:otherwise>
                        <img class="card-img" src="${pageContext.request.contextPath}/system/img/album-default.png"
                             alt="Album picture">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-lg-10 col-md-10">
                <form method="post" class="row"
                      action="${pageContext.request.contextPath}/controller?command=album-save"
                      enctype="multipart/form-data">
                    <c:if test="${album != null}">
                        <input type="hidden" name="albumid" value="${album.getId()}">
                    </c:if>
                    <div class="form-outline col-3">
                        <label for="albumName">
                            <fmt:message key="edit.album.label.name"/>
                        </label>
                        <input type="text" id="albumName" name="name" required class="form-control form-control-lg"
                        <c:if test="${album != null}">
                               value="${album.getName()}"
                        </c:if>>
                    </div>

                    <div class="file-drop-area col-6 p-3" style="border: 1px solid #dd2476; border-radius: 2px;">
                        <label for="picture">
                            <fmt:message key="edit.album.choose.picture"/>
                        </label>
                        <input class="file-input w-100 h-100" id="picture" type="file" name="picture"
                               accept=".png, .jpg, .jpeg, .gif">
                    </div>

                    <div class="btn-group-lg col-3 d-flex flex-column w-100">
                        <button type="submit" class="btn btn-lg w-100">
                            <fmt:message key="edit.album.save"/>
                        </button>
                        <c:if test="${album != null}">
                            <a class="btn btn-lg w-100"
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
