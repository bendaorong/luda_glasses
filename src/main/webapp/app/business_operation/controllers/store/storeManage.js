(function() {
    angular.module("businessOperationApp").controller("storeManageController", function($scope, NgTableParams, $filter, $location, storeService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;
        $scope.storeList = [];

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }

        $scope.gotoEdit = function(storeId) {
            $location.path("/editStore/" + storeId);
        }

        // $scope.gotoModifyPwd =  function(cellPhoneNumber){
        //     $location.path("/gotoModifyPwd/" + cellPhoneNumber);
        // }

        // 显示门店列表
        function initStoreList() {
            storeService.fetchStoreList(function(data){
                $scope.storeList  =  data;
                $scope.tableParams = new NgTableParams({}, {
                    dataset : $scope.storeList
                });
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取门店错误' + data.errorMsg
                });
            });
        }

        initStoreList();

        $scope.refresh = function() {
            $scope.$emit("loadingStart");
            initStoreList();
            $scope.$emit("loadingEnd");
        }

        //删除门店
        $scope.removeStore = function(storeId){
            if(confirm("确认删除门店吗？")){
                storeService.removeStore(storeId, function(data){
                    // BootstrapDialog.show({
                    //     type : BootstrapDialog.TYPE_SUCCESS,
                    //     title : '消息',
                    //     message : '门店删除成功'
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
    }).controller("addStoreController",function($location, $scope, storeService){
        $scope.newStore = {};

        //保存门店信息
        $scope.saveStore = function(){
            storeService.addStore($scope.newStore, function(data){
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '门店创建成功'
                    });
                    $location.path("/storeManage");
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
    }).controller("editStoreController",function($location,$scope,$filter,storeService,$routeParams){
        $scope.selectStore={};

        storeService.getById($routeParams.storeId, function(data){
            $scope.selectStore = data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
            });
        });

        $scope.updateStore = function(){
            storeService.updateStore($scope.selectStore, function(data){
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '消息',
                        message : '修改成功'
                    });
                    $location.path("/storeManage");
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
        $scope.$emit("setActive", "storeManage");
    }
})();
