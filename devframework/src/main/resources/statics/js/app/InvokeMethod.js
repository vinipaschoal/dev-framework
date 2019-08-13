app.InvokeMethod = {
		
	// funcao de inicializacao 
	init: function () {	
		
		// atualiza os elementos da pagina com o nome da classe e do metodo	  
		app.settings.loadScreenDescription();
		
		var methodDesc = app.storage.get("methodDescriptor");
		app.InvokeMethod.createForm(methodDesc.parameters);
		
		
		$(".btn").after("<div class='LoadWait' style='margin-left:20px;position:relative;width: 200px;'></div>");
		//app.settings.setLoadSubmit('btn', 'Aguarde...', 'disabled');
		
		
		//////////////////////////////////////////
		
		
		
		
		
	},

	// cria o Form para a entrada dos valores do metodo a ser invocado
	createForm: function (parameters){
		
		var $form = {};
		$.each(parameters, function (p, paramDesc) {
			$form[paramDesc.name] = JSON.parse(paramDesc.jsonSchema);
		});
		
		$('#formParam').jsonForm({
			schema: $form,
			form: [
			    "*",
			    {
			      "type": "submit",
			      "title": "Executar"
			    }    
			],
			onSubmit: function (errors, values) {
				app.settings.setLoadSubmit('btn', 'Aguarde...', 'disabled');
				if (errors) {
					app.settings.setLoadSubmit('btn', 'Executar', '');
					alertBt({
		        	      messageText: "Ocorreu um erro!",
		        	      headerText: "Erro",
		        	      alertType: "danger"
		        	    });
				} else {
					// recupera o descritor do metodo 
					var methodDesc = app.storage.get("methodDescriptor");

					// JSON de request
		    		var jsonReq = new Object();
		    		jsonReq.methodDescriptor = methodDesc;
		    		jsonReq.paramValues = values
		    		console.log(values);
		    		
		    		$.ajax({
		    			url: 'invokeMethod.op',
		    			type: 'POST',
		    			dataType: 'json',
		    			data: JSON.stringify(jsonReq),
		    			contentType: 'application/json',
		    			mimeType: 'application/json',
		    			success: function (result) {
		    				console.log(result);
		    				
		    				var $tabResult = $("#tabResult");
		    				var $result = $("#result");
		    				
		    				$tabResult.show();
		    				$("html, body").animate({scrollTop: $result.offset().top }, 1000);
		    						    				
		    				if (!app.settings.isJson(result.data)){
		    					$result.text(result.data);
		    				}else{
		    					$result.text(JSON.stringify(result, null, 2));
		    				}
		    				app.settings.setLoadSubmit('btn', 'Executar', '');
		    	        },
		    			error:function(data,status,er) {
		    				alert("error");
		    				app.settings.setLoadSubmit('btn', 'Executar', '');
		    			}
		    		});
				}
			}
		});
	}
};

$(function() {
    app.InvokeMethod.init();
});