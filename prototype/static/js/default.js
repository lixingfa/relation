//替换对话按钮
$.messager.defaults = { ok: "是", cancel: "否" }; 
//下拉选择必填
$.extend($.fn.validatebox.defaults.rules, {			
			    selectNeed: {
			       validator: function(value, param) {
			           return $(param[0]).find("option:contains('"+value+"')").val() != '';  
			       }
			   }
			});

//关闭窗体
function closeWindow(id){
				$('#' + id).window('close');
				$('#' + id).find("input").each(function(index,obj){
					$(obj).val("");
				});
				$('#' + id).find("input.easyui-textbox").each(function(index,obj){
					$(obj).textbox('setValue',"");
				});
				$('#' + id).find("select").each(function(index,obj){
					$(obj).combobox('setValue',"");
				});
			}