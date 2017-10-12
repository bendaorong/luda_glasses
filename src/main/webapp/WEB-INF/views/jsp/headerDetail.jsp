<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<li class="dropdown"><a id="enterprise_seat_Name"
	href="javascript:void(0)" class="dropdown-toggle"
	data-toggle="dropdown"><b
		class="caret"></b>
</a> 
<ul class="dropdown-menu user-menu" aria-labelledby="dLabel">
	<li>{{'ACCOUNT'|translate}} :</li>
	<li><strong>${sessionScope.sessionInfo.commonSeatNumber}</strong></li>
	<li>{{'SEAT_TYPE'|translate}} :</li>
	<li><strong>{{'SEAT_TYPE-${sessionScope.seatType}'|translate}}</strong></li>
	<li>{{'PERSONAL_NUMBER'|translate}} :</li>
	<li><strong>${sessionScope.sessionInfo.ownSeatNumber}</strong></li>
	<li>{{'ROLE'|translate}} :</li>
	<li><strong>{{'ROLE-${sessionScope.sessionInfo.roleCode}'|translate}}</strong></li>
		<li>{{'LOGIN_TIME'|translate}} :</li>
		<li>{{'LAST_LOGIN'|translate}} :</li>
		<li><a href="logout">{{'LOGOUT'|translate}}</a></li>
	</ul>
</li>
