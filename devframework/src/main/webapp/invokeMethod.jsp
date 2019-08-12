<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:_Layout>

	<jsp:attribute name="css_custom">
		<link rel="stylesheet" type="text/css" href="webjars/chartjs/2.8.0/Chart.min.css">
	</jsp:attribute>
	
	<jsp:attribute name="content">
		<main role="main">
			
			<nav aria-label="breadcrumb">
				<ol class="breadcrumb">
			    	<li class="breadcrumb-item">
			    		<a href="./">Classes</a>
			    		<i class="fas fa-angle-right mx-2" aria-hidden="true"></i>
			    		
			    	</li>
			    	<li class="breadcrumb-item active" aria-current="page">
			    		<a href="methods.jsp" id="breadcrumbClassName"/></a>
			    		<i class="fas fa-angle-right mx-2" aria-hidden="true"></i>
			    	</li>
			    	<li class="breadcrumb-item active" aria-current="page" id="breadcrumbMethodName"/>
				</ol>
			</nav>

			<section class="jumbotron text-center">
				<div class="container">
					<h1 class="jumbotron-heading">Serviço: <strong id="headerMethodName"></strong> do Módulo: <strong id="headerClassName"></strong> </h1>
				</div>
			</section>
			<div class="clearfix my-1">&nbsp;</div>
			<div class="container">
				<div class="row">
					<div class="col-md-5">
						<div class="card">
	  						<div class="card-header">
								Descrição do Serviço
							</div>
							<div class="card-body">
								<strong>Classe: </strong><span id="InvokeClassName"></span>
								<br/><strong>Descrição da Classe: </strong><span id="InvokeClassDescription"></span>
								<br/><br/>
								<strong>Método: </strong><span id="InvokeMethodName"></span>
								<br/><strong>Descrição do Método: </strong><span id="InvokeMethodDescription"></span>
	  						</div>
						</div>	
					</div>
					<div class="col-md-7">
						<div class="card">
	  						<div class="card-header">
								Simular a execução do método <strong>"Cadastrar tarefa"</strong>
							</div>
							<div class="card-body">
								<form id="formParam" data-url="invokeMethod.op"></form>
	  						</div>
						</div>
					</div>
				</div>
				<div class="clearfix my-1">&nbsp;</div>
				<div class="row">
					<div class="col-md-12">
						<div class="card" id="tabResult">
	  						<div class="card-header">
								Resultado
							</div>
							<div class="card-body">
								<div id="result"></div>
	  						</div>
						</div>
					</div>
				</div>
				<div class="clearfix my-2">&nbsp;</div>
			</div>
			<div>
				
			</div>
	
		</main>
		
	</jsp:attribute>
	
	<jsp:attribute name="js_custom">
		<!-- load chartjs library -->
		<script type="text/javascript" src="webjars/chartjs/2.8.0/Chart.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/app/InvokeMethod.js"></script>
	</jsp:attribute>
</tags:_Layout>