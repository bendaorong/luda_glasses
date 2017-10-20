(function() {
    angular.module("businessOperationApp").controller("goodsColorManageController", function($scope, NgTableParams, $filter, $location, dictionaryService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;
        $scope.goodsColorList = [];

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }

        $scope.gotoEdit = function(colorId) {
            $location.path("/editGoodsColor/" + colorId);
        }

        // 显示颜色列表
        function initGoodsColorList() {
            dictionaryService.fetchGoodsColorList(function(data){
                $scope.goodsColorList  =  data.data;
                $scope.tableParams = new NgTableParams({}, {
                    dataset : $scope.goodsColorList
                });
                console.log("size:"+$scope.goodsColorList.length);
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取颜色错误' + data.errorMsg
                });
            });
        }

        initGoodsColorList();

        $scope.refresh = function() {
            $scope.$emit("loadingStart");
            initGoodsColorList();
            $scope.$emit("loadingEnd");
        }

        //删除颜色
        $scope.removeGoodsColor = function(colorId){
            if(confirm("确认删除该颜色吗？")){
                dictionaryService.removeGoodsColor(colorId, function(data){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '消息',
                        message : '颜色删除成功'
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
    }).controller("addGoodsColorController",function($location, $scope, dictionaryService){
        $scope.newGoodsColor = {};

        //保存颜色
        $scope.saveGoodsColor = function(){
            dictionaryService.saveGoodsColor($scope.newGoodsColor, function(data){
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '颜色创建成功'
                    });
                    $location.path("/goodsColorManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '颜色创建失败:'+data.errorMsg
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
    }).controller("editGoodsColorController",function($location,$scope,$filter,dictionaryService,$routeParams){
        $scope.selectGoodsColor={};

        dictionaryService.getGoodsColorById($routeParams.colorId, function(data){
            $scope.selectGoodsColor = data.data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
            });
        });

        $scope.updateGoodsColor = function(){
            dictionaryService.updateGoodsColor($scope.selectGoodsColor, function(data){
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '消息',
                        message : '修改成功'
                    });
                    $location.path("/goodsColorManage");
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
        $scope.$emit("setActive", "goodsColorManage");
    }
})();
