<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<section>
    <h3>Счета</h3>
    <hr>
    <a href="accounts/create">Создать счет</a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Название</th>
            <th>Валюта</th>
            <th>Тип</th>
            <th>Остаток</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${accountList}" var="account">
            <tr>
            <jsp:useBean id="account" scope="page" type="org.kalashnyk.homebudget.model.Account"/>
                <td>${account.name}</td>
                <td>${account.currency}</td>
                <td>${account.type}</td>
                <td>${account.amount}</td>
                <td><a href="accounts/update?id=${account.id}">Update</a></td>
                <td><a href="accounts/delete?id=${account.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>