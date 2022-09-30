<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="../basic/header.jsp"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/memberinfo.css">
</head>
<body>
	<!--全部區塊-->
	<div class="page-container">
		<!--導航+內容-->
		<div class="content-wrap">
			<!-- 導航 -->
			<%@include file="../basic/nav.jsp"%>
			<!-- 導航end -->
			


			<!--內容-->
			<div class="container ">
				<div class="content-page mx-auto ">

					<ul class="nav nav-tabs" id="myTab" role="tablist">
						<li class="nav-item" role="presentation">
							<button class="nav-link active" id="home-tab"
								data-bs-toggle="tab" data-bs-target="#home" type="button"
								role="tab" aria-controls="home" aria-selected="true">修改資料</button>
						</li>
					</ul>

					<div class="tab-content" id="myTabContent">
						<!-- 修改個人資料區塊 -->
						<div class="tab-pane fade show active" id="home" role="tabpanel"
							aria-labelledby="home-tab">
							<sf:form class="form-login" role="form"
								action="${pageContext.request.contextPath}/member/update"
								modelAttribute="member" method="post">
								<input type="text" name="id" value="${sessionScope.member.id}"
									style="display: none">
								<input type="text" name="username"
									value="${sessionScope.member.username}" style="display: none">
								<input type="text" name="memberclass.id"
									value="${sessionScope.member.memberclass.id}"
									style="display: none">
								<div class="input-group mb-3">
									<span class="input-group-text" id="basic-addon1">名稱</span> <input
										type="text" class="form-control" placeholder="Name"
										aria-label="Name" aria-describedby="basic-addon1" name="name"
										value="${sessionScope.member.name}" ></input>
								</div>

								<div class="input-group mb-3">
									<span class="input-group-text" id="basic-addon1">帳號</span> <input
										disabled type="text" class="form-control"
										placeholder="Username" aria-label="Username"
										aria-describedby="basic-addon1"
										value="${sessionScope.member.username}">
								</div>
								<div class="input-group mb-3">
									<span class="input-group-text" id="basic-addon1">密碼</span> <input
										type="password" class="form-control" placeholder="Password"
										aria-label="Password" aria-describedby="basic-addon1"
										name="password" value="${sessionScope.member.password}">
								</div>
								<div class="input-group mb-3">
									<span class="input-group-text" id="basic-addon1">信箱</span> <input
										type="email" class="form-control" placeholder="Email"
										aria-label="Email" aria-describedby="basic-addon1"
										name="email" value="${sessionScope.member.email}">
								</div>
								<div class="input-group mb-3">
									<span class="input-group-text" id="basic-addon1">電話</span> <input
										type="tel" class="form-control" placeholder="手機號碼"
										aria-label="Phone" aria-describedby="basic-addon1"
										pattern="[0-9]{4}\-?\d{3}\-?\d{3}" title="格式:0~9(10碼)"
										maxlength="10" name="phone"
										value="${sessionScope.member.phone}">
								</div>
								<div class="input-group mb-3">
									<span class="input-group-text" id="basic-addon1">性別</span>
									<div class="radio-check-group">
										<div class="form-check form-check-inline">
											<input class="form-check-input" type="radio" name="gender"
												id="exampleRadios1" value="M"
												<c:if test="${sessionScope.member.gender=='M'}">checked</c:if>>
											<label class="form-check-label" for="exampleRadios1">
												男性 </label>
										</div>
										<div class="form-check form-check-inline">
											<input class="form-check-input" type="radio" name="gender"
												id="exampleRadios2" value="F"
												<c:if test="${sessionScope.member.gender=='F'}">checked</c:if>>

											<label class="form-check-label" for="exampleRadios2">
												女性 </label>
										</div>

									</div>
								</div>

								<!-- TODO:驗證碼設計 -->
								<button type="button" class="btn btn-dark">取消</button>
								<button type="submit" class="btn btn-dark">保存</button>
							</sf:form>
						</div>
						<!-- 修改個人資料區塊end -->
					</div>
				</div>

			</div>
			<!--內容end-->
			


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
