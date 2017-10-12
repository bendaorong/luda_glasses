(function() {
    angular.module("businessOperationApp").controller("supplierManageController", function($scope, NgTableParams, $filter, $location, supplierService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;
        $scope.supplierList = [];

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }

        // 显示供应商列表
        function initSupplierList() {
            // supplierService.fetchSupplierList(function(data){
            //     if(data.success){
            //         $scope.supplierList = data.data;
            //         $scope.tableParams = new NgTableParams({}, {
            //             dataset : $scope.supplierList
            //         });
            //         console.log("size:"+$scope.supplierList.length);
            //     }else {
            //         BootstrapDialog.show({
            //             type : BootstrapDialog.TYPE_DANGER,
            //             title : '失败',
            //             message : '获取供应商失败:' + data.errorMsg
            //         });
            //     }
            // },function(data){
            //     BootstrapDialog.show({
            //         type : BootstrapDialog.TYPE_DANGER,
            //         title : '警告',
            //         message : '获取供应商错误' + data.errorMsg
            //     });
            // });
        }

        initSupplierList();

        $scope.refresh = function() {
            $scope.$emit("loadingStart");
            initSupplierList();
            $scope.$emit("loadingEnd");
        }
    });

    function setActiveSubPage($scope) {
        $scope.$emit("setActive", "supplierManage");
    }
})();
