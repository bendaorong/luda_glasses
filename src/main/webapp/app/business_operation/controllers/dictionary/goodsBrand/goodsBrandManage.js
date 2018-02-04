(function() {
    angular.module("businessOperationApp").controller("goodsBrandManageController", function($scope, NgTableParams, $filter, $location, dictionaryService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;
        $scope.goodsBrandList = [];

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }

        $scope.gotoEdit = function(brandId) {
            $location.path("/editGoodsBrand/" + brandId);
        }

        // 显示品牌列表
        function initGoodsBrandList() {
            dictionaryService.fetchGoodsBrandList(function(data){
                $scope.goodsBrandList  =  data.data;
                $scope.tableParams = new NgTableParams({}, {
                    dataset : $scope.goodsBrandList
                });
                console.log("size:"+$scope.goodsBrandList.length);
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取品牌错误' + data.errorMsg
                });
            });
        }
        initGoodsBrandList();

        $scope.refresh = function() {
            $scope.$emit("loadingStart");
            initGoodsBrandList();
            $scope.$emit("loadingEnd");
        }

        //删除品牌
        $scope.removeGoodsBrand = function(brandId){
            if(confirm("确认删除该品牌吗？")){
                dictionaryService.removeGoodsBrand(brandId, function(data){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '消息',
                        message : '品牌删除成功'
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
    }).controller("addGoodsBrandController",function($location, $scope, dictionaryService){
        $scope.newGoodsBrand = {};

        //保存品牌
        $scope.saveGoodsBrand = function(){
            dictionaryService.saveGoodsBrand($scope.newGoodsBrand, function(data){
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '品牌创建成功'
                    });
                    $location.path("/goodsBrandManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '品牌创建失败:'+data.errorMsg
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
    }).controller("editGoodsBrandController",function($location,$scope,$filter,dictionaryService,$routeParams){
        $scope.selectGoodsBrand = {};

        dictionaryService.getGoodsBrandById($routeParams.brandId, function(data){
            $scope.selectGoodsBrand = data.data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
            });
        });

        $scope.updateGoodsBrand = function(){
            dictionaryService.updateGoodsBrand($scope.selectGoodsBrand, function(data){
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '消息',
                        message : '修改成功'
                    });
                    $location.path("/goodsBrandManage");
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
        $scope.$emit("setActive", "goodsBrandManage");
    }
})();
