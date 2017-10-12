console.log("test");

angular.module("financialSeatManageApp").controller('relationshipController', function($scope, $http) {
	// $scope.isLoading = true;
	// $scope.membersLoadingEnd = false;
	// $scope.organizationsLoadingEnd = false;
	// $scope.partners = false;
	$scope.editMembers = false;
	$scope.editSupMembers = false;
	$scope.editPartners = false;
	$scope.editFund = false;
	$scope.today = new Date();
	$scope.newMember = {};
	$scope.editingIndex = -1;
	
	function finishLoading(){
		if($scope.membersLoadingEnd && $scope.organizationsLoadingEnd && $scope.partnersLoadingEnd){
			$scope.isLoading = false;
		}
	}
	
	// init
	getMembers();
	getPartners();
	getOrganizations();
	getFunds();
	
	function getMembers(){
		$http.get("/richmen/rest/enterprise/members").success(function(data) {
			$scope.members = data;
			// $scope.membersLoadingEnd = true;
			// finishLoading();
			$scope.members.sort(function(member1, member2){
				if (member1["function"] < member2["function"]){
					return -1;
				}else if(member1["function"] > member2["function"]){
					return 1;
				}else{
					if(member1["oppositSeatNumber"] < member2["oppositSeatNumber"]){
						return -1;
					}else if(member1["oppositSeatNumber"] > member2["oppositSeatNumber"]){
						return 1;
					}else{
						return 0;
					}
				}
			});
		}).error(function(data) {
			BootstrapDialog.show({
				type : BootstrapDialog.TYPE_DANGER,
				title : '警告',
				message : '获取企业席位成员失败！'
			});
		});
	}
	
	function getOrganizations(){
		$http.get("/richmen/rest/enterprise/organizations").success(function(data) {
			$scope.organizations = data;
			// $scope.organizationsLoadingEnd = true;
			// finishLoading();
		}).error(function() {
			BootstrapDialog.show({
				type : BootstrapDialog.TYPE_DANGER,
				title : '警告',
				message : '获取企业组织失败！'
			});
		});
	}
	function getPartners(){
		$http.get("/richmen/rest/enterprise/partners").success(function(data) {
			$scope.partners = data;
			// $scope.partnersLoadingEnd = true;
			// finishLoading();
		}).error(function() {
			BootstrapDialog.show({
				type : BootstrapDialog.TYPE_DANGER,
				title : '警告',
				message : '获取企业组织失败！'
			});
		});
	}
	function getFunds(){
		$http.get("/richmen/rest/financial/funds").success(function(data) {
			$scope.funds = data;
			// $scope.partnersLoadingEnd = true;
			// finishLoading();
		}).error(function() {
			BootstrapDialog.show({
				type : BootstrapDialog.TYPE_DANGER,
				title : '警告',
				message : '获取基金席位失败！'
			});
		});
	}

	$scope.changeMembers = function() {
		$scope.editMembers = true;
	}
	$scope.cancelMembers = function() {
		$scope.editMembers = false;
		$scope.editingIndex = -1;
		$scope.newMember = {};
	}
	$scope.changeSupMembers = function() {
		$scope.editSupMembers = true;
	}
	$scope.cancelSupMembers = function() {
		$scope.editSupMembers = false;
		$scope.newParent = {};
	}
	$scope.deleteOrganizationParent = function(bpNumber) {
		console.log(bpNumber);
		$http.delete("rest/enterprise/organizations/"+ bpNumber)
		.success(function(){
			/*
			 * for(var i = 0; i < $scope.organizations.partents.length; i++){ if
			 * (bpNumber ===
			 * $scope.organizations.partents[i].oppositSeatNumber){
			 * $scope.organizations.partents.splice(i, 1); return; } }
			 */
			getOrganizations();
			
		})
		.error(function(data){
			BootstrapDialog.show({
				type : BootstrapDialog.TYPE_DANGER,
				title : '警告',
				message : '删除失败，原因为' + data.errorMsg
			});
		});
	}
	$scope.addOrganizationParent = function(){
			$http.post("rest/enterprise/organizations/" + $scope.newParent.bpNumber)
			.success(function(){
				getOrganizations();
				$scope.newParent = {};
			})
			.error(function(data){
				BootstrapDialog.show({
					type : BootstrapDialog.TYPE_DANGER,
					title : '警告',
					message : '添加失败，原因为' + data.errorMsg
				});
			})
	}
	$scope.changePartners = function(){
		$scope.editPartners = true;
	}
	$scope.cancelEditPartners = function(){
		$scope.editPartners = false;
		$scope.newPartner = {};
		
	}
	$scope.clearName = function(payload){
		payload.bpName = "";
		if(payload.bpNumber && payload.bpNumber.length>10){
			payload.bpNumber = payload.bpNumber.substring(0, 10);
		}
	}
	$scope.getSeatName = function($event, payload) {

		if ($event.type === "keypress") {
			if ($event.keyCode !== 13) {
				return;
			}
		}
		if(payload){
			$http.get("register/seatName?seatNumber=" + payload.bpNumber).success(function(data) {
				payload.bpName = data.SEATNAME;
	
			}).error(function(data) {
				payload.bpName = "席位号不存在！";
			});
		}
	}
	
	$scope.deletePartner = function(bpNumber) {
		console.log(bpNumber);
		$http.delete("rest/enterprise/partners/"+ bpNumber)
		.success(function(){
			/*
			 * for(var i = 0; i < $scope.organizations.partents.length; i++){ if
			 * (bpNumber ===
			 * $scope.organizations.partents[i].oppositSeatNumber){
			 * $scope.organizations.partents.splice(i, 1); return; } }
			 */
			getPartners();
		})
		.error(function(data){
			BootstrapDialog.show({
				type : BootstrapDialog.TYPE_DANGER,
				title : '警告',
				message : '删除失败，原因为' + data.errorMsg
			});
		});
	}
	
	$scope.addPartner = function(){
			$http.post("rest/enterprise/partners/"+ $scope.newPartner.bpNumber)
			.success(function(){
				getPartners();
				$scope.newPartner = {};
				
			})
			.error(function(data){
				BootstrapDialog.show({
					type : BootstrapDialog.TYPE_DANGER,
					title : '警告',
					message : '添加失败，原因为' + data.errorMsg
				});
			});
	}
	
	$scope.editMember = function(index){
		$scope.editingIndex = index;
		$scope.newMember.bpNumber = $scope.members[index].oppositSeatNumber;
		$scope.newMember.bpName = $scope.members[index].oppositSeatName;
		$scope.newMember.role = $scope.members[index]["function"];
		$scope.newMember.telephone = $scope.members[index].telphoneNumber.split("-")[0];
		$scope.newMember.extension = $scope.members[index].telphoneNumber.split("-")[1];
		$scope.newMember.email = $scope.members[index].email;
	}
	$scope.changeMember = function(){
		if($scope.editingIndex === -1){
			// add
			$http.post("rest/enterprise/members", {
				seatNumber: $scope.newMember.bpNumber,
				roleCode: $scope.newMember.role,
				telephoneNo: $scope.newMember.telephone,
				telephoneExtension: $scope.newMember.extension,
				email: $scope.newMember.email
			})
			.success(function(){
				getMembers();
				$scope.newMember = {};
				$scope.editingIndex = -1;
			})
			.error(function(data){
				BootstrapDialog.show({
					type : BootstrapDialog.TYPE_DANGER,
					title : '警告',
					message : '添加失败，原因为' + data.errorMsg
				});
			});
		}else{
			// edit
			$http.put("rest/enterprise/members", {
				seatNumber: $scope.newMember.bpNumber,
				roleCode: $scope.newMember.role,
				telephoneNo: $scope.newMember.telephone,
				telephoneExtension: $scope.newMember.extension,
				email: $scope.newMember.email
			})
			.success(function(){
				getMembers();
				$scope.newMember = {};
				$scope.editingIndex = -1;
			})
			.error(function(data){
				BootstrapDialog.show({
					type : BootstrapDialog.TYPE_DANGER,
					title : '警告',
					message : '修改失败，原因为' + data.errorMsg
				});
			});
		}
	}
	$scope.cancelEditMember = function(index){
		$scope.editingIndex = -1;
		$scope.newMember = {};
	}
	
	$scope.deleteMember = function(bpNumber){
		console.log(bpNumber);
		$http.delete("rest/enterprise/members/"+ bpNumber)
		.success(function(){
			getMembers();
			$scope.editingIndex = -1;
		})
		.error(function(data){
			BootstrapDialog.show({
				type : BootstrapDialog.TYPE_DANGER,
				title : '警告',
				message : '删除失败，原因为' + data.errorMsg
			});
		});
	}
	$scope.changeFund = function(){
		$scope.editFund = true;
	}
	$scope.cancelFund = function(){
		$scope.editFund = false;
	}
	
	$scope.confirmFund = function(oppositeSeatNumber, beginDate, relationshipCategory){
		$http.put("/richmen/rest/financial/funds/confirm", {
			oppositeSeatNumber: oppositeSeatNumber,
			beginDate: beginDate,
			relationshipCategory: relationshipCategory
		}).success(function(){
			getFunds();
		}).error(function(data){
			BootstrapDialog.show({
				type : BootstrapDialog.TYPE_DANGER,
				title : '警告',
				message : '确认失败，原因为' + data.errorMsg
			});
		});
	}
});