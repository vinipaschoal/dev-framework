<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CAP 385-3 - Dev Framework 2019</title>
</head>
<body>
	<h2>Dev Framework - Lista de classes:</h2>
		
	<c:forEach items="${classes}" var="classe">
		- <a href="opListaMetodos?classe=${classe}">${classe}</a><br/>
	</c:forEach>
	
	<p>
	<a href="index.jsp">Voltar</a>
	
</body>
</html>