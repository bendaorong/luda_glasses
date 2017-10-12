/**
 * 
 */
var financialRegisterApp = angular.module("financialRegisterApp", [ 'globalLanguageApp', 'ngAnimate' , 'globalConstantApp' ]);
financialRegisterApp.value("steps", [ {
	"name" : "REGISTER_INFO"
}, {
	"name" : "PASSWORD_SETTING"
}, {
	"name" : "SIGN_AGREEMENT"
} ]);

financialRegisterApp.controller('financialRegisterController', function($scope, steps, $http, countries, $http) {
	$scope.steps = steps;
	$scope.currentStep = 0;
	$scope.countries = countries;
	
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
	$scope.getSeatName = function($event){
		
		$scope.adminName = "";
		
		if(arguments.length===1){
			if($event.keyCode !== 13){
				return;
			}
		}
		
		$http.get("../register/seatName?seatNumber=" + $scope.register.adminSeatNumber).success(function(data) {
			$scope.adminName = data.SEATNAME;
			
		}).error(function(data) {
			$scope.adminName = "席位号不存在！";
		}).then(function() {
			
		});
	}
	
	$scope.setAddressLevel1 = function(){
		$scope.register.addressLevel1 = $scope.register.country;
	}
	
	$scope.submit = function(){
		console.log($scope.register);
		$scope.isLoading = true;
		$http.post("../rest/register/enterprise", $scope.register).success(function(data){
			console.log(data);
			$scope.isLoading = false;
			window.location.href = window.location.origin + "/richmen/login/notpersonal?type=Z003&seatNumber=" + data.seatNumber;
		}).error(function(data){
			console.log(data);
			$scope.isLoading = false;
			$scope.errorMsg = data.errorMsg;
		}).then(function(){
			
		});
	}
	
	$scope.clearName = function(){
		$scope.adminName = "";
		if($scope.register.adminSeatNumber && $scope.register.adminSeatNumber.length > 10){
			$scope.register.adminSeatNumber = $scope.register.adminSeatNumber.substring(0, 10);
		}
	}
});