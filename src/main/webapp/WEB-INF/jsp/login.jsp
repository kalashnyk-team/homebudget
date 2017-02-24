<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Bootstrap Snippet: Login Form</title>


    <link rel='stylesheet prefetch' href='<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>'>
    <script src="<c:url value="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/signin.css"/>">


</head>

<body>
<div class="wrapper">
    <form class="form-signin" action="j_spring_security_check" method="post">
        <h2 class="form-signin-heading">Please login</h2>
        <input type="text" id="username" class="form-control" name="j_username" placeholder="Email Address" required="" autofocus="" />
        <input type="password" id="password" class="form-control" name="j_password" placeholder="Password" required=""/>
        <label class="checkbox">
            <input type="checkbox" value="remember-me" id="rememberMe" name="remember-me"> Remember me
        </label>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
        <h2 class="form-signin-heading">OR signin as demo-user</h2>
        <button onclick="demoUser()" class="btn btn-lg btn-primary btn-block" <%--type="submit"--%>>Login as demo-user</button>
    </form>
</div>

<script>
    function demoUser() {
        $('#username').val('user@demo.com');
        $('#password').val('qwerty');
        $("form").submit();
    }
</script>


</body>
</html>