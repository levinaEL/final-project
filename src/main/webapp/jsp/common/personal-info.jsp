<%--
  Created by IntelliJ IDEA.
  User: MY
  Date: 08.08.2016
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Personal information</title>
    <link href="../../app/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../app/assets/css/bootstrap-datepicker.min.css" rel="stylesheet" type="text/css">
    <link href="../../app/assets/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<header>
    <c:choose>
        <c:when test="${role==true}">
            <c:import url="../admin/admin-header.jsp"/>
        </c:when>
        <c:otherwise>
            <c:import url="../client/client-header.jsp"/>
        </c:otherwise>
    </c:choose>
</header>
<main>
    <h3 class="text-center text-capitalize">Personal Information</h3>

    <form class="form-horizontal border-forms" method="post" action="../../controller">

        <input type="hidden" name="command" value="create_client"/>
        <input type="hidden" name="clientId" value="${client.id}"/>

        <div class="form-group">
            <label for="fname" class="col-sm-2 control-label">First Name</label>

            <div class="col-sm-10">
                <input type="text" class="form-control" id="fname" name="fname" value="${client.firstName}"
                       placeholder="First name" required pattern="^[a-zA-Z'\s]+"/>
                <strong class="text-danger text-center"><c:out value="${errName}"/></strong>
            </div>
        </div>
        <div class="form-group">
            <label for="patronymic" class="col-sm-2 control-label">Patronymic</label>

            <div class="col-sm-10">
                <input type="text" class="form-control" id="patronymic" name="pname" value="${client.patronymicName}"
                       placeholder="Patronymic">
            </div>
        </div>
        <div class="form-group">
            <label for="lname" class="col-sm-2 control-label">Last Name</label>

            <div class="col-sm-10">
                <input type="text" class="form-control" id="lname" name="lname" value="${client.lastName}"
                       placeholder="Last name" required pattern="^[a-zA-Z][a-zA-Z0-9-_\.]{3,16}$">
                <strong class="text-danger text-center"><c:out value="${errName}"/></strong>
            </div>
        </div>

        <div class="form-group">
            <label for="birthday" class="col-sm-2 control-label">Birthday</label>

            <div class="col-sm-10">
                <input type="date" class="form-control" id="birthday" value="${client.birthday}" name="birthday"
                       required pattern="(19|20)\d\d([- /.])(0[1-9]|1[012])\2(0[1-9]|[12][0-9]|3[01])" >
                <strong class="text-danger text-center"><c:out value="${errBirth}"/></strong>
            </div>
        </div>

        <div class="form-group">
            <label class=" col-sm-2 control-label">Passport Data</label>

            <div class="col-sm-10">
                <input type="text" class="form-control passport-series" id="pSeries" name="pSeries"
                       value="${client.passportSeries}" placeholder="Series" required pattern="[A-Z]{1,4}">
                <input type="text" class="form-control passport-number" id="pNumber" name="pNumber"
                       value="${client.passportNumber}" placeholder="Number" required pattern="[\d]{6,14}">

                <input type="text" class="form-control passport-personal-number" id="prslNumber"
                       value="${client.personalNumber}" name="prslNumber" placeholder="Personal number"
                       required pattern="[A-Z0-9]{11,16}">
                <strong class="text-danger text-center"><c:out value="${errPaasport}"/></strong>
            </div>
        </div>

        <div class="form-group">
            <label for="address" class="col-sm-2 control-label">Address</label>

            <div class="col-sm-10">
                <input type="text" class="form-control" id="address" name="address"
                       value="${client.address}" placeholder="Address">
            </div>
        </div>

        <div class="form-group">
            <label for="email" class="col-sm-2 control-label">Email</label>

            <div class="col-sm-10">
                <input type="email" class="form-control" id="email" name="email" value="${client.email}"
                       placeholder="Email" required>
                <strong class="text-danger text-center"><c:out value="${errEmail}"/></strong>
            </div>
        </div>

        <div class="form-group">
            <label for="phone" class="col-sm-2 control-label">Phone Number</label>

            <div class="col-sm-10">
                <input type="text" class="form-control" id="phone" name="phone" value="${client.phoneNumber}"
                       placeholder="Phone number"
                       pattern="\+(375)(\d){2}(\d){3}(-)?(\d){2}(-)?(\d){2}" title="+375(xx)...">
                <strong class="text-danger text-center"><c:out value="${errPhone}"/></strong>
            </div>
        </div>

        <div class="button-group">
            <button type="submit" class="btn btn-primary">Save</button>
            <a class="btn btn-default pull-right" href="javascript:document.booking_list.submit()">Cancel</a>
        </div>

    </form>
</main>
<script src="app/https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="../../app/assets/js/bootstrap.min.js"></script>
<script src="../../app/assets/js/bootstrap-datepicker.min.js"></script>
</body>
</html>
