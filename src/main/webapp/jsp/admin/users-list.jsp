<%--
  Created by IntelliJ IDEA.
  User: MY
  Date: 09.08.2016
  Time: 23:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags/custom-tag.tld" prefix="fn" %>
<%@ page isELIgnored="false" %>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>Users list</title>
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
                        <a class="disabled" href="controller?command=clients_list&page=${currentPage - 1}"
                           aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li><a class="active">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="controller?command=clients_list&page=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${currentPage lt noOfPages}">
                    <li>

                        <a href="controller?command=clients_list&page=${currentPage + 1}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>

    <div class="container-fluid">
        <h3>Clients List</h3>

        <table class="table table-striped">
            <thead>
            <tr>
                <th>#</th>
                <th>Last Name</th>
                <th>First Name</th>
                <th>Email</th>
                <th>Address</th>
                <th>Phone Number</th>
                <th>Passport Series</th>
                <th>PassportNumber</th>
                <th>Count Requests</th>
                <th align="center">Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${clients}" var="client">

                <tr>
                    <td><span>
                        <a href="controller?command=get_client&id=${client.id}">${client.id}</a>
                         <c:if test="${client.ban == true}">
                             <span class="text-danger">BAN!</span>
                         </c:if>
                    </span></td>
                    <td><c:out value="${client.lastName}"/></td>
                    <td><c:out value="${client.firstName}"/></td>
                    <td><c:out value="${client.email}"/></td>
                    <td><c:out value="${client.address}"/></td>
                    <td><c:out value="${client.phoneNumber}"/></td>
                    <td><c:out value="${client.passportSeries}"/></td>
                    <td><c:out value="${client.passportNumber}"/></td>
                    <td><c:out value="${clientCountMap[client.id]}"/>
                    </td>
                    <td>
                        <form class="pull-left" name="ban" method=post action="../../controller">
                            <input type="hidden" name="command" value="ban"/>
                            <input type="hidden" name="id" value="${client.id}"/>
                            <button type="submit" class="btn-link" title="BAN">
                                <span class="glyphicon glyphicon-ban-circle"></span>
                            </button>
                        </form>

                        <form class="pull-right" name="booking" action="../../controller">
                            <input type="hidden" name="command" value="get_request">
                            <input type="hidden" name="book" value="create_book">
                            <input type="hidden" name="clientId" value="${client.id}">
                            <button type="submit" class="btn-link" title="Booking">
                                <span class="glyphicon glyphicon-pencil"></span>
                            </button>
                        </form>
                        <a class="btn-link pull-right" title="Make Sale" href="javascript:document.clients.submit()"
                           onclick="${fn:add(clientsForSale, client.id)}" data-toggle="modal" data-target="#saleModal">
                            <span class="glyphicon glyphicon-piggy-bank"></span>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="saleModal" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header modal-background-color">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Information</h4>
                </div>
                <div class="modal-body">
                    <p>Client get the sale!</p>
                </div>
            </div>

        </div>
    </div>

</main>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>
