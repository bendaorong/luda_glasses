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
    }).controller("addInventoryVerificationController", function ($location,$scope,$filter,materielService,inventoryService,storeService,supplierService,adminUserService,$routeParams) {
        $scope.storeId = sessionStorage.getItem("storeId");
        $scope.adminUserId = sessionStorage.getItem("adminUserId");

        $scope.newInvntVerification = {};
        $scope.newInvntVerification.storeId = $scope.storeId;
        $scope.newInvntVerification.businessmanId = $scope.adminUserId;
        $scope.newInvntVerification.verifDate = $filter("date")(new Date(), "yyyy-MM-dd");

        $scope.storeList = [];
        $scope.adminUserList = [];
        $scope.mardList = [];

        $scope.selectedMard = {}; //选择的商品库存
        $scope.selectedMateriel = {}; //选择的商品
        $scope.invntVerificationItem = {}; //盘点单明细

        //查询商品库存
        inventoryService.fetchMardVoList(function(data){
            if(data.success){
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
                                $scope.invntVerificationItem.mardId = mardId;
                                $scope.$apply();
                                break;
                            }
                        }
                    }else {
                        $scope.selectedMateriel = {};
                        //$scope.$apply();
                    }
                });
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

        // 查询门店
        storeService.fetchStoreList(function(data){
            angular.forEach(data, function (each) {
                if(each.storeId == $scope.storeId){
                    $scope.storeList.push(angular.copy(each));
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

        // 添加新明细
        $scope.addItem = function () {
            var newItem = "<tr>" +
                "<td>" + $scope.selectedMard.materiel.code + "<input type='hidden' value='"+$scope.selectedMard.materielId+"' /></td>" +
                "<td>" + $scope.selectedMard.materiel.name + "<input type='hidden' value='"+$scope.selectedMard.id+"' /></td>" +
                "<td>" + $scope.selectedMard.materiel.color + "</td>" +
                "<td>" + $scope.selectedMard.materiel.barcode + "</td>" +
                "<td>" + $scope.invntVerificationItem.quantity + "</td>" +
                "<td>" + $scope.invntVerificationItem.type + "</td>" +
                "<td>" + $scope.selectedMard.materiel.typeName + "</td>" +
                "<td>" + $scope.selectedMard.materiel.specification + "</td>" +
                "<td>" + $scope.invntVerificationItem.remark + "</td>" +
                "</tr>";
            $('#itemContainer').append(newItem);

            // 关闭明细弹框
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        // 保存盘点单
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
                // 商品库存id
                var mardId = tdArr.eq(1).find("input").val();
                // 商品id
                var materielId = tdArr.eq(0).find("input").val();
                // 数量
                var quantity = tdArr.eq(4).text();
                // 盘点类型
                var type = tdArr.eq(5).text();
                // 备注
                var remark = tdArr.eq(8).text();

                var item = {};
                item.mardId = Number(mardId);
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

        $scope.selectedMard = {}; //选择的商品库存
        $scope.invntVerificationItem = {}; //盘点明细

        //查询商品库存
        inventoryService.fetchMardVoList(function(data){
            if(data.success){
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
                                $scope.invntVerificationItem.mardId = mardId;
                                $scope.invntVerificationItem.materielId = $scope.selectedMard.materielId;
                                $scope.$apply();
                                break;
                            }
                        }
                    }else {
                        $scope.selectedMateriel = {};
                        //$scope.$apply();
                    }
                });
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

        // 查询盘点单
        inventoryService.getInvntVerificationById($routeParams.id, function(data){
            $scope.selectInvntVerification = data.data;
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
        adminUserService.fetchUserListByStore(function(data){
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

        // 添加新明细
        $scope.addItem = function () {
            var item = angular.copy($scope.invntVerificationItem);
            item.itemId = 0;
            item.inventoryVerificationId = $scope.selectInvntVerification.id;

            inventoryService.saveInvntVerificationItem(item, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '新增明细成功'
                    });

                    item.id = data.data.id;
                    item.materiel = $scope.selectedMard.materiel;
                    item.sphere = $scope.selectedMard.sphere;
                    item.cylinder = $scope.selectedMard.cylinder;
                    item.axial = $scope.selectedMard.axial;

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
    }).controller("inventoryVerificationDetailController",function($location,$scope,$filter,materielService,inventoryService,storeService,supplierService,adminUserService,$routeParams){
        $scope.selectInvntVerification = {};
        $scope.storeList = [];
        $scope.adminUserList = [];
        $scope.materielList = [];

        // 查询盘点单
        inventoryService.getInvntVerificationById($routeParams.id, function(data){
            $scope.selectInvntVerification = data.data;
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

    function setActiveSubPage($scope) {
        $scope.$emit("setActive", "inventoryVerificationManage");
    }
})();