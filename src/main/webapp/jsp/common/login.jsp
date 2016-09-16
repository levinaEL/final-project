<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title>Login</title>
    <link href="../../app/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../app/assets/css/style.css" rel="stylesheet" type="text/css">
</head>
<body class="login-background">
<main class="container">
    <h1>HOSTEL BOOKING</h1>

    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h2 class="text-center login-header">Sign in to continue</h2>

            <div class="border-forms">

                <form method="post" action="../../controller" class="form-signin">

                    <c:if test="${userNotFound==true}">
                        <div class="alert alert-danger" role="alert">User not found!</div>
                    </c:if>
                    <c:if test="${passwordIsNotCorrect==true}">
                        <div class="alert alert-danger" role="alert">Password isn't correct!</div>
                    </c:if>

                    <input type="hidden" name="command" value="login"/>

                    <input name="user_login" type="text" class="form-control" placeholder="Login" required autofocus/>
                    <input name="user_password" type="password" class="form-control" placeholder="Password" required/>

                    <button class="btn btn-lg btn-primary btn-block" type="submit">
                        Sign in
                    </button>
                </form>

            </div>

            <a href="#registrationModal" data-toggle="modal" class="text-center new-account">Create an account </a>
        </div>
    </div>
</main>
<div class="modal fade" id="registrationModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">

    <div class="modal-content registration-content">
        <div class="modal-header modal-background-color">
            <button type="button" class="close" data-dismiss="modal">&times;</button>
            <h4 class="modal-title text-center">REGISTRATION</h4>
        </div>
        <div class="modal-body">
          <c:import url="registration.jsp"/>
        </div>
    </div>
</div>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<c:import url="../scripts-import.jsp"/>
</body>
</html>

