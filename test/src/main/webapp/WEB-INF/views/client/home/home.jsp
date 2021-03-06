<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
					<input type="hidden" class="id" name="id" value="${sessionScope.id}">
					<input type="hidden" name="totalCount" value="0">
					<input type="hidden" name="pageNo" value="1">
					<input type="hidden" name="keyword" value="">
					<c:if test="${empty sessionScope.id}">
						<a class="btn btn-primary btn-sm login" href="/user/login">로그인</a>
						<a class="btn btn-primary btn-sm signup" href="/user/signup">회원가입</a>
					</c:if>
					<c:if test="${not empty sessionScope.id}">
						<span>${sessionScope.id}</span> 님
						<a class="btn btn-danger btn-sm logout" onclick="logout()">로그아웃</a>
					</c:if>
				</div>
			</div>
			
			<div class="main-container">
				<!-- 인기 검색어 순위 차트 -->
				<div class="col-md-2">
					<div class="keyword-chart">
						<div class="list-title">검색어 순위 차트</div>
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
							<button class="btn btn-success btn-block" onclick="enterSearch()"> 검색 </button>
						</div>
					</div>
					<!-- 검색 리스트 -->
					<div class="row searchList">
						<div class="searchBookListTitle col-md-12">
							<div class="col-md-9 text-center">제목</div><div class="col-md-3 text-center">판매가</div>
						</div>
						<div class="searchBookList col-md-12">
						</div>
					</div>
					<!-- 검색 디테일 -->
					<div class="row searchDetail hidden">
					</div>
					<!-- Pagination -->
					<div class="row pagination col-md-12 text-center hidden">
					</div>
				</div>
				<!-- 나의 검색 기록 -->
				<div class="col-md-2">
					<c:if test="${not empty sessionScope.id}">
						<div class="search-history">
							<div class="list-title">검색 히스토리</div>
							<div class="searchHistoryList">
						
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- footer -->
<%@ include file="/WEB-INF/views/client/include/footerClient.jsp"%>
<script src="/script/home/home.js"></script>
