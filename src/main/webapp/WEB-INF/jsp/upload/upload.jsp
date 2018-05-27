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
    
    <link href="${resource}/stream/css/stream-v1.css" rel="stylesheet" type="text/css">
	<script src="${js}/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="${resource}/stream/js/stream-v1.js"></script>
    
  </head>
  
  <body>
	<div id="i_select_files" class="stream-browse-files stream-browse-drag-files-area" style="display: block;">
		
	</div>
	<div id="i_stream_files_queue" class=" stream-main-upload-box">
	
	</div>
	<button onclick="javascript:_t.upload();">开始上传</button>
	<button onclick="javascript:_t.stop();">停止上传</button>
	<button onclick="javascript:_t.cancel();">取消</button>
	<button onclick="javascript:_t.destroy();_t=null;_t=new Stream(config);">清空上传队列</button>
	<br>
	Messages:
	<div id="i_stream_message_container" class="stream-main-upload-box" style="overflow: auto;height:200px;"></div>
	<input type="hidden" id="fileArray"/>
<br>

<script type="text/javascript">
var extStr=".html,.htm,.js,.css,.png,.jpg,.gif,.swf";
var extArr=extStr.split(',');

var fileArray = {};
/**stream上传控件，官网：http://www.twinkling.cn/
 * 配置文件（如果没有默认字样，说明默认值就是注释下的值）
 * 但是，on*（onSelect， onMaxSizeExceed...）等函数的默认行为
 * 是在ID为i_stream_message_container的页面元素中写日志
 */
 var config = {
			maxSize:${uploadFileMaxSize},//单个文件的大小限制，单位：字节
			browseFileId : "i_select_files", 
			browseFileBtn : "<div class='ie8css'>请选择文件</div>",
			dragAndDropArea: "i_select_files", 
			//dragAndDropTips: tags, 
			filesQueueId : "i_stream_files_queue", 
			filesQueueHeight : 220, // 文件上传容器的高度（px）
			multipleFiles: true, // 多个文件一起上传
			autoUploading: false,//选择文件后是否自动上传
			//postVarsPerFile : postParms,//上传文件时传入的参数
			tokenURL : "${root}/TokenServlet",//根据文件名、大小等信息获取Token的URI（用于生成断点续传、跨域的令牌）
			frmUploadURL : "${root}/FormDataServlet",//Flash上传的URI
			uploadURL : "${root}/StreamServlet",//HTML5上传的URI
			simLimit: ${uploadFileMaxNum},//单次最大上传文件个数
			swfURL : "${resource}/stream/swf/FlashUploader.swf", //SWF文件的位置 
			extFilters :extArr,//允许上传文件的类型--这里启用会导致谷歌浏览器选择文件窗口加载缓慢
			//formed:true,//采用表单上传(FLASH)方式
			/** 文件重复的响应事件  */
			onRepeatedFile : function(f) {
				alert("文件：" + f.name + " 大小：" + f.size + " 已存在于上传队列中。");
				return false;
			},
			/** 文件数量超出的响应事件 */
			onFileCountExceed : function(selected, limit) {
				alert("上传文件数量超过最大值，一次最多只能上传： " + limit + " 个文件");
				return false;
			},
			/** 文件的扩展名不匹配的响应事件 */
			onExtNameMismatch : function() {
				alert("上传文件格式不正确，只能上传以下["+extArr+"]扩展名文件!");
				return false;
			},
			onMaxSizeExceed: function(file) {
				alert("最大文件大小： " + (${uploadFileMaxSize}/1024)
	   			+ " KB 但是 \"" + file.name + "\" 是：" + (file.size/1024).toFixed(2)+" KB");
			},
			onComplete: function(file) {
				var msg = eval('(' + file.msg+ ')');
				if(msg.success == true){
				    var fileName = file.name;
				    fileName =fileName.replace(/\#/g, "& #41;");
				    fileName =fileName.replace(/,/g, "& #39;");
				    fileName =fileName.replace(/\+/g, "& #40;");
					fileArray.push(fileName + "+" + file.size+"#"+msg.uuidFileName);
				}
			},
			onQueueComplete: function(msg) {
				$("#fileArray").val("");
				$("#fileArray").val(fileArray);
				$.ajax({
					url:"${root}/fileAnalyze/getFileLogic.do",
					data:{
						"attaVo.stuffName":encodeURIComponent(stuffName),
						"attaVo.attachType":attachType,
						"customSavePath":customSavePath,
						"fileArray":encodeURIComponent($("#fileArray").val())
					},
					dataType:"json",
					success:function(result){
						fileArray.length=0;//把上次上传的清掉，否则不刷新的情况下添加再上传，会把之前的再上传一次					
					}
				});
			}
		};
			
		var _t = new Stream(config);
		
		function upload(){
			_t.upload();
		}
</script>
</body>
</html>
