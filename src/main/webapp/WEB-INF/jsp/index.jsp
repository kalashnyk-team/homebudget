<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<div class="jumbotron">
    <div class="container">
        <form method="post" action="users">
            <select name="userId">
            <option value="1" selected>samchuk</option>
            <option value="2">kalashnyk</option>
            </select>
            <button type="submit">Log in</button>
        </form>
    </div>
</div>
</body>
</html>