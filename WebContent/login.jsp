<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		//获取请求中的所有cookie，得到cookies数组  
		Cookie[] cookies = request.getCookies();
		//如果cookies数组不为null，并且它的长度大于0  
		if (cookies != null && cookies.length > 0) {
			//就循环遍历每一条cookie  
			for (Cookie cookie : cookies) {
				//如果cookie的名称为session  
				if ("session".equals(cookie.getName())) {
					//则读取这个cookie的值，再得到sessionid  
					//然后再根据sessionid，获取储存在应用域中的session对象  
					//强转后(因为是用的application对象)，赋值给当前session对象，因为jsp中session对象已经被自动创建，所以这里只需要赋值  
					session = (HttpSession) application.getAttribute(cookie.getValue());
					request.getRequestDispatcher("home.jsp").forward(request, response);
				}
			}
		}
	%>
	<form action="Servlet?a=1" method="post">
		<input type="hidden" name="method" value="login"> 
		id：<input type="text" name="id"> 姓名：<input type="text" name="name">
		<br> 
		<input type="checkbox" name="free" value="1">十天免登陆 <input type="submit" value="提交">
	</form>
</body>

</html>