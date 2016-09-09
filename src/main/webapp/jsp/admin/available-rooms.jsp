<%--
  Created by IntelliJ IDEA.
  User: MY
  Date: 20.08.2016
  Time: 13:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Available Rooms</title>
    <link href="../../app/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../app/assets/css/bootstrap-datepicker.min.css" rel="stylesheet" type="text/css">
    <link href="../../app/assets/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="container-fluid">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th>Type Room</th>
            <th>Number Seats</th>
            <th>Cost</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${roomNotFound==true}">
            <div class="alert alert-danger">
                <strong>Danger!</strong> No available rooms
            </div>
            <c:set var="error" value="true"/>
        </c:if>
        <c:forEach items="${rooms}" var="room">
            <tr>
                <td><a>${room.roomID}</a></td>
                <td><c:out value="${room.roomType}"/></td>
                <td><c:out value="${room.numberSeats}"/></td>
                <td><c:out value="${room.cost}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
