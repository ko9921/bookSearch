<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- header -->
<%@ include file="/WEB-INF/views/client/include/headerClient.jsp"%>
<!-- style -->
<link rel="stylesheet" href="/css/user/signup.css" />
<!-- Sign UP -->
<section id="signup">
	<div id="wrapper">
		<div class="signup-container">
			<div class="row">
				<div class="col-md-12">
					<div class="hpanel">
						<div class="panel-body">
							<form id="signupForm">
								<div class="form-group">
									<label class="control-label" for="id">ID</label>
									<input type="text" title="Please enter you username" placeholder="ID" required="" value="" name="id" id="id" autofocus="autofocus" class="form-control">
								</div>
								<div class="form-group">
									<label class="control-label" for="password">비밀번호</label>
									<input type="password" placeholder="******" required="" value="" name="password" id="password" class="form-control">
								</div>
								<div class="form-group">
									<label class="control-label" for="password">비밀번호확인</label>
									<input type="password" placeholder="******" required="" value="" name="passwordConfirm" id="passwordConfirm" class="form-control">
								</div>
								<div class="form-group">
									<label class="control-label" for="password">이름</label>
									<input type="text" placeholder="Please enter your name" required="" value="" name="name" id="name" class="form-control">
								</div>
								<button type="button" class="btn btn-success btn-block" onclick="signup()"> 회원가입 </button>
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
<script src="/script/user/signup.js"></script>
