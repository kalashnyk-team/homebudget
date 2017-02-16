<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3>Создать/изменить категорию</h3>
    <form class="form-horizontal" method="POST" action="categories/save">
        <div class="form-group"><input name="id" type="hidden" value="${category.id}"></div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="parent">Родительская категория:</label>
            <div class="col-sm-2">
                <select class="form-control" id="parent" name="parentId">
                    <option>--Родительская категория--</option>
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
            <label class="control-label col-sm-2" for="type">Тип:</label>
            <div class="col-sm-2">
                <select class="form-control" id="type" name="type">
                    <option disabled>Выберите тип</option>
                    <option value="income">Доход</option>
                    <option value="expense">Расход</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="name">Название: </label>
            <div class="col-sm-2">
                <input class="form-control" id="name" name="name" type="text" value="${category.name}"/>
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