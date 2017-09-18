<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="Servlet?a=1" method="post">
	<input type="hidden" name="method" value="login">
	<!-- <input type="hidden" name="a" value="1"> -->
	id：<input type="text" name="id">
	姓名：<input type="text" name="name">
	<br>
	<input type="checkbox" name="free" >十天免登陆
	<input type="submit" value="提交">
</form>
</body>

</html>