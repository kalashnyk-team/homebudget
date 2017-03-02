<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3>Счета</h3>
    <hr>

    <!-- Trigger the modal with a button -->
    <button style="margin: 20px" type="button" class="btn btn-info btn-sm" data-toggle="modal"
            data-target="#newAccount">+ Account
    </button>

    <!-- Modal -->
    <div id="newAccount" class="modal fade" role="dialog">
        <div class="modal-dialog modal-sm">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">New account</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" method="POST" action="accounts/new">
                        <div class="form-group"><input name="id" type="hidden" value="${account.id}"></div>
                        <div class="form-group">
                            <label class="control-label col-sm-4" for="name">Название: </label>
                            <div class="col-sm-8">
                                <input class="form-control" id="name" name="name" type="text" value="${account.name}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-4" for="currency">Валюта:</label>
                            <div class="col-sm-8">
                                <select class="form-control" id="currency" name="currencyCode">
                                    <option disabled>Выберите валюту</option>
                                    <c:forEach items="${currencies}" var="currency">
                                        <jsp:useBean id="currency" scope="page"
                                                     type="java.util.Currency"/>
                                        <option
                                                <c:if test="${account.currency.currencyCode == currency.currencyCode}">selected</c:if>
                                                value="${currency.currencyCode}">${currency.currencyCode}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-4" for="amount">Остаток: </label>
                            <div class="col-sm-8">
                                <input class="form-control" id="amount" name="amount" type="number" step="0.01"
                                       min="0"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-4" for="type">Тип:</label>
                            <div class="col-sm-8">
                                <select class="form-control" id="type" name="type">
                                    <option disabled>Выберите тип счета</option>
                                    <c:forEach items="${accountTypes}" var="type">
                                        <option value="${type.name()}">${type.name()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-offset-6 col-sm-6">
                                <button class="btn btn-default" type="submit">Save</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    </div>
    <hr>
    <div class="container">
        <c:forEach items="${groupedAccounts}" var="group">
            <div class="row">
                <div class="col-xs-12 col-lg-10">
                        ${group.key}
                </div>
            </div>
            <c:forEach items="${group.value}" var="acc">
                <div class="row">
                    <div class="col-xs-12 col-lg-10">
                        <a href="<c:url value="/operations?accountId=${acc.id}"/>">${acc.name}
                            (<span class="<c:if test="${acc.amount<0}">negative-amount</c:if>">${acc.amount}${acc.currency.currencyCode}</span>)</a>
                    </div>
                </div>
            </c:forEach>
            <br>
        </c:forEach>
    </div>
</section>
</body>
</html>