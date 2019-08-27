app.InvokeMethod = {
	// funcao de inicializacao 
	init: function () {	
		
		// atualiza os elementos da pagina com o nome da classe e do metodo	  
		app.settings.loadScreenDescription();
		
		var methodDesc = app.storage.get("methodDescriptor");
		app.InvokeMethod.createForm(methodDesc.parameters);
		
		// Render map
		var mymap = L.map('mapid').setView([-23.17944, -45.88694], 10);
		
		L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
			attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
			maxZoom: 18,
			id: 'mapbox.streets',
			accessToken: 'pk.eyJ1IjoiZmt2aWxsYW5pIiwiYSI6ImNqenQ2YTliejAyNDczbG1qeW40aGpid2YifQ.rQwyF9X2CuV1s7vjS1G9oA'
		}).addTo(mymap);
		
		var marker = L.marker([-23.17944, -45.88694]).addTo(mymap);
		marker.bindPopup("<b>São José dos Campos</b><br>Estamos aqui!.").openPopup();
		
		
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