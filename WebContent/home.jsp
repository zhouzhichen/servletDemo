<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			for (Cookie cookie  : cookies) {
				//如果cookie的名称为session  
				if ("session".equals(cookie.getName())) {
					//则读取这个cookie的值，再得到sessionid  
					//然后再根据sessionid，获取储存在应用域中的session对象  
					//强转后，赋值给当前session对象，因为jsp中session对象已经被自动创建，所以这里只需要赋值  
					session = (HttpSession) application.getAttribute(cookie.getValue());
				}
			}
		}
	%>
	<form action="Servlet" method="get">
		<input type="hidden" name="method" value="search"> 查询<input
			type="text" name="find"> <input type="submit" value="提交">
	</form>

	<table border="1px" cellpadding="0px" cellspacing="0px" height="50"
		width="500" align="center">
		<tr>
			<td>id</td>
			<td>姓名</td>
		</tr>
		<c:forEach var="u" items="${li }">
			<tr>

				<td><input type="checkbox" name="choose" value="${u.id }">${u.id }</td>
				<td>${u.name }<a href="Servlet?method=delete&id=${u.id }">删除</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<br> 请输入文件名
	<input type="text" id="files">
	<!-- 只有表单和超链接可以把参数传到request里面，servlet才可以得到参数
									所以这里在js里面用对象得到相应参数，在location传参 -->
	<input type="button" onclick="choose()" name="buttons">
	<a href="Insert.jsp">添加</a>
	<a href="Servlet?method=previous">上一页</a>
	<a href="Servlet?method=next">下一页</a>
	<jsp:useBean id="pagee" class="pageDao.PageDao"></jsp:useBean>
	${a }/<%=pagee.totalPage()%>
	<form action="Servlet?method=upload" method="post"
		enctype="multipart/form-data">
		<input type="file" name="filename"> <input type="submit"
			value="提交">
	</form>
	<a href="ShowServlet">选择下载文件</a>
</body>
<script type="text/javascript">
	function choose() {
		var abc = "";
		var files = document.getElementById("files").value;
		var list = document.getElementsByName("choose");
		for (var i = 0; i < list.length; i++) {
			if (list[i].checked == true) {
				if (abc == "") {
					abc = abc + list[i].value;
				} else {
					abc = abc + "," + list[i].value;
				}
			}
		}
		//这里用location跳转页面，并实现传参(本项目用js得到了页面显示的相应id，并封装到String里面，在servlet里面
		//		和Dao层里面封装User类并把数据库里的东西转到了Excel里面)
		location.href = "Servlet?method=choose&abc=" + abc + "&files=" + files;
	}
</script>
</html>