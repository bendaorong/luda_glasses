(function() {
    angular.module("businessOperationApp").controller("customerManageController", function($scope, NgTableParams, $filter, $location, customerService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;
        $scope.materielList = [];

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }

        $scope.gotoEdit = function(customerId) {
            $location.path("/editCustomer/" + storeId);
        }

        // 显示门店列表
        function initCustomerList() {
            customerService.fetchCustomerList(function(data){
                $scope.customerList  =  data;
                $scope.tableParams = new NgTableParams({}, {
                    dataset : $scope.customerList
                });

            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取客户列表错误' + data.errorMsg
                });
            });
        }

        initCustomerList();

        $scope.refresh = function() {
            $scope.$emit("loadingStart");
            initCustomerList();
            $scope.$emit("loadingEnd");
        }

        //删除门店
        $scope.removeCustomer = function(customerId){
            if(confirm("确认删除门店吗？")){
                customerService.removeCustomer(customerId, function(data){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '消息',
                        message : '门店删除成功'
                    });
                    $scope.refresh();
                },function(data){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '错误',
                        message : data.errorMsg
                    });
                });
            }
        }
    }).controller("addCustomerController",function($location, $scope, customerService){
        $scope.newCustomer = {};

        //保存门店信息
        $scope.saveCustomer = function(){
            customerService.addCustomer($scope.newCustomer, function(data){
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '门店创建成功'
                    });
                    $location.path("/customerManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '门店创建失败:'+data.errorMsg
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
    }).controller("editCustomerController",function($location,$scope,$filter,customerService,$routeParams){
        $scope.selectCustomer={};

        customerService.getById($routeParams.customerId, function(data){
            $scope.selectCustomer = data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
            });
        });

        $scope.updateCustomer = function(){
            customerService.updateCustomer($scope.selectCustomer, function(data){
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '消息',
                        message : '修改成功'
                    });
                    $location.path("/customerManage");
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
        $scope.$emit("setActive", "customerManage");
    }
})();
