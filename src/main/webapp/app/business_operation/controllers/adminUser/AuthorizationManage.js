(function() {
	angular.module("businessOperationApp").controller("authorizationManageController", function($scope, NgTableParams, $filter, $location, adminUserService) {
		setActiveSubPage($scope);
		$scope.roleCode = sessionStorage.getItem("roleCode");
		$scope.currentTab = 0;
		$scope.adminUserList = [];

		$scope.setCurrentTab = function(currentTab) {
			$scope.currentTab = currentTab;
		}

		$scope.gotoEdit = function(adminUserId) {
			$location.path("/editAdminUser/" + adminUserId);
		}

		// $scope.gotoModifyPwd =  function(cellPhoneNumber){
		// 	$location.path("/gotoModifyPwd/" + cellPhoneNumber);
		// }

        // 显示员工列表
		function initAdminUserList() {
			adminUserService.fetchUserList(function(data){
				$scope.adminUserList  =  data;
				$scope.tableParams = new NgTableParams({}, {
					dataset : $scope.adminUserList
				});
				console.log("size:"+$scope.adminUserList.length);
			},function(data){
				BootstrapDialog.show({
					type : BootstrapDialog.TYPE_DANGER,
					title : '警告',
					message : '获取员工错误' + data.errorMsg
				});

			});
		}

		initAdminUserList();

		$scope.refresh = function() {
			$scope.$emit("loadingStart");
			initAdminUserList();
			$scope.$emit("loadingEnd");
		}

		//删除后台用户
		$scope.removeAdminUser = function(adminUserId){
			if(confirm("确认删除用户吗")){
                adminUserService.deleteAdminUser(adminUserId, function(data){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '消息',
                        message : '用户删除成功'
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

		//重置后台用户密码
		$scope.resetAdminUserPwd = function(adminUserId){
			adminUserService.resetAdminUserPwd(adminUserId, function(data){
			    if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '密码重置为手机号'
                    });
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : "重设密码失败:"+data.errorMsg
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
	}).controller("addAdminUserController",function($location,$scope,$filter,adminUserService, storeService){
		$scope.newAdminUser = {};
		$scope.storeList = [];
		$scope.roleList = [];

		//初始化门店下拉框
		function initStoreList(){
			storeService.fetchStoreList(function(data){
				$scope.storeList = data;
			},function(data){
				BootstrapDialog.show({
					type : BootstrapDialog.TYPE_DANGER,
					title : '警告',
					message : '获取门店信息失败:' + data.errorMsg
				});
			});
		}

		//初始化角色下拉框
		function initRoleList(){
            adminUserService.getAdminRoleList(function(data){
                $scope.roleList = data;
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取角色信息失败:' + data.errorMsg
                });
            });
		}

        initStoreList();
        initRoleList();

		//保存管理员
		$scope.saveAdminUser = function(){
		    // 是否激活
		    if($("#activeFlag").attr("checked")){
                $scope.newAdminUser.activeFlag = 1;
            }else {
                $scope.newAdminUser.activeFlag = 0;
            }
            // 入职时间
		    $scope.newAdminUser.adminUserDetailModel.onboardDate = $("#onboardDate").val();
            $scope.newAdminUser.adminUserDetailModel.birthday = $("#birthday").val();

			adminUserService.addAdminUser($scope.newAdminUser,function(data){
				if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '用户创建成功'
                    });
                    $location.path("/AuthorizationManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : '用户创建失败:' + data.errorMsg
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
	}).controller("editAdminUserController",function($location,$scope,$filter,adminUserService,storeService,$routeParams){
		$scope.roleCode = sessionStorage.getItem("roleCode");
		$scope.selectAdminUser={};
        $scope.roleList = [];
        $scope.storeList = [];
        //初始化门店下拉框
        function initStoreList(){
            storeService.fetchStoreList(function(data){
                $scope.storeList = data;
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取门店信息失败:' + data.errorMsg
                });
            });
        }

        //初始化角色下拉框
        function initRoleList(){
            adminUserService.getAdminRoleList(function(data){
                $scope.roleList = data;
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取角色信息失败:' + data.errorMsg
                });
            });
        }

        initStoreList();
        initRoleList();

		adminUserService.getWithDetailById($routeParams.adminUserId,function(data){
			$scope.selectAdminUser = data;
		},function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
            });
		});

		$scope.updateAdminUser = function(){
		    // 是否激活
		    if($("#activeFlag").attr("checked")){
		        $scope.selectAdminUser.activeFlag = 1;
            }else {
                $scope.selectAdminUser.activeFlag = 0;
            }
            // 入职时间
            $scope.selectAdminUser.adminUserDetailModel.onboardDate = $("#onboardDate").val();
            // 离职时间
            $scope.selectAdminUser.adminUserDetailModel.dimissionDate = $("#dimissionDate").val();

			adminUserService.updateAdminUser($scope.selectAdminUser,function(data){
			    if(data.success){
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_SUCCESS,
                        title : '成功',
                        message : '修改成功'
                    });
                    $location.path("/AuthorizationManage");
                }else {
                    BootstrapDialog.show({
                        type : BootstrapDialog.TYPE_DANGER,
                        title : '失败',
                        message : "修改失败:"+data.errorMsg
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

		$scope.modifyAdminUserPwd = function(){
			adminUserService.modifyAdminUserPwd($scope.selectAdminUser,function(data){
				BootstrapDialog.show({
					type : BootstrapDialog.TYPE_SUCCESS,
					title : '消息',
					message : '密码修改成功'
				});
				$location.path("/AuthorizationManage");
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
			$scope.newAdminUser;
		}

	}).controller("adminUserDetailController",function($location,$scope,$filter,adminUserService,storeService,$routeParams){
        $scope.roleCode = sessionStorage.getItem("roleCode");
        $scope.selectAdminUser={};
        $scope.roleList = [];
        $scope.storeList = [];
        //初始化门店下拉框
        function initStoreList(){
            storeService.fetchStoreList(function(data){
                $scope.storeList = data;
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取门店信息失败:' + data.errorMsg
                });
            });
        }

        //初始化角色下拉框
        function initRoleList(){
            adminUserService.getAdminRoleList(function(data){
                $scope.roleList = data;
            },function(data){
                BootstrapDialog.show({
                    type : BootstrapDialog.TYPE_DANGER,
                    title : '警告',
                    message : '获取角色信息失败:' + data.errorMsg
                });
            });
        }

        initStoreList();
        initRoleList();

        adminUserService.getWithDetailById($routeParams.adminUserId, function(data){
            $scope.selectAdminUser = data;
        },function(data){
            BootstrapDialog.show({
                type : BootstrapDialog.TYPE_DANGER,
                title : '错误',
                message : data.errorMsg
            });
        });

        $scope.cancel = function(){
            history.back();
            $scope.newAdminUser;
        }
    });

	function setActiveSubPage($scope) {
		$scope.$emit("setActive", "AuthorizationManage");
	}
})();
