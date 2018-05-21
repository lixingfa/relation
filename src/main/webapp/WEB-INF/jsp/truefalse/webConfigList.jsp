	<%@ page contentType="text/html; charset=UTF-8" %>
	<rapid:override name="title"><title>二级逻辑判断展示</title></rapid:override>
	<rapid:override name="body">class="easyui-layout"</rapid:override>
	<rapid:override name="content">
		<div id="north" region="north" style="height:70px; border:0; border-bottom:1px solid #ddd; padding:10px 20px 0px 0px; overflow:hidden;">
			<table id="searchTable" class="search-table" border="0">
				<tr>
					<td width="100" style="text-align: right">标识：</td>
					<td><input type="text" name="flag" class="input-text" id="flag"/></td>
					
					<td>
						<span>条件：</span>
						<select id="conditionType" panelheight="auto">
							<option value="">---请选择---</option>
							<option value="0">配置条件</option>
							<option value="1">SQL条件</option>
						</select>
					</td>
					<td>
						<span>
						<input class="ui-btn"  type="button" value="查 询" onclick="javascript:queryOption.doQuery();">
						</span>
						&nbsp;
						<span>
						<input class="ui-btn" type="button" value="清空条件" onClick="javascript: queryOption.doClean();">
						</span>
					</td>
				</tr>		
				
			</table>
		</div>
		<div region="center" split="true" style="border:0;background:#eee;">
			<table id="actionList"></table>
   		</div>
	</rapid:override>
	
	<rapid:override name="footScript">
	<script type="text/javascript">
	
		/* 表格对象 */
		var datagridTable={
			init:function(){
				$('#actionList').datagrid({
					iconCls:'icon16-card-pencil',
					fit: true,
					nowrap: true, 
					autoRowHeight: true,
					animate:true,
					scrollbarSize: 0,
					striped: true,
					collapsible:true,
					fitColumns:false,
					singleSelect:false,
					rownumbers:true,
					pagination:true,
					pageSize: 100,//每页显示的记录条数，默认为10
			        pageList: [100,200,400,800],//可以设置每页记录条数的列表
					url:'${root}/truefalse/loadWebConfigConditions.do',
					columns:[[
						{field:'flag', title:'标识',width:200,align:'center'},
						{field:'conditionType',			title:'条件类型',			width:120, align:'center',
		    				formatter:function(value,row,index){
		    					if(row){
		    						if(row.conditionType != null){
	    								if( 0 == row.conditionType ){
	    									return '配置';
	    								}else if( 1 == row.conditionType ){
	    									return 'SQL';
	    								}
		    						}
		    					}
		    				}
						},
						{field:'condition',				title:'条件',			width:750},
						{field:'tips',				title:'描述',			width:400}
					]],
					toolbar:[{
						id:'addAction',
						text:'新增逻辑',
						iconCls:'icon16-user-plus',
						handler:function(){
							//日志埋点
							track(_maq.push(['_action', '基础数据管理_逻辑管理_新增逻辑']));
							top.dialog({
							    id: 'add',
							    url:'${root}/truefalse/toAddWebConfigPage.do',
							    width: 700,
								height:320,
								title:'新增逻辑信息',
								onclose:function(){
									if(typeof(this.returnValue[0]) == "undefined")return;
									$('#actionList').datagrid('reload');
			    				}
							}).showModal();
							
							//日志埋点
							track(_maq.push(['_action', '新增逻辑']));
							
						}
					},'-',{
						id:'editTrueFalse',
						text:'编辑逻辑',
						iconCls:'icon16-user-pencil',
						handler:function(){
							//日志埋点
							track(_maq.push(['_action', '基础数据管理_逻辑管理_编辑逻辑']));
							var selected = $('#actionList').datagrid('getSelections');
							if(selected.length!=1){
								$.messager.alert("操作提示","请选择一个逻辑!","waring");
								return;
							}else{
								top.dialog({
								    id: 'edit',
								    url : '${root}/truefalse/toEditWebConfigPage.do?flag='+selected[0].flag,
								    width: 700,
								    height:320,
									title:'编辑逻辑信息',
									onclose:function(){
										if(typeof(this.returnValue[0]) == "undefined")return;
				    					$('#actionList').datagrid('reload');
				    				}
								}).showModal();
							}
							//日志埋点
							track(_maq.push(['_action', '编辑逻辑']));
						}
					} ,'-',{
						id:'deleteTrueFalse',
						text:'删除逻辑',
						iconCls:'icon16-user-minus',
						handler:function(){
							//日志埋点
							track(_maq.push(['_action', '基础数据管理_逻辑管理_删除逻辑']));
							var selected = $('#actionList').datagrid('getSelections');
							if(selected.length <1){
								$.messager.alert("操作提示","请选择一调配置!","waring");
								return;
							}else{
								var flag = "";
								var count = 0;
								for(var i = 0;i <　selected.length; i++){
									flag += selected[i].flag+",";
									count++;
								}
								flag = flag.substring(0,flag.length-1);
								$.messager.confirm('删除确认','您确认删除此'+count+'个逻辑吗？',function(r){
	   								if (r){    
										var url = '${root}/truefalse/deleteConfigCondition.do';
										$.ajax({
											url: url,
											type: 'post',
											data: {
												'flag': flag
											},
											success: function(data){
												if(data.successful){
													$.messager.alert("操作提示", "删除逻辑成功!","info");
													$('#actionList').datagrid('reload');
												}else{
													$.messager.alert("操作提示", data.message,"error");
												}
											}
										});	        							    
	   								}    
								});				
							}
						}
					},
					],
					 
					onBeforeLoad:function(row,param){
					},
					onLoadSuccess:function(){
						 $(window).resize();
					}
				});
			}	
		};
		
		/* 查询操作 */
		var queryOption = {
				/* 查询 */
			doQuery:function(){
				$('#actionList').datagrid('load',{
					'flag':$("#flag").val(),
					'conditionType':$("#conditionType").val()
				});
			},
			/* 清空 */
			doClean:function(){
				//TODO $.fn.combotree("setValue","");失灵
				$('#flag').val('');
				this.doQuery();
			}
		}
	
		/* 初始化加载完成后执行 */
		$(function(){
			/* 注册键盘事件 */
		  	$('#searchTable').bind('keydown',function(event){
		  		if(event.keyCode == 13){
		  			doQuery();
		  		}
		  	});
			/* 初始化列表 */
		  	datagridTable.init();
		});
	</script>
	</rapid:override>
	
<jsp:include page="/WEB-INF/jsp/common/commonMould.jsp" />