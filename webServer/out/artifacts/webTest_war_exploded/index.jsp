<%--
  Created by IntelliJ IDEA.
  User: cai
  Date: 2020/11/2
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>数据库操作</title>
    <script type="text/javascript">
      function checkInsertPerson(){
        return window.confirm("你确定要插入这一条Person数据吗？");
      }
      function checkDeleteUser(){
        return window.confirm("你确定要删除这一条User数据吗？");
      }
    </script>
    <style>
      table,td,th
      {
        border:1px solid black;
        margin: 0 auto;
      }
      table
      {
        width:350px;
      }
      th
      {
        height:40px;
      }
    </style>
  </head>
  <body>
  <div style="text-align: center;font-size: 22px">数据库表person插入信息</div> <br/>

  <form action="AddPersonServlet" method="post" onsubmit="return checkInsertPerson()">
    <div style="text-align: center">
      <table>
        <tr>
          <th> <label>username: <input type="text" name="username"></label> </th>
        </tr>
        <tr>
          <th> <label>name: <input type="text" name="name"></label> </th>
        </tr>
        <tr>
          <th> <label>age: <input type="text" name="age"></label> </th>
        </tr>
        <tr>
          <th> <label>telenum: <input type="text" name="telenum"></label> </th>
        </tr>
      </table>
      <br/>
      <input type="submit" value="插入">
    </div>
  </form>

  <div style="text-align: center;font-size: 22px">数据库表users删除信息</div> <br/>
  <form action="DeleteUserServlet" method="post" onsubmit="return checkDeleteUser()">
    <div style="text-align: center">
      <table>
        <tr>
          <th> <label>username: <input type="text" name="username"></label> </th>
        </tr>
      </table>
      <br/>
      <input type="submit" value="删除">
    </div>
  </form>
  <%--
  <form action="login" method="post">
    账号: <input type="text" name="name"> <br/>
    密码: <input type="password" name="password"> <br/>
    <input type="submit" value="登录">
  </form>
  --%>
  </body>
</html>
