<!DOCTYPE html>
<%@tag description="DevFramework" pageEncoding="UTF-8"%>

<%@attribute name="css_custom" fragment="true" %>
<%@attribute name="content" fragment="true" %>
<%@attribute name="js_custom" fragment="true" %>
 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>DevFramework</title>
		<link href="webjars/bootstrap/4.3.1/css/bootstrap.css" rel="stylesheet" type="text/css">
		<link href="webjars/datatables/1.10.19/css/dataTables.bootstrap4.min.css" rel="stylesheet" type="text/css">
		<link href="webjars/font-awesome/5.9.0/css/all.min.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/resources/css/app.css" rel="stylesheet" type="text/css">
     	<jsp:invoke fragment="css_custom"/>
 	</head>
	<body>
	
		<header>
			<div class="collapse bg-dark" id="navbarHeader">
				<div class="container">
					<div class="row">
						<div class="col-sm-8 col-md-7 py-4">
							<h4 class="text-white">Sobre</h4>
							<p class="text-muted">Framework
								desenvolvido pela turma do segundo período de 2019 da disciplina
								CAP-385-3 - Desenvolvimento de Frameworks do curso Computação
								Aplicada - CAP do INPE.</p>
						</div>
					</div>
				</div>
			</div>
			<div class="navbar navbar-dark bg-dark box-shadow">
				<div class="container d-flex justify-content-between">
					<a href="#" class="navbar-brand d-flex align-items-center"> <strong>DevFramework</strong>
					</a>
					<button class="navbar-toggler" type="button" data-toggle="collapse"
						data-target="#navbarHeader" aria-controls="navbarHeader"
						aria-expanded="false" aria-label="Toggle navigation">
						<span class="navbar-toggler-icon"></span>
					</button>
				</div>
			</div>
		</header>
	
    	<jsp:invoke fragment="content"/>
    	
    <!-- load jQuery library -->
	<script type="text/javascript" src="webjars/jquery/3.3.1/jquery.min.js"></script>
	<!-- load Bootstrap library -->
	<script type="text/javascript"
		src="webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
	<!-- load dataTables library -->
	<script type="text/javascript" src="webjars/datatables/1.10.19/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="webjars/datatables/1.10.19/js/dataTables.bootstrap4.min.js"></script>
	<!-- load maskedinput library -->
	<script type="text/javascript" src="webjars/jquery-maskedinput/1.4.0/jquery.maskedinput.min.js"></script>
	<!-- load underscore library -->
	<script type="text/javascript" src="webjars/underscorejs/1.8.3/underscore-min.js"></script>
	<!-- load jsonform library -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jsonform.js"></script>
	<!-- load font-awesome library -->
	<script type="text/javascript" src="webjars/font-awesome/5.9.0/js/all.min.js"></script>
	<!-- load alert.bootstrap library -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/alert.bootstrap.js"></script>
	<!-- load javascript.app -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/app/Initialize.js"></script>
 	
 	<jsp:invoke fragment="js_custom"/>
    
 	</body>
</html>