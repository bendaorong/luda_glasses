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

        // 编辑供应商
        $scope.gotoEdit = function(supplierId) {
            $location.path("/editSupplier/" + supplierId);
        }

        // 供应商详情
        $scope.supplierDetail = function(supplierId) {
            $location.path("/supplierDetail/" + supplierId);
        }

        // 停用供应商
        $scope.disableSupplier = function (supplierId) {
            supplierService.disableSupplier(supplierId, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '停用成功'
                    });
                    $scope.refresh();
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '警告',
                        message : '操作失败;' + data.errorMsg
                    });
                }
            }, function (data) {
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '停用供应商错误' + data.errorMsg
                });
            });
        }

        // 启用供应商
        $scope.enableSupplier = function (supplierId) {
            supplierService.enableSupplier(supplierId, function (data) {
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '启用成功'
                    });
                    $scope.refresh();
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '警告',
                        message : '操作失败;' + data.errorMsg
                    });
                }
            }, function (data) {
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '启用供应商错误' + data.errorMsg
                });
            });
        }

        //删除供应商
        $scope.removeSupplier = function(supplierId){
            if(confirm("确认删除该供应商吗？")){
                supplierService.removeSupplier(supplierId, function(data){
                    // BootstrapDialog.show({
                    //     type : BootstrapDialog.TYPE_SUCCESS,
                    //     title : '消息',
                    //     message : '供应商删除成功'
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
    }).controller("supplierDetailController", function ($scope, $routeParams, $location, supplierService, NgTableParams) {
        $scope.selectSupplier = {};
        $scope.supplierContactList = [];

        // 供应商详情
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

        //供应商联系人
        function fetchSupplierContacts(){
            supplierService.fetchSupplierContactList($routeParams.supplierId, function(data){
                if(data.success){
                    $scope.supplierContactList = data.data;
                    $scope.tableParams = new NgTableParams({}, {
                        dataset : $scope.supplierContactList
                    });
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '获取供应商联系人失败:' + data.errorMsg
                    });
                }
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取供应商联系人错误' + data.errorMsg
                });
            });
        }
        fetchSupplierContacts();

        // 删除联系人
        $scope.removeContact = function (contactId) {
            if(confirm("确认删除该供应商联系人吗？")){
                supplierService.removeSupplierContact(contactId, function(data){
                    if(data.success){
                        BootstrapDialog.show({
                            type : BootstrapDialog.TYPE_SUCCESS,
                            title : '消息',
                            message : '供应商联系人删除成功'
                        });
                        $scope.refresh();
                        //$location.path("/supplierDetail/" + $routeParams.supplierId);
                    }else {
                        BootstrapDialog.show({
                            type : BootstrapDialog.TYPE_DANGER,
                            title : '供应商联系人删除失败',
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

        $scope.refresh = function() {
            $scope.$emit("loadingStart");
            fetchSupplierContacts();
            $scope.$emit("loadingEnd");
        }
        
        $scope.cancel = function(){
            history.back();
        }
    }).controller("editContactController", function ($scope, $routeParams, $location, supplierService) {
        $scope.selectContact = {};

        supplierService.getSupplierContactById($routeParams.contactId, function(data){
            if(data.success){
                $scope.selectContact = data.data;
            }else {
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '失败',
                    message : "获取供应商联系人信息失败:" + data.errorMsg
                });
            }
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
            });
        });

        $scope.updateSupplierContact = function(){
            // 是否为主要联系人
            if($("#headFlag").is(":checked")){
                $scope.selectContact.headFlag = "Y";
            }else {
                $scope.selectContact.headFlag = "N";
            }

            supplierService.updateSupplierContact($scope.selectContact, function(data){
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '消息',
                        message : '修改成功'
                    });
                    $location.path("/supplierDetail/" + $scope.selectContact.supplierId);
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
    }).controller("addContactController", function ($scope, $routeParams, $location, supplierService) {
        $scope.newContact = {};
        $scope.newContact.supplierId = $routeParams.supplierId;

        //保存供应商联系人信息
        $scope.saveSupplierContact = function(){
            // 是否为主要联系人
            if($("#headFlag").is(":checked")){
                $scope.newContact.headFlag = "Y";
            }else {
                $scope.newContact.headFlag = "N";
            }

            supplierService.addSupplierContact($scope.newContact, function(data){
                if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '供应商联系人创建成功'
                    });
                    $location.path("/supplierDetail/" + $scope.newContact.supplierId);
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '供应商联系人创建失败:'+data.errorMsg
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
