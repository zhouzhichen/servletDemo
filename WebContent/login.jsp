<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 此处相当于静态代码块,自动执行 -->
	<!--实现七天免登录
	1.可以在登录时判断是否免登陆,如有则把姓名和密码转成一个字符串,封装进一个cookie中
	2.再在登录页面得到cookie且取出姓名和密码,拿到servlet进行判断是否本用户存在
	3.存在跳转到home.jsp,否则跳转到登录页面但是把request.getAttribute("daydayup")赋值,那么就不会进入 一下代码块
	4.request是请求,一般跳转页面为一次新的请求,但是如果是转发(request.getRequestDispatcher),则可以看做是同一个请求 -->
	<%
		if (request.getAttribute("daydayup") == null) {
			//获取请求(request)中的所有cookie，得到cookies数组  
			Cookie[] cookies = request.getCookies();
			//如果cookies数组不为null，并且它的长度大于0  
			if (cookies != null && cookies.length > 0) {
				//就循环遍历每一条cookie  
				for (Cookie cookie : cookies) {
					//如果cookie的名称为session  
					if ("session".equals(cookie.getName())) {
						//则读取这个cookie的值，再得到sessionid  
						//然后再根据sessionid，获取储存在应用域中的session对象  ,因为在application中或许存在许多session,所以用sessionId得到session更准确
						//强转后(因为是用的application对象)，赋值给当前session对象，因为jsp中session对象已经被自动创建，所以这里只需要赋值  
						session = (HttpSession) application.getAttribute(cookie.getValue());
						request.setAttribute("id", session.getAttribute("id"));
						request.setAttribute("name", session.getAttribute("name"));
						request.getRequestDispatcher("Servlet?method=freeload").forward(request, response);

					}
				}
			}
		}
	%>
	<form action="Servlet?a=1" method="post">
		<input type="hidden" name="method" value="login"> id：<input
			type="text" name="id"> 姓名：<input type="text" name="name">
		<br> <input type="checkbox" name="free" value="1">十天免登陆 <input
			type="submit" value="提交">
	</form>
</body>

</html>