function RenderResult() {
	
	this.TABLE = function(data, place) {
		
		var $table = $('<table>');
		$table.addClass("table table-striped table-bordered");
		
		var $thead = $('<thead>');
		var $tr = $('<tr>');
		
		$.each(data.header, function(index, value) {
			var $row = $('<th>').text(value);
			$tr.append($row);
		});
		$thead.append($tr);
		$tr = $('<tr>');
		$table.append($thead);
				
		var $tbody = $('<tbody>');
		
		
		$.each(data.rows, function (index, value) {	
			$tr = $('<tr>');
			$.each(value, function (i, v) {
				
				var $row = $('<td>').text(v);
				$tr.append($row);
			});
			$tbody.append($tr);
       	});
		
		$table.append($tbody);
		
		
		/*
		var $dataTable = $table.DataTable(
				{
					"language": app.settings.languagePtBr,
					"paging": true,
					"pageLength": 2
					"searching": false,
					"ordering": false,
			        "info":     false,
			        "fnInitComplete": function(oSettings) {
			        	if (!data.showHeader)
			        		$table.children("thead").hide();
			        }
				});
		
		$.each(data.rows, function (index, value) {	
			var $arrValues = [];
			$.each(value, function (i, v) {
				$arrValues.push(v);
			});

			$dataTable.row.add($arrValues).draw( false );
       	});
       	
       	$(".table").DataTable(
			    							{
			    								"language": app.settings.languagePtBr,
			    								"paging": true,
			    								"pageLength": 2,
			    								"searching": false,
			    								"ordering": false,
			    						        "info":     false,
			    						        "fnInitComplete": function(oSettings) {
			    						        	console.log("Aqui porra");
			    						        	if (!result.data.showHeader)
			    						        		$(this).children("thead").hide();
			    						        }
			    							});
       	
		*/
		
		place.html($table);
		
		$table.DataTable(
				{
					"language": app.settings.languagePtBr,
					"paging": true,
					"pageLength": 10,
					"searching": true,
					"ordering": false,
			        "info":     true,
			        "fnInitComplete": function(oSettings) {
			        	console.log("Ai sim");
			        	if (!data.showHeader)
			        		$table.children("thead").hide();
			        }
				});
		
	},
	this.PLAIN = function(data, place) {
		
		//Se objeto, imprime
		if ($.isPlainObject(data)) {
			place.html(JSON.stringify(data, null, 2));
		//Se array, lista
		} else if ($.isArray(data)) {
			var $str = "";
			var $length = data.length;
			$.each(data, function(index, value) {
				//Se array de objeto, imprime
				if ($.isPlainObject(value))
					$str = $str + JSON.stringify(value, null, 2);
				else
					$str = $str + value;
				
				if ($length != index)
					$str = $str + "<br/>";
			});
			
			place.html($str);
		}else{
			place.html(data);
		}	
	},
	this.CHART_BAR = function(data, place) {
		
		var $canvas = document.createElement("canvas");
		$canvas.id = "chartBar";
		$canvas.className = "col-md-12";
		
		var myChart = new Chart($canvas, data);
		
		place.html($canvas);		
	},
	this.Map = function(data) {
		console.log(data);
	}
}