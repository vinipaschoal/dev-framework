function RenderResult() {
	
	this.TABLE = function(data) {
		
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
		$table.append($tbody);
		
		var $dataTable = $table.DataTable(
				{
					"language": app.settings.languagePtBr,
					"paging": false,
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
		
		return $table;
		
	},
	this.PLAIN = function(data) {
		
		if ($.isPlainObject(data)) {
			return JSON.stringify(data, null, 2);
		} else if ($.isArray(data)) {
			var $str = "";
			var $length = data.length;
			$.each(data, function(index, value) {
				if ($.isPlainObject(value))
					$str = $str + JSON.stringify(value, null, 2);
				else
					$str = $str + value;
				
				if ($length != index)
					$str = $str + "<br/>";
			});
			
			return $str;
		}else{
			return data;
		}	
	},
	this.CHART_BAR = function(data) {
		
		var $canvas = document.createElement("canvas");
		$canvas.id = "chartBar";
		$canvas.className = "col-md-12";
		
		var myChart = new Chart($canvas, data);
		
		return $canvas;		
	},
	this.Map = function(data) {
		console.log(data);
	}
}