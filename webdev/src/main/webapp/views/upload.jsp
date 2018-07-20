<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form:form method="post" action="upload?${_csrf.parameterName}=${_csrf.token}" modelAttribute="uploadForm" enctype="multipart/form-data">
      <table id="fileTable">
           <tr>
                <td>Chọn hình ảnh: </td><td><input name="fileUpload" type="file" /></td>
           </tr>
           <tr>
                <td colspan="2"><input type="submit" value="Tải dữ liệu lên máy chủ..." /></td>
           </tr>
     </table>
</form:form>
</body>
</html>