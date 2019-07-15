app.Methods = {
		methodClass: $('#methodTable').DataTable({"language": app.settings.languagePtBr }),
		init: function () {
			app.Methods.list();
		},
		list: function(){
			
        	app.Methods.methodClass.rows().remove().draw();
        	
        	console.log($('#methodTable').data("class"));
        	
        	$.ajax({
                type: "GET",
                url: $('#methodTable').data("url"),
                data: {
                	class: $('#methodTable').data("class")
                },
                processData: true,
                contentType: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                    console.log(data);
                    if (data.success){
	                    var $classList = data.classes;                    
	                    $.each($classList, function( i, classe ) {
	                    	app.Methods.methodClass.row.add([
								classe.name
								,"<a href='methods.jsp?class=" + classe.qualifiedName + "'>" + classe.qualifiedName + "</a>"
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