<%--
  Created by IntelliJ IDEA.
  User: MY
  Date: 10.08.2016
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Booking edit</title>
    <link href="app/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="app/assets/css/bootstrap-datepicker.min.css" rel="stylesheet" type="text/css">
    <link href="app/assets/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<h2 class="text-center">Room Reservation</h2>

<form class="border-forms" action="controller" method="post">
    <input type="hidden" name="command" value="booking" />
    <div class="form-group">
        <h4>Dates</h4>

        <div>
            <label>From</label>

            <div class="input-group date" data-provide="datepicker">
                <input type="text" name="start" class="form-control">

                <div class="input-group-addon">
                    <span class="glyphicon glyphicon-th"></span>
                </div>
            </div>
        </div>
        <div>
            <label>To</label>

            <div class="input-group date" data-provide="datepicker">
                <input type="text" name="end" class="form-control">

                <div class="input-group-addon">
                    <span class="glyphicon glyphicon-th"></span>
                </div>
            </div>
        </div>
    </div>
    <div class="form-group">
        <h4>Room info</h4>

        <div>
            <label for="quantity">Quantity</label>
            <select class="form-control" name="number" id="quantity">
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
            </select>
        </div>
        <div>
            <label for="room-type">Room type</label>
            <select class="form-control" name="type" id="room-type">
                <option>Single</option>
                <option>Double</option>
                <option>Twin</option>
                <option>Lux</option>
                <option>Family</option>
            </select>
        </div>
    </div>


    <div class="button-group">
        <button type="submit" class="btn btn-primary">Submit</button>
        <c:if test="${role==true}">
            <a class="btn btn-default pull-right" href="admin-home-prot.jsp">Cancel</a>
        </c:if>
        <c:if test="${role==false}">
            <a class="btn btn-default pull-right" href="client-home-prot.jsp">Cancel</a>
        </c:if>
    </div>
</form>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="app/assets/js/bootstrap.min.js"></script>
<script src="app/assets/js/bootstrap-datepicker.min.js"></script>
</body>
</html>