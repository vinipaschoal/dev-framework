app.Methods = {
		methodClass: $('#methodTable').DataTable(
				{
					"language": app.settings.languagePtBr,
					"paging": false,
					"searching": false,
					"ordering": false,
			        "info":     false
				}),
				
		// funcao de inicializacao 
		init: function () {
			
			// atualiza os elementos da pagina com o nome da classe
        	var data = app.storage.get("methodList");        	
			$('#breadcrumbClassName').text(data['clazz']);
			$('#headerClassName').text(app.utils.clazzSimpleName(data['clazz']));
			
			app.settings.loading.show();
			
			// lista os metodos
			app.Methods.list();
		},
		
		// carrega a lista de metodos
		list: function(){
			
        	app.Methods.methodClass.rows().remove().draw();
        	
			// recupera a lista de metodos do storage
        	var data = app.storage.get("methodList");
        	var $clazz = data.clazz;
        	var $methodList = data.methods;
            $.each($methodList, function (i, method) {
           	 
            	app.Methods.methodClass.row.add([
					"<a href='#' onclick='app.Methods.invokeMethod(" + i + ")'>" + app.utils.methodSignature(method) + "</a>"
					]).draw( false );
           	});
            app.settings.loading.hide();
		},
		
		//  metodo selecionado para ser invocado
		invokeMethod: function(index){
			
			// obtem o metodo selecionado
			var $method = app.storage.get("methodList").methods[index];
			
			// armazena  recebido no storage
			app.storage.put("methodInvoke", $method);
			
			// redireciona para a pagina de invocao de metodos
			app.callPage("invokeMethod.jsp");
		}
};

$(function() {
    app.Methods.init();
});