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
    <%--<meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
    <meta name="viewport" content="width=device-width">

</head>
<body>

<div class="row">
    <div class="col-sm-3 col-md-2"><a href="<c:url value="/"/>">На главную</a></div>
    <div class="col-sm-3 col-md-2"><a href="<c:url value="/accounts"/>">Счета</a></div>
    <div class="col-sm-3 col-md-2"><a href="<c:url value="/reports"/>">Отчеты</a></div>
    <div class="col-sm-3 col-md-2"><a href="<c:url value="/categories"/>">Категории</a></div>
</div>

<form class="form-horizontal" method="GET" action="<c:url value="/operations/new"/>">
    <button class="btn btn-primary" type="submit"><span class="glyphicon glyphicon-pencil"></span> Расход</button>
</form>
<form class="form-horizontal" method="GET" action="<c:url value="/operations/transfer"/>">
    <button class="btn btn-primary" type="submit"><span class="glyphicon glyphicon-pencil"></span> Перевод</button>
</form>
</body>
</html>