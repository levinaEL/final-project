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
            <th><fmt:message key="request.info.type"/></th>
            <th><fmt:message key="request.info.quantity"/></th>
            <th><fmt:message key="request.info.cost"/></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${roomNotFound==true}">
            <div class="alert alert-danger">
                <fmt:message key="message.noroom"/>
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
<c:import url="../scripts-import.jsp"/>
</body>
</html>

