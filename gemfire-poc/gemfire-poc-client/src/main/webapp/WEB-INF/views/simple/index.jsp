<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Simple Entities</h1>
	<h2>Create a Simple entry</h2>
	<c:url value="/simple" var="urlBase"/>
	<form:form modelAttribute="simple" method="POST" action="${urlBase}">
		<table>
			<tr>
				<td><form:label path="id">Id:</form:label></td>
				<td><form:input path="id" /></td>
			</tr>

			<tr>
				<td><form:label path="value">Value:</form:label></td>
				<td><form:input path="value" /></td>
			</tr>
		</table>
		<input type="submit" value="Save" />
		<c:if test="${not empty message}"><br>
		<span style="color: red">${message}</span>
		</c:if>
	</form:form>
	<h2>Lookup a Simple entry</h2>
	<form name="queryForm">
		<table>
			<tr>
				<th>Id:</th>
				<td><input type="text" name="id" />
				</td>
			</tr>
		</table>
		<input type="button" value="Find" onClick="document.location='${urlBase}/' + queryForm.id.value"/>
		<c:if test="${not empty foundSimple}"><br>
	Found Simple Entity:<br>
			<table>
				<tr>
					<th>ID</th>
					<th>Value</th>
				</tr>
				<tr>
					<td>${foundSimple.id}</td>
					<td>${foundSimple.value}</td>
				</tr>
			</table>
		</c:if>
	</form>
</body>
</html>
