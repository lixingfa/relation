	<%@ page contentType="text/html; charset=UTF-8" %>
	<rapid:override name="title"><title>界面化配置</title></rapid:override>
	<rapid:override name="otherCss">	 
		<link rel="stylesheet" href="${css}/system/index/func_index.css" />
		<link rel="stylesheet" href="${css}/system/index/jquery-labelauty.css" />
	</rapid:override>
	<rapid:override name="body">class="easyui-layout"</rapid:override>
	<rapid:override name="content">
		<div id="north" region="north" style="height:50px; border:0; border-bottom:1px solid #ddd; padding:10px 20px 0px 0px; overflow:hidden;">
			<table id="searchTable" class="search-table" border="0">
				<tr>
					<td width="100" style="text-align: right">项目：</td>
					<td><input type="text" name="projectName" class="input-text" id="projectName" value="${t.projectName}"></td>
					<td width="100" style="text-align: right">请求地址：</td>
					<td><input type="text" style="width: 300px;" name="actionUrl" class="input-text" id="actionUrl" value="${t.actionUrl}"/></td>
					<td width="100" style="text-align: right">所在页面：</td>
					<td><input type="text" name="pageUrl" class="input-text" id="pageUrl" value="${t.pageUrl}"/></td>
					<td width="100" style="text-align: right">占位符：</td>
					<td><input type="text" name="flag" class="input-text" id="flag" value="${t.flag}"/></td>
					<td>
						<span>
						<input class="ui-btn"  type="button" value="查 询" onclick="doQuery();">
						</span>
						&nbsp;
						<span>
						<input class="ui-btn" type="button" value="清空条件" onClick="doClean();">
						</span>
					</td>
				</tr>		
				
			</table>
		</div>
		<div region="center" split="true" style="border:0;">
			<div class="div_content">
	  	  	<form id="funcForm" method = "POST">
	  		<div class="div_content_body">
	  			<c:set var="page" value=""/>
				<c:forEach items="${trueFalseList}" varStatus="status" var="tf">
	  			<c:if test="${tf.pageUrl != page}">
	  			<c:if test="${status.index > 0}">
		  			</ul></div></c:if>
		  			<div class="div_header">
		  				<font size="3px" style="line-height: 30px;font-weight: bold;">${tf.projectName}&nbsp;&nbsp;${tf.actionTips}</font>
		  				&nbsp;<span><a href="${tf.actionUrl}" target="_blank">${tf.actionUrl}</a>
		  				</span>
		  			</div>
		  			<div class="div_header_ul">
						<ul id="ul">
	  			</c:if>
						<li title="${tf.tips}">
							<label><input name='code' <c:if test="${tf.status == '1'}">checked='true'</c:if> 
							type='checkbox' value='${tf.status}' data-labelauty='${tf.tfName}' id='${tf.uuid}' flag='${tf.flag}'
							parentFlag='${tf.parentFlag}'/></label>
						</li>			
					<c:set var="page" value="${tf.pageUrl}"/>	  			
				</c:forEach>	  			
	  		</div>
	  		</form>
	  		</div>
   		</div>
   		<!-- 条件配置窗口 -->
   		<div id="condition" class="easyui-window" title="条件配置" style="width:900px;height:650px;padding:0 10px;" data-options="iconCls:'icon-edit',modal:true">
			<input type="hidden" id="itemAttachmentSeq"/>
			<table>
				<tr>
					<td width="100">条件：</td><td class="name"><textarea id="conditionTxt" rows="" cols="" style="width:800px;"></textarea></td>
		    	</tr>
		    	<tr>
					
		    	</tr>
			</table>
			<div style="margin-top:10px;text-align:center;">
				<a href="javascript:editAttach();" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
				<a href="javascript:$('#condition').window('close');" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">取消</a>		
			</div>
			<input type="hidden" id="buttonId"/>
		</div>
	</rapid:override>
	
	<rapid:override name="otherJs">
	<script type="text/javascript" src="${js}/system/func/jquery-labelauty.js"></script>
	</rapid:override>
	
	<rapid:override name="footScript">
	<script type="text/javascript">
		function doQuery(){
			var url = '/WebConfig/truefalse/toViewTrueFalsePage.do?1=1';
			var projectName = $.trim($("#projectName").val());
			if(projectName != ""){
				url = url + "&t.projectName=" + projectName;
			}
			var actionUrl = $.trim($("#actionUrl").val());
			if(actionUrl != ""){
				url = url + "&t.actionUrl=" + actionUrl;
			}
			var pageUrl = $.trim($("#pageUrl").val());
			if(pageUrl != ""){
				url = url + "&t.pageUrl=" + pageUrl;
			}
			var flag = $.trim($("#flag").val());
			if(flag != ""){
				url = url + "&t.flag=" + flag;
			}
			window.location.href=url;
		}
		/* 清空 */
		function doClean(){
			$('#projectName').val('');
			$('#actionUrl').val('');
			$('#pageUrl').val('');
			$('#flag').val('');
			doQuery();
		}
		/* 初始化加载完成后执行 */
		$(function(){
			$('#condition').window('close');
			//阻止浏览器默认右键点击事件
			$("li").bind("contextmenu", function(){
			    return false;
			});			
			/* 注册键盘事件 */
		  	$('#searchTable').bind('keydown',function(event){
		  		if(event.keyCode == 13){
		  			doQuery();
		  		}
		  	});			
			//状态修改
	  		$("li").mousedown(function(e) {
	  			var li = $(this);
	  			var input = $(li).children().children();
	  			var id = $(input).attr("id");	  			
		  		var value = $(input).val();
			    if (3 == e.which) {//右键为3
			    	$("#buttonId").val(id);
			    	//加上延迟，可以阻止右击的菜单显示。因为弹框会覆盖原来的li，浏览器会认为右击的是弹框。
			    	setTimeout(function(){$('#condition').window('open')}, 500);
			    } else if (1 == e.which) {//左键为1			        
		  			if(value == 1){
		  				value = 0;
		  			}else{
		  				value = 1;
		  			}
		  			/* 进行更新 */
		  			$.ajax({
						url : '/WebConfig/truefalse/updateSaveTrueFalse.do?t.uuid='+ id + '&t.status=' + value,
						dataType : 'json',
						type : 'post',
						async:false,
						success:function(data){
							$(input).val(value);
							//改变子项的显示，但不改变它们的值
							var flag = $(input).attr("flag");
							var ul = $(li).parent();
							$(ul).find('input[parentFlag="'+flag+'"]').each(function(index,obj){
								if(value == 0){//关掉的，则子开关也是关的
									$(obj).removeAttr("checked");
								}else{
									/*if($(obj).val() == 1){
										$(obj).attr("checked","true");
									}*/
									window.location.reload();//将属性添加回去不生效，只能刷新
								}								
						  });
						},
						error:function(error){
							alert("系统错误!");
						}
					});			        
			    }	  			
	  		});
			
			//渲染
			$("input[name='code']").labelauty();
		});
		
		//保存
		function editAttach(){			
			/*$.ajax({
				url: $("#updateAttach").val() + '?seq=' + $("#itemAttachmentSeq").val()
				+ '&attachDesc=' + $("input[name='desc']").val(),
				type: "POST",
				dataType: "json",
				cache: false,
				success: function(data) {
					$.messager.alert("操作提示", "备注修改成功！" ,"info");
					$('#stuffList').datagrid('reload');
					closeWin('fileEdit');
				},
				error: function(error) {
					$.messager.alert("操作提示", "系统错误！" + error,"error");
				}
			});*/
		}
	</script>
	</rapid:override>
	
<jsp:include page="/WEB-INF/jsp/common/commonMould.jsp" />