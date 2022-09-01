<%--
  Created by IntelliJ IDEA.
  User: lisya
  Date: 22.04.2022
  Time: 1:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Прайс-лист</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>

<body>
<c:if test="${err!=null}">
    <script>
        alert("${err}");
    </script>
</c:if>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <ul class="navbar-nav">
        <li class="nav-item">
            <a class="nav-link" href="cars">Автомобили</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="contracts">Договоры</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" href="work">Работы</a>
        </li>
    </ul>
</nav>

<p></p>

<p>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addOrEditWork"
            style="margin-left: 20%;" onclick="addTitleInModalHeaderAndButton()">Добавить</button>
</p>

<div class="modal" id="addOrEditWork">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST" action="work" enctype="text/html;charset=UTF-8">
                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title" id="modalTitle"></h4>
                    <button type="button" class="close" data-dismiss="modal" onclick="clearFillInputsPriceListModal()">×</button>
                </div>

                <!-- Modal body -->
                <div class="modal-body">
                    <div class="form-group">
                        <label for="name">Название:</label>
                        <input type="text" class="form-control" id="name" name="name" maxlength="50"
                               pattern="[A-Za-zА-Яа-яЁё\s0-9-]+$" title="Название может содежать только буквы, цифры и '-'"
                               placeholder="Введите название, например Химчистка">
                    </div>
                    <div class="form-group">
                        <label for="price">Стоимость:</label>
                        <input type="text" class="form-control" id="price" name="price" maxlength="20"
                               pattern="[А-Яа-яЁё\s\.\,]+$" title="Стоимость может может содержать только цифры, разделенные точкой, например 123.50"
                               placeholder="Укажите стоимость, например 1999.99">
                    </div>
                </div>

                <!-- Modal footer -->
                <div class="modal-footer">
                    <input type="hidden" class="form-control" id="id" name="id">
                    <button type="submit" class="btn btn-success" id="addOrSaveButton">Сохранить</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="clearFillInputsPriceListModal()">Отмена</button>
                </div>
            </form>
        </div>
    </div>
</div>

<table id="work" class="table table-striped" style="width: 60%;" align="center">
    <tr>
        <th style="width: 58%;">Название работы</th>
        <th style="width: 20%;">Стоимость</th>
        <th style="width: 23%;">Действия</th>
    </tr>

    <c:forEach var="work" items="${work}">
        <tr>
            <td>${work.getWorkName()}</td>
            <td>${work.getPrice()}</td>
            <td>
                <button type="button" class="btn btn-warning btn-sm"
                        data-id="${work.getId()}"
                        data-workname="${work.getWorkName()}"
                        data-price="${work.getPrice()}"
                        onclick="fillEditPriceListModal(this)"
                        data-toggle="modal"
                        data-target="#addOrEditWork">Изменить</button>
                <button type="button" class="btn btn-danger btn-sm"
                        data-id="${work.getId()}"
                        onclick="fillRemovePriceListModal(this)"
                        data-toggle="modal"
                        data-target="#removeWork">Удалить</button>
            </td>
        </tr>
    </c:forEach>
</table>

<div class="modal" id="removeWork">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST" action="work" enctype="text/html;charset=UTF-8">

                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">При удалении работы будут удалены все связанные с ней договоры. Вы хотите продолжить?</h4>
                    <button type="button" class="close" data-dismiss="modal">×</button>
                </div>

                <!-- Modal body -->
                <%--<div class="modal-body">

                </div>--%>

                <!-- Modal footer -->
                <div class="modal-footer">
                    <input type="hidden" class="form-control" id="removeId" name="removeId">
                    <button type="submit" class="btn btn-success">Да</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Нет</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function addTitleInModalHeaderAndButton() {
        document.getElementById("modalTitle").innerText = "Добавить работу";
        document.getElementById("addOrSaveButton").innerText = "Добавить";
    }

    function fillEditPriceListModal(but) {
        document.getElementById("modalTitle").innerText = "Изменить работу";
        document.getElementById("addOrSaveButton").innerText = "Сохранить";
        document.getElementById('id').value = but.dataset.id;
        document.getElementById('name').value = but.dataset.workname;
        document.getElementById('price').value = but.dataset.price;
    }

    function fillRemovePriceListModal(but) {
        document.getElementById('removeId').value = but.dataset.id;
    }

    function clearFillInputsPriceListModal() {
        document.getElementById('id').value = "";
        document.getElementById('name').value = "";
        document.getElementById('price').value = "";
    }
</script>
</body>
</html>
