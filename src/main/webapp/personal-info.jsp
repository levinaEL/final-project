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
    <link href="app/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="app/assets/css/bootstrap-datepicker.min.css" rel="stylesheet" type="text/css">
    <link href="app/assets/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<form name="ban" method=post action="controller">
    <input type="hidden" name="command" value="ban"/>
    <input type="hidden" name="id" value="${client.id}"/>
</form>

<h2 class="text-center">Personal Information</h2>

<form class="form-horizontal border-forms" method="post" action="controller">
    <input type="hidden" name="command" value="create_client"/>
    <input type="hidden" name="id" value="${client.id}"/>

    <div class="form-group">
        <label for="fname" class="col-sm-2 control-label">First Name</label>

        <div class="col-sm-10">
            <input type="text" class="form-control" id="fname" name="fname" value="${client.firstName}"
                   pattern="^[a-zA-Z][a-zA-Z0-9-_\.]{1,20}$" placeholder="First name" required/>
        </div>
    </div>
    <div class="form-group">
        <label for="patronymic" class="col-sm-2 control-label">Patronymic</label>

        <div class="col-sm-10">
            <input type="text" class="form-control" id="patronymic" name="pname" value="${client.patronymicName}"
                   pattern="^[a-zA-Z][a-zA-Z0-9-_\.]{1,20}$" placeholder="Patronymic" required>
        </div>
    </div>
    <div class="form-group">
        <label for="lname" class="col-sm-2 control-label">Last Name</label>

        <div class="col-sm-10">
            <input type="text" class="form-control" id="lname" name="lname" value="${client.lastName}"
                   pattern="^[a-zA-Z][a-zA-Z0-9-_\.]{1,20}$" placeholder="Last name" required>
        </div>
    </div>

    <div class="form-group">
        <label for="birthday" class="col-sm-2 control-label">Birthday</label>

        <div class="col-sm-10">
            <input type="date" class="form-control" id="birthday" value="${client.birthday}" name="birthday"
                   pattern="(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d" required>
        </div>
    </div>

    <div class="form-group">
        <label class=" col-sm-2 control-label">Passport Data</label>

        <div class="col-sm-10">
            <input type="text" class="form-control passport-series" id="pSeries" name="pSeries"
                   value="${client.passportSeries}" placeholder="Series" required>
            <input type="text" class="form-control passport-number" id="pNumber" name="pNumber"
                   value="${client.passportNumber}" placeholder="Number" required>

            <input type="text" class="form-control passport-personal-number" id="prslNumber"
                   value="${client.personalNumber}" name="prslNumber" placeholder="Personal number" required>
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
                   placeholder="Email">
        </div>
    </div>

    <div class="form-group">
        <label for="phone" class="col-sm-2 control-label">Phone Number</label>

        <div class="col-sm-10">
            <input type="text" class="form-control" id="phone" name="phone" value="${client.phoneNumber}"
                   pattern="^(801[567]|802[1234]\d{7})|\d{7})$" placeholder="Phone number">
        </div>
    </div>

    <div class="button-group">
        <button type="submit" class="btn btn-primary">Save</button>

        <c:if test="${role==true}">
            <a class="btn btn-default pull-right" href="controller?command=booking_list">Cancel</a>
        </c:if>
        <c:if test="${role==false}">
            <a class="btn btn-default pull-right"  href="controller?command=booking_list">Cancel</a>
        </c:if>
        <c:if test="${role==true and  not empty client.id}">
            <a href="javascript:document.ban.submit()" class="btn btn-danger">Ban</a>
        </c:if>
    </div>

</form>

<script src="app/https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="app/assets/js/bootstrap.min.js"></script>
<script src="app/assets/js/bootstrap-datepicker.min.js"></script>
</body>
</html>
