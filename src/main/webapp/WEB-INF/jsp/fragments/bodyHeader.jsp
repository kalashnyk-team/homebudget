<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <%--    <c:set var="url">${pageContext.request.requestURL}</c:set>
        <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/"/>--%>
    <%--<base href="${pageContext.request.contextPath}/"/>--%>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css"/>">
    <link rel="stylesheet" type="text/css"
          href="<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>">
    <script src="<c:url value="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"/>"></script>
    <script src="<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"/>"></script>
</head>
<body>

<div class="row">
    <div class="col-sm-1"><a href="<c:url value="/"/>">На главную</a>|</div>
    <div class="col-sm-1"><a href="<c:url value="/accounts"/>">Счета</a> |</div>
    <div class="col-sm-1"><a href="<c:url value="/reports"/>">Отчеты</a> |</div>
    <div class="col-sm-1"><a href="<c:url value="/categories"/>">Категории</a></div>
</div>
</body>
</html>