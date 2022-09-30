<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="basic/header.jsp" %>
<link rel="stylesheet" href="../resources/css/admin/showadminlist.css">
</head>
<body>
	<div class="page-container" id="new">
		<!--導航+內容-->
		<div class="content-wrap">
			<!-- 導航 -->
			<jsp:include page="basic/nav.jsp"></jsp:include>
			<!--導航結束-->

			<!--查詢管理員帳號-->
			<div class="container" style="width: 100%;">
				<div class="" style="width: 30%; margin-top: 20px">
					<h4>查詢管理員帳號:</h4>
					<sf:form action="${pageContext.request.contextPath}/admin/find" method="post" modelAttribute="Admin">
						<div class="input-group mb-3 add-class">
							<span class="input-group-text bg-dark text-white"
								id="basic-addon1">帳號</span> <input type="text"
								class="form-control" placeholder="Username" aria-label="Name"
								aria-describedby="nameHelp" maxlength="10"
								onkeyup="this.value=this.value.replace(/\s+/g,'')" required
								name="username">
							<button type="submit" class="btn btn-success ">查詢</button>
						</div>
					</sf:form>
				</div>

				<!-- 查詢結束 -->
				<!-- 顯示查詢結果 -->
				<div id="findAdmin" class="table-responsive">
					<table class="table table-dark table-hover  caption-top table-sm ">
						<caption>
							<label class="novel-list-title">查詢管理員結果</label>
						</caption>
						<thead>
							<tr>
								<th scope="col">帳號</th>
								<th scope="col">密碼</th>
								<th scope="col">名字</th>
								<th scope="col">信箱</th>
								<th scope="col">電話</th>
								<th scope="col">管理級別</th>
								<th scope="col">修改</th>
								<th scope="col">刪除</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<c:if test="${adminfind!=null}">
								<sf:form action="${pageContext.request.contextPath}/admin/update" method="post" modelAttribute="Admin">
									<td style="display: none"><c:out value="${adminfind.id}"></c:out></td>
									<td><c:out value="${adminfind.username}"></c:out></td>
									<input style="display: none" type="text" name="id" value="${adminfind.id}">
									<input style="display: none" type="text" name="username" value="${adminfind.username}">
									<td><input type="password" name="password"
										style="width: 140px;" required
										value="<c:out value="${adminfind.password}"></c:out>"></td>
									<td><input type="text" name="name"
										style="width: 140px;" required
										value="<c:out value="${adminfind.name}"></c:out>"></td>
									<td><input type="text" name="email"
										style="width: 140px;" required
										value="<c:out value="${adminfind.email}"></c:out>"></td>
									<td><input type="text" name="phone"
										style="width: 140px;" required
										value="<c:out value="${adminfind.phone}"></c:out>"></td>
									<td><input type="text" name="grade"
										style="width: 140px;" required
										value="<c:out value="${adminfind.grade}"></c:out>"></td>
									<td><button class="btn  btn-success btn-sm">修改</button></td>
									</sf:form>		
									<td><a href="${pageContext.request.contextPath}/admin/delete/${adminfind.id}" class="btn  btn-success btn-sm">刪除</a></td>
								</c:if>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- 顯示查詢結果結束 -->
				<p>
					<!-- 管理員清單 -->
				<div class="table-responsive">
					<table
						class="table table-dark  table-striped table-hover  caption-top table-sm ">
						<caption>管理員清單</caption>
						<thead>
							<tr>
								<th scope="col">編號</th>
								<th scope="col">帳號</th>
								<th scope="col">密碼</th>
								<th scope="col">名字</th>
								<th scope="col">信箱</th>
								<th scope="col">電話</th>
								<th scope="col">管理級別</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="users" items="${adminlist}"
								varStatus="varIndex">
								<tr>
									<td><c:out value="${varIndex.index+1}"></c:out></td>
									<!-- 印出迴圈當前index -->
									<td><c:out value="${users.username}"></c:out></td>
									<td><c:out value="******"></c:out></td>
									<td><c:out value="${users.name}"></c:out></td>
									<td><c:out value="${users.email}"></c:out></td>
									<td><c:out value="${users.phone}"></c:out></td>
									<td><c:out value="${users.grade}"></c:out></td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<sf:form action="${pageContext.request.contextPath}/admin/add" method="post" modelAttribute="Admin">
								<td><button class="btn btn-success btn-sm">添加</button></td>
								<!-- 編號 -->
								<td><input class="form-control form-control-sm"
									style="width: 140px;" type="text" name="username" required/></td>
								<td><input class="form-control form-control-sm"
									style="width: 140px;" type="text" name="password" required/></td>
								<td><input class="form-control form-control-sm"
									style="width: 140px;" type="text" name="name" required/></td>
								<td><input class="form-control form-control-sm"
									style="width: 140px;" type="text" name="email" required/></td>
								<td><input class="form-control form-control-sm"
									style="width: 140px;" type="text" name="phone" required/></td>
								<td><input class="form-control form-control-sm"
									style="width: 140px;" type="text" name="grade" required/></td>
								</sf:form>
							</tr>
						</tfoot>
					</table>
				</div>
				<!-- 管理員清單結束 -->
			</div>
			<!-- 更新表單結尾 -->
		</div>
		<!--頁腳-->
		<%@include file="basic/footer.jsp"%>
		<!--頁腳end-->
		<%@include file="basic/msg.jsp" %>
	</div>
</body>
</html>