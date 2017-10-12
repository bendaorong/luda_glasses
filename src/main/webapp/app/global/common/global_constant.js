/**
 * 
 */
var globalConstantApp = angular.module("globalConstantApp", []);
globalConstantApp.value("countries", [ 'CN', 'US', 'HK', 'TW', 'DE' ]);
globalConstantApp.value("navTabs", [ 'HOME', 'SEAT_MANAGE', 'BALANCE_SHEET_MANAGE', 'BUSINESS_OPERATION', 'IMPORTANT_NOTIFICATION' ]);
globalConstantApp.value("languages", [ {
	"name" : "English",
	"value" : "en-US"
}, {
	"name" : "简体中文",
	"value" : "zh-CN"
}, {
	"name" : "繁體中文",
	"value" : "zh-HK"
} ]);
globalConstantApp.value("currency", [ {
	"name" : "CNY",
	"value" : "CNY"
}, {
	"name" : "USD",
	"value" : "USD"
}, {
	"name" : "EUR",
	"value" : "EUR"
}, {
	"name" : "HKD",
	"value" : "HKD"
}, {
	"name" : "JPY",
	"value" : "JPY"
} ]);
globalConstantApp.value("businessOperationInvestmentDirection", [ {
	"name" : "Z01",
	"value" : "Z01"
}, {
	"name" : "Z02",
	"value" : "Z02"
}, {
	"name" : "Z03",
	"value" : "Z03"
}, {
	"name" : "Z04",
	"value" : "Z04"
}, {
	"name" : "Z05",
	"value" : "Z05"
}, {
	"name" : "Z06",
	"value" : "Z06"
}, {
	"name" : "Z07",
	"value" : "Z07"
}, {
	"name" : "Z08",
	"value" : "Z08"
}, {
	"name" : "Z09",
	"value" : "Z09"
} ]);
globalConstantApp.value("businessOperationSuspendPrimarymarket", [ {
	"name" : "S01",
	"value" : "S01"
}, {
	"name" : "S81",
	"value" : "S81"
} ]);
globalConstantApp.value("businessOperationSuspendSecondarymarket", [ {
	"name" : "S01",
	"value" : "S01"
}, {
	"name" : "S81",
	"value" : "S81"
} ]);
globalConstantApp.value("businessOperationListPrimarymarket", [ {
	"name" : "OO",
	"value" : "OO"
}, {
	"name" : "O1",
	"value" : "O1"
}, {
	"name" : "L8",
	"value" : "L8"
}, {
	"name" : "L0",
	"value" : "L0"
} ]);
globalConstantApp.value("businessOperationListSecondarymarket", [ {
	"name" : "OO",
	"value" : "OO"
}, {
	"name" : "O1",
	"value" : "O1"
}, {
	"name" : "L8",
	"value" : "L8"
}, {
	"name" : "L0",
	"value" : "L0"
} ]);
globalConstantApp.value("businessOperationSkip", [ {
	"name" : "Z0",
	"value" : "Z0"
}, {
	"name" : "Z1",
	"value" : "Z1"
} ]);
globalConstantApp.value("businessOperationCapitalRepayment", [ {
	"name" : "1",
	"value" : "1"
}, {
	"name" : "2",
	"value" : "2"
}, {
	"name" : "3",
	"value" : "3"
} ]);
globalConstantApp.value("businessOperationYieldRepayment", [ {
	"name" : "2",
	"value" : "2"
}, {
	"name" : "1",
	"value" : "1"
} ]);
globalConstantApp.value("businessOperationFrequenceUnit", [ {
	"name" : "D",
	"value" : "D"
}, {
	"name" : "M",
	"value" : "M"
} ]);
globalConstantApp.value("businessOperationAllowEarlyRedemption", [ {
	"name" : "Y",
	"value" : "Y"
}, {
	"name" : "N",
	"value" : "N"
} ]);
globalConstantApp.value("businessOperationRedemptionYieldCalculation", [ {
	"name" : "Z0",
	"value" : "Z0"
}, {
	"name" : "Z1",
	"value" : "Z1"
} ]);
globalConstantApp.value("seatTypes", [ {
	"name" : "Z001",
	"value" : "Z001"
}, {
	"name" : "Z002",
	"value" : "Z002"
}, {
	"name" : "Z003",
	"value" : "Z003"
}, {
	"name" : "Z004",
	"value" : "Z004"
}, {
	"name" : "Z005",
	"value" : "Z005"
} ]);
globalConstantApp.value("termUnit",[{
	"name": "Y",
	"value": "Y"
},{
	"name": "M",
	"value": "M"
},{
	"name": "D",
	"value": "D"
}]);
globalConstantApp.value("businessOperationYieldGuarrented",[{
	"name": "Z1",
	"value": "Z1"
},{
	"name": "Z2",
	"value": "Z2"
},{
	"name": "Z3",
	"value": "Z3"
}]);
globalConstantApp.value("businessOperationYieldPaymentFund",[{
	"name": "Z3",
	"value": "Z3"
},{
	"name": "Z2",
	"value": "Z2"
},{
	"name": "Z1",
	"value": "Z1"
}]);
globalConstantApp.value("businessOperationYieldType", [{
	"name": "Z1",
	"value": "Z1"
}, {
	"name": "Z2",
	"value": "Z2"
}]);
globalConstantApp.value("businessOperationPurchasePriceType", [{
	"name": "Z1",
	"value": "Z1"
},{
	"name": "Z2",
	"value": "Z2"
},{
	"name": "Z3",
	"value": "Z3"
},{
	"name": "Z4",
	"value": "Z4"
}]);
globalConstantApp.value("businessOperationRedemptionPriceType",[{
	"name": "Z1",
	"value": "Z1"
},{
	"name": "Z2",
	"value": "Z2"
},{
	"name": "Z3",
	"value": "Z3"
},{
	"name": "Z4",
	"value": "Z4"
}]);
globalConstantApp.value("businessOperationARResourceMode",[{
	"name": "Z0",
	"value": "Z0"
},{
	"name": "Z1",
	"value": "Z1"
}]);