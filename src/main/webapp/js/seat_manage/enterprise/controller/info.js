console.log("test");

angular.module("enterpriseSeatManageApp").controller('infoController', function($scope, $http, countries) {

	function init() {
		$http({
			method : 'GET',
			url : 'seat_manage/enterprise/fetchBasicInfo'
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

		$http({
			method : 'GET',
			url : 'seat_manage/enterprise/fetchIdentifications'
		}).success(function(data, status, headers, config) {
			$scope.certifications = {};
			console.log(data);
			$scope.certifications = data;
			
		}).error(function(data, status, headers, config) {
			alert("获取企业执照失败，原因是：" + data.ERROR);
		});

		$http({
			method : 'GET',
			url : 'seat_manage/enterprise/fetchAddresses'
		}).success(function(data, status, headers, config) {
			$scope.addresses = {};
			console.log(data);
			$scope.addresses = data;
			
		}).error(function(data, status, headers, config) {
			alert("获取企业常用地址失败，原因是：" + data.ERROR);
		});

		
	}//end of init()
	
	$scope.countries = countries;
	$scope.editBasicInfo = false;
	$scope.editOtherInfo = false;
	$scope.editAddress = false;
	$scope.seatAddress = {};
	$scope.editId = false;
	$scope.seatId = {};
	
	
	$scope.changeBasicInfo = function() {
		$scope.editBasicInfo = true;
	}
	$scope.changeOtherInfo = function() {
		$scope.editOtherInfo = true;
	};
	$scope.changeAddress = function() {
		$scope.editAddress = true;
	}
	$scope.addAddress = function() {
		$scope.editAddress = true;
		$scope.seatAddress = {};
	}
	
	$scope.updateAddress = function(addressId, extaddressnumber, countryCode, region, city, address, postalCode){
		$scope.editAddress = true;
		$scope.seatAddress.addressId = addressId;
		$scope.seatAddress.extaddressnumber = extaddressnumber;
		$scope.seatAddress.countryCode = countryCode;
		$scope.seatAddress.region = region;
		$scope.seatAddress.city = city;
		$scope.seatAddress.address = address;
		$scope.seatAddress.postalCode = postalCode;
	}
	
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


/*	$("#businessSectorSelect").multiselect({
		nSelectedText : '项',
		nonSelectedText: '请选择',
		allSelectedText: '全选'
	});
*/	


	$scope.industryModel = []; 
	$scope.industryData = [ {id: 11, label: "Oil,Gas"}, 
	                        {id: 12, label: "Raw Materials"}, 
	                        {id: 13, label: "Precious Metals"}, 
	                        {id: 14, label: "Agriculture and Livestock Farming"}
	                      ];	
	
	$scope.industrySettings = {
		    smartButtonMaxItems: 3		  
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
	
	$scope.submitAddress = function(){
		$scope.editAddress = false;		
		if($scope.seatAddress.addressId == undefined ){
	    	$http.post("seat_manage/enterprise/addAddresses",
	    			{
	    				"countryCode": $scope.seatAddress.countryCode,
	    				"region": $scope.seatAddress.region,
	    				"city":$scope.seatAddress.city,
	    				"postalCode" : $scope.seatAddress.postalCode,
	    				"address" : $scope.seatAddress.address
	    			}
	    		).success(function(data, status, headers, config) {
	    	    	alert("地址添加成功");
	    			console.log(data);
	    			$scope.addresses = data;//重新刷新地址列表
	    		}).error(function(data, status, headers, config) {
	    			alert("地址添加失败，原因是：" + data.ERROR);
	    			//修改失败，需要回到编辑状态，以方便用户修改错误的输入
	    			$scope.editAddress = true;		
	    		});
	    }
		if($scope.seatAddress.addressId != undefined && $scope.seatAddress.addressId != ""){
			
			if($scope.seatAddress.extaddressnumber == 'ZZZZ'){
				var conf = confirm("修改注册地址需重新审核，席位会被临时冻结。是否继承？");
				if(conf==false){//选择了取消  
			        return;  
			     }
			}
			
	    	$http.post("seat_manage/enterprise/updateAddresses",
	    			{
         				"addressId": $scope.seatAddress.addressId,
         				"extaddressnumber": $scope.seatAddress.extaddressnumber,
	    				"countryCode": $scope.seatAddress.countryCode,
	    				"region": $scope.seatAddress.region,
	    				"city":$scope.seatAddress.city,
	    				"postalCode" : $scope.seatAddress.postalCode,
	    				"address" : $scope.seatAddress.address
	    			}
	    		).success(function(data, status, headers, config) {
	    	    	alert("地址修改成功");
	    			console.log(data);
	    			$scope.addresses = data;//重新刷新地址列表
	    		}).error(function(data, status, headers, config) {
	    			alert("地址修改失败，原因是：" + data.ERROR);
	    			//修改失败，需要回到编辑状态，以方便用户修改错误的输入
	    			$scope.editAddress = true;		
	    		});
	    }
		
	}
	
	$scope.removeAddress = function(addressId){
    	$http.post("seat_manage/enterprise/removeAddress",
    			{
     				"addressId": addressId
    			}
    		).success(function(data, status, headers, config) {
    	    	alert("地址删除成功");
    			console.log(data);
    			$scope.addresses = data;//重新刷新地址列表
    		}).error(function(data, status, headers, config) {
    			alert("地址删除失败，原因是：" + data.ERROR);
    		});
	}
	
	
	$scope.addId = function() {
		$scope.editId = true;
		$scope.seatId = {};
		//set default values
		$scope.seatId.countryCode = 'CN';
		$scope.seatId.idType = 'Z88I01';
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
	    	$http.post("seat_manage/enterprise/addIdentification",
	    			{
	    				"countryCode": $scope.seatId.countryCode,
	    				"idType": $scope.seatId.idType,
	    				"idNo":$scope.seatId.idNo,
	    				"validFrom" : $scope.seatId.validFrom,
	    				"validTo" : $scope.seatId.validTo
	    			}
	    		).success(function(data, status, headers, config) {
	    	    	alert("执照添加成功");
	    			console.log(data);
	    			$scope.certifications = data;//重新刷新执照列表
	    		}).error(function(data, status, headers, config) {
	    			alert("执照添加失败，原因是：" + data.ERROR);
	    			//修改失败，需要回到编辑状态，以方便用户修改错误的输入
	    			$scope.editId = true;		
	    		});
	    }
		//修改提交
		if($scope.seatId.idSN != undefined && $scope.seatId.idSN != ""){
	    	$http.post("seat_manage/enterprise/updateIdentification",
	    			{
	    				"countryCode": $scope.seatId.countryCode,
	    				"idType": $scope.seatId.idType,
	    				"idNo":$scope.seatId.idNo,
	    				"idSN": $scope.seatId.idSN,
	    				"validFrom" : $scope.seatId.validFrom,
	    				"validTo" : $scope.seatId.validTo
	    			}
	    		).success(function(data, status, headers, config) {
	    	    	alert("执照修改成功");
	    			console.log(data);
	    			$scope.certifications = data;//重新刷新执照列表
	    		}).error(function(data, status, headers, config) {
	    			alert("执照修改失败，原因是：" + data.ERROR);
	    			//修改失败，需要回到编辑状态，以方便用户修改错误的输入
	    			$scope.editId = true;		
	    		});
	    }
		
	}	
	$scope.removeId = function(id){
		$scope.editId = false;		
		$scope.seatId = {};//如果有修改或添加数据，清空，关闭。能先提示比较好。
    	$http.post("seat_manage/enterprise/deleteIdentification",
    			{
    		      "id" : id
    			}
    		).success(function(data, status, headers, config) {
    	    	alert("执照删除成功");
    			console.log(data);
    			$scope.certifications = data;//重新刷新执照列表
    		}).error(function(data, status, headers, config) {
    			alert("执照删除失败，原因是：" + data.ERROR);
    		});

	}	
	
    $scope.removeAttachment = function(id){
    	//if certification form is shown, remove it first, 
    	//otherwise it may make users confusing if the same certification is being edit
    	//如果有修改或添加数据，清空，关闭。能先提示比较好。
		$scope.editId = false;		
		$scope.seatId = {};
		
    	$http.post("seat_manage/enterprise/removeAttachment",
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
