<%--
  Created by IntelliJ IDEA.
  User: MY
  Date: 07.08.2016
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <label for="login" class="col-sm-3 control-label">Login</label>

        <div class="col-sm-9">
            <input class="form-control"
                   type="text" id="login" name="login" placeholder="Login" required
                   pattern="\w+"
                   title="Username must contain only letters, numbers and underscores">
        </div>
    </div>
    <div class="form-group">
        <label for="password" class="col-sm-3 control-label">Password</label>

        <div class="col-sm-9">
            <input class="form-control"
                   type="password" id="password" name="password" placeholder="Password" required
                   pattern="(?=.*\d)(?=.*[a-z]).{6,}"
                   title="Password must contain at least 6 characters, including UPPER/lower case and numbers">
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-9 col-sm-offset-3">
            <button type="submit" class="btn btn-primary pull-left modal-background-color">
                Register
            </button>
            <a class="btn btn-default pull-right" href="login.jsp">Cancel</a>
        </div>
    </div>
</form>

</body>
</html>