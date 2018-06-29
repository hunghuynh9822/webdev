<%@page import="com.home.webdev.util.SecurityUtil"%>
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
	<c:url var="userUrl" value="./admin/list" />
	<jsp:include page="./templates/header.jsp">
		<jsp:param value="${user}" name="user" />
	</jsp:include>
	<div class="page">
		<h1><spring:message code="home.header" /></h1>

		<sec:authorize access="isAuthenticated()">
			<sec:authorize access="hasRole('ADMIN')">
				<a href="${userUrl}"><spring:message code="home.show.listuser" /></a>
			</sec:authorize>
			<!-- <%=SecurityUtil.getPrincipal()%>-->
			<td><a
				href="<c:url value='./user/edit-user-${SecurityUtil.getPrincipal()}' />"><spring:message code="home.edit" /></a></td>
		</sec:authorize>
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