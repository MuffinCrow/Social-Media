<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Home</title>
		
		<style type="text/css">
			<%@ include file="css/styles.css" %>
		</style>
	</head>
	<body>
		<div>
			<a href="${pageContext.request.contextPath}/">Log Out</a>
			<a href="${pageContext.request.contextPath}/edit">Edit Profile</a>
		</div>
		<div>
			<table>
				<c:forEach var="profile" items="${profiles}">
					<tr>
						<td><c:out value="${profile.getFirstName}" /> <c:out value="${profile.getLastName}" /></td>
						<td>
							<div>
								<a href="${pageContext.request.contextPath}/profile?username=${profile.getUsername}">Visit</a>
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>