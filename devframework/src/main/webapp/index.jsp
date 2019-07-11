<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DevFramework</title>
<link href="webjars/bootstrap/4.3.1/css/bootstrap.css" rel="stylesheet">
<link href="webjars/bootstrap/4.3.1/css/bootstrap-theme.css"
	rel="stylesheet">
</head>
<body>

	<header>
	<div class="collapse bg-dark" id="navbarHeader">
		<div class="container">
			<div class="row">
				<div class="col-sm-8 col-md-7 py-4">
					<h4 class="text-white">Sobre</h4>
					<p class="text-muted" style="font-size: 15px !important;">Framework
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

	<main role="main"> <section class="jumbotron text-center"
		style="padding: 2rem 2rem !important;">
	<div class="container">
		<h1 class="jumbotron-heading">Lista de Classes</h1>
		<p class="lead">Selecione o botão abaixo para cadastrar novas classes java.</p>
		<p>
			<button type="button" class="btn btn-primary" data-toggle="modal"
				data-target="#classModal">Adicionar Nova Classe Java</button>
		</p>
	</div>
	</section>

	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="listClass">
					<table id="classTable" class="table table-striped table-bordered"
						style="width: 100%">
						<thead>
							<tr>
								<th>#</th>
								<th>Classe Java</th>
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

	<!-- Modal -->
	<div class="modal fade" id="classModal" tabindex="-1" role="dialog"
		aria-labelledby="classModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Incluir Nova Classe Java</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
				
					<!-- FORM PARA ENVIO DE NOVAS CLASSES -->
					<form method="post" action="uploadFile.op" enctype="multipart/form-data" id="fileUploadForm">
						<div class="row">
							<div class="col-md-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
									</div>
									<div class="custom-file">
										<input value="" type="file" name="uploadFile"
											class="custom-file-input" id="uploadFile" accept="">
										<label class="custom-file-label" for="inputGroupFile01"
											id="inputGroupFile01">Selecione uma classe java</label>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="btnCancel" class="btn btn-secondary">Cancelar</button>
					<button type="submit" id="btnSubmit" class="btn btn-primary">Upload</button>
				</div>
			</div>
		</div>
	</div>

	<!-- load jQuery library -->
	<script type="text/javascript" src="webjars/jquery/3.3.1/jquery.min.js"></script>
	<!-- load Bootstrap library -->
	<script type="text/javascript"
		src="webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
	<!-- load alert.bootstrap library -->
	<script type="text/javascript" src="resources/alert.bootstrap.js"></script>
	<!-- load dataTables library -->
	<script type="text/javascript"
		src="webjars/datatables/1.10.19/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript"
		src="webjars/datatables/1.10.19/js/dataTables.bootstrap4.min.js"></script>


	<script>
		$(document).ready(
			function() {
	        	var languagePtBr = {
	            	    "sEmptyTable": "Nenhum registro encontrado",
	            	    "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
	            	    "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
	            	    "sInfoFiltered": "(Filtrados de _MAX_ registros)",
	            	    "sInfoPostFix": "",
	            	    "sInfoThousands": ".",
	            	    "sLengthMenu": "_MENU_ resultados por página",
	            	    "sLoadingRecords": "Carregando...",
	            	    "sProcessing": "Processando...",
	            	    "sZeroRecords": "Nenhum registro encontrado",
	            	    "sSearch": "Pesquisar",
	            	    "oPaginate": {
	            	        "sNext": "Próximo",
	            	        "sPrevious": "Anterior",
	            	        "sFirst": "Primeiro",
	            	        "sLast": "Último"
	            	    },
	            	    "oAria": {
	            	        "sSortAscending": ": Ordenar colunas de forma ascendente",
	            	        "sSortDescending": ": Ordenar colunas de forma descendente"
	            	    }
	            	};
	        	
	        	var $tableClass = $('#classTable').DataTable({
	                "language": languagePtBr
	        	});
	        	
	        	listClasses();
	        	
	        	var validExts = new Array(".class", ".jar");
	        	$("#uploadFile").attr("accept", validExts);
	        	
	        	$("#uploadFile").on("change", function (event) {
	        		$("#inputGroupFile01").text($(this).val());
	        	});
	        	
	        	$("#btnCancel").on("click", function (event) {
	        		$('#uploadFile').replaceWith($('#uploadFile').val('').clone(true));
	        		$("#inputGroupFile01").text("Selecione uma classe java");
	                $('#classModal').modal('hide');
	        	});
	        	
	            $("#btnSubmit").click(function (event) {
					event.preventDefault();                
					var $form = $('#fileUploadForm');
	                
	                if ($('#uploadFile').val() == ""){
	                	alertBt({
	  	        	      messageText: "Selecione uma classe java, arquivo do tipo " + validExts.toString() + ".",
	  	        	      headerText: "Alerta",
	  	        	      alertType: "warning"
	  	        	    });
	                	return false;
	                }else{
	                	var $fileExt = $('#uploadFile').val();
	                    $fileExt = $fileExt.substring($fileExt.lastIndexOf('.'));
	                    if (validExts.indexOf($fileExt) < 0) {
	    					alertBt({
	    	        	      messageText: "O arquivo selecionado é inválido. Selecione apenas arquivos do tipo " + validExts.toString() + ".",
	    	        	      headerText: "Alerta",
	    	        	      alertType: "warning"
	    	        	    });
	    					$("#inputGroupFile01").text("Selecione uma classe java");
	    					$('#uploadFile').replaceWith($('#uploadFile').val('').clone(true));
	                      return false;
	                    }
	                }
	                
	                $("#btnSubmit").attr("disabled", true);
	                
	                var $data = new FormData($form[0]);
	                $.ajax({
	                    type: "POST",
	                    enctype: 'multipart/form-data',
	                    url: $form.attr('action'),
	                    data: $data,
	                    processData: false,
	                    contentType: false,
	                    cache: false,
	                    timeout: 600000,
	                    success: function (data) {
	                        console.log(data);
	                        $("#btnSubmit").attr("disabled", false);
	                        
	                        if (data.success){
	                        	$('#uploadFile').replaceWith($('#uploadFile').val('').clone(true));
	                            $("#inputGroupFile01").text("Selecione uma classe java");
	                            $('#classModal').modal('hide');
	                            alertBt({
	           	        	      messageText: "Arquivo \""+ data.classe +"\" cadastrado com sucesso.",
	           	        	      headerText: "Confirmação",
	           	        	      alertType: "success"
	           	        	    });
	                            listClasses();
	                        }else{
	                        	alertBt({
	             	        	      messageText: "Classe java inválida, a classe deve ter as anotações @ServiceClass e @ServiceMethod.",
	             	        	      headerText: "Alerta",
	             	        	      alertType: "warning"
	             	        	    });
	                        }
	                        
	                    },
	                    error: function (e) {
	                        $("#btnSubmit").attr("disabled", false);
	                        $('#uploadFile').replaceWith($('#uploadFile').val('').clone(true));
	                        $("#inputGroupFile01").text("Selecione uma classe java");
	                        $('#classModal').modal('hide');
	                        alertBt({
	       	        	      messageText: "Ocorreu um erro.<br/><br/><b>Erro</b>: " + e + ".",
	       	        	      headerText: "Erro",
	       	        	      alertType: "danger"
	       	        	    });
	                    }
	                });
	            });
	            
	            function listClasses(){
	            	
	            	$tableClass.rows().remove().draw();
	            	
	            	$.ajax({
	                    type: "GET",
	                    url: "listClasses.op",
	                    processData: false,
	                    contentType: false,
	                    cache: false,
	                    timeout: 600000,
	                    success: function (data) {
	                        console.log(data);
	                        var $classList = data.classes;
	                        $.each($classList, function( index, value ) {
	                       	  	console.log( index + ": " + value.name + " - " + value.qualifiedName);
								$tableClass.row.add([(parseInt(index, 10) + 1), 
									"<a href='listMethods.op?class=" + value.qualifiedName + "'>" + value.qualifiedName + "</a>"]).draw( false );
	                       	});
	                    },
	                    error: function (e) {
	                        alertBt({
	       	        	      messageText: "Ocorreu um erro.<br/><br/><b>Erro</b>: " + e + ".",
	       	        	      headerText: "Erro",
	       	        	      alertType: "danger"
	       	        	    });
	                    }
	                });
	            }
	            
	        });
	    </script>
</body>
</html>