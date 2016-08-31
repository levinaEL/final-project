<%--
  Created by IntelliJ IDEA.
  User: MY
  Date: 06.08.2016
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags/custom-tag.tld" prefix="fn" %>
<%@ page isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Client page prototype</title>
    <link href="../../app/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../app/assets/css/style.css" rel="stylesheet" type="text/css">

</head>
<body>
<header>
    <c:import url="client-header.jsp"/>
</header>
<main class="client-booking">
    <div class="pull-right pagination-position">
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${currentPage != 1}">
                    <li>
                        <a href="controller?command=booking_list&page=${currentPage - 1}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li><a>${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="controller?command=booking_list&page=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${currentPage lt noOfPages}">
                    <li>

                        <a href="controller?command=booking_list&page=${currentPage + 1}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
    <h3>Booking list</h3>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Quantity</th>
            <th>Room Type</th>
            <th>Status</th>
            <th>Cost $</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requests}" var="request" varStatus="loop">
            <tr>
                <td><c:out value="${loop.index + 1}"/></td>
                <td><c:out value="${request.startDate}"/></td>
                <td><c:out value="${request.endDate}"/></td>
                <td><c:out value="${request.personsCount}"/></td>
                <td><c:out value="${request.roomType}"/></td>
                <td><c:out value="${request.statusRequest}"/></td>
                <td>
                    <c:out value="${cost[loop.index]}"/>
                    <c:if test="${fn:contains(clientsForSale, clientId) and  not empty cost[loop.index]}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            You get the sale <c:out value="${0.3*cost[loop.index]}"/>
                        </div>
                    </c:if>
                </td>
                <td>
                    <form name="booking" action="../../controller">
                        <input type="hidden" name="command" value="cancel_request">
                        <input type="hidden" name="id" value="${request.requestID}">
                        <button type="submit" class="btn-link" title="cancel">
                            <span class="glyphicon glyphicon-trash"></span>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>
</body>
</html>