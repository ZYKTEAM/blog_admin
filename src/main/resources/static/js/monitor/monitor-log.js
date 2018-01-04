$(function () {
	
	var current=1;
	var limit = 22;
	
	var params={'start':0,'limit':limit};
	logList();
 
/* 参数*/
function paramsing(){
	var query = $("#query").val();
	params['start']=current-1;
	params['limit']=limit;
}
    
/**
 *  用户列表信息
 */
function logList(){
	paramsing();
	$("#logList").empty();
	$.ajax({
		type:"get",
		url:"/admin/listLog",
		data:params,
		success : function(data){
			$("#logTempl").tmpl(data).appendTo("#logList");
			pageing(data.count);
		},error:function(data){
			layer.alert("出现错误信息!");
		}
	});
  }

/**
 * 分页事件
 */
function pageing(count){
	if(count<1) {
		$(".pagination").empty();
		return false;
	}
	var totalPage = parseInt(count/limit)+((count%limit)>0?1:0);
	if(totalPage>1){
		$(".pagination").show();
    	$(".pagination").bootstrapPaginator({
    		bootstrapMajorVersion: 3.0,
    		currentPage: current,
    		totalPages: totalPage,
    		numberOfPages: limit,
    		itemTexts: function (type, page, currentpage) {
                switch (type) {
    	            case "first": return "首页";
    	            case "prev" : return "上一页";
    	            case "next" : return "下一页";
    	            case "last" : return "尾页";
    	            case "page" : return page;
                }
            },onPageClicked: function(event, originalEvent, type, page){
    			if(current == page){
    				return false;
    			}
    			current = page;
    			logList();
    		}
    	});
	}else{
		$(".pagination").hide();
	}
};
 
});
