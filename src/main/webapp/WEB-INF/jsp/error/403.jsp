<%@ page contentType="text/html; charset=UTF-8" %>
	<rapid:override name="title"><title>广州明动软件股份有限公司</title></rapid:override>
	
	<rapid:override name="otherCss">
    <link rel="stylesheet" href="${css}/error/error.css">
    </rapid:override> 
	
	<rapid:override name="content">
	<div id="cell"> 
		<div id="box">
	        <h1>403</h1>    
			<p>    
				<ul>    
					<li>操作权限不足</li>    
					<li>您没有权限访问噢...</li>  
				</ul>    
			</p>    
			<p class="right"><a>广州明动软件股份有限公司</a></p>     
	    </div>
    </div>
	</rapid:override>

<jsp:include page="/WEB-INF/jsp/common/commonMould.jsp" />
