(function() {
    angular.module("businessOperationApp").controller("mardManageController", function($scope, NgTableParams, inventoryService, materielService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;
        $scope.mardList = [];
        $scope.filterCondition = {};

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }

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
        initMardTable();

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
