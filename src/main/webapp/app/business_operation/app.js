var businessOperationApp = angular.module("businessOperationApp", [ 'globalApp', 'ngTable', 'ngRoute', 'angularjs-dropdown-multiselect' ]);

businessOperationApp.config(['$routeProvider', function($routeProvider) {
	$routeProvider.when("/", {
		templateUrl : "app/business_operation/views/default.html",
		controller : "defaultController"
	}).when("/AuthorizationManage",{
		templateUrl : "app/business_operation/controllers/adminUser/AdminUserList.html",
		controller  : "authorizationManageController"
	}).when("/addAdminUser", {
		templateUrl : "app/business_operation/controllers/adminUser/NewAdminUser.html",
		controller : "addAdminUserController"
	}).when("/editAdminUser/:adminUserId",{
		templateUrl : "app/business_operation/controllers/adminUser/EditAdminUser.html",
		controller : "editAdminUserController"
	}).when("/gotoModifyPwd/:cellPhoneNumber",{
		templateUrl : "app/business_operation/controllers/adminUser/ModifyPwd.html",
		controller : "editAdminUserController"
	}).when("/storeManage", {
        templateUrl : "app/business_operation/controllers/store/storeList.html",
        controller : "storeManageController"
	}).when("/addStore",{
        templateUrl : "app/business_operation/controllers/store/newStore.html",
        controller : "addStoreController"
	}).when("/editStore/:storeId",{
        templateUrl : "app/business_operation/controllers/store/editStore.html",
        controller : "editStoreController"
    }).when("/supplierManage",{
        templateUrl : "app/business_operation/controllers/supplier/supplierList.html",
        controller : "supplierManageController"
	}).when("/addSupplier", {
        templateUrl : "app/business_operation/controllers/supplier/newSupplier.html",
        controller : "addSupplierController"
	}).when("/materielManage", {
        templateUrl : "app/business_operation/controllers/materiel/materielList.html",
        controller : "materielManageController"
	}).otherwise({
		redirectTo : "/"
	})
} ]);

businessOperationApp.controller("businessOperationController", function($scope, $http) {
	$http.get(route).success(function(data) {
		//$scope.pages = filterAuthority(data);
		$scope.pages = data;
		$scope.subPageCategory = "BUSINESS_OPERATION";
	}).error(function() {

	});

	// // 根据登录用户的角色过滤权限
	// function filterAuthority(data){
	// 	var enableAuthority = [];
	// 	//用户角色
	// 	$scope.roleCode = sessionStorage.getItem("roleCode");
	// 	try {
	// 		if($scope.roleCode === '100101'){ //空间操作员
	// 			$.each(data, function(index, value){
	// 				if(value.id === '200' || value.id === '600'){
	// 					enableAuthority.push(value);
	// 				}
	// 			});
	// 		}else if($scope.roleCode === '100102'){ //慕课操作员
	// 			$.each(data, function(index, value){
	// 				if(value.id === '400' || value.id === '600'){
	// 					enableAuthority.push(value);
	// 				}
	// 			});
	// 		}else if($scope.roleCode === '100103'){ //活动操作员
	// 			$.each(data, function(index, value){
	// 				if(value.id === '300' || value.id === '600'){
	// 					enableAuthority.push(value);
	// 				}
	// 			});
	// 		}else if($scope.roleCode === '1004'){ //财务管理员
	// 			// 空间订单
	// 			var wpOrderAuthority = {
	// 				"id" : "200",
	// 				"name" : "空间管理",
	// 				"subpages" : [{
	// 					"id" : "200400",
	// 					"name" : "空间订单管理",
	// 					"page" : "#workplaceOrderManage"
	// 				}]
	// 			}
	// 			enableAuthority.push(wpOrderAuthority);
	//
	// 			//活动订单
	// 			var wpOrderAuthority = {
	// 				"id" : "300",
	// 				"name" : "活动管理",
	// 				"subpages" : [{
	// 					"id" : "300200",
	// 					"name" : "活动订单管理",
	// 					"page" : "#activityOrderManage"
	// 				}]
	// 			}
	// 			enableAuthority.push(wpOrderAuthority);
	// 		}else {
	// 			enableAuthority = data;
	// 		}
	// 	} catch (e) {
	// 		enableAuthority = data;
	// 	}
	// 	return enableAuthority;
	// }
	
	$scope.$on("loadingStart", function() {
		$scope.isLoading = true;
		$("body").css("overflow", "hidden");
	});
	$scope.$on("loadingEnd", function() {
		$scope.isLoading = false;
		$("body").css("overflow", "visible");
	});
}).controller("defaultController", function($scope){
	$scope.$emit("setActive", "");
});

