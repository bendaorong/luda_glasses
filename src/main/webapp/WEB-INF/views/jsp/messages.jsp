
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:if test="${errorMessage ne null }">
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">×</span>
		</button>
		<p>${errorMessage}</p>
	</div>
</c:if>
<c:if test="${succeessMessage ne null }">
	<div class="alert alert-success alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">×</span>
		</button>
		<p>${succeessMessage}</p>
	</div>
</c:if>