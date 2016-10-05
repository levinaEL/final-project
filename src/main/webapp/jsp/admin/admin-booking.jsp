<%@ page contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tags/custom-tag.tld" prefix="fn" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="text"/>
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
    <h2 class="container"><fmt:message key="form.admin.booking.title"/></h2>

    <form id="book" class="pull-left booking-form" action="../../controller" method="post"
          target="available_frame">
        <input type="hidden" name="command" value="booking"/>
        <input type="hidden" name="req_id" value="${request.requestID}"/>
        <input type="hidden" name="client_id" value="${param.client_id}"/>

        <div class="form-group">
            <h4><fmt:message key="form.booking.dates"/></h4>

            <div>
                <label for="start"><fmt:message key="form.booking.label.start"/></label>

                <div class="input-group date" data-provide="datepicker">
                    <input id="start" type="text" name="start_date" value="${request.startDate}" class="form-control">

                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-th"></span>
                    </div>
                </div>
            </div>
            <div>
                <label for="end"><fmt:message key="form.booking.label.end"/></label>

                <div class="input-group date" data-provide="datepicker">
                    <input id="end" type="text" name="end_date" value="${request.endDate}" class="form-control">

                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-th"></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <h4><fmt:message key="form.booking.roomInfo"/></h4>

            <div>
                <label for="quantity"><fmt:message key="request.info.quantity"/></label>
                <select class="form-control" name="persons_count" id="quantity">
                    <option selected>${request.personsCount}</option>
                    <option>1</option>
                    <option>2</option>
                    <option>3</option>
                    <option>4</option>
                    <option>5</option>
                </select>
            </div>
            <div>
                <label for="room-type"><fmt:message key="request.info.type"/></label>
                <select class="form-control" name="room_type" id="room-type">
                    <option selected>${request.roomType}</option>>
                    <option>SINGLE</option>
                    <option>DOUBLE</option>
                    <option>TWIN</option>
                    <option>LUX</option>
                    <option>FAMILY</option>
                </select>
            </div>
        </div>
        <%--<div class="form-group">--%>
            <%--<label for="sale"><fmt:message key="action.sale"/></label>--%>
            <%--<input type="checkbox" class="" id="sale" name="sale" value="">--%>
        <%--</div>--%>
        <div class="form-group">
            <label for="roomId"><fmt:message key="room.label"/></label>

            <input type="text" class="form-control" id="roomId" name="room_id"
                   placeholder="Room" value="">
        </div>

        <div class="button-group">
            <button id="create" type="submit" class="btn btn-primary"><fmt:message key="action.approve"/></button>

            <input name="availableRoom" value="<fmt:message key="action.showRoom"/>" type="submit" class="btn btn-info" data-toggle="modal"
                   data-target="#myModal">
            <a class="btn btn-default pull-right" href="../../controller?command=booking_list">
                <fmt:message key="button.cancel"/>
            </a>
        </div>
    </form>
</main>
<div id="myModal" class="modal fade" role="dialog">
    <div class="modal-dialog modal-dialog-for-available-rooms">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><fmt:message key="room.action.title"/></h4>
            </div>
            <div class="modal-body">
                <iframe id="available_frame" name="available_frame" src="available-rooms.jsp" width="550px"
                        height="250px"
                        frameborder="0px"></iframe>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>

    </div>
</div>
<c:import url="../scripts-import.jsp"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="../../app/assets/js/bootstrap.min.js"></script>
<script src="../../app/assets/js/bootstrap-datepicker.min.js"></script>
</body>
</html>