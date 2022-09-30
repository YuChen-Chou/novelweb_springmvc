<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="../basic/header.jsp"%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/chapter.css">
</head>
<body>
	<!--全部區塊-->
	<div class="page-container">
		<!--導航+內容-->
		<div class="content-wrap">
			<!-- 導航 -->
			<%@include file="../basic/nav.jsp"%>
			
			<!-- 導航end -->
			<br>
			<!-- 內容 -->
			<div class="container">
				<div class="card text-white bg-dark mb-3">
				<c:forEach items="${p_chapter.list}" var="chapter">
				
					<div class="card-header  novel-name">
						<h4>書名:${chapter.novels.name}</h4>
						<h4>-BY:${chapter.novels.author.name}</h4>
					</div>
					<div class="card-body">
						<div class="chaper-name">
							<h5 class="card-title">章節名:${chapter.name}</h5>
						</div>
						<p class="card-text novel-content">
							${chapter.content}
						</p>
					</div>
				
					<div class="card-footer bg-transparent border-success footer-btn">
						<c:if test="${p_chapter.hasPreviousPage}">
							<a class="btn btn-dark btn-lg" href="${pageContext.request.contextPath}/novels/novel/${chapter.novels.id}/page/${p_chapter.pageNum-1}">上一頁</a>
						</c:if>
						<a class="btn btn-dark btn-lg" href="${pageContext.request.contextPath}/novels/novelinfo/${chapter.novels.id}">回目錄</a>
						<c:if test="${p_chapter.hasNextPage}">
							<a class="btn btn-dark btn-lg" href="${pageContext.request.contextPath}/novels/novel/${chapter.novels.id}/page/${p_chapter.pageNum+1}">下一頁</a>
						</c:if>
						
					</div>
					</c:forEach>
				</div>
			</div>
			<!-- 內容end -->
		</div>
		<!--導航+內容end-->
		<!--頁腳-->
		<%@include file="../basic/footer.jsp" %>
		<!--頁腳end-->
		<%@include file="../basic/msg.jsp" %>
	</div>
	<!--全部區塊end-->
</body>

</html>