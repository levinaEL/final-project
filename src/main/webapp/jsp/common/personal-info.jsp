<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="text"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Personal information</title>
    <link href="../../app/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../app/assets/css/bootstrap-datepicker.min.css" rel="stylesheet" type="text/css">
    <link href="../../app/assets/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<header>
    <c:choose>
        <c:when test="${role==true}">
            <c:import url="../admin/admin-header.jsp"/>
        </c:when>
        <c:otherwise>
            <c:import url="../client/client-header.jsp"/>
        </c:otherwise>
    </c:choose>
</header>
<main>
    <h3 class="text-center text-capitalize"><fmt:message key="header.action.personalInfo"/></h3>

    <form class="form-horizontal border-forms" method="post" action="../../controller">

        <input type="hidden" name="command" value="create_client"/>
        <input type="hidden" name="client_id" value="${client.id}"/>
        <input type="hidden" name="is_banned" value="${client.ban}"/>

        <div class="form-group">
            <label for="fname" class="col-sm-2 control-label"><fmt:message key="client.info.firstName"/></label>

            <div class="col-sm-10">
                <input type="text" class="form-control" id="fname" name="first_name" value="${client.firstName}"
                       placeholder="First name" title="<fmt:message key="validation.title.name"/>"
                       required pattern="^[a-zA-Z'\s]+"/>
                <c:if test="${errName==true}">
                    <strong class="text-danger text-center"><fmt:message key="message.wrong.name"/></strong>
                </c:if>
            </div>
        </div>
        <div class="form-group">
            <label for="patronymic" class="col-sm-2 control-label"><fmt:message key="client.info.patronymic"/></label>

            <div class="col-sm-10">
                <input type="text" class="form-control" id="patronymic" name="patronymic_name"
                       value="${client.patronymicName}"
                       placeholder="Patronymic">
            </div>
        </div>
        <div class="form-group">
            <label for="lname" class="col-sm-2 control-label"><fmt:message key="client.info.lastName"/></label>

            <div class="col-sm-10">
                <input type="text" class="form-control" id="lname" name="last_name" value="${client.lastName}"
                       placeholder="Last name" title="<fmt:message key="validation.title.name"/>"
                       required pattern="^[a-zA-Z'\s]+">
                <c:if test="${errName==true}">
                    <strong class="text-danger text-center"><fmt:message key="message.wrong.name"/></strong>
                </c:if>
            </div>
        </div>

        <div class="form-group">
            <label for="birthday" class="col-sm-2 control-label"><fmt:message key="client.info.birthday"/></label>

            <div class="col-sm-10">
                <input type="date" class="form-control" id="birthday" value="${client.birthday}" name="birthday"
                       title="<fmt:message key="validation.title.dates"/>"
                       required pattern="(19|20)\d\d([- /.])(0[1-9]|1[012])\2(0[1-9]|[12][0-9]|3[01])">
                <c:if test="${errBirth==true}">
                    <strong class="text-danger text-center"><fmt:message key="message.wrong.birthday"/></strong>
                </c:if>
            </div>
        </div>

        <div class="form-group">
            <label class=" col-sm-2 control-label"><fmt:message key="client.info.passport.data"/></label>

            <div class="col-sm-10">
                <input type="text" class="form-control passport-series" id="pSeries" name="pasp_series"
                       value="${client.passportSeries}" placeholder="Series" required pattern="[A-Z]{1,4}">
                <input type="text" class="form-control passport-number" id="pNumber" name="pasp_number"
                       value="${client.passportNumber}" placeholder="Number" required
                       pattern="[\d]{6,14}">

                <input type="text" class="form-control passport-personal-number" id="pasp_prsl_number"
                       value="${client.personalNumber}" name="pasp_prsl_number" placeholder="Personal number"
                       required pattern="[A-Z0-9]{11,16}">
                <c:if test="${errPassport==true}">
                    <strong class="text-danger text-center"><fmt:message key="message.wrong.passport-info"/></strong>
                </c:if>
            </div>
        </div>

        <div class="form-group">
            <label for="address" class="col-sm-2 control-label"><fmt:message key="client.info.address"/></label>

            <div class="col-sm-10">
                <input type="text" class="form-control" id="address" name="address"
                       value="${client.address}" placeholder="Address">
            </div>
        </div>

        <div class="form-group">
            <label for="email" class="col-sm-2 control-label">Email</label>

            <div class="col-sm-10">
                <input type="email" class="form-control" id="email" name="email" value="${client.email}"
                       placeholder="Email" required>
                <c:if test="${errEmail==true}">
                    <strong class="text-danger text-center"><fmt:message key="message.wrong.email"/></strong>
                </c:if>
            </div>
        </div>

        <div class="form-group">
            <label for="phone" class="col-sm-2 control-label"><fmt:message key="client.info.phone"/></label>

            <div class="col-sm-10">
                <input type="text" class="form-control" id="phone" name="telephone" value="${client.phoneNumber}"
                       placeholder="Phone number"
                       pattern="\+(375)(\d){2}(\d){3}(-)?(\d){2}(-)?(\d){2}" title="+375(xx)...">
                <c:if test="${errPhone==true}">
                    <strong class="text-danger text-center"><fmt:message key="message.wrong.phone"/></strong>
                </c:if>
            </div>
        </div>

        <div class="button-group">
            <button type="submit" class="btn btn-primary"><fmt:message key="button.save"/></button>
            <a class="btn btn-default pull-right" href="javascript:document.booking_list.submit()"> <fmt:message
                    key="button.cancel"/></a>
        </div>
    </form>
</main>
<c:import url="../scripts-import.jsp"/>
</body>
</html>
