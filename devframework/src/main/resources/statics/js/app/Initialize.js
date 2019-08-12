var app = window.app || {};

app.settings = {
		languagePtBr: {
			"sEmptyTable": "Nenhum registro encontrado",
    	    "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
    	    "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
    	    "sInfoFiltered": "(Filtrados de _MAX_ registros)",
    	    "sInfoPostFix": "",
    	    "sInfoThousands": ".",
    	    "sLengthMenu": "_MENU_ resultados por página",
    	    "sLoadingRecords": "Carregando...",
    	    "sProcessing": "Processando...",
    	    "sZeroRecords": "Nenhum registro encontrado",
    	    "sSearch": "Pesquisar",
    	    "oPaginate": {
    	        "sNext": "Próximo",
    	        "sPrevious": "Anterior",
    	        "sFirst": "Primeiro",
    	        "sLast": "Último"
    	    },
    	    "oAria": {
    	        "sSortAscending": ": Ordenar colunas de forma ascendente",
    	        "sSortDescending": ": Ordenar colunas de forma descendente"
    	    }
		},		
		loading: $("<div id='loading'><span><i class='fas fa-spinner fa-pulse fa-1x'></i> Carregando...</span></div>"),
		setLoad: function () {
			$(".Load").append(app.settings.loading);
		},
		getUrlParametes: function () {
			var vars = [], hash;
		    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
		    for(var i = 0; i < hashes.length; i++)
		    {
		        hash = hashes[i].split('=');
		        vars.push(hash[0]);
		        vars[hash[0]] = hash[1];
		    }
		    return vars;
		},
		isJson: function (str) {
			try {
		        JSON.parse(str);
		    } catch (e) {
		        return false;
		    }
		    return true;
		},
		loadScreenDescription: function () {
			var classDesc = app.storage.get("classDescriptor");
			if (classDesc != null){
				if ($('#breadcrumbClassName').length) $('#breadcrumbClassName').text(classDesc.qualifiedName);
				if ($('#headerClassName').length) $('#headerClassName').text(classDesc.label);
				if ($('#InvokeClassName').length) $('#InvokeClassName').text(classDesc.qualifiedName + "(" + classDesc.label + ")");
				if ($('#InvokeClassDescription').length) $('#InvokeClassDescription').text(classDesc.description);
			}

			var methodDesc = app.storage.get("methodDescriptor");
			if (methodDesc != null){
				if ($('#breadcrumbMethodName').length) $('#breadcrumbMethodName').text(app.utils.methodSignature(methodDesc));
				if ($('#headerMethodName').length) $('#headerMethodName').text(methodDesc.label);
				if ($('#InvokeMethodName').length) $('#InvokeMethodName').text(app.utils.methodSignature(methodDesc) + "(" + classDesc.methodDesc + ")");
				if ($('#InvokeMethodDescription').length) $('#InvokeMethodDescription').text(methodDesc.description === "" ? "Sem descrição" : methodDesc.description);
			}
		},
		init: function () {
			
			app.settings.setLoad();
			
			$('.menu-link').bigSlide();
			
	        $(".date, .dateNotpicker").mask("99/99/9999");
	        $(".zipcode").mask("99999-999");
	        $(".cnpj").mask("99.999.999/9999-99");
	        $(".cpf").mask("999.999.999-99");
	        $(".phone").focusout(function () {
	            var element = $(this);
	            element.unmask();
	            var phone = element.val().replace(/\D/g, '');
	            if (phone.length > 10) {
	                element.mask("(99) 99999-999?9");
	            } else {
	                element.mask("(99) 9999-9999?9");
	            }
	        }).trigger("focusout");
	
	        $(".numeric").on("keydown", function (event) {
	            if (!(event.keyCode === 46 || event.keyCode === 8 || event.keyCode === 9 || event.keyCode === 13)) {
	                if ((event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105)) {
	                    event.preventDefault();
	                }
	            }
	        });
		}
};

// funcoes para trabalhar com SessionStorage
app.storage = {
		
		// limpa a sessao
		clear: function() {
			sessionStorage.clear();
		},
		
		// armazena um objeto na sessao
		put: function(key, value) {
			sessionStorage.setItem(key, JSON.stringify(value));
		},
		
		// recupera um objeto da sessao
		get: function(key) {
			return app.settings.isJson(sessionStorage.getItem(key)) ? JSON.parse(sessionStorage.getItem(key)) : null;
		},
		
		// remove um objeto da sessao
		remove: function(key) {
			sessionStorage.removeItem(key);
		}		
};

// redireciona para pagina por caminho relativo
app.callPage = function (page){
	  location.pathname = location.pathname.replace(/(.*)\/[^/]*/, "$1/"+page);
};

// funcoes utilitarias
app.utils = {
		simpleClassName: function(className) {
			return className.substring(className.lastIndexOf('.') + 1);
		},
		
		methodSignature: function(methodDesc) {
        	var $parameters = "";
           	var $count = 0;
       	  	$.each(methodDesc.parameters, function (p, paramDesc) {
	  			$count++;
	  			$parameters = $parameters + paramDesc.dataType + " " + paramDesc.label + (($count < methodDesc.parameters.length ? ", " : ""));
                
	  		});
       	  	
       	  	return methodDesc.returnType + " " + methodDesc.name + "(" + $parameters + ")";
		}
}


$(function() {
    app.settings.init();
});