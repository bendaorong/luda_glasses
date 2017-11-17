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
	}).when("/editSupplier/:supplierId", {
        templateUrl : "app/business_operation/controllers/supplier/editSupplier.html",
        controller : "editSupplierController"
	}).when("/supplierDetail/:supplierId", {
        templateUrl : "app/business_operation/controllers/supplier/supplierDetail.html",
        controller : "supplierDetailController"
	}).when("/editSupplierContact/:contactId", {
        templateUrl : "app/business_operation/controllers/supplier/editContact.html",
        controller : "editContactController"
    }).when("/addSupplierContact/:supplierId", {
        templateUrl : "app/business_operation/controllers/supplier/newContact.html",
        controller : "addContactController"
    }).when("/materielManage", {
        templateUrl : "app/business_operation/controllers/materiel/materielList.html",
        controller : "materielManageController"
	}).when("/addMateriel", {
        templateUrl : "app/business_operation/controllers/materiel/newMateriel.html",
        controller : "addMaterielController"
    }).when("/editMateriel/:id", {
        templateUrl : "app/business_operation/controllers/materiel/editMateriel.html",
        controller : "editMaterielController"
    }).when("/goodsTypeManage", {
        templateUrl : "app/business_operation/controllers/dictionary/goodsType/goodsTypeList.html",
        controller : "goodsTypeManageController"
    }).when("/addGoodsType", {
        templateUrl : "app/business_operation/controllers/dictionary/goodsType/newGoodsType.html",
        controller : "addGoodsTypeController"
    }).when("/editGoodsType/:typeId", {
        templateUrl : "app/business_operation/controllers/dictionary/goodsType/editGoodsType.html",
        controller : "editGoodsTypeController"
    }).when("/goodsColorManage", {
        templateUrl : "app/business_operation/controllers/dictionary/goodsColor/goodsColorList.html",
        controller : "goodsColorManageController"
    }).when("/addGoodsColor", {
        templateUrl : "app/business_operation/controllers/dictionary/goodsColor/newGoodsColor.html",
        controller : "addGoodsColorController"
    }).when("/editGoodsColor/:colorId", {
        templateUrl : "app/business_operation/controllers/dictionary/goodsColor/editGoodsColor.html",
        controller : "editGoodsColorController"
    }).when("/dictionaryManage/:dictType", {
        templateUrl : "app/business_operation/controllers/dictionary/dictList.html",
        controller : "dictManageController"
    }).when("/addDictionary/:dictType", {
        templateUrl : "app/business_operation/controllers/dictionary/newDict.html",
        controller : "addDictController"
    }).when("/editDictionary/:dictId", {
        templateUrl : "app/business_operation/controllers/dictionary/editDict.html",
        controller : "editDictController"
    }).when("/mardManage", {
        templateUrl : "app/business_operation/controllers/inventory/mard/mardList.html",
        controller : "mardManageController"
    }).when("/purchaseOrderManage", {
        templateUrl : "app/business_operation/controllers/inventory/purchaseOrder/purchaseOrderList.html",
        controller : "purchaseOrderManageController"
    }).when("/editPurchaseOrder/:id", {
        templateUrl : "app/business_operation/controllers/inventory/purchaseOrder/editPurchaseOrder.html",
        controller : "editPurchaseOrderController"
    }).when("/addPurchaseOrder",{
        templateUrl : "app/business_operation/controllers/inventory/purchaseOrder/newPurchaseOrder.html",
        controller : "addPurchaseOrderController"
    }).when("/purchaseRefundOrderManage", {
        templateUrl : "app/business_operation/controllers/inventory/purchaseOrder/refund/purchaseRefundOrderList.html",
        controller : "purchaseRefundOrderManageController"
    }).when("/editPurchaseRefundOrder/:id", {
        templateUrl : "app/business_operation/controllers/inventory/purchaseOrder/refund/editPurchaseRefundOrder.html",
        controller : "editPurchaseRefundOrderController"
    }).when("/addPurchaseRefundOrder",{
        templateUrl : "app/business_operation/controllers/inventory/purchaseOrder/refund/newPurchaseRefundOrder.html",
        controller : "addPurchaseRefundOrderController"
    }).when("/inventoryVerificationManage", {
        templateUrl : "app/business_operation/controllers/inventory/inventoryVerification/inventoryVerificationList.html",
        controller : "invntVerifManageController"
    }).when("/addInvntVerification", {
        templateUrl : "app/business_operation/controllers/inventory/inventoryVerification/newInventoryVerification.html",
        controller : "addInventoryVerificationController"
    }).when("/editInvntVerification/:id", {
        templateUrl : "app/business_operation/controllers/inventory/inventoryVerification/editInventoryVerification.html",
        controller : "editInventoryVerificationController"
    }).when("/customerManage", {
        templateUrl : "app/business_operation/controllers/customer/customerList.html",
        controller : "customerManageController"
    }).when("/addCustomer",{
        templateUrl : "app/business_operation/controllers/customer/newCustomer.html",
        controller : "addCustomerController"
    }).when("/editCustomer/:customerId",{
        templateUrl : "app/business_operation/controllers/customer/editCustomer.html",
        controller : "editCustomerController"
    }).when("/customerDetail/:customerId", {
        templateUrl : "app/business_operation/controllers/customer/customerDetail.html",
        controller : "customerDetailController"
    }).when("/transferOrderManage", {
        templateUrl : "app/business_operation/controllers/inventory/transfer/transferList.html",
        controller : "transferOrderManageController"
    }).when("/addTransferOrder", {
        templateUrl : "app/business_operation/controllers/inventory/transfer/newTransfer.html",
        controller : "addTransferOrderController"
    }).when("/editTransferOrder/:id", {
        templateUrl : "app/business_operation/controllers/inventory/transfer/editTransfer.html",
        controller : "editTransferOrderController"
    }).when("/salesOrderManage", {
        templateUrl : "app/business_operation/controllers/sales/sale/salesOrderList.html",
        controller : "salesOrderManageController"
    }).when("/addSalesOrder",{
        templateUrl : "app/business_operation/controllers/sales/sale/newSalesOrder.html",
        controller : "addSalesOrderController"
    }).when("/editSalesOrder/:id", {
        templateUrl : "app/business_operation/controllers/sales/sale/editSalesOrder.html",
        controller : "editSalesOrderController"
    }).when("/addRefundOrder", {
        templateUrl : "app/business_operation/controllers/sales/refund/newRefundOrder.html",
        controller : "addRefundOrderController"
    }).when("/refundOrderManage", {
        templateUrl : "app/business_operation/controllers/sales/refund/refundOrderList.html",
        controller : "refundOrderManageController"
    }).when("/editRefundOrder/:id", {
        templateUrl : "app/business_operation/controllers/sales/refund/editRefundOrder.html",
        controller : "editRefundOrderController"
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
                url:"/luda_glasses/rest/supplier/addSupplier",
                data:supplier
            }).success(successCallback).error(errorCallback);
        },
        getSupplierById : function (supplierId, successCallback, errorCallback) {
            $http.get("/luda_glasses/rest/supplier/getSupplierById/" + supplierId)
				.success(successCallback).error(errorCallback);
        },
        updateSupplier : function (supplier, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/supplier/updateSupplier",
                data:supplier
            }).success(successCallback).error(errorCallback);
        },
        disableSupplier : function (supplierId, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/supplier/disableSupplier/" + supplierId
            }).success(successCallback).error(errorCallback);
        },
        enableSupplier : function (supplierId, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/supplier/enableSupplier/" + supplierId
            }).success(successCallback).error(errorCallback);
        },
        removeSupplier : function (supplierId, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/supplier/removeSupplier/" + supplierId
            }).success(successCallback).error(errorCallback);
        },
        fetchSupplierContactList : function (supplierId, successCallback, errorCallback) {
            $http.get("/luda_glasses/rest/supplier/contactList/" + supplierId).success(successCallback).error(errorCallback);
        },
        getSupplierContactById : function (contactId, successCallback, errorCallback) {
            $http.get("/luda_glasses/rest/supplier/getContactById/" + contactId).success(successCallback).error(errorCallback);
        },
        updateSupplierContact : function (supplierContact, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/supplier/updateContact",
                data:supplierContact
            }).success(successCallback).error(errorCallback);
        },
        addSupplierContact : function (supplierContact, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/supplier/saveContact",
                data:supplierContact
            }).success(successCallback).error(errorCallback);
        },
        removeSupplierContact : function (contactId, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/supplier/removeContact/" + contactId
            }).success(successCallback).error(errorCallback);
        }
    }
});

