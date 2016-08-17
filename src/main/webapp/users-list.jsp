<%--
  Created by IntelliJ IDEA.
  User: MY
  Date: 09.08.2016
  Time: 23:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>Users list</title>
    <link href="app/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
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

    <%--<form name="booking" method="post" action="controller">--%>
    <%--<input type="hidden" name="command" value="booking"/>--%>
    <%--</form>--%>


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
<main>
    <div class="container-fluid">
        <h3>Users list</h3>
        <table class="table table-striped">
            <thead>
            <th>#</th>
            <th>Last Name</th>
            <th>First Name</th>
            <th>Email</th>
            <th>Address</th>
            <th>Phone Number</th>
            <th>Passport Series</th>
            <th>PassportNumber</th>
            <th>Personal Number</th>
            <th>Ban</th>
            <th>Action</th>
            </thead>
            <tbody>
            <c:forEach items="${clients}" var="client">

                <tr>
                    <td><a href="controller?command=get_client&id=${client.id}">${client.id}</a></td>
                    <td><c:out value="${client.lastName}"/></td>
                    <td><c:out value="${client.firstName}"/></td>
                    <td><c:out value="${client.email}"/></td>
                    <td><c:out value="${client.address}"/></td>
                    <td><c:out value="${client.phoneNumber}"/></td>
                    <td><c:out value="${client.passportSeries}"/></td>
                    <td><c:out value="${client.passportNumber}"/></td>
                    <td><c:out value="${client.personalNumber}"/></td>
                    <td>
                        <c:if test="${client.ban == true}">
                            <span class="glyphicon glyphicon-ok-circle"></span>
                        </c:if>
                    </td>
                    <td>
                        <%--<form name="booking" method="post" action="controller">--%>
                            <%--<input type="hidden" name="command" value="booking">--%>
                            <%--<input type="hidden" name="id" value="${client.id}">--%>
                        <%--</form>--%>

                        <a href="controller?command=get_request&id=${client.id}" title="booking">
                            <span class="glyphicon glyphicon-pencil"></span>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</main>
</body>

</html>
