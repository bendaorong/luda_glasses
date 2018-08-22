.<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html ng-app="businessOperationApp">
<head>
<meta charset="utf-8">
<!--
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>鹭达眼镜管理</title>
<!-- Bootstrap -->
<link href="bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<link href="css/ng-table.min.css" rel="stylesheet">
<link href="bootstrap-3.3.5-dist/css/bootstrap-dialog.min.css" rel="stylesheet">
<link href="css/jquery.searchableSelect.css" rel="stylesheet">

</head>

<body ng-controller="businessOperationController">
	<div class="container">
		<nav class="navbar navbar-top navbar-default">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#navbar" aria-expanded="false"
						aria-controls="navbar">
						<span class="sr-only"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span> <span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="/luda_glasses/index">鹭达眼镜</a>
				</div>
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav navbar-right">
						<!-- <language-switch></language-switch> -->
						<li class="dropdown"><a href="javascript:void(0)"
							class="dropdown-toggle" data-toggle="dropdown">${sessionScope.sessionInfo.adminName}<b class="caret"></b>
						</a>
							<ul class="dropdown-menu user-menu" aria-labelledby="dLabel">
								<li>手机号:<strong>${sessionScope.sessionInfo.mobileNumber}</strong></li>
								<li>角色:<strong>${sessionScope.sessionInfo.adminRoleModel.roleName}</strong></li>
								<li>门店:<strong>${sessionScope.sessionInfo.storeModel.storeName}</strong></li>
								<%--<li><a href="">修改密码</a></li>--%>
								<li><a href="logout">安全退出</a></li>
							</ul></li>
						<%--<li><p class="navbar-text">--%>
								<%--<a class="navbar-link" href="#">通知中心<span--%>
									<%--class="badge">42</span></a>--%>
							<%--</p></li>--%>
					</ul>
				</div>
			</div>
		</nav>
		<hr>
		<sub-nav></sub-nav>
	</div>
	<%@ include file="footer.jsp"%>

	<%@ include file="loading.html"%>
	<script src="js/jquery.min.js"></script>
	<script src="js/jquery.searchableSelect.js"></script>
	<script src="bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
	<script src="angular-1.2.28/angular.min.js"></script>
	<script src="angular-1.2.28/angular-route.min.js"></script>
	<script src="angular-1.2.28/angular-animate.min.js"></script>
	<script src="angular-1.2.28/angular-translate.min.js"></script>
	<script src="angular-1.2.28/ng-table.min.js"></script>
	<script src="angular-1.2.28/angular-translate-loader-static-files.min.js"></script>
	<script src="js/session_timeout.js"></script>
	<script src="bootstrap-3.3.5-dist/js/bootstrap-dialog.min.js"></script>
	<script src="js/lodash.min.js"></script>
	<script src="app/global/common/global_constant.js"></script>
	<script src="app/global/common/global_language.js"></script>
	<script src="app/global/directive/global_nav.js"></script>
	<script src="app/global/global.js"></script>
	<script src="app/global/directive/global_languageSwitch.js"></script>
	<script src="app/global/directive/global_subNav.js"></script>
	<script src="app/business_operation/app.js"></script>
	<script src="bootstrap-3.3.5-dist/js/bootstrap-dialog.min.js"></script>

	<!-- 用户管理 -->
	<script src="app/business_operation/controllers/adminUser/AuthorizationManage.js"></script>
	<!-- 门店管理 -->
	<script src="app/business_operation/controllers/store/storeManage.js"></script>
	<!-- 供应商管理 -->
	<script src="app/business_operation/controllers/supplier/supplierManage.js"></script>
	<!-- 商品管理 -->
	<script src="app/business_operation/controllers/materiel/materielManage.js"></script>
	<!-- 字典管理 -->
	<script src="app/business_operation/controllers/dictionary/goodsType/goodsTypeManage.js"></script>
	<script src="app/business_operation/controllers/dictionary/goodsColor/goodsColorManage.js"></script>
	<script src="app/business_operation/controllers/dictionary/goodsBrand/goodsBrandManage.js"></script>
	<script src="app/business_operation/controllers/dictionary/dictManage.js"></script>
	<!-- 库存管理 -->
	<script src="app/business_operation/controllers/inventory/mard/mardManage.js"></script>
	<script src="app/business_operation/controllers/inventory/purchaseOrder/purchaseOrderManage.js"></script>
	<script src="app/business_operation/controllers/inventory/purchaseOrder/batch/batchPurchaseOrderManage.js"></script>
	<script src="app/business_operation/controllers/inventory/purchaseOrder/refund/purchaseRefundOrderManage.js"></script>
	<script src="app/business_operation/controllers/inventory/inventoryVerification/inventoryVerificationManage.js"></script>
	<script src="app/business_operation/controllers/inventory/transfer/transferManage.js"></script>
	<!-- 销售管理 -->
	<script src="app/business_operation/controllers/sales/sale/salesOrderManage.js"></script>
	<script src="app/business_operation/controllers/sales/refund/refundOrderManage.js"></script>
	<!--客户管理-->
	<script src="app/business_operation/controllers/customer/customerManager.js"></script>
	<!-- 统计报表 -->
	<script src="app/business_operation/controllers/statistics/sale/saleStatisticsManage.js"></script>
	<script src="app/business_operation/controllers/statistics/purchase/purchaseStatisticsManage.js"></script>


	<script src="angular-1.2.28/angular-route.min.js"></script>
	<script src="js/angularjs-dropdown-multiselect.js"></script>


	<!-- <script src="angular-datepicker/angular-datepicker.min.js"></script>   -->

	<script>
		// 默认登录用户权限
		var route = "app/business_operation/route/Svc.json";

		// 登录用户角色
		var roleCode = sessionStorage.getItem("roleCode");
		// 根据用户角色加载用户操作权限
		if(roleCode != null && roleCode != ''){
			route = "app/business_operation/route/Svc_"+roleCode+".json";
		}
	</script>
</body>
</html>