/**
 * 
 */
var notPersonalLoginApp = angular.module("notPersonalLoginApp", [ 'globalLanguageApp' ]);

notPersonalLoginApp.value("seatTypes", [ {
	"name" : "ENTERPRISE",
	"value" : "Z002"
}, {
	"name" : "FINANCIAL",
	"value" : "Z003"
}, {
	"name" : "FUND",
	"value" : "Z004"
}, {
	"name" : "COMMON",
	"value" : "Z005"
} ]);

/*
 * notPersonalLoginApp.value("availableMarkets",[{ "name": "中国", "value":"88CN"
 * },{ "name": "美国", "value":"88US" },{ "name": "中国香港", "value":"88HK" }]);
 */
notPersonalLoginApp.controller('commonRegisterController', [ '$scope', 'seatTypes', '$location', 'languages', '$translate', '$http',
		function($scope, seatTypes, $location, languages, $translate, $http) {
			$scope.seatTypes = seatTypes;

			$scope.initSeatType = getQueryStringByName('type');

			var seatNumber = getQueryStringByName('seatNumber');
			if (seatNumber) {
				$scope.successMsg = "注册成功，席位号为" + seatNumber;
			}

			$scope.getSeatTypeName = function() {
				for (var i = 0; i < $scope.seatTypes.length; i++) {
					if ($scope.login.seatType === $scope.seatTypes[i].value) {
						return $scope.seatTypes[i].name;
					}
				}
				return "";
			}

			$scope.submit = function() {
				$scope.isLoading = true;
				$http.post("../rest/login/notpersonal", $scope.login).success(function(data) {
					console.log(data);
					$scope.isLoading = false;
					window.location.href = window.location.origin + "/richmen/home";
				}).error(function(data) {
					console.log(data);
					$scope.isLoading = false;
					$scope.errorMsg = data.errorMsg;
				}).then(function() {

				});
			}

			$scope.checkSeatNumber = function(type) {
				if (type === 'notpersonal') {
					if ($scope.login.seatNumber && $scope.login.seatNumber.length > 10) {
						$scope.login.seatNumber = $scope.login.seatNumber.substring(0, 10);
					}
				} else if (type === "personal") {
					if ($scope.login.personalSeatNumber && $scope.login.personalSeatNumber.length > 10) {
						$scope.login.personalSeatNumber = $scope.login.personalSeatNumber.substring(0, 10);
					}
				}
			}

			function getQueryStringByName(name) {
				var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
				if (result == null || result.length < 1) {
					return "";
				}
				return result[1];
			}

		} ]);