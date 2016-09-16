<%@ page contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="text"/>

<form name="logout" method=post action="../../controller">
    <input type="hidden" name="command" value="logout"/>
</form>

<form id="booking_list" name="booking_list" method=post action="../../controller">
    <input type="hidden" name="command" value="booking_list"/>
</form>

<form name="get_client" method="get" action="../../controller">
    <input type="hidden" name="command" value="get_client"/>
    <input type="hidden" name="client_id" value=${client.id}>
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
            <form method="post" name="langForm" action="../../controller?command=CHANGE_LANGUAGE">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="javascript:document.get_client.submit()">
                            <fmt:message key="header.action.personalInfo"/>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:document.logout.submit()">
                            <fmt:message key="header.action.logout"/>
                        </a>
                    </li>

                    <li class="dropdown language-selector">
                        <input type="hidden" name="language" value="${language}" data-lang-target/>
                        <a href="javascript:"
                           class="dropdown-toggle"
                           data-toggle="dropdown"
                           data-close-others="true">
                            <c:if test="${language == \"ru\"}">
                                <img src="../../app/assets/images/russian.png" alt=" "/>
                            </c:if>
                            <c:if test="${language == \"en\"}">
                                <img src="../../app/assets/images/english.png" alt=" "/>
                            </c:if>
                            <span><fmt:message key="language.key"/></span>
                        </a>

                        <ul class="dropdown-menu pull-right">
                            <li>
                                <a href="javascript:" data-lang="ru">
                                    <img src="../../app/assets/images/russian.png" alt=" "/>
                                    <span>Русский</span>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:" data-lang="en">
                                    <img src="../../app/assets/images/english.png" alt=" "/>
                                    <span>English</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </form>
        </div>
    </div>
</nav>
