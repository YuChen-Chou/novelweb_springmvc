<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


	
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark nav-shadow">
		<div class="container-fluid">
			<a class="navbar-brand"
				href="${pageContext.request.contextPath}/admin/adminManager">NOVEL
				WEB管理系統</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarScroll"
				aria-controls="navbarScroll" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarScroll">
				<ul class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll"
					style="-bs-scroll-height: 100px;">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page"
						href="${pageContext.request.contextPath}/admin/adminManager">管理員表單</a>
					</li>
					<li class="nav-item"><a class="nav-link active"
						href="${pageContext.request.contextPath}/admin/novelwebManager">管理書籍分類</a>
					</li>
					<li class="nav-item"><a class="nav-link active"
						href="${pageContext.request.contextPath}/admin/slideshowManager">
							管理首頁幻燈片 </a></li>
				</ul>
			
				<sf:form action="${pageContext.request.contextPath}/admin/logout" modelAttribute="admin">
					<input type="hidden" name="nowpage" value="${nowpage}" >
					<button class="login-btn btn btn-success"  type="submit">登出</button>
				</sf:form>	
			</div>
		</div>
	</nav>
	<br>

