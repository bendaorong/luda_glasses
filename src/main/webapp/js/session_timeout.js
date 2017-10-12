	$(document).ready(function() {
		
		  $.ajaxSetup({  
		         complete:function(XMLHttpRequest){
		        	 var status = XMLHttpRequest.status;
                     if(status == 419){//自定义状态码，表示session timeout   		        	 
		        	 alert("会话超时,请重新登录！");
		               //如果超时就处理 ，指定要跳转的页面  
		                 window.location.replace("/" + window.location.pathname.split("/")[1] + "/login");  
		               }  
		            }  
		       });
		
});