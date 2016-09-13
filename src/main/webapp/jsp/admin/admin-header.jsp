<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: MY
  Date: 29.08.2016
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>admin header</title>
</head>
<body>
<form name="clients" action="../../controller">
    <input type="hidden" name="command" value="clients_list"/>
</form>
<form name="logout" method=post action="../../controller">
    <input type="hidden" name="command" value="logout"/>
</form>
<form name="booking" method=post action="../../controller">
    <input type="hidden" name="command" value="booking"/>
</form>
<form name="requests" action="../../controller">
    <input type="hidden" name="command" value="history_requests"/>
</form>
<form name="booking_list" method=post action="../../controller">
    <input type="hidden" name="command" value="booking_list"/>
</form>
<form name="get_request" method=post action="../../controller">
    <input type="hidden" name="book" value="create_book"/>
    <input type="hidden" name="clientId" value="${client.id}"/>
    <input type="hidden" name="command" value="get_request"/>
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
            <a class="navbar-brand" href="javascript:document.booking_list.submit()">Liza app</a>

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
                <li><a class="new-user" href="jsp/common/personal-info.jsp">New Client</a></li>

                <li>
                    <a href="javascript:document.logout.submit()">
                        Logout
                    </a>
                </li>
                <li class="dropdown language-selector">

                    <c:import url="../common/language-picker.jsp"/>
                </li>
            </ul>
        </div>
    </div>
</nav>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../../app/assets/js/bootstrap.min.js"></script>
<script src="../../app/assets/js/bootstrap-datepicker.min.js"></script>

</body>
</html>
