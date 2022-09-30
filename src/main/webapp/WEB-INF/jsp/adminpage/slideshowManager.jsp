<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="basic/header.jsp" %>
<link rel="stylesheet" href="../resources/css/admin/novelwebmanager.css">
</head>
<body>

	<div class="page-container">
		<!--導航+內容-->
		<div class="content-wrap">
			<!-- 導航 -->
			<jsp:include page="basic/nav.jsp"></jsp:include>
			<!-- 導航結束 -->

			<!--作者申請內容-->
			<div class="container">

				<div class=""
					style="width: 80%; margin-top: 20px; min-width: 350px;">
					<h4>新增幻燈片</h4>
					<sf:form
						action="${pageContext.request.contextPath}/admin/addSlideshowManager"
						method="post" modelAttribute="Slideshow"
						enctype="multipart/form-data">
						<div class="input-group mb-3 add-class">
							<span class="input-group-text bg-dark text-white"
								id="basic-addon1">排序位置</span> 
							<input type="text" 
								class="form-control " aria-label="Name"
								aria-describedby="nameHelp" maxlength="2" oninput="this.value = this.value.replace(/[^0-9]/g, '');" required
								name="indexNum" required> 
							<select class="form-select form-select-sm" aria-label=".form-select-sm" name="disable">
								<option value="true" selected>啟用</option>
								<option value="false" >關閉</option>
							</select>	
							<span class="input-group-text bg-dark text-white" id="basic-addon1">名稱</span>
							<input type="text" class="form-control w-25" placeholder="Name"
								aria-label="Name" aria-describedby="nameHelp" maxlength="10"
								onkeyup="this.value=this.value.replace(/\s+/g,'')" required
								name="name" required>
							<span class="input-group-text bg-dark text-white" id="basic-addon1">活動圖片</span>
							<input type="file" class="form-control w-25" name="imgfile" required>
							<button type="submit" class="btn btn-dark">新增</button>
						</div>
					</sf:form>
				</div>
				
				<div class="" style="width: 30%; margin-top: 20px">
					<h4>查詢幻燈片資訊:</h4>
					<sf:form action="${pageContext.request.contextPath}/admin/slideshowfind" method="post" modelAttribute="Slideshow">
						<div class="input-group mb-3 add-class">
							<span class="input-group-text bg-dark text-white"
								id="basic-addon1">ID</span> <input type="text"
								class="form-control" placeholder="ID" aria-label="Name"
								aria-describedby="nameHelp" maxlength="10"
								onkeyup="this.value=this.value.replace(/\s+/g,'')" required
								name="id">
							<button type="submit" class="btn btn-success ">查詢</button>
						</div>
					</sf:form>
				</div>

				<!-- 查詢結束 -->
				<!-- 顯示查詢結果 -->
				<div class="table-responsive">
					<table class="table table-dark table-hover  caption-top table-sm ">
						<caption>
							<label class="novel-list-title">查詢結果</label>
						</caption>
						<thead>
							<tr>
								<th scope="col">ID</th>
								<th scope="col">活動名稱</th>
								<th scope="col">啟用狀態</th>
								<th scope="col">顯示順序</th>
								<th scope="col">圖片路徑</th>
								<th scope="col">修改</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<c:if test="${q_slideshow!=null}">
								<sf:form action="${pageContext.request.contextPath}/admin/updateSlideshowManager" method="post" modelAttribute="Slideshow" enctype="multipart/form-data">
									<input style="display: none" type="text" name="id" value="${q_slideshow.id}">
									<td><c:out value="${q_slideshow.id}"></c:out></td>
									<td><input type="text" name="name"
										style="width: 140px;" required
										value="<c:out value="${q_slideshow.name}"></c:out>"></td>
									<td>
										<select class="form-select form-select-sm" aria-label=".form-select-sm" name="disable">
											<option value="true" <c:if test="${q_slideshow.disable==true}">selected</c:if>>啟用</option>
											<option value="false" <c:if test="${q_slideshow.disable==false}">selected</c:if> >關閉</option>
										</select>
									</td>
									<td><input type="text" name="indexNum"
										style="width: 140px;" required
										 oninput="this.value = this.value.replace(/[^0-9]/g, '');"
										value="<c:out value="${q_slideshow.indexNum}"></c:out>"></td>
									<td><input style="display: none" type="text" name="picture"
										value="<c:out value="${q_slideshow.picture}"></c:out>">
										
										<div class="input-group add-class">
										<span class="input-group-text bg-dark text-white" id="basic-addon1">更換圖片</span>
										<input type="file" class="form-control w-25" name="imgfile" >
										</div>
										<a href="${pageContext.request.contextPath}/${q_slideshow.picture}">預覽原始圖片</a>
										</td>
									
									<td><button class="btn  btn-success btn-sm">修改</button></td>
									</sf:form>		
								</c:if>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- 顯示查詢結果結束 -->
				<p>

				<!--table清單顯示所有作品-->
				<div class="table-responsive">
					<table class="table table-dark table-hover  caption-top">
						<caption>
							<label class="novel-list-title">幻燈片活動名稱清單</label>
						</caption>
						<thead>
							<tr>
								<!-- <th scope="col">#</th> -->
								<th scope="col">ID</th>
								<th scope="col">活動名稱</th>
								<th scope="col">啟用狀態</th>
								<th scope="col">顯示順序</th>
								<th scope="col">圖片預覽</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="s" items="${slideshowlist}"
								varStatus="varIndex">
								<tr>
									<%-- <td>${varIndex.index+1}</td> --%>
									<td>${s.id}</td>
									<td>${s.name}</td>
									<td>
										<c:if test="${s.disable==true}">
											<span class="btn-switch">啟用</span>
										</c:if>
										<c:if test="${s.disable!=true}">
											<span class="btn-switch">停用</span>
										</c:if>
									</td>
									<td>${s.indexNum}</td>
									<td><a href="${pageContext.request.contextPath}/${s.picture}">預覽</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!--頁腳-->
		<%@include file="basic/footer.jsp"%>
		<!--頁腳end-->
		<%@include file="basic/msg.jsp" %>		
	</div>

</body>

</html>