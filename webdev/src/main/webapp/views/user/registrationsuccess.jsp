<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet"
	href="<c:url value="/resources/core/css/bootstrap.min.css" />">

<link rel="stylesheet" href="<c:url value="/resources/css/app.css" />">
</head>
<body>
	<!--<jsp:include page="../templates/header.jsp"></jsp:include>-->
	<div class="generic-container ">
	<c:choose>
	<c:when test="${edit}">
		<div class="alert alert-success lead"><spring:message code="user.registrationsuccess.user" /> ${success} <spring:message code="user.registrationsuccess.message.update" /></div>
	</c:when>
	<c:otherwise>
		<div class="alert alert-success lead"><spring:message code="user.registrationsuccess.user" /> ${success} <spring:message code="user.registrationsuccess.message" /></div>
	</c:otherwise>
	</c:choose>
		<sec:authorize access="hasRole('ADMIN')">
			<span class="well floatRight"> <spring:message code="user.registrationsuccess.goto" /> <a
				href="<c:url value='./admin/list' />"><spring:message code="user.registrationsuccess.list" /></a>
			</span>
		</sec:authorize>

		<span class="well floatLeft"> <spring:message code="user.registrationsuccess.goto" /> <a
			href="<c:url value='./home' />"><spring:message code="user.registrationsuccess.home" /></a>
		</span>
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