(function() {
    angular.module("businessOperationApp").controller("batchPurchaseOrderManageController", function($scope, $location, NgTableParams, inventoryService) {
        setActiveSubPage($scope, "batchPurchaseOrderManage");

        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;
        $scope.purchaseOrderList = [];

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }

        // 显示采购单列表
        function initPurchaseOrderList() {
            inventoryService.fetchPurchaseOrderList("01", function(data){
                if(data.success){
                    $scope.purchaseOrderList = data.data;
                    $scope.tableParams = new NgTableParams({}, {
                        dataset : $scope.purchaseOrderList
                    });
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '获取采购单失败:' + data.errorMsg
                    });
                }
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取采购单错误:' + data.errorMsg
                });
            });
        }
        initPurchaseOrderList();

        $scope.refresh = function() {
            $scope.$emit("loadingStart");
            initPurchaseOrderList();
            $scope.$emit("loadingEnd");
        }

        // 编辑采购单
        $scope.gotoEdit = function(id) {
            $location.path("/editPurchaseOrder/" + id);
        }

        // 删除采购单
        $scope.removePurchaseOrder = function (id) {
            if(confirm("删除采购单将同时扣减采购商品的库存，确定删除吗？")){
                inventoryService.removePurchaseOrder(id, function (data) {
                    if(data.success){
                        // BootstrapDialog.show({
                        //     type : BootstrapDialog.TYPE_SUCCESS,
                        //     title : '成功',
                        //     message : '采购单删除成功'
                        // });
                        $scope.refresh();
                    }else {
                        BootstrapDialog.show({
                            type : BootstrapDialog.TYPE_DANGER,
                            title : '失败',
                            message : '采购单删除失败:' + data.errorMsg
                        });
                    }
                }, function (data) {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '警告',
                        message : '采购单删除错误:' + data.errorMsg
                    });
                });
            }
        }
    }).controller("addBatchPurchaseOrderController", function ($location,$scope,$filter,materielService,inventoryService,storeService,supplierService,adminUserService,dictionaryService) {
        setActiveSubPage($scope, "addBatchPurchaseOrder");
        $scope.adminUserId = sessionStorage.getItem("adminUserId");
        $scope.storeId = sessionStorage.getItem("storeId");
        $scope.roleCode = sessionStorage.getItem("roleCode");

        $scope.newPurchaseOrder = {};//采购单
        $scope.newPurchaseOrder.storeId = $scope.storeId;
        $scope.newPurchaseOrder.businessmanId = $scope.adminUserId;
        $scope.newPurchaseOrder.totalQuantity = 0;
        $scope.newPurchaseOrder.totalAmount = 0;
        // 采购日期默认为当前时间
        $scope.newPurchaseOrder.purchaseDate = $filter("date")(new Date(), "yyyy-MM-dd");

        $scope.purchaseOrderItemList = [];//采购明细
        $scope.newPurchaseOrder.purchaseOrderItemList = $scope.purchaseOrderItemList;

        $scope.storeList = [];
        $scope.supplierList = [];
        $scope.adminUserList = [];
        $scope.materielList = [];

        $scope.selectedMateriel = {}; //选择的商品
        $scope.purchaseOrderItem = {}; //采购单明细

        $scope.showSphereAndCylinder = false; //显示球镜柱镜
        $scope.showAxial = false; //显示度数

        // 查询商品
        materielService.fetchMaterielList(function(data){
            $scope.materielList = data.data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取商品错误' + data.errorMsg
            });
        });

        // 查询门店
        storeService.fetchStoreList(function(data){
            if($scope.roleCode == '00'){
                $scope.storeList = data;
            }else {
                angular.forEach(data, function (each) {
                    if(each.storeId == $scope.storeId){
                        $scope.storeList.push(each);
                    }
                });
            }
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取门店失败:' + data.errorMsg
            });
        });

        // 查询供应商
        supplierService.fetchUseableSupplierList(function (data) {
            $scope.supplierList = data.data;
        },function (data) {
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取供应商失败:' + data.errorMsg
            });
        });

        // 查询业务员
        adminUserService.fetchUserListByStore(function(data){
            $scope.adminUserList  =  data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取业务员失败:' + data.errorMsg
            });
        });

        // 选择供应商
        $scope.selectSupplier = function () {
            $scope.usedMaterielList = [];
            // 过滤出当前选中供应商的商品
            angular.forEach($scope.materielList, function (each) {
                if(each.supplierId == $scope.newPurchaseOrder.supplierId){
                    //批量采购可采购镜片、隐形眼镜和老花镜
                    if(each.kindId == 1 || each.kindId == 2 || each.kindId == 6){
                        $scope.usedMaterielList.push(each);
                    }
                }
            });
            // 初始化商品下拉框
            angular.element("#materiel").find("option").remove();
            $("#materiel").append("<option value=''></option>");
            for(var i=0; i<$scope.usedMaterielList.length; i++){
                $("#materiel").append("<option value='" + $scope.usedMaterielList[i].id + "'>" + $scope.usedMaterielList[i].name + "</option>");
            }
            // 先删除原有商品下拉框
            angular.element(".searchable-select").remove();
            // 新建商品下拉框
            $("#materiel").searchableSelect(function (materielId) {
                if(angular.isDefined(materielId) && materielId != null && materielId != ''){
                    for(var i=0; i<$scope.materielList.length; i++){
                        if($scope.materielList[i].id == materielId){
                            $scope.selectedMateriel = $scope.materielList[i];
                            $scope.purchaseOrderItem.materielId = materielId;
                            // 根据选择商品的商品类型，控制采购明细。
                            // 镜片时，采购明细显示球镜和柱镜
                            // 老花镜和隐形眼镜时，显示度数
                            var kindId = $scope.selectedMateriel.kindId;
                            if(kindId == 1){
                                $scope.showSphereAndCylinder = true;
                                $scope.showAxial = false;
                            }else if(kindId == 2 || kindId == 6){
                                $scope.showSphereAndCylinder = false;
                                $scope.showAxial = true;
                            }
                            break;
                        }
                    }
                }else {
                    $scope.selectedMateriel = {};
                    $scope.purchaseOrderItem.materielId = null;
                    $scope.showSphereAndCylinder = false;
                    $scope.showAxial = false;
                }
                $scope.purchaseOrderItemList = [];
                $scope.purchaseOrderItem.sphere = 0;
                $scope.purchaseOrderItem.cylinder = 0;
                $scope.purchaseOrderItem.axial = 0;
                $scope.$apply();
            });
        }



        // 保存采购单
        $scope.savePurchaseOrder = function () {
            if($scope.purchaseOrderItemList.length == 0){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '请添加采购单明细'
                });
                return false;
            }
            $scope.newPurchaseOrder.purchaseOrderItemList = $scope.purchaseOrderItemList;

            $("#saveBtn").attr("disabled", true);
            $("#saveBtn").text("处理中...");
            $("#cancelBtn").attr("disabled", true);

            var totalQuantity = 0;
            var totalAmount = 0;
            angular.forEach($scope.purchaseOrderItemList, function (each) {
                each.materielId = $scope.purchaseOrderItem.materielId;
                each.purchasePrice = $scope.purchaseOrderItem.purchasePrice;

                totalQuantity += each.purchaseQuantity;
                totalAmount += each.purchasePrice * each.purchaseQuantity;
            });
            $scope.newPurchaseOrder.totalQuantity = totalQuantity;
            $scope.newPurchaseOrder.totalAmount = totalAmount;

            // 采购日期
            $scope.newPurchaseOrder.purchaseDate = $("#purchaseDate").val();
            $scope.newPurchaseOrder.orderType = "01";

            inventoryService.savePurchaseOrder($scope.newPurchaseOrder, function (data) {
                if(data.success){
                    $location.path("/purchaseOrderManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '创建失败:'+data.errorMsg
                    });
                }
                $("#saveBtn").attr("disabled", false);
                $("#saveBtn").text("保存");
                $("#cancelBtn").attr("disabled", false);
            }, function (data) {
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '错误',
                    message : data.errorMsg
                });
            });
        }

        $scope.delItem = function(index){
            $scope.purchaseOrderItemList.splice(index, 1);
        }

        $scope.addItem = function(){
            var item = {};
            item.sphere = 0;
            item.cylinder = 0;
            item.purchaseQuantity = 1;
            $scope.purchaseOrderItemList.push(item);
        }

        $scope.cancel = function(){
            history.back();
        }

        $scope.closeBtn = function () {
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }
    }).controller("editBatchPurchaseOrderController",function($location,$scope,$filter,materielService,inventoryService,storeService,supplierService,adminUserService,$routeParams,dictionaryService){
        $scope.selectPurchaseOrder={};
        $scope.purchaseOrderItemList = [];
        $scope.storeList = [];
        $scope.supplierList = [];
        $scope.adminUserList = [];
        $scope.materielList = [];

        $scope.selectedMateriel = {}; //选择的商品
        $scope.purchaseOrderItem = {}; //采购单明细
        $scope.purchaseOrderItem.purchasePrice = 0;
        $scope.purchaseOrderItem.purchaseQuantity = 0;

        // 查询采购单
        inventoryService.getPurchaseOrderById($routeParams.id, function(data){
            $scope.selectPurchaseOrder = data.data;
            $scope.purchaseOrderItemList = $scope.selectPurchaseOrder.purchaseOrderItemList;

            // 查询商品
            materielService.fetchMaterielList(function(data){
                // 过滤出当前供应商的商品
                angular.forEach(data.data, function (each) {
                    if(each.supplierId == $scope.selectPurchaseOrder.supplierId){
                        $scope.materielList.push(each);
                    }
                });

                // 初始化商品下拉框
                angular.element("#materiel").find("option").remove();
                $("#materiel").append("<option value=''></option>");
                for(var i=0; i<$scope.materielList.length; i++){
                    $("#materiel").append("<option value='" + $scope.materielList[i].id + "'>" + $scope.materielList[i].name + "</option>");
                }
                // 先删除原有商品下拉框
                angular.element(".searchable-select").remove();
                // 新建商品下拉框
                $("#materiel").searchableSelect(function (materielId) {
                    if(angular.isDefined(materielId) && materielId != null && materielId != ''){
                        for(var i=0; i<$scope.materielList.length; i++){
                            if($scope.materielList[i].id == materielId){
                                $scope.selectedMateriel = $scope.materielList[i];
                                $scope.purchaseOrderItem.materielId = materielId;
                                $scope.switchHideAndShow($scope.materielList[i].typeId);
                                break;
                            }
                        }
                    }else {
                        $scope.selectedMateriel = {};
                        $scope.purchaseOrderItem.materielId = null;
                        $scope.switchHideAndShow(0);
                    }
                    $scope.purchaseOrderItem.sphere = 0;
                    $scope.purchaseOrderItem.cylinder = 0;
                    $scope.purchaseOrderItem.axial = 0;
                    $scope.$apply();
                });
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取商品错误' + data.errorMsg
                });
            });
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
            });
        });

        // 查询门店
        storeService.fetchStoreList(function(data){
            $scope.storeList = data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取门店失败:' + data.errorMsg
            });
        });

        // 查询供应商
        supplierService.fetchUseableSupplierList(function (data) {
            $scope.supplierList = data.data;
        },function (data) {
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取供应商失败:' + data.errorMsg
            });
        });

        $scope.goodsTypeList = [];//商品类型
        $scope.selectGoodsTypeId = 0; //选择的商品类型
        // 查询商品类型
        dictionaryService.fetchGoodsTypeList(function(data){
            $scope.goodsTypeList = data.data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取商品类型失败:' + data.errorMsg
            });
        });

        // 查询业务员
        adminUserService.fetchUserListByStore(function(data){
            $scope.adminUserList  =  data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取业务员失败:' + data.errorMsg
            });
        });

        // 根据商品类型控制球镜柱镜度数字段的显示和隐藏
        $scope.switchHideAndShow = function(typeId){
            // 老花眼镜
            if(typeId == 6){
                $scope.sphereDisplay = false;
                $scope.cylinderDisplay = false;
                $scope.axialDisplay = true;
                $("#axial").attr({"min" : 1.00, "max" : 4, "step" : 0.5});
                $("#axial").val(1.00);
            }
            // 隐形眼镜
            else if(typeId == 2){
                $scope.sphereDisplay = false;
                $scope.cylinderDisplay = false;
                $scope.axialDisplay = true;
                $("#axial").attr({"min" : -20, "max" : 0, "step" : 0.25});
                $("#axial").val(0);
            }
            // 镜片
            else if(typeId == 1){
                $scope.sphereDisplay = true;
                $scope.cylinderDisplay = true;
                $scope.axialDisplay = false;
            }
            // 镜架、护理产品、太阳镜
            else if(typeId == 5 || typeId == 6 || typeId == 10){
                $scope.sphereDisplay = false;
                $scope.cylinderDisplay = false;
                $scope.axialDisplay = false;
            }else {
                $scope.sphereDisplay = true;
                $scope.cylinderDisplay = true;
                $scope.axialDisplay = true;
            }
        }

        $scope.newPurchaseOrderItem_ = function (){
            $('.bg').css({'display':'block'});
            $('.content').css({'display':'block'});

            //$scope.selectedMateriel = {}; //选择的商品
            //$scope.purchaseOrderItem = {}; //采购单明细
            $scope.purchaseOrderItem.sphere = 0;
            $scope.purchaseOrderItem.cylinder = 0;
            $scope.purchaseOrderItem.axial = 0;
            $scope.purchaseOrderItem.purchasePrice = 0;
            $scope.purchaseOrderItem.purchaseQuantity = 1;
        }

        $scope.closeBtn_ = function () {
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        // 选择商品类型
        $scope.selectGoodsType = function () {
            $scope.usedMaterielList = [];
            // 过滤出当前选中的类型的商品
            angular.forEach($scope.materielList, function (each) {
                if($scope.selectGoodsTypeId > 0){
                    if(each.typeId == $scope.selectGoodsTypeId){
                        $scope.usedMaterielList.push(each);
                    }
                }else {
                    $scope.usedMaterielList.push(each);
                }
            });
            // 初始化商品下拉框
            angular.element("#materiel").find("option").remove();
            $("#materiel").append("<option value=''></option>");
            for(var i=0; i<$scope.usedMaterielList.length; i++){
                $("#materiel").append("<option value='" + $scope.usedMaterielList[i].id + "'>" + $scope.usedMaterielList[i].name + "</option>");
            }
            // 先删除原有商品下拉框
            angular.element(".searchable-select").remove();
            // 新建商品下拉框
            $("#materiel").searchableSelect(function (materielId) {
                if(angular.isDefined(materielId) && materielId != null && materielId != ''){
                    for(var i=0; i<$scope.materielList.length; i++){
                        if($scope.materielList[i].id == materielId){
                            $scope.selectedMateriel = $scope.materielList[i];
                            $scope.purchaseOrderItem.materielId = materielId;
                            $scope.switchHideAndShow($scope.materielList[i].typeId);
                            break;
                        }
                    }
                }else {
                    $scope.selectedMateriel = {};
                    $scope.purchaseOrderItem.materielId = null
                    $scope.switchHideAndShow(0);
                }
                $scope.purchaseOrderItem.sphere = 0;
                $scope.purchaseOrderItem.cylinder = 0;
                $scope.purchaseOrderItem.axial = 0;
                $scope.$apply();
            });
        }

        // 添加新明细
        $scope.addItem_ = function () {
            var item = angular.copy($scope.purchaseOrderItem);
            item.itemId = 0;
            item.purchaseOrderId = $scope.selectPurchaseOrder.purchaseOrderId;
            item.materielId = $scope.selectedMateriel.id;

            inventoryService.savePurchaseOrderItem(item, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '新增明细成功'
                    });

                    item.itemId = data.data.itemId;
                    item.materiel = $scope.selectedMateriel;
                    $scope.purchaseOrderItemList.push(item);

                    // 采购数量累计
                    $scope.selectPurchaseOrder.totalQuantity += Number($scope.purchaseOrderItem.purchaseQuantity);
                    // 采购金额累计
                    $scope.selectPurchaseOrder.totalAmount += $scope.purchaseOrderItem.purchasePrice * $scope.purchaseOrderItem.purchaseQuantity;
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '新增明细失败:'+data.errorMsg
                    });
                }
            }, function (data) {
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '失败',
                    message : '新增明细失败:'+data.errorMsg
                });
            });

            // 关闭明细弹框
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        // 更新采购单(只更新采购单信息，采购单明细在编辑采购单时单独新增和删除)
        $scope.updatePurchaseOrder = function () {
            // 采购日期
            $scope.selectPurchaseOrder.purchaseDate = $("#purchaseDate").val();

            inventoryService.updatePurchaseOrder($scope.selectPurchaseOrder, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '采购单更新成功'
                    });
                    $location.path("/purchaseOrderManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '采购单更新失败:'+data.errorMsg
                    });
                }
            }, function (data) {
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '失败',
                    message : '采购单更新失败:'+data.errorMsg
                });
            });
        }

        // 删除明细
        $scope.delItem_ = function (index) {
            var item = $scope.selectPurchaseOrder.purchaseOrderItemList[index];
            if(confirm("确定删除该条明细吗？")){
                inventoryService.removePurchaseOrderItem(item.itemId, function (data) {
                    if(data.success){
                        BootstrapDialog.show({
                            type : BootstrapDialog.TYPE_SUCCESS,
                            title : '成功',
                            message : '明细删除成功'
                        });
                        // 将明细从列表删除
                        $scope.selectPurchaseOrder.purchaseOrderItemList.splice(index, 1);
                        // 采购单总数量
                        $scope.selectPurchaseOrder.totalQuantity -= item.purchaseQuantity;
                        // 采购单总金额
                        $scope.selectPurchaseOrder.totalAmount -= item.purchasePrice * item.purchaseQuantity;
                    }else {
                        BootstrapDialog.show({
                            type : BootstrapDialog.TYPE_DANGER,
                            title : '失败',
                            message : '明细删除失败:'+data.errorMsg
                        });
                    }
                }, function (data) {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '明细删除失败:'+data.errorMsg
                    });
                });
            }
        }

        $scope.cancel = function(){
            history.back();
        }
    }).controller("batchPurchaseOrderDetailController",function($location,$scope,$filter,materielService,inventoryService,storeService,supplierService,adminUserService,$routeParams,dictionaryService){
        $scope.selectPurchaseOrder={};
        $scope.purchaseOrderItemList = [];
        $scope.storeList = [];
        $scope.supplierList = [];
        $scope.adminUserList = [];
        $scope.materielList = [];

        // 查询采购单
        inventoryService.getPurchaseOrderById($routeParams.id, function(data){
            $scope.selectPurchaseOrder = data.data;
            $scope.purchaseOrderItemList = $scope.selectPurchaseOrder.purchaseOrderItemList;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
            });
        });

        // 查询门店
        storeService.fetchStoreList(function(data){
            $scope.storeList = data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取门店失败:' + data.errorMsg
            });
        });

        // 查询供应商
        supplierService.fetchUseableSupplierList(function (data) {
            $scope.supplierList = data.data;
        },function (data) {
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取供应商失败:' + data.errorMsg
            });
        });

        // 查询业务员
        adminUserService.fetchUserListByStore(function(data){
            $scope.adminUserList  =  data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取业务员失败:' + data.errorMsg
            });
        });

        $scope.cancel = function(){
            history.back();
        }
    });

    function setActiveSubPage($scope, pageName) {
        $scope.$emit("setActive", pageName);
    }
})();