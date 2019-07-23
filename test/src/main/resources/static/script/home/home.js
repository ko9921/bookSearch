$(document).ready(function() {
	
	getKeywordList();
	
	$("#search").keypress(function (e) {
		if (e.which == 13){
			search();
		}
	});
});

function search() {
	
	var search = $("#home input[name=search]").val();
	
	if(!search) {
		alert("검색어를 입력하세요.");
	}
	
	$.ajax({
		type:"get",
		url : "/book/searchList?search="+search,
		success :function(data){
			getKeywordList();
		}
	});
}

function getKeywordList() {
	$.ajax({
		type:"get",
		url : "/book/keywordList",
		success :function(data){
			
			var keywordList = data;
			
			if(keywordList != null && keywordList.length > 0) {
				var keywordHTML = "";
				
				for(var i = 0; i < keywordList.length; i++){
					var keywordItem = keywordList[i];
					
					keywordHTML += "<div><a onclick='setSearchVal(\""+keywordItem.keyword+"\")'>";
					keywordHTML += (i+1) + ". " + keywordItem.keyword + " <span style='color:red;'>("+keywordItem.searchcnt+")</span>";
					keywordHTML += "</a></div>";
				}
				
				$("#home .keywordList").empty();
				$("#home .keywordList").append(keywordHTML);
			}
		}
	});
}

function setSearchVal(keyword){
	$("#home input[name=search]").val(keyword);
	
	search();
}