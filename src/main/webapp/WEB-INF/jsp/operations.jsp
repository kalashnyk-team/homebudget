<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3>Операции по счету ${account.name}</h3>
    <div class="container-fluid">
        <c:forEach items="${groupedOperations}" var="group">
            <div class="row">
                <hr>
                <div class="col-md-12 col-lg-10">
                        <b>${group.key}</b>
                </div>
            </div>
            <c:forEach items="${group.value}" var="operation">
                <span>*&nbsp;*&nbsp;*&nbsp;*&nbsp;*&nbsp;*&nbsp;*&nbsp;*  &nbsp;*&nbsp;*</span>
                <div class="row" title="${operation.comment}">
                    <div class="col-md-5 col-lg-3">
                            ${operation.description()}: <span class="<c:if test="${operation.isExpense()}">expense</c:if>">${operation.amount}</span>
                    </div>
                    <div class="col-md-5 col-lg-2">
                        Остаток: <span class="<c:if test="${operation.remainOnAccount<0}">negative-amount</c:if>">${operation.remainOnAccount}</span>
                    </div>
                </div>
            </c:forEach>
        </c:forEach>
    </div>
</section>
</body>
</html>