/**
 * 商品服务
 */
businessOperationApp.factory("materielService", function($http) {
    return {
        fetchMaterielList : function(successCallback, errorCallback){
            $http.get("/luda_glasses/rest/materiel/list").success(successCallback).error(errorCallback);
        },
        saveMateriel : function(materiel, successCallback, errorCallback){
            $http({
                method:"POST",
                url:"/luda_glasses/rest/materiel/saveMateriel",
                data:materiel
            }).success(successCallback).error(errorCallback);
        },
        getById : function (id, successCallback, errorCallback) {
            $http({
                method:"GET",
                url:"/luda_glasses/rest/materiel/getById/" + id,
            }).success(successCallback).error(errorCallback);
        },
        updateMateriel : function (materiel, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/materiel/updateMateriel",
                data:materiel
            }).success(successCallback).error(errorCallback);
        },
        removeMateriel : function (id, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/materiel/removeMateriel/" + id
            }).success(successCallback).error(errorCallback);
        }
    }
});

/**
 * 字典服务
 */
businessOperationApp.factory("dictionaryService", function($http) {
    return {
        fetchGoodsKindList : function(successCallback, errorCallback){
            $http.get("/luda_glasses/rest/dictionary/goodsKind/list").success(successCallback).error(errorCallback);
        },
        fetchGoodsTypeList : function(successCallback, errorCallback){
            $http.get("/luda_glasses/rest/dictionary/goodsType/list").success(successCallback).error(errorCallback);
        },
        saveGoodsType : function (goodsType, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/dictionary/goodsType/saveGoodsType",
                data:goodsType
            }).success(successCallback).error(errorCallback);
        },
        getGoodsTypeById : function (typeId, successCallback, errorCallback) {
            $http.get("/luda_glasses/rest/dictionary/goodsType/getById/" + typeId).success(successCallback).error(errorCallback);
        },
        updateGoodsType : function (goodsType, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/dictionary/goodsType/updateGoodsType",
                data:goodsType
            }).success(successCallback).error(errorCallback);
        },
        removeGoodsType : function (typeId, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/dictionary/goodsType/removeGoodsType/" + typeId
            }).success(successCallback).error(errorCallback);
        },
        fetchGoodsColorList : function(successCallback, errorCallback){
            $http.get("/luda_glasses/rest/dictionary/goodsColor/list").success(successCallback).error(errorCallback);
        },
        saveGoodsColor : function (goodsColor, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/dictionary/goodsColor/saveGoodsColor",
                data:goodsColor
            }).success(successCallback).error(errorCallback);
        },
        getGoodsColorById : function (colorId, successCallback, errorCallback) {
            $http.get("/luda_glasses/rest/dictionary/goodsColor/getById/" + colorId).success(successCallback).error(errorCallback);
        },
        updateGoodsColor : function (goodsColor, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/dictionary/goodsColor/updateGoodsColor",
                data:goodsColor
            }).success(successCallback).error(errorCallback);
        },
        removeGoodsColor : function (colorId, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/dictionary/goodsColor/removeGoodsColor/" + colorId
            }).success(successCallback).error(errorCallback);
        },
        fetchDictionaryByType : function (dictType, successCallback, errorCallback) {
            $http.get("/luda_glasses/rest/dictionary/fetchByType/" + dictType).success(successCallback).error(errorCallback);
        }
    }
});

