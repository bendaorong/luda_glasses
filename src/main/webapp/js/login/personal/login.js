/**
 * 
 */
var personalLoginApp = angular.module("personalLoginApp", [ 'globalLanguageApp' ]);

personalLoginApp.controller('loginController', [ '$scope', '$location', 'languages', '$translate', '$http', function($scope, $location, languages, $translate, $http) {
	$scope.submit = function() {
		$scope.isLoading = true;
		console.log($scope.login);
		$http.post(
			"/luda_glasses/doLogin",
			$scope.login
		).success(function(data) {
			console.log(data);
			$scope.isLoading = false;
			window.location.href = window.location.origin + "/luda_glasses/index";
			window.role = data.adminRoleModel.roleCode;
			sessionStorage.setItem("roleCode",data.adminRoleModel.roleCode);
            sessionStorage.setItem("storeId",data.storeId);
            sessionStorage.setItem("adminUserId",data.adminUserId);
			console.log(sessionStorage.getItem("roleCode"));
		}).error(function(data) {
			console.log(data);
			$scope.isLoading = false;
			$scope.errorMsg = data.errorMsg;
		}).then(function() {

		});
	}
	function getQueryStringByName(name) {
		var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
		if (result == null || result.length < 1) {
			return "";
		}
		return result[1];
	}

} ]);