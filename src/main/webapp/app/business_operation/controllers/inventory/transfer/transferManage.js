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

        //删除商品
        $scope.removeTransferOrder = function(id){
            if(confirm("确认删除吗？")){
                inventoryService.removeTransferOrder(id, function(data){
                    if(data.success){
                        BootstrapDialog.show({
                            type : BootstrapDialog.TYPE_SUCCESS,
                            title : '消息',
                            message : '删除成功'
                        });
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
    }).controller("addTransferOrderController", function($location,$scope,$filter,materielService,inventoryService,storeService,adminUserService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;

        $scope.newTransferOrder = {};   //调拨单
        $scope.transferOrderItems = []; //调拨单明细
        $scope.newTransferOrder.transferOrderItems = $scope.transferOrderItems;

        $scope.storeList = []; //门店
        $scope.adminUserList = []; //业务员
        $scope.materielList = []; //商品

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
            $scope.storeList = data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取门店失败:' + data.errorMsg
            });
        });

        // 查询业务员
        adminUserService.fetchUserList(function(data){
            $scope.adminUserList  =  data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取业务员失败:' + data.errorMsg
            });
        });

        $scope.selectedMateriel = {};   //选择的商品
        $scope.transferOrderItem = {};  //调拨单明细

        $scope.newItem = function (){
            $('.bg').css({'display':'block'});
            $('.content').css({'display':'block'});

            $scope.selectedMateriel = {}; //选择的商品
            $scope.transferOrderItem = {}; //采购单明细
        }

        // 选择商品时加载数据
        $('#abc').change(function(){
            var materielId = $(this).children('option:selected').val();
            for(var i=0; i<$scope.materielList.length; i++){
                if($scope.materielList[i].id == materielId){
                    $scope.selectedMateriel = $scope.materielList[i];
                    break;
                }
            }
            // 查询商品库存
            inventoryService.getMard(materielId, $scope.newTransferOrder.outStoreId, function (data) {
                $scope.selectedMateriel.currentInventory = data.data;
            }, function (data) {

            });
        })

        // 添加新明细
        $scope.addItem = function () {
            if($scope.transferOrderItem.materielId == null){
                alert("请选择商品");
                return false;
            }
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

            materiel.code = $scope.selectedMateriel.code;
            materiel.name = $scope.selectedMateriel.name;
            materiel.color = $scope.selectedMateriel.color;
            materiel.barcode = $scope.selectedMateriel.barcode;
            materiel.typeName = $scope.selectedMateriel.typeName;
            materiel.specification = $scope.selectedMateriel.specification;

            item.itemId = 0;
            item.materielId = $scope.transferOrderItem.materielId;
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
    }).controller("editTransferOrderController", function($location,$scope,$filter,$routeParams,materielService,inventoryService,storeService,adminUserService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;

        $scope.selectTransferOrder = {};   //调拨单

        $scope.storeList = []; //门店
        $scope.adminUserList = []; //业务员
        $scope.materielList = []; //商品

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
        adminUserService.fetchUserList(function(data){
            $scope.adminUserList  =  data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取业务员失败:' + data.errorMsg
            });
        });

        $scope.selectedMateriel = {};   //选择的商品
        $scope.transferOrderItem = {};  //调拨单明细

        $scope.newItem = function (){
            $('.bg').css({'display':'block'});
            $('.content').css({'display':'block'});

            $scope.selectedMateriel = {}; //选择的商品
            $scope.transferOrderItem = {}; //采购单明细
        }

        // 选择商品时加载数据
        $('#abc').change(function(){
            var materielId = $(this).children('option:selected').val();
            for(var i=0; i<$scope.materielList.length; i++){
                if($scope.materielList[i].id == materielId){
                    $scope.selectedMateriel = $scope.materielList[i];
                    break;
                }
            }
            // 查询商品库存
            inventoryService.getMard(materielId, $scope.selectTransferOrder.outStoreId, function (data) {
                $scope.selectedMateriel.currentInventory = data.data;
            }, function (data) {

            });
        })

        // 添加新明细
        $scope.addItem = function () {
            if($scope.transferOrderItem.materielId == null){
                alert("请选择商品");
                return false;
            }
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
            item.materielId = $scope.transferOrderItem.materielId;
            item.quantity = $scope.transferOrderItem.quantity;
            item.remark = $scope.transferOrderItem.remark;
            inventoryService.saveTransferOrderItem(item, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '新增明细成功'
                    });

                    item.itemId = data.data.itemId;
                    var materiel = {};
                    materiel.code = $scope.selectedMateriel.code;
                    materiel.name = $scope.selectedMateriel.name;
                    materiel.color = $scope.selectedMateriel.color;
                    materiel.barcode = $scope.selectedMateriel.barcode;
                    materiel.typeName = $scope.selectedMateriel.typeName;
                    materiel.specification = $scope.selectedMateriel.specification;
                    item.materiel = materiel;
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
    });

    function setActiveSubPage($scope) {
        $scope.$emit("setActive", "transferOrderManage");
    }
})();
