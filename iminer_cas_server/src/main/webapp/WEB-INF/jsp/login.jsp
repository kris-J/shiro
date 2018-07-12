<%--
  Created by IntelliJ IDEA.
  User: fangjie
  Date: 2018/6/14
  Time: 9:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
    ${error}
    <form action="" method="post">
        用户名：<input type="text" name="username"><br/>
        密码：<input type="password" name="password"><br/>
        <input type="submit" value="登录">
    </form>
    <a href="<%=request.getContextPath()%>/register/index">去注册</a>
</body>
</html>
