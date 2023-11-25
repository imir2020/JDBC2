<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 24.11.2023
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Перелёты</title>
</head>
<body>
<%@include file="header.jsp"%>
<ul>
    <c:if test="${not empty requestScope.flights}">
        <c:forEach var="flight" items="${requestScope.flights}">
            <li><a href="${pageContext.request.contextPath}/tickets?flightId=${flight.id()}">${flight.description()}</a> </li>
        </c:forEach>

    </c:if>
</ul>
</body>
</html>
