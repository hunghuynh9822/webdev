
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Insert title here</title>

<c:url var="home" value="/" scope="request" />

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css"
	var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />


</head>
<body>
<nav class="navbar navbar-inverse">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">Spring 4 MVC Ajax Hello World</a>
		</div>
	</div>
</nav>

<div class="container" style="min-height: 500px">

	<div class="starter-template">
		<h1>Search Form</h1>
		<br>

		<div id="feedback"></div>

		<form class="form-horizontal" id="search-form">
			<div class="form-group form-group-lg">
				<label class="col-sm-2 control-label">Username</label>
				<div class="col-sm-10">
					<input type=text class="form-control" id="username">
				</div>
			</div>
			<div class="form-group form-group-lg">
				<label class="col-sm-2 control-label">Email</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="email">
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" id="bth-search"
						class="btn btn-primary btn-lg">Search</button>
				</div>
			</div>
		</form>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="csrfToken" />
		<!-- ?${_csrf.parameterName}=${_csrf.token} -->
	<!-- ${_csrf.parameterName}-->
	<!-- ${_csrf.headerName}-->
	</div>

</div>

<div class="container">
	<footer>
		<p>
			© <a href="http://www.mkyong.com">Mkyong.com</a> 2015
		</p>
	</footer>
</div>
<script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<script>
jQuery(document).ready(function() {

		$("#search-form").submit(function(event) {


			// Prevent the form from submitting via the browser.
			event.preventDefault();

			searchViaAjax();

		});

	});

	function searchViaAjax() {
		var token = $('#csrfToken').val();
		var header = $("meta[name='_csrf_header']").attr("content");
		
		var search = {}
		search["username"] = $("#username").val();
		search["email"] = $("#email").val();

		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "${home}search/api/getSearchResult",
			data : JSON.stringify(search),
			dataType : 'json',
			timeout : 100000,
			beforeSend : function(xhr) {
				xhr.setRequestHeader("accept", "application/json");
				xhr.setRequestHeader("contentType", "application/json");
				xhr.setRequestHeader(header, token);
			},
			
			success : function(data) {
				console.log("SUCCESS: ", data);
				display(data);
			},
			error : function(e) {
				console.log("ERROR: ", e);
				display(e);
			},
			done : function(e) {
				console.log("DONE");
				enableSearchButton(true);
			}
		});

	}
	
	function display(data) {
		var json = "<h4>Ajax Response</h4><pre>"
				+ JSON.stringify(data, null, 4) + "</pre>";
		$('#feedback').html(json);
	}
</script>

</body>

</html>