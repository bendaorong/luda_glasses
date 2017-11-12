(function() {
    angular.module("businessOperationApp").controller("customerManageController", function($scope, NgTableParams, $filter, $location, customerService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;
        $scope.materielList = [];

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }

        // 显示客户列表
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

        //用户详情
        $scope.customerDetail = function (customerId) {
            $location.path("/customerDetail/" + customerId);
        }

        $scope.gotoEdit = function(customerId) {
            $location.path("/editCustomer/" + customerId);
        }

        //删除客户
        $scope.removeCustomer = function(customerId){
            if(confirm("确认删除客户吗？")){
                customerService.removeCustomer(customerId, function(data){
                    // BootstrapDialog.show({
                    //     type : BootstrapDialog.TYPE_SUCCESS,
                    //     title : '消息',
                    //     message : '删除成功'
                    // });
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
    }).controller("addCustomerController",function($location, $scope, customerService, dictionaryService){
        $scope.newCustomer = {};
        $scope.regionList = []; //地区

        // 查询客户地区
        dictionaryService.fetchDictionaryByType("REGION", function(data){
            $scope.regionList = data.data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取地区失败:' + data.errorMsg
            });
        });

        //保存门店信息
        $scope.saveCustomer = function(){
            customerService.addCustomer($scope.newCustomer, function(data){
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '客户创建成功'
                    });
                    $location.path("/customerManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '客户创建失败:'+data.errorMsg
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
    }).controller("editCustomerController",function($location,$scope,$filter,customerService,dictionaryService,$routeParams){
        $scope.selectCustomer={};
        $scope.regionList = []; //地区

        // 查询客户地区
        dictionaryService.fetchDictionaryByType("REGION", function(data){
            $scope.regionList = data.data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取地区失败:' + data.errorMsg
            });
        });

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
    }).controller("customerDetailController",function($location,$scope,$filter,customerService,dictionaryService,salesService,$routeParams,NgTableParams){
        $scope.selectCustomer={};

        // 查询客户详情
        customerService.getById($routeParams.customerId, function(data){
            $scope.selectCustomer = data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
            });
        });

        // 查询客户消费记录
        $scope.salesOrderList = [];
        function querySalesOrders() {
            salesService.fetchCustomerSalesOrderVoList($routeParams.customerId, function (data) {
                $scope.salesOrderList = data.data;
                $scope.salesOrders = new NgTableParams({}, {
                    dataset : $scope.salesOrderList
                });
                //console.log("size:"+$scope.salesOrderList.length);
            }, function (data) {

            });
        }
        querySalesOrders();

        // 刷新销售记录
        $scope.refreshSalesOrder = function() {
            $scope.$emit("loadingStart");
            querySalesOrders();
            $scope.$emit("loadingEnd");
        }

        // 查询客户验光记录
        $scope.optometryRecordList = [];
        function queryOptometryRecords() {
            customerService.fetchOptometryRecordsByCustomerId($routeParams.customerId, function (data) {
                $scope.optometryRecordList = data.data;
                $scope.optometryRecords = new NgTableParams({}, {
                    dataset : $scope.optometryRecordList
                });
                console.log("optometryRecordList size:"+$scope.optometryRecordList.length);
            }, function (data) {

            });
        }
        queryOptometryRecords();

        // 刷新验光记录
        $scope.refreshOptometryRecord = function() {
            $scope.$emit("loadingStart");
            queryOptometryRecords();
            $scope.$emit("loadingEnd");
        }

        $scope.cancel = function(){
            history.back();
        }
    });

    function setActiveSubPage($scope) {
        $scope.$emit("setActive", "customerManage");
    }
})();
