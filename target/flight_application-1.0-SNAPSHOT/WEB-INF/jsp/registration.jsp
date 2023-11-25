<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 24.11.2023
  Time: 14:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/registration" method="post">
    <label for="name">Name:
        <input type="text" name="name" id="name">
    </label><br/>
    <label for="birthday">Birthday:
        <input type="date" name="birthday" id="birthday">
    </label><br/>
    <label for="email">Email:
        <input type="text" name="email" id="email">
    </label><br/>
    <label for="pwd">Password:
        <input type="password" name="pwd" id="pwd">
    </label><br/>
    <select name="role" id="role">
<%--        <option label="USER">USER</option>--%>
<%--        <option label="ADMIN">ADMIN</option>--%>
    <c:forEach var="role" items="${requestScope.roles}">
        <option label="${role}">${role}</option>
        <br>
    </c:forEach>
    </select><br/>
<%--    <input type="radio" name="gender" VALUE="MALE">MALE--%>
<%--    <br/>--%>
<%--    <input type="radio" name="gender" value="FEMALE">FEMALE--%>
<%--    <br/>--%>
<%--    <input type="submit" name="Send">--%>
        <c:forEach var="gender" items="${requestScope.genders}">
            <input type="radio" name="gender" VALUE="${gender}"> ${gender}
            <br/>
        </c:forEach>
        <input type="submit" value="Send">
</form>
<c:if test="${not empty requestScope.errors}">
    <div style="color: red">
        <c:forEach var="error" items="${requestScope.errors}">
            <span>${error.message}</span>
            <br>
        </c:forEach>
    </div>
</c:if>
</body>
</html>