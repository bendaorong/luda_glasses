var globalLanguageApp = angular.module("globalLanguageApp");
globalLanguageApp.directive('languageSwitch', function() {
	return {
		restrict : "E",
		templateUrl : "app/global/directive/language_switch.html",
		replace : true
	}
});