<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
  Created by IntelliJ IDEA.
  User: cai
  Date: 2020/11/22
  Time: 21:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>数据库查询</title>
    <style>
      table,td,th
      {
        border:1px solid black;
        margin: 0 auto;
      }
      table
      {
        width:400px;
      }
      th
      {
        height:40px;
      }
    </style>
  </head>
  <body>
    <div style="text-align: center;font-size: 22px">数据库表users</div> <br/>
    <div style="text-align: center">
      <table>
        <tr>
          <th>username</th>
          <th>pass</th>
        </tr>
        <c:forEach var="user" items="${userList}">
          <tr>
            <td>${user.getUsername()}</td>
            <td>${user.getPassword()}</td>
          </tr>
        </c:forEach>
      </table>
    </div>
    <br/>
    <div style="text-align: center;font-size: 22px">数据库表person</div> <br/>
    <div style="text-align: center">
      <table>
        <tr>
          <th>username</th>
          <th>name</th>
          <th>age</th>
          <th>telenum</th>
        </tr>
        <c:forEach var="person" items="${personList}">
          <tr>
            <td>${person.getUsername()}</td>
            <td>${person.getName()}</td>
            <td>${person.getAgeStr()}</td>
            <td>${person.getTeleno()}</td>
          </tr>
        </c:forEach>
      </table>
    </div>
    <div style="text-align: center">
      <a href="index.jsp">返回数据库操作页面</a>
    </div>
    <br/>
  </body>
</html>
