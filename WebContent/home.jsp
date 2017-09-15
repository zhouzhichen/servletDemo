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
	<input type="button" onclick="choose()" name="buttons">
	<a href="Insert.jsp">添加</a>
	<a href="Servlet?method=previous">上一页</a>
	<a href="Servlet?method=next">下一页</a>
	<jsp:useBean id="pagee" class="pageDao.PageDao"></jsp:useBean>
	${a }/<%=pagee.totalPage()%>
</body>
<script type="text/javascript">
	function choose() {
		var abc = "";
		var files=document.getElementById("files").value;
		var list = document.getElementsByName("choose");
		for (var i = 0; i < list.length; i++) {
			if (list[i].checked == true) {
				if (abc=="") {
					abc = abc + list[i].value;
				} else {
					abc = abc + "," + list[i].value;
				}
			}
		}

		location.href = "Servlet?method=choose&abc=" + abc+"&files="+files;
	}
</script>
</html>