<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title"/></title><c:import url="/jsp/fragments/head.jsp"/>
</head>
<body>
<c:redirect url="${pageContext.request.contextPath}/controller?command=home-page"/>
</body>
</html>