app.uploadClass = {
		validExts: new Array(".class",".jar"),
		tableClass: $('#classTable').DataTable({"language": app.settings.languagePtBr }),
		init: function () {

			app.uploadClass.list();
	    	
	    	var validExts = new Array(".class",".jar");
	    	$("#uploadFile").attr("accept", app.uploadClass.validExts);
	    	
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
				app.uploadClass.save();
	        });
	    	
		},
		list: function(){
			
        	app.uploadClass.tableClass.rows().remove().draw();
        	
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
                    
                    $.each($classList, function( i, classe ) {

                    	/*var $methods = "";
                   	  	var $count = 0;
                   	  	$.each(classe.methods, function( m, metodo ) {                   	  		
                   	  		
                   	  		$count++;
                   	  		
                   	  		var $parameters = "";
		                   	var $countP = 0;
	                   	  	$.each(metodo.parameters, function( p, param ) {
	            	  			$countP++;
	            	  			$parameters = $parameters + param + (($countP < metodo.parameters.length ? ", " : ""));
	            	  		});
	                   	  	$parameters = ($parameters == "" ? "" : "(" + $parameters + ")");
                   	  		
                	  		$methods = $methods + "<a href='#'>" + metodo.methodName + $parameters + "</a>" + (($count < classe.methods.length ? ", " : ""));
                	  		
                	  	});
                   	  	
                   	  	app.uploadClass.tableClass.row.add([
							classe.className, 
							$methods
							]).draw( false );
						*/
                    	
                    	app.uploadClass.tableClass.row.add([
							i + 1, classe.qualifiedName
							]).draw( false );
                   	});
                    
                },
                error: function (e) {
                    alertBt({
   	        	      messageText: "Ocorreu um erro.<br/>",
   	        	      headerText: "Erro",
   	        	      alertType: "danger"
   	        	    });
                }
            });

		},
		save: function () {

			var $form = $('#fileUploadForm');
            
            if ($('#uploadFile').val() == ""){
            	alertBt({
	        	      messageText: "Selecione uma classe java, arquivo do tipo " + app.uploadClass.validExts.toString() + ".",
	        	      headerText: "Alerta",
	        	      alertType: "warning"
	        	    });
            	return false;
            }else{
            	var $fileExt = $('#uploadFile').val();
                $fileExt = $fileExt.substring($fileExt.lastIndexOf('.'));
                if (app.uploadClass.validExts.indexOf($fileExt) < 0) {
					alertBt({
	        	      messageText: "O arquivo selecionado é inválido. Selecione apenas arquivos do tipo " + app.uploadClass.validExts.toString() + ".",
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
       	        	      messageText: data.message,
       	        	      headerText: "Confirmação",
       	        	      alertType: "success"
       	        	    });
                        app.uploadClass.list();
                    }else{
                    	alertBt({
         	        	      messageText: data.message, //"Classe java inválida, a classe deve ter as anotações @ServiceClass e @ServiceMethod.",
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
		}
};

$(function() {
    app.uploadClass.init();
});