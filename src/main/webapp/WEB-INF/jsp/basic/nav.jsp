<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>    
			<nav class="navbar navbar-expand-lg navbar-dark bg-dark nav-shadow">
				<div class="container-fluid">
					<a class="navbar-brand" href="${pageContext.request.contextPath}/novels/index">NOVEL WEB</a>
					<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
						data-bs-target="#navbarScroll" aria-controls="navbarScroll" aria-expanded="false"
						aria-label="Toggle navigation">
						<span class="navbar-toggler-icon"></span>
					</button>
					<div class="collapse navbar-collapse" id="navbarScroll">
						<ul class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll"
							style="--bs-scroll-height: 100px;">
							<li class="nav-item">
								<a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/novels/index">首頁</a>
							</li>
							<li class="nav-item">
								
								<c:choose>
								<c:when test="${sessionScope.member!=null}">
									<a class="nav-link" href="${pageContext.request.contextPath}/member/favorites">收藏</a>
								</c:when>
								<c:otherwise>
									<a class="nav-link" onclick="return confirm('請先登入會員')">收藏</a>
								</c:otherwise>
							</c:choose>
							</li>
							<li class="nav-item dropdown">
								<a class="nav-link dropdown-toggle" href="#" id="navbarScrollingDropdown" role="button"
									data-bs-toggle="dropdown" aria-expanded="false">
									分類
								</a>
								<ul class="dropdown-menu" aria-labelledby="navbarScrollingDropdown">
									<c:forEach items="${sessionScope.nav_novel_classification}" var="cls">
										<li><a class="dropdown-item" href="${pageContext.request.contextPath}/novels/classlist/${cls.id}">${cls.name}</a></li>
									</c:forEach>
								</ul>
							</li>
							<li class="nav-item">
							<c:choose>
								<c:when test="${sessionScope.member!=null}">
									<a class="nav-link" href="${pageContext.request.contextPath}/member/authorpage">作者專欄</a>
								</c:when>
								<c:otherwise>
									<a class="nav-link" onclick="return confirm('請先登入會員')">作者專欄</a>
								</c:otherwise>
							</c:choose>
							</li>
						</ul>
						
						<c:choose>
							<c:when test="${sessionScope.member!=null}">
								<div class="login-tooltip dropdown">
									<button class="login-btn" type="button" class="btn btn-primary" id="dropdownMenu2" data-bs-toggle="dropdown" aria-expanded="false">
										<i class="login-icon fa fa-user fa-fw fa-2x fa-inverse "></i>
									</button>
									<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
										<li>
											<sf:form action="${pageContext.request.contextPath}/member/info" modelAttribute="member" method="get">
												<button class="dropdown-item" type="submit">個人資訊</button>
											</sf:form>
										</li>
										<li>
											<sf:form action="${pageContext.request.contextPath}/member/logout" modelAttribute="member">
												<button class="dropdown-item" type="submit">登出</button>
											</sf:form>
										</li>
									</ul>
								</div>
							</c:when>
							<c:otherwise>
								<!-- TODO:登入按鈕+點選跳出登入表單/註冊表單切換+驗證碼-->
								<span class="login-tooltip">
									<span class="login-tooltiptext">登入/註冊</span>
									<button class="login-btn" type="button" class="btn btn-primary" data-bs-toggle="modal"
										data-bs-target="#staticBackdrop" data-bs-whatever="@mdo">
										<i class="login-icon fa fa-user fa-fw fa-2x fa-inverse "></i>
									</button> 
								</span>
							</c:otherwise>
						</c:choose>
						
						
						<form class="d-flex" action="${pageContext.request.contextPath}/novels/search">
							<input class="form-control me-2" type="search" placeholder="找書名、作者名" aria-label="Search" name="search_string">
							<button class="btn btn-outline-success" type="submit">Search</button>
						</form>
					</div>
				</div>
			</nav>
			<!-- 導航end -->

			<!-- login-dialog-->
			<div class="modal fade login-dialog " id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false"  tabindex="-1" aria-labelledby="staticBackdropLabel"
				aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="staticBackdropLabel">NOVEL</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class="modal-body">
							<div class="container ">
								<div class="content-page mx-auto ">

									<ul class="nav nav-tabs" id="myTab" role="tablist">
										<li class="nav-item" role="presentation">
											<button class="nav-link active" id="home-tab" data-bs-toggle="tab"
												data-bs-target="#home" type="button" role="tab" aria-controls="home"
												aria-selected="true">登入</button>
										</li>
										<li class="nav-item" role="presentation">
											<button class="nav-link" id="profile-tab" data-bs-toggle="tab"
												data-bs-target="#profile" type="button" role="tab"
												aria-controls="profile" aria-selected="false">註冊</button>
										</li>
									</ul>

									<div class="tab-content" id="myTabContent">
										<!-- 登入區塊 -->
										<div class="tab-pane fade show active" id="home" role="tabpanel"
											aria-labelledby="home-tab">
											<sf:form class="form-login" role="form" action="${pageContext.request.contextPath}/member/login" modelAttribute="member" method="post">
												<div class="input-group mb-3">
													<span class="input-group-text" id="basic-addon1">帳號</span>
													<input type="text" class="form-control" placeholder="Username"
														aria-label="Username" aria-describedby="basic-addon1" name="username"/>
												</div>
												<div class="input-group mb-3">
													<span class="input-group-text" id="basic-addon1">密碼</span>
													<input type="password" class="form-control" placeholder="Password"
														aria-label="Password" aria-describedby="basic-addon1" name="password"/>
												</div>
												<button type="button" class="btn btn-dark cancel">取消</button>
												<button type="submit" class="btn btn-dark">登入</button>
												<label class="err-msg">${errloginMsg}</label>
											</sf:form>
										</div>
										<!-- 登入區塊end -->
										<!-- 註冊區塊 -->
										<div class="tab-pane fade" id="profile" role="tabpanel"
											aria-labelledby="profile-tab">
											<sf:form class="form-login" role="form" action="../member/add" modelAttribute="member" method="post">
												<!-- 註冊資訊... -->
												<div class="input-group mb-3">
													<span class="input-group-text" id="basic-addon1">名稱</span>
													<input type="text" class="form-control" placeholder="Name"
														aria-label="Name" aria-describedby="basic-addon1" name="name">
												</div>
												<div class="input-group mb-3">
													<span class="input-group-text" id="basic-addon1">帳號</span>
													<input type="text" class="form-control" placeholder="Username"
														aria-label="Username" aria-describedby="basic-addon1" name="username">
												</div>
												<div class="input-group mb-3">
													<span class="input-group-text" id="basic-addon1">密碼</span>
													<input type="password" class="form-control" placeholder="Password"
														aria-label="Password" aria-describedby="basic-addon1" name="password">
												</div>
												<div class="input-group mb-3">
													<span class="input-group-text" id="basic-addon1">信箱</span>
													<input type="email" class="form-control" placeholder="Email"
														aria-label="Email" aria-describedby="basic-addon1" name="email">
												</div>
												<div class="input-group mb-3">
													<span class="input-group-text" id="basic-addon1">電話</span>
													<input type="tel" class="form-control" placeholder="手機號碼"
														aria-label="Phone" aria-describedby="basic-addon1"
														pattern="[0-9]{4}\-?\d{3}\-?\d{3}" title="格式:0~9(10碼)"
														maxlength="10" name="phone">
												</div>
												<div class="input-group mb-3">
													<span class="input-group-text" id="basic-addon1">性別</span>
													<div class="radio-check-group">
														<div class="form-check form-check-inline">
															<input class="form-check-input" type="radio"
																name="gender" id="exampleRadios1" value="M"
																checked>
															<label class="form-check-label" for="exampleRadios1">
																男性
															</label>
														</div>
														<div class="form-check form-check-inline">
															<input class="form-check-input" type="radio"
																name="gender" id="exampleRadios2"
																value="F">
															<label class="form-check-label" for="exampleRadios2">
																女性
															</label>
														</div>

													</div>
												</div>
												<!-- TODO:驗證碼設計 -->
												<button type="button" class="btn btn-dark">取消</button>
												<button type="submit" class="btn btn-dark">註冊</button>
											</sf:form>
										</div>
									</div>
									<!-- 註冊區塊end -->
									<div class="modal-footer"></div>
								</div>
							</div>
						</div>


					</div>
				</div>
			</div>