<%--
  Created by IntelliJ IDEA.
  User: MY
  Date: 17.08.2016
  Time: 20:50
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
<header>
    <form name="clients" method=post action=controller>
        <input type="hidden" name="command" value="clients_list"/>
    </form>
    <form name="logout" method=post action=controller>
        <input type="hidden" name="command" value="logout"/>
    </form>
    <form name="booking" method=post action=controller>
        <input type="hidden" name="command" value="booking"/>
    </form>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="controller?command=booking_list">Liza app</a>
            </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

                <ul class="nav navbar-nav">

                    <li>
                        <a href="javascript:document.clients.submit()">
                            Clients List
                        </a>
                    </li>
                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li><a class="new-user" href="personal-info.jsp">New Client</a></li>
                    <li>
                        <a href="javascript:document.logout.submit()">
                            Logout
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<h2 class="container">Processing Requests</h2>

<form class="pull-left booking-form" action="controller" method="post">
    <input type="hidden" name="command" value="booking"/>

    <div class="form-group">
        <h4>Dates</h4>

        <div>
            <label>From</label>

            <div class="input-group date" data-provide="datepicker">
                <input type="text" name="start" class="form-control" value="${request.startDate}">

                <div class="input-group-addon">
                    <span class="glyphicon glyphicon-th"></span>
                </div>
            </div>
        </div>
        <div>
            <label>To</label>

            <div class="input-group date" data-provide="datepicker">
                <input type="text" name="end" class="form-control" value="${request.endDate}">

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
                <option selected>${request.personsCount}</option>
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
                <option selected>${request.roomType}</option>
                <option>Single</option>
                <option>Double</option>
                <option>Twin</option>
                <option>Lux</option>
                <option>Family</option>
            </select>
        </div>
    </div>
    <c:if test="${role==true}">
        <div class="form-group">
            <label for="room">Room</label>

            <input type="text" class="form-control" id="room" name="room"
                   placeholder="Room">

        </div>
    </c:if>
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


<table class="table table-striped table-bordered pull-right table-rooms">
    <thead>
    <tr>
        <th>#</th>
        <th>Type Room</th>
        <th>Number Seats</th>
        <th>Cost</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <th><a>101</a></th>
        <th>Single</th>
        <th>1</th>
        <th>300</th>
    </tr>
    </tbody>
</table>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="app/assets/js/bootstrap.min.js"></script>
<script src="app/assets/js/bootstrap-datepicker.min.js"></script>
</body>
</html>