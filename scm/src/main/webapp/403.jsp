<%@page contentType="text/html;charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <title>403</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath %>res/bui/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath %>res/bui/css/page-min.css" rel="stylesheet" type="text/css" />  
 
 </head>
 <body>
  
  <div class="container">
    <div class="row">
      <div class="span10">
        <div class="tips tips-large tips-warning">
          <span class="x-icon x-icon-error">×</span>
          <div class="tips-content">
            <h2>拒绝访问</h2>
            <p class="auxiliary-text">
            没有进入此页面的权限
            </p>
          </div>
        </div>
      </div> 
    </div>
  </div>
	</body>
</html>