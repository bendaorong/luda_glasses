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
    }).controller("addInventoryVerificationController", function ($location,$scope,$filter,materielService,inventoryService,storeService,supplierService,adminUserService,NgTableParams) {
        $scope.storeId = sessionStorage.getItem("storeId");
        $scope.adminUserId = sessionStorage.getItem("adminUserId");

        $scope.newInvntVerification = {};
        $scope.newInvntVerification.storeId = $scope.storeId;
        $scope.newInvntVerification.businessmanId = $scope.adminUserId;
        $scope.newInvntVerification.verifDate = $filter("date")(new Date(), "yyyy-MM-dd");
        $scope.newInvntVerification.invtVerifItemList = [];

        $scope.storeList = [];
        $scope.adminUserList = [];
        $scope.mardList = [];

        $scope.selectedMard = {}; //选择的商品库存
        $scope.selectedMateriel = {}; //选择的商品
        $scope.invntVerificationItem = {}; //盘点单明细

        $scope.filterCondition = {};
        $scope.filterCondition.storeId = $scope.storeId;

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

        // 盘点
        $scope.verificate = function(mardId){
            $('.bg').css({'display':'block'});
            $('.content').css({'display':'block'});

            angular.forEach($scope.mardList, function (each) {
                if(each.id == mardId){
                    $scope.selectedMard = each;
                }
            });
        }

        $scope.closeBtn = function () {
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        // 添加新明细
        $scope.addItem = function () {
            if($scope.invntVerificationItem.quantity == null){
                alert("请填写盘点数量");
                return false;
            }
            if($scope.invntVerificationItem.quantity <= 0){
                alert("盘点数量必须大于0");
                return false;
            }
            if($scope.invntVerificationItem.type == null){
                alert("请选择盘点类型");
                return false;
            }

            var item = {};
            item.materiel = $scope.selectedMard.materiel;
            item.mardId = $scope.selectedMard.id;
            item.materielId = $scope.selectedMard.materielId;
            item.sphere = $scope.selectedMard.sphere;
            item.cylinder = $scope.selectedMard.cylinder;
            item.axial = $scope.selectedMard.axial;
            item.quantity = $scope.invntVerificationItem.quantity;
            item.type = $scope.invntVerificationItem.type;
            item.remark = $scope.invntVerificationItem.remark;
            $scope.newInvntVerification.invtVerifItemList.push(item);

            // 关闭明细弹框
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        // 保存盘点单
        $scope.saveInvntVerification = function () {
            // 收集采购单明细
            if($scope.newInvntVerification.invtVerifItemList.length == 0){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '库存盘点明细不能为空'
                });
                return false;
            }

            // 盘点日期
            $scope.newInvntVerification.verifDate = $("#verifDate").val();
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

        $scope.cancel = function(){
            history.back();
        }
    }).controller("editInventoryVerificationController",function($location,$scope,$filter,materielService,inventoryService,storeService,supplierService,adminUserService,$routeParams,NgTableParams){
        $scope.storeId = sessionStorage.getItem("storeId");
        $scope.selectInvntVerification = {};
        $scope.storeList = [];
        $scope.adminUserList = [];
        $scope.materielList = [];

        $scope.selectedMard = {}; //选择的商品库存
        $scope.invntVerificationItem = {}; //盘点明细

        $scope.filterCondition = {};
        $scope.filterCondition.storeId = $scope.storeId;

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

        // 盘点
        $scope.verificate = function(mardId){
            $('.bg').css({'display':'block'});
            $('.content').css({'display':'block'});

            angular.forEach($scope.mardList, function (each) {
                if(each.id == mardId){
                    $scope.selectedMard = each;
                }
            });
        }

        // // 添加盘点明细
        // $scope.newInvntVerificationItem = function (){
        //     $('.bg').css({'display':'block'});
        //     $('.content').css({'display':'block'});
        // }

        $scope.closeBtn = function () {
            $('.bg').css({'display':'none'});
            $('.content').css({'display':'none'});
        }

        // 添加新明细
        $scope.addItem = function () {
            if($scope.invntVerificationItem.quantity == null){
                alert("请填写盘点数量");
                return false;
            }
            if($scope.invntVerificationItem.quantity <= 0){
                alert("盘点数量必须大于0");
                return false;
            }
            if($scope.invntVerificationItem.type == null){
                alert("请选择盘点类型");
                return false;
            }

            var item = angular.copy($scope.invntVerificationItem);
            item.itemId = 0;
            item.mardId = $scope.selectedMard.id;
            item.materielId = $scope.selectedMard.materielId;
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