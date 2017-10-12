var loginApp = angular.module("LoginApp", []);
loginApp.controller("LoginController", function($scope, $timeout) {
	var oConfig = {
		iCountDown : 60,
	};

	init();

	$scope.getDynamicNumber = function() {
		var $phone_number = $("#phone_number");

		if ($phone_number.val() === "") {
			var $phone_number = $("#phone_number");
			$phone_number.focus();
			$("#phone_form_group").addClass("has-error");
		} else {
			var $button = $("#btn_get_phone_dynamic_number");
			$button.attr("disabled", true);
			var sBtnText = $button.text();
			var iCountDown = oConfig.iCountDown;
			$button.text(sBtnText + "(" + iCountDown + ")");
			var iIntervalId = setInterval(function() {
				if (iCountDown-- > 0) {
					$button.text(sBtnText + "(" + iCountDown + ")");
				} else {
					$button.attr("disabled", false);
					$button.text(sBtnText);
					clearInterval(iIntervalId);
				}
			}, 1000);
		}
	}

	function init() {
		$scope.phone_country_number = 86;
	}

});