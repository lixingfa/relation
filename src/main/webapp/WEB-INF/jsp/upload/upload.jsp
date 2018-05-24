<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>My JSP 'index.jsp' starting page</title>
    
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
	<div>请选择文件</div><span>把文件(文件夹)拖拽到这里</span></div>

	<div id="i_stream_files_queue" class=" stream-main-upload-box">
	<div class="stream-files-scroll" style="height: 200px;"><ul id="files-container_1527176743142_01v_1"></ul></div><div id="total-container_1527176743142_01v_2" class="stream-total-tips">	上传总进度：<span class="stream-process-bar"><span style="width: 0%;"></span></span>	<span class="stream-percent">0%</span>，已上传<strong class="_stream-total-uploaded">&nbsp;</strong>	，总文件大小<strong class="_stream-total-size">&nbsp;</strong></div></div>
	<button onclick="javascript:_t.upload();">开始上传</button>|<button onclick="javascript:_t.stop();">停止上传</button>|<button onclick="javascript:_t.cancel();">取消</button>
	|<button onclick="javascript:_t.disable();">禁用文件选择</button>|<button onclick="javascript:_t.enable();">启用文件选择</button>
	<br>
	Messages:
	<div id="i_stream_message_container" class="stream-main-upload-box" style="overflow: auto;height:200px;">
	</div>
<br>

<script type="text/javascript">
/**
 * 配置文件（如果没有默认字样，说明默认值就是注释下的值）
 * 但是，on*（onSelect， onMaxSizeExceed...）等函数的默认行为
 * 是在ID为i_stream_message_container的页面元素中写日志
 */
	var config = {
		browseFileId : "i_select_files", /** 选择文件的ID, 默认: i_select_files */
		browseFileBtn : "<div>请选择文件</div>", /** 显示选择文件的样式, 默认: `<div>请选择文件</div>` */
		dragAndDropArea: "i_select_files", /** 拖拽上传区域，Id（字符类型"i_select_files"）或者DOM对象, 默认: `i_select_files` */
		dragAndDropTips: "<span>把文件(文件夹)拖拽到这里</span>", /** 拖拽提示, 默认: `<span>把文件(文件夹)拖拽到这里</span>` */
		filesQueueId : "i_stream_files_queue", /** 文件上传容器的ID, 默认: i_stream_files_queue */
		filesQueueHeight : 200, /** 文件上传容器的高度（px）, 默认: 450 */
		messagerId : "i_stream_message_container", /** 消息显示容器的ID, 默认: i_stream_message_container */
		multipleFiles: true /** 多个文件一起上传, 默认: false */
	};
	var _t = new Stream(config);
</script>
</body>
</html>
