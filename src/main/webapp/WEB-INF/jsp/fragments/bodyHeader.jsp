<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Home Budget</title>
    <%--    <c:set var="url">${pageContext.request.requestURL}</c:set>
        <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/"/>--%>
    <%--<base href="${pageContext.request.contextPath}/"/>--%>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css"/>">
    <link rel="stylesheet" type="text/css"
          href="<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>">
    <script src="<c:url value="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"/>"></script>
    <script src="<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"/>"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="<c:url value="/resources/js/main.js"/>"></script>
</head>
<body>


<div class="fluid-container">
    <div class="col-xs-12 col-md-3"><a href="<c:url value="/"/>">На главную</a></div>
    <div class="col-xs-12 col-md-3"><a href="<c:url value="/accounts"/>">Счета</a></div>
    <div class="col-xs-12 col-md-3"><a href="<c:url value="/categories"/>">Категории</a></div>
    <div class="col-xs-12 col-md-3">
        <form class="form-horizontal" action="<c:url value="/j_spring_security_logout"/>">
            <button class="btn btn-primary" type="submit">log out <span class="glyphicon glyphicon-log-out"></span>
            </button>
        </form>
    </div>
</div>

<div class="fluid-container">
    <div class="col-xs-12">
        <form class="form-horizontal" method="GET" action="<c:url value="/operations/new"/>">
            <button class="btn btn-primary" type="submit">Расход <span class="glyphicon glyphicon-pencil"></span>
            </button>
        </form>
    </div>
    <div class="col-xs-12">
        <form class="form-horizontal" method="GET" action="<c:url value="/operations/transfer"/>">
            <button class="btn btn-primary" type="submit">Перевод <span class="glyphicon glyphicon-pencil"></span>
            </button>
        </form>
    </div>
</div>
</body>
</html>