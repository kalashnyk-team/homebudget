<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>

</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3>Создать/изменить счет</h3>
    <form class="form-horizontal" method="POST" action="accounts/save">
        <div class="form-group"><input name="id" type="hidden" value="${account.id}"></div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="name">Название: </label>
            <div class="col-sm-2">
                <input class="form-control" id="name" name="name" type="text" value="${account.name}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="currency">Валюта:</label>
            <div class="col-sm-2">
                <select class="form-control" id="currency" name="currencyCode">
                    <option disabled>Выберите валюту</option>
                    <c:forEach items="${currencies}" var="currency">
                        <jsp:useBean id="currency" scope="page" type="org.kalashnyk.homebudget.model.Currency"/>
                        <option
                                <c:if test="${account.currency.name == currency.name}">selected</c:if>
                                value="${currency.name}">${currency.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="amount">Остаток: </label>
            <div class="col-sm-2">
                <input class="form-control" id="amount" name="amount" type="number" step="0.01" min="0"
                       value="${account.amount}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="type">Тип:</label>
            <div class="col-sm-2">
                <select class="form-control" id="type" name="type">
                    <option disabled>Выберите тип счета</option>
                    <c:forEach items="${accountTypes}" var="type">
                        <option
                                <c:if test="${account.type == type}">selected</c:if>
                                value="${type.name()}">${type.name()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-2">
                <button class="btn btn-default" type="submit">Save</button>
            </div>
        </div>
    </form>
</section>
</body>
</html>