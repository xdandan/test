<%@page contentType="text/html;charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
     <link  rel="stylesheet"  href="<%=basePath %>res/js/resources/css/ext-all-neptune.css"/>

     <script type="text/javascript" src="<%=basePath %>res/js/ext-all.js"></script>
     <script type="text/javascript" src="<%=basePath %>res/js/ext-theme-neptune.js"></script>
    <script type="text/javascript" src="<%=basePath %>res/js/locale/ext-lang-zh_CN.js"></script>
    <!-- <script type="text/javascript" src="<%=basePath %>res/js/ux/DataTip.js"></script> -->
