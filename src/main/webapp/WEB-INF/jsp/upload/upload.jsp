<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>文件上传</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="系统自动,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    
	<script type="text/javascript" src="${js}/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="${js}/jquery.form.js"></script>
  </head>
  
  <body>
	<form action="${root}/upload/upload.do" method="post" enctype="multipart/form-data" id="form">
      項目页面目录：<input type="file" name="multipartFile" id="fileFolder" webkitdirectory><!-- 上传文件夹 -->
   <input type="button" value="提交上传" onclick="upload();">
   <a href="${root}/fileAnalyze/getFileLogic.do">测试解析效果</a>
  </form>
  <div id="tips" style="width:100%;height:80%;border:solid 1px #ccc;overflow-y:auto;"></div>
  <script type="text/javascript">
  	/*document.getElementById('fileFolder').onchange = function(e) {
		actual_filesSize=0;
		//是否选中文件夹  文件夹是否为空  数量和大小是否超过限制
		//判断是否选中文件
		var file=$("#fileFolder").val();
	  	if(file!=""){
	  		var files = e.target.files;            // files是选中的文件夹数组
			  //文件数量
			  actual_filesCount = files.length;
			  if(actual_filesCount > filesCount){
				  $("#tips").text(msg2+filesCount+msg3);
				  document.getElementById("tips").style.color="red";
				  return;
		  		}
		  //修改tips文本框内容
		  $("#tips").text(actual_filesCount+tip);
		  document.getElementById("tips").style.color="black";
		  
		  for (var i = 0; i< files.length; ++i){
			  actual_filesSize=actual_filesSize+files[i].size;
			  if(actual_filesSize > filesSize){
				  $("#tips").text(msg4+(filesSize/1024/1024)+"M");
				  document.getElementById("tips").style.color="red";
				  return;
			  }
		 	 }
		  }else{
			  $("#tips").text(msg);
			  document.getElementById("tips").style.color="red";
			  return;
		  }
	  };*/
	  function upload(){
		$("#form").ajaxSubmit(function (result) {
        	$("#tips").append(result.message + "<br/>");
        	//上传成功
        	if(result.successful){
            	$.ajax({
                    type: "post",
                    url: "${root}/fileAnalyze/getFileLogic.do",
                    data: {path:result.data},
                    dataType: "json",
                    success: function(data){
                    	$("#tips").append(data.message + "<br/>");
                    	if(data.successful){
                    		
                    	}
                    }
                });
             }
		});	  
	  }
	  
	  /*$(document).ready(function() {
	        // 指定websocket路径
	        var websocket = new WebSocket('ws://localhost:8080/relation/ws.do');
	        websocket.onmessage = function(event) {
	            // 接收服务端的实时日志并添加到HTML页面中
	            $("#tips").append(event.data + "<br/>");
	            // 滚动条滚动到最低部
	            $("#tips").scrollTop($("#tips").height());
	        };
	    });*/
  </script>
  
</body>
</html>
