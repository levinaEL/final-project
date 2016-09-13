<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: MY
  Date: 29.08.2016
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title>client header</title>
</head>
<body>
<form name="logout" method=post action="../../controller">
    <input type="hidden" name="command" value="logout"/>
</form>

<form id="booking_list" name="booking_list" method=post action="../../controller">
    <input type="hidden" name="command" value="booking_list"/>
</form>

<form name="get_client" method="get" action="../../controller">
    <input type="hidden" name="command" value="get_client"/>
    <input type="hidden" name="clientId" value=${client.id}>
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
                    <a href="controller?command=get_request">
                        <fmt:message key="header.action.booking"/>
                    </a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="javascript:document.get_client.submit()">
                        <fmt:message key="header.action.personal.info"/>
                    </a>
                </li>
                <li>
                    <a href="javascript:document.logout.submit()">
                        <fmt:message key="header.action.logout"/>
                    </a>
                </li>

                <li class="dropdown language-selector">

                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-close-others="true">
                        <img src="../../app/assets/images/united_states_of_america.png"/>
                        <span>English</span>
                    </a>

                    <ul class="dropdown-menu pull-right">
                        <li>
                            <a href="#">
                                <img src="../../app/assets/images/icon_flag_russian.gif"/>
                                <span>Russian</span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <img src="../../app/assets/images/united_states_of_america.png"/>
                                <span>English</span>
                            </a>
                        </li>
                    </ul>
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
