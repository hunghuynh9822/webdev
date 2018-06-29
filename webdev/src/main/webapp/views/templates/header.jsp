<%@page import="com.home.webdev.util.SecurityUtil"%>
<%@page import="com.home.webdev.service.UserService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="header">
	<sec:authorize access="isAnonymous()">
		<span><spring:message code="header.welcome" /></span>
		<span class="floatRight"><a href="<c:url value="/login" />"><spring:message
					code="header.login" /></a></span>
	</sec:authorize>
	<sec:authorize access="isAuthenticated()">
		<!-- <%=SecurityUtil.getPrincipal()%> -->
		<span><spring:message code="header.dear" /> <strong>${user.getFullname()}</strong>,
			<spring:message code="header.welcome" /></span>
		<span class="floatRight"><a href="<c:url value="/logout" />"><spring:message
					code="header.logout" /></a></span>
	</sec:authorize>
	<%
	String url = (String)request.getAttribute("javax.servlet.forward.request_uri");
	String currentPage = url.replace("/webdev/vn", "").replace("/webdev/en", "").replace("/webdev", "");
	
	%> 
	<!-- <%=currentPage %> sau khi đã replace -->
	<div style="text-align: right; padding: 5px; margin: 5px 0px;">
		<a href="<%= request.getContextPath() %>/en<%=currentPage.toString() %>"><spring:message code="header.english" /></a> &nbsp;&nbsp; <a href="<%= request.getContextPath() %>/vn<%=currentPage.toString() %>"><spring:message code="header.vietnamese" /></a>
	</div>
	
	<!-- ${requestScope['javax.servlet.forward.request_uri']} get từ request_uri-->
</div>