<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Sign Up</title>
		
		<style type="text/css">
			<%@ include file="css/styles.css" %>
		</style>
	</head>
	<body>
		<div>
			<h1>Sign Up</h1>
			<h2>* = required</h2>
		</div>
		<div>
		<table>
				<tr>
					<th>*Username: </th>
					<td><input type="text" name="username" /></td>
				</tr>
				<tr>
					<th>*Password: </th>
					<td><input type="text" name="password" /></td>
				</tr>
				<tr>
					<th>*First Name: </th>
					<td><input type="text" name="firstName" /></td>
				</tr>
				<tr>
					<th>*Last Name: </th>
					<td><input type="text" name="lastName" /></td>
				</tr>
				<tr>
					<th>High School: </th>
					<td><input type="text" name="highSchool" /></td>
				</tr>
					<tr>
					<th>College: </th>
					<td><input type="text" name="college" /></td>
				</tr>
				<tr>
					<th>Town: </th>
					<td><input type="text" name="town" /></td>
				</tr>
				<tr>
					<th>Birthday: </th>
					<td><input type="text" name="birthday" /></td>
				</tr>
				<tr>
					<th>Email: </th>
					<td><input type="text" name="email" /></td>
				</tr>
				<tr>
					<th>Phone Number: </th>
					<td><input type="text" name="phone" /></td>
				</tr>
				<tr>
					<th>Profile Image: </th>
					<td><input type="file" name="base64Image" /></td>
				</tr>
				<tr>
					<td>
						<div>
							<a href="${pageContext.request.contextPath}/home?action=signup">Submit</a>
							<a href="${pageContext.request.contextPath}/">Cancel</a>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>