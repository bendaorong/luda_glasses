(function() {
    angular.module("businessOperationApp").controller("purchaseRefundOrderManageController", function($scope, $location, NgTableParams, inventoryService) {
        setActiveSubPage($scope, "purchaseRefundOrderManage");

        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;
        $scope.purchaseOrderList = [];

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }

        // 显示采购退货单列表
        function initPurchaseOrderList() {
            inventoryService.fetchPurchaseOrderList("02", function(data){
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
            $location.path("/editPurchaseRefundOrder/" + id);
        }

        // 删除采购单
        $scope.removePurchaseOrder = function (id) {
            if(confirm("删除采购退货单将同时扣减采购商品的库存，确定删除吗？")){
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
                            message : '删除失败:' + data.errorMsg
                        });
                    }
                }, function (data) {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '警告',
                        message : '删除错误:' + data.errorMsg
                    });
                });
            }
        }
    }).controller("addPurchaseRefundOrderController", function ($location,$scope,$filter,materielService,inventoryService,storeService,supplierService,adminUserService,NgTableParams) {
        setActiveSubPage($scope, "addPurchaseRefundOrder");
        $scope.adminUserId = sessionStorage.getItem("adminUserId");
        $scope.storeId = sessionStorage.getItem("storeId");

        $scope.newPurchaseOrder = {};//采购单
        $scope.newPurchaseOrder.storeId = $scope.storeId;
        $scope.newPurchaseOrder.businessmanId = $scope.adminUserId;
        $scope.newPurchaseOrder.totalQuantity = 0;
        $scope.newPurchaseOrder.totalAmount = 0;
        $scope.newPurchaseOrder.purchaseDate = $filter("date")(new Date(), "yyyy-MM-dd");

        $scope.purchaseOrderItemList = [];//采购明细
        $scope.newPurchaseOrder.purchaseOrderItemList = $scope.purchaseOrderItemList;

        $scope.storeList = [];
        $scope.supplierList = [];
        $scope.adminUserList = [];
        $scope.materielList = [];

        $scope.selectedMateriel = {}; //选择的商品
        $scope.purchaseOrderItem = {}; //采购单明细

        $scope.filterCondition = {};
        $scope.filterCondition.storeId = $scope.storeId;

        // //查询商品库存
        // inventoryService.fetchMardVoList(function(data){
        //     if(data.success){
        //         $scope.mardList = data.data;
        //     }else {
        //         BootstrapDialog.show({
        //             type : BootstrapDialog.TYPE_DANGER,
        //             title : '失败',
        //             message : '获取商品库存失败' + data.errorMsg
        //         });
        //     }
        // },function(data){
        //     BootstrapDialog.show({
        //         type : BootstrapDialog.TYPE_DANGER,
        //         title : '警告',
        //         message : '获取商品库存错误' + data.errorMsg
        //     });
        // });

        // 查询商品
        materielService.fetchMaterielList(function(data){
            $scope.materielList = data.data;
            // for(var i=0; i<$scope.materielList.length; i++){
            //     $("#materiel").append("<option value='" + $scope.materielList[i].id + "'>" + $scope.materielList[i].name + "</option>");
            // }
            // $scope.materielSearchableSelect = $("#materiel").searchableSelect(function (materielId) {
            //     if(angular.isDefined(materielId) && materielId != null && materielId != ''){
            //         $scope.filterCondition.materielId = materielId;
            //         $scope.$apply();
            //     }
            // });
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
                    $scope.usedMaterielList.push(each);
                }
            });
            // 初始化商品下拉框
            angular.element("#materiel").find("option").remove();
            $("#materiel").append("<option value=''></option>");
            angular.forEach($scope.usedMaterielList, function (each) {
                $("#materiel").append("<option value='" + each.id + "'>" + each.name + "</option>");
            });

            // 先删除原有商品下拉框
            angular.element(".searchable-select").remove();
            // 新建商品下拉框
            $("#materiel").searchableSelect(function (materielId) {
                if(angular.isDefined(materielId) && materielId != null && materielId != ''){
                    $scope.filterCondition.materielId = materielId;
                    $scope.$apply();
                    // for(var i=0; i<$scope.usedMardList.length; i++){
                    //     if($scope.usedMardList[i].id == mardId){
                    //         $scope.selectedMard = $scope.usedMardList[i];
                    //         $scope.purchaseOrderItem.mardId = mardId;
                    //         $scope.purchaseOrderItem.materielId = $scope.selectedMard.materielId;
                    //         $scope.purchaseOrderItem.sphere = $scope.selectedMard.sphere;
                    //         $scope.purchaseOrderItem.cylinder = $scope.selectedMard.cylinder;
                    //         $scope.purchaseOrderItem.axial = $scope.selectedMard.axial;
                    //         $scope.$apply();
                    //         break;
                    //     }
                    // }
                }else {
                }
            });
        }

        // 查询销售商品
        $scope.queryMateriel = function(){
            initMardTable();
        }

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

        $scope.refund = function(mardId){
            $('.bg').css({'display':'block'});
            $('.content').css({'display':'block'});

            angular.forEach($scope.mardList, function (each) {
                if(each.id == mardId){
                    $scope.selectedMard = each;
                }
                $scope.purchaseOrderItem.mardId = mardId;
                $scope.purchaseOrderItem.purchasePrice = 0;
                $scope.purchaseOrderItem.purchaseQuantity = 0;
            });
        }

        // $scope.newPurchaseOrderItem = function (){
        //     $('.bg').css({'display':'block'});
        //     $('.content').css({'display':'block'});
        //
        //     $scope.purchaseOrderItem = {}; //采购单明细
        //     $scope.purchaseOrderItem.purchasePrice = 0;
        //     $scope.purchaseOrderItem.purchaseQuantity = 0;
        // }

        $scope.closeBtn = function () {
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        // 添加新明细
        $scope.addItem = function () {
            if($scope.purchaseOrderItem.mardId == null){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '请选择商品'
                });
                return false;
            }
            if($scope.purchaseOrderItem.purchasePrice == null || $scope.purchaseOrderItem.purchasePrice == 0){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '请填写价格'
                });
                return false;
            }
            if($scope.purchaseOrderItem.purchaseQuantity == null || $scope.purchaseOrderItem.purchaseQuantity == 0){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '请填写数量'
                });
                return false;
            }

            var item = angular.copy($scope.purchaseOrderItem);
            item.itemId = 0;
            item.materiel = $scope.selectedMard.materiel;
            item.materielId = $scope.selectedMard.materielId;
            item.sphere = $scope.selectedMard.sphere;
            item.cylinder = $scope.selectedMard.cylinder;
            item.axial = $scope.selectedMard.axial;
            $scope.purchaseOrderItemList.push(item);

            // 采购单总数量
            $scope.newPurchaseOrder.totalQuantity += Number($scope.purchaseOrderItem.purchaseQuantity);
            // 采购单总金额
            $scope.newPurchaseOrder.totalAmount += $scope.purchaseOrderItem.purchasePrice * $scope.purchaseOrderItem.purchaseQuantity;

            // 临时变量重置
            $scope.purchaseOrderItem = {}; //采购单明细
            $scope.purchaseOrderItem.purchasePrice = 0;
            $scope.purchaseOrderItem.purchaseQuantity = 0;

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
            // 退货日期
            $scope.newPurchaseOrder.purchaseDate = $("#purchaseDate").val();
            $scope.newPurchaseOrder.orderType = "02";

            inventoryService.savePurchaseOrder($scope.newPurchaseOrder, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '采购退货单创建成功'
                    });
                    $location.path("/purchaseRefundOrderManage");
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
    }).controller("editPurchaseRefundOrderController",function($location,$scope,$filter,materielService,inventoryService,storeService,supplierService,adminUserService,$routeParams,NgTableParams){
        $scope.selectPurchaseOrder={};
        $scope.purchaseOrderItemList = [];
        $scope.storeList = [];
        $scope.supplierList = [];
        $scope.adminUserList = [];
        $scope.purchaseOrderItem = {}; //采购单明细
        $scope.purchaseOrderItem.purchasePrice = 0;
        $scope.purchaseOrderItem.purchaseQuantity = 0;

        // 查询采购退货单
        inventoryService.getPurchaseOrderById($routeParams.id, function(data){
            $scope.selectPurchaseOrder = data.data;

            // //查询商品库存
            // inventoryService.fetchMardVoList(function(data){
            //     if(data.success){
            //         $scope.usedMardList = [];
            //         // 过滤出当前选中供应商的商品
            //         angular.forEach(data.data, function (each) {
            //             if(each.materiel.supplierId == $scope.selectPurchaseOrder.supplierId){
            //                 $scope.usedMardList.push(each);
            //             }
            //         });
            //         // 初始化商品下拉框
            //         angular.element("#materiel").find("option").remove();
            //         $("#materiel").append("<option value=''></option>");
            //         for(var i=0; i<$scope.usedMardList.length; i++){
            //             var tempMard = $scope.usedMardList[i];
            //             $("#materiel").append("<option value='" + tempMard.id + "'>" + tempMard.materiel.name + "("+tempMard.sphere+", " + tempMard.cylinder + ", " + tempMard.axial +")" + "</option>");
            //         }
            //         // 先删除原有商品下拉框
            //         angular.element(".searchable-select").remove();
            //         // 新建商品下拉框
            //         $("#materiel").searchableSelect(function (mardId) {
            //             if(angular.isDefined(mardId) && mardId != null && mardId != ''){
            //                 for(var i=0; i<$scope.usedMardList.length; i++){
            //                     if($scope.usedMardList[i].id == mardId){
            //                         $scope.selectedMard = $scope.usedMardList[i];
            //                         $scope.purchaseOrderItem.mardId = mardId;
            //                         $scope.purchaseOrderItem.materielId = $scope.selectedMard.materielId;
            //                         $scope.purchaseOrderItem.sphere = $scope.selectedMard.sphere;
            //                         $scope.purchaseOrderItem.cylinder = $scope.selectedMard.cylinder;
            //                         $scope.purchaseOrderItem.axial = $scope.selectedMard.axial;
            //                         $scope.$apply();
            //                         break;
            //                     }
            //                 }
            //             }else {
            //             }
            //         });
            //     }else {
            //         BootstrapDialog.show({
            //             type : BootstrapDialog.TYPE_DANGER,
            //             title : '失败',
            //             message : '获取商品库存失败' + data.errorMsg
            //         });
            //     }
            // },function(data){
            //     BootstrapDialog.show({
            //         type : BootstrapDialog.TYPE_DANGER,
            //         title : '警告',
            //         message : '获取商品库存错误' + data.errorMsg
            //     });
            // });
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
            });
        });

        // 查询采购明细(分页)
        inventoryService.getPurchaseOrderItemsCount($routeParams.id, function(data){
            if(data.success){
                $scope.itemTable = new NgTableParams({
                    page: 1,
                    count: 10
                },{
                    total: data.data,
                    getData: function ($defer, params) {
                        getPurchaseOrderItemsList($defer, params.page());
                    }
                });
            }
        }, function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
            });
        });

        function getPurchaseOrderItemsList($defer, pageNo){
            inventoryService.listPurchaseOrderItemsPage({
                "purchaseOrderId" : $routeParams.id,
                "pageNo" : pageNo
            }, function(data){
                if(data.success){
                    $scope.purchaseOrderItemList = data.data;
                    $defer.resolve($scope.purchaseOrderItemList);
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '获取采购单明细失败' + data.errorMsg
                    });
                }
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取采购单明细错误' + data.errorMsg
                });
            });
        }

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

        $scope.newPurchaseOrderItem_ = function (){
            $('.bg').css({'display':'block'});
            $('.content').css({'display':'block'});
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
            item.sphere = $scope.selectedMard.sphere;
            item.cylinder = $scope.selectedMard.cylinder;
            item.axial = $scope.selectedMard.axial;

            inventoryService.savePurchaseOrderItem(item, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '新增明细成功'
                    });

                    item.materiel = $scope.selectedMard.materiel;
                    item.itemId = data.data.itemId;
                    $scope.selectPurchaseOrder.purchaseOrderItemList.push(item);
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

            // 采购数量累计
            $scope.selectPurchaseOrder.totalQuantity += Number($scope.purchaseOrderItem.purchaseQuantity);
            // 采购金额累计
            $scope.selectPurchaseOrder.totalAmount += $scope.purchaseOrderItem.purchasePrice * $scope.purchaseOrderItem.purchaseQuantity;

            // 关闭明细弹框
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        // 更新采购退货单
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
                    $location.path("/purchaseRefundOrderManage");
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
            var item = $scope.purchaseOrderItemList[index];
            if(confirm("确定删除该条明细吗？")){
                inventoryService.removePurchaseOrderItem(item.itemId, function (data) {
                    if(data.success){
                        BootstrapDialog.show({
                            type : BootstrapDialog.TYPE_SUCCESS,
                            title : '成功',
                            message : '明细删除成功'
                        });
                        // 将明细从列表删除
                        $scope.purchaseOrderItemList.splice(index, 1);
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
    }).controller("purchaseRefundOrderDetailController",function($location,$scope,$filter,materielService,inventoryService,storeService,supplierService,adminUserService,$routeParams,NgTableParams){
        $scope.selectPurchaseOrder={};
        $scope.purchaseOrderItemList = [];
        $scope.storeList = [];
        $scope.supplierList = [];
        $scope.adminUserList = [];
        $scope.materielList = [];

        // 查询采购退货单
        inventoryService.getPurchaseOrderById($routeParams.id, function(data){
            $scope.selectPurchaseOrder = data.data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
            });
        });

        // 查询采购退货明细(分页)
        inventoryService.getPurchaseOrderItemsCount($routeParams.id, function(data){
            if(data.success){
                $scope.itemTable = new NgTableParams({
                    page: 1,
                    count: 10
                },{
                    total: data.data,
                    getData: function ($defer, params) {
                        getPurchaseOrderItemsList($defer, params.page());
                    }
                });
            }
        }, function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
            });
        });

        function getPurchaseOrderItemsList($defer, pageNo){
            inventoryService.listPurchaseOrderItemsPage({
                "purchaseOrderId" : $routeParams.id,
                "pageNo" : pageNo
            }, function(data){
                if(data.success){
                    $scope.purchaseOrderItemList = data.data;
                    $defer.resolve($scope.purchaseOrderItemList);
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '获取采购退货单明细失败' + data.errorMsg
                    });
                }
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取采购退货单明细错误' + data.errorMsg
                });
            });
        }

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