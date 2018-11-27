(function() {
    angular.module("businessOperationApp").controller("materielManageController", function($scope, NgTableParams, $filter, $location, materielService, dictionaryService) {
        setActiveSubPage($scope);
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.currentTab = 0;
        $scope.materielList = [];
        // 商品类型
        $scope.goodsTypeList = [];
        $scope.filterCondition = {};

        $scope.setCurrentTab = function(currentTab) {
            $scope.currentTab = currentTab;
        }

        // 查询商品类型
        dictionaryService.fetchGoodsTypeList(function(data){
            $scope.goodsTypeList = data.data;
            for(var i=0; i<$scope.goodsTypeList.length; i++){
                $("#goodsType").append("<option value='" + $scope.goodsTypeList[i].typeId + "'>" + $scope.goodsTypeList[i].typeName + "</option>");
            }
            $scope.goodsTypeSearchableSelect = $("#goodsType").searchableSelect(function (typeId) {
                if(angular.isDefined(typeId) && typeId != null && typeId != ''){
                    $scope.filterCondition.typeId = typeId;
                }else {
                    $scope.filterCondition.typeId = null;
                }
                $scope.$apply();
            });
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取商品类型失败:' + data.errorMsg
            });
        });


        // init table
        function initMaterielTable(){
            materielService.getMaterielTotalCount($scope.filterCondition, function(data){
                if(data.success){
                    $scope.materielTable = new NgTableParams({
                        page: 1,
                        count: 10
                    },{
                        total: data.data,
                        getData: function ($defer, params) {
                            $scope.filterCondition.pageNo = params.page();
                            getMaterielList($defer);
                        }
                    });
                }
            }, function(data){

            });
        }
        initMaterielTable();

        // 显示商品库存列表
        function getMaterielList($defer) {
            materielService.fetchMaterielListPage($scope.filterCondition, function(data){
                if(data.success){
                    $scope.materielList = data.data;
                    $defer.resolve($scope.materielList);
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '获取商品失败' + data.errorMsg
                    });
                }
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取商品错误' + data.errorMsg
                });
            });
        }

        // 查询销售商品
        $scope.queryMateriel = function(){
            initMaterielTable();
        }



        $scope.refresh = function() {
            $scope.$emit("loadingStart");
            initMaterielList();
            $scope.$emit("loadingEnd");
        }

        $scope.gotoEdit = function(id) {
            $location.path("/editMateriel/" + id);
        }

        //删除商品
        $scope.removeMateriel = function(id){
            if(confirm("删除商品将同时删除该商品的库存，确认删除吗？")){
                materielService.removeMateriel(id, function(data){
                    if(data.success){
                        $scope.refresh();
                    }else {
                        BootstrapDialog.show({
                            type : BootstrapDialog.TYPE_DANGER,
                            title : '失败',
                            message : data.errorMsg
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
        }
    }).controller("addMaterielController",function($location, $scope, materielService, dictionaryService, supplierService){
        $scope.newMateriel = {};
        // 商品类型
        $scope.goodsTypeList = [];
        // 过滤商品类型
        $scope.usedGoodsTypeList = [];
        // 商品种类
        $scope.goodsKindList = [];
        // // 商品品牌
        // $scope.goodsBrandList = [];
        // 商品单位
        $scope.goodsUnitList = [];
        // 供应商
        $scope.supplierList = [];

        // 查询供应商
        supplierService.fetchUseableSupplierList(function (data) {
            $scope.supplierList = data.data;
        },function (data) {
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取供应商失败:' + data.errorMsg
            });
        });

        // 查询商品类型
        dictionaryService.fetchGoodsTypeList(function(data){
            $scope.goodsTypeList = data.data;
            $scope.usedGoodsTypeList = data.data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取商品类型失败:' + data.errorMsg
            });
        });

        // 查询商品种类
        dictionaryService.fetchGoodsKindList(function (data) {
            $scope.goodsKindList = data.data;
        }, function (data) {
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取商品种类错误' + data.errorMsg
            });
        });

        // // 查询商品品牌
        // dictionaryService.fetchGoodsBrandList(function(data){
        //     $scope.goodsBrandList = data.data;
        // },function(data){
        //     BootstrapDialog.show({
        //         type : BootstrapDialog.TYPE_DANGER,
        //         title : '警告',
        //         message : '获取商品品牌失败:' + data.errorMsg
        //     });
        // });

        // 查询商品单位
        dictionaryService.fetchDictionaryByType("GOODS_UNIT", function(data){
            $scope.goodsUnitList = data.data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取商品单位失败:' + data.errorMsg
            });
        });

        // 选择商品种类
        $scope.selectGoodsKind = function(){
            $scope.usedGoodsTypeList = [];
            var kindId = $scope.newMateriel.kindId;
            if(kindId != 0){
                angular.forEach($scope.goodsTypeList, function (each) {
                    if(each.kindId == kindId){
                        $scope.usedGoodsTypeList.push(each);
                    }
                });
            }else {
                $scope.usedGoodsTypeList = $scope.goodsTypeList;
            }
        }

        //保存商品信息
        $scope.saveMateriel = function(){
            materielService.saveMateriel($scope.newMateriel, function(data){
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '商品创建成功'
                    });
                    $location.path("/materielManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '商品创建失败:'+data.errorMsg
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
    }).controller("editMaterielController",function($location,$scope,$filter,supplierService,materielService,dictionaryService,$routeParams){
        $scope.selectMateriel={};
        // 商品类型
        $scope.goodsTypeList = [];
        // 过滤商品类型
        $scope.usedGoodsTypeList = [];
        // 商品品牌
        $scope.goodsBrandList = [];
        // 商品单位
        $scope.goodsUnitList = [];
        // 供应商
        $scope.supplierList = [];
        // 商品种类
        $scope.goodsKindList = [];


        // 查询供应商
        supplierService.fetchUseableSupplierList(function (data) {
            $scope.supplierList = data.data;
        },function (data) {
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取供应商失败:' + data.errorMsg
            });
        });

        // 查询商品类型
        dictionaryService.fetchGoodsTypeList(function(data){
            $scope.goodsTypeList = data.data;
            $scope.usedGoodsTypeList = data.data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取商品类型失败:' + data.errorMsg
            });
        });

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

        // // 查询商品品牌
        // dictionaryService.fetchGoodsBrandList(function(data){
        //     $scope.goodsBrandList = data.data;
        // },function(data){
        //     BootstrapDialog.show({
        //         type : BootstrapDialog.TYPE_DANGER,
        //         title : '警告',
        //         message : '获取商品品牌失败:' + data.errorMsg
        //     });
        // });

        // 查询商品单位
        dictionaryService.fetchDictionaryByType("GOODS_UNIT", function(data){
            $scope.goodsUnitList = data.data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取商品单位失败:' + data.errorMsg
            });
        });

        materielService.getById($routeParams.id, function(data){
            $scope.selectMateriel = data.data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
            });
        });

        // 选择商品种类
        $scope.selectGoodsKind = function(){
            $scope.usedGoodsTypeList = [];
            var kindId = $scope.selectMateriel.kindId;
            if(kindId != 0){
                angular.forEach($scope.goodsTypeList, function (each) {
                    if(each.kindId == kindId){
                        $scope.usedGoodsTypeList.push(each);
                    }
                });
            }else {
                $scope.usedGoodsTypeList = $scope.goodsTypeList;
            }
        }

        $scope.updateMateriel = function(){
            // 是否停用
            if($("#useFlag").is(":checked")){
                $scope.selectMateriel.useFlag = 0;
            }else {
                $scope.selectMateriel.useFlag = 1;
            }
            $scope.selectMateriel.createTime = null;
            $scope.selectMateriel.updateTime = null;

            // 库存上下限校验
            if($scope.selectMateriel.minInventory > $scope.selectMateriel.maxInventory){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '库存上限不能大于库存下限'
                });
                return;
            }

            materielService.updateMateriel($scope.selectMateriel, function(data){
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '消息',
                        message : '修改成功'
                    });
                    $location.path("/materielManage");
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
    }).controller("materielDetailController",function($location,$scope,$filter,supplierService,materielService,dictionaryService,$routeParams){
        $scope.selectMateriel={};
        // 商品类型
        $scope.goodsTypeList = [];
        // 过滤商品类型
        $scope.usedGoodsTypeList = [];
        // 商品品牌
        $scope.goodsBrandList = [];
        // 商品单位
        $scope.goodsUnitList = [];
        // 供应商
        $scope.supplierList = [];
        // 商品种类
        $scope.goodsKindList = [];

        // 查询供应商
        supplierService.fetchUseableSupplierList(function (data) {
            $scope.supplierList = data.data;
        },function (data) {
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取供应商失败:' + data.errorMsg
            });
        });

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

        // 查询商品品牌
        dictionaryService.fetchGoodsBrandList(function(data){
            $scope.goodsBrandList = data.data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取商品品牌失败:' + data.errorMsg
            });
        });

        // 查询商品单位
        dictionaryService.fetchDictionaryByType("GOODS_UNIT", function(data){
            $scope.goodsUnitList = data.data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '警告',
                message : '获取商品单位失败:' + data.errorMsg
            });
        });

        materielService.getById($routeParams.id, function(data){
            $scope.selectMateriel = data.data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
            });
        });

        $scope.updateMateriel = function(){
            // 是否停用
            if($("#useFlag").is(":checked")){
                $scope.selectMateriel.useFlag = 0;
            }else {
                $scope.selectMateriel.useFlag = 1;
            }
            $scope.selectMateriel.createTime = null;
            $scope.selectMateriel.updateTime = null;

            // 库存上下限校验
            if($scope.selectMateriel.minInventory > $scope.selectMateriel.maxInventory){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '库存上限不能大于库存下限'
                });
                return;
            }

            materielService.updateMateriel($scope.selectMateriel, function(data){
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '消息',
                        message : '修改成功'
                    });
                    $location.path("/materielManage");
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
        $scope.$emit("setActive", "materielManage");
    }
})();
