<%@ page contentType="text/html; charset=UTF-8" %>
    <rapid:override name="title"><title>登录</title></rapid:override>
    
    <rapid:override name="otherCss">
    <link rel="stylesheet" href="${css}/login/login.css">
    </rapid:override> 
    
	<rapid:override name="content">
	<div class="header">
	    <div class="logo">
	        <a href="" class="logo-guide"><i class="guide-icon"></i>使用指南</a>
	    </div>
	    <div class="main clearfxi">
			<div class="container clearfxi">
		        <div id="login-tabs-item" class="fl">
				<span class="shadowbg"></span>
			        <div class="login-tab-inner">
			            <div class="login-header">
			                <ul class="list-inline login-tab clearfix">
			                    <li  class="fl on" style="width:100%"><a href="javascript:void(0);">账户登录</a><span class="on-arrow"></span></li>
			                </ul>
			            </div>
			            <div class="tab-containier">
			                <div class="tab-box">
			                    <form id="login_server" name = "login_server" method="post" action="j_security_check">
			                    <div class="field-item">
			                        <label for="">用户名：</label>
			                        <div class="form-field">
			                            <input class="easyui-validatebox  form-text"  type="text" name="j_username" id="j_username" data-options="required:true" />
			                        </div>
			                    </div>
			                    <div class="field-item">
			                        <label for="">密码：</label>
			                        <div class="form-field">
			                            <input class="easyui-validatebox  form-text" type="password"  name="j_password" id="j_password" data-options="required:true" />
			                        </div>
			                    </div>
			                    <div class="field-item">
			                        <label for="">验证码：</label>
			                        <div class="form-field" style="width: 76%;">
			                     		<input class="form-text" style="background-color: rgb(250, 255, 189);background-image: none;width: 130px;" type="text" id="checkCode" name="checkCode" />
										<img class="yzm" onclick="javaScript:loginOption.changeCode();" title="点击更换验证码" id="checkCodeImg" />
									</div>
		                        </div>
			                    <div class="field-item" style="margin-top:0;">
			                        <label class="re"><input type="checkbox" id="remember">记住用户名</label>
			                    </div>
			                	</form>
			                	<form id="tempForm" method="post" action="j_security_check">
			                		<input type="hidden" name="j_username" id="tempUsername"/>
			                		<input type="hidden" name="j_password" id="tempPassword"/>
			                		<input type="hidden" name="checkCode" id="tempCheckCode"> 
			                	</form>
			                    <div class="field-item">
			                        <div class="form-field-btn">
			                            <a href="javascript:void(0)" class=" btn btn-primary btn-large mr-10" onClick="javaScript:loginOption.submitForm();">登&nbsp;&nbsp;&nbsp;&nbsp;录</a>
			                            <a href="javascript:void(0)" class="btn btn-primary btn-default btn-large" style="float:right;" onClick="javaScript:loginOption.clearForm();">重&nbsp;&nbsp;&nbsp;&nbsp;置</a>
			
			                        </div>
			                    </div>
			                    <div class="mt-10 forget-link"></div>
			                </div>
			            </div>
			            <div style="font-size:14px; padding:5px 20px 20px;"><span style="color:#F00;">为了系统正常运行，请点击</span>
			            	<br><span style="color:#5f5f5f;">技术支持电话:</span><br/>
			            </div> 
					 </div>
				</div>
			</div>
		</div>
	</div>
	</rapid:override>
	
	<rapid:override name="otherJs">
	<script type="text/javascript" src="${resource}/cryptojs/aes.js"></script>
	<script type="text/javascript" src="${resource}/cryptojs/mode-ecb-min.js"></script>
	<script type="text/javascript" src="${resource}/cryptojs/base64.js"></script>
	<script type="text/javascript" src="${resource}/cryptojs/jquery.md5.js"></script>
	</rapid:override>
	
	<rapid:override name="footScript">
	<script type="text/javascript">
		
		/* 全局对象 */
		var loginConfig={
			userName : $("#j_username"),/* 账户名 */
			passWord : $("#j_password"),/* 密码 */
			checkCode : $("#checkCode"),/* 验证码 */
			checkCodeImg : $("#checkCodeImg"),/* 验证码图片 */
			remember : $("#remember"),/* 记住账户 */
			form : $("#login_server"),/* 表单 */
			cookieId : "49BAC005-7D5B-4231-8CEA-16939BEACD67",/* cookie标识 */
			isEncrypt : ${sessionScope.isEncrypt} /* 开启加密 */
		}
	
		/* 登录操作事件对象 */
		var loginOption={
			keyDown : function(obj,event){ /* 键盘事件 */
				if(event==this.submitForm){
					obj.bind("keydown",function(e){
						if(e.keyCode == 13){
							event();
						}
					});
					return false;
				}
			},
			clearForm : function() { /* 清空 */
				loginConfig.form.form('clear');
				loginConfig.userName.focus();
			    this.changeCode();
			},
			changeCode : function() { /* 刷新验证码 */
				loginConfig.checkCodeImg.attr("src","${root}/markCode.do?time=" + new Date().getMilliseconds());
			},
			closeDialog : function(obj){ /* 定时关闭弹窗或者按enter关闭弹窗 */
				setTimeout(function(){ obj.close();},3000);
			},
			checkNull : function(){ /* 使用帐号密码形式登录时，用户名、密码、验证码需不为空 */
				var objs=[loginConfig.userName,loginConfig.passWord,loginConfig.checkCode];
				var res=["用户名不能为空!","密码不能为空!","验证码不能为空!"];
				var flag = true;
				for(var i=0;i<objs.length;i++){
					if(objs[i].val() == ''){
						objs[i].focus();
						var dialogShow = top.dialog({title:'提示',content:res[i],width:200,height:100}).showModal();
						this.closeDialog(dialogShow);
						flag = false;
						break;
					}
				}
				return flag;
			},
			checkCode : function(){ /* 验证验证码 */
				var flag = false;
				$.ajax({
					   url: "${root}/checkCode.do?checkCode="+loginConfig.checkCode.val()+'&userCode='+encode64(loginConfig.userName.val()),
					   type: 'POST',
					   async:false,
					   success: function(msg){
							if(!msg){
								loginOption.changeCode();
								loginConfig.checkCode.val("").focus();
								var dialogShow = top.dialog({title:'提示',content:'验证码错误!',width:200,height:100}).showModal();
								loginOption.closeDialog(dialogShow);
							}
							flag =  msg;
						}
			   	});
				return flag;
			},
			encrypt : function(word){ /* 加密 */
				 var key = CryptoJS.enc.Utf8.parse('${sessionScope.webToken}');	
				 var srcs = CryptoJS.enc.Utf8.parse(word);
				 var encrypted = CryptoJS.AES.encrypt(srcs, key, {iv:key,mode:CryptoJS.mode.CBC,padding: CryptoJS.pad.Pkcs7});
		         return encrypted.toString();
		         
			},
			submitForm : function(){ /* 提交 */
				if(!loginOption.checkNull())
					return false;
				if(!loginOption.checkCode())
					return false;
				if(!loginConfig.form.form("validate"))
					return false;
				if(loginConfig.remember.is(":checked"))
					loginOption.setLastUser();
				if(loginConfig.isEncrypt){
					$("#tempUsername").val(encode64(loginConfig.userName.val()+"@"+loginConfig.checkCode.val()));
					$("#tempPassword").val($.md5(loginConfig.passWord.val()));
					$("#tempCheckCode").val(loginConfig.checkCode.val());
					$("#tempForm").submit();
				}else{
					loginConfig.form.submit();
				}
				
				//日志埋点
				loginTrack(_maq.push(['_action', 'login', loginConfig.userName]));
			},
			init : function(){ /* 初始化事件 */
				if("${error}"=="true"){
					var dialogShow = top.dialog({title:'提示',content:'您输入的用户名或密码不正确！请您重新输入。',width:200,height:100}).showModal();
					loginOption.closeDialog(dialogShow);
				}
				/* 注册键盘事件 */
				var objs=[loginConfig.userName,loginConfig.passWord,loginConfig.checkCode];
				for(var i=0;i<objs.length;i++){
					this.keyDown(objs[i],this.submitForm);
				}
				/* 清除表单 */
				this.clearForm();
				/* 默认勾选记住账户 */
				loginConfig.remember.prop("checked",true);
				loginConfig.userName.val(this.getLastUser());
			},
		    setLastUser : function() {/* 覆盖最后的用户名 */
		    	var exdate=new Date()
		    	exdate.setDate(exdate.getDate()+7);/* 默认记住七天 */
		    	document.cookie = loginConfig.cookieId +"="+loginConfig.userName.val()+";expires="+exdate.toGMTString();
		    },
			getLastUser : function(){/* 获取最后的用户名 */
				var c_name = loginConfig.cookieId;
				if (document.cookie.length>0){
				  	c_start=document.cookie.indexOf(c_name + "=");
				  	if (c_start!=-1){ 
					    c_start=c_start + c_name.length+1 ;
					    c_end=document.cookie.indexOf(";",c_start);
					    if (c_end==-1) c_end=document.cookie.length;
					    return unescape(document.cookie.substring(c_start,c_end))
				    } 
			  	}
				return "";
			}
		}
		
		/* 初始化事件 */
		$(function() {
			loginOption.init();
		});
		
		
		//用户登录埋点
		function loginTrack(){		
			var ma = document.createElement('script'); ma.type = 'text/javascript'; ma.async = true;
		    ma.src = ('http:' == document.location.protocol ? 'https://'+window.location.host+'/ApprUserActionLog' : ('http://'+window.location.host+'/ApprUserActionLog')) + '/static/js/loginma.js';
		    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ma, s);
		}
	</script>
  	</rapid:override>
  	<rapid:override name="loginTrack">
	</rapid:override>
<jsp:include page="/WEB-INF/jsp/common/commonMould.jsp" />