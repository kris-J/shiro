<%--
  Created by IntelliJ IDEA.
  User: fangjie
  Date: 2018/6/12
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录成功</title>
</head>
<body>

欢迎${subject.principal}登录成功！<a href="${pageContext.request.contextPath}/logout">退出</a>
</body>
</html>
