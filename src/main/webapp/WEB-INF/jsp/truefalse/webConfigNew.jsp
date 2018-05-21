	<%@ page contentType="text/html; charset=UTF-8" %>
	<rapid:override name="title"><title>逻辑配置请求增加</title></rapid:override>
	
	<rapid:override name="otherCss">
	<style type="text/css">
		.td-left{text-align:right;padding:5px 5px 5px 0;}
		select{width:180px}
		td input[type='text']{ width:280px;}
	</style>
	</rapid:override>
	
	<rapid:override name="content">
	<div id="container" style="height:560px;" >
		<form method="post" action='' id="submitForm">
		<table style="margin:0px auto;">
			<tr>
	  				<td class="td-left">FLAG标识 ：</td>
	  				<td>
	  					<input type="text" id="flag" name="flag" class="easyui-validatebox" style="width:200px; height: 20px;"/>
	  					<label id="showError"></label>
	  				</td>
	  			</tr>
	  			<tr>
	  				<td class="td-left">条件类型  ：</td>
	  				<td id="conditionRadio">
						<input type="radio" id="conditionType_0" name="conditionType" value="0" Class="easyui-validatebox">
						<span title="指配置文件里的条件，如ENV_DISTRICT=luogang">配置条件</span>
						&nbsp;
	  					<input type="radio" id="conditionType_1" name="conditionType" value="1" Class="easyui-validatebox">
	  					<span title="全表明.字段和条件，如lzcity_approve_control_info.approve_status=0 or lzcity_approve_control_info.approve_status = -2">SQL条件</span>
	  					&nbsp;
	  					<input type="button" value="清除条件" onClick="clearCondition();" title="没有条件时不需要配置">
	  				</td>
	  			</tr>
	  			<tr>
	  				<td class="td-left">条件 ：</td>
	  				<td>
	  					<textarea rows="5" cols="8" name="condition" id="condition" style="width:400px;overflow-y:scroll;"></textarea>
	  				</td>
	  				<td><input id="checkBtn" type="button" value="检测sql" ></input> </td>
	  			</tr>
	  			<tr>
	  				<td class="td-left"><span style="color:red;">注意:</span></td>
	  				<td>
	  					&nbsp;&nbsp;配置条件不允许有换行\n等符号
	  				</td>
	  			</tr>
	  			<tr>
	  				<td class="td-left">描述：</td>
	  				<td>
	  					<textarea rows="3" cols="8" name="tips" id="tips" style="width:400px;"></textarea>
	  				</td>
	  			</tr>
	  			<tr>
	  				<td colspan="4" style="text-align:center;padding-top: 10px;">
						<input id="sumbitButton" type="button" value="提交" ></input>
						<input id="closeButton" type="button" value="关闭"></input>
					</td>
	  			</tr>
	  		</table>
  		</form>
	</div>
	</rapid:override>
	
	<rapid:override name="otherJs">
		<script type="text/javascript" src="${js }/easyui/combotreekeyHandler.js"></script>
		<script type="text/javascript" src="${js }/common/tool.js"></script>
	</rapid:override>
	<rapid:override name="footScript">
	<script type="text/javascript">
		/* 初始化加载完成后执行 */
		$(function(){
			/* 提交 */
			$('#sumbitButton').click(function(){
				if(!$("#submitForm").form('validate')) return;
				var flag = $('#flag').val();
				var conditionType = $("input[name='conditionType']:checked").val();
				if(flag==''){
					return alertAndFoucus('标识必填!','flag');
				}
				var condition = $("#condition").val();
				if(typeof(conditionType) != "undefined" && condition == ""){
					return alertAndFoucus('请输入条件或者不要条件!','condition');
				}
				if (conditionType=="1"&&!checkContext(condition,false)){
					return alertAndFoucus('请输入条件或者不要条件!','condition');
				}
				$("#submitForm").ajaxSubmit({
					url: '${root}/truefalse/saveConfigCondition.do',
					dataType: 'json',
					success: function(data) {
						if(data.successful){
							$.messager.alert("操作提示", "新增逻辑请求成功!","info",function(){
								top.dialog({id:'add'}).close([1]).remove();
							});
						}else{
							$.messager.alert("操作提示", data.message,"error");
						}
					},
					error: function(error) {
						alert("系统错误！");
					}
				});
				
			});
			/* 关闭 */
			$('#closeButton').click(function(){
				top.dialog({id:'add'}).close().remove();
			});
			$("#checkBtn").click(function(){
				var condition = $("#condition").val();
				var conditionType = $("input[name='conditionType']:checked").val();
				if (conditionType == "1"){
					checkContext(condition,true);
				}
			});
			//页面对部分关键词做处理
			function checkContext(condition,isSay) {
				if (condition.indexOf("delete") != -1 
						|| condition.indexOf("drop") !=-1
						|| condition.indexOf("create")!=-1){
					if (isSay){
						alert("无效SQL,包含关键字"+condition);
					}
					return false;
				} else if (condition.trim().indexOf("select") == 0){
					if (isSay) {
						alert("有效SQL");
					}
					return true;
				} else {
					if (isSay) {
						alert("无效SQL");
					}
					return false;
				}
			}
			
			$("input[name='flag']").blur(function(){
				var flag = $(this).val();
				$.ajax({
					url : '${root}/truefalse/checkFlagForUse.do?flag='+flag,
					dataType : 'json',
					type : 'post',
					async:false,
					success:function(result) {
						var succ = result.successful;
						if (succ) {
							$("#showError").html("<span style='color:green;'>"+flag+" 标识有效</span>");
							$("#sumbitButton").show();
						} else {
							$("#showError").html("<span style='color:red;'>"+flag+" 标识已有</span>");
							$("#sumbitButton").hide();
						}
					},
					error:function(error){
						alert("系统错误!");
					}
				});
			});
		});
		
		
		
	
	</script>
	</rapid:override>
	
<jsp:include page="/WEB-INF/jsp/common/commonMould.jsp" />