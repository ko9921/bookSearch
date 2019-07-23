/**
 * 
 * Form Data Convert to Json
 *
 */

$(document).ready(function () {
	jQuery.fn.serializeObject = function() {
	    var obj = null;
	    try {
	        if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
	            var arr = this.serializeArray();
	            if (arr) {
	                obj = {};
	                jQuery.each(arr, function() {
	                	if(this.name === "_csrf") return;
	                	
	                	// 동일 name의 값은 배열 형태로 넣기
	                	if(obj[this.name] !== undefined){
	                		if (!obj[this.name].push) {
	                			obj[this.name] = [obj[this.name]];
	                        }
	                		obj[this.name].push(this.value || '');
	                	}
	                	else{
	                		obj[this.name] = this.value;
	                	}
	                });
	            }//if ( arr ) {
	        }
	    } catch (e) {
	        alert(e.message);
	    } finally {
	    }
	 
	    return obj;
	};
	// 원하는 데이터만 json으로
	jQuery.fn.serializeObjectBySelect = function() {
		var obj = null;
		try {
			var arr = this.find('input').serializeArray();
			var arrSel = this.find('select').serializeArray();
			
			arr = arr.concat(arrSel);
			
			if (arr) {
				obj = {};
				jQuery.each(arr, function() {
					// 동일 name의 값은 배열 형태로 넣기
                	if(obj[this.name] !== undefined){
                		if (!obj[this.name].push) {
                			obj[this.name] = [obj[this.name]];
                        }
                		obj[this.name].push(this.value || '');
                	}
                	else{
                		obj[this.name] = this.value;
                	}
                	// ie에서 includes 안됨..
//                	if(Object.keys(obj).includes(this.name)){
//                		obj[this.name] = Array.from(obj[this.name]);
//                		obj[this.name].push(this.value);
//                	}
//                	else{
//                		obj[this.name] = this.value;
//                	}
				});
			}//if ( arr ) {
		} catch (e) {
			alert(e.message);
		} finally {
		}
		
		return obj;
	};
	
	// ajax 토큰 값 설정
	var token = $("input[name='_csrf']").val();
	var header = "X-CSRF-TOKEN";
		
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
});
