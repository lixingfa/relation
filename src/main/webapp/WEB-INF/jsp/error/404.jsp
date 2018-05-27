<%@ page contentType="text/html; charset=UTF-8" %>
	<rapid:override name="title"><title>格兰科技</title></rapid:override>
	
	<rapid:override name="otherCss">
    <link rel="stylesheet" href="${css}/error/error.css">
    </rapid:override> 
	
	<rapid:override name="content">
	<div id="cell"> 
		<div id="box">
	        <h1>404</h1>    
			<p>    
				<ul>    
					<li>页面未找到！</li>    
					<li>抱歉，页面好像去火星了~</li>  
				</ul>    
			</p>    
			<p class="right"><a>格兰科技</a></p>       
	    </div>
    </div>
	</rapid:override>

<jsp:include page="/WEB-INF/jsp/common/commonMould.jsp" />
