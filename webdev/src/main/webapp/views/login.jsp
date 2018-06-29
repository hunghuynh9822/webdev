<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<div id="mainWrapper">
		<div class="login-container">
			<div class="login-card">
				<div class="login-form">
					<c:url var="loginUrl" value="/login" />
					<form action="${loginUrl}" method="post" class="form-horizontal">
						<c:if test="${param.error != null}">
							<div class="alert alert-danger">
								<p><spring:message code="login.invalid" /></p>
							</div>
						</c:if>
						<c:if test="${param.logout != null}">
							<div class="alert alert-success">
								<p><spring:message code="login.logout" /></p>
							</div>
						</c:if>
						<div class="input-group input-sm">
							<label class="input-group-addon" for="username"><i
								class="fa fa-user"></i></label> <input type="text" class="form-control"
								id="username" name="username" placeholder="<spring:message code="login.enter.username" />"	required>
						</div>
						<div class="input-group input-sm">
							<label class="input-group-addon" for="password"><i
								class="fa fa-lock"></i></label> <input type="password"
								class="form-control" id="password" name="password"
								placeholder="<spring:message code="login.enter.password" />" required>
						</div>
						<div class="input-group input-sm">
							<div class="checkbox">
								<label><input type="checkbox" id="rememberme"
									name="remember-me"> <spring:message code="login.check.remember" /></label>
							</div>
						</div>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />

						<div class="form-actions">
							<input type="submit"
								class="btn btn-block btn-primary btn-default" value="<spring:message code="login.button.login" />">
						</div>
						<div class="well">
							<a href="<c:url value='./user/newuser' />"><spring:message code="login.registrantion" /></a> <span
								class="well floatRight"> <a
								href="<c:url value='./home' />"><spring:message code="login.home" /></a>
							</span>
						</div>

					</form>
				</div>
			</div>
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