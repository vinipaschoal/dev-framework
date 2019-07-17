<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:_Layout>

	<jsp:attribute name="css_custom">
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
			    		<a href="./">${ param['clazz'] }</a>
			    		<i class="fas fa-angle-right mx-2" aria-hidden="true"></i>
			    	</li>
			    	<li class="breadcrumb-item active" aria-current="page">
			    		${ param['method'] }
			    	</li>
				</ol>
			</nav>

			<section class="jumbotron text-center">
				<div class="container">
					<h1 class="jumbotron-heading">Métodos da Classe: <strong>${ param['clazz'] }</strong> </h1>
				</div>
			</section>
	
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<form></form>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						&nbsp;<br />&nbsp;<br />
					</div>
				</div>
			</div>
	
		</main>
		
		
		
	</jsp:attribute>
	
	<jsp:attribute name="js_custom">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/app/InvokeMethod.js"></script>
	</jsp:attribute>
</tags:_Layout>