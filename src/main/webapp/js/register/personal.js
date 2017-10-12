/**
 * 
 */
var personalRegisterApp = angular.module("personalRegisterApp", [ 'ngAnimate', 'globalLanguageApp' ]);
personalRegisterApp.value("steps", [ {
	"name" : "1.录入信息"
}, {
	"name" : "2.设置密码"
}, {
	"name" : "3.签署协议"
} ]);
personalRegisterApp.controller('personalRegisterController', function($scope, steps, $http) {
	$scope.steps = steps;
	$scope.currentStep = 0;
	
	$scope.nextStep = function() {
		if ($scope.currentStep < $scope.steps.length - 1) {
			$scope.currentStep++;
		}
	}
	$scope.previousStep = function() {
		if ($scope.currentStep > 0) {
			$scope.currentStep--;
		}
	}
	
	$scope.setGreenCard = function(){
		if(!$scope.register.hasGreenCard){
			$scope.register.greenCardNo = "";
			$scope.register.greenCardStartDate = "";
			$scope.register.greenCardEndDate = "";
		}
	}
	
	$scope.submit = function(){
		$scope.isLoading = true;
		$http.post('../rest/register/personal', $scope.register).success(function(data){
			console.log(data);
			$scope.isLoading = false;
			window.location.href = window.location.origin + "/richmen/login/personal?seatNumber=" + data.seatNumber;
		}).error(function(data){
			console.log(data);
			$scope.isLoading = false;
			$scope.errorMsg = data.errorMsg;
		});
	}
});