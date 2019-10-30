app.Classes = {
		validExts: new Array(".class",".jar"),
		tableClass: $('#classTable').DataTable({"language": app.settings.languagePtBr,
			// desabilita a ordenacao na coluna do icone de remover servico:
			"columnDefs": [{"orderable": false, "targets": 3}]}), 
		
		// funcao de inicializacao 
		init: function () {
			
			app.settings.loading.show();
			
			// apaga o storage atual
			app.storage.clear();

			// lista as classes
			app.Classes.list();
	    	
	    	var validExts = new Array(".class",".jar");
	    	$("#uploadFile").attr("accept", app.Classes.validExts);
	    	
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
				app.Classes.save();
	        });
	    	
	    	// lista os metodos da classe de servico selecionada
	    	$(document).on('click', '.listMethods', function(){
	    		event.preventDefault();
	    		$index = $(this).data("index");
	    		app.Classes.listMethods($index);
	    	});
	    	
	    	// remove a classe de servico selecionada
	    	$(document).on('click', '.removeService', function(){
	    		event.preventDefault();
	    		$index = $(this).data("index");
	    		app.Classes.removeService($index);
	    	});
		},
		
		// carrega a lista de classes
		list: function(){
			
        	app.Classes.tableClass.rows().remove().draw();
        	
        	$.ajax({
                type: "GET",
                url: $('#classTable').data("url"),
                processData: false,
                contentType: false,
                cache: false,
                timeout: 600000,
                success: function (result) {
                    console.log(result);
                    if (result.success){
                    	var classList = result.data;
                        
                        // armazena as classes recebidas no storage
                        app.storage.put("classList", classList);
                    	
	                    $.each(classList, function( i, classDesc ) {	                    	
	                    	app.Classes.tableClass.row.add([
	                    		classDesc.label,
								"<a href='#' class='listMethods' data-index='" + i + "'>" + classDesc.qualifiedName + "</a>",
								classDesc.description,
								"<center><a href='#' class='removeService' data-index='" + i + "'><img src='resources/images/delete.png' height='25' width='25'></a></center>",
								]).draw( false );
	                   	});
                    }else{
                    	alertBt({
       	        	      messageText: result.message,
       	        	      headerText: "Alerta",
       	        	      alertType: "danger"
       	        	    });
                    }
        			app.settings.loading.hide();
                },
                error: function (e) {
                	var $msg = $(e.responseText).filter('title').text();
                	if ($msg == '') $msg = "Ocorreu um erro.<br/>";
                    alertBt({
   	        	      messageText: $msg,
   	        	      headerText: "Erro",
   	        	      alertType: "danger"
   	        	    });
        			app.settings.loading.hide();
                }
            });
		},
		
		// carrega os metodos da classe selecionada
		listMethods: function(index){
			
			// recupera o descritor da classe escolhida
    		var classDesc = app.storage.get("classList")[index];

			// JSON de request
    		var jsonReq = new Object();
    		jsonReq.clazz = classDesc.qualifiedName;
    		
    		
    		$.ajax({
    			url: 'listMethods.op',
    			type: 'POST',
    			dataType: 'json',
    			data: JSON.stringify(jsonReq),
    			contentType: 'application/json',
    			mimeType: 'application/json',
    			success: function (result) {
    				console.log(result);
    				
    				// apaga o storage atual
    				app.storage.clear();
    				
    				// armazena o descritor da classe selecionada
    				app.storage.put("classDescriptor", classDesc);
    				
    				// armazena o objeto recebido no storage
    				app.storage.put("methodList", result.data);
    				
    				// redireciona para a pagina de metodos
    				app.callPage("methods.jsp");
    	        },
    			error:function(data,status,er) {
    				alert("error");
    			}
    		});
		},
		
		// remove a classe de servico selecionada
		removeService: function(index){
			
			// recupera o descritor da classe escolhida
    		var classDesc = app.storage.get("classList")[index];

			// JSON de request
    		var jsonReq = new Object();
    		jsonReq.clazz = classDesc.qualifiedName;
    		
    		// confirma apagar o servico
    		var promise = alertBt({
    		  type: "confirm",
      	      messageText: "Deseja realmente apagar o m&oacute;dulo '" + classDesc.label + "'?",
      	      headerText: "Alerta",
      	      alertType: "warning"
      	    });
    		
    		// processa a escolha do usuario
    		promise.done(function (dlgResult) {
    			
    			// confirmou apagar o servico
    			if ( dlgResult ){
    				
    	    		$.ajax({
    	    			url: 'removeService.op',
    	    			type: 'POST',
    	    			dataType: 'json',
    	    			data: JSON.stringify(jsonReq),
    	    			contentType: 'application/json',
    	    			mimeType: 'application/json',
    	    			success: function (result) {
    	    				console.log(result);
    	    				
    	                    if (result.success){
    	                        alertBt({
    	         	        	      messageText: result.message,
    	         	        	      headerText: "Confirma&ccedil;&atilde;o",
    	         	        	      alertType: "success"
    	         	        	    });
    	                        
    	                          // lista as classes de servico
    	                          app.Classes.list();
    	                      }else{
    	                      	alertBt({
    	           	        	      messageText: result.message,
    	           	        	      headerText: "Alerta",
    	           	        	      alertType: "warning"
    	           	        	    });
    	                      }
    	    	        },
    	    			error:function(data,status,er) {
    	                    var $msg = $(er.responseText).filter('title').text();
    	                	if ($msg == '') $msg = "Ocorreu um erro.<br/><br/><b>Erro</b>: " + er + ".";
    	                    alertBt({
    	   	        	      messageText: $msg,
    	   	        	      headerText: "Erro",
    	   	        	      alertType: "danger"
    	   	        	    });
    	                }
    	    		});
    			}
    		});
		},
		
		// faz o upload do arquivo (classe/jar) selecionado 
		save: function () {

			var $form = $('#fileUploadForm');
            
            if ($('#uploadFile').val() == ""){
            	alertBt({
	        	      messageText: "Selecione uma classe java, arquivo do tipo " + app.Classes.validExts.toString() + ".",
	        	      headerText: "Alerta",
	        	      alertType: "warning"
	        	    });
            	return false;
            }else{
            	var $fileExt = $('#uploadFile').val();
                $fileExt = $fileExt.substring($fileExt.lastIndexOf('.'));
                if (app.Classes.validExts.indexOf($fileExt) < 0) {
					alertBt({
	        	      messageText: "O arquivo selecionado &eacute; inv&aacute;lido. Selecione apenas arquivos do tipo " + app.Classes.validExts.toString() + ".",
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
                success: function (result) {
                    console.log(result);
                    $("#btnSubmit").attr("disabled", false);
                    
                    if (result.success){
                    	$('#uploadFile').replaceWith($('#uploadFile').val('').clone(true));
                        $("#inputGroupFile01").text("Selecione uma classe java");
                        $('#classModal').modal('hide');
                        alertBt({
       	        	      messageText: result.message,
       	        	      headerText: "Confirma&ccedil;&atilde;o",
       	        	      alertType: "success"
       	        	    });
                        
                        // lista as classes de servico
                        app.Classes.list();
                    }else{
                    	alertBt({
         	        	      messageText: result.message,
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

                    var $msg = $(e.responseText).filter('title').text();
                	if ($msg == '') $msg = "Ocorreu um erro.<br/><br/><b>Erro</b>: " + e + ".";
                    alertBt({
   	        	      messageText: $msg,
   	        	      headerText: "Erro",
   	        	      alertType: "danger"
   	        	    });
                }
            });
		}
};

$(function() {
    app.Classes.init();
});