<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Profile</title>
		
		<style type="text/css">
			<%@ include file="css/styles.css" %>
		</style>
	</head>
	<body>
		<div>
			<a href="${pageContext.request.contextPath}/">Log Out</a>
			<a href="${pageContext.request.contextPath}/home">Back</a>
		</div>
		<table>
			<tr>
				<td><c:out value="${profile.firstName}" /> <c:out value="${profile.lastName}" /></td>
			</tr>
			<tr>
				<c:if test="${profile.base64Image == null}">
					<img src="data:image/jpeg;base64,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">
				</c:if>
				<c:if test="${profile.base64Image != null}">
					<img src="data:image/jpg;base64,${profile.base64Image}" />
				</c:if>
			</tr>
			<tr>
				<td>Last Edit: <c:out value="${profile.getLastEdit}" /></td>
			</tr>
			<tr>
				<td>Last Login: <c:out value="${profile.getLastLogin}" /></td>
			</tr>
			<tr>
				<td>Username: <c:out value="${profile.username}" /></td>
			</tr>
			<tr>
				<td>Email: <c:out value="${profile.email}" /></td>
			</tr>
			<tr>
				<td>Phone Number: <c:out value="${profile.phone}" /></td>
			</tr>
			<tr>
				<td>Birthday: <c:out value="${profile.birthday}" /></td>
			</tr>
			<tr>
				<td>Town: <c:out value="${profile.town}" /></td>
			</tr>
			<tr>
				<td>High School: <c:out value="${profile.highSchool}" /></td>
			</tr>
			<tr>
				<td>College: <c:out value="${profile.college}" /></td>
			</tr>
		</table>
	</body>
</html>