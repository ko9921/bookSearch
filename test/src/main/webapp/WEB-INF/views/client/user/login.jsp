<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- header -->
<%@ include file="/WEB-INF/views/client/include/headerClient.jsp"%>
<!-- style -->
<link rel="stylesheet" href="/css/user/login.css" />
<!-- Login -->
<section id="login">
	<div id="wrapper">
		<div class="login-container">
			<div class="row">
				<div class="col-md-12">
					<div class="hpanel">
						<div class="panel-body">
							<form id="loginForm">
								<div class="form-group">
									<label class="control-label" for="id">ID</label>
									<input type="text" title="Please enter you username" placeholder="ID" required="" value="" name="id" id="id" autofocus="autofocus" class="form-control">
								</div>
								<div class="form-group">
									<label class="control-label" for="password">비밀번호</label>
									<input type="password" placeholder="******" required="" value="" name="password" id="password" class="form-control">
								</div>
								<button class="btn btn-success btn-block" onclick="login()"> 로그인 </button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- footer -->
<%@ include file="/WEB-INF/views/client/include/footerClient.jsp"%>
<script src="/script/user/login.js"></script>