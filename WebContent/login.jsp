<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Login</title>
		
		<style type="text/css">
			<%@ include file="css/styles.css" %>
		</style>
	</head>
	<body>
		<div>
			<h1>Login | Sign Up</h1>
		</div>
		<div>
			<table>
				<tr>
					<th>Username: </th>
					<td><input type="text" name="enterName" /></td>
				</tr>
				<tr>
					<th>Password: </th>
					<td><input type="text" name="enterPass" /></td>
				</tr>
				<tr>
					<td>
						<div>
							<a href="${pageContext.request.contextPath}/home?action=login">Login</a>
							<a href="${pageContext.request.contextPath}/signup">Sign Up</a>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>