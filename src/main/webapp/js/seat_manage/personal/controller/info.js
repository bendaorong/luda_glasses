console.log("test");

angular.module("personalSeatManageApp").controller('infoController', function($scope, $http, countries) {

	function init() {
/*		$http({
			method : 'GET',
			url : 'seat_manage/personal/fetchBasicInfo'
		}).success(function(data, status, headers, config) {
			$scope.basicInfo = {};
			$scope.originBasicInfo = {};
			console.log(data);
			$scope.basicInfo.legalEnterpriseName = data.legalEnterpriseName;
			$scope.basicInfo.enterpriseType = data.enterpriseType;
			console.log($scope.basicInfo);
			$scope.originBasicInfo = angular.copy($scope.basicInfo);
			
			$scope.otherBasicInfo = {};
			$scope.originOtherBasicInfo = {};
			$scope.otherBasicInfo.legalEnglishEnterpriseName = data.legalEnglishEnterpriseName;
			$scope.otherBasicInfo.abbrLegalEnterpriseName = data.abbrLegalEnterpriseName;
			$scope.otherBasicInfo.abbrLegalEnglishEnterpriseName = data.abbrLegalEnglishEnterpriseName;
			console.log($scope.otherBasicInfo);
			$scope.originOtherBasicInfo = angular.copy($scope.otherBasicInfo);
		
			$scope.industryModel = angular.copy(data.industrySectorVos);			
			
		}).error(function(data, status, headers, config) {
			alert("获取席位基本信息失败，原因是：" + data.ERROR);
		});
*/
		$http({
			method : 'GET',
			url : 'seat_manage/personal/fetchIdentifications'
		}).success(function(data, status, headers, config) {
			$scope.certifications = {};
			console.log(data);
			$scope.certifications = data;
			
		}).error(function(data, status, headers, config) {
			alert("获取个人证件失败，原因是：" + data.ERROR);
		});
		
	}//end of init()
	
	$scope.countries = countries;
	$scope.editBasicInfo = false;
	$scope.editOtherInfo = false;
	$scope.editId = false;
	$scope.seatId = {};
	
	
	$scope.changeBasicInfo = function() {
		$scope.editBasicInfo = true;
	}
	$scope.changeOtherInfo = function() {
		$scope.editOtherInfo = true;
	};
	
	$scope.cancelBasicInfo = function() {
		$scope.editBasicInfo = false;
		$scope.basicInfo = angular.copy($scope.originBasicInfo);
	}
	$scope.cancelOtherInfo = function() {
		$scope.editOtherInfo = false;
		$scope.otherBasicInfo = angular.copy($scope.originOtherBasicInfo);
	};
	$scope.cancelAddress = function() {
		$scope.editAddress = false;
	};
	
	$scope.submitBasicInfo = function(){
		$scope.editBasicInfo = false;		
    	$http.post("seat_manage/enterprise/updateBasicInfo",
    			{
    				"legalEnterpriseName": $scope.basicInfo.legalEnterpriseName,
    				"enterpriseType":$scope.basicInfo.enterpriseType
    			}
    		).success(function(data, status, headers, config) {
    	    	alert("企业席位基本信息修改成功");
    			console.log(data);
    			$scope.originBasicInfo = angular.copy($scope.basicInfo);
    			$("#enterprise_seat_Name").html($scope.basicInfo.legalEnterpriseName + '<b class="caret"></b>');
    		}).error(function(data, status, headers, config) {
    			alert("修改企业席位基本信息失败，原因是：" + data.ERROR);
    			//修改失败，需要回到编辑状态，以方便用户修改错误的输入
    			$scope.changeBasicInfo();
    		});
	}
	
	$scope.submitOtherInfo = function(){
		$scope.editOtherInfo = false;		
    	$http.post("seat_manage/enterprise/updateOtherBasicInfo",
    			{
    				"englishName": $scope.otherBasicInfo.legalEnglishEnterpriseName,
    				"abbrName": $scope.otherBasicInfo.abbrLegalEnterpriseName,
    				"abbrEnglishName":$scope.otherBasicInfo.abbrLegalEnglishEnterpriseName,
    				"industrySectors" : $scope.industryModel
    			}
    		).success(function(data, status, headers, config) {
    	    	alert("企业席位其他基本信息修改成功");
    			console.log(data);
    			$scope.originOtherBasicInfo = angular.copy($scope.otherBasicInfo);
    		}).error(function(data, status, headers, config) {
    			alert("修改企业席位其他基本信息失败，原因是：" + data.ERROR);
    			//修改失败，需要回到编辑状态，以方便用户修改错误的输入
    			$scope.changeOtherInfo();
    		});
	}
	
	
	$scope.addId = function() {
		$scope.editId = true;
		$scope.seatId = {};
		//set default values
		$scope.seatId.countryCode = 'CN';
		$scope.seatId.idType = 'Z88P01';
		$scope.seatId.validFrom = new Date();
		$scope.seatId.validTo = new Date();
	}
	
	$scope.changeId = function(countryCode, idType, idSN, idNo, validFrom, validTo) {
		$scope.editId = true;
		$scope.seatId.countryCode = countryCode;
		$scope.seatId.idType = idType;
		$scope.seatId.idSN = idSN;
		$scope.seatId.idNo = idNo;
		$scope.seatId.validFrom = new Date(validFrom);
		$scope.seatId.validTo = new Date(validTo);
	}

	$scope.cancelId = function() {
		$scope.editId = false;
	};
	$scope.submitId = function(){
		$scope.editId = false;
		//添加提交
		if($scope.seatId.idSN == undefined ){
	    	$http.post("seat_manage/personal/addIdentification",
	    			{
	    				"countryCode": $scope.seatId.countryCode,
	    				"idType": $scope.seatId.idType,
	    				"idNo":$scope.seatId.idNo,
	    				"validFrom" : $scope.seatId.validFrom,
	    				"validTo" : $scope.seatId.validTo
	    			}
	    		).success(function(data, status, headers, config) {
	    	    	alert("证件添加成功");
	    			console.log(data);
	    			$scope.certifications = data;//重新刷新执照列表
	    		}).error(function(data, status, headers, config) {
	    			alert("证件添加失败，原因是：" + data.ERROR);
	    			//修改失败，需要回到编辑状态，以方便用户修改错误的输入
	    			$scope.editId = true;		
	    		});
	    }
		//修改提交
		if($scope.seatId.idSN != undefined && $scope.seatId.idSN != ""){
	    	$http.post("seat_manage/personal/updateIdentification",
	    			{
	    				"countryCode": $scope.seatId.countryCode,
	    				"idType": $scope.seatId.idType,
	    				"idNo":$scope.seatId.idNo,
	    				"idSN": $scope.seatId.idSN,
	    				"validFrom" : $scope.seatId.validFrom,
	    				"validTo" : $scope.seatId.validTo
	    			}
	    		).success(function(data, status, headers, config) {
	    	    	alert("证件修改成功");
	    			console.log(data);
	    			$scope.certifications = data;//重新刷新执照列表
	    		}).error(function(data, status, headers, config) {
	    			alert("证件修改失败，原因是：" + data.ERROR);
	    			//修改失败，需要回到编辑状态，以方便用户修改错误的输入
	    			$scope.editId = true;		
	    		});
	    }
		
	}	
	$scope.removeId = function(id){
		$scope.editId = false;		
		$scope.seatId = {};//如果有修改或添加数据，清空，关闭。能先提示比较好。
    	$http.post("seat_manage/personal/deleteIdentification",
    			{
    		      "id" : id
    			}
    		).success(function(data, status, headers, config) {
    	    	alert("证件删除成功");
    			console.log(data);
    			$scope.certifications = data;//重新刷新执照列表
    		}).error(function(data, status, headers, config) {
    			alert("证件删除失败，原因是：" + data.ERROR);
    		});

	}	
	
    $scope.removeAttachment = function(id){
    	//if certification form is shown, remove it first, 
    	//otherwise it may make users confusing if the same certification is being edit
    	//如果有修改或添加数据，清空，关闭。能先提示比较好。
		$scope.editId = false;		
		$scope.seatId = {};
		
    	$http.post("seat_manage/personal/removeAttachment",
    			{
    		      "id" : id
    			}
    		).success(function(data, status, headers, config) {
    	    	alert("附件删除成功");
    			console.log(data);
    			$scope.certifications = data;//重新刷新执照列表
    		}).error(function(data, status, headers, config) {
    			alert("附件删除失败，原因是：" + data.ERROR);
    		});
    }
	
	
	init();
	
});
