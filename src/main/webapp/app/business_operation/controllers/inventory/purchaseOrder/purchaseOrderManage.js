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

        $scope.gotoEdit = function(id) {
            $location.path("/editPurchaseOrder/" + id);
        }
    }).controller("addPurchaseOrderController", function ($location,$scope,$filter,materielService,inventoryService,storeService,supplierService,adminUserService,$routeParams) {
        $scope.newPurchaseOrder = {};
        $scope.newPurchaseOrder.totalQuantity = 0;
        $scope.newPurchaseOrder.totalAmount = 0;

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

        $scope.newPurchaseOrderItem = function (){
            $('.bg').css({'display':'block'});
            $('.content').css({'display':'block'});

            // $("#closeBtn").click(function(){
            //     $('.bg').css({'display':'none'});
            //     $('.content').css({'display':'none'});
            // });
        }

        $scope.closeBtn = function () {
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
        $scope.addItem = function () {
            var newItem = "<tr>" +
                "<td>" + $scope.selectedMateriel.code + "<input type='hidden' value='"+$scope.selectedMateriel.id+"' /></td>" +
                "<td>" + $scope.selectedMateriel.name + "<input type='hidden' value='0' /></td>" +
                "<td>" + $scope.selectedMateriel.color + "</td>" +
                "<td>" + $scope.selectedMateriel.barcode + "</td>" +
                "<td>" + $scope.purchaseOrderItem.purchasePrice + "</td>" +
                "<td>" + $scope.purchaseOrderItem.purchaseQuantity + "</td>" +
                "<td>" + $scope.selectedMateriel.typeName + "</td>" +
                "<td>" + $scope.selectedMateriel.specification + "</td>" +
                "<td>" + $scope.purchaseOrderItem.purchasePrice * $scope.purchaseOrderItem.purchaseQuantity + "</td>" +
                "<td>" + $scope.purchaseOrderItem.remark + "</td>" +
                "</tr>";
            $('#itemContainer').append(newItem);

            // 采购数量累计
            $scope.newPurchaseOrder.totalQuantity += Number($scope.purchaseOrderItem.purchaseQuantity);
            // 采购金额累计
            $scope.newPurchaseOrder.totalAmount += $scope.purchaseOrderItem.purchasePrice * $scope.purchaseOrderItem.purchaseQuantity;
            console.log("newPurchaseOrder:" + JSON.stringify($scope.newPurchaseOrder));

            // 关闭明细弹框
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        // 保存采购单
        $scope.savePurchaseOrder = function () {
            // 收集采购单明细
            var purchaseOrderItems = buildPurchaseOrderItems();
            if(purchaseOrderItems.length == 0){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '采购单明细不能为空'
                });
                return false;
            }

            $scope.newPurchaseOrder.purchaseOrderItemList = purchaseOrderItems;
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

        // 构造采购单明细
        function buildPurchaseOrderItems(){
            var purchaseOrderItems = [];
            $("#itemContainer").find("tr:not(':first')").each(function(){
                var tdArr = $(this).children();
                // 商品id
                var materielId = tdArr.eq(0).find("input").val();
                // 采购价格
                var purchasePrice = tdArr.eq(4).text();
                // 采购数量
                var purchaseQuantity = tdArr.eq(5).text();
                // 备注
                var remark = tdArr.eq(9).text();

                var item = {};
                item.materielId = Number(materielId);
                item.purchasePrice = Number(purchasePrice);
                item.purchaseQuantity = Number(purchaseQuantity);
                item.remark = remark;
                purchaseOrderItems.push(item);
            });
            return purchaseOrderItems;
        }

        $scope.cancel = function(){
            history.back();
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
            console.log($scope.selectPurchaseOrder);
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

        $scope.newPurchaseOrderItem = function (){
            $('.bg').css({'display':'block'});
            $('.content').css({'display':'block'});

            // $("#closeBtn").click(function(){
            //     $('.bg').css({'display':'none'});
            //     $('.content').css({'display':'none'});
            // });
        }

        $scope.closeBtn = function () {
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
        $scope.addItem = function () {

            var newItem = "<tr>" +
                "<td>" + $scope.selectedMateriel.code + "<input type='hidden' value='"+$scope.selectedMateriel.id+"' /></td>" +
                "<td>" + $scope.selectedMateriel.name + "<input type='hidden' value='0' /></td>" +
                "<td>" + $scope.selectedMateriel.color + "</td>" +
                "<td>" + $scope.selectedMateriel.barcode + "</td>" +
                "<td>" + $scope.purchaseOrderItem.purchasePrice + "</td>" +
                "<td>" + $scope.purchaseOrderItem.purchaseQuantity + "</td>" +
                "<td>" + $scope.selectedMateriel.typeName + "</td>" +
                "<td>" + $scope.selectedMateriel.specification + "</td>" +
                "<td>" + $scope.purchaseOrderItem.purchasePrice * $scope.purchaseOrderItem.purchaseQuantity + "</td>" +
                "<td>" + $scope.purchaseOrderItem.remark + "</td>" +
                "</tr>";
            $('#itemContainer').append(newItem);

            // 采购数量累计
            $scope.selectPurchaseOrder.totalQuantity += Number($scope.purchaseOrderItem.purchaseQuantity);
            // 采购金额累计
            $scope.selectPurchaseOrder.totalAmount += $scope.purchaseOrderItem.purchasePrice * $scope.purchaseOrderItem.purchaseQuantity;
            console.log("selectPurchaseOrder:" + JSON.stringify($scope.selectPurchaseOrder));

            // 关闭明细弹框
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        // 更新采购单
        $scope.updatePurchaseOrder = function () {
            // 收集采购单明细
            var purchaseOrderItems = buildPurchaseOrderItems();
            console.log("purchaseOrderItems:" + JSON.stringify(purchaseOrderItems));
        }

        // 构造采购单明细
        function buildPurchaseOrderItems(){
            var purchaseOrderItems = [];
            $("#itemContainer").find("tr:not(':first')").each(function(){
                var tdArr = $(this).children();
                // 商品id
                var materielId = tdArr.eq(0).find("input").val();
                // 采购价格
                var purchasePrice = tdArr.eq(4).text();
                // 采购数量
                var purchaseQuantity = tdArr.eq(5).text();
                // 备注
                var remark = tdArr.eq(9).text();
                // 明细id
                var itemId = tdArr.eq(1).find("input").val();

                var item = {};
                item.materielId = Number(materielId);
                item.purchasePrice = Number(purchasePrice);
                item.purchaseQuantity = Number(purchaseQuantity);
                item.remark = remark;
                item.purchaseOrderId = Number($scope.selectPurchaseOrder.purchaseOrderId);
                item.itemId = Number(itemId);
                purchaseOrderItems.push(item);
            });
            return purchaseOrderItems;
        }

        $scope.cancel = function(){
            history.back();
        }
    });

    function setActiveSubPage($scope) {
        $scope.$emit("setActive", "purchaseOrderManage");
    }
})();