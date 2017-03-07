<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div id="main-container">
<section>
    <h3>Операции по счету ${account.name}</h3>
    <div class="container-fluid">
        <c:forEach items="${groupedOperations}" var="group">
            <div class="row">
                <hr>
                <div class="col-xs-12 col-lg-10">
                    <b>${group.key}</b>
                </div>
            </div>
            <c:forEach items="${group.value}" var="operation">
                <span>*&nbsp;*&nbsp;*&nbsp;*&nbsp;*&nbsp;*&nbsp;*&nbsp;*  &nbsp;*&nbsp;*</span>
                <div id="${operation.id}" class="row" title="${operation.comment}">
                    <div class="col-xs-8 col-lg-3">
                            ${operation.description()}: <span
                            class="<c:if test="${operation.isExpense()}">expense</c:if>">${operation.amount}</span>.
                        Остаток: <span
                            class="<c:if test="${operation.remainOnAccount<0}">negative-amount</c:if>">${operation.remainOnAccount}</span>
                    </div>
                    <div class="col-xs-3 col-lg-2">
                        <form id="delete_${operation.id}" class="form-horizontal" method="get"
                              action="<c:url value="/operations/${operation.id}/delete"/>">
                            <button onclick="confirmDeleting(${operation.id})" class="btn btn-default" type="button"><span class="glyphicon glyphicon-trash"></span></button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </c:forEach>
    </div>
</section></div>
<script>
    function confirmDeleting(id) {
        var confirmation = confirm("Вы действительно хотите удалить оперцию?");
        if (confirmation) {
            $("#delete_" + id).submit();
        } else {
            return false;
        }
    }
</script>
</body>
</html>
