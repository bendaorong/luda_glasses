<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html ng-app="seatManageApp">
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
<title>席位管理</title>

<!-- Bootstrap -->
<link href="bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body ng-controller="seatManageController">
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
					<a class="navbar-brand" href="#"><span
						class="glyphicon glyphicon-usd" aria-hidden="true"></span>Richmen</a>
				</div>
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						<!-- <li>
							<div class="btn-group navbar-btn">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false">
									<img class="select-flag-image"
										src="resources/images/flags/china.png" alt="china">中国 <span
										class="caret"></span>
								</button>
								<ul class="dropdown-menu">
									<li><a href="#"><img class="select-flag-image"
											src="resources/images/flags/china.png" alt="China">中国</a></li>
									<li><a href="#"><img class="select-flag-image"
											src="resources/images/flags/usa.png" alt="USA">美国</a></li>
									<li><a href="#"><img class="select-flag-image"
											src="resources/images/flags/hong_kong.png" alt="Hongkong">香港</a></li>
								</ul>
							</div>
						</li> -->

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


						<!--  Only to Separate two parts -->
						<li>
							<p class="navbar-text"></p>

						</li>
						<li>
							<button type="button"
								class="btn btn-default navbar-btn btn-success">
								<span class="glyphicon glyphicon-headphones" aria-hidden="true"></span>在线客服
							</button>
						</li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<!-- Language -->
						<!-- <li><form class="navbar-form">
								<select class="form-control">
									<option>简体中文</option>
									<option>English</option>
									<option>繁體中文</option>
								</select>
							</form></li>

						<li> -->
						<li class="dropdown"><a href="javascript:void(0)"
							class="dropdown-toggle" data-toggle="dropdown">简体中文<b
								class="caret"></b></a>
							<ul class="dropdown-menu" aria-labelledby="dLabel">
								<li><a href="#">简体中文</a></li>
								<li><a href="#">English</a></li>
								<li><a href="#">繁體中文</a></li>
							</ul></li>
						<li class="dropdown"><a href="javascript:void(0)"
							class="dropdown-toggle" data-toggle="dropdown">${sessionScope.sessionInfo.lastName}
								${sessionScope.sessionInfo.firstName}<b class="caret"></b>
						</a>
							<ul class="dropdown-menu user-menu" aria-labelledby="dLabel">
								<li>登陆账号 : <strong>${sessionScope.sessionInfo.seatNumber}</strong></li>
								<li>席位类别 : <strong>${sessionScope.sessionInfo.seatType}</strong></li>
								<li>登陆时间 :</li>
								<li>上次登陆 :</li>
								<li><a href="logout">安全退出</a></li>
							</ul></li>

						<li><p class="navbar-text">
								<a class="navbar-link" href="#">通知中心<span class="badge">42</span></a>
							</p></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</nav>


		<ul class="nav nav-tabs nav-menu nav-justified">
			<li role="presentation"><a href="home">首页</a></li>
			<li role="presentation" class="active"><a href="#">席位管理</a></li>
			<li role="presentation"><a href="#">资产负债管理</a></li>
			<li role="presentation"><a href="#">业务操作</a></li>
			<li role="presentation"><a href="#">重要公告</a></li>

		</ul>
		<hr>
		<ol class="breadcrumb">
			<li><a href="home">首页</a></li>
			<li><a href="javascript:void(0)">席位管理</a></li>
			<li id="breadcrum_leaf" class="active">席位状态</li>
		</ol>

		<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
			<!-- <ul class="nav nav-sidebar  nav-pills nav-stacked">
				<li><a href="#">席位状态</a></li>
				<li class="active"><a href="#">基本信息</a></li>
				<li><a href="#">银行账户</a></li>
				<li><a href="#">状态及权限管理</a></li>
				<li><a href="#">等级信息</a></li>
				<li><a href="#">关联会员信息</a></li>
			</ul> -->

			<div class="list-group">
				<a ng-repeat="subPage in subPages" href="javascript:void(0)" ng-class="{'active' : subPage.page === currentSubPage}"
					class="list-group-item" ng-click="setCurrentSubPage(subPage.page)">{{subPage.name}}</a>
			</div>
		</div>
		<div class="col-sm-9 col-md-9 ">
			<!-- <div id="content"></div> -->
			<ng-include src="currentSubPage"></ng-include>
		</div>


	</div>
	<footer class="footer">
		<div class="container">
			<hr>
			<p class="text-muted">版权所有 © Richmen 2015</p>
		</div>
	</footer>


	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
	<script src="angular-1.2.28/angular.min.js"></script>
	<script src="angular-1.2.28/angular-route.min.js"></script>
	<script>
		var seatManageApp = angular.module("seatManageApp", []);
		seatManageApp.controller('seatManageController', function($scope) {
			$scope.subPages = [ {
				"name" : "席位状态",
				"page" : "seat_manage/status"
			}, {
				"name" : "基本信息",
				"page" : "seat_manage/info"
			}, {
				"name" : "银行账户",
				"page" : "seat_manage/account"
			}, {
				"name" : "市场准入及权限管理",
				"page" : "seat_manage/auth"
			}, {
				"name" : "席位等级",
				"page" : "seat_manage/grade"
			}, {
				"name" : "关联席位信息",
				"page" : "seat_manage/relationship"
			}

			];
			$scope.currentSubPage = "seat_manage/status";
			$scope.setCurrentSubPage = function(subPage) {
				$scope.currentSubPage = subPage;
			}
		});
	</script>
	<!-- 	<script>
		$(document).ready(function() {
			$("#content").load("seat_manage/status");

			$("a.list-group-item").click(function() {
				if (!$(this).hasClass("active")) {
					$("a.list-group-item.active").removeClass("active");
					$(this).addClass("active");
					$("#content").load("seat_manage/" + $(this).attr("data-page")); 
					$("#breadcrum_leaf").text($(this).text());
				}
			});

		});
	</script> -->
	<script src="js/seat_manage/controller/auth.js"></script>
</body>
</html>