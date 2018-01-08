$(function () {
    //修改头像
    headSculpture("#input-photo",".j-photo");

})

//修改头像
function headSculpture(inputcla,imgcla) {
    $(inputcla).change(function(){
        var file=this.files[0];
        var reader=new FileReader();
        reader.readAsDataURL(file);
        //当选择的文件加载成功时
        reader.onload=function(){
            var url=reader.result;
            setImageURL(url);
        }
    })

    var image=$(imgcla);
    function setImageURL(url){
        image.attr("src",url);
    }
}

//提交
  $(".j-submit").click(function(){
	  userAdd();
  });
  
  function userAdd(){
	  if($("#input-photo").prop("files")[0]!=""){
		  file = $("#input-photo").prop("files")[0];
	  }
	  var formData = new FormData();
		  formData.append("file", file);
		  formData.append("loginName", $.trim($("#login-name").val()));
		  formData.append("sex", $("#wrap input[name='sex']:checked").val());
		  formData.append("phone", $("#phone").val().trim());
		  formData.append("address", $("#phone").val().trim());
		  formData.append("email", $("#email").val().trim());
		  formData.append("introduce", $("#introduce").val().trim());
		  formData.append("addr",$("#addr").val().trim());
		  formData.append("qq", $.trim($("#qq").val().trim()));
		   $.ajax({
			  type:"post",
			  url:"/admin/userAdd",
			  data:formData,
			  processData : false, // 不处理发送的数据，因为data值是Formdata对象，不需要对数据做处理
			  contentType : false, // 不设置Content-type请求头
			  success:function(data){ 
				  alertLayerMessageUrl("创建成功！","/admin/user")
			  },error:function(data){
				  alertLayerMessage("出现数据异常");
			  },
		  });
	 }
