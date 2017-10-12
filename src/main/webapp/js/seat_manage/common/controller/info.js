console.log("test");

angular.module("commonSeatManageApp").controller('infoController', function($scope, $http) {

	init();

	function init() {
		$scope.data = {};
		$scope.isModifyButtonShown = true;
		$scope.isSubmitButtonShown = false;
		$scope.isCancelButtonShown = false;
		$scope.isSeatNameReadonly = true;
		$scope.groupSeatName = {};

		$http({
			method : 'GET',
			url : 'seat_manage/getSessionInfo'
		}).success(function(data, status, headers, config) {
			console.log(data);
			$scope.groupSeatName = data.name;
			$scope.originGroupSeatName = $scope.groupSeatName;
		}).error(function(data, status, headers, config) {
			alert("获取会话信息失败，原因是：" + data.ERROR);
		});
		
		
		$http({
			method : 'GET',
			url : 'seat_manage/common/relationships'
		}).success(function(data, status, headers, config) {
			console.log(data);
			$scope.data = data;
		}).error(function(data, status, headers, config) {
			alert("获取共同席位成员失败，原因是：" + data.ERROR);
		});
	}

    $scope.confirmStatus = function (seatNumber, validFrom, validTo){
    	$http.post("seat_manage/common/confirmMembership",
			{
				"seatNumber": seatNumber,
				"validFrom": validFrom,
				"validTo": validTo,
				
			}
		).success(function(data, status, headers, config) {
	    	alert("共同席位成员确认成功");
			console.log(data);
			$scope.data = data;
		}).error(function(data, status, headers, config) {
			alert("确认共同席位成员失败，原因是：" + data.ERROR);
		});
    	
    }


    var updateGroupSeatName = function (seatName){
    	$http.post("seat_manage/common/updateGroupSeatName",
			{
				"seatName": seatName				
			}
		).success(function(data, status, headers, config) {
	    	alert("共同席位名称修改成功");
			console.log(data);
			$scope.originGroupSeatName = seatName;
			$("#group_seat_Name").html(seatName + '<b class="caret"></b>');
		}).error(function(data, status, headers, config) {
			alert("修改共同席位名称失败，原因是：" + data.ERROR);
			//修改失败，需要回到编辑状态
			$scope.modifySeatName();
		});
    	
    }
    
    
    $scope.modifySeatName = function(){
    	$scope.isModifyButtonShown = false;
    	$scope.isSubmitButtonShown = true;
    	$scope.isCancelButtonShown = true;
		$scope.isSeatNameReadonly = false;
    }

    
    $scope.submitSeatName = function(){
    	$scope.isModifyButtonShown = true;
    	$scope.isSubmitButtonShown = false;
    	$scope.isCancelButtonShown = false;
		$scope.isSeatNameReadonly = true;
    	
		updateGroupSeatName($scope.groupSeatName);
            	
    }
    

    $scope.cancelSeatName = function(){
    	$scope.isModifyButtonShown = true;
    	$scope.isSubmitButtonShown = false;
    	$scope.isCancelButtonShown = false;
		$scope.isSeatNameReadonly = true;
    	
	    $scope.groupSeatName = $scope.originGroupSeatName;
    }


});