/**
 * 用户服务
 */
businessOperationApp.factory("adminUserService", function($http) {
	return {
        fetchUserList : function(successCallback, errorCallback){
            $http.get("/luda_glasses/rest/adminUser/list").success(successCallback).error(errorCallback);
        },
		addAdminUser:function(adminUser, successCallback, errorCallback){
			$http({
				method:"POST",
				url:"/luda_glasses/rest/adminUser/addAdminUser",
				data:adminUser
			}).success(successCallback).error(errorCallback);
		},
		deleteAdminUser:function(adminUserId,successCallback, errorCallback){
			$http({
				method:"POST",
				url:"/luda_glasses/rest/adminUser/removeAdminUser/" + adminUserId
			}).success(successCallback).error(errorCallback);
		},
		resetAdminUserPwd:function(adminUserId, successCallback, errorCallback){
			$http({
				method:"POST",
				url:"/luda_glasses/rest/adminUser/resetPwd/" + adminUserId
			}).success(successCallback).error(errorCallback);
		},
        getWithDetailById:function(adminUserId,successCallback,errorCallback){
			$http({
				method:"GET",
				url:"/luda_glasses/rest/adminUser/"+adminUserId,
			}).success(successCallback).error(errorCallback);
		},
        updateAdminUser:function(adminUser,successCallback, errorCallback){
			$http({
				method:"POST",
				url:"/luda_glasses/rest/adminUser/updateAdminUser",
				data:adminUser
			}).success(successCallback).error(errorCallback);
		},
		modifyAdminUserPwd:function(adminUser,successCallback, errorCallback){
			$http({
				method:"POST",
				url:"/luda_glasses/rest/adminUser/modifyPwd",
				data:{
					oldPassword:adminUser.oldPassword,
					newPassword:adminUser.newPassword,
					cellPhoneNumber:adminUser.cellPhoneNumber
				}
			}).success(successCallback).error(errorCallback);
		},
        getAdminRoleList:function (successCallback, errorCallback) {
            $http.get("/luda_glasses/rest/adminUser/roleList").success(successCallback).error(errorCallback);
        }
	}
});

/**
 * 门店服务
 */
businessOperationApp.factory("storeService", function($http) {
    return {
        fetchStoreList : function(successCallback, errorCallback){
            $http.get("/luda_glasses/rest/store/list").success(successCallback).error(errorCallback);
        },
        addStore : function(store, successCallback, errorCallback){
            $http({
                method:"POST",
                url:"/luda_glasses/rest/store/addStore",
                data:store
            }).success(successCallback).error(errorCallback);
        },
		getById : function (storeId, successCallback, errorCallback) {
            $http({
                method:"GET",
                url:"/luda_glasses/rest/store/getById/" + storeId,
            }).success(successCallback).error(errorCallback);
        },
        updateStore : function (store, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/store/updateStore",
                data:store
            }).success(successCallback).error(errorCallback);
        },
        removeStore : function (storeId, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/store/removeStore/" + storeId
            }).success(successCallback).error(errorCallback);
        }
    }
});

/**
 * 供应商服务
 */
businessOperationApp.factory("supplierService", function($http) {
    return {
        fetchSupplierList : function(successCallback, errorCallback){
            $http.get("/luda_glasses/rest/supplier/list").success(successCallback).error(errorCallback);
        },
        addSupplier : function(supplier, successCallback, errorCallback){
            $http({
                method:"POST",
                url:"/luda_glasses/rest/store/addStore",
                data:supplier
            }).success(successCallback).error(errorCallback);
        }
    }
});

businessOperationApp.factory("sessionTimeoutInteceptor", ['$q','$injector', function($q,$injector) {
	var httpInterceptor  = {
		responseError: function(response) {
			// Session has expired
			if (response.status == 419){
				alert("会话超时,请重新登录！");
			   	//如果超时就处理 ，指定要跳转的页面
				window.location.replace("/" + window.location.pathname.split("/")[1] + "/");
			}else{ //other errors, just return the response. it's important to use $q.reject, otherwise, controller in angularjs will tackle it as success response.
				// return $q.reject(response);
  		    }
		 }
	 };
	 return httpInterceptor;
}]);

businessOperationApp.config(['$httpProvider', function($httpProvider) {
	$httpProvider.interceptors.push('sessionTimeoutInteceptor');
}]);
