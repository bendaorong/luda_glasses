var globalNavApp = angular.module("globalNavApp", [ 'globalConstantApp' ]);
globalNavApp.directive('navTab', function() {
	return {
		restrict : "E",
		templateUrl : "app/global/directive/navTab.html",
		replace : true,
		controller : 'navTabController',
		link: function(scope, element, attrs){
			scope["currentTab"] = attrs["currentTab"];
		}
	}
});
globalNavApp.controller('navTabController', function($scope, $element, $attrs, $transclude, navTabs) {
	$scope.navTabs = navTabs;
	$scope.getHref = function(navTab) {
		if ($scope.currentTab === navTab) {
			return "#";
		} else {
			return navTab;
		}
	}
	$scope.isActive = function(navTab) {
		if ($scope.currentTab === navTab) {
			return true;
		} else {
			return false;
		}
	}

});