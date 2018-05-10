(function() {
    angular.module("businessOperationApp").controller("transferOrderManageController", function($scope, NgTableParams, $filter, $location, inventoryService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;
        $scope.transferOrderList = [];

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }

        // 显示调拨单列表
        function initTransferOrderList() {
            inventoryService.fetchTransferOrders(function(data){
                if(data.success){
                    $scope.transferOrderList = data.data;
                    $scope.tableParams = new NgTableParams({}, {
                        dataset : $scope.transferOrderList
                    });
                    console.log("size:"+$scope.transferOrderList.length);
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '错误',
                        message : '获取调拨单失败' + data.errorMsg
                    });
                }
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '错误',
                    message : '获取调拨单失败' + data.errorMsg
                });
            });
        }
        initTransferOrderList();

        $scope.refresh = function() {
            $scope.$emit("loadingStart");
            initTransferOrderList();
            $scope.$emit("loadingEnd");
        }

        // 编辑调拨单
        $scope.gotoEdit = function(id) {
            $location.path("/editTransferOrder/" + id);
        }

        //删除调拨单
        $scope.removeTransferOrder = function(id){
            if(confirm("确认删除吗？")){
                inventoryService.removeTransferOrder(id, function(data){
                    if(data.success){
                        $scope.refresh();
                    }else {
                        BootstrapDialog.show({
                            type : BootstrapDialog.TYPE_DANGER,
                            title : '失败',
                            message : data.errorMsg
                        });
                    }
                },function(data){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '错误',
                        message : data.errorMsg
                    });
                });
            }
        }
    }).controller("addTransferOrderController", function($location,$scope,$filter,materielService,inventoryService,storeService,adminUserService,NgTableParams) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.storeId = sessionStorage.getItem("storeId");
        $scope.adminUserId = sessionStorage.getItem("adminUserId");

        $scope.currentTab = 0;

        $scope.newTransferOrder = {};   //调拨单
        $scope.transferOrderItems = []; //调拨单明细
        $scope.newTransferOrder.transferOrderItems = $scope.transferOrderItems;
        $scope.newTransferOrder.transferDate = $filter("date")(new Date(), "yyyy-MM-dd");
        $scope.newTransferOrder.outStoreId = $scope.storeId;
        $scope.newTransferOrder.businessmanId = $scope.adminUserId;

        $scope.outStoreList = []; //调出门店
        $scope.inStoreList = []; //调入门店
        $scope.adminUserList = []; //业务员
        $scope.materielList = []; //商品

        $scope.transferOrderItem = {};  //调拨单明细

        $scope.mardList = []; //商品库存

        $scope.filterCondition = {};

        // 查询商品
        materielService.fetchMaterielList(function(data){
            $scope.materielList = data.data;
            for(var i=0; i<$scope.materielList.length; i++){
                $("#materiel").append("<option value='" + $scope.materielList[i].id + "'>" + $scope.materielList[i].name + "</option>");
            }
            $scope.materielSearchableSelect = $("#materiel").searchableSelect(function (materielId) {
                if(angular.isDefined(materielId) && materielId != null && materielId != ''){
                    $scope.filterCondition.materielId = materielId;
                    $scope.$apply();
                }
            });
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取商品错误' + data.errorMsg
            });
        });

        // 查询门店
        storeService.fetchStoreList(function(data){
            //调出门店=登录用户当前所属门店
            //调入门店=除去调出门店的其他所有门店
            angular.forEach(data, function (each) {
                if(each.storeId == $scope.storeId){
                    $scope.outStoreList.push(angular.copy(each));
                }else {
                    $scope.inStoreList.push(angular.copy(each));
                }
            });
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取门店失败:' + data.errorMsg
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

        // init table
        function initMardTable(){
            $scope.filterCondition.storeId = $scope.storeId;
            inventoryService.getMardTotalCount($scope.filterCondition, function(data){
                if(data.success){
                    $scope.mardTable = new NgTableParams({
                        page: 1,
                        count: 10
                    },{
                        total: data.data,
                        getData: function ($defer, params) {
                            $scope.filterCondition.pageNo = params.page();
                            getMardList($defer);
                        }
                    });
                }
            }, function(data){

            });
        }

        // 显示商品库存列表
        function getMardList($defer) {
            inventoryService.fetchMardVoListPage($scope.filterCondition, function(data){
                if(data.success){
                    $scope.mardList = data.data;
                    $defer.resolve($scope.mardList);
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '获取商品库存失败' + data.errorMsg
                    });
                }
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取商品库存错误' + data.errorMsg
                });
            });
        }

        // 查询销售商品
        $scope.queryMateriel = function(){
            initMardTable();
        }

        $scope.transfer = function(mardId){
            $('.bg').css({'display':'block'});
            $('.content').css({'display':'block'});

            angular.forEach($scope.mardList, function (each) {
                if(each.id == mardId){
                    $scope.selectedMard = each;
                }
            });
        }

        // 添加新明细
        $scope.addItem = function () {
            if($scope.transferOrderItem.quantity == null){
                alert("请填写调拨数量");
                return false;
            }
            if($scope.transferOrderItem.quantity <= 0){
                alert("调拨数量必须大于0");
                return false;
            }

            var item = {};
            var materiel = {};
            item.materiel = materiel;
            materiel.name = $scope.selectedMard.materiel.name;
            materiel.typeName = $scope.selectedMard.materiel.typeName;
            item.itemId = 0;
            item.mardId = $scope.selectedMard.id;
            item.materielId = $scope.selectedMard.materiel.id;
            item.sphere = $scope.selectedMard.sphere;
            item.cylinder = $scope.selectedMard.cylinder;
            item.axial = $scope.selectedMard.axial;
            item.quantity = $scope.transferOrderItem.quantity;
            item.remark = $scope.transferOrderItem.remark;
            $scope.transferOrderItems.push(item);

            // 关闭明细弹框
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        // 删除明细
        $scope.delItem = function (index) {
            if(confirm("确定删除吗?")){
                $scope.transferOrderItems.splice(index, 1);
            }
        }

        // 保存调拨单
        $scope.saveTransferOrder = function () {
            if($scope.transferOrderItems.length == 0){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '调拨单明细不能为空'
                });
                return false;
            }
            // 采购日期
            $scope.newTransferOrder.transferDate = $("#transferDate").val();
            console.log("newTransferOrder:" + JSON.stringify($scope.newTransferOrder));

            inventoryService.saveTransferOrder($scope.newTransferOrder, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '调拨单创建成功'
                    });
                    $location.path("/transferOrderManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '调拨单创建失败:'+data.errorMsg
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

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }
    }).controller("editTransferOrderController", function($location,$scope,$filter,$routeParams,materielService,inventoryService,storeService,adminUserService,NgTableParams) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.storeId = sessionStorage.getItem("storeId");
        $scope.currentTab = 0;

        $scope.selectTransferOrder = {};   //调拨单

        $scope.storeList = []; //门店
        $scope.adminUserList = []; //业务员

        $scope.mardList = []; //商品库存
        $scope.selectedMard = {}; //选择的商品库存
        $scope.transferOrderItem = {};  //调拨单明细

        $scope.filterCondition = {};
        $scope.filterCondition.storeId = $scope.storeId;

        // 查询调拨单
        inventoryService.getTransferOrderById($routeParams.id, function (data) {
            if(data.success){
                $scope.selectTransferOrder = data.data;
            }else {
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '失败',
                    message : '调拨单查询失败:'+data.errorMsg
                });
            }
        }, function (data) {
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '失败',
                message : '调拨单查询失败:'+data.errorMsg
            });
        });

        // 查询商品
        materielService.fetchMaterielList(function(data){
            $scope.materielList = data.data;
            for(var i=0; i<$scope.materielList.length; i++){
                $("#materiel").append("<option value='" + $scope.materielList[i].id + "'>" + $scope.materielList[i].name + "</option>");
            }
            $scope.materielSearchableSelect = $("#materiel").searchableSelect(function (materielId) {
                if(angular.isDefined(materielId) && materielId != null && materielId != ''){
                    $scope.filterCondition.materielId = materielId;
                    $scope.$apply();
                }
            });
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取商品错误' + data.errorMsg
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

        // init table
        function initMardTable(){
            inventoryService.getMardTotalCount($scope.filterCondition, function(data){
                if(data.success){
                    $scope.mardTable = new NgTableParams({
                        page: 1,
                        count: 10
                    },{
                        total: data.data,
                        getData: function ($defer, params) {
                            $scope.filterCondition.pageNo = params.page();
                            getMardList($defer);
                        }
                    });
                }
            }, function(data){

            });
        }

        // 显示商品库存列表
        function getMardList($defer) {
            inventoryService.fetchMardVoListPage($scope.filterCondition, function(data){
                if(data.success){
                    $scope.mardList = data.data;
                    $defer.resolve($scope.mardList);
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '获取商品库存失败' + data.errorMsg
                    });
                }
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取商品库存错误' + data.errorMsg
                });
            });
        }

        // 查询销售商品
        $scope.queryMateriel = function(){
            initMardTable();
        }

        $scope.transfer = function(mardId){
            $('.bg').css({'display':'block'});
            $('.content').css({'display':'block'});

            angular.forEach($scope.mardList, function (each) {
                if(each.id == mardId){
                    $scope.selectedMard = each;
                }
            });
        }

        // 添加新明细
        $scope.addItem = function () {
            if($scope.transferOrderItem.quantity == null){
                alert("请填写调拨数量");
                return false;
            }
            if($scope.transferOrderItem.quantity <= 0){
                alert("调拨数量必须大于0");
                return false;
            }

            var item = {};
            item.itemId = 0;
            item.transferOrderId = $scope.selectTransferOrder.id;
            item.mardId = $scope.selectedMard.id;
            item.materielId = $scope.selectedMard.materiel.id;
            item.quantity = $scope.transferOrderItem.quantity;
            item.remark = $scope.transferOrderItem.remark;
            item.sphere = $scope.selectedMard.sphere;
            item.cylinder = $scope.selectedMard.cylinder;
            item.axial = $scope.selectedMard.axial;
            inventoryService.saveTransferOrderItem(item, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '新增明细成功'
                    });

                    item.itemId = data.data.itemId;
                    item.materiel = $scope.selectedMard.materiel;
                    $scope.selectTransferOrder.transferOrderItems.push(item);
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

        // 删除明细
        $scope.delItem = function (index) {
            var itemId = $scope.selectTransferOrder.transferOrderItems[index].itemId;
            if(itemId > 0){
                if(confirm("确定删除吗?")){
                    inventoryService.removeTransferOrderItem(itemId, function (data) {
                        BootstrapDialog.show({
                            type : BootstrapDialog.TYPE_SUCCESS,
                            title : '成功',
                            message : '删除成功'
                        });
                        $scope.selectTransferOrder.transferOrderItems.splice(index, 1);
                    }, function (data) {
                        BootstrapDialog.show({
                            type : BootstrapDialog.TYPE_DANGER,
                            title : '失败',
                            message : '删除失败:'+data.errorMsg
                        });
                    });
                }
            }else {
                $scope.selectTransferOrder.transferOrderItems.splice(index, 1);
            }
        }

        // 保存调拨单
        $scope.updateTransferOrder = function () {
            $scope.selectTransferOrder.transferDate = $("#transferDate").val();
            inventoryService.updateTransferOrder($scope.selectTransferOrder, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '调拨单更新成功'
                    });
                    $location.path("/transferOrderManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '调拨单更新失败:'+data.errorMsg
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

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }
    }).controller("transferOrderDetailController", function($location,$scope,$filter,$routeParams,materielService,inventoryService,storeService,adminUserService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;

        $scope.selectTransferOrder = {};   //调拨单
        $scope.storeList = []; //门店
        $scope.adminUserList = []; //业务员

        // 查询调拨单
        inventoryService.getTransferOrderById($routeParams.id, function (data) {
            if(data.success){
                $scope.selectTransferOrder = data.data;
            }else {
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '失败',
                    message : '调拨单查询失败:'+data.errorMsg
                });
            }
        }, function (data) {
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '失败',
                message : '调拨单查询失败:'+data.errorMsg
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

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }
    });

    function setActiveSubPage($scope) {
        $scope.$emit("setActive", "transferOrderManage");
    }
})();
