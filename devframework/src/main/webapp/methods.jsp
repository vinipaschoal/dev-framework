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
			    	<li class="breadcrumb-item active" aria-current="page" id="breadcrumbClassName"/>
				</ol>
			</nav>

			<section class="jumbotron text-center">
				<div class="container">
					<h1 class="jumbotron-heading">Serviços do Módulo: <strong id="headerClassName"></strong></h1>
				</div>
			</section>
	
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<div class="listClass load">
							<table id="methodTable" class="table table-striped table-bordered">
								<thead>
									<tr>
										<th>Serviço</th>
										<th>Assinatura</th>
										<th>Descrição</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/app/Methods.js"></script>
	</jsp:attribute>
</tags:_Layout>