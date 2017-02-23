<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3>Операции по счету ${account.name}</h3>
    <hr>
    <a href="operations/create">Ввести затраты/доходы</a>
    <hr>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Дата</th>
                <th>Категория</th>
                <th>Сумма</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${operations}" var="operation">
                <tr>
                    <jsp:useBean id="operation" scope="page" type="org.kalashnyk.homebudget.model.Operation"/>
                    <td>${operation.getDate()}</td>
                    <td>${operation.category.name}</td>
                    <td>${account.currency}${operation.amount}</td>
                    <td><a href="operations/update?id=${operation.id}">Change</a></td>
                    <td><a href="operations/delete?id=${operation.id}">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
</section>
</body>
</html>
