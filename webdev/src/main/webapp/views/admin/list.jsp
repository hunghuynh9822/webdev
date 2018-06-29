<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	isELIgnored="false" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet"
	href="<c:url value="/resources/core/css/bootstrap.min.css" />">

<link rel="stylesheet" href="<c:url value="/resources/css/app.css" />">
</head>
<body>
	<jsp:include page="../templates/header.jsp"></jsp:include>
	<div class="generic-container">

		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<span class="lead"><spring:message code="admin.list.list" /></span>
			</div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th><spring:message code="admin.list.name" /></th>
						<th><spring:message code="admin.list.email" /></th>
						<th><spring:message code="admin.list.phone" /></th>
						<th><spring:message code="admin.list.loginName" /></th>
						<th><spring:message code="admin.list.role" /></th>
						<sec:authorize access="hasRole('ADMIN')">
							<th width="100"></th>
						</sec:authorize>
						<sec:authorize access="hasRole('ADMIN')">
							<th width="100"></th>
						</sec:authorize>

					</tr>
				</thead>
				<tbody>
					<c:forEach items="${users}" var="user">
						<tr>
							<td>${user.fullname}</td>
							<td>${user.email}</td>
							<td>${user.phone}</td>
							<td>${user.username}</td>
							<td><spring:message code="${user.role.getName()}" /></td>
							<sec:authorize access="hasRole('ADMIN')">
								<td><a
									href="<c:url value='../user/edit-user-${user.username}' />"
									class="btn btn-success custom-width"><spring:message
											code="admin.list.edit" /></a></td>
							</sec:authorize>
							<sec:authorize access="hasRole('ADMIN')">
								<td><a
									href="<c:url value='../user/delete-user-${user.username}' />"
									class="btn btn-danger custom-width"><spring:message
											code="admin.list.delete" /></a></td>
							</sec:authorize>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="well">
			<a href="<c:url value='../user/newuser' />"><spring:message
					code="admin.list.addNew" /></a> <span class="well floatRight"><a
				href="<c:url value='../home' />"><spring:message
						code="admin.list.goHome" /></a> </span>
		</div>
	</div>
</body>

<script type="text/javascript"
	src="<c:url value="/resources/core/js/bootstrap.min.js" />"></script>

<script type="text/javascript"
	src="<c:url value="/resources/core/js/jquery-3.3.1.min.js" />"></script>

<script type="text/javascript"
	src="<c:url value="/resources/js/app.js" />"></script>

<script type="text/javascript"
	src="<c:url value="/resources/js/validate.js" />"></script>

</html>