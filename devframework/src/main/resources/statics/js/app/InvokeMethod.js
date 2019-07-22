app.InvokeMethod = {
	init: function () {	
		app.InvokeMethod.list();

	},
	createForm: function (fields){
		
		var $form = {};
		$.each(fields, function (f, field) {			
			$form[field.name] = field.attribute;
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
	},
	list: function(){
			
		var url = window.location.href.slice(window.location.href.indexOf('?') + 1);
		
    	$.ajax({
            type: "GET",
            url: $('#formParam').data("url"),
            data: url,
            processData: true,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                if (data.success){                    
                	app.InvokeMethod.createForm(data.parameters.fields);
                    app.settings.loading.hide();
                }else{
                	alertBt({
   	        	      messageText: data.message,
   	        	      headerText: "Alerta",
   	        	      alertType: "danger"
   	        	    });
                	app.settings.loading.hide();
                }
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
	}
};

$(function() {
    app.InvokeMethod.init();
});