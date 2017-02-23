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
                    <form class="form-horizontal" method="POST" action="accounts/save">
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
                                                     type="org.kalashnyk.homebudget.model.Currency"/>
                                        <option
                                                <c:if test="${account.currency.name == currency.name}">selected</c:if>
                                                value="${currency.name}">${currency.name}</option>
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
            <table class="table table-striped">
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
                <c:forEach items="${accounts}" var="account">
                    <tr>
                        <jsp:useBean id="account" scope="page" type="org.kalashnyk.homebudget.model.Account"/>
                        <td><a href="accounts/${account.id}/operations">${account.name}</a></td>
                        <td>${account.currency.name}</td>
                        <td>${account.type}</td>
                        <td>${account.amount}</td>
                        <td><a href="accounts/${account.id}">Update</a></td>
                        <td><a href="accounts/${account.id}/delete">Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
    </div>
</section>
</body>
</html>