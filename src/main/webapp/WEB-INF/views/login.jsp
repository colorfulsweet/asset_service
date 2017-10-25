<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<link rel="stylesheet" type="text/css" href="/resources/lib/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="/resources/css/normalize.min.css">
<link rel="stylesheet" type="text/css" href="/resources/css/login.css">
</head>
<body>

<form class="login" action="/views/login" method="post">
	<p class="title">用户登录</p>
	<input type="text" placeholder="用户名" name="user" value="${username}"/>
	<i class="fa fa-user"></i>
	<input type="password" placeholder="密码" name="password" />
	<i class="fa fa-key"></i>
	<button type="submit">
		<span class="state">登录</span>
	</button>
	<span class="msg">${msg}</span>
</form>

</body>
</html>