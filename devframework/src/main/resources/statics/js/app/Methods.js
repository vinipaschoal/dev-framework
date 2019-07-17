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
			app.Methods.list();
		},
		list: function(){
			
        	app.Methods.methodClass.rows().remove().draw();
        	
        	console.log($('#methodTable').data("clazz"));
        	
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
	                    	var $parameters = "";
		                   	var $count = 0;
	                   	  	$.each(method.parameters, function (p, parameter) {
	            	  			$count++;
	            	  			$parameters = $parameters + parameter.dataType + " " + parameter.name + (($count < method.parameters.length ? ", " : ""));
	            	  		});
	                   	  	$parameters = "(" + $parameters + ")";
                   	  		
	                    	app.Methods.methodClass.row.add([
								"<a href='invokeMethod.jsp?clazz=" + $clazz.qualifiedName + "&method=" + method.name + "'>" + method.returnType + " " + method.name + $parameters + "</a>"
								]).draw( false );
	                   	});
                    }else{
                    	alertBt({
       	        	      messageText: data.message,
       	        	      headerText: "Alerta",
       	        	      alertType: "danger"
       	        	    });
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
                }
            });
		}
};

$(function() {
    app.Methods.init();
});