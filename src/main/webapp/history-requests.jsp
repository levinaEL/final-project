<%--
  Created by IntelliJ IDEA.
  User: MY
  Date: 20.08.2016
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>History Of Booking</title>
    <link href="app/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="app/assets/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<header>
    <form name="clients" method=post action=controller>
        <input type="hidden" name="command" value="clients_list"/>
    </form>
    <form name="requests" method=post action=controller>
        <input type="hidden" name="command" value="HISTORY_REQUESTS"/>
    </form>
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
                <a class="navbar-brand" href="controller?command=booking_list">Liza app</a>
            </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

                <ul class="nav navbar-nav">
                    <li>
                        <a href="javascript:document.clients.submit()">
                            Clients List
                        </a>
                    </li>
                    <li>
                        <a href="javascript:document.requests.submit()">
                            History Of Booking
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
<main>
    <div class="container-fluid">
        <h3>History Of Booking</h3>

        <table class="table table-striped">
            <thead>
            <tr>
                <th>#</th>
                <th>Last Name</th>
                <th>Time Of Booking</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Quantity</th>
                <th>Room Type</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requests}" var="request" varStatus="loop">
                <tr>
                    <td><c:out value="${loop.index}"/></td>
                    <td>${clientsName[loop.index]}</td>
                    <td><c:out value="${request.requestDate}"/></td>
                    <td><c:out value="${request.startDate}"/></td>
                    <td><c:out value="${request.endDate}"/></td>
                    <td><c:out value="${request.personsCount}"/></td>
                    <td><c:out value="${request.roomType}"/></td>
                    <td><c:out value="${request.statusRequest}"/></td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</main>

</body>
</html>
