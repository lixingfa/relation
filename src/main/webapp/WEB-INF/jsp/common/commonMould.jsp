<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head> 
<%-- 标题 --%>
	<rapid:block name="title"></rapid:block>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="renderer" content="webkit">
	<rapid:block name="meta"></rapid:block>
<%-- 初始化css引入 --%>
	<rapid:block name="startCss">
    <link rel="stylesheet" href="${resource}/easyui/themes/default/easyui.css">
    <link rel="stylesheet" href="${resource}/easyui/themes/base.css">
    <link rel="stylesheet" href="${resource}/easyui/themes/ui.css">
    <link rel="stylesheet" href="${resource}/easyui/themes/icon.css">
    <link rel="stylesheet" href="${resource}/easyui/themes/color.css">
    <link rel="stylesheet" href="${resource}/artdialog/artDialog.css">
    <link rel="stylesheet" href="${resource}/font-awesome/font-awesome.min.css">
	</rapid:block>
<%-- 其他css引入或者编写 --%>
	<rapid:block name="otherCss"></rapid:block> 
</head>
<body <rapid:block name="body"></rapid:block>>
	<%-- 内容 --%>
	<rapid:block name="content"></rapid:block>
<%-- 初始化js引入 --%>
	<rapid:block name="startJs">
	<script type="text/javascript" src="${resource}/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${resource}/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${resource}/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${resource}/artdialog/artDialog.js"></script>
	<script type="text/javascript" src="${resource}/jqueryForm/jquery.form.js"></script>
	<script type="text/javascript" src="${js}/log/userActionLog.js?usercode=<%=request.getRemoteUser()%>"></script>
	<!--[if lt IE 9]>
	<script type="text/javascript" src="${resource}/ieh5/html5shiv-printshiv.js"></script>
	<script type="text/javascript" src="${resource}/ieh5/respond.min.js"></script>
	<script type="text/javascript" src="${resource}/ieh5/excanvas.js"></script>
    <![endif]-->
	
	</rapid:block>
<%-- 其他js引入 --%>
	<rapid:block name="otherJs"></rapid:block>
<%-- 脚底js自定义编写 --%>
	<rapid:block name="footScript"></rapid:block>   
	</body>
</html>	
