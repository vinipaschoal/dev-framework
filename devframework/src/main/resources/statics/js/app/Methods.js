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
			app.settings.loadScreenDescription();
			
			app.settings.loading.show();
			
			// lista os metodos
			app.Methods.list();
		},
		
		// carrega a lista de metodos
		list: function(){
			
        	app.Methods.methodClass.rows().remove().draw();
        	
			// recupera a lista de metodos do storage
        	var methodList = app.storage.get("methodList");
            $.each(methodList, function (i, methodDesc) {
           	 
            	app.Methods.methodClass.row.add([
            		methodDesc.label,
					"<a href='#' onclick='app.Methods.invokeMethod(" + i + ")'>" + app.utils.methodSignature(methodDesc) + "</a>",
					methodDesc.description]).draw( false );
           	});
            app.settings.loading.hide();
		},
		
		//  metodo selecionado para ser invocado
		invokeMethod: function(index){
			
			// recupera o descritor do metodo escolhido
    		var methodDesc = app.storage.get("methodList")[index];
			
			// armazena  recebido no storage
			app.storage.put("methodDescriptor", methodDesc);
			
			// redireciona para a pagina de invocao de metodos
			app.callPage("invokeMethod.jsp");
		}
};

$(function() {
    app.Methods.init();
});