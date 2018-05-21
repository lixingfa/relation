	<%@ page contentType="text/html; charset=UTF-8" %>
	<rapid:override name="title"><title>逻辑请求增加</title></rapid:override>
	
	<rapid:override name="otherCss">
	<style type="text/css">
		.td-left{text-align:right;padding:5px 5px 5px 0;}
		select{width:180px}
		td input[type='text']{ width:280px;}
	</style>
	</rapid:override>
	
	<rapid:override name="content">
	<div id="container" style="height:560px;overflow-y:scroll;" >
		<form method="post" action='' id="submitForm">
		<table style="margin:10px auto;">
			<tr>
	  				<td class="td-left">项目名称：</td>
	  				<td>
	  					<input id="projectName" name="projectName"  style="width:130px;" readonly="readonly" value="${trueFalseModel.projectName}">
	  					<span title="系统自动生成">uuid主键  ：</span>
	  					<input type="text" id="uuid" name="uuid" value="${trueFalseModel.uuid}" class="easyui-validatebox" readonly="readonly" style="width:150px;"/>
		  				<span title="不启用则不显示，启用则根据规则显示或隐藏">启用：</span>
		  				<input type="checkbox" id="status" <c:if test="${trueFalseModel.status=='1'}">checked</c:if>/><input type="hidden" name="status" value="${trueFalseModel.status}">
	  				</td>
	  			</tr>
				<tr>
	  				<td class="td-left" title="该请求URL标签所在的页面">所属页面 ：</td>
	  				<td>
	  					<input type="text" id="pageUrl" name="pageUrl" value="${trueFalseModel.pageUrl}" readonly="readonly" style="width:250px;">
	  					<span title="url标志，即页面上的请求占位符,约定以_URL}结尾。对于多个自加载请求，他们其实都是共用一个url_flag，页面排列时根据设定的逻辑进行">URL标志  ：</span>
	  					<input type="text" id="flag" name="flag" value="${trueFalseModel.flag}" readonly="readonly" style="width:250px;">
	  				</td>
	  			</tr>
	  			<tr>
	  				<td class="td-left" title="配置名称">配置名称 ：</td>
	  				<td>
	  					<input type="text" id="tfName" name="tfName" value="${trueFalseModel.tfName}" class="easyui-validatebox" style="width:150px;"/>
	  					<span title="当父配置为false，则子配置都为false">父配置  ：</span>
	  					<input type="text" id="parentFlag" name="parentFlag" value="${trueFalseModel.parentFlag}" class="easyui-validatebox" style="width:150px;"/>
	  				</td>
	  			</tr>
	  			<tr>
	  				<td class="td-left">条件 ：</td>
	  				<td>
	  					<textarea rows="5" cols="8" name="condition" id="condition" style="width:500px;overflow-y:scroll;">${trueFalseModel.condition}</textarea>
	  				</td>
	  			</tr>
	  			<tr>
	  				<td class="td-left">描述 ：</td>
	  				<td>
	  				<textarea rows="5" cols="8" name="tips" id="tips" style="width:500px;overflow-y:scroll;">${trueFalseModel.tips}</textarea>
	  				</td>
	  			</tr>
	  			<tr>
	  				<td class="td-left"><span style="color:red;">注意:</span></td>
	  				<td>
	  					&nbsp;&nbsp;配置条件不允许有换行\n等符号
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
				if($('#status').prop('checked')){
					$("input[name='status']").val('1');
				}else{
					$("input[name='status']").val('0');
				}
				//日志埋点
				track(_maq.push(['_action', '基础数据管理_逻辑请求管理_编辑逻辑请求_保存编辑逻辑请求信息']));
				if(!$("#submitForm").form('validate'))return;
				
				$("#submitForm").ajaxSubmit({
					url: '${root}/truefalse/updateSaveTrueFalse.do',
					dataType: 'json',
					success: function(data) {
						if(data.successful){
							$.messager.alert("操作提示", "更改逻辑请求成功!","info",function(){
								top.dialog({id:'edit'}).close([1]).remove();
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
				top.dialog({id:'edit'}).close().remove();
			});
			
		});
		
	</script>
	</rapid:override>
	
<jsp:include page="/WEB-INF/jsp/common/commonMould.jsp" />