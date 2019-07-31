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
					alertBt({
	   	        	      messageText: "Objeto Json a ser submetido:<br/>" + JSON.stringify(values, null, 2),
	   	        	      headerText: "Alerta",
	   	        	      alertType: "success"
	   	        	    });					
				}
			}
		});
	}
};

$(function() {
    app.InvokeMethod.init();
});