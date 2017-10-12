/**
 * 
 */
var commonRegisterApp = angular.module("commonRegisterApp", [ 'ngAnimate', 'globalLanguageApp' ]);
commonRegisterApp.value("steps", [ {
	"name" : "1.注册信息"
}, {
	"name" : "2.成员和出资比例"
}, {
	"name" : "3.设置密码"
}, {
	"name" : "4.签署协议"
} ]);
commonRegisterApp.controller('commonRegisterController', function($scope, steps, $http) {
	$scope.steps = steps;
	$scope.currentStep = 0;
	$scope.members = [];

	$scope.nextStep = function() {
		if ($scope.currentStep === 0) {
			if ($scope.members.length === 0) {
				var admin = {
					"seatNumber" : $scope.register.adminSeatNumber,
					"name" : "",
					"isAdmin" : true,
					"ratio" : 50,
					"isLoading" : false
				};
				$scope.members.push(admin);
			} else {
				$scope.members[0].seatNumber = $scope.register.adminSeatNumber;
			}
			$http.get("../register/seatName?seatNumber=" + $scope.register.adminSeatNumber).success(function(data) {
				$scope.members[0].name = data.SEATNAME;
			}).error(function(data) {
			});

		}
		if ($scope.currentStep < $scope.steps.length - 1) {
			if ($scope.currentStep === 1) {
				var sum = 0;
				for (var index = 0; index < $scope.members.length; index++) {
					$scope.members[index].ratio = parseInt($scope.members[index].ratio);
					sum = sum + $scope.members[index].ratio;
				}
				console.log(sum);
				if (sum !== 100) {
					// alert("累加比例不等于100");
					BootstrapDialog.alert({
						"type" : BootstrapDialog.TYPE_WARNING,
						"message" : "比例累加不等于100%!",
						"title" : "警告"
					});
					return;
				}
				if ($scope.members.length < 2) {
					BootstrapDialog.alert({
						"type" : BootstrapDialog.TYPE_WARNING,
						"message" : "共同席位至少有两位成员！",
						"title" : "警告"
					});
					return;
				}
				$scope.register.members = angular.toJson($scope.members);
				console.log($scope.membersString);
			}
			$scope.currentStep++;
		}
	}
	$scope.previousStep = function() {
		if ($scope.currentStep > 0) {
			$scope.currentStep--;
		}
	}
	$scope.addMember = function() {
		var member = {
			"seatNumber" : "",
			"name" : "",
			"isAdmin" : false,
			"ratio" : 0,
			"isLoading" : false
		};
		$scope.members.push(member);
		console.log($scope.members);
	}
	$scope.removeMember = function(seatNumber) {
		for (var i = 0; i < $scope.members.length; i++) {
			if ($scope.members[i].seatNumber === seatNumber) {
				$scope.members.splice(i, 1);
				return;
			}
		}
	}
	$scope.getSeatName = function(seatNumber) {
		var index = 0;
		for (var i = 0; i < $scope.members.length; i++) {
			if ($scope.members[i].seatNumber === seatNumber) {
				index = i;
				break;
			}
		}
		$scope.members[index].name = "";
		if (seatNumber.length === 10) {
			$scope.members[index].isLoading = true;
			$http.get("../register/seatName?seatNumber=" + seatNumber).success(function(data) {
				$scope.members[index].isLoading = false;
				$scope.members[index].name = data.SEATNAME;
			}).error(function(data) {
				BootstrapDialog.alert({
					"type" : BootstrapDialog.TYPE_WARNING,
					"message" : "您输入的席位号不正确，查找不到用户",
					"title" : "警告"
				});
				$scope.members[index].isLoading = false;
			});
		}
	}
	$scope.getAdminName = function(adminSeatNumber) {
		if (adminSeatNumber.length === 10) {
			$http.get("../register/seatName?seatNumber=" + adminSeatNumber).success(function(data) {
				$scope.adminName = data.SEATNAME;
			}).error(function(data) {
				BootstrapDialog.alert({
					"type" : BootstrapDialog.TYPE_WARNING,
					"message" : "您输入的席位号不正确，查找不到用户",
					"title" : "警告"
				});
			});
		} else {
			$scope.adminName = "";
		}
	}
	$scope.submit = function() {
		$scope.isLoading = true;
		$http.post('../rest/register/common', $scope.register).success(function(data){
			console.log(data);
			$scope.isLoading = false;
			window.location.href = window.location.origin + "/richmen/login/notpersonal?type=Z005&seatNumber=" + data.seatNumber;
		}).error(function(data){
			console.log(data);
			$scope.isLoading = false;
			$scope.errorMsg = data.errorMsg;
		});
	}

});