<%--
  Created by IntelliJ IDEA.
  User: MY
  Date: 17.08.2016
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags/custom-tag.tld" prefix="fn" %>
<%@ page isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Booking edit</title>
    <link href="../../app/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../app/assets/css/bootstrap-datepicker.min.css" rel="stylesheet" type="text/css">
    <link href="../../app/assets/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<header>
    <c:import url="admin-header.jsp"/>
</header>
<main>
    <h2 class="container">Processing Requests</h2>
    <form id="book" class="pull-left booking-form" action="../../controller" method="post" target="available_frame">
        <input type="hidden" name="command" value="booking"/>
        <input type="hidden" name="requestId" value="${request.requestID}"/>

        <div class="form-group">
            <h4>Dates</h4>

            <div>
                <label>From</label>

                <div class="input-group date" data-provide="datepicker">
                    <input type="text" name="start" value="${request.startDate}" class="form-control">

                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-th"></span>
                    </div>
                </div>
            </div>
            <div>
                <label>To</label>

                <div class="input-group date" data-provide="datepicker">
                    <input type="text" name="end" value="${request.endDate}" class="form-control">

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

        <div class="form-group">
            <label for="roomId">Room</label>

            <input type="text" class="form-control" id="roomId" name="roomId"
                   placeholder="Room" value="">
        </div>

        <div class="button-group">
            <button id="create" type="submit" class="btn btn-primary">Booking</button>

            <input name="availableRoom" value="Show Room" type="submit" class="btn btn-info" data-toggle="modal"
                   data-target="#myModal">

            <c:if test="${role==true}">
                <a class="btn btn-default pull-right" href="controller?command=booking_list">Cancel</a>
            </c:if>
            <c:if test="${role==false}">
                <a class="btn btn-default pull-right" href="controller?command=booking_list">Cancel</a>
            </c:if>
        </div>
    </form>
</main>
<div id="myModal" class="modal fade" role="dialog">
    <div class="modal-dialog modal-dialog-for-available-rooms">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Available Rooms</h4>
            </div>
            <div class="modal-body">
                <iframe id="available_frame" name="available_frame" src="jsp/admin/available-rooms.jsp" width="550px"
                        height="250px"
                        frameborder="0px"></iframe>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>

    </div>
</div>

<script>
    var form = document.getElementById('book');
    document.getElementById('create').onclick = function () {
        form.target = '_self';
        form.submit();
    };

</script>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../../app/assets/js/bootstrap.min.js"></script>
<script src="../../app/assets/js/bootstrap-datepicker.min.js"></script>
</body>
</html>