<%--
  Created by IntelliJ IDEA.
  User: MY
  Date: 06.08.2016
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Client page prototype</title>
    <link href="app/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="app/assets/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<header>
    <form name="logout" method=post action=controller>
        <input type="hidden" name="command" value="logout"/>
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
                <a class="navbar-brand" href="client-home-prot.jsp">Liza app</a>
            </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="booking-edit-form.jsp">
                            Booking
                        </a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="PersonalInfo?id=${client.id}">Personal information</a></li>
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
<main class="client-booking">
    <h3>Booking list</h3>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Quantity</th>
            <th>Room Type</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requests}" var="request">
            <tr>
                <td><c:out value="${request.requestID}"/></td>
                <td><c:out value="${request.startDate}"/></td>
                <td><c:out value="${request.endDate}"/></td>
                <td><c:out value="${request.personsCount}"/></td>
                <td>Single</td>
                <td>

                    <a href="#">
                        <span class="glyphicon glyphicon-trash"></span>
                    </a>

                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</main>
</body>
</html>
