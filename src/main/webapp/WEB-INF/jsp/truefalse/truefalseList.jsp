	<%@ page contentType="text/html; charset=UTF-8" %>
	<rapid:override name="title"><title>逻辑判断</title></rapid:override>
	<rapid:override name="body">class="easyui-layout"</rapid:override>
	<rapid:override name="content">
		<div id="north" region="north" style="height:70px; border:0; border-bottom:1px solid #ddd; padding:10px 20px 0px 0px; overflow:hidden;">
			<table id="searchTable" class="search-table" border="0">
				<tr>
					<td width="100" style="text-align: right">主键：</td>
					<td><input type="text" name="uuid" class="input-text" id="uuid" style="width:80%;"></td>
					<td width="100" style="text-align: right">条件：</td>
					<td><input type="text" name="condition" class="input-text" id="condition"/></td>
					<td width="100" style="text-align: right">所在页面：</td>
					<td><input type="text" name="pageUrl" class="input-text" id="pageUrl"/></td>
					<td width="100" style="text-align: right">占位符：</td>
					<td><input type="text" name="flag" class="input-text" id="flag"/></td>
					<td>
						<span>
						<input class="ui-btn"  type="button" value="查 询" onclick="javascript: doQuery();">
						</span>
						&nbsp;
						<span>
						<input class="ui-btn" type="button" value="清空条件" onClick="javascript:doClean();">
						</span>
						&nbsp;
						<span>
						<a href="/WebConfig/truefalse/toShowWebConfigPageList.do" target="_blank">二级条件配置</a>
						</span>
					</td>
				</tr>		
				
			</table>
		</div>
		<div region="center" split="true" style="border:0;background:#eee;">
			<table id="dateList"></table>
   		</div>
	</rapid:override>
	
	<rapid:override name="footScript">
	<script type="text/javascript">
	
		/* 表格对象 */
		var datagridTable={
			init:function(){
				$('#dateList').datagrid({
					iconCls:'icon16-card-pencil',
					fit: true,
					nowrap: false,//自动换行 
					autoRowHeight: true,
					animate:true,
					scrollbarSize: 0,
					striped: true,
					collapsible:true,
					fitColumns:true,
					singleSelect:false,
					rownumbers:true,
					pagination:true,
					pageSize: 100,//每页显示的记录条数，默认为25
			        pageList: [100,200,400,800,1600],//可以设置每页记录条数的列表
					url:'${root}/truefalse/loadListTrueFalse.do',
					columns:[[
						{field:'uuid', 					checkbox: true},
						{field:'pageUrl',				title:'所在页面',			width:200},
						{field:'flag',			        title:'占位符',		width:250,
							formatter:function(val,row){
								if(row.parentFlag != null){
									return '<span title="父标签：'+ row.parentFlag +'">' + val + '</span>';
								}else{
									return val;
								}
							}
						},
						{field:'condition',				title:'条件',			width:470},
						{field:'tfName',			title:'配置名称',			width:150},
						{field:'tips',			title:'描述',			width:300},
						{field:'status',			title:'是否启用',			width:40,
							formatter:function(val){
								if(val=='1'){
									return '是';
								}else{
									return '否';
								}
							}
						},
						{field:'projectName',			title:'所属项目',			width:120}
						/*{field:'createTime',			title:'创建时间',			width:100,
										formatter:function(val){
											var date=new Date(val);
											var year=date.getFullYear();
											var month=date.getMonth()+1;
											var day=date.getDate();
											return year+'-'+month+'-'+day;
										}
									}*/
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
							    url:'${root}/truefalse/toAddPage.do',
							    width: 700,
								height:400,
								title:'新增逻辑信息',
								onclose:function(){									
			    					$('#dateList').datagrid('reload');
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
							var selected = $('#dateList').datagrid('getSelections');
							if(selected.length!=1){
								$.messager.alert("操作提示","请选择一个逻辑!","waring");
								return;
							}else{
								top.dialog({
								    id: 'edit',
								    url : '${root}/truefalse/toEditPage.do?uuid='+selected[0].uuid,
								    width: 700,
								    height:400,
									title:'编辑逻辑信息',
									onclose:function(){
				    					$('#dateList').datagrid('reload');
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
							var selected = $('#dateList').datagrid('getSelections');
							if(selected.length <1){
								$.messager.alert("操作提示","请选择一个用户!","waring");
								return;
							}else{
								var uuid = "";
								var count = 0;
								for(var i = 0;i <　selected.length; i++){
									uuid += selected[i].uuid+",";
									count++;
								}
								uuid = uuid.substring(0,uuid.length-1);
								$.messager.confirm('删除确认','您确认删除此'+count+'个逻辑吗？',function(r){
	   								if (r){    
										var url = '${root}/truefalse/deleteTrueFalse.do';
										$.ajax({
											url: url,
											type: 'post',
											data: {
												'uuid': uuid
											},
											success: function(data){
												if(data.successful){
													$.messager.alert("操作提示", "删除逻辑成功!","info");
													$('#dateList').datagrid('reload');
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
			function doQuery(){
				$('#dateList').datagrid('load',
				{
					'uuid':$('#uuid').val(),
					'condition':$('#condition').val(),
					'pageUrl':$('#pageUrl').val(),
					'flag':$('#flag').val()
				});
			}
			/* 清空 */
			function doClean(){
				//TODO $.fn.combotree("setValue","");失灵
				$('#uuid').val('');
				$('#condition').val('');
				$('#pageUrl').val('');
				$('#flag').val();
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