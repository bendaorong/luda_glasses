<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html ng-app="personalLoginApp">
<head>
<meta charset="utf-8">
<!-- 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>用户登录</title>
<!-- Bootstrap -->
<link href="bootstrap-3.3.5-dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<script src="js/jquery.min.js"></script>
<script src="bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
<script src="bootstrap-3.3.5-dist/js/bootstrap-dialog.min.js"></script>

</head>
<body>
	<div class="container" ng-controller="loginController">
		<nav class="navbar navbar-default">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#navbar_menu"
						aria-expanded="false" aria-controls="navbar">
						<span class="sr-only"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span> <span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="../"><span
						 aria-hidden="true"></span>用户登录</a>
				</div>
			</div>
		</nav>
		<div class="col-xs-12">
			<div class="col-xs-12 col-sm-12 col-md-12">
			<div class="alert alert-danger alert-dismissible fade in" role="alert" ng-show="errorMsg">
				<p>{{errorMsg}}</p>
			</div>
				<form name="loginForm" method="post">
					<div class="form-horizontal">
						<div class="form-group" id="phone_form_group">
							<label for="password"
								class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">手机号</label>
								<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
									<input type="tel" class="form-control" id="phone_number"
										ng-model="login.mobileNumber" required name="mobileNumber"
										placeholder="手机号">
								</div>
							</div>
							<div class="form-group" id="phone_form_group">
							<label for="password"
								class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">用户名</label>
								<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
									<input type="text" class="form-control" id="adminName"
										ng-model="login.adminName" required name="adminName"
										placeholder="用户名">
								</div>
							</div>
					
						<div class="form-group">
							<label for="password"
								class="col-lg-3 col-md-3 col-sm-3 col-xs-12 control-label">密码</label>
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<input type="password" class="form-control" id="password"
									ng-model="login.loginPassword" required name="password"
									placeholder="密码">
							</div>
						</div>
						<div class="form-group">
							<div class="center-block">
								<button id="btn_register_form_submit" type="button"
									ng-click="submit()" class="btn btn-danger center-block">登录</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

	<%@ include file="footer.jsp"%>
	<%@ include file="loading.html"%>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
	<script src="angular-1.2.28/angular.min.js"></script>
	<script src="angular-1.2.28/angular-translate.min.js"></script>
	<script
		src="angular-1.2.28/angular-translate-loader-static-files.min.js"></script>
	<script src="js/login/personal/login.js"></script>
	<script src="js/global_language.js"></script>
</body>
</html>