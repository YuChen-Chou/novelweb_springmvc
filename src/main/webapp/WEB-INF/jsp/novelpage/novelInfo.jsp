<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="../basic/header.jsp"%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/navelInfo.css">
</head>
<body>
	<!--全部區塊-->
	<div class="page-container">
		<!--導航+內容-->
		<div class="content-wrap">
			<!-- 導航 -->
			<%@include file="../basic/nav.jsp" %>
			<!-- 導航end -->	

			<br>
			<!-- 內容 -->
			<div class="container">
				<div class="card mb-3 border-0" style="max-width: 1200px;">
					<div class="row g-0">
						<div class="col-md-2 novel-img">
							<div class="tab-status-default">連載中</div>
							<img src="${pageContext.request.contextPath}/${novel.picture}" alt="...">
						</div>
						<div class="col-md-10">
							<div class="card-body novel-list-info">
								<h5 class="card-title">${novel.name}
								<c:if test="${sessionScope.member!=null}">
								<a href="${pageContext.request.contextPath}/member/addFavorites/${novel.id}" class="btn-add-myfavorites" type="button">
									收藏
								</a>
								</c:if>
								</h5>
								<c:set var="update_date"><fmt:formatDate value="${novel.updatetime.time}" 
													pattern="yyyy-MM-dd"/></c:set>
								<p class="card-text">介紹:${novel.introduction}</p>
								<p class="card-text"><small>作者:&nbsp;${novel.author.name}</small></p>
								<p class="card-text"><small>類型:&nbsp;${novel.classification.name}</small></p>
								<p class="card-text"><small>更新日期:&nbsp;${update_date}</small></p>
								<div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="novel-list-name">章節列表</div>
				<div class="novel-list-item row row-cols-1 row-cols-md-6 wrap-auto g-0">
					<c:forEach items="${chapters}" var="chapter" varStatus="varIndex">
						<div class="col">
							<a class="a-chapter" href="${pageContext.request.contextPath}/novels/novel/${chapter.novels.id}/page/${varIndex.index+1}">第${varIndex.index+1}章 ${chapter.name}</a>
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
	<!--全部區塊-->
</body>

</html>