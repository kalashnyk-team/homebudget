<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>

</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3>Новый доход/расход</h3>
    <form class="form-horizontal" method="POST" action="new">
        <div class="form-group"><input name="id" type="hidden" value="${operation.id}"></div>
        <div class="form-group">
            <label class="control-label col-sm-3" for="date">Дата: </label>
            <div class="col-sm-6">
                <input class="form-control" id="date" name="date" type="datetime-local"
                       value="${operation.date}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-3" for="account">Счет:</label>
            <div class="col-sm-6">
                <select class="form-control" id="account" name="accId">
                    <option disabled>Выберите счет</option>
                    <c:forEach items="${accounts}" var="account">
                        <jsp:useBean id="account" scope="page"
                                     type="org.kalashnyk.homebudget.model.Account"/>
                        <option value="${account.id}">${account.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-3" for="category">Категория:</label>
            <div class="col-sm-6">
                <select class="form-control" id="category" name="categoryId">
                    <option disabled>Выберите категорию</option>
                    <c:forEach items="${categories}" var="category">
                        <jsp:useBean id="category" scope="page"
                                     type="org.kalashnyk.homebudget.model.OperationCategory"/>
                        <option
                                value="${category.id}"
                                class="
<c:choose>
<c:when test="${category.operationType.name() == 'INCOME'}">income-category</c:when>
<c:otherwise>expense-category</c:otherwise>
</c:choose>"
                        >${category.toString()}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-3" for="amount">Сумма: </label>
            <div class="col-sm-6">
                <input class="form-control" id="amount" name="amount" type="number" step="0.01" min="0"
                       value="${operation.amount}"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-4">
                <button class="btn btn-primary" type="submit">Save</button>
            </div>
        </div>
    </form>
</section>
</body>
</html>