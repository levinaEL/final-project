<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="text"/>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>Registration</title>
    <link href="../../app/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../app/assets/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<form action="../../controller" class="form-horizontal" method="post">
    <input type="hidden" name="command" value="register"/>

    <div class="form-group">
        <label for="login" class="col-sm-3 control-label"><fmt:message key="login.label"/></label>

        <div class="col-sm-9">
            <input class="form-control"
                   type="text" id="login" name="user_login" placeholder="Login" required
                   pattern="\w+"
                   title="Username must contain only letters, numbers and underscores">
            <c:if test="${errLogin==true}">
                <strong class="text-danger text-center"><fmt:message key="message.wrong.login"/></strong>
            </c:if>
        </div>
    </div>
    <div class="form-group">
        <label for="password" class="col-sm-3 control-label"><fmt:message key="password.label"/></label>

        <div class="col-sm-9">
            <input class="form-control"
                   type="password" id="password" name="user_password" placeholder="Password" required
                   pattern="(?=.*\d)(?=.*[a-z]).{6,}"
                   title="Password must contain at least 6 characters, including UPPER/lower case and numbers">
            <c:if test="${errPass==true}">
                <strong class="text-danger text-center"> <fmt:message key="message.wrong.password"/></strong>
            </c:if>
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-9 col-sm-offset-3">

            <button type="submit" class="btn btn-primary pull-left modal-background-color">
                <fmt:message key="button.registration"/>
            </button>
            <a class="btn btn-default pull-right" href="login.jsp"> <fmt:message key="button.cancel"/></a>
        </div>
    </div>
</form>
<c:import url="../scripts-import.jsp"/>
</body>
</html>