/**
 * 库存服务
 */
businessOperationApp.factory("inventoryService", function($http) {
    return {
        fetchMardVoList : function(successCallback, errorCallback){
            $http.get("/luda_glasses/rest/inventory/mard/list").success(successCallback).error(errorCallback);
        },
        fetchPurchaseOrderList : function (orderType, successCallback, errorCallback) {
            $http.get("/luda_glasses/rest/inventory/purchaseOrder/list/" + orderType)
                .success(successCallback).error(errorCallback);
        },
        getPurchaseOrderById : function (id, successCallback, errorCallback) {
            $http.get("/luda_glasses/rest/inventory/purchaseOrder/getById/" + id).success(successCallback).error(errorCallback);
        },
        savePurchaseOrder : function (purchaseOrder, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/inventory/purchaseOrder/savePurchaseOrder",
                data:purchaseOrder
            }).success(successCallback).error(errorCallback);
        },
        updatePurchaseOrder : function (purchaseOrder, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/inventory/purchaseOrder/updatePurchaseOrder",
                data:purchaseOrder
            }).success(successCallback).error(errorCallback);
        },
        removePurchaseOrder : function (id, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/inventory/purchaseOrder/removePurchaseOrder/" + id
            }).success(successCallback).error(errorCallback);
        },
        savePurchaseOrderItem : function (purchaseOrderItem, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/inventory/purchaseOrder/savePurchaseOrderItem",
                data:purchaseOrderItem
            }).success(successCallback).error(errorCallback);
        },
        removePurchaseOrderItem : function (itemId, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/inventory/purchaseOrder/removePurchaseOrderItem/" + itemId
            }).success(successCallback).error(errorCallback);
        },
        fetchInvntVerifList : function (successCallback, errorCallback) {
            $http.get("/luda_glasses/rest/inventory/inventoryVerification/list").success(successCallback).error(errorCallback);
        },
        saveInventoryVerification : function (inventoryVerification,successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/inventory/inventoryVerification/saveInventoryVerification",
                data:inventoryVerification
            }).success(successCallback).error(errorCallback);
        },
        removeInvntVerification : function (id, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/inventory/inventoryVerification/removeInvntVerification/" + id
            }).success(successCallback).error(errorCallback);
        },
        getInvntVerificationById : function (id, successCallback, errorCallback) {
            $http.get("/luda_glasses/rest/inventory/inventoryVerification/getInvntVerificationById/" + id).success(successCallback).error(errorCallback);
        },
        updateInvntVerification : function (inventoryVerification, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/inventory/inventoryVerification/updateInvntVerification",
                data:inventoryVerification
            }).success(successCallback).error(errorCallback);
        },
        saveInvntVerificationItem : function (item, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/inventory/inventoryVerification/saveInvntVerificationItem",
                data:item
            }).success(successCallback).error(errorCallback);
        },
        removeInvntVerificationItem : function (id,successCallback, errorCallback ) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/inventory/inventoryVerification/removeInvntVerificationItem/" + id
            }).success(successCallback).error(errorCallback);
        },
        fetchTransferOrders : function (successCallback, errorCallback) {
            $http.get("/luda_glasses/rest/inventory/transfer/fetchTransferOrders").success(successCallback).error(errorCallback);
        },
        getTransferOrderById : function (id, successCallback, errorCallback) {
            $http.get("/luda_glasses/rest/inventory/transfer/getTransferOrderById/" + id).success(successCallback).error(errorCallback);
        },
        saveTransferOrder : function (transferOrder, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/inventory/transfer/saveTransferOrder",
                data:transferOrder
            }).success(successCallback).error(errorCallback);
        },
        updateTransferOrder : function (transferOrder, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/inventory/transfer/updateTransferOrder",
                data:transferOrder
            }).success(successCallback).error(errorCallback);
        },
        removeTransferOrder : function (id, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/inventory/transfer/removeTransferOrder/" + id
            }).success(successCallback).error(errorCallback);
        },
        removeTransferOrderItem : function (itemId, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/inventory/transfer/removeTransferOrderItem/" + itemId
            }).success(successCallback).error(errorCallback);
        },
        saveTransferOrderItem : function (transferOrderItem, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/inventory/transfer/saveTransferOrderItem",
                data:transferOrderItem
            }).success(successCallback).error(errorCallback);
        },
        getMard : function (materielId, storeId, successCallback, errorCallback) {
            $http.get("/luda_glasses/rest/inventory/mard/getMard/" + materielId + "/" + storeId)
                .success(successCallback).error(errorCallback);
        }
    }
});

