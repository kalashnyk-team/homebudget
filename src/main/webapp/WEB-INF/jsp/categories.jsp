<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3>Категории доходов/затрат</h3>
    <hr>
    <a href="categories/new">Создать категорию</a>
    <hr>
    <ul>
        <c:forEach items="${categories}" var="category">
            <jsp:useBean id="category" scope="page" type="org.kalashnyk.homebudget.model.OperationCategory"/>
            <li class=
                <c:choose>
                    <c:when test="${category.operationType.name() == 'INCOME'}">"income-category"</c:when>
                    <c:otherwise>"expense-category"</c:otherwise>
                </c:choose>
            >
                    ${category.toString()}
            </li>
        </c:forEach>
    </ul>
</section>
</body>
</html>
