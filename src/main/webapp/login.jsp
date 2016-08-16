<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Login</title>
    <link href="app/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="app/assets/css/style.css" rel="stylesheet" type="text/css">
</head>
<body class="login-background">
<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h2 class="text-center login-h2">Sign in to continue</h2>

            <div class="border-forms">

                <form method="post" action="controller" class="form-signin">

                    <c:if test="${userNotFound==true}">
                        <div class="alert alert-danger" role="alert">User not found!</div>
                    </c:if>
                    <c:if test="${passwordIsNotCorrect==true}">
                        <div class="alert alert-danger" role="alert">Password isn't correct!</div>
                    </c:if>

                    <input type="hidden" name="command" value="login" />

                    <input name="u_login" type="text" class="form-control" placeholder="Login" required autofocus />
                    <input name="u_password" type="password" class="form-control" placeholder="Password" required />

                    <button class="btn btn-lg btn-primary btn-block" type="submit">
                        Sign in
                    </button>
                </form>

            </div>
            <a href="registration.jsp" class="text-center new-account">Create an account </a>
        </div>
    </div>
</div>

</body>
</html>

