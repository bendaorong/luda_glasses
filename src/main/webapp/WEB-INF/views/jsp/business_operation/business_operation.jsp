<%@ page language="java" contentType="text/html; charset=utf-8"
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
<title>{{'BUSINESS_OPERATION'|translate}}</title>
<!-- Bootstrap -->
<link href="bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<link href="css/ng-table.min.css" rel="stylesheet">
<link href="bootstrap-3.3.5-dist/css/bootstrap-dialog.min.css"
	rel="stylesheet">

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
					<a class="navbar-brand" href="/"><span
						class="glyphicon glyphicon-usd" aria-hidden="true"></span>Richmen</a>
				</div>
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav">

						<li class="dropdown"><a href="javascript:void(0)"
							class="dropdown-toggle" data-toggle="dropdown"><img
								class="select-flag-image" src="resources/images/flags/china.png"
								alt="China">中国<b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="#"><img class="select-flag-image"
										src="resources/images/flags/china.png" alt="China">中国</a></li>
								<li role="separator" class="divider"></li>
								<li><a href="#"><img class="select-flag-image"
										src="resources/images/flags/usa.png" alt="USA">美国</a></li>
								<li role="separator" class="divider"></li>
								<li><a href="#"><img class="select-flag-image"
										src="resources/images/flags/hong_kong.png" alt="Hongkong">香港</a>
								</li>
							</ul></li>
						<li>
							<p class="navbar-text"></p>
						</li>
						<li>
							<button type="button"
								class="btn btn-default navbar-btn btn-success">
								<span class="glyphicon glyphicon-headphones" aria-hidden="true"></span>{{'ONLINE_SERVICE'|translate}}
							</button>
						</li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<language-switch></language-switch>
						<jsp:include page="../headerDetail.jsp"></jsp:include>
						<li><p class="navbar-text">
								<a class="navbar-link" href="#">{{'NOTIFICATION'|translate}}<span
									class="badge">42</span></a>
							</p></li>
					</ul>
				</div>
			</div>
		</nav>


		<nav-tab current-tab="BUSINESS_OPERATION"></nav-tab>
		<hr>
		<sub-nav></sub-nav>

	</div>
	<%@ include file="../footer.jsp"%>

	<%@ include file="../loading.html"%>
	<script src="js/jquery.min.js"></script>
	<script src="bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
	<script src="angular-1.2.28/angular.min.js"></script>
	<script src="angular-1.2.28/angular-route.min.js"></script>
	<script src="angular-1.2.28/angular-animate.min.js"></script>
	<script src="angular-1.2.28/angular-translate.min.js"></script>
	<script
		src="angular-1.2.28/angular-translate-loader-static-files.min.js"></script>
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
	<script src="app/business_operation/controllers/fixTermDepositProductManage.js"></script>
	<script src="app/business_operation/controllers/fixTermDepositRateUpdate.js"></script>
	<script src="app/business_operation/controllers/shortTermCloseEndProductManage.js"></script>
  <script src="app/business_operation/controllers/fixInterestRate4VariableRateProduct.js"></script>
  <script src="app/business_operation/controllers/openEndedFundProductManage.js"></script>
  <script src="app/business_operation/controllers/openEndedFundProfitDistribution.js"></script>
  <script src="app/business_operation/controllers/openEndedFundNetAssetValue.js"></script>
  <script src="app/business_operation/controllers/openEndedFundClose.js"></script>
  <script src="app/business_operation/controllers/openEndedFundDismissal.js"></script>
  <script src="app/business_operation/controllers/closedEndedFundProductManage.js"></script>
  <script src="app/business_operation/controllers/closedEndedFundNetAssetValue.js"></script>
  <script src="app/business_operation/controllers/closedEndedFundOpen.js"></script>
  <script src="app/business_operation/controllers/closedEndedFundDismissal.js"></script>
  <script src="app/business_operation/controllers/closedEndedFundProfitDistribution.js"></script>
  <script src="app/business_operation/controllers/companyBondProductManage.js"></script>
  <script src="app/business_operation/controllers/companyBondListSuspend.js"></script>
  <script src="app/business_operation/controllers/companyShareProductManage.js"></script>
  <script src="app/business_operation/controllers/CompanyShareProductSharePayment.js"></script>
  <script src="app/business_operation/controllers/companyShareListSuspend.js"></script>
  <script src="app/business_operation/directives/lengthLimit.js"></script>
  <script src="app/business_operation/controllers/ftdListSuspend.js"></script>
  <script src="app/business_operation/controllers/shortTermCloseEndListSuspend.js"></script>
  <script src="app/business_operation/controllers/openEndedFundListSuspend.js"></script>
  <script src="app/business_operation/controllers/closeEndedFundListSuspend.js"></script>
  <script src="app/business_operation/controllers/arListSuspend.js"></script>
  <script src="app/business_operation/controllers/arProductManage.js"></script>
  <script src="app/business_operation/controllers/apProductManage.js"></script>
  <script src="app/business_operation/controllers/cashInOut.js"></script>
  <script src="app/business_operation/controllers/commonCashInOut.js"></script>
  <script src="app/business_operation/controllers/cashInOutHistory.js"></script>
  <script src="app/business_operation/controllers/commonCashInOutHistory.js"></script>
  <script src="bootstrap-3.3.5-dist/js/bootstrap-dialog.min.js"></script>
	<script src="angular-1.2.28/ng-table.min.js"></script>
	<script src="angular-1.2.28/angular-route.min.js"></script>
	<script src="js/angularjs-dropdown-multiselect.js"></script>
	<script>
		var route = "app/business_operation/route/${sessionScope.seatType}.json";
		var role = "${sessionScope.roleCode}";
		var ownSeatNumber = "${sessionScope.ownSeatNumber}";
		
	</script>

</body>
</html>