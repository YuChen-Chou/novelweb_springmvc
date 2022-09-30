<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%> 
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="../basic/header.jsp"%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/updateNovel.css">
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
							<!-- <img src="https://picsum.photos/180/200/?random=2" alt="..."> -->
							
							<img src="${pageContext.request.contextPath}/${novels.picture}" alt="...">
						</div>
						<div class="col-md-10">
							<div class="card-body novel-list-info">
								<h5 class="card-title">${novels.name}
								<button class="btn-icon-modifity" type="button" data-bs-toggle="modal" data-bs-target="#update1" data-bs-whatever="@mdo">
									<i class="fa fa-pencil-square-o"></i>
								</button>
								</h5>
								<p class="card-text">介紹:${novels.introduction}</p>
								<p class="card-text"><small>作者:&nbsp;${novels.author.name}</small></p>
								<p class="card-text"><small>類型:&nbsp;${novels.classification.name}</small></p>
								<p class="card-text"><small>更新日期:&nbsp;${dt}</small></p>
								<div class="modal fade" id="update1" tabindex="-1"
									aria-labelledby="exampleModalLabel" aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<h5 class="modal-title text-dark" id="exampleModalLabel">修改小說</h5>
												<button type="button" class="btn-close"
													data-bs-dismiss="modal" aria-label="Close"></button>
											</div>
											<div class="modal-body">
												<sf:form class="form-add-novel" role="form" method="post" modelAttribute="novels" enctype="multipart/form-data"
													action="${pageContext.request.contextPath}/member/updateNovel/${novels.id}">
													<div class="input-group mb-3">
														<span class="input-group-text" id="basic-addon1">作者名稱</span>
														<input type="text"  name="author.id" value="${author.id}" style="display: none">
														<input disabled type="text" class="form-control" 
															aria-label="NovelName" aria-describedby="basic-addon1" value="${author.name}">
											
													</div>
													<div class="input-group mb-3">
														<span class="input-group-text" id="basic-addon1">小說名稱</span>
														<input type="text" class="form-control" placeholder="小說名稱"
															aria-label="NovelName" aria-describedby="basic-addon1" name="name" value="${novels.name}">
											
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
																<option  value="${cls.id}" <c:if test="${cls.id==novels.classification.id}">selected</c:if> > ${cls.name}</option>
															</c:forEach>
														</select>
														
													</div>
													<div class="input-group mb-3">
														<textarea class="form-control" placeholder="小說介紹"
															id="floatingTextarea" name="introduction">${novels.introduction}</textarea>
													</div>
			
													<button type="button" class="btn btn-dark cancel">取消</button>
													<button type="submit" class="btn btn-dark">送出</button>
			
												</sf:form>
											</div>
											<div class="modal-footer"></div>
										</div>
									</div>
								</div>
								
							</div>
						</div>
					</div>
				</div>
				
				<div class="novel-list-name">章節列表</div>
				<div class="novel-list-item row row-cols-1 row-cols-md-6 wrap-auto g-0">
					<c:forEach items="${chapters}" var="chapter" varStatus="varIndex"> 
					<div class="col">
						<button class="btn-update-chaper" type="button" data-bs-toggle="modal" data-bs-target="#_${chapter.id}" data-bs-whatever="@mdo">第${varIndex.index+1}章 ${chapter.name}</button>
					</div>
					<div class="modal fade" id="_${chapter.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div class="modal-dialog">
						  <div class="modal-content">
							<div class="modal-header">
							  <h5 class="modal-title" id="exampleModalLabel">修改章節</h5>
							  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							</div>
							<div class="modal-body">
								<sf:form class="form-add-novel" role="form" action="${pageContext.request.contextPath}/member/updateChapter/${novels.id}" modelAttribute="chapter" method="post">
									<input type="text"  name="novels.id" value="${novels.id}" style="display: none">
									<input type="text"  name="id" value="${chapter.id}" style="display: none">
									<input type="text"  name="contentpath" value="${chapter.contentpath}" style="display: none">
									<div class="input-group mb-3">
										<span class="input-group-text" id="basic-addon1">章節名稱</span>
										<input type="text" class="form-control" placeholder="章節名稱"
											aria-label="NovelName" aria-describedby="basic-addon1" name="name" value="${chapter.name}">
									</div>
									
									<div class="input-group mb-3">
											<textarea class="form-control" placeholder="章節內容" id="floatingTextarea" name="content">${chapter.content}</textarea>
									</div>
									
									<button type="button" class="btn btn-dark cancel">取消</button>
									<button type="submit" class="btn btn-dark">送出</button>
									
								</sf:form>
							</div>
							<div class="modal-footer">
							</div>
						  </div>
						</div>
					  </div>	
					
					</c:forEach>

					<div class="col">
						<button class="btn-add-chaper" type="button" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo">+</button>
					</div>
				</div>
			</div>
			<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
						  <h5 class="modal-title" id="exampleModalLabel">新增章節</h5>
						  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class="modal-body">
							<sf:form class="form-add-novel" role="form" action="${pageContext.request.contextPath}/member/addChapter/${novels.id}" modelAttribute="chapter" method="post">
								<div class="input-group mb-3">
									<span class="input-group-text" id="basic-addon1">章節名稱</span>
									<input type="text"  name="novels.id" value="${novels.id}" style="display: none">
									<input type="text" class="form-control" placeholder="章節名稱"
										aria-label="NovelName" aria-describedby="basic-addon1" name="name">
								</div>
								
								<div class="input-group mb-3">
										<textarea class="form-control" placeholder="章節內容" id="floatingTextarea" name="content"></textarea>
								</div>
								
								<button type="button" class="btn btn-dark cancel">取消</button>
								<button type="submit" class="btn btn-dark">送出</button>
								
							</sf:form>
						</div>
						<div class="modal-footer">
						</div>
					</div>
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