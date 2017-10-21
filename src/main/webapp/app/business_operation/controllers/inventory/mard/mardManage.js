(function() {
    angular.module("businessOperationApp").controller("mardManageController", function($scope, NgTableParams, inventoryService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;
        $scope.mardList = [];

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }

        // 显示商品库存列表
        function initMardList() {
            inventoryService.fetchMardVoList(function(data){
                if(data.success){
                    $scope.mardList = data.data;
                    $scope.tableParams = new NgTableParams({}, {
                        dataset : $scope.mardList
                    });
                    console.log("size:"+$scope.mardList.length);
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
        initMardList();

        $scope.refresh = function() {
            $scope.$emit("loadingStart");
            initMardList();
            $scope.$emit("loadingEnd");
        }
    });

    function setActiveSubPage($scope) {
        $scope.$emit("setActive", "mardManage");
    }
})();
