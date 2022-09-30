<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<!DOCTYPE html>
<html lang="en">

<head>
<%@include file="../basic/header.jsp"%>
<link rel="stylesheet"	href="${pageContext.request.contextPath}/resources/css/authorpage.css">
<style>
</style>

</head>

<body>
	<!--全部區塊-->
	<div class="page-container">
		<!--導航+內容-->
		<div class="content-wrap">
			<!-- 導航 -->
			<%@include file="../basic/nav.jsp"%>
			<!--導航結束-->

			<!--作者申請內容-->
			<div class="container">
				<c:choose>
					<c:when test="${sessionScope.member.memberclass.id==0}">
						<div class="" style="width: 30%; margin-top: 80px">
							<h4>成為作者</h4>
							<sf:form
								action="${pageContext.request.contextPath}/member/authorpage"
								method="post" modelAttribute="author">
								<div class="input-group mb-3 add-author">
									<span class="input-group-text bg-dark text-white"
										id="basic-addon1">作者名稱</span> <input type="text"
										class="form-control" placeholder="Name" aria-label="Name"
										aria-describedby="nameHelp" maxlength="15"
										onkeyup="this.value=this.value.replace(/\s+/g,'')" required
										name="name">
									<button type="submit" class="btn btn-dark">申請</button>
									<div id="nameHelp" class="form-text">不可輸入空白，最少1字，最多15字</div>

								</div>

							</sf:form>
						</div>
					</c:when>
					<c:otherwise>
						<!--作品-->
						<!--新增作品. 書名 封面(可空白) 介紹(可空白)-->
						<button class="btn-add-novel" type="button" data-bs-toggle="modal"
							data-bs-target="#exampleModal" data-bs-whatever="@mdo">新增小說</button>
						<div class="modal fade" id="exampleModal" tabindex="-1"
							aria-labelledby="exampleModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title" id="exampleModalLabel">新增小說</h5>
										<button type="button" class="btn-close"
											data-bs-dismiss="modal" aria-label="Close"></button>
									</div>
									<div class="modal-body">
										<sf:form class="form-add-novel" role="form" method="post" modelAttribute="novels"
											action="${pageContext.request.contextPath}/member/addNovel"
											enctype="multipart/form-data">
											<div class="input-group mb-3">
												<span class="input-group-text" id="basic-addon1">作者名稱</span>
												<input type="text"  name="author.id" value="${author.id}" style="display: none">
												<input disabled type="text" class="form-control" 
													aria-label="NovelName" aria-describedby="basic-addon1" value="${author.name}">
									
											</div>
											<div class="input-group mb-3">
												<span class="input-group-text" id="basic-addon1">小說名稱</span>
												<input type="text" class="form-control" placeholder="小說名稱"
													aria-label="NovelName" aria-describedby="basic-addon1" name="name">
									
											</div>
											<div class="input-group mb-3">
												<span class="input-group-text" id="basic-addon1">小說封面</span>
												<input type="file" class="form-control" name="imgfile" >
											</div>
											<div class="input-group mb-3">
												
												<span class="input-group-text" id="basic-addon1">小說分類</span>
												<!-- 從資料庫傳來分類 因為在novels物件中欄位為classification類別的ID欄位(資料庫)，所以要使用classification.id來mapping-->
												<select name="classification.id">
													<!-- ${classification}是從model的資料透過EL語法顯示  -->
													<c:forEach items="${classification}" var="cls">
														<option  value="${cls.id}"> ${cls.name}</option>
													</c:forEach>
												</select>
												
											</div>
											<div class="input-group mb-3">
												<textarea class="form-control" placeholder="小說介紹"
													id="floatingTextarea" name="introduction"></textarea>
											</div>
	
											<button type="button" class="btn btn-dark cancel">取消</button>
											<button type="submit" class="btn btn-dark">送出</button>
	
										</sf:form>
									</div>
									<div class="modal-footer"></div>
								</div>
							</div>
						</div>
						<!--新增作品. 書名 封面(可空白) 介紹(可空白) end-->
	
						<!--table清單顯示所有作品-->
						<div class="table-responsive">
							<table class="table table-dark table-hover  caption-top">
								<caption>
									<label class="novel-list-title">小說清單</label>
								</caption>
								<thead>
									<tr>
										<th scope="col">#</th>
										<th scope="col">書名</th>
										<th scope="col">類型</th>
										<th scope="col">狀態</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="novel" items="${novels}" varStatus="varIndex">
										<tr>
											<td>${varIndex.index+1}</td>
											<td><a href="${pageContext.request.contextPath}/member/updateNovel/${novel.id}">${novel.name}</a></td>
											<td>${novel.classification.name}</td>
											<td>${novel.status.name}</td>
										</tr>
									</c:forEach>									
								</tbody>
							</table>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			<!--作者內容end-->
			
			<!-- 提示申請成功 -->
			<c:if test="${successMsg!=null}">
				<div class="my-full-screen">
					<div class="my-text">
						<h1>申請作者成功</h1>
						成功申請作者...現在可以開始新增你的小說....... <a href="#" class="my-btn-close">X</a>
					</div>
				</div>
				<script
					src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
				<script type="text/javascript">
					$(document).ready(function() {
						$(".my-btn-close").click(function() {
							$(".my-full-screen").fadeOut();
						})
					})
				</script>
			</c:if>
			<!-- 提示申請成功end -->

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