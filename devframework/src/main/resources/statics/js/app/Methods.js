app.Methods = {
		methodClass: $('#methodTable').DataTable(
				{
					"language": app.settings.languagePtBr,
					"paging": false,
					"searching": false,
					"ordering": false,
			        "info":     false
				}),
		init: function () {
			app.settings.loading.show();
			app.Methods.list();
		},
		list: function(){
			
        	app.Methods.methodClass.rows().remove().draw();
        	
        	$.ajax({
                type: "GET",
                url: $('#methodTable').data("url"),
                data: {
                	clazz: $('#methodTable').data("clazz")
                },
                processData: true,
                contentType: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                    console.log(data);
                    if (data.success){
                    	var $clazz = data.clazz;
                    	var $methodList = data.clazz.methods;
	                    $.each($methodList, function (i, method) {
	                    	
	                    	jMethod = {};
	                    	jMethod.methodName = method.name;
	                    	jMethod.methodReturnType = method.returnType;
	                    	jMethod.parameters = [];
	                    	
	                    	var $parameters = "";
	                    	var $parametersUrl = "";
		                   	var $count = 0;
	                   	  	$.each(method.parameters, function (p, parameter) {
	            	  			$count++;
	            	  			$parameters = $parameters + parameter.dataType + " " + parameter.name + (($count < method.parameters.length ? ", " : ""));

	            	  			param = {};
	            	  			param.name = parameter.name;
	            	  			param.dataType = parameter.dataType;
	            	  			jMethod.parameters.push(param);
	     	                    
	            	  		});
	                   	  	$parameters = "(" + $parameters + ")";
	                   	  	
	                   	  	var $methodQuery = jQuery.param(jMethod);
	                   	 
	                    	app.Methods.methodClass.row.add([
								"<a href='invokeMethod.jsp?clazz=" + $clazz.qualifiedName + "&" + $methodQuery + "'>" + method.returnType + " " + method.name + $parameters + "</a>"
								]).draw( false );
	                   	});
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
    app.Methods.init();
});