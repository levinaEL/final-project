<%@ page contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags/custom-tag.tld" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="text"/>
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
        <h3><fmt:message key="header.action.clientsList"/></h3>

        <table class="table table-striped">
            <thead>
            <tr>
                <th>#</th>
                <th><fmt:message key="client.info.lastName"/></th>
                <th><fmt:message key="client.info.firstName"/></th>
                <th>Email</th>
                <th><fmt:message key="client.info.address"/></th>
                <th><fmt:message key="client.info.phone"/></th>
                <th><fmt:message key="client.info.passport.series"/></th>
                <th><fmt:message key="client.info.passport.number"/></th>
                <th><fmt:message key="client.info.count"/></th>
                <th align="center"><fmt:message key="action"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${clients}" var="client">

                <tr>
                    <td><span>
                        <a href="controller?command=get_client&client_id=${client.id}">${client.id}</a>
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
                        <a href="/jsp/admin/admin-booking.jsp?client_id=${client.id}" class="btn-link"
                           title="<fmt:message key="button.book"/>">
                            <span class="glyphicon glyphicon-pencil"></span>
                        </a>

                        <form class="pull-left" name="ban" method=post action="../../controller">
                            <input type="hidden" name="command" value="ban"/>
                            <input type="hidden" name="client_id" value="${client.id}"/>
                            <button type="submit" class="btn-link" title="<fmt:message key="action.ban"/>">
                                <span class="glyphicon glyphicon-ban-circle"></span>
                            </button>
                        </form>
                        <a class="btn-link pull-right" title="<fmt:message key="action.sale"/>"
                           href="javascript:document.clients.submit()"
                           onclick="${fn:add(clientsForSale, client.id)}" data-toggle="modal" data-target="#saleModal">
                            <span class="glyphicon glyphicon-piggy-bank"></span>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <form class="pull-left" name="clients_list" method=post action="../../controller">

            <input type="hidden" name="command" value="clients_list"/>
            <%--<input type="hidden" name="isSort" value="sort">--%>

                <input type="submit" class="btn btn-primary btn-xs" name="sort" value="Sort By Name">

                <input type="submit" class="btn btn-primary btn-xs" name="sort" value="Revert">

        </form>
    </div>
</main>

<!-- Modal Sale-->
<div class="modal fade" id="saleModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header modal-background-color">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><fmt:message key="message.title"/></h4>
            </div>
            <div class="modal-body">
                <p><fmt:message key="message.info.sale"/></p>
            </div>
        </div>
    </div>
</div>

<!-- Modal Ban-->
<div class="modal fade" id="banModal" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header modal-background-color">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><fmt:message key="message.title"/></h4>
            </div>
            <div class="modal-body">
                <p><fmt:message key="message.info.ban"/></p>
            </div>
        </div>
    </div>
</div>

</body>

<c:import url="../scripts-import.jsp"/>
</html>
