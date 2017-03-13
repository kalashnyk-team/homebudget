<%--
  Created by IntelliJ IDEA.
  User: Sergii
  Date: 13.03.2017
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html>
<head>
    <title>Главная</title>
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div id="charts-container">
    <canvas id="canvas" width="360" height="340">
        Your browser does not support HTML5 Canvas
    </canvas>
    <h3>Куда уходят деньги</h3>
</div>
</body>
</html>
