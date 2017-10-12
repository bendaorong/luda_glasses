var globalSubNavApp = angular.module("globalSubNavApp", [ 'ngAnimate' ]);
globalSubNavApp.directive('subNav', function() {
	return {
		restrict : "E",
		templateUrl : "app/global/directive/subNav.html",
		replace : true,
		controller : function($scope) {
			$scope.$on("setActive", function(event, args) {
				for (var i = 0; i < $scope.pages.length; i++) {
					for (var j = 0; j < $scope.pages[i].subpages.length; j++) {
						if ($scope.pages[i].subpages[j].page === "#" + args) {
							$scope.currentCategory = $scope.pages[i].name;
							$scope.currentPage = $scope.pages[i].subpages[j];
							$scope.currentPageLength = $scope.pages[i].subpages.length;
							return;
						}
					}
				}
				$scope.currentCategory = undefined;
				$scope.currentPage = undefined;
			});
		}
	}
});
