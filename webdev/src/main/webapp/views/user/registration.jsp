<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<c:url var="home" value="/" scope="request" />
<link rel="stylesheet"
	href="<c:url value="/resources/core/css/bootstrap.min.css" />">

<link rel="stylesheet" href="<c:url value="/resources/css/app.css" />">

	<title>Web Coffee</title>
</head>
<body>

	<jsp:include page="../templates/header.jsp"></jsp:include>
	<div class="generic-container ">
		<div class="well lead" style="text-align: center;"><spring:message code="user.registration.form" /></div>
		<form:form method="POST" modelAttribute="user" class="form-horizontal" onsubmit="return validateForm()">
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="fullname"><spring:message code="user.registration.fullName" /></label>
					<div class="col-md-12">
						<form:input type="text" path="fullname" id="fullname" class="form-control input-sm" onblur="validatefullname()"/>
						<div class="has-error" id="err-fullname">
							<form:errors path="fullname" class="help-inline" />
						</div>
					</div>
				</div>
			</div>
			<div id="feedback"></div>
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="username"><spring:message code="user.registraion.loginName" /></label>
					<div class="col-md-12">
						<c:choose>
							<c:when test="${edit}">
								<form:input type="text" path="username" id="username"
									class="form-control input-sm" disabled="true" />
							</c:when>
							<c:otherwise>
								<form:input type="text" path="username" id="username"
									class="form-control input-sm" /><!-- onblur="validateusername()" -->
								<div class="has-error" id="err-username">
									<form:errors path="username" class="help-inline"/>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
			<c:choose>
				<c:when test="${!edit}">
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-3 control-lable" for="password"><spring:message code="user.registration.password" /></label>
							<div class="col-md-12">
								<form:input type="password" path="password" id="password"
									class="form-control input-sm" onblur="validatePassword()"/>
								<div class="has-error" id="err-password">
									<form:errors path="password" class="help-inline" />
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-3 control-lable"><spring:message code="user.registration.comfirm" /></label>
							<div class="col-md-12">
								<input type="password" id="comfirmPassword"	class="form-control input-sm" onblur="validateComfirm()"/>
								<div class="has-error" id="err-comfirm">
									
								</div>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>

				</c:otherwise>
			</c:choose>
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="email"><spring:message code="user.registration.email" /></label>
					<div class="col-md-12">
						<form:input type="text" path="email" id="email"
							class="form-control input-sm" onblur="validateEmail()"/>
						<div class="has-error" id="err-email">
							<form:errors path="email" class="help-inline" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="phone"><spring:message code="user.registration.phone" /></label>
					<div class="col-md-12">
						<form:input type="text" path="phone" id="phone"
							class="form-control input-sm" onblur="validatePhone()"/>
						<div class="has-error" id="err-phone">
							<form:errors path="phone" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<c:choose>
						<c:when test="${edit}">
							<sec:authorize access="hasRole('ADMIN')">
								<label class="col-md-3 control-lable" for="role"><spring:message code="user.registration.role" /> </label>
								<!-- : ${user.role.getName()} -->
								<!-- <spring:message code="${user.role.getName()}" /> -->
								<div class="col-md-12">
									<form:select path="role" class="form-control input-sm">
										<c:forEach items="${roles}" var="role">
											<c:choose>
												<c:when test="${role.getId() == user.role.getId()}">
													<form:option value="${role.getId()}" selected="true"><spring:message code="${role.getName()}" /></form:option>
												</c:when>
												<c:otherwise>
													<form:option value="${role.getId()}"><spring:message code="${role.getName()}" /></form:option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
									</form:select>
									<div class="has-error">
										<form:errors path="role" class="help-inline" />
									</div>
								</div>
							</sec:authorize>
							<sec:authorize access="hasAnyRole('STAFF','CUSTOMER')">
								<form:input type="hidden" path="role" id="role"
									class="form-control input-sm" value="${user.role.getId()}" />
							</sec:authorize>
						</c:when>
						<c:otherwise>
							<sec:authorize access="hasRole('ADMIN')">
								<label class="col-md-3 control-lable" for="role"><spring:message code="user.registration.role" /> </label>
								<div class="col-md-12">
									<form:select path="role" class="form-control input-sm">
										<c:forEach items="${roles}" var="role">
												<form:option value="${role.getId()}"><spring:message code="${role.getName()}" /></form:option>
										</c:forEach>
									</form:select>
									<div class="has-error">
										<form:errors path="role" class="help-inline" />
									</div>
								</div>
							</sec:authorize>
							<sec:authorize access="isAnonymous()">
								<form:input type="hidden" path="role" id="role"
									class="form-control input-sm" value="2" />
							</sec:authorize>
						</c:otherwise>
					</c:choose>
				</div>
			</div>

			<div class="row">
				<div class="form-actions center">
					<c:choose>
						<c:when test="${edit}">
							<input type="submit" value="<spring:message code="user.registration.update" />"
								class="btn btn-primary btn-sm"/> <spring:message code="user.registration.or" /> 
							<sec:authorize access="hasRole('ADMIN')">
								<a href="<c:url value='../admin/list' />"
									class="btn btn-primary btn-sm"><spring:message code="user.registration.cancel" /></a>
							</sec:authorize>
							<sec:authorize access="hasAnyRole('STAFF','CUSTOMER')">
								<a href="<c:url value='../home' />" class="btn btn-primary btn-sm"><spring:message code="user.registration.cancel" /></a>
							</sec:authorize>
						</c:when>
						<c:otherwise>
							<input type="submit" value="<spring:message code="user.registration.register" />"
								class="btn btn-primary btn-sm" id = "btn-submit"/> <spring:message code="user.registration.or" />
								<sec:authorize access="hasRole('ADMIN')">
								<a href="<c:url value='../admin/list' />"
									class="btn btn-primary btn-sm"><spring:message code="user.registration.cancel" /></a>
								</sec:authorize>
							<sec:authorize access="isAnonymous()">
								<a href="<c:url value='../home' />" class="btn btn-primary btn-sm"><spring:message code="user.registration.cancel" /></a>
							</sec:authorize>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</form:form>
		<!-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="csrfToken" /> -->
		<!-- ?${_csrf.parameterName}=${_csrf.token} -->
	<!-- ${_csrf.parameterName}-->
	<!-- ${_csrf.headerName}-->
	</div>
</body>

<script type="text/javascript"
	src="<c:url value="/resources/core/js/bootstrap.min.js" />"></script>

<script type="text/javascript"
	src="<c:url value="/resources/js/app.js" />"></script>

<script type="text/javascript"
	src="<c:url value="/resources/js/validate.js" />"></script>
	
</html>