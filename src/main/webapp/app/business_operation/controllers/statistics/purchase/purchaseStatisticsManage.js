(function() {
    angular.module("businessOperationApp").controller("purchaseStatisticsController", function($scope, NgTableParams, $filter, $location, statisticsService, dictionaryService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }

        // 查询条件
        $scope.statisticsCondition = {};
        $scope.purchaseStatisticsByMaterielList = [];
        $scope.totalQuantity = 0;
        $scope.totalAmount = 0;

        // 初始化查询条件
        // 默认查询一个月内的销售数据
        $scope.statisticsCondition.beginDate = $filter('date')(addDate(new Date(), -30), "yyyy-MM-dd");
        $scope.statisticsCondition.endDate = $filter('date')(new Date(), "yyyy-MM-dd");

        // 按商品统计
        function purchaseStatisticsByMateriel(){
            statisticsService.purchaseStatisticsByMateriel($scope.statisticsCondition, function (data) {
                $scope.purchaseStatisticsByMaterielList = data.data;
                var totalQuantity = 0;
                var totalAmount = 0;
                angular.forEach($scope.purchaseStatisticsByMaterielList, function (each) {
                    totalQuantity += each.subtotalQuantity;
                    totalAmount += each.subtotalAmount;
                });
                $scope.totalQuantity = totalQuantity;
                $scope.totalAmount = totalAmount;
            }, function (data) {

            });
        }
        purchaseStatisticsByMateriel();

        // 按商品-过滤查询
        $scope.queryByMateriel = function () {
            var beginDate = $("#beginDate").val();
            var endDate = $("#endDate").val();
            if(beginDate != null && endDate != null){
                if(beginDate > endDate){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '错误',
                        message : '开始日期不可大于结束日期'
                    });
                    return false;
                }
                $scope.statisticsCondition.beginDate = beginDate;
                $scope.statisticsCondition.endDate = endDate;
            }
            purchaseStatisticsByMateriel();
        }

        // 查询商品类型
        dictionaryService.fetchGoodsTypeList(function(data){
            $scope.goodsTypeList = data.data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取商品类型失败:' + data.errorMsg
            });
        });

        /**
         * ------------------------------------------------------------------------------------------------
         */
        // 按供应商统计
        // 查询条件
        $scope.statisticsCondition_1 = {};
        $scope.purchaseStatisticsBySupplierList = [];
        $scope.totalQuantity_1 = 0;
        $scope.totalAmount_1 = 0;

        // 初始化查询条件
        // 默认查询一个月内的销售数据
        $scope.statisticsCondition_1.beginDate = $filter('date')(addDate(new Date(), -30), "yyyy-MM-dd");
        $scope.statisticsCondition_1.endDate = $filter('date')(new Date(), "yyyy-MM-dd");

        function purchaseStatisticsBySupplier(){
            statisticsService.purchaseStatisticsBySupplier($scope.statisticsCondition_1, function (data) {
                $scope.purchaseStatisticsBySupplierList = data.data;
                var totalQuantity = 0;
                var totalAmount = 0;
                angular.forEach($scope.purchaseStatisticsBySupplierList, function (each) {
                    totalQuantity += each.subtotalQuantity;
                    totalAmount += each.subtotalAmount;
                });
                $scope.totalQuantity_1 = totalQuantity;
                $scope.totalAmount_1 = totalAmount;
            }, function (data) {

            });
        }
        purchaseStatisticsBySupplier();

        // 按供应商-过滤查询
        $scope.queryByStore = function () {
            var beginDate = $("#beginDate_1").val();
            var endDate = $("#endDate_1").val();
            if(beginDate != null && endDate != null){
                if(beginDate > endDate){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '错误',
                        message : '开始日期不可大于结束日期'
                    });
                    return false;
                }
                $scope.statisticsCondition_1.beginDate = beginDate;
                $scope.statisticsCondition_1.endDate = endDate;
            }
            purchaseStatisticsBySupplier();
        }
    });

    // 日期加减
    function addDate(date,days){
        var d = new Date(date);
        d.setDate(d.getDate() + days);
        return d;
    }

    function setActiveSubPage($scope) {
        $scope.$emit("setActive", "purchaseStatisticsManage");
    }
})();
