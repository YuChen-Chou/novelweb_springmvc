<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html>
<head>
<%
response.setHeader("Cache-Control","no-store"); 
response.setHeader("Pragma","no-store"); 
response.setDateHeader ("Expires", 0);
%>
<meta charset="UTF-8">
<title>登入管理首頁</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" data-crossorigin="anonymous">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" href="../resources/css/admin/adminlogin.css">
</head>
<body> 

<!-- 登入介面 -->
<div class="login">
	<sf:form action="login" modelAttribute="admin" method="post" class="form" id="form1">
		<h2>管理人員登入</h2>
		<div class="group">
			<label for="username">帳號：</label><label class="err-msg"><sf:errors path="username"></sf:errors></label>
			<label class="err-msg">${errloginMsg}</label>
			<sf:input path="username" id="username" placeholder="請輸入帳號" autocomplete="off" required="required" />
		</div>
		<div class="group">
			<label for="password">密碼：</label><label class="err-msg"><sf:errors path="password"></sf:errors></label>
			<sf:password path="password" id="password" placeholder="請輸入密碼" autocomplete="off" required="required" />
		</div>
		<div class="btn-group">
			<button class="btn" id="send" >登入</button>
			<button class="btn" onclick="cancel()" type="button">取消</button>
		</div>
	</sf:form>
</div>

<script type="text/javascript">
function cancel() {
	document.getElementById("username").value="";
	document.getElementById("password").value="";
}
</script>
</body>
</html>