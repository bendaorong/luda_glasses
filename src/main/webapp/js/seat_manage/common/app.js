var commonSeatManageApp = angular.module("commonSeatManageApp", [ 'ngRoute', 'ngAnimate', 'globalLanguageApp' ]);
commonSeatManageApp.controller('seatManageController', function($scope) {
	$scope.subPages = [ {
		"name" : "席位状态",
		"page" : "seat_manage/shared/status"
	}, {
		"name" : "席位信息",
		"page" : "seat_manage/common/info"
	}, {
		"name" : "市场准入及权限管理",
		"page" : "seat_manage/common/auth"
	}, {
		"name" : "席位等级",
		"page" : "seat_manage/shared/grade"
	}, {
		"name" : "关联席位信息",
		"page" : "seat_manage/common/partnerRelationship"
	} ];
	$scope.isLoading = false;
	$scope.currentSubPage = $scope.subPages[0];
	$scope.setCurrentSubPage = function(subPage) {
		subPage.page = subPage.page.split("?")[0] + '?random=' + parseInt(Math.random() * 10000 + 1);
		$scope.currentSubPage = subPage;
	}
	$scope.$on('$includeContentRequested', function(e) {
		$scope.isLoading = true;
		console.log("request");
	});
	$scope.$on('$includeContentLoaded', function(e) {
		$scope.isLoading = false;
		console.log('loaded');
	});
});
/*
 * angular.module("seatManageApp").config([ '$routeProvider',
 * function($routeProvider) { $routeProvider.when('/', { templateUrl :
 * 'seat_manage/status' }).when('/status', { templateUrl : 'seat_manage/status'
 * }).when('/info', { templateUrl : "seat_manage/info" }).when('/account', {
 * templateUrl : "seat_manage/account" }).when('/auth', { templateUrl :
 * "seat_manage/auth", controller : "authController" }).when('/grade', {
 * templateUrl : "seat_manage/grade" }).when('/relationship', { templateUrl :
 * "seat_manage/relationship" }) } ]);
 */