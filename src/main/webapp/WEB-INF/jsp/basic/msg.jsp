<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

	<!-- 錯誤/成功訊息 -->
	<div id="errMsg" style="display: none">${errMsg}</div>
	<div id="success" style="display: none">${success}</div>
	<div>
		<c:if test="${errMsg!=null && errMsg!=''}">
			<script>
				$(document).ready(function() {
					errMsg = document.getElementById("errMsg").innerText;
					window.alert(errMsg);
				});
			</script>
		</c:if>
		<c:if test="${success!=null && success!=''}">
			<script>
				$(document).ready(function() {
					successMsg = document.getElementById("success").innerText;
					window.alert(successMsg);
				});
			</script>
		</c:if>
	</div>
