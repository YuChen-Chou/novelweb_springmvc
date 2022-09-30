<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../basic/header.jsp"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/index.css">
</head>
<body>
	<!--全部區塊-->
	<div class="page-container">
		<!--導航+內容-->
		<div class="content-wrap">
			<!-- 導航 -->
			<%@include file="../basic/nav.jsp"%>
			<!-- 幻燈片-->
			<div id="carouselExampleIndicators"
				class="carousel carousel-dark slide" data-bs-ride="carousel">
				<div class="carousel-indicators">
				<c:forEach items="${slideshows_list}" var="slideshow" varStatus="varIndex">
				<button type="button" data-bs-target="#carouselExampleIndicators"
						data-bs-slide-to="${varIndex.index}" <c:if test="${varIndex.index==0}">class="active" aria-current="true"</c:if>
						aria-label="Slide ${varIndex.index+1}"></button>
				</c:forEach>
				</div>
				
				<div class="carousel-inner">
					<c:forEach items="${slideshows_list}" var="slideshow" varStatus="varIndex">
					<div class="carousel-item <c:if test="${varIndex.index+1==1}"> active</c:if>" >
						<img style="width:1200px;height:400px;" src="${pageContext.request.contextPath}/${slideshow.picture}"
							class="d-block w-100" alt="...">
						<div class="carousel-caption d-none d-md-block">
							<h5 class="text-white">${slideshow.name}</h5>
						<!-- 	<p>Some representative placeholder content for the first
								slide.</p> -->
						</div>
					</div>
					</c:forEach>
				</div>
				<button class="carousel-control-prev" type="button"
					data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
					<span class="carousel-control-prev-icon" aria-hidden="true"></span>
					<span class="visually-hidden">Previous</span>
				</button>
				<button class="carousel-control-next" type="button"
					data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
					<span class="carousel-control-next-icon" aria-hidden="true"></span>
					<span class="visually-hidden">Next</span>
				</button>
			</div>
			<!-- 幻燈片end-->
			<!-- 
			自己加入path跟container兩層
			path:自己麵包屑名稱
			container:bootstrap裡面有規定寬度，參考https://getbootstrap.com/docs/5.0/layout/containers/
			 -->
			<div class="path">
				<div class="container">
					<nav aria-label="breadcrumb">
						<ol class="breadcrumb">
							<li class="breadcrumb-item"><a
								href="${pageContext.request.contextPath}/novels/index">Home</a></li>
							<c:choose>
								<c:when test="${classname!=null}">
									<li class="breadcrumb-item active" aria-current="page">${classname}</li>
								</c:when>
								<c:otherwise>
									<c:if test="${searchString!=null}">
										<li class="breadcrumb-item active" aria-current="page">搜尋:${searchString}</li>
									</c:if>
									<c:if test="${searchString==null}">
										<li class="breadcrumb-item active" aria-current="page">新書推薦</li>
									</c:if>
								</c:otherwise>
							</c:choose>

						</ol>
					</nav>
				</div>
			</div>
			<!-- 麵包屑end -->
			<!-- 內容區塊1 -->
			<div class="container">
				<div class="row">
					<div class="col-12 col-md-3">
						<div class="list-group">
							<a href="#"
								class="list-group-item list-group-item-action disabled"
								tabindex="-1" aria-disabled="true"><b>新書推薦榜</b></a>
							<c:forEach items="${new_novels_list}" var="novel">
								<a
									href="${pageContext.request.contextPath}/novels/novelinfo/${novel.id}"
									class="list-group-item list-group-item-action">${novel.name}
								</a>
							</c:forEach>

						</div>
					</div>
					<div class="col-12 col-md-9">
						<div class="title-new-novels">
							<c:choose>
								<c:when test="${classname!=null}">
									<h2>${classname}</h2>
								</c:when>
								<c:otherwise>
									<c:if test="${searchString!=null}">
										<h2>搜尋:${searchString}</h2>
									</c:if>
									<c:if test="${searchString==null}">
										<h2>新書推薦</h2>
									</c:if>
								</c:otherwise>
							</c:choose>

							<!-- <h5><a href="#" class="link-dark">更多>></a></h5> -->
						</div>
						<!-- 找不到該類別的list -->
						<c:if test="${errinfo!=null}">
							<h1>${errinfo}</h1>
						</c:if>
						<!--card外層1列 每列row-cols-1全部排一行 row-cols-md-5最小寬度(從第5行開始換行) wrap-auto自動分配 g-4欄位間隙--->
						<div class="row row-cols-1 row-cols-md-4 g-4 ">
							<!-- 此列有幾個col就幾行  -->
							<c:if test="${errinfo==null}">
								<c:forEach items="${pageInfo.list}" var="novel">
									<div class="col">
										<!-- 每行裡面放card區塊 h-100使全部等高度 -->
										<div class="card h-100 item-hot">
											<c:set var="now_date">
												<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" />
											</c:set>
											<c:set var="update_date">
												<fmt:formatDate value="${novel.updatetime.time}"
													pattern="yyyy-MM-dd" />
											</c:set>

											<c:if test="${now_date==update_date}">
												<div class="tab-hot">Hot</div>
											</c:if>

											<img class="cover"
												src="${pageContext.request.contextPath}/${novel.picture}"
												class="card-img-top" alt="...">
											<div class="card-body txt">
												<h5 class="card-title">${novel.name}</h5>
												<p class="card-text">${novel.author.name}</p>
												<div class="a-heart-a-more">
													<!-- <span class="like">&#10084;</span> -->
													<a
														href="${pageContext.request.contextPath}/novels/novelinfo/${novel.id}"
														class="btn btn-primary a-more">more</a>
												</div>
											</div>
										</div>
									</div>
								</c:forEach>
							</c:if>
						</div>
						<br>
						<!--換行 避免分頁擠到上面-->
						<!-- 分頁 -->
						<div class="page">
							<nav aria-label="Page navigation example">
								<ul class="pagination">
									<li class="page-item"><a class="page-link"
										href="${pageContext.request.contextPath}/novels/index?pageNum=1<c:if test="${classid!=null}">&classid=${classid}</c:if>"
										aria-label="Previous"> <span aria-hidden="true">首頁</span>
									</a></li>
									<c:if test="${pageInfo.hasPreviousPage}">
										<li class="page-item"><a class="page-link"
											href="${pageContext.request.contextPath}/novels/index?pageNum=${pageInfo.pageNum-1}<c:if test="${classid!=null}">&classid=${classid}</c:if>"
											aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
										</a></li>
									</c:if>

									<c:forEach items="${pageInfo.navigatepageNums}" var="page_Num">
										<c:if test="${page_Num==pageInfo.pageNum}">
											<li class="page-item active"><a class="page-link"
												href="#">${page_Num}</a></li>
										</c:if>
										<c:if test="${page_Num!=pageInfo.pageNum}">
											<li class="page-item"><a class="page-link"
												href="${pageContext.request.contextPath}/novels/index?pageNum=${page_Num}<c:if test="${classid!=null}">&classid=${classid}</c:if>">${page_Num}</a></li>
										</c:if>
									</c:forEach>

									<c:if test="${pageInfo.hasNextPage}">
										<li class="page-item"><a class="page-link"
											href="${pageContext.request.contextPath}/novels/index?pageNum=${pageInfo.pageNum+1}<c:if test="${classid!=null}">&classid=${classid}</c:if>"
											aria-label="Next"> <span aria-hidden="true">&raquo;</span>
										</a></li>
									</c:if>

									<li class="page-item"><a class="page-link"
										href="${pageContext.request.contextPath}/novels/index?pageNum=${pageInfo.pages}<c:if test="${classid!=null}">&classid=${classid}</c:if>"
										aria-label="Previous"> <span aria-hidden="true">尾頁</span>
									</a></li>
								</ul>
							</nav>
						</div>
						<!--分頁 end-->
					</div>
				</div>
			</div>
			<!-- 內容區塊end -->
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