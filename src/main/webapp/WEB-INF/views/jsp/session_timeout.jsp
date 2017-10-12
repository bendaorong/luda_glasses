<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
</head>
<body>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>

<script>

$(document).ready(function() {
	alert("会话超时,请重新登录！");
    window.location.replace("/" + window.location.pathname.split("/")[1] + "/login");  
});

</script>
</body>
</html>