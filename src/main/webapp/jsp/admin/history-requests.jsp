<%--
  Created by IntelliJ IDEA.
  User: MY
  Date: 20.08.2016
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags/custom-tag.tld" prefix="fn" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>History Of Booking</title>
    <link href="../../app/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="../../app/assets/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<header>
    <c:import url="admin-header.jsp"/>
</header>
<main>
    <div class="pull-right pagination-position">
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${currentPage != 1}">
                    <li>
                        <a href="controller?command=history_requests&page=${currentPage - 1}" aria-label="Previous">
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
                            <li><a href="controller?command=history_requests&page=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${currentPage lt noOfPages}">
                    <li>
                        <a href="controller?command=history_requests&page=${currentPage + 1}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
    <div class="container-fluid">
        <h3>History Of Booking</h3>

        <table class="table table-striped">
            <thead>
            <tr>
                <th>#</th>
                <th>Last Name</th>
                <th>Time Of Booking</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Quantity</th>
                <th>Room Type</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requests}" var="request" varStatus="loop">
                <tr>

                    <td><c:out value="${(currentPage-1)*recordsPerPage + (loop.index+1)}"/></td>
                    <td>${clientsName[loop.index]}</td>
                    <td><c:out value="${request.requestDate}"/></td>
                    <td><c:out value="${request.startDate}"/></td>
                    <td><c:out value="${request.endDate}"/></td>
                    <td><c:out value="${request.personsCount}"/></td>
                    <td><c:out value="${request.roomType}"/></td>
                    <td><c:out value="${request.statusRequest}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</main>
</body>
</html>
