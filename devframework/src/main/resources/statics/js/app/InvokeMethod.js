app.InvokeMethod = {
		
	// funcao de inicializacao 
	init: function () {	
		
		// atualiza os elementos da pagina com o nome da classe e do metodo
    	var classDesc = app.storage.get("classDescriptor");        	
		$('#breadcrumbClassName').text(classDesc.qualifiedName);
		$('#headerClassName').text(classDesc.label);
		
		var methodDesc = app.storage.get("methodDescriptor");  
		$('#breadcrumbMethodName').text(app.utils.methodSignature(methodDesc));
		$('#headerMethodName').text(methodDesc.label);

		app.InvokeMethod.createForm(methodDesc.parameters);
	},
	
	// cria o Form para a entrada dos valores do metodo a ser invocado
	createForm: function (parameters){
		
		var $form = {};
		$.each(parameters, function (p, parameter) {			
			$form[parameter.name] = app.utils.jsonformType(parameter);
		});
		
		$('form').jsonForm({
			schema: $form,
			onSubmit: function (errors, values) {
				if (errors) {
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
		    		jsonReq.text="Some text";
		    		jsonReq.integer=300;
		    		jsonReq.float=11.22;
		    		jsonReq.methodDescriptor = methodDesc;
		    		jsonReq.paramValues = values
		    		
		    		$.ajax({
		    			url: 'invokeMethod.op',
		    			type: 'POST',
		    			dataType: 'json',
		    			data: JSON.stringify(jsonReq),
		    			contentType: 'application/json',
		    			mimeType: 'application/json',
		    			success: function (data) {
		    				console.log(data);
		    				
							alertBt({
			   	        	      messageText: "Resposta:<br/>" + JSON.stringify(data, null, 2),
			   	        	      headerText: "Alerta",
			   	        	      alertType: "success"
			   	        	    });					
		    	        },
		    			error:function(data,status,er) {
		    				alert("error");
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