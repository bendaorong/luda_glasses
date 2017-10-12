angular.module("businessOperationApp").controller("fixTermDepositRateController", function($scope, NgTableParams) {

	var originalData = {
		name : "大赚钱基金",
		currentRate : "4.5%",
		effectiveDate : "2015.09.01",
		
	};
	$scope.data = [];
	for (var i = 0; i < 250; i++) {
		var copyData = Object.create(originalData)
		copyData.number = i;
		copyData.name += i;
		$scope.data.push(Object.create(copyData));
	}

	$scope.tableParams = new NgTableParams({}, {
		dataset : $scope.data
	});


});