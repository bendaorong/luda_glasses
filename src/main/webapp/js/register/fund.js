/**
 * 
 */
var fundRegisterApp = angular.module("fundRegisterApp", [ 'globalLanguageApp', 'ngAnimate', 'globalConstantApp' ]);
fundRegisterApp.value("steps", [ {
	"name" : "REGISTER_INFO"
}, {
	"name" : "PASSWORD_SETTING"
}, {
	"name" : "SIGN_AGREEMENT"
} ]);

fundRegisterApp.controller('fundRegisterController', function($scope, steps, $http, countries, $http) {
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
	$scope.getSeatName = function($event) {

		$scope.adminName = "";

		if (arguments.length === 1) {
			if ($event.keyCode !== 13) {
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

	$scope.getFundManagerSeatName = function($event) {

		$scope.fundManagerSeatName = "";

		if (arguments.length === 1) {
			if ($event.keyCode !== 13) {
				return;
			}
		}

		$http.get("../register/seatName?seatNumber=" + $scope.register.fundManager).success(function(data) {
			$scope.fundManagerSeatName = data.SEATNAME;

		}).error(function(data) {
			$scope.fundManagerSeatName = "席位号不存在！";
		}).then(function() {

		});
	}

	$scope.getFundCustodySeatName = function($event) {

		$scope.fundCustodySeatName = "";

		if (arguments.length === 1) {
			if ($event.keyCode !== 13) {
				return;
			}
		}

		$http.get("../register/seatName?seatNumber=" + $scope.register.fundCustody).success(function(data) {
			$scope.fundCustodySeatName = data.SEATNAME;

		}).error(function(data) {
			$scope.fundCustodyrSeatName = "席位号不存在！";
		}).then(function() {

		});
	}

	$scope.setAddressLevel1 = function() {
		$scope.register.addressLevel1 = $scope.register.country;
	}

	$scope.submit = function() {
		console.log($scope.register);
		$scope.isLoading = true;
		$http.post("../rest/register/fund", {
			seatType : $scope.register.seatType,
			custodySeatNumber : $scope.register.fundCustody,
			manageSeatNumber : $scope.register.fundManager,
			seatName : $scope.register.seatName,
			seatPassword : $scope.register.password,
			adminSeatNumber : $scope.register.adminSeatNumber,
			adminPassword : $scope.register.adminPassword
		}).success(function(data) {
			console.log(data);
			$scope.isLoading = false;
			window.location.href = window.location.origin + "/richmen/login/notpersonal?type=Z004&seatNumber=" + data.seatNumber;
		}).error(function(data) {
			console.log(data);
			$scope.isLoading = false;
			$scope.errorMsg = data.errorMsg;
		}).then(function() {

		});
	}

	$scope.clearName = function() {
		$scope.adminName = "";
		if ($scope.register.adminSeatNumber && $scope.register.adminSeatNumber.length > 10) {
			$scope.register.adminSeatNumber = $scope.register.adminSeatNumber.substring(0, 10);
		}
	}
	$scope.clearFundManagerSeatName = function() {
		$scope.fundManagerSeatName = "";
		if ($scope.register.fundManager && $scope.register.fundManager.length > 10) {
			$scope.register.fundManager = $scope.register.fundManager.substring(0, 10);
		}
	}
	$scope.clearFundCustodySeatName = function() {
		$scope.fundCustodySeatName = "";
		if ($scope.register.fundCustody && $scope.register.fundCustody.length > 10) {
			$scope.register.fundCustody = $scope.register.fundCustody.substring(0, 10);
		}
	}
});