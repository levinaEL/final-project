<%--
  Created by IntelliJ IDEA.
  User: MY
  Date: 07.08.2016
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin page prototype</title>
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
                <a class="navbar-brand" href="admin-home-prot.jsp">Liza app</a>
            </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

                <ul class="nav navbar-nav">
                    <li>
                        <a href="booking-edit-form.jsp">
                            Booking
                        </a>
                    </li>
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
        <h3>Booking list</h3>

        <table class="table table-striped">
            <thead>
            <tr>
                <th>#</th>
                <th>Last Name</th>
                <th>First Name</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Quantity</th>
                <th>Room Type</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><a>1</a></td>
                <td>Ivanov</td>
                <td>Ivan</td>
                <td>12.08.2016</td>
                <td>19.08.2016</td>
                <td>1</td>
                <td>Single</td>
                <td>

                    <a href="#">
                        <span style="color: green" class="glyphicon glyphicon-ok"></span>
                    </a>
                    <a href="#">
                        <span style="color:red;" class="glyphicon glyphicon-remove left"></span>
                    </a>

                </td>
            </tr>
            <tr>
                <td><a>2</a></td>
                <td>Morozova</td>
                <td>Masha</td>
                <td>11.07.2016</td>
                <td>20.07.2016</td>
                <td>2</td>
                <td>Double</td>
                <td>
                    <a href="#">
                        <span style="color: green" class="glyphicon glyphicon-ok"></span>
                    </a>
                    <a href="#">
                        <span style="color:red;" class="glyphicon glyphicon-remove left"></span>
                    </a>

                </td>
            </tr>
            </tbody>
        </table>
    </div>
</main>
</body>
</html>