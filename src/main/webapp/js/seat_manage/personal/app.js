var personalSeatManageApp = angular.module("personalSeatManageApp", [ 'ngRoute', 'ngAnimate', 'globalLanguageApp', 'globalConstantApp' ]);
personalSeatManageApp.controller('seatManageController', function($scope) {
	$scope.subPages = [ {
		"name" : "席位状态",
		"page" : "seat_manage/shared/status"
	}, {
		"name" : "基本信息",
		"page" : "seat_manage/personal/info"
	}, {
		"name" : "银行账户",
		"page" : "seat_manage/personal/account"
	}, {
		"name" : "市场准入及权限管理",
		"page" : "seat_manage/personal/auth"
	}, {
		"name" : "席位等级",
		"page" : "seat_manage/personal/grade"
	}, {
		"name" : "关联席位信息",
		"page" : "seat_manage/personal/relationship"
	}

	];
	$scope.currentSubPage = $scope.subPages[0];
	$scope.setCurrentSubPage = function(subPage) {
		subPage.page = subPage.page.split("?")[0] + '?random=' + parseInt(Math.random()*10000+1); 
		$scope.currentSubPage = subPage;
	}
});


personalSeatManageApp.factory('sessionTimeoutInterceptor', ['$log', function($log) {
    $log.debug('$log is here to show you that this is a regular factory with injection');

    var sessionTimeoutInterceptor = {
		responseError: function(response) {
			if(response.status == 419 ){//session is timeout
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : "失败",
                    message : "会话超时,请重新登录！"
                });
				//如果超时就处理 ，指定要跳转的页面
				window.location.replace("/" + window.location.pathname.split("/")[1] + "/index");
			}else{
				return response;
			}
		}
    };

    return sessionTimeoutInterceptor;
}]);


personalSeatManageApp.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push('sessionTimeoutInterceptor');
}]);
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