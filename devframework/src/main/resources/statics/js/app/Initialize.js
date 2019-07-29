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

$(function() {
    app.settings.init();
});