/**
 * 销售服务
 */
businessOperationApp.factory("salesService", function($http) {
    return {
        fetchSalesOrderVoList : function (orderType, successCallback, errorCallback) {
            $http.get("/luda_glasses/rest/sales/fetchSalesOrderVoList/" + orderType)
                .success(successCallback).error(errorCallback);
        },
        fetchCustomerSalesOrderVoList: function (customerId, successCallback, errorCallback) {
            $http.get("/luda_glasses/rest/sales/fetchCustomerSalesOrderVoList/" + customerId)
                .success(successCallback).error(errorCallback);
        },
        saveSalesOrder : function (salesOrder, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/sales/saveSalesOrder",
                data:salesOrder
            }).success(successCallback).error(errorCallback);
        },
        getSalesOrderWithItemsById : function (id, successCallback, errorCallback) {
            $http.get("/luda_glasses/rest/sales/getSalesOrderWithItemsById/" + id)
                .success(successCallback).error(errorCallback);
        },
        updateSalesOrder : function (salesOrder, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/sales/updateSalesOrder",
                data:salesOrder
            }).success(successCallback).error(errorCallback);
        },
        saveSalesOrderItem : function (salesOrderItem, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/sales/saveSalesOrderItem",
                data:salesOrderItem
            }).success(successCallback).error(errorCallback);
        },
        removeSalesOrderItem : function (itemId, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/sales/removeSalesOrderItem/" + itemId
            }).success(successCallback).error(errorCallback);
        },
        removeSalesOrder : function (id, successCallback, errorCallback) {
            $http({
                method:"POST",
                url:"/luda_glasses/rest/sales/removeSalesOrder/" + id
            }).success(successCallback).error(errorCallback);
        }
    }
});

