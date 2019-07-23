$(document).ready(function() {
	
	
});

function signup() {
	
	var params = $('#signupForm').serializeObject();
	
	if(validation()){
		$.ajax({
			type: "POST",
			url : "/user/signupAction",
			data: params,
			dataType : "json",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8", 
			success : function(result){
				if(result != null) {
					if(result.result == "SUCCESS") {
						alert(result.msg);
						location.href = "/";
					} else {
						alert(result.msg);
					}
				}
			}
		});
	}
}

function validation() {
	if(!$("#signup input[name=id]").val()){
		alert("아이디는 필수입력입니다.");
		return false;
	}
	if(!$("#signup input[name=password]").val()){
		alert("패스워드는 필수입력입니다.");
		return false;
	}
	if(!$("#signup input[name=passwordConfirm]").val()){
		alert("패스워드 확인은 필수입력입니다.");
		return false;
	}
	if($("#signup input[name=password]").val() != $("#signup input[name=passwordConfirm]").val()){
		alert("패스워드와 패스워드 확인이 일치하지 않습니다. 다시 확인해 주시기 바랍니다.");
		return false;
	}
	if(!$("#signup input[name=name]").val()){
		alert("이름은 필수입력입니다.");
		return false;
	}
	
	return true;
}
