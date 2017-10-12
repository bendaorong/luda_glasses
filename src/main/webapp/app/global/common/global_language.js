/**
 * 
 */
var globalLanguageApp = angular.module("globalLanguageApp", [ 'pascalprecht.translate', 'globalConstantApp' ]);
globalLanguageApp.factory("cookieService", function() {
	return {
		get : function(key) {
			var arr = document.cookie.split('; ');
			for (var i = 0; i < arr.length; i++) {
				var arr2 = arr[i].split('=');
				if (arr2[0] == key) {
					return arr2[1];
				}
			}
			return '';
		},
		set : function(name, value, iDay) {
			var oDate = new Date();
			oDate.setDate(oDate.getDate() + iDay);
			document.cookie = name + '=' + value + ';expires=' + oDate + ';path=/';
		}
	}
});
globalLanguageApp.config(function($translateProvider) {
	$translateProvider.useStaticFilesLoader({
		files : [ {
			prefix : '/luda_glasses/i18n/',
			suffix : '.json'
		} ]
	});
	/*
	 * $translateProvider.registerAvailableLanguageKeys(['en', 'zh'], { 'en_US':
	 * 'en-US', 'en_UK': 'en-US', 'zh_CN': 'zh' });
	 */
	// set preferred lang
	// var lang = navigator.language||'en-US';
	// $translateProvider.preferredLanguage(lang);
	// or auto determine preferred lang
	// $translateProvider.determinePreferredLanguage();
	// when can not determine lang, choose en lang.
	$translateProvider.fallbackLanguage('en-US');
});
globalLanguageApp.run(function($rootScope, languages, $translate, cookieService) {
	$rootScope.languages = languages;
	
	$rootScope.setCurrentLanguage = function(language) {
		$rootScope.currentLanguage = language;
		$translate.use(language);
		cookieService.set("language", language);
	}
	// default language
	if (cookieService.get("language")) {
		$rootScope.setCurrentLanguage(cookieService.get("language"));
	} else {
		var language = isValueInArray(navigator.language, $rootScope.languages);
		if (!language) {
			language = $rootScope.languages[0].value;
		}
		$rootScope.setCurrentLanguage(language);
	}

	function isValueInArray(value, array) {
		for (var i = 0; i < array.length; i++) {
			if (value === array[i].value) {
				return value;
			}
		}
		return false;
	}
	$rootScope.getLanguageNameByValue = function(languageValue) {
		for (var i = 0; i < $rootScope.languages.length; i++) {
			if ($rootScope.languages[i].value === languageValue) {
				return $rootScope.languages[i].name;
			}
		}
	}
});
