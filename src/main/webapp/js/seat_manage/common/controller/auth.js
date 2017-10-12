console.log("test");

angular.module("commonSeatManageApp").controller('authController', function($scope, $http) {

	init();

	$scope.refreshData = function() {
		init();
	}

	$scope.showMarketAuth = function(sMarketCode) {
		$scope.currentSelectedMarketCode = sMarketCode;
		// angular.element("a.list-group-item[data-marketcode]").removeClass("active");
		// angular.element("a.list-group-item[data-marketcode='" + sMarketCode +
		// "']").addClass("active");

		// Clear first
		$scope.currentSeatAdditionalInfosforA = [];
		$scope.currentSeatAdditionalInfosforL = [];
		$scope.currentSeatAdditionalInfosforZ = [];
		$scope.currentSeatAdditionalInfoforG00 = undefined;
		for ( var index in $scope.data) {
			if ($scope.data[index].market.marketCode === sMarketCode) {
				$scope.currentSeatAdditionalInfos = $scope.data[index].seatAdditionalInfos;
				classifyAuth($scope.currentSeatAdditionalInfos);
				$scope.bShowMarketBlock = showMarketBlockFlag($scope.currentSeatAdditionalInfos);
				break;
			}
		}
	}
	$scope.changeAuth = function(investArea) {
		for (var i = 0; i < $scope.currentSeatAdditionalInfos.length; i++) {
			if ($scope.currentSeatAdditionalInfos[i].investArea === investArea) {
				$scope.currentSeatAdditionalInfos[i].selfAuth = ($scope.currentSeatAdditionalInfos[i].selfAuth === "X" ? "" : "X");
				break;
			}
		}
	}
	$scope.changeMode = function() {
		$scope.bShowEditButton = !$scope.bShowEditButton;
		$scope.bShowSubmitButton = !$scope.bShowSubmitButton;
		$scope.bShowCancelButton = !$scope.bShowCancelButton;
		$scope.bShowChangeButton = !$scope.bShowChangeButton;
	}
	$scope.submitAuth = function() {
		var market = null;
		
		if ($scope.currentSelectedMarketCode) {
			var isAvailableMarket = false;
			for (var i = 0; i < $scope.availableMarkets.length; i++) {
				if ($scope.currentSelectedMarketCode === $scope.availableMarkets[i]) {
					isAvailableMarket = true;
					break;
				}
			}
			if (isAvailableMarket) {
				market = {};
				market.marketCode = $scope.currentSelectedMarketCode;
				market.partnerRole = "FS003"; // hard code
				market.currency = "CNY";
				market.seatNumber = "";
				
				$http.post("rest/seat_manage/personal/market", {
					"market" : market,
					"seatAdditionalInfos" : $scope.currentSeatAdditionalInfos
				}).success(function() {
					alert("添加成功");
					init();
				}).error(function(data) {
					alert("添加失败，原因是：" + data.errorMsg);
					init();
				});
			} else {
				for (var i = 0; i < $scope.data.length; i++) {
					if ($scope.data[i].market.marketCode === $scope.currentSelectedMarketCode) {
						market = $scope.data[i].market;
						break;
					}
				}
				$http.put("rest/seat_manage/personal/market", {
					"market" : market,
					"seatAdditionalInfos" : $scope.currentSeatAdditionalInfos
				}).success(function() {
					alert("修改成功");
					$scope.changeMode();
				}).error(function(data) {
					alert("修改失败，原因是：" + data.errorMsg);
					init();
				});
			}
		}
	}
	$scope.addMarket = function(availableMarket) {
		$scope.currentSelectedMarketCode = availableMarket;
		console.log($scope.currentSelectedMarketCode);
		$http({
			method : 'GET',
			url : 'rest/seat_manage/personal/availableMarketTemplate/' + availableMarket
		}).success(function(data, status, headers, config) {
			console.log(data);
			// Clear first
			$scope.currentSeatAdditionalInfosforA = [];
			$scope.currentSeatAdditionalInfosforL = [];
			$scope.currentSeatAdditionalInfosforZ = [];
			$scope.currentSeatAdditionalInfoforG00 = undefined;
			$scope.currentSeatAdditionalInfos = data;
			classifyAuth($scope.currentSeatAdditionalInfos);
			$scope.bShowMarketBlock = showMarketBlockFlag($scope.currentSeatAdditionalInfos);
			$scope.aMarketsInUse.push(availableMarket);
			$scope.currentSelectedMarketCode = availableMarket;

			$scope.changeMode();
		}).error(function(data, status, headers, config) {
			alert("获取可入市场模板失败，原因是：" + data.errorMsg);
			init();
		});
	}
	function showMarketBlockFlag(aSeatAdditionalInfos) {
		for (var i = 0; i < aSeatAdditionalInfos.length; i++) {
			if (aSeatAdditionalInfos[i].investArea === "G00" && aSeatAdditionalInfos[i].legalAllowed !== "N") {
				return true;
			}
		}
		return false;
	}
	function classifyAuth(aCurrentSeatAdditionalInfos) {
		for (var i = 0; i < aCurrentSeatAdditionalInfos.length; i++) {
			if ($scope.currentSeatAdditionalInfos[i].investArea.startsWith('A')) {
				$scope.currentSeatAdditionalInfosforA.push($scope.currentSeatAdditionalInfos[i]);
			} else if ($scope.currentSeatAdditionalInfos[i].investArea.startsWith('L')) {
				$scope.currentSeatAdditionalInfosforL.push($scope.currentSeatAdditionalInfos[i]);
			} else if (($scope.currentSeatAdditionalInfos[i].investArea.startsWith('Z'))) {
				$scope.currentSeatAdditionalInfosforZ.push($scope.currentSeatAdditionalInfos[i]);
			} else if  ($scope.currentSeatAdditionalInfos[i].investArea === 'G00'){
				$scope.currentSeatAdditionalInfoforG00 = $scope.currentSeatAdditionalInfos[i];
			}
		}
	}
	function init() {
		$scope.bShowEditButton = true;
		$scope.bShowSubmitButton = false;
		$scope.bShowCancelButton = false;
		$scope.bShowMarketBlock = false;
		$scope.bShowChangeButton = false;
		$scope.currentSelectedMarketCode = "";
		$scope.currentSeatAdditionalInfosforA = [];
		$scope.currentSeatAdditionalInfosforL = [];
		$scope.currentSeatAdditionalInfosforZ = [];
		$scope.currentSeatAdditionalInfoforG00 = undefined;
		$scope.data = {};
		$scope.availableMarkets = [];
		$http({
			method : 'GET',
			url : 'rest/seat_manage/personal/market'
		}).success(function(data, status, headers, config) {
			console.log(data);
			$scope.data = data;
			// get markets already in
			var aMarketsInUse = [];
			for ( var index in data) {
				aMarketsInUse.push(data[index].market.marketCode);
			}
			$scope.aMarketsInUse = aMarketsInUse;

			if (data[0]) {
				$scope.currentSelectedMarketCode = data[0].market.marketCode;
				$scope.currentSeatAdditionalInfos = data[0].seatAdditionalInfos;
				classifyAuth($scope.currentSeatAdditionalInfos);
				$scope.bShowMarketBlock = showMarketBlockFlag($scope.currentSeatAdditionalInfos);

			}

		}).error(function(data, status, headers, config) {
			alert("获取市场权限失败，原因是：" + data.errorMsg);
		});

		$http({
			method : 'GET',
			url : 'rest/seat_manage/personal/availableMarket'
		}).success(function(data, status, headers, config) {
			console.log(data);
			$scope.availableMarkets = data;

		}).error(function(data, status, headers, config) {
			alert("获取可入市场失败，原因是：" + data.errorMsg);
		});

	}
});