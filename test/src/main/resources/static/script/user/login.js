$(document).ready(function() {
	
	
});

function login() {
	
	if(validation()){
		$.ajax({
			type: "POST",
			url : "/user/loginAction",
			data: JSON.stringify(params),
			contentType: "application/json; charset=utf-8",
			success : function(result){
				alert("123");
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
