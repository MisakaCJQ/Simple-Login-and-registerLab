<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
  Created by IntelliJ IDEA.
  User: cai
  Date: 2020/11/22
  Time: 21:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>数据库操作结果</title>
</head>
<body>
    <div style="text-align: center;font-size: 22px">数据库操作结果</div> <br/>
    <c:choose>
        <c:when test="${type==0}">
            <div style="text-align: center;font-size: 22px">person ${username}插入成功</div>
        </c:when>
        <c:when test="${type==1}">
            <div style="text-align: center;font-size: 22px">person ${username}更新成功</div>
        </c:when>
        <c:when test="${type==2}">
            <div style="text-align: center;font-size: 22px">user ${username}删除成功</div>
        </c:when>
    </c:choose>
    <br/>

    <form action="ListTableServlet">
        <div style="text-align: center">
            <input type="submit" value="查看数据库">
        </div>
    </form>
    <br/>
    <div style="text-align: center">
        <a href="index.jsp">返回数据库操作页面</a>
    </div>
</body>
</html>
