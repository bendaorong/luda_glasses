(function() {
    angular.module("businessOperationApp").controller("salesOrderManageController", function($scope, NgTableParams, $filter, $location, salesService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;
        $scope.salesOrderList = [];

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }

        // 显示销售单列表
        function initSalesOrderList() {
            salesService.fetchSalesOrderVoList("01", function(data){
                if(data.success){
                    $scope.salesOrderList = data.data;
                    $scope.tableParams = new NgTableParams({}, {
                        dataset : $scope.salesOrderList
                    });
                    console.log("size:"+$scope.salesOrderList.length);
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '错误',
                        message : '获取销售单失败' + data.errorMsg
                    });
                }
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '错误',
                    message : '获取销售单失败' + data.errorMsg
                });
            });
        }
        initSalesOrderList();

        $scope.refresh = function() {
            $scope.$emit("loadingStart");
            initSalesOrderList();
            $scope.$emit("loadingEnd");
        }

        // 编辑销售单
        $scope.gotoEdit = function(id) {
            $location.path("/editSalesOrder/" + id);
        }

        //删除销售单
        $scope.removeSalesOrder = function(id){
            if(confirm("确认删除吗？")){
                salesService.removeSalesOrder(id, function (data) {
                    if(data.success){
                        // BootstrapDialog.show({
                        //     type : BootstrapDialog.TYPE_SUCCESS,
                        //     title : '成功',
                        //     message : '删除成功'
                        // });
                        $scope.refresh();
                    }else {
                        BootstrapDialog.show({
                            type : BootstrapDialog.TYPE_DANGER,
                            title : '失败',
                            message : '删除失败' + data.errorMsg
                        });
                    }
                }, function (data) {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '删除失败' + data.errorMsg
                    });
                });
            }
        }

        // 创建退货单
        $scope.addRefundOrder = function (id) {
            $location.path("/addRefundOrder/" + id);
        }
    }).controller("addSalesOrderController", function($location,$scope,$filter,inventoryService,materielService,salesService,storeService,adminUserService,customerService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.adminUserId = sessionStorage.getItem("adminUserId");
        $scope.storeId = sessionStorage.getItem("storeId");
        $scope.currentTab = 0;

        $scope.newSalesOrder = {};   // 销售单
        $scope.salesOrderItems = []; // 销售单明细
        $scope.newSalesOrder.salesOrderItems = $scope.salesOrderItems;
        // 销售日期默认为当前时间
        $scope.newSalesOrder.saleDate = $filter("date")(new Date(), "yyyy-MM-dd");
        $scope.newSalesOrder.totalQuantity = 0;
        $scope.newSalesOrder.totalAmount = 0;
        $scope.newSalesOrder.storeId = $scope.storeId;
        $scope.newSalesOrder.businessManId = $scope.adminUserId;

        $scope.salesOrderItem = {};  //销售单明细
        $scope.salesOrderItem.quantity = 0;
        $scope.salesOrderItem.sellPrice = 0;

        $scope.storeList = []; //门店
        $scope.adminUserList = []; //业务员
        $scope.materielList = []; //商品
        $scope.customerList = []; //客户

        $scope.mardList = []; //商品库存
        $scope.selectedMard = {};

        // 查询客户
        $scope.customerSearchableSelect;
        customerService.fetchCustomerList(function (data) {
            $scope.customerList = data;
            for(var i=0; i<data.length; i++){
                $("#customer").append("<option value='" + data[i].id + "'>" + data[i].name + "-" + data[i].mobileNumber + "</option>");
            }
            $scope.customerSearchableSelect = $("#customer").searchableSelect(function (customerId) {
                if(angular.isDefined(customerId) && customerId != null && customerId != ''){
                    $scope.newSalesOrder.customerId = customerId;
                    $scope.$apply();
                }
            });

        }, function (data) {
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取客户列表错误' + data.errorMsg
            });
        });

        // 查询商品库存
        inventoryService.fetchMardVoList(function(data){
            $scope.mardList = data.data;
            if($scope.mardList.length > 0){
                for(var i=0; i<$scope.mardList.length; i++){
                    var tempMard = $scope.mardList[i];
                    $("#materiel").append("<option value='" + tempMard.id + "'>" + tempMard.materiel.name + "("+tempMard.sphere+", " + tempMard.cylinder + ", " + tempMard.axial +")" + "</option>");
                }
            }
            $("#materiel").searchableSelect(function (mardId) {
                if(angular.isDefined(mardId) && mardId != null && mardId != ''){
                    for(var i=0; i<$scope.mardList.length; i++){
                        if($scope.mardList[i].id == mardId){
                            $scope.selectedMard = $scope.mardList[i];
                            $scope.salesOrderItem.mardId = mardId;
                            $scope.salesOrderItem.materielId = $scope.selectedMard.materielId;
                            $scope.salesOrderItem.sellPrice = $scope.selectedMard.materiel.sellPrice;
                            $scope.$apply();
                            break;
                        }
                    }
                }else {
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

        $scope.customer = {}; //临时添加新客户
        $scope.newCustomer = function () {
            $('.bg').css({'display':'block'});
            $('.customerContent').css({'display':'block'});
        }

        $scope.addCustomer = function () {
            customerService.addCustomer($scope.customer, function (data) {
                // 关闭明细弹框
                $('.bg').css({'display':'none'});
                $('.customerContent').css({'display':'none'});
            }, function (data) {
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '客户添加失败:' + data.errorMsg
                });
            });
        }

        $scope.closeCustomerBtn = function () {
            $('.bg').css({'display':'none'});
            $('.customerContent').css({'display':'none'});
        }

        $scope.newItem = function (){
            $('.bg').css({'display':'block'});
            $('.content').css({'display':'block'});

            $scope.selectedMateriel = {}; //选择的商品
            $scope.salesOrderItem = {}; //销售单明细
            $scope.salesOrderItem.quantity = 0;
            $scope.salesOrderItem.sellPrice = 0;
        }

        // 添加新明细
        $scope.addItem = function () {
            if($scope.salesOrderItem.mardId == null){
                alert("请选择商品");
                return false;
            }
            if($scope.salesOrderItem.quantity == null){
                alert("请填写销售数量");
                return false;
            }
            if($scope.salesOrderItem.quantity <= 0){
                alert("销售数量必须大于0");
                return false;
            }
            if($scope.salesOrderItem.quantity > $scope.selectedMard.currentInventory){
                alert("库存不足");
                return false;
            }

            var item = {};
            item.itemId = 0;
            item.materiel = $scope.selectedMard.materiel;
            item.mardId = $scope.selectedMard.id;
            item.materielId = $scope.selectedMard.materielId;
            item.sphere = $scope.selectedMard.sphere;
            item.cylinder = $scope.selectedMard.cylinder;
            item.axial = $scope.selectedMard.axial;
            item.quantity = $scope.salesOrderItem.quantity;
            item.sellPrice = $scope.salesOrderItem.sellPrice;
            item.remark = $scope.salesOrderItem.remark;
            $scope.salesOrderItems.push(item);

            // 销售单总数量
            $scope.newSalesOrder.totalQuantity += Number($scope.salesOrderItem.quantity);
            // 销售单总金额
            $scope.newSalesOrder.totalAmount += $scope.salesOrderItem.sellPrice * $scope.salesOrderItem.quantity;

            // 关闭明细弹框
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        // 删除明细
        $scope.delItem = function (index) {
            if(confirm("确定删除吗?")){
                var item = $scope.salesOrderItems[index];
                // 销售单总数量
                $scope.newSalesOrder.totalQuantity -= Number(item.quantity);
                // 销售单总金额
                $scope.newSalesOrder.totalAmount -= item.sellPrice * item.quantity;
                $scope.salesOrderItems.splice(index, 1);
            }
        }

        // 保存销售单
        $scope.saveSalesOrder = function () {
            if($scope.salesOrderItems.length == 0){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '请添加销售明细'
                });
                return false;
            }
            // 销售日期
            $scope.newSalesOrder.saleDate = $("#saleDate").val();
            $scope.newSalesOrder.pickUpDate = $("#pickUpDate").val();
            $scope.newSalesOrder.orderType = "01"; //销售单
            console.log("newSalesOrder:" + JSON.stringify($scope.newSalesOrder));

            salesService.saveSalesOrder($scope.newSalesOrder, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '销售单创建成功'
                    });
                    $location.path("/salesOrderManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '销售单创建失败:'+data.errorMsg
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
    }).controller("editSalesOrderController", function($location,$scope,$filter,inventoryService,materielService,salesService,storeService,adminUserService,customerService,$routeParams) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;

        $scope.selectedSalesOrder = {};   // 销售单
        $scope.salesOrderItems = []; // 销售单明细

        $scope.storeList = []; //门店
        $scope.adminUserList = []; //业务员
        $scope.materielList = []; //商品
        $scope.customerList = []; //客户

        $scope.mardList = []; //商品库存
        $scope.selectedMard = {}; //选择的商品库存

        $scope.salesOrderItem = {};  //销售单明细
        $scope.salesOrderItem.quantity = 0;
        $scope.salesOrderItem.sellPrice = 0;

        // 查询商品库存
        inventoryService.fetchMardVoList(function(data){
            $scope.mardList = data.data;
            if($scope.mardList.length > 0){
                for(var i=0; i<$scope.mardList.length; i++){
                    var tempMard = $scope.mardList[i];
                    $("#materiel").append("<option value='" + tempMard.id + "'>" + tempMard.materiel.name + "("+tempMard.sphere+", " + tempMard.cylinder + ", " + tempMard.axial +")" + "</option>");
                }
            }
            $("#materiel").searchableSelect(function (mardId) {
                if(angular.isDefined(mardId) && mardId != null && mardId != ''){
                    for(var i=0; i<$scope.mardList.length; i++){
                        if($scope.mardList[i].id == mardId){
                            $scope.selectedMard = $scope.mardList[i];
                            $scope.salesOrderItem.mardId = mardId;
                            $scope.salesOrderItem.materielId = $scope.selectedMard.materielId;
                            $scope.salesOrderItem.sellPrice = $scope.selectedMard.materiel.sellPrice;
                            $scope.$apply();
                            break;
                        }
                    }
                }else {
                }
            });
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取商品错误' + data.errorMsg
            });
        });

        // 查询销售单
        salesService.getSalesOrderWithItemsById($routeParams.id, function (data) {
            $scope.selectedSalesOrder = data.data
        }, function (data) {
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取销售单失败：' + data.errorMsg
            });
        });

        // 查询客户
        customerService.fetchCustomerList(function (data) {
            $scope.customerList = data;
        }, function (data) {
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取客户列表错误' + data.errorMsg
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
        adminUserService.fetchUserListByStore(function(data){
            $scope.adminUserList  =  data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取业务员失败:' + data.errorMsg
            });
        });

        $scope.newItem = function (){
            $('.bg').css({'display':'block'});
            $('.content').css({'display':'block'});

            $scope.selectedMateriel = {}; //选择的商品
            $scope.salesOrderItem = {}; //销售单明细
            $scope.salesOrderItem.quantity = 0;
            $scope.salesOrderItem.sellPrice = 0;
        }

        // 添加新明细
        $scope.addItem = function () {
            if($scope.salesOrderItem.materielId == null){
                alert("请选择商品");
                return false;
            }
            if($scope.salesOrderItem.quantity == null){
                alert("请填写销售数量");
                return false;
            }
            if($scope.salesOrderItem.quantity <= 0){
                alert("销售数量必须大于0");
                return false;
            }
            if($scope.salesOrderItem.sellPrice <= 0){
                alert("销售单价必须大于0");
                return false;
            }

            var item = {};
            item.itemId = 0;
            item.salesOrderId = $scope.selectedSalesOrder.id;
            item.mardId = $scope.selectedMard.id;
            item.materielId = $scope.selectedMard.materielId;
            item.sphere = $scope.selectedMard.sphere;
            item.cylinder = $scope.selectedMard.cylinder;
            item.axial = $scope.selectedMard.axial;
            item.quantity = $scope.salesOrderItem.quantity;
            item.sellPrice = $scope.salesOrderItem.sellPrice;
            item.remark = $scope.salesOrderItem.remark;
            // 增加明细
            salesService.saveSalesOrderItem(item, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '明细添加成功'
                    });

                    // 明细添加到明细列表
                    item.itemId = data.data.itemId;
                    item.materiel = $scope.selectedMard.materiel;
                    $scope.selectedSalesOrder.salesOrderItems.push(item);

                    // 销售单总数量
                    $scope.selectedSalesOrder.totalQuantity += Number($scope.salesOrderItem.quantity);
                    // 销售单总金额
                    $scope.selectedSalesOrder.totalAmount += $scope.salesOrderItem.sellPrice * $scope.salesOrderItem.quantity;
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '明细添加失败:'+data.errorMsg
                    });
                }
            }, function (data) {
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '失败',
                    message : '明细添加失败:'+data.errorMsg
                });
            });

            // 关闭明细弹框
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        // 删除明细
        $scope.delItem = function (index) {
            var item = $scope.selectedSalesOrder.salesOrderItems[index];
            if(item.itemId > 0){
                if(confirm("确定删除该条明细吗？")){
                    salesService.removeSalesOrderItem(item.itemId, function (data) {
                        if(data.success){
                            BootstrapDialog.show({
                                type : BootstrapDialog.TYPE_SUCCESS,
                                title : '成功',
                                message : '明细删除成功'
                            });
                            // 将明细从列表删除
                            $scope.selectedSalesOrder.salesOrderItems.splice(index, 1);
                            // 销售单总数量
                            $scope.selectedSalesOrder.totalQuantity -= Number(item.quantity);
                            // 销售单总金额
                            $scope.selectedSalesOrder.totalAmount -= item.sellPrice * item.quantity;
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
            }else {
                // 将明细从列表删除
                $scope.selectedSalesOrder.salesOrderItems.splice(index, 1);
                // 销售单总数量
                $scope.selectedSalesOrder.totalQuantity -= Number(item.quantity);
                // 销售单总金额
                $scope.selectedSalesOrder.totalAmount -= item.sellPrice * item.quantity;
            }
        }

        // 保存销售单
        $scope.updateSalesOrder = function () {
            // 销售日期
            $scope.selectedSalesOrder.saleDate = $("#saleDate").val();
            $scope.selectedSalesOrder.pickUpDate = $("#pickUpDate").val();
            console.log("selectedSalesOrder:" + JSON.stringify($scope.selectedSalesOrder));

            salesService.updateSalesOrder($scope.selectedSalesOrder, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '销售单更新成功'
                    });
                    $location.path("/salesOrderManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '销售单更新失败:'+data.errorMsg
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
    }).controller("addRefundOrderController", function($scope, NgTableParams, $filter, $location, $routeParams, salesService,customerService, storeService, adminUserService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;

        $scope.selectedSalesOrder = {}; //销售单
        $scope.newRefundOrder = {}; //退货单

        // 退货日期默认为当前时间
        $scope.newRefundOrder.refundDate = $filter("date")(new Date(), "yyyy-MM-dd");
        $scope.newRefundOrder.totalQuantity = 0;
        $scope.newRefundOrder.totalAmount = 0;

        $scope.storeList = []; //门店
        $scope.adminUserList = []; //业务员
        $scope.materielList = []; //商品
        $scope.customerList = []; //客户

        // 查询销售单
        salesService.getSalesOrderWithItemsById($routeParams.id, function (data) {
            $scope.selectedSalesOrder = data.data;
            // 退货单信息
            $scope.newRefundOrder.salesOrderId = $scope.selectedSalesOrder.id;
            $scope.newRefundOrder.customerId = $scope.selectedSalesOrder.customerId;
            $scope.newRefundOrder.storeId = $scope.selectedSalesOrder.storeId;
        }, function (data) {
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取销售单失败：' + data.errorMsg
            });
        });

        // 查询客户
        customerService.fetchCustomerList(function (data) {
            $scope.customerList = data;
        }, function (data) {
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取客户列表错误' + data.errorMsg
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

        // 选择需要退货的商品明细
        $scope.chooseItem = function (index) {
            var item = $scope.selectedSalesOrder.salesOrderItems[index];
            // 计算退货单的总数量和总金额
            if($("#item_" + item.itemId).is(":checked")){
                $scope.newRefundOrder.totalQuantity += item.quantity;
                $scope.newRefundOrder.totalAmount += item.quantity * item.sellPrice;
            }else {
                $scope.newRefundOrder.totalQuantity -= item.quantity;
                $scope.newRefundOrder.totalAmount -= item.quantity * item.sellPrice;
            }
        }

        // 保存退货单
        $scope.saveRefundOrder = function () {
            var refundOrderItems = [];
            //获取勾选的退货明细
            $("input[name='refundItem']:checkbox:checked").each(function () {
                for(var i=0; i<$scope.selectedSalesOrder.salesOrderItems.length; i++){
                    var item_ = $scope.selectedSalesOrder.salesOrderItems[i];
                    if(item_.itemId == $(this).val()){
                        var refundOrderItem = {};
                        refundOrderItem.salesOrderItemId = item_.itemId;
                        refundOrderItem.materielId = item_.materielId;
                        refundOrderItem.quantity = item_.quantity;
                        refundOrderItem.sellPrice = item_.sellPrice;
                        refundOrderItem.remark = item_.remark;
                        refundOrderItems.push(refundOrderItem);
                    }
                }
            });
            console.log(JSON.stringify(refundOrderItems));
            $scope.newRefundOrder.refundOrderItems = refundOrderItems;

            if(refundOrderItems.length == 0){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '请选择退货商品'
                });
                return false;
            }

            salesService.saveRefundOrder($scope.newRefundOrder, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '退货单创建成功'
                    });
                    $location.path("/salesOrderManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '退货单创建失败:' + data.errorMsg
                    });
                }
            }, function (data) {
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '失败',
                    message : '退货单创建失败:' + data.errorMsg
                });
            });
        }

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }

        $scope.cancel = function(){
            history.back();
        }
    });

    function setActiveSubPage($scope) {
        $scope.$emit("setActive", "salesOrderManage");
    }
})();
