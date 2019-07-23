$(document).ready(function() {
	
	
});

function login() {
	
	var params = $('#loginForm').serializeObject();
	
	if(validation()){
		$.ajax({
			type: "POST",
			url : "/user/loginAction",
			data: params,
			dataType : "json",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success : function(result){
				if(result.result == "SUCCESS") {
					alert(result.msg);
					location.href = "/";
				} else {
					alert(result.msg);
				}
			}
		});
	}
}

function validation() {
	if(!$("#login input[name=id]").val()){
		alert("아이디는 필수입력입니다.");
		return false;
	}
	
	if(!$("#login input[name=password]").val()){
		alert("패스워드는 필수입력입니다.");
		return false;
	}
	
	return true;
}
