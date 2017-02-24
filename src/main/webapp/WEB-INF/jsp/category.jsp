<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <div class="container">
        <h3>Создать/изменить категорию</h3>
        <form class="form-horizontal" method="POST">
            <div class="form-group"><input name="id" type="hidden" value="${category.id}"></div>
            <div class="form-group">
                <div class="col-md-offset-2 col-md-2">
                    <label class="radio-inline"><input type="radio" name="type" value="expense" checked>Расход</label>
                    <label class="radio-inline"><input type="radio" name="type" value="income">Доход</label>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2" for="parent">Родительская категория</label>
                <div class="col-md-2">
                    <select class="form-control" id="parent" name="parentId">
                        <option></option>
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
                            >${category}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2" for="name">Название</label>
                <div class="col-md-2">
                    <input class="form-control" id="name" name="name" type="text" value="${category.name}"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-offset-2 col-md-2">
                    <button class="btn btn-default" type="submit">Save</button>
                </div>
            </div>
        </form>
    </div>
</section>
</body>
</html>