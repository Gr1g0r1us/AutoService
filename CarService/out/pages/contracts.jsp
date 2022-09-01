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
    <title>Договоры</title>
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
            <a class="nav-link active" href="contracts">Договоры</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="work">Работы</a>
        </li>
    </ul>
</nav>

<p></p>

<p>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addOrEditContract"
            style="margin-left: 2.5%;" onclick="addTitleInModalHeaderAndButton()">Добавить</button>
</p>

<div class="modal" id="addOrEditContract">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST" action="contracts" enctype="text/html;charset=UTF-8">
                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title" id="modalTitle"></h4>
                    <button type="button" class="close" data-dismiss="modal" onclick="clearFillInputsContractModal()">×</button>
                </div>

                <!-- Modal body -->
                <div class="modal-body">
                    <div class="form-group">
                        <label for="fioCustomer">ФИО заказчика:</label>
                        <input required type="text" class="form-control" id="fioCustomer" name="fioCustomer">
                    </div>
                    <div class="form-group">
                        <label for="car">Автомобиль:</label>
                        <select class="form-control" id="car" name="carList">
                            <c:forEach var="car" items="${cars}">
                                <option value="${car.getId()}">${car.getRegNumber()} ${car.getBrand()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="listWorks">Список работ:</label>
                        <select multiple class="form-control" id="listWorks" name="listWorks">
                            <c:forEach var="work" items="${works}">
                                <option value="${work.getId()}">${work.getWorkName()}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="startDate">Дата заключения договора:</label>
                        <input required type="date" class="form-control" id="startDate" name="startDate">
                    </div>
                    <div class="form-group">
                        <label for="endDate">Дата окончания договора:</label>
                        <input required type="date" class="form-control" id="endDate" name="endDate">
                    </div>
                </div>

                <!-- Modal footer -->
                <div class="modal-footer">
                    <input type="hidden" class="form-control" id="id" name="id">
                    <button type="submit" class="btn btn-success" id="addOrSaveButton" onclick="getListWorks()">Сохранить</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="clearFillInputsContractModal()">Отмена</button>
                </div>
            </form>
        </div>
    </div>
</div>

<table id="contracts" class="table table-striped" style="width: 95%;" align="center">
    <tr>
        <th style="width: 17%;">ФИО заказчика</th>
        <th style="width: 17%;">Автомобиль</th>
        <th style="width: 25%;">Список работ</th>
        <th style="width: 12%;">Дата начала работ</th>
        <th style="width: 12%;">Дата окончания работ</th>
        <th style="width: 17%;">Действия</th>
    </tr>

    <c:forEach var="contract" items="${contracts}">
        <tr>
            <td>${contract.getFioCustomer()}</td>
            <td>${contract.getCar().getRegNumber()} ${contract.getCar().getBrand()}</td>
            <td>${contract.printWorks()}</td>
            <td>${contract.getStartDate()}</td>
            <td>${contract.getEndDate()}</td>

            <td>
                <button type="button" class="btn btn-warning btn-sm"
                        data-id="${contract.getId()}"
                        data-fioCustomer="${contract.getFioCustomer()}"
                        data-carbrand="${contract.getCar().getId()}"
                        data-listworks="${contract.printWorks()}"
                        data-startdate="${contract.getStartDate()}"
                        data-enddate="${contract.getEndDate()}"
                        onclick="fillEditContractModal(this)"
                        data-toggle="modal"
                        data-target="#addOrEditContract">Изменить</button>
                <button type="button" class="btn btn-danger btn-sm"
                        data-id="${contract.id}"
                        onclick="fillRemoveContractModal(this)"
                        data-toggle="modal"
                        data-target="#removeContract">Удалить</button>
            </td>
        </tr>
    </c:forEach>
</table>

<div class="modal" id="removeContract">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST" action="contracts" enctype="text/html;charset=UTF-8">

                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">Вы действительно хотите удалить данный договор?</h4>
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

    function fillEditContractModal(but) {
        document.getElementById("modalTitle").innerText = "Изменить договор";
        document.getElementById("addOrSaveButton").innerText = "Сохранить";
        document.getElementById('id').value = but.dataset.id;
        document.getElementById('fioCustomer').value = but.dataset.fiocustomer;
        document.getElementById('car').value = but.dataset.carbrand;

        var elm = document.getElementById("listWorks");
        for (var i = 0; i < elm.options.length; i ++) {
            if (but.dataset.listworks.indexOf(elm.options[i].text) > -1) {
                elm.options[i].selected = true;
            } else {
                elm.options[i].selected = false;
            }
        }
        document.getElementById('startDate').value = but.dataset.startdate;
        document.getElementById('endDate').value = but.dataset.enddate;
    }

    function fillRemoveContractModal(but) {
        document.getElementById('removeId').value = but.dataset.id;
    }

    function clearFillInputsContractModal() {
        document.getElementById('id').value = "";
        document.getElementById('fio').value = "";
        document.getElementById('car').value = "";
        document.getElementById('listWorks').value = "";
        document.getElementById('startDate').value = "";
        document.getElementById('endDate').value = "";
    }

    function getListWorks() {
        if (document.getElementById('endDate').value < document.getElementById('startDate').value) {
            document.getElementById('endDate').value = "";
            alert("Дата начала работ должна быть не позднее даты завершения работ");
        }
    }
</script>
</body>
</html>
