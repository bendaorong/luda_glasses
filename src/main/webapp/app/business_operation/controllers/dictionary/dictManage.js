(function() {
    angular.module("businessOperationApp").controller("goodsTypeManageController", function($scope, NgTableParams, $filter, $location, dictionaryService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;
        $scope.goodsTypeList = [];

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }

        $scope.gotoEdit = function(typeId) {
            $location.path("/editGoodsType/" + typeId);
        }

        // 显示商品类型列表
        function initGoodsTypeList() {
            dictionaryService.fetchGoodsTypeList(function(data){
                $scope.goodsTypeList  =  data.data;
                $scope.tableParams = new NgTableParams({}, {
                    dataset : $scope.goodsTypeList
                });
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取商品类型错误' + data.errorMsg
                });
            });
        }

        initGoodsTypeList();

        $scope.refresh = function() {
            $scope.$emit("loadingStart");
            initGoodsTypeList();
            $scope.$emit("loadingEnd");
        }

        //删除商品类型
        $scope.removeGoodsType = function(typeId){
            if(confirm("确认删除该商品类型吗？")){
                dictionaryService.removeGoodsType(typeId, function(data){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '消息',
                        message : '商品类型删除成功'
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
    }).controller("addGoodsTypeController",function($location, $scope, dictionaryService){
        $scope.newGoodsType = {};
        $scope.goodsKindList = [];

        // 查询商品种类
        dictionaryService.fetchGoodsKindList(function (data) {
            $scope.goodsKindList = data.data;
        }, function (data) {
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取商品类型错误' + data.errorMsg
            });
        });

        //保存商品类型
        $scope.saveGoodsType = function(){
            dictionaryService.saveGoodsType($scope.newGoodsType, function(data){
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '商品类型创建成功'
                    });
                    $location.path("/goodsTypeManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '商品类型创建失败:'+data.errorMsg
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
    }).controller("editGoodsTypeController",function($location,$scope,$filter,dictionaryService,$routeParams){
        $scope.selectGoodsType={};
        $scope.goodsKindList = [];

        // 查询商品种类
        dictionaryService.fetchGoodsKindList(function (data) {
            $scope.goodsKindList = data.data;
        }, function (data) {
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取商品类型错误' + data.errorMsg
            });
        });

        dictionaryService.getGoodsTypeById($routeParams.typeId, function(data){
            $scope.selectGoodsType = data.data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
            });
        });

        $scope.updateGoodsType = function(){
            dictionaryService.updateGoodsType($scope.selectGoodsType, function(data){
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '消息',
                        message : '修改成功'
                    });
                    $location.path("/goodsTypeManage");
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
        $scope.$emit("setActive", "goodsTypeManage");
    }
})();
