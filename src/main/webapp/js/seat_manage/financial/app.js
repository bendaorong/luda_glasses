var financialSeatManageApp = angular.module("financialSeatManageApp", [ 'ngAnimate', 'globalLanguageApp', 'globalConstantApp', 'angularjs-dropdown-multiselect']);
financialSeatManageApp.value("subPages", [ {
	"name" : "SEAT_STATUS",
	"page" : "seat_manage/shared/status"
}, {
	"name" : "SEAT_INFO",
	"page" : "seat_manage/financial/info"
}, {
	"name" : "MARKET_ACCESS_AUTH_MANAGEMENT",
	"page" : "seat_manage/financial/auth"
}, {
	"name" : "BANK_ACCOUNT",
	"page" : "seat_manage/shared/account"
}, {
	"name" : "SEAT_GRADE",
	"page" : "seat_manage/shared/grade"
}, {
	"name" : "SEAT_RELATIONSHIP",
	"page" : "seat_manage/financial/relationship"
} ]);

financialSeatManageApp.controller('financialSeatManageController', function($scope, subPages) {
	$scope.subPages = subPages;
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
