app.InvokeMethod = {
		dataChartBar: function (){
			
		var color = Chart.helpers.color;
		var barChartData = {
	        labels: ['janeiro', 'fevereiro', 'março', 'abril', 'maio', 'junho'],
	        datasets: [
	        	{
		            label: 'Máximas',
		            backgroundColor: color('#36a2eb').alpha(0.5).rgbString(),
					borderColor: '#36a2eb',
					borderWidth: 1,
		            data: [12, 19, 32, 15, 2, 30]
		        },
		        {
		            label: 'Mínimas',
		            backgroundColor: color('#ff6384').alpha(0.5).rgbString(),
					borderColor: '#ff6384',
					borderWidth: 1,
		            data: [-12, 5, 18, -5, -12, 10]
		        }
		    ]
	    }
		
		return barChartData;
	},
	// funcao de inicializacao 
	init: function () {	
		
		// atualiza os elementos da pagina com o nome da classe e do metodo	  
		app.settings.loadScreenDescription();
		
		var methodDesc = app.storage.get("methodDescriptor");
		app.InvokeMethod.createForm(methodDesc.parameters);
		
		//teste
		var $tabResult = $("#tabResult");
		var $result = $("#result");
		
		$tabResult.show();
		var render = new RenderResult();
		$result.append(render["ChartBar"](app.InvokeMethod.dataChartBar()));
		
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
		    				$("html, body").animate({scrollTop: $result.offset().top - 100 }, 1000);
		    				
		    				var render = new RenderResult();
		    				
		    				if ($.isFunction(render[result.type])) {
		    					render[result.type](result.data);
		    			    } else {
		    			    	$result.html(result.data);
		    				}
		    				
		    				//Este código é apenas para testes de renderização enquanto não há o retono json
		    				$result.append(render["ChartBar"](app.InvokeMethod.dataChartBar));
		    				
		    				/*
		    				if (!app.settings.isJson(result.data)){
		    					$result.text(result.data);
		    				}else{
		    					$result.text(JSON.stringify(result, null, 2));
		    				}
		    				*/
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