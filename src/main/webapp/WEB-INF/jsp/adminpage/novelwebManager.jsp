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
					style="width: 35%; margin-top: 20px; min-width: 350px;">
					<h4>新增小說分類</h4>
					<sf:form
						action="${pageContext.request.contextPath}/admin/addNovelClass"
						method="post" modelAttribute="Classification">
						<div class="input-group mb-3 add-class">
							<span class="input-group-text bg-dark text-white"
								id="basic-addon1">分類ID</span> <input type="text"
								class="form-control " placeholder="ID" aria-label="Name"
								aria-describedby="nameHelp" maxlength="2"
								onkeyup="this.value=this.value.replace(/\s+/g,'')" required
								name="id"> <span
								class="input-group-text bg-dark text-white" id="basic-addon1">分類名稱</span>
							<input type="text" class="form-control w-25" placeholder="Name"
								aria-label="Name" aria-describedby="nameHelp" maxlength="10"
								onkeyup="this.value=this.value.replace(/\s+/g,'')" required
								name="name">
							<button type="submit" class="btn btn-dark">新增</button>
						</div>
					</sf:form>
				</div>
				
				<div class="" style="width: 30%; margin-top: 20px">
					<h4>查詢小說分類:</h4>
					<sf:form action="${pageContext.request.contextPath}/admin/classfind" method="post" modelAttribute="Classification">
						<div class="input-group mb-3 add-class">
							<span class="input-group-text bg-dark text-white"
								id="basic-addon1">分類ID</span> <input type="text"
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
							<label class="novel-list-title">查詢分類ID結果</label>
						</caption>
						<thead>
							<tr>
								<th scope="col">分類ID</th>
								<th scope="col">分類名稱</th>
								<th scope="col">修改</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<c:if test="${q_classification!=null}">
								<sf:form action="${pageContext.request.contextPath}/admin/classupdate" method="post" modelAttribute="Classification">
									<input style="display: none" type="text" name="id" value="${q_classification.id}">
									<td><c:out value="${q_classification.id}"></c:out></td>
									<td><input type="text" name="name"
										style="width: 140px;" required
										value="<c:out value="${q_classification.name}"></c:out>"></td>
									
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
							<label class="novel-list-title">小說分類名稱清單</label>
						</caption>
						<thead>
							<tr>
								<th scope="col">#</th>
								<th scope="col">分類ID</th>
								<th scope="col">分類名稱</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="classi" items="${classification}"
								varStatus="varIndex">
								<tr>
									<td>${varIndex.index+1}</td>
									<td>${classi.id}</td>
									<td>${classi.name}</td>
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