<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- header -->
<%@ include file="/WEB-INF/views/client/include/headerClient.jsp"%>
<!-- style -->
<link rel="stylesheet" href="/css/home/home.css" />
<!-- HOME -->
<section id="home">
	<div id="wrapper">
		<div class="content">
			<div class="login-container">
				<div class="text-right m-b-md">
					<a class="btn btn-primary btn-sm login" href="/user/login">로그인</a>
					<a class="btn btn-primary btn-sm signup" href="/user/signup">회원가입</a>
					<a class="btn btn-danger btn-sm logout hidden" href="/user/logout">로그아웃</a>
				</div>
			</div>
			
			<div class="main-container">
				<!-- 인기 검색어 순위 차트 -->
				<div class="col-md-2">
					<div class="keyword-chart">
						<div>검색어 순위 차트</div>
						<div class="keywordList">
						
						</div>
					</div>
				</div>
				<!-- 도서 검색 Main -->
				<div class="col-md-8">
					<!-- 검색 -->
					<div class="row">
						<div class="col-md-10">
							<input type="text" title="Please enter title / ISBN / publisher / person" placeholder="Search" value="" name="search" id="search" autofocus="autofocus" class="form-control">
						</div>
						<div class="col-md-2">
							<button class="btn btn-success btn-block" onclick="search()"> 검색 </button>
						</div>
					</div>
					<!-- 검색 리스트 -->
					<div class="row">
					</div>
					<!-- Pagination -->
					<div class="row">
					</div>
				</div>
				<!-- 나의 검색 기록 -->
				<div class="col-md-2">
				</div>
			</div>
		</div>
	</div>
</section>
<!-- footer -->
<%@ include file="/WEB-INF/views/client/include/footerClient.jsp"%>
<script src="/script/home/home.js"></script>
