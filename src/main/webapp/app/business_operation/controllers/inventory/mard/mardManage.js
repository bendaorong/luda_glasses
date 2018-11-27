(function() {
    angular.module("businessOperationApp").controller("mardManageController", function($scope, NgTableParams, inventoryService, materielService, storeService, dictionaryService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;
        $scope.mardList = [];
        $scope.storeList = [];
        $scope.goodsTypeList = [];
        $scope.filterCondition = {};
        $scope.totalInventorys = 0;

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
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

        // 查询商品类型
        dictionaryService.fetchGoodsTypeList(function(data){
            $scope.goodsTypeList = data.data;
            for(var i=0; i<$scope.goodsTypeList.length; i++){
                $("#goodsType").append("<option value='" + $scope.goodsTypeList[i].typeId + "'>" + $scope.goodsTypeList[i].typeName + "</option>");
            }
            $scope.goodsTypeSearchableSelect = $("#goodsType").searchableSelect(function (typeId) {
                if(angular.isDefined(typeId) && typeId != null && typeId != ''){
                    $scope.filterCondition.typeId = typeId;
                }else {
                    $scope.filterCondition.typeId = null;
                }
                $scope.$apply();
            });
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取商品类型失败:' + data.errorMsg
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
                }else {
                    $scope.filterCondition.materielId = null;
                }
                $scope.$apply();
            });
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取商品错误' + data.errorMsg
            });
        });

        // init table
        function initMardTable(){
            getTotalInventorys();
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
        initMardTable();

        //查询库存总计
        function getTotalInventorys(){
            inventoryService.getTotalInventorys($scope.filterCondition, function(data){
                if(data.success){
                    $scope.totalInventorys = data.data;
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '获取库存总计失败' + data.errorMsg
                    });
                }
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取库存总计错误' + data.errorMsg
                });
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

        $scope.refresh = function() {
            $scope.$emit("loadingStart");
            initMardTable();
            $scope.$emit("loadingEnd");
        }
    });

    function setActiveSubPage($scope) {
        $scope.$emit("setActive", "mardManage");
    }
})();
