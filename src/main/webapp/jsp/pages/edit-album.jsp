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

<div class="container d-flex d-flex flex-columnumn h-100 pt-3 pb-3">
    <div class="col card pt-0 pb-3 mb-0 d-flex d-flex flex-columnumn h-100 bg-dark">
        <div class="row pt-3 pb-3" style="border-bottom: 1px solid #dd2476;">
            <div class="col-lg-2 col-md-2">
                <c:choose>
                    <c:when test="${album != null}">
                        <img class="card-img" src="/img/album/${album.getPicture()}" alt="Album picture">
                    </c:when>
                    <c:otherwise>
                        <img class="card-img" src="/img/album/default.png" alt="Album picture">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-lg-10 col-md-10 d-flex justify-content-between align-items-center">
                <form method="post" class="d-flex justify-content-between align-items-center"
                      action="${pageContext.request.contextPath}/controller?command=album-save">
                    <div class="form-outline mb-4">
                        <label for="albumName">
                            <fmt:message key="edit.album.label.name"/>
                        </label>
                        <input type="text" id="albumName" name="name" required class="form-control form-control-lg"
                        <c:if test="${album != null}">
                               value="${album.getName()}"
                        </c:if>>
                    </div>

                    <div class="file-drop-area">
                        <label for="picture">
                            <fmt:message key="edit.album.choose.picture"/>
                        </label>
                        <input class="file-input" id="picture" type="file" name="picture">
                    </div>

                    <button type="submit" class="btn btn-sm w-100 mt-3">
                        <fmt:message key="edit.album.save"/>
                    </button>
                </form>
                <c:if test="${album != null}">
                    <form method="post" action="${pageContext.request.contextPath}/controller?command=album-delete">
                        <input type="hidden" name="albumid" value="${album.getId()}"/>
                        <button type="submit" class="btn btn-sm w-100 mt-2">
                            <fmt:message key="edit.album.delete"/>
                        </button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
</div>

</body>
</html>
