$(document).ready(function() {
	
	getKeywordList();
	
	if($("#home input[name=id]").val()){
		getUserHistoryList();
	}
	
	$("#search").keypress(function (e) {
		if (e.which == 13){
			enterSearch();
		}
	});
});

function enterSearch() {
	var keyword = $("#home input[name=search]").val();
	setSearchVal(keyword);
}

function search(isPageChange) {
	
	var search = $("#home input[name=keyword]").val();
	var pageNo = Number($("#home input[name=pageNo]").val());
	
	if(!search) {
		alert("검색어를 입력하세요.");
	}
	
	console.log(isPageChange);
	
	$.ajax({
		type:"get",
		url : "/book/searchList?keyword="+search+"&pageNo="+pageNo+"&isPageChange="+isPageChange,
		success :function(data){
			getKeywordList();
			getUserHistoryList();
			
			if(data.result == 'SUCCESS'){
				if(data.bookList != null){
					var bookListJson = JSON.parse(data.bookList);
					// pagination
					$("#home input[name=totalCount]").val(bookListJson.meta.pageable_count);
					drawPagination();
					
					// bookList
					if(bookListJson.documents != null && bookListJson.documents.length > 0) {
						var searchBookHTML = "";
						for(var i = 0; i < bookListJson.documents.length; i++){
							var bookItem = bookListJson.documents[i];
							var saleItme = "";
							
							if(bookItem.status == "" || bookItem.sale_price < 0) {
								saleItme = "<span style='color:red; font-weight: bold;'>품절</span>";
							} else {
								saleItme = "<span style='color:#333333; font-weight: bold;'>" + bookItem.sale_price + "</span>";
							}
							
							searchBookHTML += "<div class='row book-item' onclick='bookDetail(\""+bookItem.isbn+"\")'>"; 
							searchBookHTML += "<div class='col-md-9 text-left'>"+bookItem.title+"</div>"; 
							searchBookHTML += "<div class='col-md-3 text-right'>"+saleItme+"</div>"; 
							searchBookHTML += "</div>"; 
						}
						$("#home .searchBookList").empty();
						$("#home .searchBookList").append(searchBookHTML);
						
						$("#home .searchList").removeClass("hidden");
						$("#home .pagination").removeClass("hidden");
						$("#home .searchDetail").addClass("hidden");
					} else {
						$("#home .searchBookList").empty();
						$("#home .searchBookList").append("<div class='row' style='color:red; font-weight: bold; text-align: center; margin-top:20px'>검색결과가 없습니다.</div>");
						
						$("#home .searchList").removeClass("hidden");
						$("#home .pagination").removeClass("hidden");
						$("#home .searchDetail").addClass("hidden");
					}
				}
			} else {
				alert(data.msg);
			}
		}
	});
}

