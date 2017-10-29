(function() {
    angular.module("businessOperationApp").controller("purchaseOrderManageController", function($scope, $location, NgTableParams, inventoryService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;
        $scope.purchaseOrderList = [];

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }

        // 显示采购单列表
        function initPurchaseOrderList() {
            inventoryService.fetchPurchaseOrderList(function(data){
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
                        BootstrapDialog.show({
                            type : BootstrapDialog.TYPE_SUCCESS,
                            title : '成功',
                            message : '采购单删除成功'
                        });
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
        $scope.newPurchaseOrder = {};//采购单
        $scope.newPurchaseOrder.totalQuantity = 0;
        $scope.newPurchaseOrder.totalAmount = 0;
        $scope.purchaseOrderItemList = [];//采购明细
        $scope.newPurchaseOrder.purchaseOrderItemList = $scope.purchaseOrderItemList;

        $scope.storeList = [];
        $scope.supplierList = [];
        $scope.adminUserList = [];
        $scope.materielList = [];

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
        adminUserService.fetchUserList(function(data){
            $scope.adminUserList  =  data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取业务员失败:' + data.errorMsg
            });
        });

        $scope.selectedMateriel = {}; //选择的商品
        $scope.purchaseOrderItem = {}; //采购单明细
        $scope.purchaseOrderItem.purchasePrice = 0;
        $scope.purchaseOrderItem.purchaseQuantity = 0;

        $scope.newPurchaseOrderItem = function (){
            $('.bg').css({'display':'block'});
            $('.content').css({'display':'block'});

            $scope.selectedMateriel = {}; //选择的商品
            $scope.purchaseOrderItem = {}; //采购单明细
            $scope.purchaseOrderItem.purchasePrice = 0;
            $scope.purchaseOrderItem.purchaseQuantity = 0;

            // $("#closeBtn").click(function(){
            //     $('.bg').css({'display':'none'});
            //     $('.content').css({'display':'none'});
            // });
        }

        $scope.closeBtn = function () {
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        // 选择商品时加载数据
        $('#abc').change(function(){
            var materielId = $(this).children('option:selected').val();
            materielService.getById(materielId, function(data){
                $scope.selectedMateriel = data.data;
                $scope.purchaseOrderItem.purchasePrice = $scope.selectedMateriel.tradePrice;
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '错误',
                    message : data.errorMsg
                });
            });
        })

        // 添加新明细
        $scope.addItem = function () {
            if($scope.selectedMateriel.id == null){
                alert("请选择商品");
                return false;
            }
            if($scope.purchaseOrderItem.purchasePrice == null || $scope.purchaseOrderItem.purchasePrice == 0){
                alert("请填写采购价格");
                return false;
            }
            if($scope.purchaseOrderItem.purchaseQuantity == null || $scope.purchaseOrderItem.purchaseQuantity == 0){
                alert("请填写采购数量");
                return false;
            }

            // var newItem = "<tr>" +
            //     "<td>" + $scope.selectedMateriel.code + "<input name='materielId' type='hidden' value='"+$scope.selectedMateriel.id+"' /></td>" +
            //     "<td>" + $scope.selectedMateriel.name + "<input name='itemId' type='hidden' value='0' /></td>" +
            //     "<td>" + $scope.selectedMateriel.color + "</td>" +
            //     "<td>" + $scope.selectedMateriel.barcode + "</td>" +
            //     "<td>" + $scope.purchaseOrderItem.purchasePrice + "</td>" +
            //     "<td>" + $scope.purchaseOrderItem.purchaseQuantity + "</td>" +
            //     "<td>" + $scope.selectedMateriel.typeName + "</td>" +
            //     "<td>" + $scope.selectedMateriel.specification + "</td>" +
            //     "<td>" + $scope.purchaseOrderItem.purchasePrice * $scope.purchaseOrderItem.purchaseQuantity + "</td>" +
            //     "<td>" + $scope.purchaseOrderItem.remark + "</td>" +
            //     "<td><button name='del'>删除</button></td>" +
            //     "</tr>";
            // $('#itemContainer').append(newItem);
            console.log($scope.selectedMateriel);
            var item = {};
            var materiel = {};
            item.materiel = materiel;

            materiel.code = $scope.selectedMateriel.code;
            item.materielId = $scope.selectedMateriel.id;
            materiel.name = $scope.selectedMateriel.name;
            item.itemId = 0;
            materiel.color = $scope.selectedMateriel.color;
            materiel.barcode = $scope.selectedMateriel.barcode;
            item.purchasePrice = $scope.purchaseOrderItem.purchasePrice;
            item.purchaseQuantity = $scope.purchaseOrderItem.purchaseQuantity;
            materiel.typeName = $scope.selectedMateriel.typeName;
            materiel.specification = $scope.selectedMateriel.specification;
            item.remark = $scope.purchaseOrderItem.remark;
            $scope.purchaseOrderItemList.push(item);

            // 采购单总数量
            $scope.newPurchaseOrder.totalQuantity += Number($scope.purchaseOrderItem.purchaseQuantity);
            // 采购单总金额
            $scope.newPurchaseOrder.totalAmount += $scope.purchaseOrderItem.purchasePrice * $scope.purchaseOrderItem.purchaseQuantity;
            console.log("newPurchaseOrder:" + JSON.stringify($scope.newPurchaseOrder));

            // 临时变量重置
            $scope.selectedMateriel = {}; //选择的商品
            $scope.purchaseOrderItem = {}; //采购单明细
            $scope.purchaseOrderItem.purchasePrice = 0;
            $scope.purchaseOrderItem.purchaseQuantity = 0;

            // // 编辑明细
            // $("#itemContainer").find("button[name='edit']").each(function(){
            //     $(this).unbind("click");
            //     $(this).bind("click", function () {
            //         var tdArr = $(this).parent().parent().children();
            //         // 商品id
            //         $scope.purchaseOrderItem.materielId = tdArr.eq(0).find("input").val();
            //         // 编号
            //         $scope.selectedMateriel.code = tdArr.eq(0).text();
            //         // 名称
            //         $scope.selectedMateriel.name = tdArr.eq(1).text();
            //         // 条码
            //         $scope.selectedMateriel.barcode = tdArr.eq(3).text();
            //         // 采购价格
            //         $scope.purchaseOrderItem.purchasePrice = tdArr.eq(4).text();
            //         // 采购数量
            //         $scope.purchaseOrderItem.purchaseQuantity = tdArr.eq(5).text();
            //         // 备注
            //         $scope.purchaseOrderItem.remark = tdArr.eq(9).text();
            //         $scope.purchaseOrderItem.remark = tdArr.eq(9).text();
            //         console.log($scope.purchaseOrderItem);
            //         console.log($scope.selectedMateriel);
            //         $('.bg').css({'display':'block'});
            //         $('.content').css({'display':'block'});
            //     });
            // });
            //
            // // 删除明细
            // $("#itemContainer").find("button[name='del']").each(function(){
            //     $(this).unbind("click");
            //     $(this).bind("click", function () {
            //         var tdArr = $(this).parent().parent().children();
            //         // 采购价格
            //         var purchasePrice = tdArr.eq(4).text();
            //         // 采购数量
            //         var purchaseQuantity = tdArr.eq(5).text();
            //         //计算采购数量和采购金额
            //         $scope.newPurchaseOrder.totalQuantity -= Number(purchaseQuantity);
            //         $scope.newPurchaseOrder.totalAmount -= purchasePrice * purchaseQuantity;
            //         //删除该明细
            //         $(this).parent().parent().remove();
            //     });
            // });

            // 关闭明细弹框
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        // 删除采购明细
        $scope.delItem = function (index) {
            if(confirm("确定删除吗?")){
                $scope.purchaseOrderItemList.splice(index, 1);
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
            console.log("newPurchaseOrder:" + JSON.stringify($scope.newPurchaseOrder));

            inventoryService.savePurchaseOrder($scope.newPurchaseOrder, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '采购单创建成功'
                    });
                    $location.path("/purchaseOrderManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '采购单创建失败:'+data.errorMsg
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

        // // 构造采购单明细
        // function buildPurchaseOrderItems(){
        //     var purchaseOrderItems = [];
        //     $("#itemContainer").find("tr:not(':first')").each(function(){
        //         var tdArr = $(this).children();
        //         // 商品id
        //         var materielId = tdArr.eq(0).find("input").val();
        //         // 采购价格
        //         var purchasePrice = tdArr.eq(4).text();
        //         // 采购数量
        //         var purchaseQuantity = tdArr.eq(5).text();
        //         // 备注
        //         var remark = tdArr.eq(9).text();
        //
        //         var item = {};
        //         item.materielId = Number(materielId);
        //         item.purchasePrice = Number(purchasePrice);
        //         item.purchaseQuantity = Number(purchaseQuantity);
        //         item.remark = remark;
        //         purchaseOrderItems.push(item);
        //     });
        //     return purchaseOrderItems;
        // }

        $scope.cancel = function(){
            history.back();
        }

        $scope.closeBtn = function () {
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }
    }).controller("editPurchaseOrderController",function($location,$scope,$filter,materielService,inventoryService,storeService,supplierService,adminUserService,$routeParams){
        $scope.selectPurchaseOrder={};
        $scope.storeList = [];
        $scope.supplierList = [];
        $scope.adminUserList = [];
        $scope.materielList = [];

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

        // 查询采购单
        inventoryService.getPurchaseOrderById($routeParams.id, function(data){
            $scope.selectPurchaseOrder = data.data;
            //console.log($scope.selectPurchaseOrder);
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
        adminUserService.fetchUserList(function(data){
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

        $scope.selectedMateriel = {}; //选择的商品
        $scope.purchaseOrderItem = {}; //采购单明细
        $scope.purchaseOrderItem.purchasePrice = 0;
        $scope.purchaseOrderItem.purchaseQuantity = 0;
        // 选择商品时加载数据
        $('#abc').change(function(){
            var materielId = $(this).children('option:selected').val();
            materielService.getById(materielId, function(data){
                $scope.selectedMateriel = data.data;
                $scope.purchaseOrderItem.purchasePrice = $scope.selectedMateriel.tradePrice;
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '错误',
                    message : data.errorMsg
                });
            });
        })

        // 添加新明细
        $scope.addItem_ = function () {
            // var newItem = "<tr>" +
            //     "<td>" + $scope.selectedMateriel.code + "<input type='hidden' value='"+$scope.selectedMateriel.id+"' /></td>" +
            //     "<td>" + $scope.selectedMateriel.name + "<input type='hidden' value='0' /></td>" +
            //     "<td>" + $scope.selectedMateriel.color + "</td>" +
            //     "<td>" + $scope.selectedMateriel.barcode + "</td>" +
            //     "<td>" + $scope.purchaseOrderItem.purchasePrice + "</td>" +
            //     "<td>" + $scope.purchaseOrderItem.purchaseQuantity + "</td>" +
            //     "<td>" + $scope.selectedMateriel.typeName + "</td>" +
            //     "<td>" + $scope.selectedMateriel.specification + "</td>" +
            //     "<td>" + $scope.purchaseOrderItem.purchasePrice * $scope.purchaseOrderItem.purchaseQuantity + "</td>" +
            //     "<td>" + $scope.purchaseOrderItem.remark + "</td>" +
            //     "<td><button name='del'>删除</button></td>" +
            //     "</tr>";
            // $('#itemContainer').append(newItem);

            // // 删除明细
            // $("#itemContainer").find("button[name='del']").each(function(){
            //     $(this).unbind("click");
            //     $(this).bind("click", function () {
            //         var tdArr = $(this).parent().parent().children();
            //         if(tdArr.eq(1).find("input").val() == 0){
            //             //删除该明细
            //             $(this).parent().parent().remove();
            //         }else {
            //             if(confirm("确定删除该条明细吗？")){
            //                 // 采购价格
            //                 var purchasePrice = tdArr.eq(4).text();
            //                 // 采购数量
            //                 var purchaseQuantity = tdArr.eq(5).text();
            //                 //计算采购数量和采购金额
            //                 $scope.newPurchaseOrder.totalQuantity -= Number(purchaseQuantity);
            //                 $scope.newPurchaseOrder.totalAmount -= purchasePrice * purchaseQuantity;
            //                 //删除该明细
            //                 $(this).parent().parent().remove();
            //             }
            //         }
            //     });
            // });

            var item = {};
            item.itemId = 0;
            item.purchaseOrderId = $scope.selectPurchaseOrder.purchaseOrderId;
            item.materielId = $scope.selectedMateriel.id;
            item.purchasePrice = $scope.purchaseOrderItem.purchasePrice;
            item.purchaseQuantity = $scope.purchaseOrderItem.purchaseQuantity;
            item.remark = $scope.purchaseOrderItem.remark;

            console.log(JSON.stringify(item));
            inventoryService.savePurchaseOrderItem(item, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '新增明细成功'
                    });

                    var materiel = {};
                    item.materiel = materiel;
                    item.itemId = data.data.itemId;
                    materiel.code = $scope.selectedMateriel.code;
                    materiel.name = $scope.selectedMateriel.name;
                    materiel.color = $scope.selectedMateriel.color;
                    materiel.barcode = $scope.selectedMateriel.barcode;
                    materiel.typeName = $scope.selectedMateriel.typeName;
                    materiel.specification = $scope.selectedMateriel.specification;
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

        // // 构造采购单明细
        // function buildPurchaseOrderItems(){
        //     var purchaseOrderItems = [];
        //     $("#itemContainer").find("tr:not(':first')").each(function(){
        //         var tdArr = $(this).children();
        //         // 商品id
        //         var materielId = tdArr.eq(0).find("input").val();
        //         // 采购价格
        //         var purchasePrice = tdArr.eq(4).text();
        //         // 采购数量
        //         var purchaseQuantity = tdArr.eq(5).text();
        //         // 备注
        //         var remark = tdArr.eq(9).text();
        //         // 明细id
        //         var itemId = tdArr.eq(1).find("input").val();
        //
        //         var item = {};
        //         item.materielId = Number(materielId);
        //         item.purchasePrice = Number(purchasePrice);
        //         item.purchaseQuantity = Number(purchaseQuantity);
        //         item.remark = remark;
        //         item.purchaseOrderId = Number($scope.selectPurchaseOrder.purchaseOrderId);
        //         item.itemId = Number(itemId);
        //         purchaseOrderItems.push(item);
        //     });
        //     return purchaseOrderItems;
        // }

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

    function setActiveSubPage($scope) {
        $scope.$emit("setActive", "purchaseOrderManage");
    }
})();