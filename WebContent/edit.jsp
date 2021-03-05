<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Profile Editor</title>
		
		<style type="text/css">
			<%@ include file="css/styles.css" %>
		</style>
	</head>
	<body>
		<div>
			<a href="${pageContext.request.contextPath}/">Log Out</a>
			<a href="${pageContext.request.contextPath}/home">Back</a>
		</div>
		<div>
			<label>Username<input type="text" name="username" value="<c:out value="${product.username}" />" /></label>
			<label>Password<input type="text" name="password" value="<c:out value="${product.password}" />" /></label>
			<label>First Name<input type="text" name="firstName" value="<c:out value="${product.firstName}" />" /></label>
			<label>Last Name<input type="text" name="lastName" value="<c:out value="${product.lastName}" />" /></label>
			<label>High School<input type="text" name="highSchool" value="<c:out value="${product.highSchool}" />" /></label>
			<label>College<input type="text" name="college" value="<c:out value="${product.college}" />" /></label>
			<label>Town<input type="text" name="town" value="<c:out value="${product.town}" />" /></label>
			<label>Birthday<input type="text" name="birthday" value="<c:out value="${product.birthday}" />" /></label>
			<label>Email<input type="text" name="email" value="<c:out value="${product.email}" />" /></label>
			<label>Phone Number<input type="text" name="phone" value="<c:out value="${product.phone}" />" /></label>
			<label>Profile Image<input type="file" name="base64Image" /></label>
			<label>Current Profile Image<img src="data:image/jpg;base64,${profile.base64Image}" /></label>
		</div>
		<div>
			<a href="${pageContext.request.contextPath}/home?action=update">Save</a>
		</div>
	</body>
</html>