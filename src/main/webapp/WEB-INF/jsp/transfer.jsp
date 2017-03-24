<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>

</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3>Новый перевод</h3>
    <form class="form-horizontal" method="POST">
        <div class="form-group"><input name="id" type="hidden" value="${operation.id}"></div>
        <div class="form-group">
            <label class="control-label col-xs-2 col-lg-2" for="datePicker">Дата</label>
            <div class="col-xs-9 col-lg-3">
                <input class="form-control" id="datePicker" name="date" type="date"
                       value="${operation.date}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-lg-2 col-xs-2" for="toAccount">Перевод со счета:</label>
            <div class="col-lg-3 col-xs-9">
                <select class="form-control" id="fromAccount" name="fromAccountId">
                    <option disabled>Выберите счет</option>
                    <c:forEach items="${accounts}" var="fromAccount">
                        <jsp:useBean id="fromAccount" scope="page"
                                     type="org.kalashnyk.homebudget.model.Account"/>
                        <option value="${fromAccount.id}">${fromAccount.name} (${fromAccount.amount} ${fromAccount.currency})</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-lg-2 col-xs-2" for="toAccount">Перевод на счет:</label>
            <div class="col-lg-3 col-xs-9">
                <select class="form-control" id="toAccount" name="toAccountId">
                    <option disabled>Выберите счет</option>
                    <c:forEach items="${accounts}" var="toAccount">
                        <jsp:useBean id="toAccount" scope="page"
                                     type="org.kalashnyk.homebudget.model.Account"/>
                        <option value="${toAccount.id}">${toAccount.name} (${toAccount.amount} ${toAccount.currency})</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-2 col-lg-2" for="amount">Сумма</label>
            <div class="col-xs-9 col-lg-3">
                <input class="form-control" id="amount" name="amount" type="number" step="0.01" min="0"
                       value="${operation.amount}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-2 col-lg-2" for="comment">Комментарий</label>
            <div class="col-xs-9 col-lg-3">
                <input class="form-control" id="comment" name="comment" type="text"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-8 col-xs-offset-4">
                <button class="btn btn-primary" type="submit">Save</button>
            </div>
        </div>
    </form>
</section>
<script>
    $(document).ready(function () {
        var today = isoDate(new Date());
        $('#datePicker').val(today);
    });
</script>
</body>
</html>