/**
 * 客户管理
 */
businessOperationApp.factory("customerService", function($http) {
    return {
        fetchCustomerList : function(successCallback, errorCallback){
            $http.get("/luda_glasses/rest/customer/list").success(successCallback).error(errorCallback);
        },
        removeCustomer:function(customerId,successCallback, errorCallback){
            $http({
                method:"POST",
                url:"/luda_glasses/rest/customer/removeCustomer/" + customerId
            }).success(successCallback).error(errorCallback);
        },
        updateCustomer:function(selectedCustomer,successCallback, errorCallback){
            $http({
                method:"POST",
                url:"/luda_glasses/rest/customer/updateCustomer",
                data:selectedCustomer
            }).success(successCallback).error(errorCallback);
        },
        getById:function(customerId,successCallback, errorCallback){
            $http({
                method:"GET",
                url:"/luda_glasses/rest/customer/getById/" + customerId,
            }).success(successCallback).error(errorCallback);
        },
        addCustomer : function(newCustomer, successCallback, errorCallback){
            $http({
                method:"POST",
                url:"/luda_glasses/rest/customer/addCustomer",
                data:newCustomer
            }).success(successCallback).error(errorCallback);
        },
        fetchOptometryRecordsByCustomerId : function (customerId, successCallback, errorCallback) {
            $http({
                method:"GET",
                url:"/luda_glasses/rest/customer/optometryRecord/getByCustomerId/" + customerId,
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
