<%--
  Created by IntelliJ IDEA.
  User: lisya
  Date: 22.04.2022
  Time: 1:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Автомобили</title>
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
            <a class="nav-link active" href="cars">Автомобили</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="contracts">Договоры</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="work">Работы</a>
        </li>
    </ul>
</nav>

<p></p>

<p>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addOrEditCar"
            style="margin-left: 2.5%;" onclick="addTitleInModalHeaderAndButton()">Добавить</button>
</p>

<div class="modal" id="addOrEditCar">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST" action="cars" enctype="text/html;charset=UTF-8">

                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title" id="modalTitle"></h4>
                    <button type="button" class="close" data-dismiss="modal" onclick="clearFillInputsCarModal()">×</button>
                </div>

                <!-- Modal body -->
                <div class="modal-body">
                    <div class="form-group">
                        <label for="regNumber">Госномер:</label>
                        <input required type="text" class="form-control" id="regNumber" name="regNumber" maxlength="9"
                               pattern="([А-Яа-я]{1})([0-9]{3})([А-Яа-я]{2})([0-9]{2,3})" title="Только русские буквы и цифры, в формате А777АА763"
                               placeholder="Введите госномер, например А777АА763">
                    </div>
                    <div class="form-group">
                        <label for="brand">Марка:</label>
                        <input required type="text" class="form-control" id="brand" name="brand" maxlength="30"
                               pattern="[A-Za-zА-Яа-яЁё\s0-9-ну ]+$" title="Поддерживаются только буквы и цифры"
                               placeholder="Введите марку, например BMW M5 Competition">
                    </div>
                    <div class="form-group">
                        <label for="fioOwner">ФИО владельца:</label>
                        <input required type="text" class="form-control" id="fioOwner" name="fioOwner" maxlength="30"
                               pattern="[A-Za-zА-Яа-яЁё\s]+$" title="ФИО должно содержать только буквы"
                               placeholder="Иванов Иван Иванович">
                    </div>
                    <div class="form-group">
                        <label for="releaseDate">Дата выпуска:</label>
                        <input required type="date" class="form-control" id="releaseDate" name="releaseDate">
                    </div>
                </div>

                <!-- Modal footer -->
                <div class="modal-footer">
                    <input type="hidden" class="form-control" id="id" name="id">
                    <button type="submit" class="btn btn-success" id="addOrSaveButton" onclick="checkDate()">Сохранить</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="clearFillInputsCarModal()">Отмена</button>
                </div>
            </form>
        </div>
    </div>
</div>

<table id="cars" class="table table-striped" style="width: 95%;" align="center">
    <tr>
        <th style="width: 14%;">Госномер</th>
        <th style="width: 27%;">Марка</th>
        <th style="width: 27%;">ФИО владельца</th>
        <th style="width: 15%;">Дата выпуска</th>
        <th style="width: 17%;">Действия</th>
    </tr>

    <c:forEach var="car" items="${cars}">
        <tr>
            <td>${car.getRegNumber()}</td>
            <td>${car.getBrand()}</td>
            <td>${car.getFioOwner()}</td>
            <td>${car.getReleaseDate()}</td>
            <td>
                <button type="submit" class="btn btn-warning btn-sm"
                        data-id="${car.getId()}"
                        data-number="${car.getRegNumber()}"
                        data-brand="${car.getBrand()}"
                        data-fio_owner="${car.getFioOwner()}"
                        data-releasedate="${car.getReleaseDate()}"
                        onclick="fillEditCarModal(this)"
                        data-toggle="modal" data-target="#addOrEditCar">Изменить</button>
                <button type="button" class="btn btn-danger btn-sm"
                        data-id="${car.getId()}"
                        onclick="fillRemoveCarModal(this)"
                        data-toggle="modal" data-target="#removeCar" >Удалить</button>
            </td>
        </tr>
    </c:forEach>
</table>

<div class="modal" id="removeCar">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST" action="cars" enctype="text/html;charset=UTF-8">

                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">При удалении автомобиля будут удалены все связанные с ним договоры. Вы хотите продолжить?</h4>
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
        document.getElementById("modalTitle").innerText = "Добавить договор";
        document.getElementById("addOrSaveButton").innerText = "Добавить";
    }

    function checkDate() {
        if (document.getElementById('releaseDate').value > new Date().toISOString().substring(0, 10)) {
            document.getElementById('releaseDate').value = "";
            alert("Дата не может быть больше текущей");
        }
    }

    function fillEditCarModal(but) {
        document.getElementById("modalTitle").innerText = "Изменить автомобиль";
        document.getElementById("addOrSaveButton").innerText = "Сохранить";
        document.getElementById('id').value = but.dataset.id;
        document.getElementById('regNumber').value = but.dataset.number;
        document.getElementById('brand').value = but.dataset.brand;
        document.getElementById('fioOwner').value = but.dataset.fio_owner;
        document.getElementById('releaseDate').value = but.dataset.releasedate;
    }

    function fillRemoveCarModal(but) {
        document.getElementById('removeId').value = but.dataset.id;
    }

    function clearFillInputsCarModal() {
        document.getElementById('id').value = "";
        document.getElementById('regNumber').value = "";
        document.getElementById('brand').value = "";
        document.getElementById('fioOwner').value = "";
        document.getElementById('releaseDate').value = "";
    }
</script>
</body>
</html>
