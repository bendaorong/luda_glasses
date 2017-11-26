(function() {
    angular.module("businessOperationApp").controller("purchaseOrderManageController", function($scope, $location, NgTableParams, inventoryService) {
        setActiveSubPage($scope, "purchaseOrderManage");

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
                    console.log("size:"+$scope.purchaseOrderList.length);
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
    }).controller("addPurchaseOrderController", function ($location,$scope,$filter,materielService,inventoryService,storeService,supplierService,adminUserService,$routeParams) {
        setActiveSubPage($scope, "addPurchaseOrder");
        $scope.adminUserId = sessionStorage.getItem("adminUserId");
        $scope.storeId = sessionStorage.getItem("storeId");

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
            angular.forEach(data, function (each) {
                if(each.storeId == $scope.storeId){
                    $scope.storeList.push(each);
                }
            });
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取门店失败:' + data.errorMsg
            });
        });

        // 查询供应商
        supplierService.fetchSupplierList(function (data) {
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
                            break;
                        }
                    }
                }else {
                    $scope.selectedMateriel = {};
                    $scope.purchaseOrderItem.materielId = null;
                }
                $scope.purchaseOrderItem.sphere = 0;
                $scope.purchaseOrderItem.cylinder = 0;
                $scope.purchaseOrderItem.axial = 0;
                $scope.$apply();
            });
        }

        $scope.newPurchaseOrderItem = function (){
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

        $scope.closeBtn = function () {
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        // 添加新明细
        $scope.addItem = function () {
            if($scope.purchaseOrderItem.materielId == null){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '请选择商品'
                });
                return false;
            }
            // 镜片商品必须填写球镜、柱镜、轴向
            if($scope.purchaseOrderItem.sphere == null){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '请填写球镜'
                });
                return false;
            }
            if($scope.purchaseOrderItem.cylinder == null){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '请填写柱镜'
                });
                return false;
            }
            if($scope.purchaseOrderItem.axial == null){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '请填写轴向'
                });
                return false;
            }

            if($scope.purchaseOrderItem.purchasePrice == null || $scope.purchaseOrderItem.purchasePrice == 0){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '请填写采购价格'
                });
                return false;
            }
            if($scope.purchaseOrderItem.purchaseQuantity == null || $scope.purchaseOrderItem.purchaseQuantity == 0){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '请填写采购数量'
                });
                return false;
            }

            var item = angular.copy($scope.purchaseOrderItem);
            item.itemId = 0;
            item.materielId = $scope.selectedMateriel.id;

            var materiel = {};
            materiel.name = $scope.selectedMateriel.name;
            materiel.typeName = $scope.selectedMateriel.typeName;
            item.materiel = materiel;

            $scope.purchaseOrderItemList.push(item);

            // 采购单总数量
            $scope.newPurchaseOrder.totalQuantity += Number($scope.purchaseOrderItem.purchaseQuantity);
            // 采购单总金额
            $scope.newPurchaseOrder.totalAmount += $scope.purchaseOrderItem.purchasePrice * $scope.purchaseOrderItem.purchaseQuantity;

            // 关闭明细弹框
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        // 删除采购明细
        $scope.delItem = function (index) {
            if(confirm("确定删除吗?")){
                var delItem = $scope.purchaseOrderItemList[index];
                $scope.purchaseOrderItemList.splice(index, 1);
                // 采购单总数量
                $scope.newPurchaseOrder.totalQuantity -= delItem.purchaseQuantity;
                // 采购单总金额
                $scope.newPurchaseOrder.totalAmount -= delItem.purchasePrice * delItem.purchaseQuantity;
            }
        }

        // 保存采购单
        $scope.savePurchaseOrder = function () {
            if($scope.purchaseOrderItemList.length == 0){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '采购单明细不能为空'
                });
                return false;
            }
            // 采购日期
            $scope.newPurchaseOrder.purchaseDate = $("#purchaseDate").val();
            $scope.newPurchaseOrder.orderType = "01";
            //console.log("newPurchaseOrder:" + JSON.stringify($scope.newPurchaseOrder));

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
            }, function (data) {
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '错误',
                    message : data.errorMsg
                });
            });
        }

        $scope.cancel = function(){
            history.back();
        }

        $scope.closeBtn = function () {
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }
    }).controller("editPurchaseOrderController",function($location,$scope,$filter,materielService,inventoryService,storeService,supplierService,adminUserService,$routeParams){
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
                                break;
                            }
                        }
                    }else {
                        $scope.selectedMateriel = {};
                        $scope.purchaseOrderItem.materielId = null;
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
        supplierService.fetchSupplierList(function (data) {
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

        // 添加新明细
        $scope.addItem_ = function () {
            var item = angular.copy($scope.purchaseOrderItem);
            item.itemId = 0;
            item.purchaseOrderId = $scope.selectPurchaseOrder.purchaseOrderId;
            item.materielId = $scope.selectedMateriel.id;

            //console.log(JSON.stringify(item));
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
            //console.log("selectPurchaseOrder:" + JSON.stringify($scope.selectPurchaseOrder));

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
    });

    function setActiveSubPage($scope, pageName) {
        $scope.$emit("setActive", pageName);
    }
})();