function bookDetail(tempIsbn) {
	
	var isbnSplit = tempIsbn.split(" ");
	
	var isbn = isbnSplit[0] == "" ? isbnSplit[1] : isbnSplit[0];
	
	$.ajax({
		type:"get",
		url : "/book/detail?keyword="+isbn,
		success :function(data){
			if(data.result == 'SUCCESS'){
				if(data.bookList != null){
					var bookListJson = JSON.parse(data.bookList);
					if(bookListJson.documents != null && bookListJson.documents.length > 0) {
						var searchBookHTML = "";
						var bookItem = bookListJson.documents[0];
						var saleItme = "";
						
						if(bookItem.status == "" || bookItem.sale_price < 0) {
							saleItme = "<span style='color:red; font-weight: bold;'>품절</span>";
						} else {
							saleItme = "<span style='color:#333333; font-weight: bold;'>" + bookItem.sale_price + "</span>";
						}
						
						searchBookHTML += "<div class='row detail-content'>"; 
						searchBookHTML += "<label class='col-md-3 control-label'>썸네일</label>"; 
						searchBookHTML += "<div class='col-md-9 text-left'><img src='"+bookItem.thumbnail+"'></div>"; 
						searchBookHTML += "</div>"; 
						
						searchBookHTML += "<div class='row detail-content'>"; 
						searchBookHTML += "<label class='col-md-3 control-label'>제목</label>"; 
						searchBookHTML += "<div class='col-md-9 text-left'>"+bookItem.title+"</div>"; 
						searchBookHTML += "</div>"; 
						
						searchBookHTML += "<div class='row detail-content'>"; 
						searchBookHTML += "<label class='col-md-3 control-label'>소개</label>"; 
						searchBookHTML += "<div class='col-md-9 text-left'>"+bookItem.contents+"</div>"; 
						searchBookHTML += "</div>"; 
						
						searchBookHTML += "<div class='row detail-content'>"; 
						searchBookHTML += "<label class='col-md-3 control-label'>ISBN</label>"; 
						searchBookHTML += "<div class='col-md-9 text-left'>"+bookItem.isbn+"</div>"; 
						searchBookHTML += "</div>"; 
						
						searchBookHTML += "<div class='row detail-content'>"; 
						searchBookHTML += "<label class='col-md-3 control-label'>저자</label>"; 
						searchBookHTML += "<div class='col-md-9 text-left'>"+bookItem.authors+"</div>"; 
						searchBookHTML += "</div>"; 
						
						searchBookHTML += "<div class='row detail-content'>"; 
						searchBookHTML += "<label class='col-md-3 control-label'>출판사</label>"; 
						searchBookHTML += "<div class='col-md-9 text-left'>"+bookItem.publisher+"</div>"; 
						searchBookHTML += "</div>"; 
						
						searchBookHTML += "<div class='row detail-content'>"; 
						searchBookHTML += "<label class='col-md-3 control-label'>출판일</label>"; 
						searchBookHTML += "<div class='col-md-9 text-left'>"+new Date(bookItem.datetime).format("yyyy-MM-dd")+"</div>"; 
						searchBookHTML += "</div>"; 
						
						searchBookHTML += "<div class='row detail-content'>"; 
						searchBookHTML += "<label class='col-md-3 control-label'>정가</label>"; 
						searchBookHTML += "<div class='col-md-9 text-left'>"+bookItem.price+"</div>"; 
						searchBookHTML += "</div>"; 
						
						searchBookHTML += "<div class='row detail-content'>"; 
						searchBookHTML += "<label class='col-md-3 control-label'>판매가</label>"; 
						searchBookHTML += "<div class='col-md-9 text-left'>"+saleItme+"</div>"; 
						searchBookHTML += "</div>"; 
						
						$("#home .searchDetail").empty();
						$("#home .searchDetail").append(searchBookHTML);
						
						$("#home .searchList").addClass("hidden");
						$("#home .pagination").addClass("hidden");
						$("#home .searchDetail").removeClass("hidden");
					} else {
						alert(data.msg);
					}
				}
			} else {
				alert(data.msg);
			}
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
	$("#home input[name=keyword]").val(keyword);
	$("#home input[name=search]").val(keyword);
	$("#home input[name=totalCount]").val(0);
	$("#home input[name=pageNo]").val(1);
	
	search(false);
}

function getUserHistoryList() {
	
	$.ajax({
		type:"get",
		url : "/user/userHistoryList",
		success :function(data){
			
			var keywordList = data;
			
			if(keywordList != null && keywordList.length > 0) {
				var keywordHTML = "";
				
				for(var i = 0; i < keywordList.length; i++){
					var keywordItem = keywordList[i];
					
					keywordHTML += "<div><a style='color:grey;' onclick='setSearchVal(\""+keywordItem.keyword+"\")'>";
					keywordHTML += "- " + keywordItem.keyword + " <p style='color:blue;'>("+new Date(keywordItem.searchdtime).format("yyyy-MM-dd HH:mm:ss")+")</p>";
					keywordHTML += "</a></div>";
				}
				
				$("#home .searchHistoryList").empty();
				$("#home .searchHistoryList").append(keywordHTML);
			}
		}
	});
}

function drawPagination() {
	var totalCount = Number($("#home input[name=totalCount]").val());
	var currentPageNo = Number($("#home input[name=pageNo]").val());
	// 끝번호
	var endPageNo = Math.ceil(totalCount/10);
	if(endPageNo > 100) endPageNo = 100;
	// 최대 7개씩 한페이지에 보여줌
	var visualCount = 7;
	// 앞에 ... 넣을지 결정하는부분
	var morePrev = "<div class='col-md-1'></div>";
	if(currentPageNo > Math.ceil(visualCount/2))  {
		morePrev = "<div class='col-md-1'>...</div>";
	} 
	// 뒤에 ... 넣을지 결정하는 부분
	var moreNext = "<div class='col-md-1'></div>";
	if((endPageNo - currentPageNo) >= Math.ceil(visualCount/2))  {
		moreNext = "<div class='col-md-1'>...</div>";
	}
	
	var pagingHTML = "";
	if(currentPageNo == 1) {
		pagingHTML += "<div class='col-md-1 btn btn-primary'>&lt;</div>";
	} else {
		pagingHTML += "<div class='col-md-1 btn btn-primary' onclick='changePage(\""+(currentPageNo-1)+"\")'>&lt;</div>";
	}
	pagingHTML += morePrev;
	if((currentPageNo-3) > 0) {
		pagingHTML += "<div class='col-md-1 btn btn-page' onclick='changePage(\""+(currentPageNo-3)+"\")'>"+(currentPageNo-3)+"</div>";
	}
	if((currentPageNo-2) > 0) {
		pagingHTML += "<div class='col-md-1 btn btn-page' onclick='changePage(\""+(currentPageNo-2)+"\")'>"+(currentPageNo-2)+"</div>";
	}
	if((currentPageNo-1) > 0) {
		pagingHTML += "<div class='col-md-1 btn btn-page' onclick='changePage(\""+(currentPageNo-1)+"\")'>"+(currentPageNo-1)+"</div>";
	}
	pagingHTML += "<div class='col-md-1 btn btn-success' onclick='changePage(\""+(currentPageNo)+"\")'>"+(currentPageNo)+"</div>";
	if((currentPageNo+1) < (endPageNo+1)) {
		pagingHTML += "<div class='col-md-1 btn btn-page' onclick='changePage(\""+(currentPageNo+1)+"\")'>"+(currentPageNo+1)+"</div>";
	}
	if((currentPageNo+2) < (endPageNo+1)) {
		pagingHTML += "<div class='col-md-1 btn btn-page' onclick='changePage(\""+(currentPageNo+2)+"\")'>"+(currentPageNo+2)+"</div>";
	}
	if((currentPageNo+3) < (endPageNo+1)) {
		pagingHTML += "<div class='col-md-1 btn btn-page' onclick='changePage(\""+(currentPageNo+3)+"\")'>"+(currentPageNo+3)+"</div>";
	}
	pagingHTML += moreNext;
	if(currentPageNo == endPageNo) {
		pagingHTML += "<div class='col-md-1 btn btn-primary'>&gt;</div>";
	} else {
		pagingHTML += "<div class='col-md-1 btn btn-primary' onclick='changePage(\""+(currentPageNo+1)+"\")'>&gt;</div>";
	}
	
	$("#home .pagination").empty();
	$("#home .pagination").append(pagingHTML);
}

function changePage(selPageNo) {
	$("#home input[name=pageNo]").val(selPageNo);
	
	search(true);
}