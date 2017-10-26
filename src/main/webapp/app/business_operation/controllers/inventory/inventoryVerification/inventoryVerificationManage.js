(function() {
    angular.module("businessOperationApp").controller("invntVerifManageController", function($scope, $location, NgTableParams, inventoryService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;
        $scope.invntVerifList = [];

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }

        // 显示盘点单列表
        function initInvntVerifList() {
            inventoryService.fetchInvntVerifList(function(data){
                if(data.success){
                    $scope.invntVerifList = data.data;
                    $scope.tableParams = new NgTableParams({}, {
                        dataset : $scope.invntVerifList
                    });
                    console.log("size:"+$scope.invntVerifList.length);
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '获取盘点单失败:' + data.errorMsg
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
        initInvntVerifList();

        $scope.refresh = function() {
            $scope.$emit("loadingStart");
            initInvntVerifList();
            $scope.$emit("loadingEnd");
        }

        // 编辑采购单
        $scope.gotoEdit = function(id) {
            $location.path("/editInvntVerification/" + id);
        }

        // 删除盘点单
        $scope.removeInvntVerification = function (id) {
            if(confirm("删除盘点单将恢复盘点商品的库存，确定删除吗？")){
                inventoryService.removeInvntVerification(id, function (data) {
                    if(data.success){
                        BootstrapDialog.show({
                            type : BootstrapDialog.TYPE_SUCCESS,
                            title : '成功',
                            message : '盘点单删除成功'
                        });
                        refresh();
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
    }).controller("addInventoryVerificationController", function ($location,$scope,$filter,materielService,inventoryService,storeService,supplierService,adminUserService,$routeParams) {
        $scope.newInvntVerification = {};

        $scope.storeList = [];
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

        // 添加一条盘点明细
        $scope.newInvntVerificationItem = function (){
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
        $scope.invntVerificationItem = {}; //采购单明细
        // 选择商品时加载数据
        $('#abc').change(function(){
            var materielId = $(this).children('option:selected').val();
            materielService.getById(materielId, function(data){
                $scope.selectedMateriel = data.data;
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
                "<td>" + $scope.invntVerificationItem.quantity + "</td>" +
                "<td>" + $scope.invntVerificationItem.type + "</td>" +
                "<td>" + $scope.selectedMateriel.typeName + "</td>" +
                "<td>" + $scope.selectedMateriel.specification + "</td>" +
                "<td>" + $scope.invntVerificationItem.remark + "</td>" +
                "</tr>";
            $('#itemContainer').append(newItem);

            // 关闭明细弹框
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        // 保存采购单
        $scope.saveInvntVerification = function () {
            // 收集采购单明细
            var invtVerifItems = buildInvtVerifItemsItems();
            if(invtVerifItems.length == 0){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '库存盘点明细不能为空'
                });
                return false;
            }

            $scope.newInvntVerification.invtVerifItemList = invtVerifItems;
            // 盘点日期
            $scope.newInvntVerification.verifDate = $("#verifDate").val();
            console.log("newInvntVerification:" + JSON.stringify($scope.newInvntVerification));

            inventoryService.saveInventoryVerification($scope.newInvntVerification, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '盘点单创建成功'
                    });
                    $location.path("/inventoryVerificationManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '盘点单创建失败:'+data.errorMsg
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
        function buildInvtVerifItemsItems(){
            var items = [];
            $("#itemContainer").find("tr:not(':first')").each(function(){
                var tdArr = $(this).children();
                // 商品id
                var materielId = tdArr.eq(0).find("input").val();
                // 数量
                var quantity = tdArr.eq(4).text();
                // 盘点类型
                var type = tdArr.eq(5).text();
                // 备注
                var remark = tdArr.eq(8).text();

                var item = {};
                item.materielId = Number(materielId);
                item.quantity = Number(quantity);
                item.type = type;
                item.remark = remark;
                items.push(item);
            });
            return items;
        }

        $scope.cancel = function(){
            history.back();
        }
    }).controller("editInventoryVerificationController",function($location,$scope,$filter,materielService,inventoryService,storeService,supplierService,adminUserService,$routeParams){
        $scope.selectInvntVerification = {};
        $scope.storeList = [];
        $scope.adminUserList = [];
        $scope.materielList = [];

        // 查询盘点单
        inventoryService.getInvntVerificationById($routeParams.id, function(data){
            $scope.selectInvntVerification = data.data;
            console.log($scope.selectPurchaseOrder);
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
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

        // 添加盘点明细
        $scope.newInvntVerificationItem = function (){
            $('.bg').css({'display':'block'});
            $('.content').css({'display':'block'});
        }

        $scope.closeBtn = function () {
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        $scope.selectedMateriel = {}; //选择的商品
        $scope.invntVerificationItem = {}; //盘点明细
        // 选择商品时加载数据
        $('#abc').change(function(){
            var materielId = $(this).children('option:selected').val();
            materielService.getById(materielId, function(data){
                $scope.selectedMateriel = data.data;
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
            var item = {};
            item.itemId = 0;
            item.inventoryVerificationId = $scope.selectInvntVerification.id;
            item.materielId = $scope.selectedMateriel.id;
            item.quantity = $scope.invntVerificationItem.quantity;
            item.type = $scope.invntVerificationItem.type;
            item.remark = $scope.invntVerificationItem.remark;

            inventoryService.saveInvntVerificationItem(item, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '新增明细成功'
                    });

                    var materiel = {};
                    item.materiel = materiel;
                    item.id = data.data.id;
                    materiel.code = $scope.selectedMateriel.code;
                    materiel.name = $scope.selectedMateriel.name;
                    materiel.color = $scope.selectedMateriel.color;
                    materiel.barcode = $scope.selectedMateriel.barcode;
                    materiel.typeName = $scope.selectedMateriel.typeName;
                    materiel.specification = $scope.selectedMateriel.specification;
                    $scope.selectInvntVerification.invtVerifItemList.push(item);
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

        // 更新盘点单
        $scope.updateInvntVerification = function () {
            // 采购日期
            $scope.selectInvntVerification.verifDate = $("#verifDate").val();
            console.log("selectInvntVerification:" + JSON.stringify($scope.selectInvntVerification));

            inventoryService.updateInvntVerification($scope.selectInvntVerification, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '更新成功'
                    });
                    $location.path("/inventoryVerificationManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '更新失败:'+data.errorMsg
                    });
                }
            }, function (data) {
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '失败',
                    message : '更新失败:'+data.errorMsg
                });
            });
        }

        // 删除明细
        $scope.delItem_ = function (index) {
            var item = $scope.selectInvntVerification.invtVerifItemList[index];
            if(confirm("确定删除该条明细吗？")){
                inventoryService.removeInvntVerificationItem(item.id, function (data) {
                    if(data.success){
                        BootstrapDialog.show({
                            type : BootstrapDialog.TYPE_SUCCESS,
                            title : '成功',
                            message : '明细删除成功'
                        });
                        // 将明细从列表删除
                        $scope.selectInvntVerification.invtVerifItemList.splice(index, 1);
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
        $scope.$emit("setActive", "inventoryVerificationManage");
    }
})();