<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="../basic/header.jsp"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/authorpage.css">
</head>
<body>
	<!--全部區塊-->
	<div class="page-container">
		<!--導航+內容-->
		<div class="content-wrap">
			<!-- 導航 -->
			<%@include file="../basic/nav.jsp"%>
			<!--導航結束-->
			<br>
			<!--作者申請內容-->
			<div class="container">
				<!--table清單顯示所有收藏作品-->
				<div class="table-responsive">
					<table class="table table-dark table-hover  caption-top">
						<caption>
							<label class="novel-list-title">收藏清單</label>
						</caption>
						<thead>
							<tr>
								<th scope="col">#</th>
								<th scope="col">書名</th>
								<th scope="col">作者</th>
								<th scope="col">類型</th>
								<th scope="col">狀態</th>
								<th scope="col">移除</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="favorites" items="${favorites}" varStatus="varIndex">
								<tr>
									<td>${varIndex.index+1}</td>
									<td><a
										href="${pageContext.request.contextPath}/novels/novelinfo/${favorites.novels.id}">${favorites.novels.name}</a></td>
									<td>${favorites.novels.author.name}</td>
									<td>${favorites.novels.classification.name}</td>
									<td>${favorites.novels.status.name}</td>
									<td><a href="${pageContext.request.contextPath}/member/deleteFavorites/${favorites.id}">移除</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<!--作者內容end-->
		</div>
		<!--導航+內容end-->

		<!--頁腳-->
		<%@include file="../basic/footer.jsp"%>
		<!--頁腳end-->
		<%@include file="../basic/msg.jsp" %>
	</div>
	<!--全部區塊-->
</body>

</html>