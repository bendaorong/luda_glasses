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
            supplierService.fetchSupplierList(function(data){
                if(data.success){
                    $scope.supplierList = data.data;
                    $scope.tableParams = new NgTableParams({}, {
                        dataset : $scope.supplierList
                    });
                    console.log("size:"+$scope.supplierList.length);
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '获取供应商失败:' + data.errorMsg
                    });
                }
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取供应商错误' + data.errorMsg
                });
            });
        }

        initSupplierList();

        $scope.refresh = function() {
            $scope.$emit("loadingStart");
            initSupplierList();
            $scope.$emit("loadingEnd");
        }

        $scope.gotoEdit = function(supplierId) {
            $location.path("/editSupplier/" + supplierId);
        }
    }).controller("addSupplierController",function($location, $scope, supplierService){
        $scope.newSupplier = {};

        //保存供应商信息
        $scope.saveSupplier = function(){
            supplierService.addSupplier($scope.newSupplier, function(data){
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '供应商创建成功'
                    });
                    $location.path("/supplierManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '供应商创建失败:'+data.errorMsg
                    });
                }
            },function(data){
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
    }).controller("editSupplierController",function($scope, $routeParams, $location, supplierService){
        $scope.selectSupplier = {};

        supplierService.getSupplierById($routeParams.supplierId, function(data){
            if(data.success){
                $scope.selectSupplier = data.data;
            }else {
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '失败',
                    message : "获取供应商信息失败:" + data.errorMsg
                });
            }
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
            });
        });

        $scope.updateSupplier = function(){
            supplierService.updateSupplier($scope.selectSupplier, function(data){
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '消息',
                        message : '修改成功'
                    });
                    $location.path("/supplierManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '修改失败:'+data.errorMsg
                    });
                }
            },function(data){
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
    });

    function setActiveSubPage($scope) {
        $scope.$emit("setActive", "supplierManage");
    }
})();
