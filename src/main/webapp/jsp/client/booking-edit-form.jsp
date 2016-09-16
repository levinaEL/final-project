<%@ page contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
   <c:import url="client-header.jsp"/>
</header>

<h2 class="text-center"><fmt:message key="form.client.booking.title"/></h2>

<form class="border-forms" action="../../controller" method="post">
    <input type="hidden" name="command" value="booking"/>
    <c:if test="${empty clientId}">
        <div class="alert alert-danger">
            <fmt:message key="message.noinfo"/>
        </div>
        <c:set var="error" value="true"/>
    </c:if>
    <c:if test="${ban == true}">
        <div class="alert alert-danger">
            <fmt:message key="message.ban"/>
        </div>
        <c:set var="error" value="true"/>
    </c:if>
    <c:if test="${errMsg==true}">
        <div class="alert alert-danger">
            <fmt:message key="message.wrong.dates"/>
        </div>
    </c:if>

    <div class="form-group">
        <h4><fmt:message key="form.booking.dates"/></h4>

        <div>
            <label for="start"><fmt:message key="form.booking.label.start"/></label>

            <div class="input-group date" data-provide="datepicker">
                <input type="text" name="start_date" id="start" class="form-control" title="yyyy-MM-dd OR dd/MM/yyyy"
                       pattern="\d{1,2}/\d{1,2}/\d{4}" required>

                <div class="input-group-addon">
                    <span class="glyphicon glyphicon-th"></span>
                </div>
            </div>
        </div>
        <div>
            <label for="end"><fmt:message key="form.booking.label.end"/></label>

            <div class="input-group date" data-provide="datepicker">
                <input type="text" name="end_date" id="end" class="form-control"
                       title="yyyy-MM-dd OR dd/MM/yyyy" pattern="\d{1,2}/\d{1,2}/\d{4}" required>

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
                <option>SINGLE</option>
                <option>DOUBLE</option>
                <option>TWIN</option>
                <option>LUX</option>
                <option>FAMILY</option>
            </select>
        </div>

    </div>

    <div class="button-group">
        <c:if test="${error != true}">
            <button type="submit" class="btn btn-primary"><fmt:message key="button.book"/></button>
        </c:if>
        <a class="btn btn-default" href="javascript:document.booking_list.submit()">
            <fmt:message key="button.cancel"/>
        </a>
    </div>
</form>

<c:import url="../scripts-import.jsp"/>
</body>
</html>