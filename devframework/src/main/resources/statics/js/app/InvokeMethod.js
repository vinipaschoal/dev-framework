app.InvokeMethod = {
	// funcao de inicializacao 
	init: function () {	
		
		// atualiza os elementos da pagina com o nome da classe e do metodo	  
		app.settings.loadScreenDescription();
		
		var methodDesc = app.storage.get("methodDescriptor");
		app.InvokeMethod.createForm(methodDesc.parameters);
		
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
		    		jsonReq.paramValues = values;
		    		
		    		$.ajax({
		    			url: 'invokeMethod.op',
		    			type: 'POST',
		    			dataType: 'json',
		    			data: JSON.stringify(jsonReq),
		    			contentType: 'application/json',
		    			mimeType: 'application/json',
		    			success: function (result) {
		    				
		    				if (!result.success) {
								app.settings.setLoadSubmit('btn', 'Executar', '');
								alertBt({
					        	      messageText: result.message,
					        	      headerText: "Erro",
					        	      alertType: "danger"
					        	    });
							} else {
		    						    				
			    				console.log(result);
			    				
			    				var $tabResult = $("#tabResult");
			    				var $result = $("#result");
			    				
			    				$tabResult.show();
			    				$("html, body").animate({scrollTop: $result.offset().top - 100 }, 1000);
			    				
			    				var render = new RenderResult();
			    				
			    				if ($.isFunction(render[result.type])) {
			    					render[result.type](result.data, $result);
			    					
			    					//Código teste para renderizar o mapa
			    					
			    					//Modelo de objeto para receber do serviço
			    					var $data = {
			    							map: {
			    								lat: -23.533773,
		    									long: -46.625290,
		    									zoom: 6
			    							},			    					
			    							markers: [
			    								{
			    									lat: -23.533773,
			    									long: -46.625290,
			    									popup: "<b>São Paulo</b><br/>Temperaturas:<br>Janeiro/19 - Min: 10C Max: 28C"
			    								},
			    								{
			    									lat: -23.17944,
			    									long: -45.88694,
			    									popup: "<b>São José dos Campos</b><br/>Temperaturas:<br>Janeiro/19 - Min: 13C Max: 32C"
			    								}
			    							]
			    					};
			    					
			    					//Chama o método de mapa
			    					render["MAP"]($data, $result);
			    					
			    					
			    			    } else {
			    			    	$result.html(result.data);
			    				}
			    				
			    				/*
			    				if (!app.settings.isJson(result.data)){
			    					$result.text(result.data);
			    				}else{
			    					$result.text(JSON.stringify(result, null, 2));
			    				}
			    				*/
			    				app.settings.setLoadSubmit('btn', 'Executar', '');
							}
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