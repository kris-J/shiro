<%--
  Created by IntelliJ IDEA.
  User: fangjie
  Date: 2018/6/13
  Time: 9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <form action="<%=request.getContextPath()%>/register/toRegister" method="post">
        用户名:<input type="text" name="username"/><br>
        密码:<input type="password" name="password"/><br>
        <input type="submit" value="提交"/>
    </form>
</body>
</html>
